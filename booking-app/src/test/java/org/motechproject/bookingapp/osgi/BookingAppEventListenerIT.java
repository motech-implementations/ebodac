package org.motechproject.bookingapp.osgi;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.bookingapp.constants.BookingAppConstants;
import org.motechproject.bookingapp.domain.VisitBookingDetails;
import org.motechproject.bookingapp.repository.VisitBookingDetailsDataService;
import org.motechproject.ebodac.domain.Language;
import org.motechproject.ebodac.domain.Subject;
import org.motechproject.ebodac.domain.Visit;
import org.motechproject.ebodac.domain.VisitType;
import org.motechproject.ebodac.repository.SubjectDataService;
import org.motechproject.ebodac.repository.VisitDataService;
import org.motechproject.testing.osgi.BasePaxIT;
import org.motechproject.testing.osgi.container.MotechNativeTestContainerFactory;
import org.ops4j.pax.exam.ExamFactory;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerSuite;

import javax.inject.Inject;
import java.util.List;

import static junit.framework.Assert.assertEquals;


@RunWith(PaxExam.class)
@ExamReactorStrategy(PerSuite.class)
@ExamFactory(MotechNativeTestContainerFactory.class)
public class BookingAppEventListenerIT extends BasePaxIT{

    @Inject
    private SubjectDataService subjectDataService;

    @Inject
    private VisitDataService visitDataService;

    @Inject
    private VisitBookingDetailsDataService visitBookingDetailsDataService;

    private DateTimeFormatter formatter = DateTimeFormat.forPattern(BookingAppConstants.SIMPLE_DATE_FORMAT);

    @Before
    public void cleanBefore() {
        visitBookingDetailsDataService.deleteAll();
        visitDataService.deleteAll();
        subjectDataService.deleteAll();
    }

    @After
    public void cleanAfter() {
        visitBookingDetailsDataService.deleteAll();
        visitDataService.deleteAll();
        subjectDataService.deleteAll();
    }

    @Test
    public void shouldCreateVisitBookingDetails() {
        List<VisitBookingDetails> visitBookingDetailsList = visitBookingDetailsDataService.retrieveAll();
        assertEquals(0, visitBookingDetailsList.size());

        Subject firstSubject = new Subject("1000000161", "Michal", "Abacki", "Cabacki",
                "729402018364", "address", Language.English, "community", "B05-SL10001", "chiefdom", "section", "district");

        Subject secondSubject = new Subject("1000000162", "Rafal", "Dabacki", "Ebacki",
                "44443333222", "address", Language.Susu, "community", "B05-SL10001", "chiefdom", "section", "district");

        Visit firstVisit = createVisit(secondSubject, VisitType.SCREENING, LocalDate.parse("2014-10-17", formatter),
                LocalDate.parse("2014-10-18", formatter));

        Visit secondVisit = createVisit(firstSubject, VisitType.PRIME_VACCINATION_FOLLOW_UP_VISIT, LocalDate.parse("2014-10-19", formatter),
                LocalDate.parse("2014-10-20", formatter));

        subjectDataService.create(firstSubject);
        subjectDataService.create(secondSubject);
        visitDataService.create(firstVisit);
        visitDataService.create(secondVisit);

        visitBookingDetailsList = visitBookingDetailsDataService.retrieveAll();

        assertEquals(2, visitBookingDetailsList.size());
    }

    private Visit createVisit(Subject subject, VisitType visitType, LocalDate date, LocalDate projectedDate) {
        Visit visit = new Visit();
        visit.setSubject(subject);
        visit.setType(visitType);
        visit.setDate(date);
        visit.setDateProjected(projectedDate);
        visit.setMotechProjectedDate(projectedDate);
        return visit;
    }
}
