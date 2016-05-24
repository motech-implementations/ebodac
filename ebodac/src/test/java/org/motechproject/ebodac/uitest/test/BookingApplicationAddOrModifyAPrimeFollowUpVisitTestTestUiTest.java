package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.motech.page.LoginPage;
import org.motech.test.TestBase;
import org.motechproject.ebodac.uitest.page.BookingAppClinicVisitSchedulePage;
import org.motechproject.ebodac.uitest.page.BookingAppPage;
import org.motechproject.ebodac.uitest.page.HomePage;

import static org.junit.Assert.assertEquals;


public class BookingApplicationAddOrModifyAPrimeFollowUpVisitTestTestUiTest extends TestBase {

    private LoginPage loginPage;
    private HomePage homePage;
    private BookingAppPage bookingAppPage;
    private BookingAppClinicVisitSchedulePage bookingAppClinicVisitSchedulePage;
    private String user;
    private String password;

    @Before
    public void setUp() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        bookingAppPage = new BookingAppPage(driver);
        bookingAppClinicVisitSchedulePage = new BookingAppClinicVisitSchedulePage(driver);
        user = properties.getUserName();
        password = properties.getPassword();
        if (homePage.expectedUrlPath() != currentPage().urlPath()) {
            loginPage.login(user, password);
        }
    }
    @Ignore
    @Test//EBODAC-800
    public void bookingApplicationAddOrModifyAPrimeFollowUpVisitTestTestUiTest() throws InterruptedException {
        homePage.clickModules();
        homePage.openBookingAppModule();
        bookingAppPage.openClinicVisitSchedule();
        bookingAppClinicVisitSchedulePage.clickOnDropDownParticipantId();
        String dayBeforeClean = bookingAppClinicVisitSchedulePage.getPrimeVacDateInput();
        bookingAppClinicVisitSchedulePage.clickOnPrimeVacDayDate();
        bookingAppClinicVisitSchedulePage.clickOnFirstDayInCalendar();
        bookingAppClinicVisitSchedulePage.clickButtonCleanDate();
        assertEquals(dayBeforeClean, bookingAppClinicVisitSchedulePage.assertIfPrimeVacDayIsEmpty());

    }

    @After
    public void tearDown() throws Exception {
        loginPage.logOut();
    }
}
