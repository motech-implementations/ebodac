package org.motechproject.ebodac.uitest.page.ebodac.reports;

import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BaseReportPage extends EbodacPage {

    private static final By NUMBER_OF_ROWS = By.xpath("//*[@id='pageReportTable_left']/div");

    private static final By LOOKUP = By.id("lookupDialogButton");
    private static final By LOOKUP_DROPDOWN = By.id("selectLookupBtn");
    private static final By LOOKUP_FIND = By.id("lookupFindBtn");

    private static final By REPORT_TABLE_LOADING = By.id("load_reportTable");

    public BaseReportPage(WebDriver driver) {
        super(driver);
    }

    public boolean isReportEmpty() {
        return findElement(NUMBER_OF_ROWS).getAttribute("innerHTML").contains("No records to view");
    }

    public void openLookup() throws InterruptedException {
        clickWhenVisible(LOOKUP);
    }

    public void openDropdown() throws InterruptedException {
        clickWhenVisible(LOOKUP_DROPDOWN);
    }

    public void selectLookup(String name) throws InterruptedException {
        openLookup();
        openDropdown();
        clickWhenVisible(By.linkText(name));
    }

    public void clickFind() throws InterruptedException {
        clickWhenVisible(LOOKUP_FIND);
        waitForElementToBeHidden(REPORT_TABLE_LOADING);
    }
}
