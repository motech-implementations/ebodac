package org.motechproject.ebodac.uitest.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class ServerLogPage extends HomePage {

    public static final String URL_PATH = "/home#/admin/log";

    private static final By REFRESH_BUTTON = By.xpath("//*[@id='main-content']/div/div/div/div/div[1]/a");
    private static final By LOG_CONTENT = By.id("logContent");

    public ServerLogPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public void refresh() throws InterruptedException {
        clickWhenVisible(REFRESH_BUTTON);
    }

    public String getLogContent() {
        return findElement(LOG_CONTENT).getText();
    }
}
