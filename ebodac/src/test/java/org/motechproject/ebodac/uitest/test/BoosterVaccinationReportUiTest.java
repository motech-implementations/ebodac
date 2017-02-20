package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.BoosterVaccinationReportPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.ReportsPage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoosterVaccinationReportUiTest extends EbodacTestBase {

    private ReportsPage reportsPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        EbodacPage ebodacPage = homePage.goToEbodacModule();

        reportsPage = ebodacPage.gotoReports();
    }

    @Test //EBODAC-804
    public void boosterVaccinationReportTest() throws InterruptedException {
        BoosterVaccinationReportPage boosterVaccinationReportPage = reportsPage.goToBoostVaccinationReport();
        assertTrue(boosterVaccinationReportPage.existTable());
        assertFalse(boosterVaccinationReportPage.isReportEmpty());
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
