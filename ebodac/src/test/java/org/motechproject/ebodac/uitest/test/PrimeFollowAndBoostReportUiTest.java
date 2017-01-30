package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.PrimeFollowAndBoostReportPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.ReportsPage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PrimeFollowAndBoostReportUiTest extends EbodacTestBase {

    private PrimeFollowAndBoostReportPage primeFollowAndBoostReportPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        EbodacPage ebodacPage = homePage.goToEbodacModule();
        ReportsPage reportsPage = ebodacPage.gotoReports();

        primeFollowAndBoostReportPage = reportsPage.goToPrimeFollowAndBoostReport();
    }

    @Test //EBODAC-953
    public void primeFollowAndBoostReportTest() throws InterruptedException {
        assertFalse(primeFollowAndBoostReportPage.isReportEmpty());

        primeFollowAndBoostReportPage.openLookup();
        primeFollowAndBoostReportPage.openDropdown();
        assertTrue(primeFollowAndBoostReportPage.areLookupsPresent());
        primeFollowAndBoostReportPage.openByVisitTypeAndActualVisitDateLookup();
        assertTrue(primeFollowAndBoostReportPage.isLookupOpen());
        primeFollowAndBoostReportPage.openVisitType();
        assertTrue(primeFollowAndBoostReportPage.areAllVisitsAvailable());
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
