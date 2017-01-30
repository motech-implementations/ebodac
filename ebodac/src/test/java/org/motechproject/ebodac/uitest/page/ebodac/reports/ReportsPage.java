package org.motechproject.ebodac.uitest.page.ebodac.reports;

import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ReportsPage extends EbodacPage {

    public static final String URL_PATH = "/home#/ebodac/reports";

    private static final By PRIME_VACCINATION_REPORT = By.id("primerVaccinationReport");
    private static final By BOOSTER_VACCINATION_REPORT = By.id("boosterVaccinationReport");
    private static final By DAILY_CLINIC_VISIT_SCHEDULE_REPORT = By.id("dailyClinicVisitScheduleReport");
    private static final By FOLLOW_UPS_AFTER_PRIME_INJECTION_REPORT = By.id("followupsAfterPrimeInjectionReport");
    private static final By FOLLOW_UPS_MISSED_CLINIC_REPORT = By.id("followupsMissedClinicVisitsReport");
    private static final By NUMBER_OF_TIMES_REPORT = By.id("ivrAndSmsStatisticReport");
    private static final By MEMISSED_CLINIC_VISITS_REPORT = By.id("MandEMissedClinicVisitsReport");
    private static final By PARTICIPANTS_WHO_OPT_OUT_OF_MESSAGES_REPORT = By.id("optsOutOfMotechMessagesReport");
    private static final By SCREENING_REPORT = By.id("screeningReport");
    private static final By CALL_DETAIL_RECORD = By.id("callDetailRecord");
    private static final By PRIME_FOLLOW_AND_BOOST_REPORT = By.id("day8AndDay57Report");
    private static final By SMS_LOG = By.id("SMSLog");

    private static final By REPORT_TABLE = By.id("load_reportTable");

    public ReportsPage(WebDriver driver) {
        super(driver);
    }

    public PrimerVaccinationReportPage goToPrimeVaccinationReport() throws InterruptedException {
        clickWhenVisible(PRIME_VACCINATION_REPORT);
        waitUntilBlockUiIsGone();
        waitUntilInstancesTableLoadingIsGone();
        return new PrimerVaccinationReportPage(getDriver());
    }

    public BoosterVaccinationReportPage goToBoostVaccinationReport() throws InterruptedException {
        clickWhenVisible(BOOSTER_VACCINATION_REPORT);
        waitUntilBlockUiIsGone();
        waitUntilInstancesTableLoadingIsGone();
        return new BoosterVaccinationReportPage(getDriver());
    }

    public DailyClinicVisitScheduleReportPage goToDailyClinicVisitReportSchedule() throws InterruptedException {
        clickWhenVisible(DAILY_CLINIC_VISIT_SCHEDULE_REPORT);
        //wait for grid to load the data (when "Loading..." disappear)
        waitForElementToBeHidden(REPORT_TABLE);
        return new DailyClinicVisitScheduleReportPage(getDriver());
    }

    public FollowupsAfterPrimeInjectionReportPage goToFollowUpsAfterPrimeInjectionReport() throws InterruptedException {
        clickWhenVisible(FOLLOW_UPS_AFTER_PRIME_INJECTION_REPORT);
        waitForElementToBeHidden(REPORT_TABLE);
        return new FollowupsAfterPrimeInjectionReportPage(getDriver());
    }

    public FollowupsMissedClinicVisitsReportPage goToFollowUpsMissedClinicReport() throws InterruptedException {
        clickWhenVisible(FOLLOW_UPS_MISSED_CLINIC_REPORT);
        waitForElementToBeHidden(REPORT_TABLE);
        return new FollowupsMissedClinicVisitsReportPage(getDriver());
    }

    public NumberOfTimesListenedReportPage goToNumberOfTimesReport() throws InterruptedException {
        clickWhenVisible(NUMBER_OF_TIMES_REPORT);
        waitForElementToBeHidden(REPORT_TABLE);
        return new NumberOfTimesListenedReportPage(getDriver());
    }

    public MEMissedClinicVisitsReportPage goToMEMissedClinicVisitsReport() throws InterruptedException {
        clickWhenVisible(MEMISSED_CLINIC_VISITS_REPORT);
        waitForElementToBeHidden(REPORT_TABLE);
        return new MEMissedClinicVisitsReportPage(getDriver());
    }

    public ParticipantsWhoOptOutOfMessagesReportPage goToParticipantsWhoOptOutOfMessages() throws InterruptedException {
        clickWhenVisible(PARTICIPANTS_WHO_OPT_OUT_OF_MESSAGES_REPORT);
        waitForElementToBeHidden(REPORT_TABLE);
        return new ParticipantsWhoOptOutOfMessagesReportPage(getDriver());
    }

    public ScreeningReportPage goToScreeningReport() throws InterruptedException {
        clickWhenVisible(SCREENING_REPORT);
        waitForElementToBeHidden(REPORT_TABLE);
        return new ScreeningReportPage(getDriver());
    }

    public CallDetailRecordPage goToCallDetailRecord() throws InterruptedException {
        clickWhenVisible(CALL_DETAIL_RECORD);
        waitUntilBlockUiIsGone();
        waitUntilInstancesTableLoadingIsGone();
        return new CallDetailRecordPage(getDriver());
    }

    public PrimeFollowAndBoostReportPage goToPrimeFollowAndBoostReport() throws InterruptedException {
        clickWhenVisible(PRIME_FOLLOW_AND_BOOST_REPORT);
        waitForElementToBeHidden(REPORT_TABLE);
        return new PrimeFollowAndBoostReportPage(getDriver());
    }

    public SMSLogReportPage goToSMSLog() throws InterruptedException {
        clickWhenVisible(SMS_LOG);
        waitUntilBlockUiIsGone();
        waitUntilInstancesTableLoadingIsGone();
        return new SMSLogReportPage(getDriver());
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }
}
