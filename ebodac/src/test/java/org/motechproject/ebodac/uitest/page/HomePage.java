package org.motechproject.ebodac.uitest.page;

import org.motechproject.ebodac.uitest.page.bookingapp.BookingAppPage;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.ivr.IVRPage;
import org.motechproject.uitest.page.MotechPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.Set;

public class HomePage extends MotechPage {

    private static final By EBODAC = By.linkText("EBODAC");
    private static final By SECURITY = By.linkText("Security");
    private static final By DATA_SERVICES = By.linkText("Data Services");
    private static final By EMAIL = By.linkText("Email");
    private static final By MESSAGE_CAMPAIGN = By.linkText("Message Campaign");
    private static final By IVR = By.linkText("IVR");
    private static final By SMS = By.linkText("SMS");
    private static final By SCHEDULER = By.linkText("Scheduler");
    private static final By TASKS = By.linkText("Tasks");
    private static final By MODULES = By.linkText("Modules");
    private static final By BOOKING_APP = By.linkText("Booking App");
    private static final By ADMIN = By.linkText("Admin");

    private static final By GRID_TABLE = By.xpath("//table[@class='ui-jqgrid-htable']");
    private static final By INSTANCES_TABLE_LOADING = By.id("load_instancesTable");
    private static final By IVR_FILTERS = By.className("filter-header");

    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;

    private static final int TIME_500 = 500;
    private static final int TIME_10000 = 10000;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void openAdmin() throws InterruptedException {
        clickWhenVisible(ADMIN);
    }

    public void openSecurity() throws InterruptedException {
        clickWhenVisible(SECURITY);
    }

    public void openModules() throws InterruptedException {
        clickWhenVisible(MODULES);
    }

    public BookingAppPage goToBookingAppModule() throws InterruptedException {
        openModules();
        clickWhenVisible(BOOKING_APP);
        waitUntilBlockUiIsGone();
        return new BookingAppPage(getDriver());
    }

    public DataServicesPage goToDataServicesModule() throws InterruptedException {
        openModules();
        clickWhenVisible(DATA_SERVICES);
        waitUntilBlockUiIsGone();
        return new DataServicesPage(getDriver());
    }

    public EbodacPage goToEbodacModule() throws InterruptedException {
        openModules();
        clickWhenVisible(EBODAC);
        waitUntilBlockUiIsGone();
        return new EbodacPage(getDriver());
    }

    public IVRPage goToIVRModule() throws InterruptedException {
        openModules();
        clickWhenVisible(IVR);
        waitUntilBlockUiIsGone();

        WebElement filters = findElement(IVR_FILTERS);

        Long startTime = System.currentTimeMillis();
        while (filters.isDisplayed() && (System.currentTimeMillis() - startTime) < TIME_10000) {
            clickOn(IVR);
            sleep500();
        }
        return new IVRPage(getDriver());
    }

    public SMSPage goToSMSModule() throws InterruptedException {
        openModules();
        clickWhenVisible(SMS);
        waitUntilBlockUiIsGone();
        return new SMSPage(getDriver());
    }

    public boolean isDataServicesModulePresent() throws InterruptedException {
        openModules();
        return isElementPresent(DATA_SERVICES);
    }

    public boolean isEBODACModulePresent() throws InterruptedException {
        openModules();
        return isElementPresent(EBODAC);
    }

    public boolean isEmailModulePresent() throws InterruptedException {
        openModules();
        return isElementPresent(EMAIL);
    }

    public boolean isMessageCampaignModulePresent() throws InterruptedException {
        openModules();
        return isElementPresent(MESSAGE_CAMPAIGN);
    }

    public boolean isIVRModulePresent() throws InterruptedException {
        openModules();
        return isElementPresent(IVR);
    }

    public boolean isSMSModulePresent() throws InterruptedException {
        openModules();
        return isElementPresent(SMS);
    }

    public boolean isSchedulerModulePresent() throws InterruptedException {
        openModules();
        return isElementPresent(SCHEDULER);
    }

    public boolean isTasksModulePresent() throws InterruptedException {
        openModules();
        return isElementPresent(TASKS);
    }

    public boolean closePdfIfIsOpen(String originalHandle) {
        Set<String> handles = getDriver().getWindowHandles();

        Long startTime = System.currentTimeMillis();
        while (handles.size() < 2 && (System.currentTimeMillis() - startTime) < TIME_10000) {
            handles = getDriver().getWindowHandles();
            sleep500();
        }

        if (handles.size() < 2) {
            return false;
        }

        handles.remove(originalHandle);

        Iterator<String> it = handles.iterator();

        if (handles.size() > 1) {
            it.next();
        }

        getDriver().switchTo().window(it.next());
        getDriver().close();

        getDriver().switchTo().window(originalHandle);

        return true;
    }

    public String getWindowHandle() {
        return getDriver().getWindowHandle();
    }

    public boolean existTable() {
        return (!findElement(GRID_TABLE).getAttribute("innerHTML").isEmpty());
    }

    public void resizePage() {
        getDriver().manage().window().setSize(new Dimension(WIDTH, HEIGHT));
    }

    private void sleep(long sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            getLogger().error("waitTimeout - Exception . Reason : " + e.getLocalizedMessage(), e);
        }
    }

    public void sleep500() {
        sleep(TIME_500);
    }

    public boolean isElementPresent(By by) {
        return !getDriver().findElements(by).isEmpty();
    }

    public void waitUntilInstancesTableLoadingIsGone() {
        waitForElementToBeHidden(INSTANCES_TABLE_LOADING);
    }

    public boolean isElementEditable(By by) {
        String readonly = findElement(by).getAttribute("readonly");
        return readonly == null || !(readonly.contains("readonly") || readonly.contains("true"));
    }
}
