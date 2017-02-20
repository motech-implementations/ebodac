package org.motechproject.ebodac.uitest.page.ebodac.reports;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class FollowupsMissedClinicVisitsReportPage extends BaseReportPage {

    public static final String URL_PATH = "/home#/ebodac/reports/followupsMissedClinicVisitsReport";

    private static final By NAME_COLUMN = By.id("reportTable_subjectName");
    private static final By HOUSEHOLD_NAME_COLUMN = By.id("reportTable_subjectHouseholdName");
    private static final By HEAD_OF_HOUSEHOLD_COLUMN = By.id("reportTable_subjectHeadOfHousehold");
    private static final By DATE_OF_BIRTH_COLUMN = By.id("reportTable_subjectDateOfBirth");
    private static final By GENDER_COLUMN = By.id("reportTable_subjectGender");
    private static final By ADDRESS_COLUMN = By.id("reportTable_subjectAddress");
    private static final By TYPE_COLUMN = By.id("reportTable_type");
    private static final By VISIT_DATE_COLUMN = By.id("reportTable_planedVisitDate");
    private static final By EXCEEDED_VISIT_COLUMN = By.id("reportTable_noOfDaysExceededVisit");
    private static final By COMMUNITY_COLUMN = By.id("reportTable_subjectCommunity");
    private static final By STAGE_ID_COLUMN = By.id("reportTable_subjectStageId");
    private static final By SITE_NAME_COLUMN = By.id("reportTable_subjectSiteName");

    public FollowupsMissedClinicVisitsReportPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public void checkColumns() {
        assertTrue(checkColumn(NAME_COLUMN));
        assertTrue(checkColumn(HOUSEHOLD_NAME_COLUMN));
        assertTrue(checkColumn(HEAD_OF_HOUSEHOLD_COLUMN));
        assertTrue(checkColumn(DATE_OF_BIRTH_COLUMN));
        assertTrue(checkColumn(GENDER_COLUMN));
        assertTrue(checkColumn(ADDRESS_COLUMN));
        assertTrue(checkColumn(TYPE_COLUMN));
        assertTrue(checkColumn(VISIT_DATE_COLUMN));
        assertTrue(checkColumn(EXCEEDED_VISIT_COLUMN));
        assertTrue(checkColumn(COMMUNITY_COLUMN));
        assertTrue(checkColumn(STAGE_ID_COLUMN));
        assertTrue(checkColumn(SITE_NAME_COLUMN));
    }
}
