package org.motechproject.ebodac.uitest.page.ivr;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IVRLogPage extends IVRPage {

    private static final String URL_PATH = "/home#/ivr/log";

    private static final By FIRST_RECORD = By.xpath("//*[@id='instancesTable']/tbody/tr[2]");

    public IVRLogPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public IVREditPage editFirstRecord() throws InterruptedException {
        clickWhenVisible(FIRST_RECORD);
        waitUntilBlockUiIsGone();
        return new IVREditPage(getDriver());
    }
}
