package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.FollowupsMissedClinicVisitsReportPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.ReportsPage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FollowedUpsMissedClinicVisitsReportUiTest extends EbodacTestBase {

    private ReportsPage reportsPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        EbodacPage ebodacPage = homePage.goToEbodacModule();

        reportsPage = ebodacPage.gotoReports();
    }

    @Test //EBODAC-807
    public void followupsMissedClinicVisitsReportTest() throws InterruptedException {
        FollowupsMissedClinicVisitsReportPage followupsMissedClinicVisitsReportPage = reportsPage.goToFollowUpsMissedClinicReport();
        assertTrue(followupsMissedClinicVisitsReportPage.existTable());
        assertFalse(followupsMissedClinicVisitsReportPage.isReportEmpty());
        followupsMissedClinicVisitsReportPage.checkColumns();
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
