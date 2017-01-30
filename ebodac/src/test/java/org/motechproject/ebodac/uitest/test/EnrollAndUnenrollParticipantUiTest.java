package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.ebodac.EnrollmentPage;
import org.motechproject.ebodac.uitest.page.HomePage;

import static org.junit.Assert.assertTrue;

public class EnrollAndUnenrollParticipantUiTest extends EbodacTestBase {

    private EbodacPage ebodacPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        ebodacPage = homePage.goToEbodacModule();
    }

    @Test // Test for EBODAC-524, EBODAC-525
    public void enrollAndUnenrollParticipantTest() throws InterruptedException {
        EnrollmentPage enrollmentPage = ebodacPage.goToEnrollment();

        enrollmentPage.findTestParticipant();
        assertTrue(enrollmentPage.enrollParticipant());
        assertTrue(enrollmentPage.unenrollParticipant());
    }

    @After
    public void tearDown() throws InterruptedException {
        // We close the page
        logout();
    }
}
