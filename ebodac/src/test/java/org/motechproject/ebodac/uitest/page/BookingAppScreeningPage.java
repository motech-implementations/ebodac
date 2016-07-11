package org.motechproject.ebodac.uitest.page;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.motechproject.uitest.page.AbstractBasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;


public class BookingAppScreeningPage extends AbstractBasePage {

    public BookingAppScreeningPage(WebDriver driver) {
        super(driver);
    }

    public static final String URL_PATH = "/#/bookingApp/screening";

    static final int TEXTPOINT = 52;
    static final int TIMEOUT = 1000;
    static final int MAX_PAGES = 10;
    static final By SCREENING_BUTTON = By.xpath("//div[@id='main-content']/div/div/div/button");
    static final By SCREENING_BUTTON_NG_CLICK = By.xpath("//button[@ng-click='addScreening()']");
    static final By DATE_FIELD = By.xpath("//div[@class='modal-body']/div/input[@type='text']");
    static final By DAY = By.linkText("13");
    static final By TIME_FIELD = By.xpath("//input[@mds-time-picker='']");
    static final By TIME_DONE = By.xpath("//button[@data-handler='hide']");
    static final By CLINIC_LOCATION = By.xpath("//div[@class='booking-app input-group'][3]");
    static final By CLINIC = By.xpath("(//div[@class='select2-result-label'])[2]");
    static final By SAVE_BUTTON = By.xpath("//button[@ng-click='saveScreening(false)']");
    static final By POPUP_OK = By.id("popup_ok");
    static final By PRINT_CARD = By.xpath("//button[@ng-click='printRow(-1)']");
    static final By CLOSE_BUTTON = By.xpath("//button[@data-dismiss='modal']");
    static final By CLOSE_BUTTON_TEXT = By.xpath("//button[contains(text(),'Close')]");
    static final By EXPORT_BUTTON = By.xpath("(//button[@type='button'])[5]");
    static final By CONFIRM_EXPORT = By.xpath("//div[@id='exportBookingAppInstanceModal']/div[2]/div/div[3]/button");
    static final By FILTER = By.xpath("//div[@class='btn-group']/button[@data-toggle='dropdown']");
    static final By BOOKING_STRING = By.xpath("//div[@id='screeningModal']/div[2]/div/div[2]/div");
    static final By FIRST_VISIT = By.xpath("//table[@id='screenings']/tbody/tr[2]/td[3]");
    static final By FORMAT = By.linkText("PDF");
    static final By XLS = By.xpath("(//a[contains(text(),'XLS')])[2]");
    static final By START_DATE = By.xpath("//input[@ng-model='selectedFilter.startDate']");
    static final By END_DATE = By.xpath("//input[@ng-model='selectedFilter.endDate']");
    static final By FIRST_DAY = By.linkText("1");
    static final By LAST_DAY = By.linkText("28");
    static final By NEXT_PAGE = By.xpath("//td[@id='next_pager']/span");
    static final By DATE_FIELD_INPUT = By.xpath("//input[@ng-model='form.dto.date']");
    static final By TODAY_BUTTON = By.xpath("//button[@data-handler='today']");
    static final By DONE_BUTTON = By.xpath("//button[@data-handler='hide']");
    static final By START_TIME_PICKER = By.xpath("//input[@ng-model='form.dto.startTime']");
    static final By NOW_BUTTON = By.xpath("//button[@data-handler='today']");
    static final By CLINIC_LOCATION_DROP_DOWN = By.xpath("//span[contains(text(), '- Please Choose -')]");
    static final By CLINIC_LOCACATION_KAMBIA_I = By.xpath("//div[contains(text(), 'Kambia I')]");
    static final By SAVE_BUTTON_NG_CLICK = By.xpath("//button[@ng-click='saveScreening(false)']");
    static final By CONFIRM_BUTTON = By.id("popup_ok");
    static final int SLEEP_4000 = 4000;
    static final int SLEEP_1000 = 1000;
    static final int SLEEP_500 = 500;

    private ScreeningCardPage screeningCardPage;

