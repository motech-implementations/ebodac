package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.helper.UITestHttpClientHelper;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.ebodac.ParticipantPage;
import org.motechproject.ebodac.uitest.page.ebodac.VisitPage;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class GenerateParticipantAndVisitsUiTest extends EbodacTestBase {

    private UITestHttpClientHelper httpClientHelper;
    private EbodacPage ebodacPage;

    private String user;
    private String password;

    @Before
    public void setUp() throws InterruptedException {
        // General params
        user = getTestProperties().getUserName();
        password = getTestProperties().getPassword();
        String url = getServerUrl();
        httpClientHelper = new UITestHttpClientHelper(url);

        // We start the pages.
        HomePage homePage = login(user, password);
        ebodacPage = homePage.goToEbodacModule();
    }

    @Test // Test for EBODAC-512
    public void setDataVisitsTest() throws IOException, InterruptedException {
        // We change the participant id
        String participantId = httpClientHelper.generateNewParticipantId();
        // We start the visits and the participants.
        assertTrue(httpClientHelper.addParticipantFromZetes(user, password, participantId));
        assertTrue(httpClientHelper.addVisitsFromRAVE(user, password, participantId));

        // We open the Ebodac page
        ParticipantPage participantPage = ebodacPage.goToParticipants();

        assertTrue(participantPage.findByParticipantIdAndCheckResults(participantId));
        // Go to Visit
        VisitPage visitPage = ebodacPage.goToVisit();
        assertTrue(visitPage.findByParticipantIdAndCheckResults(participantId));
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
