package org.motechproject.ebodac.uitest.page.ebodac.statistics;

import org.openqa.selenium.WebDriver;

public class IVRGraphsPage extends AbstractGraphsPage {

    public static final String URL_PATH = "/home#/ebodac/statistics/graphs/ivr";

    public IVRGraphsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return getServerURL() + URL_PATH;
    }
}
