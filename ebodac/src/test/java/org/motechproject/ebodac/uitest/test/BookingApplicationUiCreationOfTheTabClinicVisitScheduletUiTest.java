package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppClinicVisitSchedulePage;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppPage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BookingApplicationUiCreationOfTheTabClinicVisitScheduletUiTest extends EbodacTestBase {

    private BookingAppPage bookingAppPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        bookingAppPage = homePage.goToBookingAppModule();
    }

    @Test // EBODAC-710
    public void modifyAPrimeFollowUpVisitTest() throws InterruptedException {
        BookingAppClinicVisitSchedulePage bookingAppClinicVisitSchedulePage = bookingAppPage.openClinicVisitSchedule();
        String handle = bookingAppClinicVisitSchedulePage.getWindowHandle();

        bookingAppClinicVisitSchedulePage.findTestParticipant();
        assertFalse(bookingAppClinicVisitSchedulePage.checkIfPrimeVacFirstFollowUpCalculated());

        bookingAppClinicVisitSchedulePage.clickOnPrimeVacDayDate();
        bookingAppClinicVisitSchedulePage.clickOnFirstDayInCalendar();
        assertTrue(bookingAppClinicVisitSchedulePage.checkIfPrimeVacFirstFollowUpCalculated());

        bookingAppClinicVisitSchedulePage.clickPrintButton();

        assertTrue(bookingAppClinicVisitSchedulePage.closePdfIfIsOpen(handle));
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
