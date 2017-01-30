package org.motechproject.ebodac.uitest.page.ebodac.reports;

import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BaseReportPage extends EbodacPage {

    private static final By NUMBER_OF_ROWS = By.xpath("//*[@id='pageReportTable_left']/div");

    public BaseReportPage(WebDriver driver) {
        super(driver);
    }

    public boolean isReportEmpty() {
        return findElement(NUMBER_OF_ROWS).getAttribute("innerHTML").contains("No records to view");
    }
}
