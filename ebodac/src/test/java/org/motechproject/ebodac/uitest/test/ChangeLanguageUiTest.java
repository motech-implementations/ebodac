package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.domain.enums.Language;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.ebodac.ParticipantEditPage;
import org.motechproject.ebodac.uitest.page.ebodac.ParticipantPage;

import static org.junit.Assert.assertEquals;

public class ChangeLanguageUiTest extends EbodacTestBase {

    private ParticipantPage participantPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        EbodacPage ebodacPage = homePage.goToEbodacModule();
        participantPage = ebodacPage.goToParticipants();
    }

    /**
     * Method : changeLanguageTest Description: This method changes the Language
     * of the participant and test if it is different.
     *
     * @throws InterruptedException
     */
    @Test
    public void changeLanguageTest() throws InterruptedException {
        participantPage.findTestParticipant();
        // We access to the edit page of the participant
        ParticipantEditPage participantEditPage = participantPage.editFirstParticipant();

        // Change the language
        participantEditPage.changeLanguage(Language.English);
        participantEditPage.saveParticipant();

        participantPage.findTestParticipant();
        participantEditPage = participantPage.editFirstParticipant();
        assertEquals(Language.English, participantEditPage.getLanguage());

        participantEditPage.changeLanguage(Language.Temne);
        participantEditPage.saveParticipant();
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
