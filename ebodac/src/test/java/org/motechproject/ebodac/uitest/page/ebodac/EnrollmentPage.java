package org.motechproject.ebodac.uitest.page.ebodac;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class EnrollmentPage extends EbodacPage {

    public static final String URL_PATH = "home#/ebodac/enrollment";

    private static final String TEST_PARTICIPANT_ID = "9990000004";

    private static final String ENROLL_SUCCESS_TITLE = "Enrolled";
    private static final String UNENROLL_SUCCESS_TITLE = "Unenrolled";

    private static final String EBODAC_ENROLLMENT_ADVANCED = "EBODAC - Enrollment Advanced";

    private static final By ENROLL_TEST_PARTICIPANT_BTN = By.xpath("//tr[.//td[contains(text(), '" + TEST_PARTICIPANT_ID + "')]]//td[6]/button[contains(@class, 'btn-success')]");
    private static final By UNENROLL_TEST_PARTICIPANT_BTN = By.xpath("//tr[.//td[contains(text(), '" + TEST_PARTICIPANT_ID + "')]]//td[6]/button[contains(@class, 'btn-danger')]");

    private static final By ENROLL_BTN = By.id("enroll1");
    private static final By UNENROLL_BTN = By.id("unenroll1");

    private static final By FIRST_ENROLLMENT_ROW = By.xpath("//table[@id='enrollmentTable']/tbody/tr[2]");
    private static final By ENROLLMENT_TITLE = By.xpath("//*[@id='main-content']/div/div/h4");

    private static final By LOOKUP_BUTTON = By.id("lookupDialogButton");
    private static final By LOOKUP_SELECT = By.id("lookupSelectBtn");
    private static final By FIND_BY_PARTICIPANT_ID = By.linkText("Find By Participant Id");
    private static final By LOOKUP_INPUT = By.xpath("//*[@id='lookup-dialog']/div[2]/div[2]/div/input");
    private static final By FIND_BUTTON = By.id("findBtn");

    private static final By AMOUNT_OF_RESULTS = By.xpath("//select[@title='Records per Page']");
    private static final By AMOUNT_OF_PAGES = By.id("sp_1_pageEnrollmentTable");
    private static final By NUMBER_OF_ACTUAL_PAGE = By.className("ui-paging-info");
    private static final By LAST_PAGE_BUTTON = By.id("last_pageEnrollmentTable");

    private static final By PARTICIPANT_ID_COLUMN = By.id("jqgh_enrollmentTable_subject");

    private static final By POPUP_OK = By.id("popup_ok");
    private static final By POPUP_MESSAGE = By.id("popup_message");
    private static final By POPUP_TITLE = By.id("popup_title");

    private static final By ENROLLMENT_TABLE_LOADING = By.id("load_enrollmentTable");

    public EnrollmentPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public void findTestParticipant() throws InterruptedException {
        clickWhenVisible(LOOKUP_BUTTON);
        clickWhenVisible(LOOKUP_SELECT);
        clickWhenVisible(FIND_BY_PARTICIPANT_ID);
        findElement(LOOKUP_INPUT).sendKeys(TEST_PARTICIPANT_ID);
        clickWhenVisible(FIND_BUTTON);
        waitUntilEnrolmentTableLoadingIsGone();
    }

    public boolean enrollParticipant() throws InterruptedException {
        clickEnroll();
        clickOK();
        String popupTitle = findElement(POPUP_TITLE).getText();
        clickOK();

        return popupTitle.contains(ENROLL_SUCCESS_TITLE);
    }

    public boolean unenrollParticipant() throws InterruptedException {
        clickUnenroll();
        clickOK();
        String popupTitle = findElement(POPUP_TITLE).getText();
        clickOK();

        return popupTitle.contains(UNENROLL_SUCCESS_TITLE);
    }

    public void clickEnroll() throws InterruptedException {
        clickWhenVisible(ENROLL_BTN);
    }

    public void clickUnenroll() throws InterruptedException {
        clickWhenVisible(UNENROLL_BTN);
    }

    public void clickOK() throws InterruptedException {
        clickWhenVisible(POPUP_OK);
    }

    public String getPopupMessage() {
        return findElement(POPUP_MESSAGE).getText();
    }

    public void selectFirstEnrollment() throws InterruptedException {
        clickWhenVisible(FIRST_ENROLLMENT_ROW);
    }

    public boolean isOnEnrollmentAdvancedScreen() {
        return EBODAC_ENROLLMENT_ADVANCED.equals(findElement(ENROLLMENT_TITLE).getText().trim());
    }

    public void changeAmountOfResultsShownOnPage() {
        Select dropdown = new Select(findElement(AMOUNT_OF_RESULTS));
        dropdown.selectByVisibleText("10");
        waitUntilEnrolmentTableLoadingIsGone();
    }

    public int getAmountPagesOfTable() {
        return Integer.parseInt(findElement(AMOUNT_OF_PAGES).getText());
    }

    public String getNumberOfActualPage() {
        return findElement(NUMBER_OF_ACTUAL_PAGE).getText();
    }

    public void goToLastPageInTable() throws InterruptedException {
        clickWhenVisible(LAST_PAGE_BUTTON);
        waitUntilEnrolmentTableLoadingIsGone();
    }

    public void sortByParticipantId() throws InterruptedException {
        clickWhenVisible(PARTICIPANT_ID_COLUMN);
        waitUntilEnrolmentTableLoadingIsGone();
    }

    public boolean enrollTestParticipant() throws InterruptedException {
        clickWhenVisible(ENROLL_TEST_PARTICIPANT_BTN);
        clickOK();
        String popupTitle = findElement(POPUP_TITLE).getText();
        clickOK();

        return popupTitle.contains(ENROLL_SUCCESS_TITLE);
    }

    public boolean unenrollTestParticipant() throws InterruptedException {
        clickWhenVisible(UNENROLL_TEST_PARTICIPANT_BTN);
        clickOK();
        String popupTitle = findElement(POPUP_TITLE).getText();
        clickOK();

        return popupTitle.contains(UNENROLL_SUCCESS_TITLE);
    }

    private void waitUntilEnrolmentTableLoadingIsGone() {
        waitForElementToBeHidden(ENROLLMENT_TABLE_LOADING);
    }
}
