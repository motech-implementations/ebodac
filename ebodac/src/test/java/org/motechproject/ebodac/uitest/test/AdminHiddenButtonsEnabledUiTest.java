package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.ParticipantEditPage;
import org.motechproject.ebodac.uitest.page.ebodac.ParticipantPage;

import static org.junit.Assert.assertTrue;

public class AdminHiddenButtonsEnabledUiTest extends EbodacTestBase {

    private HomePage homePage;

    @Before
    public void setUp() {
        homePage = loginAsAdmin();
        homePage.resizePage();
    }

    @Test //EBODAC-474
    public void hiddenButtonsEnabledTest() throws InterruptedException {
        assertTrue(homePage.isEBODACModulePresent());
        assertTrue(homePage.isIVRModulePresent());
        assertTrue(homePage.isSMSModulePresent());
        EbodacPage ebodacPage = homePage.goToEbodacModule();

        ParticipantPage participantPage = ebodacPage.goToParticipants();

        ParticipantEditPage participantEditPage = participantPage.editFirstParticipant();
        assertTrue(participantEditPage.areButtonsHidden());
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
