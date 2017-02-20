package org.motechproject.ebodac.uitest.page.ebodac.reports;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class NumberOfTimesListenedReportPage extends BaseReportPage {

    public static final String URL_PATH = "/home#/ebodac/reports/ivrAndSmsStatisticReport";

    private static final By ID_COLUMN = By.id("reportTable_subjectId");
    private static final By PHONE_COLUMN = By.id("reportTable_phone");
    private static final By GENDER_COLUMN = By.id("reportTable_gender");
    private static final By AGE_COLUMN = By.id("reportTable_age");
    private static final By COMMUNITY_COLUMN = By.id("reportTable_community");
    private static final By MESSAGE_COLUMN = By.id("reportTable_messageId");
    private static final By SENT_DATE_COLUMN = By.id("reportTable_sendDate");
    private static final By EXPECTED_DURATION_COLUMN = By.id("reportTable_expectedDuration");
    private static final By TIME_LISTENED_COLUMN = By.id("reportTable_timeListenedTo");
    private static final By PERCENT_LISTENED_COLUMN = By.id("reportTable_messagePercentListened");
    private static final By DATE_RECEIVED_COLUMN = By.id("reportTable_receivedDate");
    private static final By NUMBER_OF_ATTEMPTS_COLUMN = By.id("reportTable_numberOfAttempts");
    private static final By SMS_COLUMN = By.id("reportTable_sms");
    private static final By SMS_RECEIVED_COLUMN = By.id("reportTable_smsReceivedDate");
    private static final By STAGE_ID_COLUMN = By.id("reportTable_stageId");

    public NumberOfTimesListenedReportPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public void checkColumns() {
        assertTrue(checkColumn(ID_COLUMN));
        assertTrue(checkColumn(PHONE_COLUMN));
        assertTrue(checkColumn(GENDER_COLUMN));
        assertTrue(checkColumn(AGE_COLUMN));
        assertTrue(checkColumn(COMMUNITY_COLUMN));
        assertTrue(checkColumn(MESSAGE_COLUMN));
        assertTrue(checkColumn(SENT_DATE_COLUMN));
        assertTrue(checkColumn(EXPECTED_DURATION_COLUMN));
        assertTrue(checkColumn(TIME_LISTENED_COLUMN));
        assertTrue(checkColumn(PERCENT_LISTENED_COLUMN));
        assertTrue(checkColumn(DATE_RECEIVED_COLUMN));
        assertTrue(checkColumn(NUMBER_OF_ATTEMPTS_COLUMN));
        assertTrue(checkColumn(SMS_COLUMN));
        assertTrue(checkColumn(SMS_RECEIVED_COLUMN));
        assertTrue(checkColumn(STAGE_ID_COLUMN));
    }
}
