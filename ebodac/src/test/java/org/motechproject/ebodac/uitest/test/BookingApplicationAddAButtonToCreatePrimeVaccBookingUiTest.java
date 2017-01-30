package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.DataServicesPage;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppPage;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppPrimeVaccinationPage;
import org.motechproject.ebodac.uitest.page.HomePage;

import static org.junit.Assert.assertTrue;

public class BookingApplicationAddAButtonToCreatePrimeVaccBookingUiTest extends EbodacTestBase {

    private BookingAppPrimeVaccinationPage bookingAppPrimeVaccinationPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        BookingAppPage bookingAppPage = homePage.goToBookingAppModule();

        bookingAppPrimeVaccinationPage = bookingAppPage.openPrimeVaccination();
    }

    @Test // EBODAC-781
    public void addAButtonToCreatePrimeVacBookingTest() throws InterruptedException {
        String handle = bookingAppPrimeVaccinationPage.getWindowHandle();
        bookingAppPrimeVaccinationPage.clickAddPrimeVaccinationButton();
        assertTrue(bookingAppPrimeVaccinationPage.selectTestParticipant());
        // After setting the participant id the rest should run step by step.
        // We set the screening date
        bookingAppPrimeVaccinationPage.setScreeningDate();
        // Set the female Child Bearing age
        bookingAppPrimeVaccinationPage.setFemaleChildBearingAge();

        // We click this option to make sure we can select dates for the prime.vaccination
        bookingAppPrimeVaccinationPage.selectIgnoreLatestEarliestDate();

        bookingAppPrimeVaccinationPage.setPrimeVacDate();
        bookingAppPrimeVaccinationPage.setPrimeVacTime();

        bookingAppPrimeVaccinationPage.saveAndConfirmChanges();
        bookingAppPrimeVaccinationPage.clickPrintCard();
        assertTrue(bookingAppPrimeVaccinationPage.closePdfIfIsOpen(handle));
        bookingAppPrimeVaccinationPage.closeSuccessModal();
    }

    @After
    public void tearDown() throws InterruptedException {
        DataServicesPage dataServicesPage = bookingAppPrimeVaccinationPage.goToDataServicesModule();
        dataServicesPage.resetFirstTestVisitBookingDetails();
        logout();
    }
}
