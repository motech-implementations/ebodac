package org.motechproject.bookingapp.service;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.motechproject.bookingapp.domain.Config;
import org.motechproject.bookingapp.domain.VisitBookingDetails;
import org.motechproject.bookingapp.dto.VisitRescheduleDto;
import org.motechproject.bookingapp.domain.VisitScheduleOffset;
import org.motechproject.bookingapp.repository.VisitBookingDetailsDataService;
import org.motechproject.bookingapp.service.impl.VisitRescheduleServiceImpl;
import org.motechproject.bookingapp.web.domain.BookingGridSettings;
import org.motechproject.commons.api.Range;
import org.motechproject.commons.date.model.Time;
import org.motechproject.ebodac.domain.enums.Language;
import org.motechproject.ebodac.domain.Subject;
import org.motechproject.ebodac.domain.Visit;
import org.motechproject.ebodac.domain.enums.VisitType;
import org.motechproject.ebodac.repository.VisitDataService;
import org.motechproject.ebodac.service.EbodacEnrollmentService;
import org.motechproject.ebodac.service.LookupService;
import org.motechproject.ebodac.web.domain.Records;
import org.motechproject.mds.query.QueryParams;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(PowerMockRunner.class)
@PrepareForTest(VisitRescheduleServiceImpl.class)
public class VisitRescheduleServiceTest {


    @InjectMocks
    private VisitRescheduleService visitRescheduleService = new VisitRescheduleServiceImpl();

    @Mock
    private org.motechproject.ebodac.service.ConfigService ebodacConfigService;

    @Mock
    private ConfigService bookingAppConfigService;

    @Mock
    private VisitScheduleOffsetService visitScheduleOffsetService;

    @Mock
    private VisitDataService visitDataService;

    @Mock
    private VisitBookingDetailsDataService visitBookingDetailsDataService;

    @Mock
    private EbodacEnrollmentService ebodacEnrollmentService;

    @Mock
    private LookupService lookupService;

    private Subject subject1;

    private Subject subject2;

    @Before
    public void setUp() {
        initMocks(this);
        subject1 = new Subject("1", "asd", "asd", "asd", "123", "asd", Language.English, "asd", "asd", "asd", "asd", "asd", "asd");
        subject1.setPrimerVaccinationDate(new LocalDate(2217, 2, 1));
        subject1.setBoosterVaccinationDate(new LocalDate(2217, 3, 1));
        subject2 = new Subject("2", "asd", "asd", "asd", "123", "asd", Language.English, "asd", "asd", "asd", "asd", "asd", "asd");
    }

