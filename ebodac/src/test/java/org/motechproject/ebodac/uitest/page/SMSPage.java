package org.motechproject.ebodac.uitest.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class SMSPage extends HomePage {

    public static final String URL_PATH = "/home#/sms";

    private static final By LOG = By.linkText("Log");

    public SMSPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public boolean logExists() {
        return isElementPresent(LOG);
    }
}
