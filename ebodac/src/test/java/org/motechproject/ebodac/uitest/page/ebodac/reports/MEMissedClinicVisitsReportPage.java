package org.motechproject.ebodac.uitest.page.ebodac.reports;

import org.openqa.selenium.WebDriver;

public class MEMissedClinicVisitsReportPage extends BaseReportPage {

    public static final String URL_PATH = "/home#/ebodac/reports/MandEMissedClinicVisitsReport";

    public MEMissedClinicVisitsReportPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }
}
