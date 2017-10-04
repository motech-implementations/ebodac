package org.motechproject.bookingapp.service.impl;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.LocalDate;
import org.motechproject.bookingapp.constants.BookingAppConstants;
import org.motechproject.bookingapp.domain.Clinic;
import org.motechproject.bookingapp.domain.VisitBookingDetails;
import org.motechproject.bookingapp.domain.VisitScheduleOffset;
import org.motechproject.bookingapp.dto.VisitRescheduleDto;
import org.motechproject.bookingapp.exception.LimitationExceededException;
import org.motechproject.bookingapp.helper.VisitLimitationHelper;
import org.motechproject.bookingapp.repository.ClinicDataService;
import org.motechproject.bookingapp.repository.VisitBookingDetailsDataService;
import org.motechproject.bookingapp.service.ConfigService;
import org.motechproject.bookingapp.service.VisitRescheduleService;
import org.motechproject.bookingapp.service.VisitScheduleOffsetService;
import org.motechproject.bookingapp.web.domain.BookingGridSettings;
import org.motechproject.commons.api.Range;
import org.motechproject.commons.date.model.Time;
import org.motechproject.ebodac.constants.EbodacConstants;
import org.motechproject.ebodac.domain.Visit;
import org.motechproject.ebodac.domain.enums.VisitType;
import org.motechproject.ebodac.repository.VisitDataService;
import org.motechproject.ebodac.service.EbodacEnrollmentService;
import org.motechproject.ebodac.service.LookupService;
import org.motechproject.ebodac.util.QueryParamsBuilder;
import org.motechproject.ebodac.web.domain.Records;
import org.motechproject.mds.query.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("visitRescheduleService")
public class VisitRescheduleServiceImpl implements VisitRescheduleService {

    @Autowired
    private LookupService lookupService;

    @Autowired
    private EbodacEnrollmentService ebodacEnrollmentService;

    @Autowired
    private VisitBookingDetailsDataService visitBookingDetailsDataService;

    @Autowired
    private VisitDataService visitDataService;

    @Autowired
    private ClinicDataService clinicDataService;

    @Autowired
    private VisitScheduleOffsetService visitScheduleOffsetService;

    @Autowired
    private VisitLimitationHelper visitLimitationHelper;

    @Autowired
    @Qualifier("configService")
    private org.motechproject.ebodac.service.ConfigService ebodacConfigService;

    @Autowired
    @Qualifier("bookingAppConfigService")
    private ConfigService bookingAppConfigService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Records<VisitRescheduleDto> getVisitsRecords(BookingGridSettings settings) throws IOException {
        QueryParams queryParams = QueryParamsBuilder.buildQueryParams(settings, getFields(settings.getFields()));
        Records<VisitBookingDetails> detailsRecords = lookupService.getEntities(VisitBookingDetails.class, settings.getLookup(), settings.getFields(), queryParams);

        Map<Long, Map<VisitType, VisitScheduleOffset>> offsetMap = visitScheduleOffsetService.getAllAsMap();
        List<String> boosterRelatedMessages = bookingAppConfigService.getConfig().getBoosterRelatedMessages();
        List<String> thirdVaccinationRelatedMessages = bookingAppConfigService.getConfig().getThirdVaccinationRelatedMessages();
        Long activeStageId = ebodacConfigService.getConfig().getActiveStageId();

        List<VisitRescheduleDto> dtos = new ArrayList<>();

        for (VisitBookingDetails details: detailsRecords.getRows()) {
            Long stageId = details.getVisit().getSubject().getStageId();

            if (stageId == null) {
                stageId = activeStageId;
            }

            Boolean boosterRelated = isBoosterRelated(details.getVisit().getType(), boosterRelatedMessages, stageId);
            Boolean thirdVaccinationRelated = isThirdVaccinationRelated(details.getVisit().getType(), thirdVaccinationRelatedMessages, stageId);
            LocalDate vaccinationDate = getVaccinationDate(details.getVisit(), boosterRelated, thirdVaccinationRelated);
            Boolean notVaccinated = true;
            Range<LocalDate> dateRange = null;

            if (vaccinationDate != null) {
                dateRange = calculateEarliestAndLatestDate(details.getVisit().getType(), offsetMap, vaccinationDate, stageId);
                notVaccinated = false;
            }

            dtos.add(new VisitRescheduleDto(details, dateRange, boosterRelated, notVaccinated));
        }

        return new Records<>(detailsRecords.getPage(), detailsRecords.getTotal(), detailsRecords.getRecords(), dtos);
    }

