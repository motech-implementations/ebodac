package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.ebodac.statistics.SMSGraphsPage;
import org.motechproject.ebodac.uitest.page.ebodac.statistics.StatisticsPage;

import static org.junit.Assert.assertTrue;

public class GenerateSMSGraphicsUiTest extends EbodacTestBase {

    private SMSGraphsPage smsGraphsPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        EbodacPage ebodacPage = homePage.goToEbodacModule();

        StatisticsPage statisticsPage = ebodacPage.goToStatistics();
        smsGraphsPage = statisticsPage.goToSMSGraphs();
    }

    @Test //EBODAC-1006
    public void generateSMSGraphicsTest() throws InterruptedException {
        smsGraphsPage.showStatsFromLastYear();
        assertTrue(smsGraphsPage.checkGraphs());
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