    @Test
    public void shouldGetVisitBookingDetailsRecords() throws IOException {
        BookingGridSettings bookingGridSettings = new BookingGridSettings();
        bookingGridSettings.setPage(1);
        bookingGridSettings.setRows(10);

        List<VisitBookingDetails> visitBookingDetails = new ArrayList<>(Arrays.asList(
                new VisitBookingDetails(new LocalDate(2217, 4, 1), createVisit(1L, VisitType.PRIME_VACCINATION_FIRST_FOLLOW_UP_VISIT, null, new LocalDate(2217, 4, 1), subject1)),
                new VisitBookingDetails(new LocalDate(2217, 4, 1), createVisit(2L, VisitType.BOOST_VACCINATION_FIRST_FOLLOW_UP_VISIT, null, new LocalDate(2217, 4, 1), subject1)),
                new VisitBookingDetails(new LocalDate(2217, 4, 1), createVisit(1L, VisitType.FIRST_LONG_TERM_FOLLOW_UP_VISIT, null, new LocalDate(2217, 4, 1), subject2))
        ));

        Records<VisitBookingDetails> records = new Records<>(1, 10, 3, visitBookingDetails);

        when(lookupService.getEntities(eq(VisitBookingDetails.class), anyString(), anyString(), any(QueryParams.class))).thenReturn(records);

        List<VisitScheduleOffset> visitScheduleOffsets = new ArrayList<>(Arrays.asList(
                createVisitScheduleOffset(VisitType.PRIME_VACCINATION_FIRST_FOLLOW_UP_VISIT, 2L, 10, 5, 12),
                createVisitScheduleOffset(VisitType.BOOST_VACCINATION_FIRST_FOLLOW_UP_VISIT, 2L, 20, 15, 24),
                createVisitScheduleOffset(VisitType.FIRST_LONG_TERM_FOLLOW_UP_VISIT, 2L, 30, 25, 27)
        ));
        Map<VisitType, VisitScheduleOffset> offsetMapForStageId = new LinkedHashMap<>();
        offsetMapForStageId.put(VisitType.PRIME_VACCINATION_FIRST_FOLLOW_UP_VISIT, visitScheduleOffsets.get(0));
        offsetMapForStageId.put(VisitType.BOOST_VACCINATION_FIRST_FOLLOW_UP_VISIT, visitScheduleOffsets.get(1));
        offsetMapForStageId.put(VisitType.FIRST_LONG_TERM_FOLLOW_UP_VISIT, visitScheduleOffsets.get(2));
        Map<Long, Map<VisitType, VisitScheduleOffset>> offsetMap = new LinkedHashMap<>();
        offsetMap.put(2L, offsetMapForStageId);

        when(visitScheduleOffsetService.getAllAsMap()).thenReturn(offsetMap);

        org.motechproject.ebodac.domain.Config ebodacConfig = new org.motechproject.ebodac.domain.Config();
        ebodacConfig.setActiveStageId(2L);

        Config bookingAppConfig = new Config();
        List<String> boosterRelatedMessage = new ArrayList<>(Collections.singletonList("Boost Vaccination First Follow-up visit - stage 2"));
        bookingAppConfig.setBoosterRelatedMessages(boosterRelatedMessage);

        when(ebodacConfigService.getConfig()).thenReturn(ebodacConfig);
        when(bookingAppConfigService.getConfig()).thenReturn(bookingAppConfig);

        List<VisitRescheduleDto> expectedDtos = new ArrayList<>(Arrays.asList(
                new VisitRescheduleDto(visitBookingDetails.get(0), new Range<>(new LocalDate(2217, 2, 6), new LocalDate(2217, 2, 13)), false, false, false),
                new VisitRescheduleDto(visitBookingDetails.get(1), new Range<>(new LocalDate(2217, 3, 16), new LocalDate(2217, 3, 25)), true, false, false),
                new VisitRescheduleDto(visitBookingDetails.get(2), null, false, false, true)
        ));

        List<VisitRescheduleDto> resultDtos = visitRescheduleService.getVisitsRecords(bookingGridSettings).getRows();

        checkVisitRescheduleDtoList(expectedDtos, resultDtos);
    }

