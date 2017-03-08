package org.motechproject.ebodac.uitest.page.ebodac;

import org.motechproject.ebodac.domain.enums.Language;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ParticipantEditPage extends EbodacPage {

    public static final String URL_PATH = "/home#/mds/dataBrowser";

    private static final By SELECTED_LANGUAGE = By.xpath("//ng-form[@name='language']/div[1]/div/button");
    private static final By PHONE_NUMBER_FIELD = By.id("phoneNumberForm");
    private static final By NAME_FIELD = By.xpath("//ng-form[@name='name']/div/input");
    private static final By HOUSEHOLD_NAME_FIELD = By.xpath("//ng-form[@name='householdName']/div/input");
    private static final By HEAD_OF_HOUSEHOLD_FIELD = By.xpath("//ng-form[@name='headOfHousehold']/div/input");
    private static final By SAVE_BUTTON = By.xpath("//*[@id='dataBrowser']/div[1]/div/div/ng-form/div[2]/div[1]/button[1]");
    private static final By CONFIRMATION_BUTTON = By.id("confirmSaveBtn");

    private static final By VISIT_TABLE = By.id("visitTable");
    private static final By HIDDEN_BUTTONS = By.xpath("//div[@ng-include='loadEditValueForm(field)']/table/tbody/tr/*/button");

    public ParticipantEditPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    @Override
    public void goToPage() {
    }

    public void changePhoneNumber(String number) {
        WebElement phoneNumberField = findElement(PHONE_NUMBER_FIELD);
        phoneNumberField.clear();
        sleep500();
        phoneNumberField.clear();
        phoneNumberField.sendKeys(number);
    }

    public boolean isNameEditable() {
        return isElementEditable(NAME_FIELD);
    }

    public boolean isHouseholdNameEditable() {
        return isElementEditable(HOUSEHOLD_NAME_FIELD);
    }

    public boolean isHeadOfHouseholdEditable() {
        return isElementEditable(HEAD_OF_HOUSEHOLD_FIELD);
    }

    public void saveParticipant() throws InterruptedException {
        clickWhenVisible(SAVE_BUTTON);
        clickWhenVisible(CONFIRMATION_BUTTON);

        waitUntilBlockUiIsGone();
        waitUntilInstancesTableLoadingIsGone();
    }

    public boolean areButtonsHidden() {
        return !isElementPresent(HIDDEN_BUTTONS);
    }

    public void changeLanguage(Language language) throws InterruptedException {
        clickWhenVisible(SELECTED_LANGUAGE);
        clickWhenVisible(By.linkText(language.name()));
        sleep500();
    }

    /**
     * @return Language Participant's language
     */
    public Language getLanguage() {
        return Language.valueOf(findElement(SELECTED_LANGUAGE).getText().trim());
    }

    public boolean isVisitInTabularFormat() {
        return isElementPresent(VISIT_TABLE);
    }
}
