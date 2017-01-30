package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.statistics.IVRKPIsPage;
import org.motechproject.ebodac.uitest.page.ebodac.statistics.StatisticsPage;


public class GenerateIVRTableUiTest extends EbodacTestBase {

    private IVRKPIsPage ivrKPIsPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        EbodacPage ebodacPage = homePage.goToEbodacModule();

        StatisticsPage statisticsPage = ebodacPage.goToStatistics();
        ivrKPIsPage = statisticsPage.goToIVRKPIs();
    }

    @Test //EBODAC-1000
    public void generateIVRTableTest() throws InterruptedException {
        ivrKPIsPage.showStatsFromLast30Days();
        ivrKPIsPage.checkIVRColumns();
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
