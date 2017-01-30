package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.ebodac.reports.CallDetailRecordPage;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.ReportsPage;

import static org.junit.Assert.assertTrue;

public class IVRCallReportsTestUiTest extends EbodacTestBase {

    private ReportsPage reportsPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        EbodacPage ebodacPage = homePage.goToEbodacModule();
        reportsPage = ebodacPage.gotoReports();
    }

    @Test // EBODAC-811
    public void iVRCallReportsTestUiTest() throws InterruptedException {
        CallDetailRecordPage callDetailRecordPage = reportsPage.goToCallDetailRecord();
        assertTrue(callDetailRecordPage.checkIfIVRTableHistoryContainsRows());
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