    @Test
    public void shouldCalculateThirdVaccinationRelatedVisitsDateRangeUsingThirdVaccinationDate() throws IOException {
        List<VisitBookingDetails> visitBookingDetails = new ArrayList<>(Arrays.asList(
                new VisitBookingDetails(null, createVisit(1L, VisitType.THIRD_VACCINATION_DAY, new LocalDate(2217, 4, 1), null, subject1)),
                new VisitBookingDetails(null, createVisit(2L, VisitType.FIRST_POST_THIRD_VACCINATION_VISIT, null, null, subject1)),
                new VisitBookingDetails(null, createVisit(3L, VisitType.SECOND_POST_THIRD_VACCINATION_VISIT, null, null, subject1))
        ));

        Records<VisitBookingDetails> records = new Records<>(1, 10, 3, visitBookingDetails);

        when(lookupService.getEntities(eq(VisitBookingDetails.class), anyString(), anyString(), any(QueryParams.class))).thenReturn(records);

        Map<VisitType, VisitScheduleOffset> offsetMapForStageId = new LinkedHashMap<>();
        offsetMapForStageId.put(VisitType.THIRD_VACCINATION_DAY, createVisitScheduleOffset(VisitType.THIRD_VACCINATION_DAY, 1L, 10, 5, 15));
        offsetMapForStageId.put(VisitType.FIRST_POST_THIRD_VACCINATION_VISIT, createVisitScheduleOffset(VisitType.FIRST_POST_THIRD_VACCINATION_VISIT, 1L, 4, 4, 4));
        offsetMapForStageId.put(VisitType.SECOND_POST_THIRD_VACCINATION_VISIT, createVisitScheduleOffset(VisitType.SECOND_POST_THIRD_VACCINATION_VISIT, 1L, 14, 7, 21));
        Map<Long, Map<VisitType, VisitScheduleOffset>> offsetMap = new LinkedHashMap<>();
        offsetMap.put(1L, offsetMapForStageId);

        when(visitScheduleOffsetService.getAllAsMap()).thenReturn(offsetMap);

        org.motechproject.ebodac.domain.Config ebodacConfig = new org.motechproject.ebodac.domain.Config();
        ebodacConfig.setActiveStageId(1L);

        Config bookingAppConfig = new Config();
        List<String> thirdVaccinationRelatedMessage = new ArrayList<>(Arrays.asList("First Post Third Vaccination visit", "Second Post Third Vaccination visit"));
        bookingAppConfig.setThirdVaccinationRelatedMessages(thirdVaccinationRelatedMessage);

        when(ebodacConfigService.getConfig()).thenReturn(ebodacConfig);
        when(bookingAppConfigService.getConfig()).thenReturn(bookingAppConfig);

        List<VisitRescheduleDto> expectedDtos = new ArrayList<>(Arrays.asList(
                new VisitRescheduleDto(visitBookingDetails.get(0), new Range<>(new LocalDate(2217, 2, 6), new LocalDate(2217, 2, 16)), false, false, false),
                new VisitRescheduleDto(visitBookingDetails.get(1), new Range<>(new LocalDate(2217, 4, 5), new LocalDate(2217, 4, 5)), false, true, false),
                new VisitRescheduleDto(visitBookingDetails.get(2), new Range<>(new LocalDate(2217, 4, 8), new LocalDate(2217, 4, 22)), false, true, false)
        ));

        List<VisitRescheduleDto> resultDtos = visitRescheduleService.getVisitsRecords(new BookingGridSettings()).getRows();

        checkVisitRescheduleDtoList(expectedDtos, resultDtos);
    }

