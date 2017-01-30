package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.MEMissedClinicVisitsReportPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.ReportsPage;

import static org.junit.Assert.assertTrue;

public class MEMissedClinicVisitsReportUiTest extends EbodacTestBase {

    private ReportsPage reportsPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        EbodacPage ebodacPage = homePage.goToEbodacModule();
        reportsPage = ebodacPage.gotoReports();
    }

    @Test //EBODAC-809
    public void mEMissedClinicVisitsReportTest() throws InterruptedException {
        MEMissedClinicVisitsReportPage mEMissedClinicVisitsReportPage = reportsPage.goToMEMissedClinicVisitsReport();
        assertTrue(mEMissedClinicVisitsReportPage.existTable());
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
