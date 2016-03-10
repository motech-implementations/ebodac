package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.motech.page.LoginPage;
import org.motech.test.TestBase;
import org.motechproject.ebodac.uitest.page.BookingAppAdvancedSettingsPage;
import org.motechproject.ebodac.uitest.page.BookingAppCapacityInfoPage;
import org.motechproject.ebodac.uitest.page.BookingAppPage;
import org.motechproject.ebodac.uitest.page.HomePage;


public class BookingApplicationAdvanceSettingsTestUiTest extends TestBase {

    private LoginPage loginPage;
    private HomePage homePage;
    private BookingAppPage bookingAppPage;
    private BookingAppCapacityInfoPage bookingAppCapacityInfoPage;
    private BookingAppAdvancedSettingsPage bookingAppAdvancedSettingsPage;
    private String user;
    private String password;

    @Before
    public void setUp() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        bookingAppPage = new BookingAppPage(driver);
        bookingAppCapacityInfoPage = new BookingAppCapacityInfoPage(driver);
        bookingAppAdvancedSettingsPage = new BookingAppAdvancedSettingsPage(driver);
        user = properties.getUserName();
        password = properties.getPassword();
        if (homePage.expectedUrlPath() != currentPage().urlPath()) {
            loginPage.login(user, password);
        }
    }

    @Ignore
    @Test//EBODAC-802
    public void bookingApplicationCapacityInfoTest() throws InterruptedException {
        homePage.clickModules();
        homePage.openBookingAppModule();
        bookingAppPage.openAdvancedSettings();
        bookingAppAdvancedSettingsPage.clickKambiaIRowAndShowMore();
        bookingAppAdvancedSettingsPage.removeTextFromInputMaxPrimeVisitsPasteOtherValue();
        bookingAppAdvancedSettingsPage.clickSaveAfterEditKambiaI();


    }

    @After
    public void tearDown() throws Exception {
        loginPage.logOut();
    }
}