    @Test
    public void shouldNotCalculateThirdVaccinationRelatedVisitsDateRangeIfThirdVaccinationDateIsNull() throws IOException {
        List<VisitBookingDetails> visitBookingDetails = new ArrayList<>(Arrays.asList(
                new VisitBookingDetails(null, createVisit(1L, VisitType.THIRD_VACCINATION_DAY, null, null, subject1)),
                new VisitBookingDetails(null, createVisit(2L, VisitType.FIRST_POST_THIRD_VACCINATION_VISIT, null, null, subject1)),
                new VisitBookingDetails(null, createVisit(3L, VisitType.SECOND_POST_THIRD_VACCINATION_VISIT, null, null, subject1))
        ));

        Records<VisitBookingDetails> records = new Records<>(1, 10, 3, visitBookingDetails);

        when(lookupService.getEntities(eq(VisitBookingDetails.class), anyString(), anyString(), any(QueryParams.class))).thenReturn(records);

        Map<VisitType, VisitScheduleOffset> offsetMapForStageId = new LinkedHashMap<>();
        offsetMapForStageId.put(VisitType.THIRD_VACCINATION_DAY, createVisitScheduleOffset(VisitType.THIRD_VACCINATION_DAY, 1L, 10, 5, 15));
        offsetMapForStageId.put(VisitType.FIRST_POST_THIRD_VACCINATION_VISIT, createVisitScheduleOffset(VisitType.FIRST_POST_THIRD_VACCINATION_VISIT, 1L, 4, 4, 4));
        offsetMapForStageId.put(VisitType.SECOND_POST_THIRD_VACCINATION_VISIT, createVisitScheduleOffset(VisitType.SECOND_POST_THIRD_VACCINATION_VISIT, 1L, 14, 7, 21));
        Map<Long, Map<VisitType, VisitScheduleOffset>> offsetMap = new LinkedHashMap<>();
        offsetMap.put(1L, offsetMapForStageId);

        when(visitScheduleOffsetService.getAllAsMap()).thenReturn(offsetMap);

        org.motechproject.ebodac.domain.Config ebodacConfig = new org.motechproject.ebodac.domain.Config();
        ebodacConfig.setActiveStageId(1L);

        Config bookingAppConfig = new Config();
        List<String> thirdVaccinationRelatedMessage = new ArrayList<>(Arrays.asList("First Post Third Vaccination visit", "Second Post Third Vaccination visit"));
        bookingAppConfig.setThirdVaccinationRelatedMessages(thirdVaccinationRelatedMessage);

        when(ebodacConfigService.getConfig()).thenReturn(ebodacConfig);
        when(bookingAppConfigService.getConfig()).thenReturn(bookingAppConfig);

        List<VisitRescheduleDto> expectedDtos = new ArrayList<>(Arrays.asList(
                new VisitRescheduleDto(visitBookingDetails.get(0), new Range<>(new LocalDate(2217, 2, 6), new LocalDate(2217, 2, 16)), false, false, false),
                new VisitRescheduleDto(visitBookingDetails.get(1), null, false, true, true),
                new VisitRescheduleDto(visitBookingDetails.get(2), null, false, true, true)
        ));

        List<VisitRescheduleDto> resultDtos = visitRescheduleService.getVisitsRecords(new BookingGridSettings()).getRows();

        checkVisitRescheduleDtoList(expectedDtos, resultDtos);
    }

    @Test
    public void shouldNotCalculateVisitDateRangeIfStageIsNull() throws IOException {
        List<VisitBookingDetails> visitBookingDetails = new ArrayList<>(Arrays.asList(
                new VisitBookingDetails(null, createVisit(1L, VisitType.FIRST_LONG_TERM_FOLLOW_UP_VISIT, null, null, subject1)),
                new VisitBookingDetails(null, createVisit(2L, VisitType.SECOND_LONG_TERM_FOLLOW_UP_VISIT, null, null, subject1))
        ));

        Records<VisitBookingDetails> records = new Records<>(1, 10, 3, visitBookingDetails);

        when(lookupService.getEntities(eq(VisitBookingDetails.class), anyString(), anyString(), any(QueryParams.class))).thenReturn(records);

        Map<VisitType, VisitScheduleOffset> offsetMapForStageId = new LinkedHashMap<>();
        offsetMapForStageId.put(VisitType.FIRST_LONG_TERM_FOLLOW_UP_VISIT, createVisitScheduleOffset(VisitType.FIRST_LONG_TERM_FOLLOW_UP_VISIT, 1L, 10, 5, 15));
        offsetMapForStageId.put(VisitType.SECOND_LONG_TERM_FOLLOW_UP_VISIT, createVisitScheduleOffset(VisitType.SECOND_LONG_TERM_FOLLOW_UP_VISIT, 1L, 4, 4, 4));

        Map<Long, Map<VisitType, VisitScheduleOffset>> offsetMap = new LinkedHashMap<>();
        offsetMap.put(1L, offsetMapForStageId);

        when(visitScheduleOffsetService.getAllAsMap()).thenReturn(offsetMap);

        org.motechproject.ebodac.domain.Config ebodacConfig = new org.motechproject.ebodac.domain.Config();
        ebodacConfig.setActiveStageId(null);

        when(ebodacConfigService.getConfig()).thenReturn(ebodacConfig);
        when(bookingAppConfigService.getConfig()).thenReturn(new Config());

        List<VisitRescheduleDto> expectedDtos = new ArrayList<>(Arrays.asList(
                new VisitRescheduleDto(visitBookingDetails.get(0), null, false, false, false),
                new VisitRescheduleDto(visitBookingDetails.get(1), null, false, false, false)
        ));

        List<VisitRescheduleDto> resultDtos = visitRescheduleService.getVisitsRecords(new BookingGridSettings()).getRows();

        checkVisitRescheduleDtoList(expectedDtos, resultDtos);
    }

