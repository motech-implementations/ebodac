package org.motechproject.ebodac.uitest.page.bookingapp;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class BookingAppClinicVisitSchedulePage extends BookingAppPage {

    private static final String URL_PATH = "/home#/bookingApp/clinicVisitSchedule";

    private static final String TEST_PARTICIPANT_ID = "9990000001";
    private static final String NO_RESULTS_CLASS = "select2-no-results";
    private static final String PARTICIPANT_WITH_ID_PATH = "//*[@id='select2-drop']/ul/li/div[contains(text(), '%s')]";
    private static final int TIME_10000 = 10000;

    private static final By PARTICIPANT_LIST = By.xpath("//*[@id='select2-drop']/ul/li[1]");
    private static final By PARTICIPANT_ID_DROPDOWN = By.id("s2id_subjectId");
    private static final By PRIME_VAC_DAY = By.id("primeVacDateInput");
    private static final By CLEAN_DATE_BUTTON = By.id("cancelBtn");
    private static final By PRINT_BUTTON = By.id("printBtn");
    private static final By SET_FIRST_DAY = By.linkText("1");
    private static final By SEARCH = By.id("s2id_autogen2_search");
    private static final By PRIME_VAC_FIRST_FOLLOW_UP = By.id("primeVacFirstFollowUp");

    public BookingAppClinicVisitSchedulePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public boolean findTestParticipant() throws InterruptedException {
        return findParticipantById(TEST_PARTICIPANT_ID);
    }

    public void clickButtonCleanDate() throws InterruptedException {
        clickWhenVisible(CLEAN_DATE_BUTTON);
    }

    public void clickOnPrimeVacDayDate() throws InterruptedException {
        clickWhenVisible(PRIME_VAC_DAY);
    }

    public String getPrimeVacDate() {
        return findElement(PRIME_VAC_DAY).getText();
    }

    public boolean checkIfPrimeVacFirstFollowUpCalculated() {
        return StringUtils.isNotBlank(findElement(PRIME_VAC_FIRST_FOLLOW_UP).getText());
    }

    public void clickOnFirstDayInCalendar() throws InterruptedException {
        clickWhenVisible(SET_FIRST_DAY);
    }

    public void clickPrintButton() throws InterruptedException {
        clickWhenVisible(PRINT_BUTTON);
    }

    private boolean findParticipantById(String participantId) throws InterruptedException {
        if (!waitForParticipantListToBeLoaded()) {
            return false;
        }

        clickOnDropDownParticipantId();
        clickWhenVisible(By.xpath(String.format(PARTICIPANT_WITH_ID_PATH, participantId)));
        return true;
    }

    private boolean waitForParticipantListToBeLoaded() throws InterruptedException {
        Long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < TIME_10000) {
            clickOnDropDownParticipantId();
            String elementClass = findElement(PARTICIPANT_LIST).getAttribute("class");

            findElement(SEARCH).sendKeys(Keys.ESCAPE);

            if (elementClass == null || !elementClass.contains(NO_RESULTS_CLASS)) {
                return true;
            }

            sleep500();
        }

        return false;
    }

    private void clickOnDropDownParticipantId() throws InterruptedException {
        clickWhenVisible(PARTICIPANT_ID_DROPDOWN);
    }
}
