package org.motechproject.ebodac.uitest.page.ebodac.reports;

import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BoosterVaccinationReportPage extends EbodacPage {

    public static final String URL_PATH = "/home#/ebodac/reportBoosterVaccination";

    private static final By NUMBER_OF_ROWS = By.xpath("//*[@id='pageInstancesTable_left']/div");

    public BoosterVaccinationReportPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public boolean isReportEmpty() {
        return findElement(NUMBER_OF_ROWS).getAttribute("innerHTML").contains("No records to view");
    }
}