    @Test
    public void shouldNotCalculateVisitDateRangeIfVisitScheduleOffsetIsMissing() throws IOException {
        List<VisitBookingDetails> visitBookingDetails = new ArrayList<>(Arrays.asList(
                new VisitBookingDetails(null, createVisit(1L, VisitType.FIRST_LONG_TERM_FOLLOW_UP_VISIT, null, null, subject1)),
                new VisitBookingDetails(null, createVisit(2L, VisitType.SECOND_LONG_TERM_FOLLOW_UP_VISIT, null, null, subject1))
        ));

        Records<VisitBookingDetails> records = new Records<>(1, 10, 3, visitBookingDetails);

        when(lookupService.getEntities(eq(VisitBookingDetails.class), anyString(), anyString(), any(QueryParams.class))).thenReturn(records);

        when(visitScheduleOffsetService.getAllAsMap()).thenReturn(new HashMap<Long, Map<VisitType, VisitScheduleOffset>>());

        org.motechproject.ebodac.domain.Config ebodacConfig = new org.motechproject.ebodac.domain.Config();
        ebodacConfig.setActiveStageId(1L);

        when(ebodacConfigService.getConfig()).thenReturn(ebodacConfig);
        when(bookingAppConfigService.getConfig()).thenReturn(new Config());

        List<VisitRescheduleDto> expectedDtos = new ArrayList<>(Arrays.asList(
                new VisitRescheduleDto(visitBookingDetails.get(0), null, false, false, false),
                new VisitRescheduleDto(visitBookingDetails.get(1), null, false, false, false)
        ));

        List<VisitRescheduleDto> resultDtos = visitRescheduleService.getVisitsRecords(new BookingGridSettings()).getRows();

        checkVisitRescheduleDtoList(expectedDtos, resultDtos);
    }

