package org.motechproject.ebodac.uitest.page.ebodac.statistics;

import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public abstract class AbstractGraphsPage extends EbodacPage {

    private static final By GRAPH_PERIOD_BUTTON = By.id("dateFilterBtn");
    private static final By DATE_RANGE = By.linkText("Date Range");
    private static final By START_DATE = By.id("startDateInput");
    private static final By END_DATE = By.id("endDateInput");
    private static final By DATE_PICKER_TODAY_BUTTON = By.xpath("//*[@id='ui-datepicker-div']/div[2]/button[1]");
    private static final By DATE_PICKER_DONE_BUTTON = By.xpath("//*[@id='ui-datepicker-div']/div[2]/button[2]");
    private static final By DATE_SELECT = By.xpath("//*[@id='ui-datepicker-div']/div[1]/div/select");
    private static final By DAY_10 = By.linkText("10");

    private static final By STATUS_GRAPH = By.id("statusGraph");
    private static final By GENDER_GRAPH = By.id("genderGraph");
    private static final By SUCCESSFUL_GENDER_GRAPH = By.id("successfulGenderGraph");

    public AbstractGraphsPage(WebDriver driver) {
        super(driver);
    }

    public void showStatsFromLastYear() throws InterruptedException {
        clickWhenVisible(GRAPH_PERIOD_BUTTON);
        clickWhenVisible(DATE_RANGE);

        clickWhenVisible(START_DATE);
        Select dropdown = new Select(findElement(DATE_SELECT));
        dropdown.selectByVisibleText("2015");
        clickWhenVisible(DAY_10);

        clickWhenVisible(END_DATE);
        clickWhenVisible(DATE_PICKER_TODAY_BUTTON);
        clickWhenVisible(DATE_PICKER_DONE_BUTTON);
    }

    //isElementPresent cannot be used, because we need to wait for graphs to be loaded
    public boolean checkGraphs() {
        try {
            if (findElement(STATUS_GRAPH) == null) {
                return false;
            }
            if (findElement(GENDER_GRAPH) == null) {
                return false;
            }
            if (findElement(SUCCESSFUL_GENDER_GRAPH) == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
