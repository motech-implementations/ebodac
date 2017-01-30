package org.motechproject.ebodac.uitest.page.ivr;

import org.motechproject.ebodac.uitest.page.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class IVRPage extends HomePage {

    private static final String URL_PATH = "/home#/ivr";

    private static final By LOG = By.linkText("Log");

    public IVRPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public IVRLogPage goToLogPage() throws InterruptedException {
        clickWhenVisible(LOG);
        waitUntilBlockUiIsGone();
        waitUntilInstancesTableLoadingIsGone();
        return new IVRLogPage(getDriver());
    }
}
