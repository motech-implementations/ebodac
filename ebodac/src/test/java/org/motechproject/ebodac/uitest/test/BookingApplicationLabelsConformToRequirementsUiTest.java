package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppPage;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppPrimeVaccinationPage;
import org.motechproject.ebodac.uitest.page.HomePage;

import static org.junit.Assert.assertTrue;

public class BookingApplicationLabelsConformToRequirementsUiTest extends EbodacTestBase {
    private BookingAppPage bookingAppPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        bookingAppPage = homePage.goToBookingAppModule();
    }

    @Test //EBODAC-840
    public void labelsConformToRequirementsTest() throws InterruptedException {
        assertTrue(bookingAppPage.checkBookingAppModules());
        BookingAppPrimeVaccinationPage bookingAppPrimeVaccinationPage = bookingAppPage.openPrimeVaccination();
        assertTrue(bookingAppPrimeVaccinationPage.checkTableHeaders());
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
