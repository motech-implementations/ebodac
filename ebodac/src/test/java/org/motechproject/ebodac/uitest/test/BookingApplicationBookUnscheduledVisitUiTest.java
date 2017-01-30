package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppPage;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppUnscheduledVisitPage;
import org.motechproject.ebodac.uitest.page.HomePage;

import static org.junit.Assert.assertEquals;

public class BookingApplicationBookUnscheduledVisitUiTest extends EbodacTestBase {

    private static final String CONFIRM_MESSAGE = "Are you sure you want to book Unscheduled visit?";
    private static final String CORRECTLY_UPDATED_MESSAGE = "Visit Booking Details updated successfully.";

    private BookingAppPage bookingAppPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        bookingAppPage = homePage.goToBookingAppModule();
    }

    @Test //EBODAC-934
    public void bookingApplicationRescheduleVisitTest() throws InterruptedException {
        BookingAppUnscheduledVisitPage unscheduledVisitPage = bookingAppPage.openUnscheduledVisit();

        unscheduledVisitPage.clickOnBookUnscheduledVisitButton();
        unscheduledVisitPage.clickOnParticipantIdDropDownAndChooseTestParticipant();
        unscheduledVisitPage.setDatesForUnscheduledVisit();
        unscheduledVisitPage.clickOnButtonToSaveUnscheduledVisit();
        assertEquals(CONFIRM_MESSAGE, unscheduledVisitPage.checkIfConfirmModalIsVisible());
        unscheduledVisitPage.clickOnButtonToConfirmBookUnscheduledVisit();
        assertEquals(CORRECTLY_UPDATED_MESSAGE, unscheduledVisitPage.checkIfVisitIsCorrectlySaved());
        unscheduledVisitPage.clickOnButtonToCloseModal();
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
