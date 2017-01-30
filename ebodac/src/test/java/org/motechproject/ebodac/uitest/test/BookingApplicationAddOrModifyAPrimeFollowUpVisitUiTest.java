package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppClinicVisitSchedulePage;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppPage;
import org.motechproject.ebodac.uitest.page.HomePage;

import static org.junit.Assert.assertEquals;

public class BookingApplicationAddOrModifyAPrimeFollowUpVisitUiTest extends EbodacTestBase {

    private BookingAppPage bookingAppPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        bookingAppPage = homePage.goToBookingAppModule();
    }

    @Test // EBODAC-800
    public void bookingAppAddOrModifyUiTest() throws InterruptedException {
        BookingAppClinicVisitSchedulePage bookingAppClinicVisitSchedulePage = bookingAppPage.openClinicVisitSchedule();

        bookingAppClinicVisitSchedulePage.findTestParticipant();
        String dayBeforeClean = bookingAppClinicVisitSchedulePage.getPrimeVacDate();
        bookingAppClinicVisitSchedulePage.clickOnPrimeVacDayDate();
        bookingAppClinicVisitSchedulePage.clickOnFirstDayInCalendar();
        bookingAppClinicVisitSchedulePage.clickButtonCleanDate();

        // Assert to validate the changes.
        assertEquals(dayBeforeClean, bookingAppClinicVisitSchedulePage.getPrimeVacDate());
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
