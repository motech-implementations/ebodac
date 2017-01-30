package org.motechproject.ebodac.uitest.page.ebodac;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class VisitEditPage extends EbodacPage {

    public static final String URL_PATH = "/home#/mds/dataBrowser";

    private static final By PLANNED_VISIT_DATE = By.xpath("//ng-form[@name='motechProjectedDate']/div/input");
    private static final By ACTUAL_VISIT_DATE = By.xpath("//ng-form[@name='date']/div/input");
    private static final By VISIT_TYPE = By.xpath("//ng-form[@name='type']/div/input");
    private static final By SAVE_BUTTON = By.xpath("//div[@id='dataBrowser']/div/div/div/ng-form/div[2]/div[1]/button[1]");
    private static final By POPUP_OK = By.id("popup_ok");
    private static final By POPUP_MESSAGE = By.id("popup_message");

    public VisitEditPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    @Override
    public void goToPage() {
    }

    public boolean isPlannedVisitDateEditable() {
        return isElementEditable(PLANNED_VISIT_DATE);
    }

    public boolean isActualVisitDateEditable() {
        return isElementEditable(ACTUAL_VISIT_DATE);
    }

    public boolean isVisitTypeEditable() {
        return isElementEditable(VISIT_TYPE);
    }

    public boolean saveVisit() throws InterruptedException {
        clickWhenVisible(SAVE_BUTTON);
        clickWhenVisible(POPUP_OK);
        String text = findElement(POPUP_MESSAGE).getText();
        clickWhenVisible(POPUP_OK);
        if (text.contains("Planned Visit date has been changed successfully")) {
            return true;
        }
        return false;
    }

    public void changePlannedDate(String date) {
        findElement(PLANNED_VISIT_DATE).clear();
        findElement(PLANNED_VISIT_DATE).sendKeys(date);
        findElement(PLANNED_VISIT_DATE).sendKeys(Keys.ENTER);
    }
}
