package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppPage;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppRescheduleVisitPage;
import org.motechproject.ebodac.uitest.page.HomePage;

import static org.junit.Assert.assertTrue;

public class BookingApplicationRescheduleVisitUiTest extends EbodacTestBase {
    private BookingAppRescheduleVisitPage bookingAppRescheduleVisitPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        BookingAppPage bookingAppPage = homePage.goToBookingAppModule();
        bookingAppRescheduleVisitPage = bookingAppPage.openRescheduleVisit();
    }

    @Test //EBODAC-801
    public void rescheduleVisitTest() throws InterruptedException {
        String handle = bookingAppRescheduleVisitPage.getWindowHandle();
        bookingAppRescheduleVisitPage.chooseTestVisit();
        assertTrue(bookingAppRescheduleVisitPage.rescheduleVisit());
        bookingAppRescheduleVisitPage.printCard();
        assertTrue(bookingAppRescheduleVisitPage.closePdfIfIsOpen(handle));
        bookingAppRescheduleVisitPage.clickCose();
        bookingAppRescheduleVisitPage.chooseTestVisit();
        assertTrue(bookingAppRescheduleVisitPage.changeBackTheDate());
        bookingAppRescheduleVisitPage.clickCose();
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
