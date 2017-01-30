package org.motechproject.ebodac.uitest.page.ebodac.statistics;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class IVRKPIsPage extends AbstractKPIsPage {

    public static final String URL_PATH = "/home#/ebodac/statistics/tables/ivr";

    private static final By DATE_COLUMN = By.xpath("//th[text()='Date']");
    private static final By TOTAL_CALLS_COLUMN = By.xpath("//th[text()='Total Calls']");
    private static final By TOTAL_PENDING_COLUMN = By.xpath("//th[text()='Total Pending']");
    private static final By TOTAL_FAILED_COLUMN = By.xpath("//th[text()='Total Failed']");
    private static final By TOTAL_SUCCEEDED_COLUMN = By.xpath("//th[text()='Total Succeed']");
    private static final By CALL_TO_MEN_COLUMN = By.xpath("//th[text()='Call To Men']");
    private static final By CALL_TO_WOMEN_COLUMN = By.xpath("//th[text()='Call To Women']");
    private static final By SUCCESSFULL_CALL_TO_MEN_COLUMN = By.xpath("//th[text()='Successful call To Men']");
    private static final By SUCCESSFULL_CALL_TO_WOMEN_COLUMN = By.xpath("//th[text()='Successful call To Women']");

    public IVRKPIsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public void checkIVRColumns() {
        assertTrue(checkColumn(DATE_COLUMN));
        assertTrue(checkColumn(TOTAL_CALLS_COLUMN));
        assertTrue(checkColumn(TOTAL_PENDING_COLUMN));
        assertTrue(checkColumn(TOTAL_FAILED_COLUMN));
        assertTrue(checkColumn(TOTAL_SUCCEEDED_COLUMN));
        assertTrue(checkColumn(CALL_TO_MEN_COLUMN));
        assertTrue(checkColumn(CALL_TO_WOMEN_COLUMN));
        assertTrue(checkColumn(SUCCESSFULL_CALL_TO_MEN_COLUMN));
        assertTrue(checkColumn(SUCCESSFULL_CALL_TO_WOMEN_COLUMN));
    }
}
