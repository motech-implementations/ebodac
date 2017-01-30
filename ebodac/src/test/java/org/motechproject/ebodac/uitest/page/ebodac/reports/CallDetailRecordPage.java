package org.motechproject.ebodac.uitest.page.ebodac.reports;

import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CallDetailRecordPage extends EbodacPage {

    public static final String URL_PATH = "/home#/ebodac/callDetailRecord";

    private static final String ID_PAGE_INSTANCES_TABLE_LEFT_DIV = "//*[@id='pageInstancesTable_left']/div";
    private static final String NO_RECORDS_TO_VIEW = "No records to view";

    public CallDetailRecordPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public boolean checkIfIVRTableHistoryContainsRows() {
        return !findElement(By.xpath(ID_PAGE_INSTANCES_TABLE_LEFT_DIV)).getAttribute("innerHTML").contains(NO_RECORDS_TO_VIEW);
    }
}
