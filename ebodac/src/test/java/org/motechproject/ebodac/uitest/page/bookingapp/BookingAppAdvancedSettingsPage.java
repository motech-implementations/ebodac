package org.motechproject.ebodac.uitest.page.bookingapp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BookingAppAdvancedSettingsPage extends BookingAppPage {

    public static final String URL_PATH = "/home#/bookingApp/visitLimitation";

    private static final String TEST_CLINIC = "Test Clinic 1";

    private static final By MAX_CAPACITY_BY_DAY = By.xpath("//tr[.//td[@title='" + TEST_CLINIC + "']]//td[@aria-describedby='instancesTable_maxCapacityByDay']");
    private static final By AMOUNT_OF_ROOMS = By.xpath("//tr[.//td[@title='" + TEST_CLINIC + "']]//td[@aria-describedby='instancesTable_numberOfRooms']");
    private static final By TEST_LOCATION = By.xpath("//td[@title='" + TEST_CLINIC + "']");
    private static final By SAVE_BUTTON = By.xpath("//*[@id='dataBrowser']/div/div/div/ng-form/div[2]/div[1]/button[1]");
    private static final By SHOW_MORE_ADVANCED_SETTINGS_BUTTON = By.xpath("//button[@ng-click='showOrHideAdvanced()']");
    private static final String AMOUNT_OF_ROOMS_INPUT = "//*[@id='dataBrowser']/div/div/div/ng-form/div[1]/form/div[8]/div/ng-form/div[1]/input";

    public BookingAppAdvancedSettingsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public int getMaxCapacity() {
        return Integer.parseInt(findElement(MAX_CAPACITY_BY_DAY).getAttribute("title"));
    }

    public int getAmountOfRooms() {
        return Integer.parseInt(findElement(AMOUNT_OF_ROOMS).getAttribute("title"));
    }

    public void editTestClinic() throws InterruptedException {
        clickWhenVisible(TEST_LOCATION);
        waitUntilBlockUiIsGone();
    }

    public void clickShowMore() throws InterruptedException {
        clickWhenVisible(SHOW_MORE_ADVANCED_SETTINGS_BUTTON);
    }

    public void setAmountOfRooms(int amount) {
        WebElement element = findElement(By.xpath(AMOUNT_OF_ROOMS_INPUT));
        element.clear();
        element.sendKeys(Integer.toString(amount));
    }

    public void saveTestClinic() throws InterruptedException {
        clickWhenVisible(SAVE_BUTTON);
        waitUntilBlockUiIsGone();
        waitUntilInstancesTableLoadingIsGone();
    }
}
