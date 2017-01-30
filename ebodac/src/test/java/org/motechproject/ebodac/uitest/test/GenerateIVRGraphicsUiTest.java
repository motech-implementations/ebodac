package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.ebodac.statistics.IVRGraphsPage;
import org.motechproject.ebodac.uitest.page.ebodac.statistics.StatisticsPage;

import static org.junit.Assert.assertTrue;

public class GenerateIVRGraphicsUiTest extends EbodacTestBase {

    private IVRGraphsPage ivrGraphsPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        EbodacPage ebodacPage = homePage.goToEbodacModule();
        StatisticsPage statisticsPage = ebodacPage.goToStatistics();

        ivrGraphsPage = statisticsPage.goToIVRGraphs();
    }

    @Test //EBODAC-1004
    public void generateIVRGraphicsTest() throws InterruptedException {
        ivrGraphsPage.showStatsFromLastYear();
        assertTrue(ivrGraphsPage.checkGraphs());
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
