package org.motechproject.ebodac.uitest.page.bookingapp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BookingAppUnscheduledVisitPage extends BookingAppPage {

    public static final String URL_PATH = "/home#/bookingApp/unscheduledVisit";
    private static final String TEST_PARTICIPANT_ID = "9990000004";

    private static final By TEST_PARTICIPANT = By.xpath("//*[@id='select2-drop']/ul/li/div[contains(text(), '" + TEST_PARTICIPANT_ID + "')]");
    private static final By BOOK_UNSCHEDULED_VISIT_BUTTON = By.id("addUnscheduledBtn");
    private static final By PARTICIPANT_DROP_DOWN = By.id("s2id_participantSelect");
    private static final By UNSCHEDULED_VISIT_DATE_PICKER = By.id("dateInput");
    private static final By UNSCHEDULED_VISIT_DATE_PICKER_TODAY_BUTTON = By.xpath("//button[@data-handler='today']");
    private static final By UNSCHEDULED_VISIT_DATE_PICKER_DONE_BUTTON = By.xpath("//button[@data-handler='hide']");
    private static final By UNSCHEDULED_VISIT_START_TIME_INPUT = By.id("startTimeInput");
    private static final By UNSCHEDULED_VISIT_SAVE_BUTTON = By.id("saveBtn");
    private static final By CONFIRM_MODAL_MESSAGE = By.id("popup_message");
    private static final By CONFIRM_MODAL_OK_BUTTON = By.id("popup_ok");
    private static final By SUCCESSFULLY_MODAL_MESSAGE = By.id("successMsg");
    private static final By SUCCESSFULLY_MODAL_CLOSE_BUTTON = By.id("closeBtn");


    public BookingAppUnscheduledVisitPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public void clickOnBookUnscheduledVisitButton() throws InterruptedException {
        clickWhenVisible(BOOK_UNSCHEDULED_VISIT_BUTTON);
    }

    public void clickOnParticipantIdDropDownAndChooseTestParticipant() throws InterruptedException {
        clickWhenVisible(PARTICIPANT_DROP_DOWN);
        clickWhenVisible(TEST_PARTICIPANT);
    }

    public void setDatesForUnscheduledVisit() throws InterruptedException {
        clickWhenVisible(UNSCHEDULED_VISIT_DATE_PICKER);
        clickWhenVisible(UNSCHEDULED_VISIT_DATE_PICKER_TODAY_BUTTON);
        clickWhenVisible(UNSCHEDULED_VISIT_DATE_PICKER_DONE_BUTTON);
        findElement(UNSCHEDULED_VISIT_START_TIME_INPUT).sendKeys("23:59");
        clickWhenVisible(UNSCHEDULED_VISIT_DATE_PICKER_DONE_BUTTON);
    }

    public void clickOnButtonToSaveUnscheduledVisit() throws InterruptedException {
        clickWhenVisible(UNSCHEDULED_VISIT_SAVE_BUTTON);
    }

    public String checkIfConfirmModalIsVisible() {
        return findElement(CONFIRM_MODAL_MESSAGE).getText();
    }

    public void clickOnButtonToConfirmBookUnscheduledVisit() throws InterruptedException {
        clickWhenVisible(CONFIRM_MODAL_OK_BUTTON);
    }

    public String checkIfVisitIsCorrectlySaved() {
        return findElement(SUCCESSFULLY_MODAL_MESSAGE).getText();
    }

    public void clickOnButtonToCloseModal() throws InterruptedException {
        clickWhenVisible(SUCCESSFULLY_MODAL_CLOSE_BUTTON);
    }
}
