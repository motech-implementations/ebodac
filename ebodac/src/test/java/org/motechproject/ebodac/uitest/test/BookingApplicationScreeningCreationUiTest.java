package org.motechproject.ebodac.uitest.test;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppPage;
import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppScreeningPage;

import static org.junit.Assert.assertTrue;

public class BookingApplicationScreeningCreationUiTest extends EbodacTestBase {

    private static final String TODAY = "Today";
    private static final String TOMORROW = "Tomorrow";
    private static final String DAY_AFTER_TOMORROW = "Day after tomorrow";
    private static final String NEXT_3_DAYS = "Next 3 days";
    private static final String NEXT_7_DAYS = "Next 7 days";
    private static final String DATE_RANGE = "Date range";

    private static final LocalDate TODAY_DATE = LocalDate.now();
    private static final LocalDate TOMORROW_DATE = LocalDate.now().plusDays(1);
    private static final LocalDate DAY_AFTER_TOMORROW_DATE = LocalDate.now().plusDays(2);
    private static final LocalDate NEXT_3_DAYS_DATE = LocalDate.now().plusDays(2);
    private static final LocalDate NEXT_7_DAYS_DATE = LocalDate.now().plusDays(6);

    private BookingAppScreeningPage bookingAppScreeningPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        BookingAppPage bookingAppPage = homePage.goToBookingAppModule();
        bookingAppScreeningPage = bookingAppPage.openScreening();
    }

    @Test //EBODAC-704
    public void bAScreeningVisitCreationTest() throws InterruptedException {
        String bookingId = bookingAppScreeningPage.bookScreeningVisit();
        bookingAppScreeningPage.changeFilterTo(DATE_RANGE);
        assertTrue(bookingAppScreeningPage.bookingIdExists(bookingId));

        bookingAppScreeningPage.changeFilterTo(TODAY);
        assertTrue(bookingAppScreeningPage.areVisitsInDateRange(TODAY_DATE, TODAY_DATE));
        bookingAppScreeningPage.changeFilterTo(TOMORROW);
        assertTrue(bookingAppScreeningPage.areVisitsInDateRange(TOMORROW_DATE, TOMORROW_DATE));
        bookingAppScreeningPage.changeFilterTo(DAY_AFTER_TOMORROW);
        assertTrue(bookingAppScreeningPage.areVisitsInDateRange(DAY_AFTER_TOMORROW_DATE, DAY_AFTER_TOMORROW_DATE));
        bookingAppScreeningPage.changeFilterTo(NEXT_3_DAYS);
        assertTrue(bookingAppScreeningPage.areVisitsInDateRange(TODAY_DATE, NEXT_3_DAYS_DATE));
        bookingAppScreeningPage.changeFilterTo(NEXT_7_DAYS);
        assertTrue(bookingAppScreeningPage.areVisitsInDateRange(TODAY_DATE, NEXT_7_DAYS_DATE));

        bookingAppScreeningPage.exportToPDF();
        bookingAppScreeningPage.exportToXLS();
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
