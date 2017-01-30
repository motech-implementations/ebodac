package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.ebodac.statistics.SMSKPIsPage;
import org.motechproject.ebodac.uitest.page.ebodac.statistics.StatisticsPage;


public class GenerateSMSTableUiTest extends EbodacTestBase {

    private SMSKPIsPage smsKPIsPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        EbodacPage ebodacPage = homePage.goToEbodacModule();

        StatisticsPage statisticsPage = ebodacPage.goToStatistics();
        smsKPIsPage = statisticsPage.goToSMSKPIs();
    }

    @Test //EBODAC-1005
    public void generateSMSTableTest() throws InterruptedException {
        smsKPIsPage.showStatsFromLast30Days();
        smsKPIsPage.checkSMSColumns();
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
