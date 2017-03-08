package org.motechproject.ebodac.uitest.page.ebodac.reports;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.motechproject.ebodac.domain.enums.VisitType;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.junit.Assert.assertTrue;

public class DailyClinicVisitScheduleReportPage extends BaseReportPage {

    public static final String URL_PATH = "/home#/ebodac/reports/dailyClinicVisitScheduleReport";

    private static final String FIND_BY_PARTICIPANT_NAME = "Find By Participant Name";
    private static final String FIND_BY_VISIT_TYPE = "Find By Type";
    private static final String FIND_BY_PARTICIPANT_ID = "Find By Participant Id";
    private static final String FIND_BY_PARTICIPANT_ADDRESS = "Find By Participant Address";
    private static final String FIND_BY_VISIT_PLANNED_DATE_RANGE = "Find By Planned Visit Date Range";
    private static final String FIND_BY_VISIT_PLANNED_DATE_RANGE_AND_TYPE = "Find By Planned Visit Date Range And Type";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    private static final By RECORD_NAME = By.xpath("//*[@id='reportTable']/tbody/tr[@id='1']/td[@aria-describedby='reportTable_subjectName']");
    private static final By RECORD_TYPE = By.xpath("//*[@id='reportTable']/tbody/tr[@id='1']/td[@aria-describedby='reportTable_type']");
    private static final By RECORD_ID = By.xpath("//*[@id='reportTable']/tbody/tr[@id='1']/td[@aria-describedby='reportTable_subject']");
    private static final By RECORD_ADDRESS = By.xpath("//*[@id='reportTable']/tbody/tr[@id='1']/td[@aria-describedby='reportTable_subjectAddress']");
    private static final By RECORD_DATE = By.xpath("//*[@id='reportTable']/tbody/tr[@id='1']/td[@aria-describedby='reportTable_motechProjectedDate']");

    private static final By LOOKUP_FIRST_INPUT = By.xpath("//*[@id='lookup-dialog']/div[2]/div[2]/div/input");
    private static final By LOOKUP_FIRST_SELECT = By.xpath("//*[@id='lookup-dialog']/div[2]/div[2]/div/select");
    private static final By LOOKUP_SECOND_SELECT = By.xpath("//*[@id='lookup-dialog']/div[2]/div[3]/div/select");
    private static final By LOOKUP_RANGE_FROM_INPUT = By.xpath("//*[@id='lookup-dialog']/div[2]/div[2]/div/div[1]/input");
    private static final By LOOKUP_RANGE_TO_INPUT = By.xpath("//*[@id='lookup-dialog']/div[2]/div[2]/div/div[2]/input");

    public DailyClinicVisitScheduleReportPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    public void findByParticipantNameAndCheckResult(String name) throws InterruptedException {
        selectLookup(FIND_BY_PARTICIPANT_NAME);
        findElement(LOOKUP_FIRST_INPUT).sendKeys(name);
        clickFind();

        assertTrue(findElement(RECORD_NAME).getText().toLowerCase().contains(name.toLowerCase()));
    }

    public void findByVisitTypeAndCheckResult(VisitType type) throws InterruptedException {
        selectLookup(FIND_BY_VISIT_TYPE);
        Select select = new Select(findElement(LOOKUP_FIRST_SELECT));
        select.selectByVisibleText(type.getMotechValue());
        clickFind();

        assertTrue(findElement(RECORD_TYPE).getText().contains(type.getMotechValue()));
    }

    public void findByParticipantIdAndCheckResult(String id) throws InterruptedException {
        selectLookup(FIND_BY_PARTICIPANT_ID);
        findElement(LOOKUP_FIRST_INPUT).sendKeys(id);
        clickFind();

        assertTrue(findElement(RECORD_ID).getText().contains(id));
    }

    public void findByParticipantAddressAndCheckResult(String address) throws InterruptedException {
        selectLookup(FIND_BY_PARTICIPANT_ADDRESS);
        findElement(LOOKUP_FIRST_INPUT).sendKeys(address);
        clickFind();

        assertTrue(findElement(RECORD_ADDRESS).getText().toLowerCase().contains(address.toLowerCase()));
    }

    public void findByVisitPlannedDateRangeAndCheckResult(String from, String to) throws InterruptedException {
        selectLookup(FIND_BY_VISIT_PLANNED_DATE_RANGE);
        WebElement fromInput = findElement(LOOKUP_RANGE_FROM_INPUT);
        fromInput.sendKeys(from);
        fromInput.sendKeys(Keys.ESCAPE);
        WebElement toInput = findElement(LOOKUP_RANGE_TO_INPUT);
        toInput.sendKeys(to);
        toInput.sendKeys(Keys.ESCAPE);
        clickFind();

        assertTrue(isDateInRange(findElement(RECORD_DATE).getText(), from, to));
    }

    public void findByVisitPlannedDateRangeAndTypeAndCheckResult(String from, String to, VisitType type) throws InterruptedException {
        selectLookup(FIND_BY_VISIT_PLANNED_DATE_RANGE_AND_TYPE);
        WebElement fromInput = findElement(LOOKUP_RANGE_FROM_INPUT);
        fromInput.sendKeys(from);
        fromInput.sendKeys(Keys.ESCAPE);
        WebElement toInput = findElement(LOOKUP_RANGE_TO_INPUT);
        toInput.sendKeys(to);
        toInput.sendKeys(Keys.ESCAPE);
        Select select = new Select(findElement(LOOKUP_SECOND_SELECT));
        select.selectByVisibleText(type.getMotechValue());
        clickFind();

        assertTrue(isDateInRange(findElement(RECORD_DATE).getText(), from, to));
        assertTrue(findElement(RECORD_TYPE).getText().contains(type.getMotechValue()));
    }

    private boolean isDateInRange(String dateStr, String from, String to) {
        if (dateStr == null || dateStr.isEmpty()) {
            return false;
        }

        LocalDate date = LocalDate.parse(dateStr, DateTimeFormat.forPattern(YYYY_MM_DD));
        LocalDate min = from == null ? null : LocalDate.parse(from, DateTimeFormat.forPattern(YYYY_MM_DD));
        LocalDate max = to == null ? null : LocalDate.parse(to, DateTimeFormat.forPattern(YYYY_MM_DD));

        if (min != null && date.isBefore(min)) {
            return false;
        }

        if (max != null && date.isAfter(max)) {
            return false;
        }

        return true;
    }
}
