package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppPage;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppScreeningPage;

import static org.junit.Assert.assertTrue;

public class BookingApplicationBindRoomMaxScreeningVisitValueUiTest extends EbodacTestBase {

    private BookingAppPage bookingAppPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        bookingAppPage = homePage.goToBookingAppModule();
    }

    @Test // EBODAC-718
    public void bindRoomMaxScreeningVisitValue() throws InterruptedException {
        BookingAppScreeningPage bookingAppScreeningPage = bookingAppPage.openScreening();

        bookingAppScreeningPage.bookVisitForScreening();
        assertTrue(bookingAppScreeningPage.isVisitLimitExceeded());
        bookingAppScreeningPage.clickOnButtonToCancelScheduleScreening();
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
