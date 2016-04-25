package org.motechproject.ebodac.uitest.page;

import org.motech.page.AbstractBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static java.lang.Thread.sleep;


public class BookingAppClinicVisitSchedulePage extends AbstractBasePage {

    public static final String URL_PATH = "/#/bookingApp/capacityInfo/";

    static final By PRIME_VAC_FIRST_FOLLOW_UP_VALUE = By.cssSelector("#main-content > div > div > table > tbody > tr > td:nth-child(4)");

    static final By PARTICIPANT_ID_DROPDOWN = By.id("s2id_subjectId");

    static final By FIRST_PARTICIPANT_IN_DROPDOWN = By.cssSelector("#select2-result-label-3");

    static final By CLEAN_DATE_BUTTON = By.xpath("//*[@id=\"main-content\"]/div/div/button[3]");

    static final By PRIME_VAC_DAY_DATE = By.cssSelector("#primeVacDateInput");

    static final By SET_FIRST_DAY = By.linkText("1");

    public BookingAppClinicVisitSchedulePage(WebDriver driver) {
        super(driver);
    }

    public void clickOnDropDownParticipantId() throws InterruptedException {
        sleep(500);
        clickWhenVisible(PARTICIPANT_ID_DROPDOWN);
        sleep(500);
        findElement(FIRST_PARTICIPANT_IN_DROPDOWN).click();
    }

    public void clickButtonCleanDate() throws InterruptedException {
        clickWhenVisible(CLEAN_DATE_BUTTON);
    }

    public void ClickOnPrimeVacDayDate() throws InterruptedException {
        clickWhenVisible(PRIME_VAC_DAY_DATE);
    }

    public void ClickOnFirstDayInCalendar() throws InterruptedException {
        clickWhenVisible(SET_FIRST_DAY);
    }

    public String AssertIfPrimeVacFirstFollowUpIsEmpty() throws InterruptedException {

        WebElement element = findElement(PRIME_VAC_FIRST_FOLLOW_UP_VALUE);
        String textt = element.getText();
        return textt;
    }


    @Override
    public String expectedUrlPath() {
        return URL_ROOT + URL_PATH;
    }
}
