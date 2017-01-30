package org.motechproject.ebodac.uitest.test;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.DataServicesPage;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppPage;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppPrimeVaccinationPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BookingApplicationModifyaPrimeFollowUpVisitUiTest extends EbodacTestBase {

    private BookingAppPage bookingAppPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        bookingAppPage = homePage.goToBookingAppModule();
    }

    @Test // EBODAC-798
    public void modifyAPrimeFollowUpVisit() throws InterruptedException {
        BookingAppPrimeVaccinationPage bookingAppPrimeVaccinationPage = bookingAppPage.openPrimeVaccination();
        String handle = bookingAppPrimeVaccinationPage.getWindowHandle();
        bookingAppPrimeVaccinationPage.changeDateFilterToDateRange();
        bookingAppPrimeVaccinationPage.filterByParticipantId();
        bookingAppPrimeVaccinationPage.clickOnFirstRowInTheGrid();
        bookingAppPrimeVaccinationPage.selectIgnoreLatestEarliestDate();
        bookingAppPrimeVaccinationPage.setPrimeVacDate();
        bookingAppPrimeVaccinationPage.setPrimeVacTime();
        bookingAppPrimeVaccinationPage.saveAndConfirmChanges();
        bookingAppPrimeVaccinationPage.clickPrintCard();
        assertTrue(bookingAppPrimeVaccinationPage.closePdfIfIsOpen(handle));
        bookingAppPrimeVaccinationPage.closeSuccessModal();

        bookingAppPrimeVaccinationPage.changeDateFilterToDateRange();
        bookingAppPrimeVaccinationPage.filterByParticipantId();
        assertEquals(LocalDate.now().toString("yyyy-MM-dd"), bookingAppPrimeVaccinationPage.firstParticipantPrimeVacDay());
    }

    @After
    public void tearDown() throws InterruptedException {
        DataServicesPage dataServicesPage = bookingAppPage.goToDataServicesModule();
        dataServicesPage.resetSecondTestVisitBookingDetails();
        logout();
    }
}
