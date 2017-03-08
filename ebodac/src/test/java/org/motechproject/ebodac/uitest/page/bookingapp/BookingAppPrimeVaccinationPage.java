package org.motechproject.ebodac.uitest.page.bookingapp;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BookingAppPrimeVaccinationPage extends BookingAppPage {

    public static final String URL_PATH = "/home#/bookingApp/primeVaccination";

    private static final String FIRST_TEST_PARTICIPANT_ID = "9990000002";
    private static final String SECOND_TEST_PARTICIPANT_ID = "9990000003";
    private static final String TIME_10_29 = "10:29";
    private static final String PARTICIPANT_WITH_ID_PATH = "//*[@id='select2-drop']/ul/li/div[contains(text(), '%s')]";
    private static final String PRIME_VAC_INPUT_DISABLED = "select2-container-disabled";
    private static final int TIME_10000 = 10000;

    private static final By PRIME_VAC_TABLE_LOADING = By.id("load_primeVaccinationSchedule");
    private static final By ADD_PRIME_VACCINATION_BUTTON = By.id("addPrimeVacBtn");
    private static final By PARTICIPANT_SELECT = By.id("s2id_subjectIdSelect");
    private static final By SCREENING_DATE_INPUT = By.id("screeningDateInput");
    private static final By PRIME_VAC_DATE_INPUT = By.id("primeVacDateInput");
    private static final By IGNORE_LATEST_EARLIEST_DATE_CHECKBOX = By.id("ignoreDateLimitationInput");
    private static final By DATE_PICKER_TODAY_BUTTON = By.xpath("//*[@id='ui-datepicker-div']/div[2]/button[1]");
    private static final By DATE_PICKER_DONE_BUTTON = By.xpath("//*[@id='ui-datepicker-div']/div[2]/button[2]");
    private static final By PRIME_VAC_TIME_INPUT = By.id("primeVacTimeInput");
    private static final By SAVE_BUTTON = By.id("saveBtn");
    private static final By POPUP_OK = By.id("popup_ok");
    private static final By PRINT_BUTTON = By.id("printBtn");
    private static final By CLOSE_BUTTON = By.id("closeBtn");
    private static final By DATE_FILTER_BUTTON = By.id("dateFilterBtn");
    private static final By DATE_FILTER_DATE_RANGE = By.xpath("//*[@id='main-content']/div/div/div[1]/div/ul/li[6]/a");
    private static final By FIRST_ROW_IN_THE_GRID = By.id("1");
    private static final By FILTER_BUTTON = By.id("lookupDialogButton");
    private static final By FILTER_DROPDOWN_BUTTON = By.id("selectLookupBtn");
    private static final By FIND_BY_PARTICIPANT_ID = By.xpath("//*[@id='lookupList']/li/a[contains(text(),'Find By Participant Id')]");
    private static final By FIND_BY_PARTICIPANT_ID_FIELD = By.xpath("//*[@id='lookup-dialog']/div[2]/div[2]/div/input");
    private static final By FILTER_FIND_BUTTON = By.id("lookupFindBtn");
    private static final By FIRST_PARTICIPANT_PRIME_VAC_DATE = By.xpath("//*[@id='1']/td[6]");
    private static final By FEMALE_CHILD_BEARING_AGE_DROPDOWN = By.id("s2id_femaleChildBearingAgeSelect");
    private static final By FEMALE_CHILD_BEARING_AGE_YES_OPTION = By.xpath("//*[@id='select2-drop']/ul/li[2]/div");

    private static final By CLINIC_LOCATION = By.id("jqgh_primeVaccinationSchedule_location");
    private static final By PARTICIPANT_ID = By.id("jqgh_primeVaccinationSchedule_participantId");
    private static final By PARTICIPANT_NAME = By.id("jqgh_primeVaccinationSchedule_participantName");
    private static final By FEMALE_CHILD_BEARING_AGE = By.id("jqgh_primeVaccinationSchedule_femaleChildBearingAge");
    private static final By ACTUAL_SCREENING_DATE = By.id("jqgh_primeVaccinationSchedule_bookingScreeningActualDate");
    private static final By PRIME_VAC_DATE = By.id("jqgh_primeVaccinationSchedule_date");
    private static final By PRIME_VAC_TIME = By.id("jqgh_primeVaccinationSchedule_startTime");

    public BookingAppPrimeVaccinationPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public void clickAddPrimeVaccinationButton() throws InterruptedException {
        clickWhenVisible(ADD_PRIME_VACCINATION_BUTTON);
    }

    /**
     * Method: We use this method to find the participant.
     *
     * @throws InterruptedException
     */
    public boolean selectTestParticipant() throws InterruptedException {
        if (!waitForParticipantListToBeLoaded()) {
            return false;
        }

        clickWhenVisible(PARTICIPANT_SELECT);
        clickWhenVisible(By.xpath(String.format(PARTICIPANT_WITH_ID_PATH, FIRST_TEST_PARTICIPANT_ID)));

        return true;
    }

    private boolean waitForParticipantListToBeLoaded() {
        Long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < TIME_10000) {
            if (!findElement(PARTICIPANT_SELECT).getAttribute("class").contains(PRIME_VAC_INPUT_DISABLED)) {
                return true;
            }

            sleep500();
        }

        return false;
    }

    public void setScreeningDate() throws InterruptedException {
        clickWhenVisible(SCREENING_DATE_INPUT);
        clickWhenVisible(DATE_PICKER_TODAY_BUTTON);
        clickWhenVisible(DATE_PICKER_DONE_BUTTON);
    }

    public void selectIgnoreLatestEarliestDate() throws InterruptedException {
        if (!findElement(IGNORE_LATEST_EARLIEST_DATE_CHECKBOX).isSelected()) {
            clickWhenVisible(IGNORE_LATEST_EARLIEST_DATE_CHECKBOX);
        }
    }

    public void setPrimeVacDate() throws InterruptedException {
        clickWhenVisible(PRIME_VAC_DATE_INPUT);
        clickWhenVisible(DATE_PICKER_TODAY_BUTTON);
        clickWhenVisible(DATE_PICKER_DONE_BUTTON);
    }

    public void setPrimeVacTime() throws InterruptedException {
        WebElement element = findElement(PRIME_VAC_TIME_INPUT);
        element.clear();
        element.sendKeys(TIME_10_29);
        element.sendKeys(Keys.ESCAPE);
    }

    public void saveAndConfirmChanges() throws InterruptedException {
        clickWhenVisible(SAVE_BUTTON);
        clickWhenVisible(POPUP_OK);
    }

    public void clickPrintCard() throws InterruptedException {
        clickWhenVisible(PRINT_BUTTON);
    }

    public void closeSuccessModal() throws InterruptedException {
        clickWhenVisible(CLOSE_BUTTON);
    }

    public void changeDateFilterToDateRange() throws InterruptedException {
        clickWhenVisible(DATE_FILTER_BUTTON);
        clickWhenVisible(DATE_FILTER_DATE_RANGE);
        waitUntilPrimeVacTableLoadingIsGone();
    }

    public void filterByParticipantId() throws InterruptedException {
        clickWhenVisible(FILTER_BUTTON);
        clickWhenVisible(FILTER_DROPDOWN_BUTTON);
        clickWhenVisible(FIND_BY_PARTICIPANT_ID);
        findElement(FIND_BY_PARTICIPANT_ID_FIELD).sendKeys(SECOND_TEST_PARTICIPANT_ID);
        clickWhenVisible(FILTER_FIND_BUTTON);
        waitUntilPrimeVacTableLoadingIsGone();
    }

    public void clickOnFirstRowInTheGrid() throws InterruptedException {
        clickWhenVisible(FIRST_ROW_IN_THE_GRID);
    }

    public String firstParticipantPrimeVacDay() {
        return findElement(FIRST_PARTICIPANT_PRIME_VAC_DATE).getText();
    }

    public void setFemaleChildBearingAge() throws InterruptedException {
        clickWhenVisible(FEMALE_CHILD_BEARING_AGE_DROPDOWN);
        clickWhenVisible(FEMALE_CHILD_BEARING_AGE_YES_OPTION);
    }

    public boolean checkTableHeaders() {
        try {
            if (!"Clinic Location".equals(findElement(CLINIC_LOCATION).getText())) {
                return false;
            }
            if (!"Participant Id".equals(findElement(PARTICIPANT_ID).getText())) {
                return false;
            }
            if (!"Participant Name".equals(findElement(PARTICIPANT_NAME).getText())) {
                return false;
            }
            if (!"Female Child Bearing Age".equals(findElement(FEMALE_CHILD_BEARING_AGE).getText())) {
                return false;
            }
            if (!"Actual Screening Date".equals(findElement(ACTUAL_SCREENING_DATE).getText())) {
                return false;
            }
            if (!"Prime Vac. Date".equals(findElement(PRIME_VAC_DATE).getText())) {
                return false;
            }
            if (!"Prime Vac. Time".equals(findElement(PRIME_VAC_TIME).getText())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void waitUntilPrimeVacTableLoadingIsGone() {
        waitForElementToBeHidden(PRIME_VAC_TABLE_LOADING);
    }
}
