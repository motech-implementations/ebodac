package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.ebodac.EnrollmentPage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AdminShouldNotSeeAdvancePageUiTest extends EbodacTestBase {

    private EnrollmentPage enrollmentPage;

    @Before
    public void setUp() throws InterruptedException {
        //We try to log in Ebodac.
        HomePage homePage = loginAsAdmin();
        homePage.resizePage();

        EbodacPage ebodacPage = homePage.goToEbodacModule();
        enrollmentPage = ebodacPage.goToEnrollment();
    }

    @Test //EBODAC-440
    public void adminShouldNotSeeAdvancePageTest() throws InterruptedException {
        // It should be allowed to enrol unenroll participants.
        // We try to enroll.
        enrollmentPage.findTestParticipant();
        assertTrue(enrollmentPage.enrollParticipant());
        assertTrue(enrollmentPage.unenrollParticipant());

        enrollmentPage.selectFirstEnrollment();
        // We should not be able to see the advance page for enrollment.
        assertFalse(enrollmentPage.isOnEnrollmentAdvancedScreen());
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
