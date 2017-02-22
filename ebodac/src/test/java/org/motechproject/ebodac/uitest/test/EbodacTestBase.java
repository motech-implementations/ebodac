package org.motechproject.ebodac.uitest.test;

import org.junit.Before;
import org.motechproject.ebodac.uitest.helper.EbodacTestProperties;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.LoginPage;
import org.motechproject.uitest.TestBase;

public class EbodacTestBase extends TestBase {
    private static final EbodacTestProperties TEST_PROPERTIES = EbodacTestProperties.instance();

    private LoginPage loginPage;

    @Before
    public void initLoginPage() {
        loginPage = new LoginPage(getDriver());
    }

    public HomePage login() {
        String user = getTestProperties().getUserName();
        String password = getTestProperties().getPassword();

        return login(user, password);
    }

    public HomePage loginAsAdmin() {
        String user = getTestProperties().getAdminUserName();
        String password = getTestProperties().getAdminPassword();

        return login(user, password);
    }

    public HomePage loginAsAnalyst() {
        String user = getTestProperties().getAnalystUserName();
        String password = getTestProperties().getAnalystPassword();

        return login(user, password);
    }

    public HomePage login(String username, String password) {
        loginPage.goToPage();
        assertPage(loginPage);
        HomePage homePage = loginPage.login(username, password);
        homePage.waitUntilBlockUiIsGone();
        return homePage;
    }

    protected static EbodacTestProperties getTestProperties() {
        return TEST_PROPERTIES;
    }
}
