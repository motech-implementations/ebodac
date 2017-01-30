package org.motechproject.ebodac.uitest.page.ebodac.reports;

import org.openqa.selenium.WebDriver;


public class FollowupsAfterPrimeInjectionReportPage extends BaseReportPage {

    public static final String URL_PATH = "/home#/ebodac/reports/followupsAfterPrimeInjectionReport";

    public FollowupsAfterPrimeInjectionReportPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }
}
