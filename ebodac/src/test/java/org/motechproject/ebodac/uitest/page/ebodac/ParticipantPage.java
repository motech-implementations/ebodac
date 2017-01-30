package org.motechproject.ebodac.uitest.page.ebodac;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ParticipantPage extends EbodacPage {

    public static final String URL_PATH = "/home#/ebodac/subjects";

    private static final String TEST_PARTICIPANT_ID = "9990000004";

    private static final By PARTICIPANT_ROW = By.xpath("//*[@id='instancesTable']/tbody/tr[2]");
    private static final By LOOKUP_DIALOG_BUTTON = By.id("lookupDialogButton");
    private static final By SELECT_LOOKUP_BUTTON = By.xpath("//*[@id='lookup-dialog']/div[2]/div[1]/div/button");
    private static final By FIND_BY_PARTICIPANT_ID = By.linkText("Find By Participant Id");
    private static final By LOOKUP_INPUT = By.xpath("//*[@id='lookup-dialog']/div[2]/div[2]/div/input");
    private static final By FIND_BUTTON = By.xpath("//*[@id='lookup-dialog']/div[2]/div[3]/div/button[2]");
    private static final By PHONE_NUMBER_FIELD = By.xpath("//td[@aria-describedby='instancesTable_phoneNumber']");
    private static final By NUMBER_OF_ROWS = By.xpath("//*[@id='pageInstancesTable_left']/div");

    public ParticipantPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public ParticipantEditPage editFirstParticipant() throws InterruptedException {
        clickWhenVisible(PARTICIPANT_ROW);
        waitUntilBlockUiIsGone();
        return new ParticipantEditPage(getDriver());
    }

    public void findTestParticipant() throws InterruptedException {
        findParticipantById(TEST_PARTICIPANT_ID);
    }

    /**
     * @return String Participant's phone number
     */
    public String getPhoneNumber() {
        return findElement(PHONE_NUMBER_FIELD).getText().trim();
    }

    public boolean findByParticipantIdAndCheckResults(String participantId) throws InterruptedException {
        findParticipantById(participantId);
        return !findElement(NUMBER_OF_ROWS).getAttribute("innerHTML").contains("No records to view");
    }

    private void findParticipantById(String id) throws InterruptedException {
        clickWhenVisible(LOOKUP_DIALOG_BUTTON);
        clickWhenVisible(SELECT_LOOKUP_BUTTON);
        clickWhenVisible(FIND_BY_PARTICIPANT_ID);
        findElement(LOOKUP_INPUT).sendKeys(id);
        clickWhenVisible(FIND_BUTTON);
        waitUntilInstancesTableLoadingIsGone();
    }
}
