package org.motechproject.ebodac.uitest.page.ebodac.reports;

import org.openqa.selenium.WebDriver;

public class NumberOfTimesListenedReportPage extends BaseReportPage {

    public static final String URL_PATH = "/home#/ebodac/reports/ivrAndSmsStatisticReport";

    public NumberOfTimesListenedReportPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }
}
