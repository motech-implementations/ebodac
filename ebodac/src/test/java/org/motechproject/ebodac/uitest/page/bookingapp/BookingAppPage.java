package org.motechproject.ebodac.uitest.page.bookingapp;

import org.motechproject.ebodac.uitest.page.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class BookingAppPage extends HomePage {

    public static final String URL_PATH = "/home#/bookingApp/";

    private static final By CAPACITY_INFO = By.id("capacityInfo-tab");
    private static final By SCREENING = By.id("screening-tab");
    private static final By PRIME_VACCINATION = By.id("primeVaccination-tab");
    private static final By CLINIC_VISIT_SCHEDULE = By.id("clinicVisitSchedule-tab");
    private static final By RESCHEDULE_VISIT = By.id("reschedule-tab");
    private static final By UNSCHEDULED_VISIT = By.id("unscheduledVisit-tab");
    private static final By ADVANCED_SETTINGS = By.id("visitLimitation-tab");
    private static final By REPORTS = By.id("reports-tab");

    private static final By CAPACITY_INFO_LOADING = By.id("load_capacityInfo");
    private static final By SCREENING_LOADING = By.id("load_screenings");
    private static final By PRIME_VACCINATION_LOADING = By.id("load_primeVaccinationSchedule");
    private static final By RESCHEDULE_VISIT_LOADING = By.id("load_visitReschedule");
    private static final By UNSCHEDULED_VISIT_LOADING = By.id("load_unscheduledVisit");

    public BookingAppPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public BookingAppCapacityInfoPage openCapacityInfo() throws InterruptedException {
        clickWhenVisible(CAPACITY_INFO);
        waitForElementToBeHidden(CAPACITY_INFO_LOADING);
        return new BookingAppCapacityInfoPage(getDriver());
    }

    public BookingAppScreeningPage openScreening() throws InterruptedException {
        clickWhenVisible(SCREENING);
        waitForElementToBeHidden(SCREENING_LOADING);
        return new BookingAppScreeningPage(getDriver());
    }

    public BookingAppPrimeVaccinationPage openPrimeVaccination() throws InterruptedException {
        clickWhenVisible(PRIME_VACCINATION);
        waitForElementToBeHidden(PRIME_VACCINATION_LOADING);
        return new BookingAppPrimeVaccinationPage(getDriver());
    }

    public BookingAppClinicVisitSchedulePage openClinicVisitSchedule() throws InterruptedException {
        clickWhenVisible(CLINIC_VISIT_SCHEDULE);
        return new BookingAppClinicVisitSchedulePage(getDriver());
    }

    public BookingAppRescheduleVisitPage openRescheduleVisit() throws InterruptedException {
        clickWhenVisible(RESCHEDULE_VISIT);
        waitForElementToBeHidden(RESCHEDULE_VISIT_LOADING);
        return new BookingAppRescheduleVisitPage(getDriver());
    }

    public BookingAppUnscheduledVisitPage openUnscheduledVisit() throws InterruptedException {
        clickWhenVisible(UNSCHEDULED_VISIT);
        waitForElementToBeHidden(UNSCHEDULED_VISIT_LOADING);
        return new BookingAppUnscheduledVisitPage(getDriver());
    }

    public BookingAppAdvancedSettingsPage openAdvancedSettings() throws InterruptedException {
        clickWhenVisible(ADVANCED_SETTINGS);
        waitUntilBlockUiIsGone();
        waitUntilInstancesTableLoadingIsGone();
        return new BookingAppAdvancedSettingsPage(getDriver());
    }

    public boolean checkBookingAppModules() {
        if (!isElementPresent(CAPACITY_INFO)) {
            return false;
        }
        if (!isElementPresent(SCREENING)) {
            return false;
        }
        if (!isElementPresent(PRIME_VACCINATION)) {
            return false;
        }
        if (!isElementPresent(CLINIC_VISIT_SCHEDULE)) {
            return false;
        }
        if (!isElementPresent(RESCHEDULE_VISIT)) {
            return false;
        }
        if (!isElementPresent(UNSCHEDULED_VISIT)) {
            return false;
        }
        if (!isElementPresent(ADVANCED_SETTINGS)) {
            return false;
        }
        if (!isElementPresent(REPORTS)) {
            return false;
        }
        return true;
    }
}
