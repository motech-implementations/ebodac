
package org.motechproject.ebodac.uitest.page.bookingapp;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BookingAppRescheduleVisitPage extends BookingAppPage {

    public static final String URL_PATH = "/home#/bookingApp/reschedule";

    private static final String TEST_PARTICIPANT_ID = "9990000099";
    private static final String HOUR_12_MINUTE_00 = "12:00";
    private static final String VISIT_PLANNED_DATE_UPDATED_SUCCESSFULLY = "Visit Planned Date updated successfully.";
    private static final int DAY_BEFORE_CHANGE = 5;
    private static final int DAY_AFTER_CHANGE = 20;

    private static final By TEST_VISIT = By.xpath("//table[@id='visitReschedule']/tbody/tr/td[@title='Second Long-term Follow-up visit']");
    private static final By FILTER_BUTTON = By.id("lookupDialogButton");
    private static final By FILTER_DROPDOWN_BUTTON = By.id("selectLookupBtn");
    private static final By FIND_BY_PARTICIPANT_ID = By.xpath("//*[@id='lookupList']/li/a[contains(text(),'Find By Participant Id')]");
    private static final By FIND_BY_PARTICIPANT_ID_FIELD = By.xpath("//*[@id='lookup-dialog']/div[2]/div[2]/div/input");
    private static final By FILTER_FIND_BUTTON = By.id("lookupFindBtn");
    private static final By IGNORE_EARLIEST_LATEST_DATE = By.id("ignoreDateLimit");
    private static final By SAVE_BUTTON = By.id("saveBtn");
    private static final By CLOSE_BUTTON = By.id("closeBtn");
    private static final By PRINT_CARD = By.id("printBtn");
    private static final By DATE_FIELD = By.id("plannedDateInput");
    private static final By TIME_FIELD = By.id("startTimeInput");
    private static final By DIALOG_TEXT = By.id("rescheduleStatusMessage");
    private static final By POPUP_OK = By.id("popup_ok");

    private static final By ENROLLMENT_TABLE_LOADING = By.id("load_visitReschedule");

    public BookingAppRescheduleVisitPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public void filterByParticipantId() throws InterruptedException {
        clickWhenVisible(FILTER_BUTTON);
        clickWhenVisible(FILTER_DROPDOWN_BUTTON);
        clickWhenVisible(FIND_BY_PARTICIPANT_ID);
        findElement(FIND_BY_PARTICIPANT_ID_FIELD).sendKeys(TEST_PARTICIPANT_ID);
        clickWhenVisible(FILTER_FIND_BUTTON);
        waitUntilRescheduleTableLoadingIsGone();
    }

    public void chooseTestVisit() throws InterruptedException {
        filterByParticipantId();
        clickWhenVisible(TEST_VISIT);
    }

    public boolean rescheduleVisit(int day) throws InterruptedException {
        String text;

        if (!findElement(IGNORE_EARLIEST_LATEST_DATE).isSelected()) {
            clickWhenVisible(IGNORE_EARLIEST_LATEST_DATE);
        }
        clickWhenVisible(DATE_FIELD);
        sleep500();
        clickWhenVisible(By.linkText(Integer.toString(day)));

        WebElement timeInput = findElement(TIME_FIELD);
        timeInput.clear();
        timeInput.sendKeys(HOUR_12_MINUTE_00);
        timeInput.sendKeys(Keys.ESCAPE);

        clickWhenVisible(SAVE_BUTTON);
        clickWhenVisible(POPUP_OK);
        text = findElement(DIALOG_TEXT).getText();

        return text.contains(VISIT_PLANNED_DATE_UPDATED_SUCCESSFULLY);
    }

    public boolean rescheduleVisit() throws InterruptedException {
        return rescheduleVisit(DAY_AFTER_CHANGE);
    }

    public void printCard() throws InterruptedException {
        clickWhenVisible(PRINT_CARD);
    }

    public void clickClose() throws InterruptedException {
        clickWhenVisible(CLOSE_BUTTON);
    }

    public boolean changeBackTheDate() throws InterruptedException {
        return rescheduleVisit(DAY_BEFORE_CHANGE);
    }

    private void waitUntilRescheduleTableLoadingIsGone() {
        waitForElementToBeHidden(ENROLLMENT_TABLE_LOADING);
    }
}
