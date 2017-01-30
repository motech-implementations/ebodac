package org.motechproject.ebodac.uitest.page.ebodac.reports;

import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.openqa.selenium.WebDriver;

public class PrimerVaccinationReportPage extends EbodacPage {

    public static final String URL_PATH = "/home#/ebodac/reportPrimerVaccination";

    public PrimerVaccinationReportPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }
}
