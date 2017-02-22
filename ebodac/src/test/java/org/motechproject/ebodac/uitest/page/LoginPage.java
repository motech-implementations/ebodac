package org.motechproject.ebodac.uitest.page;

import org.motechproject.uitest.page.AbstractBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends AbstractBasePage {

    public static final String LOGIN_PATH = "/module/server/login";

    private static final By USERNAME = By.name("j_username");
    private static final By PASSWORD = By.name("j_password");
    private static final By LOGIN = By.cssSelector("input.btn.btn-primary");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public HomePage login(String user, String password) {
        this.waitForElement(USERNAME);
        this.setTextToFieldNoEnter(USERNAME, user);
        getLogger().debug("User name: " + user + ", password: " + password);
        this.setTextToFieldNoEnter(PASSWORD, password);
        try {
            this.clickWhenVisible(LOGIN);
        } catch (InterruptedException e) {
            getLogger().error(e.getMessage(), e);
        }
        this.waitForElement(By.cssSelector("span.ng-binding"));
        getLogger().debug("User logged in");
        return new HomePage(this.getDriver());
    }

    public String expectedUrlPath() {
        return LOGIN_PATH;
    }

    public void goToPage() {
        this.getDriver().get(this.getMotechUrl() + this.expectedUrlPath());
    }
}
