package org.motechproject.bookingapp.service.impl;

import org.apache.commons.lang.Validate;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.LocalDate;
import org.motechproject.bookingapp.constants.BookingAppConstants;
import org.motechproject.bookingapp.domain.Clinic;
import org.motechproject.bookingapp.domain.Screening;
import org.motechproject.bookingapp.domain.ScreeningDto;
import org.motechproject.bookingapp.domain.Volunteer;
import org.motechproject.bookingapp.exception.LimitationExceededException;
import org.motechproject.bookingapp.helper.VisitLimitationHelper;
import org.motechproject.bookingapp.repository.ClinicDataService;
import org.motechproject.bookingapp.repository.ScreeningDataService;
import org.motechproject.bookingapp.repository.VolunteerDataService;
import org.motechproject.bookingapp.service.ScreeningService;
import org.motechproject.bookingapp.util.ScreeningValidator;
import org.motechproject.bookingapp.web.domain.BookingGridSettings;
import org.motechproject.commons.date.model.Time;
import org.motechproject.ebodac.service.LookupService;
import org.motechproject.ebodac.util.QueryParamsBuilder;
import org.motechproject.ebodac.web.domain.Records;
import org.motechproject.mds.query.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("screeningService")
public class ScreeningServiceImpl implements ScreeningService {

    @Autowired
    private ScreeningDataService screeningDataService;

    @Autowired
    private VolunteerDataService volunteerDataService;

    @Autowired
    private ClinicDataService clinicDataService;

    @Autowired
    private VisitLimitationHelper visitLimitationHelper;

    @Autowired
    private LookupService lookupService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Records<Screening> getScreenings(BookingGridSettings bookingGridSettings) throws IOException {
        QueryParams queryParams = QueryParamsBuilder.buildQueryParams(bookingGridSettings, getFields(bookingGridSettings.getFields()));
        return lookupService.getEntities(Screening.class, bookingGridSettings.getLookup(), bookingGridSettings.getFields(), queryParams);
    }

    @Override
    public Screening addOrUpdate(ScreeningDto screeningDto, Boolean ignoreLimitation) {
        if (screeningDto.getId() != null) {
            return update(screeningDto, ignoreLimitation);
        }
        return add(screeningDto, ignoreLimitation);
    }

    @Override
    public Screening add(ScreeningDto screeningDto, Boolean ignoreLimitation) {

        ScreeningValidator.validateForAdd(screeningDto);

        Screening screening = new Screening();
        screening.setVolunteer(volunteerDataService.create(new Volunteer()));
        checkNumberOfPatientsAndSetScreeningData(screeningDto, screening, ignoreLimitation);

        return screeningDataService.create(screening);
    }

    @Override
    public Screening update(ScreeningDto screeningDto, Boolean ignoreLimitation) {

        ScreeningValidator.validateForUpdate(screeningDto);

        Long screeningId = Long.parseLong(screeningDto.getId());
        Screening screening = screeningDataService.findById(screeningId);

        Validate.notNull(screening, String.format("Screening with id \"%s\" doesn't exist!", screeningId));

        checkNumberOfPatientsAndSetScreeningData(screeningDto, screening, ignoreLimitation);

        return screeningDataService.update(screening);
    }

    @Override
    public ScreeningDto getScreeningById(Long id) {
        return screeningDataService.findById(id).toDto();
    }

    private void checkNumberOfPatientsAndSetScreeningData(ScreeningDto screeningDto, Screening screening, Boolean ignoreLimitation) {
        Clinic clinic = clinicDataService.findById(Long.parseLong(screeningDto.getClinicId()));
        LocalDate date = LocalDate.parse(screeningDto.getDate());
        Time startTime = Time.valueOf(screeningDto.getStartTime());
        Time endTime = calculateEndTime(startTime);

        if (!ignoreLimitation) {
            visitLimitationHelper.checkCapacityForScreening(date, clinic, screening.getId());
            List<Screening> screeningList = screeningDataService.findByDateAndClinicId(date, clinic.getId());

            if (screeningList != null) {
                int numberOfRooms = clinic.getNumberOfRooms();
                int maxVisits = clinic.getMaxScreeningVisits();
                int patients = 0;

                for (Screening s : screeningList) {
                    if (s.getId().equals(screening.getId())) {
                        maxVisits++;
                    } else {
                        if (startTime.isBefore(s.getStartTime())) {
                            if (s.getStartTime().isBefore(endTime)) {
                                patients++;
                            }
                        } else {
                            if (startTime.isBefore(s.getEndTime())) {
                                patients++;
                            }
                        }
                    }
                }

                if (screeningList.size() >= maxVisits) {
                    throw new LimitationExceededException("The limit of the type of the visit is reached for this day");
                }
                if (patients >= numberOfRooms) {
                    throw new LimitationExceededException("Too many visits at the same time");
                }
            }
        }

        screening.setDate(date);
        screening.setStartTime(startTime);
        screening.setEndTime(endTime);
        screening.setClinic(clinic);
    }

    private Map<String, Object> getFields(String json) throws IOException {
        if (json == null) {
            return null;
        } else {
            return objectMapper.readValue(json, new TypeReference<LinkedHashMap>() {}); //NO CHECKSTYLE WhitespaceAround
        }
    }

    private Time calculateEndTime(Time startTime) {
        int endTimeHour = (startTime.getHour() + BookingAppConstants.TIME_OF_THE_VISIT) % BookingAppConstants.MAX_TIME_HOUR;
        return new Time(endTimeHour, startTime.getMinute());
    }
}
