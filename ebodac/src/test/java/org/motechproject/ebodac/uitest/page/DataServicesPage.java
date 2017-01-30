package org.motechproject.ebodac.uitest.page;

import org.motechproject.ebodac.domain.enums.VisitType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class DataServicesPage extends HomePage {

    public static final String URL_PATH = "/home#/mds/dataBrowser";

    private static final String FIRST_TEST_PARTICIPANT_ID = "9990000002";
    private static final String SECOND_TEST_PARTICIPANT_ID = "9990000003";
    private static final String LOOKUP_NAME_PATH = "//*[@id='lookup-dialog']/div[2]/div[1]/div/ul/li/a[contains(text(), '%s')]";
    private static final String LOOKUP_BY_PARTICIPANT_ID_AND_VISIT_TYPE = "Find By Participant Id And Visit Type";

    private static final By VISIT_BOOKING_DETAILS_ENTITY = By.xpath("//div[text()='VisitBookingDetails']");
    private static final By PARTICIPANT_ENTITY = By.xpath("//div[text()='Participant']");
    private static final By VISIT_ENTITY = By.xpath("//div[text()='Visit']");

    private static final By LOOKUP_BUTTON = By.id("lookupDialogButton");
    private static final By LOOKUP_SELECT = By.xpath("//*[@id='lookup-dialog']/div[2]/div[1]/div/button");
    private static final By FIND_BUTTON = By.xpath("//*[@id='lookup-dialog']/div[2]/div[4]/div/button");
    private static final By FIRST_ROW = By.xpath("//*[@id='instancesTable']/tbody/tr[2]");
    private static final By FIRST_INPUT = By.xpath("//*[@id='lookup-dialog']/div[2]/div[2]/div/input");
    private static final By SECOND_SELECT = By.xpath("//*[@id='lookup-dialog']/div[2]/div[3]/div/select");

    private static final By SAVE_BUTTON = By.xpath("//*[@id='dataBrowser']/div/div/div/ng-form/div[2]/div[1]/button[1]");
    private static final By BOOKING_ACTUAL_DATE_FIELD = By.xpath("//ng-form[@name='bookingActualDate']/div[1]/input");
    private static final By BOOKING_PLANNED_DATE_FIELD = By.xpath("//ng-form[@name='bookingPlannedDate']/div[1]/input");

    public DataServicesPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    @Override
    public void goToPage() {
    }

    public  void openEntity(By by) throws InterruptedException {
        clickWhenVisible(by);
        waitUntilBlockUiIsGone();
        waitUntilInstancesTableLoadingIsGone();
    }

    public void openVisitBookingDetailsEntity() throws InterruptedException {
        openEntity(VISIT_BOOKING_DETAILS_ENTITY);
    }

    public void openParticipantEntity() throws InterruptedException {
        openEntity(PARTICIPANT_ENTITY);
    }

    public void openVisitsEntity() throws InterruptedException {
        openEntity(VISIT_ENTITY);
    }

    public void chooseLookupByName(String name) throws InterruptedException {
        clickWhenVisible(LOOKUP_BUTTON);
        clickWhenVisible(LOOKUP_SELECT);
        clickWhenVisible(By.xpath(String.format(LOOKUP_NAME_PATH, name)));
    }

    public void findByParticipantIdAndVisitType(String participantId, VisitType visitType) throws InterruptedException {
        findElement(FIRST_INPUT).sendKeys(participantId);
        Select select = new Select(findElement(SECOND_SELECT));
        select.selectByVisibleText(visitType.getMotechValue());
        clickFindButton();
    }

    public void clickFindButton() throws InterruptedException {
        clickWhenVisible(FIND_BUTTON);
        waitUntilInstancesTableLoadingIsGone();
    }

    public void editFirstEntity() throws InterruptedException {
        clickWhenVisible(FIRST_ROW);
        waitUntilBlockUiIsGone();
    }

    public void saveEntity() throws InterruptedException {
        clickWhenVisible(SAVE_BUTTON);
        waitUntilBlockUiIsGone();
        waitUntilInstancesTableLoadingIsGone();
    }

    public void resetFirstTestVisitBookingDetails() throws InterruptedException {
        openVisitBookingDetailsEntity();
        chooseLookupByName(LOOKUP_BY_PARTICIPANT_ID_AND_VISIT_TYPE);
        findByParticipantIdAndVisitType(FIRST_TEST_PARTICIPANT_ID, VisitType.SCREENING);
        editFirstEntity();
        findElement(BOOKING_ACTUAL_DATE_FIELD).clear();
        saveEntity();

        chooseLookupByName(LOOKUP_BY_PARTICIPANT_ID_AND_VISIT_TYPE);
        findByParticipantIdAndVisitType(FIRST_TEST_PARTICIPANT_ID, VisitType.PRIME_VACCINATION_DAY);
        editFirstEntity();
        findElement(BOOKING_PLANNED_DATE_FIELD).clear();
        saveEntity();
    }

    public void resetSecondTestVisitBookingDetails() throws InterruptedException {
        openVisitBookingDetailsEntity();
        chooseLookupByName(LOOKUP_BY_PARTICIPANT_ID_AND_VISIT_TYPE);
        findByParticipantIdAndVisitType(SECOND_TEST_PARTICIPANT_ID, VisitType.PRIME_VACCINATION_DAY);
        editFirstEntity();
        WebElement element = findElement(BOOKING_PLANNED_DATE_FIELD);
        element.clear();
        element.sendKeys("1016-10-28");
        saveEntity();
    }
}
