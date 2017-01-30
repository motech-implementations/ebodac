package org.motechproject.ebodac.uitest.page.bookingapp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class BookingAppCapacityInfoPage extends BookingAppPage {

    public static final String URL_PATH = "/home#/bookingApp/capacityInfo";

    public static final String TEST_CLINIC = "Test Clinic 1";

    private static final By MAX_CAPACITY = By.xpath("//tr[.//td[@title='" + TEST_CLINIC + "']]//td[@aria-describedby='capacityInfo_maxCapacity']");
    private static final By FILTER_BUTTON = By.xpath("(//button[@type='button'])[2]");
    private static final By TODAY = By.linkText("Today");
    private static final By TOMORROW = By.linkText("Tomorrow");
    private static final By DAY_AFTER_TOMORROW = By.linkText("Day after tomorrow");
    private static final By NEXT_3_DAYS = By.linkText("Next 3 days");
    private static final By NEXT_7_DAYS = By.linkText("Next 7 days");
    private static final By DATE_RANGE = By.linkText("Date range");
    private static final By START_DATE = By.xpath("//input[@ng-model='selectedFilter.startDate']");
    private static final By END_DATE = By.xpath("//input[@ng-model='selectedFilter.endDate']");
    private static final By FIRST_DAY = By.linkText("1");
    private static final By LAST_DAY = By.linkText("28");
    private static final By CAPACITY_INFO_TABLE_LOADING = By.id("load_capacityInfo");

    public BookingAppCapacityInfoPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public void filterToday() throws InterruptedException {
        clickWhenVisible(FILTER_BUTTON);
        clickWhenVisible(TODAY);
        waitUntilEnrolmentTableLoadingIsGone();
    }

    public void filterTomorrow() throws InterruptedException {
        clickWhenVisible(FILTER_BUTTON);
        clickWhenVisible(TOMORROW);
        waitUntilEnrolmentTableLoadingIsGone();
    }

    public void filterDayAfterTomorrow() throws InterruptedException {
        clickWhenVisible(FILTER_BUTTON);
        clickWhenVisible(DAY_AFTER_TOMORROW);
        waitUntilEnrolmentTableLoadingIsGone();
    }

    public void filterNext3Days() throws InterruptedException {
        clickWhenVisible(FILTER_BUTTON);
        clickWhenVisible(NEXT_3_DAYS);
        waitUntilEnrolmentTableLoadingIsGone();
    }

    public void filterNext7Days() throws InterruptedException {
        clickWhenVisible(FILTER_BUTTON);
        clickWhenVisible(NEXT_7_DAYS);
        waitUntilEnrolmentTableLoadingIsGone();
    }

    public void filterDateRange() throws InterruptedException {
        clickWhenVisible(FILTER_BUTTON);
        clickWhenVisible(DATE_RANGE);
        clickWhenVisible(START_DATE);
        clickWhenVisible(FIRST_DAY);
        clickWhenVisible(END_DATE);
        clickWhenVisible(LAST_DAY);
        waitUntilEnrolmentTableLoadingIsGone();
    }

    public int getMaxCapacity() {
        return Integer.parseInt(findElement(MAX_CAPACITY).getAttribute("title"));
    }

    private void waitUntilEnrolmentTableLoadingIsGone() {
        waitForElementToBeHidden(CAPACITY_INFO_TABLE_LOADING);
    }
}