    @Override
    public VisitRescheduleDto saveVisitReschedule(VisitRescheduleDto visitRescheduleDto, Boolean ignoreLimitation) {
        VisitBookingDetails visitBookingDetails = visitBookingDetailsDataService.findById(visitRescheduleDto.getVisitBookingDetailsId());

        if (visitBookingDetails == null) {
            throw new IllegalArgumentException("Cannot reschedule, because details for Visit not found");
        }

        Clinic clinic = visitBookingDetails.getClinic();

        Visit visit = visitBookingDetails.getVisit();
        validateDate(visitRescheduleDto, visit);

        if (clinic != null && !ignoreLimitation) {
            checkNumberOfPatients(visitRescheduleDto, clinic);
        }

        updateVisitPlannedDate(visit, visitRescheduleDto);

        return new VisitRescheduleDto(updateVisitDetailsWithDto(visitBookingDetails, visitRescheduleDto));
    }

    private void checkNumberOfPatients(VisitRescheduleDto dto, Clinic clinic) { //NO CHECKSTYLE CyclomaticComplexity

        List<VisitBookingDetails> visits = visitBookingDetailsDataService
                .findByClinicIdVisitPlannedDateAndType(clinic.getId(), dto.getPlannedDate(), dto.getVisitType());

        visitLimitationHelper.checkCapacityForVisitBookingDetails(dto.getPlannedDate(), clinic, dto.getVisitId());

        if (visits != null && !visits.isEmpty()) {
            int numberOfRooms = clinic.getNumberOfRooms();
            int maxVisits = visitLimitationHelper.getMaxVisitCountForVisitType(dto.getVisitType(), clinic);
            int patients = 0;

            Time startTime = dto.getStartTime();
            Time endTime = null;

            if (startTime != null) {
                endTime = calculateEndTime(startTime);
            }

            for (VisitBookingDetails visit : visits) {
                if (visit.getId().equals(dto.getVisitBookingDetailsId())) {
                    maxVisits++;
                } else if (startTime != null && visit.getStartTime() != null) {
                    if (startTime.isBefore(visit.getStartTime())) {
                        if (visit.getStartTime().isBefore(endTime)) {
                            patients++;
                        }
                    } else {
                        if (startTime.isBefore(visit.getEndTime())) {
                            patients++;
                        }
                    }
                }
            }

            if (visits.size() >= maxVisits) {
                throw new LimitationExceededException("The booking limit for this type of visit has been reached");
            }
            if (patients >= numberOfRooms) {
                throw new LimitationExceededException("Too many visits at the same time");
            }
        }
    }

    private void validateDate(VisitRescheduleDto dto, Visit visit) {
        if (visit.getDate() != null) {
            throw new IllegalArgumentException("Cannot reschedule, because Visit already took place");
        }

        if (dto.getPlannedDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be in the past");
        }

        if (!dto.getIgnoreDateLimitation()) {
            Map<Long, Map<VisitType, VisitScheduleOffset>> offsetMap = visitScheduleOffsetService.getAllAsMap();
            List<String> boosterRelatedMessages = bookingAppConfigService.getConfig().getBoosterRelatedMessages();
            List<String> thirdVaccinationRelatedMessages = bookingAppConfigService.getConfig().getThirdVaccinationRelatedMessages();
            Long activeStageId = ebodacConfigService.getConfig().getActiveStageId();

            Range<LocalDate> dateRange = calculateEarliestAndLatestDate(visit, offsetMap, boosterRelatedMessages,
                    thirdVaccinationRelatedMessages, activeStageId);

            if (dateRange == null) {
                throw new IllegalArgumentException("Cannot calculate Earliest and Latest Date");
            }

            LocalDate earliestDate = dateRange.getMin();
            LocalDate latestDate = dateRange.getMax();

            if (dto.getPlannedDate().isBefore(earliestDate) || dto.getPlannedDate().isAfter(latestDate)) {
                throw new IllegalArgumentException(String.format("The date should be between %s and %s but is %s",
                        earliestDate, latestDate, dto.getPlannedDate()));
            }
        }
    }

    private VisitBookingDetails updateVisitDetailsWithDto(VisitBookingDetails details, VisitRescheduleDto dto) {
        details.setStartTime(dto.getStartTime());
        details.setEndTime(calculateEndTime(dto.getStartTime()));
        details.setIgnoreDateLimitation(dto.getIgnoreDateLimitation());
        return visitBookingDetailsDataService.update(details);
    }

