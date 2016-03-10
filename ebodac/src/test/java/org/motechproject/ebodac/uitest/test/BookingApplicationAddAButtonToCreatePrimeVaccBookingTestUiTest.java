package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.motech.page.LoginPage;
import org.motech.test.TestBase;
import org.motechproject.ebodac.uitest.page.BookingAppPage;
import org.motechproject.ebodac.uitest.page.BookingAppPrimeVaccinationPage;
import org.motechproject.ebodac.uitest.page.HomePage;

import static org.junit.Assert.assertEquals;


public class BookingApplicationAddAButtonToCreatePrimeVaccBookingTestUiTest extends TestBase {

    private LoginPage loginPage;
    private HomePage homePage;
    private BookingAppPage bookingAppPage;

    private BookingAppPrimeVaccinationPage bookingAppPrimeVaccinationPage;
    private String user;
    private String password;

    @Before
    public void setUp() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        bookingAppPage = new BookingAppPage(driver);
        bookingAppPrimeVaccinationPage = new BookingAppPrimeVaccinationPage(driver);
        user = properties.getUserName();
        password = properties.getPassword();
        if (homePage.expectedUrlPath() != currentPage().urlPath()) {
            loginPage.login(user, password);
        }
    }

    @Test //EBODAC-781
    public void bookingApplicationCapacityInfoTest() throws InterruptedException {
        homePage.clickModules();
        homePage.openBookingAppModule();
        bookingAppPage.openPrimeVaccination();
        assertEquals(true, bookingAppPrimeVaccinationPage.checkIfElementAddPrimeVaccinationIsVisible());
        bookingAppPrimeVaccinationPage.clickAddPrimeVaccinationButton();
        bookingAppPrimeVaccinationPage.clickFirstParticipantId();
        bookingAppPrimeVaccinationPage.setDateOfScreeningDate();
        bookingAppPrimeVaccinationPage.clickOnIngoreLatesEarliestDate();
        bookingAppPrimeVaccinationPage.setFemaleChildBearingAge();
        bookingAppPrimeVaccinationPage.setDateOfPrimeVacDateFields();
        bookingAppPrimeVaccinationPage.setTimeOfPrimeVacDateFields();
        bookingAppPrimeVaccinationPage.saveCreatedPrimeVaccination();
        bookingAppPrimeVaccinationPage.confirmAddVisitBookingDetailsAndPrintCard();
    }

    @After
    public void tearDown() throws Exception {
        loginPage.logOut();
    }
}
