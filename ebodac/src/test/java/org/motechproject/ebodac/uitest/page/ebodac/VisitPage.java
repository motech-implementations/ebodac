package org.motechproject.ebodac.uitest.page.ebodac;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class VisitPage extends EbodacPage {

    private static final String URL_PATH = "/home#/ebodac/visits";

    private static final String TEST_PARTICIPANT_ID = "9990000099";
    private static final String INNER_HTML = "innerHTML";
    private static final String NO_RECORDS_TO_VIEW = "No records to view";

    private static final By TEST_VISIT = By.xpath("//table[@id='instancesTable']/tbody/tr/td[@title='Second Long-term Follow-up visit']");
    private static final By TEST_VISIT_PLANNED_DATE = By.xpath("//table[@id='instancesTable']/tbody/tr[.//td[@title='Second Long-term Follow-up visit']]/td[@aria-describedby='instancesTable_motechProjectedDate']");
    private static final By VISIT_ROW = By.xpath("//table[@id='instancesTable']/tbody/tr[2]");
    private static final By LOOKUP_DIALOG_BUTTON = By.id("lookupDialogButton");
    private static final By SELECT_LOOKUP_BUTTON = By.xpath("//*[@id='lookup-dialog']/div[2]/div[1]/div/button");
    private static final By FIND_BY_PARTICIPANT_ID = By.linkText("Find By Participant Id");
    private static final By LOOKUP_INPUT = By.xpath("//*[@id='lookup-dialog']/div[2]/div[2]/div/input");
    private static final By FIND_BUTTON = By.xpath("//*[@id='lookup-dialog']/div[2]/div[3]/div/button[2]");
    private static final By NUMBER_OF_ROWS = By.xpath("//*[@id='pageInstancesTable_left']/div");

    public VisitPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public VisitEditPage editFirstVisit() throws InterruptedException {
        clickWhenVisible(VISIT_ROW);
        waitUntilBlockUiIsGone();
        return new VisitEditPage(getDriver());
    }

    public void findTestVisit() throws InterruptedException {
        findParticipantById(TEST_PARTICIPANT_ID);
    }

    public VisitEditPage editTestVisit() throws InterruptedException {
        clickWhenVisible(TEST_VISIT);
        waitUntilBlockUiIsGone();
        return new VisitEditPage(getDriver());
    }

    public String getTestVisitPlannedDate() {
        return findElement(TEST_VISIT_PLANNED_DATE).getText().trim();
    }

    public boolean findByParticipantIdAndCheckResults(String participantId) throws InterruptedException {
        findParticipantById(participantId);
        return !findElement(NUMBER_OF_ROWS).getAttribute(INNER_HTML).contains(NO_RECORDS_TO_VIEW);
    }

    private void findParticipantById(String participantId) throws InterruptedException {
        waitUntilBlockUiIsGone();
        waitUntilInstancesTableLoadingIsGone();
        clickWhenVisible(LOOKUP_DIALOG_BUTTON);
        clickWhenVisible(SELECT_LOOKUP_BUTTON);
        clickWhenVisible(FIND_BY_PARTICIPANT_ID);
        findElement(LOOKUP_INPUT).sendKeys(participantId);
        clickWhenVisible(FIND_BUTTON);
        waitUntilInstancesTableLoadingIsGone();
    }
}
