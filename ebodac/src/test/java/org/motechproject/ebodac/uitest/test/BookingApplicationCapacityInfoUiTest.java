package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppAdvancedSettingsPage;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppCapacityInfoPage;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppPage;
import org.motechproject.ebodac.uitest.page.HomePage;

public class BookingApplicationCapacityInfoUiTest extends EbodacTestBase {

    private BookingAppPage bookingAppPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        bookingAppPage = homePage.goToBookingAppModule();
    }

    @Test
    public void bookingApplicationCapacityInfoTest() throws InterruptedException {
        BookingAppAdvancedSettingsPage bookingAppAdvancedSettingsPage = bookingAppPage.openAdvancedSettings();
        int maxCapacity = bookingAppAdvancedSettingsPage.getMaxCapacity();

        BookingAppCapacityInfoPage bookingAppCapacityInfoPage = bookingAppPage.openCapacityInfo();
        bookingAppCapacityInfoPage.filterToday();
        Assert.assertEquals(bookingAppCapacityInfoPage.getMaxCapacity(), maxCapacity);
        bookingAppCapacityInfoPage.filterTomorrow();
        Assert.assertEquals(bookingAppCapacityInfoPage.getMaxCapacity(), maxCapacity);
        bookingAppCapacityInfoPage.filterDayAfterTomorrow();
        Assert.assertEquals(bookingAppCapacityInfoPage.getMaxCapacity(), maxCapacity);
        bookingAppCapacityInfoPage.filterNext3Days();
        Assert.assertEquals(bookingAppCapacityInfoPage.getMaxCapacity(), 3 * maxCapacity);
        bookingAppCapacityInfoPage.filterNext7Days();
        Assert.assertEquals(bookingAppCapacityInfoPage.getMaxCapacity(), 7 * maxCapacity);
        bookingAppCapacityInfoPage.filterDateRange();
        Assert.assertEquals(bookingAppCapacityInfoPage.getMaxCapacity(), 28 * maxCapacity);
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
