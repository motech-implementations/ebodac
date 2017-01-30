package org.motechproject.ebodac.uitest.page.ebodac.reports;

import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.openqa.selenium.WebDriver;

public class BoosterVaccinationReportPage extends EbodacPage {

    public static final String URL_PATH = "/home#/ebodac/reportBoosterVaccination";

    public BoosterVaccinationReportPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }
}
