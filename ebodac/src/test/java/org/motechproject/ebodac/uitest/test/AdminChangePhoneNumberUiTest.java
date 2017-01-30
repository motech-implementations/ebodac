package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.ParticipantEditPage;
import org.motechproject.ebodac.uitest.page.ebodac.ParticipantPage;

import static org.junit.Assert.assertEquals;

public class AdminChangePhoneNumberUiTest extends EbodacTestBase {
    private ParticipantPage participantPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = loginAsAdmin();
        homePage.resizePage();

        EbodacPage ebodacPage = homePage.goToEbodacModule();
        participantPage = ebodacPage.goToParticipants();
    }

    @Test // Test for EBODAC-508/EBODAC-509
    public void changePhoneNumberTest() throws InterruptedException {
        // Open the participant
        participantPage.findTestParticipant();
        String oldNumber = participantPage.getPhoneNumber();
        ParticipantEditPage participantEditPage = participantPage.editFirstParticipant();

        // Set the phone number.
        participantEditPage.changePhoneNumber("232000000117");
        participantEditPage.saveParticipant();

        participantPage.findTestParticipant();
        String number = participantPage.getPhoneNumber();
        participantEditPage = participantPage.editFirstParticipant();
        assertEquals("232000000117", number);

        participantEditPage.changePhoneNumber(oldNumber);
        participantEditPage.saveParticipant();
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
