package org.motechproject.ebodac.uitest.page.ebodac.statistics;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class SMSKPIsPage extends AbstractKPIsPage {

    public static final String URL_PATH = "/home#/ebodac/statistics/tables/sms";

    private static final By DATE_COLUMN = By.xpath("//th[text()='Date']");
    private static final By TOTAL_SMS_COLUMN = By.xpath("//th[text()='Total SMS Sent']");
    private static final By TOTAL_PENDING_COLUMN = By.xpath("//th[text()='Total Pending']");
    private static final By TOTAL_FAILED_COLUMN = By.xpath("//th[text()='Total Failed']");
    private static final By TOTAL_SUCCEEDED_COLUMN = By.xpath("//th[text()='Total Succeed']");
    private static final By SMS_TO_MEN_COLUMN = By.xpath("//th[text()='SMS To Men']");
    private static final By SMS_TO_WOMEN_COLUMN = By.xpath("//th[text()='SMS To Women']");
    private static final By SUCCESSFUL_SMS_TO_MEN_COLUMN = By.xpath("//th[text()='Successful SMS To Men']");
    private static final By SUCCESSFUL_SMS_TO_WOMEN_COLUMN = By.xpath("//th[text()='Successful SMS To Women']");

    public SMSKPIsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public void checkSMSColumns() {
        assertTrue(checkColumn(DATE_COLUMN));
        assertTrue(checkColumn(TOTAL_SMS_COLUMN));
        assertTrue(checkColumn(TOTAL_PENDING_COLUMN));
        assertTrue(checkColumn(TOTAL_FAILED_COLUMN));
        assertTrue(checkColumn(TOTAL_SUCCEEDED_COLUMN));
        assertTrue(checkColumn(SMS_TO_MEN_COLUMN));
        assertTrue(checkColumn(SMS_TO_WOMEN_COLUMN));
        assertTrue(checkColumn(SUCCESSFUL_SMS_TO_MEN_COLUMN));
        assertTrue(checkColumn(SUCCESSFUL_SMS_TO_WOMEN_COLUMN));
    }
}
