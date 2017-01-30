package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.ebodac.EnrollmentPage;

import static org.junit.Assert.assertEquals;

public class EnrollAndUnenrollParicipantByAnalystUiTest extends EbodacTestBase {

    private static final String POPUP_UNENROLLED_SUCCESSFULLY = "Participant was unenrolled successfully.";
    private static final String POPUP_ENROLLED_SUCCESSFULLY = "Participant was enrolled successfully.";
    private static final String POPUP_UNENROLL_THE_PARTICIPANT = "Are you sure you want to un-enroll the participant?";
    private static final String POPUP_ENROLL_THE_PARTICIPANT = "Are you sure you want to enroll the participant?";

    private EnrollmentPage enrollmentPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = loginAsAnalyst();
        homePage.resizePage();

        EbodacPage ebodacPage = homePage.goToEbodacModule();
        enrollmentPage = ebodacPage.goToEnrollment();
    }

    @Test //EBODAC-476
    public void shouldBlockAllActionsDuringEnrolment() throws InterruptedException {
        enrollmentPage.findTestParticipant();

        enrollmentPage.clickEnroll();

        //Modal withe the confirmation question should be shown
        assertEquals(POPUP_ENROLL_THE_PARTICIPANT, enrollmentPage.getPopupMessage());
        enrollmentPage.clickOK();

        //Success modal should be shown
        assertEquals(POPUP_ENROLLED_SUCCESSFULLY, enrollmentPage.getPopupMessage());
        enrollmentPage.clickOK();

        enrollmentPage.clickUnenroll();

        //Modal withe the confirmation question should be shown
        assertEquals(POPUP_UNENROLL_THE_PARTICIPANT, enrollmentPage.getPopupMessage());
        enrollmentPage.clickOK();

        //Success modal should be shown
        assertEquals(POPUP_UNENROLLED_SUCCESSFULLY, enrollmentPage.getPopupMessage());
        enrollmentPage.clickOK();
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
