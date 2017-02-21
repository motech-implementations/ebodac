package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.domain.enums.VisitType;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.DailyClinicVisitScheduleReportPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.ReportsPage;

public class RefactorCurrentReportControllerUiTest extends EbodacTestBase {

    private ReportsPage reportsPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        EbodacPage ebodacPage = homePage.goToEbodacModule();

        reportsPage = ebodacPage.gotoReports();
    }

    @Test //EBODAC-432
    public void numberOfTimesParticipantsListenedToEachMessageTest() throws InterruptedException {
        DailyClinicVisitScheduleReportPage dailyClinicVisitScheduleReportPage = reportsPage.goToDailyClinicVisitReportSchedule();
        dailyClinicVisitScheduleReportPage.findByParticipantNameAndCheckResult("test participant");
        dailyClinicVisitScheduleReportPage.findByVisitTypeAndCheckResult(VisitType.SCREENING);
        dailyClinicVisitScheduleReportPage.findByParticipantIdAndCheckResult("9990000004");
        dailyClinicVisitScheduleReportPage.findByParticipantAddressAndCheckResult("address");
        dailyClinicVisitScheduleReportPage.findByVisitPlannedDateRangeAndCheckResult("2116-04-13", "2117-02-21");
        dailyClinicVisitScheduleReportPage.findByVisitPlannedDateRangeAndTypeAndCheckResult("2116-04-13", "2117-02-21", VisitType.BOOST_VACCINATION_DAY);
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