    public String bookScreeningVisit() throws InterruptedException {
        waitForElement(SCREENING_BUTTON);
        clickOn(SCREENING_BUTTON);
        clickWhenVisible(SCREENING_BUTTON);
        clickWhenVisible(DATE_FIELD);
        clickWhenVisible(DAY);
        setTextToFieldNoEnter(TIME_FIELD, "12:00");
        clickWhenVisible(TIME_DONE);
        waitForElement(CLINIC_LOCATION);
        sleep(TIMEOUT);
        clickWhenVisible(CLINIC_LOCATION);
        clickWhenVisible(CLINIC);
        waitForElement(SAVE_BUTTON);
        clickWhenVisible(SAVE_BUTTON);
        waitForElement(POPUP_OK);
        clickWhenVisible(POPUP_OK);
        sleep(TIMEOUT);
        clickWhenVisible(POPUP_OK);
        sleep(TIMEOUT);
        waitForElement(BOOKING_STRING);
        String bookingString = findElement(BOOKING_STRING).getText().substring(TEXTPOINT).replace("." , "").replace(" " , "");
        waitForElement(PRINT_CARD);
        clickWhenVisible(PRINT_CARD);
        sleep(TIMEOUT);
        ArrayList<String> tabs2 = new ArrayList<String>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs2.get(1));
        screeningCardPage = new ScreeningCardPage(getDriver());
        String screeningBookingId = screeningCardPage.getBookingId();
        assertEquals(screeningBookingId, bookingString);
        getDriver().close();
        getDriver().switchTo().window(tabs2.get(0));
        sleep(TIMEOUT);
        waitForElement(CLOSE_BUTTON);
        clickOn(CLOSE_BUTTON);
        clickWhenVisible(CLOSE_BUTTON);
        return bookingString;
    }

    public void bookVisitForScreening() throws InterruptedException {
        sleep(SLEEP_1000);
        clickWhenVisible(SCREENING_BUTTON_NG_CLICK);
        clickWhenVisible(DATE_FIELD_INPUT);
        clickWhenVisible(TODAY_BUTTON);
        clickWhenVisible(DONE_BUTTON);
        clickWhenVisible(START_TIME_PICKER);
        clickWhenVisible(NOW_BUTTON);
        clickWhenVisible(DONE_BUTTON);
        clickWhenVisible(CLINIC_LOCATION_DROP_DOWN);
        sleep(SLEEP_1000);
        clickWhenVisible(CLINIC_LOCACATION_KAMBIA_I);
        clickWhenVisible(SAVE_BUTTON_NG_CLICK);
    }

    public void confirmBookVistiForScreening() throws InterruptedException {
        clickWhenVisible(CONFIRM_BUTTON);
    }

    public void clickOnButtonToAddAnotherScreening() throws InterruptedException {
        clickWhenVisible(SCREENING_BUTTON_NG_CLICK);
    }

    public void clickOnButtonToCloseScheduleScreening() throws InterruptedException {
        sleep(SLEEP_1000);
        clickWhenVisible(CLOSE_BUTTON_TEXT);
        sleep(SLEEP_4000);
    }

    public void changeFilterTo(String filter) throws InterruptedException  {
        waitForElement(FILTER);
        clickOn(FILTER);
        clickWhenVisible(FILTER);
        waitForElement(By.linkText(filter));
        clickOn(By.linkText(filter));
        clickWhenVisible(By.linkText(filter));
    }

    public void exportToPDF() throws InterruptedException {
        clickWhenVisible(EXPORT_BUTTON);
        clickWhenVisible(CONFIRM_EXPORT);
    }


    public void exportToXLS() throws InterruptedException {
        clickWhenVisible(EXPORT_BUTTON);
        clickWhenVisible(FORMAT);
        clickWhenVisible(XLS);
        clickWhenVisible(CONFIRM_EXPORT);
    }

    public void setDate() throws InterruptedException  {
        clickWhenVisible(START_DATE);
        clickWhenVisible(FIRST_DAY);
        clickWhenVisible(END_DATE);
        clickWhenVisible(LAST_DAY);
    }

    public boolean bookingIdExists(String id) {
        boolean exists = false;
        int counter = 0;
        while (findElement(NEXT_PAGE).isEnabled()) {
            if (counter > MAX_PAGES) {
                break;
            }
            if (bookingIdExistsOnPage(id)) {
                exists = true;
                break;
            } else {
                clickOn(NEXT_PAGE);
            }
            counter++;
        }
        return exists;

    }

    public boolean bookingIdExistsOnPage(String id) {
        try {
            By elementBookingId = By.cssSelector("td[title=\"" + id + "\"]");
            sleep(TIMEOUT);
            waitForElement(elementBookingId);
            WebElement element = findElement(elementBookingId);
            if (element != null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFirstBookingOK(ArrayList<LocalDate> dates) {
        try {
            WebElement firstVisitElement = findElement(FIRST_VISIT);
            if (firstVisitElement == null) {
                return true;
            } else {
                LocalDate date = LocalDate.parse((firstVisitElement.getAttribute("title")).toString(), DateTimeFormat.forPattern("yyyy-MM-dd"));
                if (dates.contains(date)) {
                    return true;
                }
                    return false;
            }
        } catch (Exception e) {
            return true;
        }
    }



    @Override
    public String expectedUrlPath() {
        return getServerURL() + URL_PATH;
    }

    @Override
    public void goToPage() {

    }
}
