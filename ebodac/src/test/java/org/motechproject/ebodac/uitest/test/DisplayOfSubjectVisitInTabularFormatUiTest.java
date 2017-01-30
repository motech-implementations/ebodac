package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.ParticipantEditPage;
import org.motechproject.ebodac.uitest.page.ebodac.ParticipantPage;

import static org.junit.Assert.assertTrue;

public class DisplayOfSubjectVisitInTabularFormatUiTest extends EbodacTestBase {

    private ParticipantPage participantPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        EbodacPage ebodacPage = homePage.goToEbodacModule();
        participantPage = ebodacPage.goToParticipants();
    }

    @Test //EBODAC-472
    public void displayOfSubjectVisitInTabularFormatTest() throws InterruptedException {
        ParticipantEditPage participantEditPage = participantPage.editFirstParticipant();
        assertTrue(participantEditPage.isVisitInTabularFormat());
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
