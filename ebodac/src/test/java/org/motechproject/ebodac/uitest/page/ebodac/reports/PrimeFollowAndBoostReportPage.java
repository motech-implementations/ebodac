package org.motechproject.ebodac.uitest.page.ebodac.reports;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PrimeFollowAndBoostReportPage extends BaseReportPage {

    public static final String URL_PATH = "/home#/ebodac/reports/day8AndDay57Report";

    private static final By LOOKUP = By.id("lookupDialogButton");
    private static final By LOOKUP_DROPDOWN = By.id("selectLookupBtn");
    private static final By VISIT_TYPE_AND_ACTUAL_VISIT_DATE_LOOKUP = By.linkText("Find By Visit Type And Actual Visit Date");
    private static final By VISIT_TYPE_AND_SITE_NAME_LOOKUP = By.linkText("Find By Visit Type And Site Name");
    private static final By VISIT_TYPE_PLANNED_VISIT_DATE_AND_SITE_NAME_LOOKUP = By.linkText("Find By Visit Type Planned Visit Date And Site Name");
    private static final By VISIT_TYPE_ACTUAL_VISIT_DATE_AND_STAGE_ID = By.linkText("Find By Visit Type Actual Visit Date And Stage Id");
    private static final By VISIT_TYPE_AND_STAGE_ID = By.linkText("Find By Visit Type And Stage Id");
    private static final By VISIT_TYPE_PLANNED_VISIT_DATE_AND_STAGE_ID = By.linkText("Find By Visit Type Planned Visit Date And Stage Id");
    private static final By VISIT_TYPE_ACTUAL_VISIT_DATE_AND_SITE_NAME_LOOKUP = By.linkText("Find By Visit Type Actual Visit Date And Site Name");

    private static final By VISIT_TYPE_FIELD = By.xpath("//*[@id='lookup-dialog']/div[2]/div[2]/div/select");
    private static final By PRIME_VACCINATION_FIRST_FOLLOW_UP_VISIT_OPTION = By.xpath("//option[text()='Prime Vaccination First Follow-up visit']");
    private static final By BOOST_VACCINATION_VISIT_OPTION = By.xpath("//option[text()='Boost Vaccination Day']");

    public PrimeFollowAndBoostReportPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public void openLookup() throws InterruptedException {
        clickWhenVisible(LOOKUP);
    }

    public void openDropdown() throws InterruptedException {
        clickWhenVisible(LOOKUP_DROPDOWN);
    }

    public boolean areLookupsPresent() {
        if (!isElementPresent(VISIT_TYPE_AND_ACTUAL_VISIT_DATE_LOOKUP)) {
            return false;
        }
        if (!isElementPresent(VISIT_TYPE_AND_SITE_NAME_LOOKUP)) {
            return false;
        }
        if (!isElementPresent(VISIT_TYPE_PLANNED_VISIT_DATE_AND_SITE_NAME_LOOKUP)) {
            return false;
        }
        if (!isElementPresent(VISIT_TYPE_ACTUAL_VISIT_DATE_AND_STAGE_ID)) {
            return false;
        }
        if (!isElementPresent(VISIT_TYPE_AND_STAGE_ID)) {
            return false;
        }
        if (!isElementPresent(VISIT_TYPE_PLANNED_VISIT_DATE_AND_STAGE_ID)) {
            return false;
        }
        if (!isElementPresent(VISIT_TYPE_ACTUAL_VISIT_DATE_AND_SITE_NAME_LOOKUP)) {
            return false;
        }

        return true;
    }

    public void openByVisitTypeAndActualVisitDateLookup() throws InterruptedException {
        clickWhenVisible(VISIT_TYPE_AND_ACTUAL_VISIT_DATE_LOOKUP);
    }

    public boolean isLookupOpen() {
        return isElementPresent(VISIT_TYPE_FIELD);
    }

    public boolean areAllVisitsAvailable() {
        if (!isElementPresent(PRIME_VACCINATION_FIRST_FOLLOW_UP_VISIT_OPTION)) {
            return false;
        }
        if (!isElementPresent(BOOST_VACCINATION_VISIT_OPTION)) {
            return false;
        }

        return true;
    }

    public void openVisitType() throws InterruptedException {
        clickWhenVisible(VISIT_TYPE_FIELD);
    }
}
