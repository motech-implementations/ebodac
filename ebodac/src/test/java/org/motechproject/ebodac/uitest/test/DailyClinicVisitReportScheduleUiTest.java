package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.ebodac.reports.DailyClinicVisitScheduleReportPage;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.ReportsPage;

import static org.junit.Assert.assertFalse;

public class DailyClinicVisitReportScheduleUiTest extends EbodacTestBase {

    private ReportsPage reportsPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        EbodacPage ebodacPage = homePage.goToEbodacModule();

        reportsPage = ebodacPage.gotoReports();
    }

    @Test //EBODAC-805
    public void dailyClinicVisitReportScheduleTest() throws InterruptedException {
        DailyClinicVisitScheduleReportPage dailyClinicVisitScheduleReportPage = reportsPage.goToDailyClinicVisitReportSchedule();
        assertFalse(dailyClinicVisitScheduleReportPage.isReportEmpty());
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