    @Test
    public void shouldSaveRescheduledVisit() {
        VisitBookingDetails visitBookingDetails = new VisitBookingDetails(null, createVisit(1L, VisitType.BOOST_VACCINATION_FIRST_FOLLOW_UP_VISIT, null, new LocalDate(2217, 1, 1), subject1));

        VisitRescheduleDto visitRescheduleDto = new VisitRescheduleDto(visitBookingDetails, new Range<>(new LocalDate(2217, 2, 1), new LocalDate(2217, 3, 1)), true, false, false);
        Boolean ignoreLimitation = true;
        visitRescheduleDto.setStartTime(new Time(9, 0));
        visitRescheduleDto.setIgnoreDateLimitation(ignoreLimitation);
        visitRescheduleDto.setVisitBookingDetailsId(1L);

        when(visitBookingDetailsDataService.findById(1L)).thenReturn(visitBookingDetails);
        when(ebodacEnrollmentService.checkIfEnrolledAndUpdateEnrollment(visitBookingDetails.getVisit())).thenReturn(true);
        when(visitDataService.update(visitBookingDetails.getVisit())).thenReturn(visitBookingDetails.getVisit());
        when(visitBookingDetailsDataService.update(visitBookingDetails)).thenReturn(visitBookingDetails);

        visitRescheduleService.saveVisitReschedule(visitRescheduleDto, ignoreLimitation);

        verify(visitBookingDetailsDataService).update(any(VisitBookingDetails.class));
        verify(visitDataService).update(any(Visit.class));
        verify(ebodacEnrollmentService).reenrollSubject(visitBookingDetails.getVisit());
        verify(visitBookingDetailsDataService, never()).findByClinicIdVisitPlannedDateAndType(anyLong(), any(LocalDate.class), any(VisitType.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenVisitAlreadyTookPlace() {
        VisitBookingDetails visitBookingDetails = new VisitBookingDetails(null, createVisit(1L, VisitType.BOOST_VACCINATION_FIRST_FOLLOW_UP_VISIT, new LocalDate(2217, 1, 1), new LocalDate(2217, 1, 1), subject1));

        VisitRescheduleDto visitRescheduleDto = new VisitRescheduleDto(visitBookingDetails);
        visitRescheduleDto.setStartTime(new Time(9, 0));
        visitRescheduleDto.setIgnoreDateLimitation(true);
        visitRescheduleDto.setVisitBookingDetailsId(1L);

        when(visitBookingDetailsDataService.findById(1L)).thenReturn(visitBookingDetails);
        when(ebodacEnrollmentService.checkIfEnrolledAndUpdateEnrollment(visitBookingDetails.getVisit())).thenReturn(false);
        when(visitDataService.update(visitBookingDetails.getVisit())).thenReturn(visitBookingDetails.getVisit());
        when(visitBookingDetailsDataService.update(visitBookingDetails)).thenReturn(visitBookingDetails);

        visitRescheduleService.saveVisitReschedule(visitRescheduleDto, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenPlannedDateIsInThePast() {
        VisitBookingDetails visitBookingDetails = new VisitBookingDetails(null, createVisit(1L, VisitType.BOOST_VACCINATION_FIRST_FOLLOW_UP_VISIT, null, new LocalDate(2015, 1, 1), subject1));

        VisitRescheduleDto visitRescheduleDto = new VisitRescheduleDto(visitBookingDetails);
        visitRescheduleDto.setStartTime(new Time(9, 0));
        visitRescheduleDto.setIgnoreDateLimitation(true);
        visitRescheduleDto.setVisitBookingDetailsId(1L);

        when(visitBookingDetailsDataService.findById(1L)).thenReturn(visitBookingDetails);
        when(ebodacEnrollmentService.checkIfEnrolledAndUpdateEnrollment(visitBookingDetails.getVisit())).thenReturn(false);
        when(visitDataService.update(visitBookingDetails.getVisit())).thenReturn(visitBookingDetails.getVisit());
        when(visitBookingDetailsDataService.update(visitBookingDetails)).thenReturn(visitBookingDetails);

        visitRescheduleService.saveVisitReschedule(visitRescheduleDto, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCannotCalculateVisitWindow() {
        VisitBookingDetails visitBookingDetails = new VisitBookingDetails(null, createVisit(1L, VisitType.BOOST_VACCINATION_FIRST_FOLLOW_UP_VISIT, null, new LocalDate(2217, 1, 1), subject1));

        VisitRescheduleDto visitRescheduleDto = new VisitRescheduleDto(visitBookingDetails);
        visitRescheduleDto.setStartTime(new Time(9, 0));
        visitRescheduleDto.setIgnoreDateLimitation(false);
        visitRescheduleDto.setVisitBookingDetailsId(1L);

        when(visitBookingDetailsDataService.findById(1L)).thenReturn(visitBookingDetails);
        when(ebodacEnrollmentService.checkIfEnrolledAndUpdateEnrollment(visitBookingDetails.getVisit())).thenReturn(false);
        when(visitDataService.update(visitBookingDetails.getVisit())).thenReturn(visitBookingDetails.getVisit());
        when(visitBookingDetailsDataService.update(visitBookingDetails)).thenReturn(visitBookingDetails);

        org.motechproject.ebodac.domain.Config ebodacConfig = new org.motechproject.ebodac.domain.Config();
        ebodacConfig.setActiveStageId(1L);

        when(ebodacConfigService.getConfig()).thenReturn(ebodacConfig);
        when(bookingAppConfigService.getConfig()).thenReturn(new Config());

        visitRescheduleService.saveVisitReschedule(visitRescheduleDto, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenVisitPlannedDateOutOfWindow() {
        VisitBookingDetails visitBookingDetails = new VisitBookingDetails(null, createVisit(1L, VisitType.BOOST_VACCINATION_FIRST_FOLLOW_UP_VISIT, null, new LocalDate(2217, 1, 1), subject1));

        VisitRescheduleDto visitRescheduleDto = new VisitRescheduleDto(visitBookingDetails);
        visitRescheduleDto.setStartTime(new Time(9, 0));
        visitRescheduleDto.setIgnoreDateLimitation(false);
        visitRescheduleDto.setVisitBookingDetailsId(1L);

        when(visitBookingDetailsDataService.findById(1L)).thenReturn(visitBookingDetails);
        when(ebodacEnrollmentService.checkIfEnrolledAndUpdateEnrollment(visitBookingDetails.getVisit())).thenReturn(false);
        when(visitDataService.update(visitBookingDetails.getVisit())).thenReturn(visitBookingDetails.getVisit());
        when(visitBookingDetailsDataService.update(visitBookingDetails)).thenReturn(visitBookingDetails);

        Map<VisitType, VisitScheduleOffset> offsetMapForStageId = new LinkedHashMap<>();
        offsetMapForStageId.put(VisitType.BOOST_VACCINATION_FIRST_FOLLOW_UP_VISIT, createVisitScheduleOffset(VisitType.BOOST_VACCINATION_FIRST_FOLLOW_UP_VISIT, 1L, 10, 5, 15));

        Map<Long, Map<VisitType, VisitScheduleOffset>> offsetMap = new LinkedHashMap<>();
        offsetMap.put(1L, offsetMapForStageId);

        when(visitScheduleOffsetService.getAllAsMap()).thenReturn(offsetMap);

        org.motechproject.ebodac.domain.Config ebodacConfig = new org.motechproject.ebodac.domain.Config();
        ebodacConfig.setActiveStageId(1L);

        when(ebodacConfigService.getConfig()).thenReturn(ebodacConfig);
        when(bookingAppConfigService.getConfig()).thenReturn(new Config());

        visitRescheduleService.saveVisitReschedule(visitRescheduleDto, true);
    }

    private VisitScheduleOffset createVisitScheduleOffset(VisitType visitType, Long stageId, Integer timeOffset, Integer earliestDateOffset, Integer latestDateOffset) {
        VisitScheduleOffset visitScheduleOffset = new VisitScheduleOffset();
        visitScheduleOffset.setVisitType(visitType);
        visitScheduleOffset.setStageId(stageId);
        visitScheduleOffset.setTimeOffset(timeOffset);
        visitScheduleOffset.setEarliestDateOffset(earliestDateOffset);
        visitScheduleOffset.setLatestDateOffset(latestDateOffset);
        return visitScheduleOffset;
    }

    private Visit createVisit(Long id, VisitType visitType, LocalDate date, LocalDate projectedDate, Subject subject) {
        Visit visit = new Visit();
        visit.setId(id);
        visit.setType(visitType);
        visit.setDate(date);
        visit.setMotechProjectedDate(projectedDate);
        visit.setSubject(subject);

        subject.getVisits().add(visit);

        return visit;
    }

    private void checkVisitRescheduleDtoList(List<VisitRescheduleDto> expectedDtos, List<VisitRescheduleDto> resultDtos) {
        for (int i = 0; i < expectedDtos.size(); i++) {
            assertEquals(expectedDtos.get(i).getEarliestDate(), resultDtos.get(i).getEarliestDate());
            assertEquals(expectedDtos.get(i).getLatestDate(), resultDtos.get(i).getLatestDate());
            assertEquals(expectedDtos.get(i).getParticipantId(), resultDtos.get(i).getParticipantId());
            assertEquals(expectedDtos.get(i).getIgnoreDateLimitation(), resultDtos.get(i).getIgnoreDateLimitation());
        }
    }
}
