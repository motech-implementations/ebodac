package org.motechproject.ebodac.uitest.page.bookingapp;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BookingAppScreeningPage extends BookingAppPage {

    private static final String URL_PATH = "/home#/bookingApp/screening";

    private static final String FIRST_TEST_CLINIC_NAME = "Test Clinic 1";
    private static final String SECOND_TEST_CLINIC_NAME = "Test Clinic 2";
    private static final String LIMIT_EXCEEDED_TITLE = "Confirm scheduling Screening Visit";
    private static final String INNER_HTML = "innerHTML";
    private static final String NO_RECORDS_TO_VIEW = "No records to view";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static final String TITLE = "title";

    private static final By FIRST_TEST_CLINIC = By.xpath("//div[@id='select2-drop']/ul/li/div[contains(text(),'" + FIRST_TEST_CLINIC_NAME + "')]");
    private static final By SECOND_TEST_CLINIC = By.xpath("//div[@id='select2-drop']/ul/li/div[contains(text(),'" + SECOND_TEST_CLINIC_NAME + "')]");
    private static final By ADD_SCREENING_BUTTON = By.id("addScreeningBtn");
    private static final By DATE_FIELD = By.id("screeningDateInput");
    private static final By TODAY_BUTTON = By.xpath("//button[@data-handler='today']");
    private static final By DONE_BUTTON = By.xpath("//button[@data-handler='hide']");
    private static final By TIME_FIELD = By.id("startTimeInput");
    private static final By TIME_DONE = By.xpath("//button[@data-handler='hide']");
    private static final By CLINIC_LOCATION = By.id("s2id_clinicSelect");
    private static final By SAVE_BUTTON = By.id("saveBtn");
    private static final By POPUP_OK = By.id("popup_ok");
    private static final By BOOKING_STRING = By.id("addScreeningMessage");
    private static final By FILTER_BUTTON = By.id("lookupDialogButton");
    private static final By LOOKUP_INPUT_FIELD = By.xpath("//*[@id='lookup-dialog']/div[2]/div[2]/div/input");
    private static final By FIND_BUTTON = By.id("findBtn");
    private static final By FIND_BY_BOOKING_ID = By.linkText("Find By Booking Id");
    private static final By LOOKUP_SELECT = By.id("selectLookupBtn");
    private static final By TABLE_SCREENING_LIST = By.xpath("//*[@id='pager_left']/div");
    private static final By LOOKUP_CLEAR = By.id("lookupClearBtn");
    private static final By PRINT_CARD = By.id("printBtn");
    private static final By CLOSE_BUTTON = By.id("closeBtn");
    private static final By SCREENING_DATES_LIST = By.xpath("//table[@id='screenings']/tbody/tr/td[@aria-describedby='screenings_date']");
    private static final By EXPORT_BUTTON = By.id("exportBtn");
    private static final By CONFIRM_EXPORT = By.id("confExportBtn");
    private static final By FORMAT = By.xpath("//*[@id='exportFormat']/a");
    private static final By PDF = By.xpath("//*[@id='exportFormat']/ul/li[1]/a");
    private static final By XLS = By.xpath("//*[@id='exportFormat']/ul/li[2]/a");
    private static final By DATE_FILTER = By.id("dateFilterBtn");
    private static final By CONFIRM_TITLE = By.id("popup_title");
    private static final By POPUP_CANCEL = By.id("popup_cancel");
    private static final By CANCEL_BUTTON = By.id("cancelBtn");
    private static final By SCREENING_TABLE_LOADING = By.id("load_screenings");

    public BookingAppScreeningPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public String bookScreeningVisit() throws InterruptedException {
        clickWhenVisible(ADD_SCREENING_BUTTON);
        clickWhenVisible(DATE_FIELD);
        clickWhenVisible(TODAY_BUTTON);
        clickWhenVisible(DONE_BUTTON);
        setTextToFieldNoEnter(TIME_FIELD, DateTime.now().toString("HH:mm"));
        clickWhenVisible(TIME_DONE);
        clickWhenVisible(CLINIC_LOCATION);
        clickWhenVisible(FIRST_TEST_CLINIC);
        clickWhenVisible(SAVE_BUTTON);
        clickWhenVisible(POPUP_OK);

        String bookingString = findElement(BOOKING_STRING).getText();
        bookingString = bookingString.split("=")[1].replace(".", "").trim();

        clickWhenVisible(PRINT_CARD);
        List<String> tabs2 = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs2.get(1));
        ScreeningCardPage screeningCardPage = new ScreeningCardPage(getDriver());
        String screeningBookingId = screeningCardPage.getBookingId();
        assertEquals(screeningBookingId, bookingString);
        getDriver().close();
        getDriver().switchTo().window(tabs2.get(0));
        clickWhenVisible(CLOSE_BUTTON);

        return bookingString;
    }

    public boolean bookingIdExists(String id) throws InterruptedException {
        clickWhenClickable(FILTER_BUTTON);
        clickWhenClickable(LOOKUP_SELECT);
        clickWhenClickable(FIND_BY_BOOKING_ID);
        findElement(LOOKUP_INPUT_FIELD).sendKeys(id);
        clickWhenVisible(FIND_BUTTON);
        waitUntilScreeningTableLoadingIsGone();

        boolean exists = hasVisits();

        clickWhenClickable(FILTER_BUTTON);
        clickWhenVisible(LOOKUP_CLEAR);
        waitUntilScreeningTableLoadingIsGone();

        return exists;
    }

    public boolean hasVisits() {
        return !findElement(TABLE_SCREENING_LIST).getAttribute(INNER_HTML).contains(NO_RECORDS_TO_VIEW);
    }

    public void changeFilterTo(String filter) throws InterruptedException {
        clickWhenVisible(DATE_FILTER);
        clickWhenVisible(By.linkText(filter));
        waitUntilScreeningTableLoadingIsGone();
    }

    public boolean areVisitsInDateRange(LocalDate min, LocalDate max) {
        if (hasVisits()) {
            for (WebElement element : findElements(SCREENING_DATES_LIST)) {
                LocalDate date = LocalDate.parse(element.getAttribute(TITLE), DateTimeFormat.forPattern(YYYY_MM_DD));
                if (date.isAfter(max) || date.isBefore(min)) {
                    return false;
                }
            }
        }

        return true;
    }

    public void exportToPDF() throws InterruptedException {
        clickWhenVisible(EXPORT_BUTTON);
        clickWhenVisible(FORMAT);
        clickWhenVisible(PDF);
        clickWhenVisible(CONFIRM_EXPORT);
    }

    public void exportToXLS() throws InterruptedException {
        clickWhenVisible(EXPORT_BUTTON);
        clickWhenVisible(FORMAT);
        clickWhenVisible(XLS);
        clickWhenVisible(CONFIRM_EXPORT);
    }

    public void bookVisitForScreening() throws InterruptedException {
        clickWhenVisible(ADD_SCREENING_BUTTON);
        clickWhenVisible(DATE_FIELD);
        clickWhenVisible(TODAY_BUTTON);
        clickWhenVisible(DONE_BUTTON);
        setTextToFieldNoEnter(TIME_FIELD, DateTime.now().toString("HH:mm"));
        clickWhenVisible(TIME_DONE);
        clickWhenVisible(CLINIC_LOCATION);
        clickWhenVisible(SECOND_TEST_CLINIC);
        clickWhenVisible(SAVE_BUTTON);
        clickWhenVisible(POPUP_OK);
    }

    public boolean isVisitLimitExceeded() throws InterruptedException {
        String messageTitle = findElement(CONFIRM_TITLE).getText();
        clickWhenVisible(POPUP_CANCEL);

        return messageTitle.contains(LIMIT_EXCEEDED_TITLE);
    }

    public void clickOnButtonToCancelScheduleScreening() throws InterruptedException {
        clickWhenVisible(CANCEL_BUTTON);

    }

    private void waitUntilScreeningTableLoadingIsGone() {
        waitForElementToBeHidden(SCREENING_TABLE_LOADING);
    }
}