    private Visit updateVisitPlannedDate(Visit visit, VisitRescheduleDto visitRescheduleDto) {
        visit.setMotechProjectedDate(visitRescheduleDto.getPlannedDate());

        if (ebodacEnrollmentService.checkIfEnrolledAndUpdateEnrollment(visit)) {
            ebodacEnrollmentService.reenrollSubject(visit);
        }

        return visitDataService.update(visit);
    }

    private Map<String, Object> getFields(String json) throws IOException {
        if (json == null) {
            return null;
        } else {
            return objectMapper.readValue(json, new TypeReference<LinkedHashMap>() {});  //NO CHECKSTYLE WhitespaceAround
        }
    }

    private Time calculateEndTime(Time startTime) {
        int endTimeHour = (startTime.getHour() + BookingAppConstants.TIME_OF_THE_VISIT) % BookingAppConstants.MAX_TIME_HOUR;
        return new Time(endTimeHour, startTime.getMinute());
    }

    private Range<LocalDate> calculateEarliestAndLatestDate(Visit visit, Map<Long, Map<VisitType, VisitScheduleOffset>> offsetMap,
                                                            List<String> boosterRelatedMessages, List<String> thirdVaccinationRelatedMessages,
                                                            Long activeStageId) {
        Long stageId = visit.getSubject().getStageId();

        if (stageId == null) {
            stageId = activeStageId;
        }

        Boolean boosterRelated = isBoosterRelated(visit.getType(), boosterRelatedMessages, stageId);
        Boolean thirdVaccinationRelated = isThirdVaccinationRelated(visit.getType(), thirdVaccinationRelatedMessages, stageId);
        LocalDate vaccinationDate = getVaccinationDate(visit, boosterRelated, thirdVaccinationRelated);

        if (vaccinationDate == null) {
            return null;
        }

        return calculateEarliestAndLatestDate(visit.getType(), offsetMap, vaccinationDate, stageId);
    }

    private Range<LocalDate> calculateEarliestAndLatestDate(VisitType visitType, Map<Long, Map<VisitType, VisitScheduleOffset>> offsetMap,
                                                            LocalDate vaccinationDate, Long stageId) {
        if (stageId == null) {
            return null;
        }

        Map<VisitType, VisitScheduleOffset> visitTypeOffset = offsetMap.get(stageId);

        if (visitTypeOffset == null) {
            return null;
        }

        VisitScheduleOffset offset = visitTypeOffset.get(visitType);

        if (offset == null) {
            return null;
        }

        LocalDate minDate = vaccinationDate.plusDays(offset.getEarliestDateOffset());
        LocalDate maxDate = vaccinationDate.plusDays(offset.getLatestDateOffset());

        return new Range<>(minDate, maxDate);
    }

    private LocalDate getVaccinationDate(Visit visit, Boolean boosterRelated, Boolean thirdVaccinationRelated) {
        if (boosterRelated) {
            return visit.getSubject().getBoosterVaccinationDate();
        } else if (thirdVaccinationRelated) {
            return getThirdVaccinationDate(visit);
        } else {
            return visit.getSubject().getPrimerVaccinationDate();
        }
    }

    private LocalDate getThirdVaccinationDate(Visit visit) {
        List<Visit> visits = visit.getSubject().getVisits();
        LocalDate vaccinationDate = null;

        for (Visit v : visits) {
            if (VisitType.THIRD_VACCINATION_DAY.equals(v.getType())) {
                vaccinationDate = v.getDate();
            }
        }

        return vaccinationDate;
    }

    private Boolean isBoosterRelated(VisitType visitType, List<String> boosterRelatedMessages, Long stageId) {
        String campaignName = getCampaignNameWithStage(visitType, stageId);
        return boosterRelatedMessages.contains(campaignName);
    }

    private Boolean isThirdVaccinationRelated(VisitType visitType, List<String> thirdVaccinationRelatedMessages, Long stageId) {
        String campaignName = getCampaignNameWithStage(visitType, stageId);
        return thirdVaccinationRelatedMessages.contains(campaignName);
    }

    private String getCampaignNameWithStage(VisitType visitType, Long stageId) {
        if (stageId == null) {
            return null;
        }

        if (stageId > 1) {
            return visitType.getMotechValue() + EbodacConstants.STAGE + stageId;
        }

        return visitType.getMotechValue();
    }
}
