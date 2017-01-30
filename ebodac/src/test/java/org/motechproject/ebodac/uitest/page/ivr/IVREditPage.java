package org.motechproject.ebodac.uitest.page.ivr;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class IVREditPage extends IVRPage {

    private static final String URL_PATH = "/home#/mds/dataBrowser";

    private static final By SAVE_BUTTON = By.className("btn-primary");

    public IVREditPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    @Override
    public void goToPage() {
    }

    public boolean isSaveButtonVisible() {
        WebElement saveButton = findElement(SAVE_BUTTON);
        return saveButton.isDisplayed();
    }
}
