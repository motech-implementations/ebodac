package org.motechproject.ebodac.uitest.test;

import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.HomePage;

import static org.junit.Assert.assertTrue;

public class MotechAccessToAllModuelsUiTest extends EbodacTestBase {
    private HomePage homePage;

    @Before
    public void setUp() {
        homePage = login();
    }

    @Test //EBODAC-526
    public void motechAccessToAllModulesUiTest() throws InterruptedException {
        assertTrue(homePage.isDataServicesModulePresent());
        assertTrue(homePage.isEBODACModulePresent());
        assertTrue(homePage.isEmailModulePresent());
        assertTrue(homePage.isMessageCampaignModulePresent());
        assertTrue(homePage.isIVRModulePresent());
        assertTrue(homePage.isSMSModulePresent());
        assertTrue(homePage.isSchedulerModulePresent());
        assertTrue(homePage.isTasksModulePresent());
    }

    public void tearDown() throws InterruptedException {
        logout();
    }
}
