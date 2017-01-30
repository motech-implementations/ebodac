package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppAdvancedSettingsPage;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppPage;

import static org.junit.Assert.assertEquals;

public class BookingApplicationAdvanceSettingsTestUiTest extends EbodacTestBase {

    private BookingAppPage bookingAppPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        bookingAppPage = homePage.goToBookingAppModule();
    }

    @Test // EBODAC-802
    public void bookingAppAdvanceSettingsTest() throws InterruptedException {
        BookingAppAdvancedSettingsPage bookingAppAdvancedSettingsPage = bookingAppPage.openAdvancedSettings();

        int amountOfRoomsOld = bookingAppAdvancedSettingsPage.getAmountOfRooms();
        int amountOfRoomsNew = amountOfRoomsOld + 2;

        bookingAppAdvancedSettingsPage.editTestClinic();
        bookingAppAdvancedSettingsPage.clickShowMore();
        bookingAppAdvancedSettingsPage.setAmountOfRooms(amountOfRoomsNew);
        bookingAppAdvancedSettingsPage.saveTestClinic();

        assertEquals(amountOfRoomsNew, bookingAppAdvancedSettingsPage.getAmountOfRooms());

        bookingAppAdvancedSettingsPage.editTestClinic();
        bookingAppAdvancedSettingsPage.clickShowMore();
        bookingAppAdvancedSettingsPage.setAmountOfRooms(amountOfRoomsOld);
        bookingAppAdvancedSettingsPage.saveTestClinic();
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
