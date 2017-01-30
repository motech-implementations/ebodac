package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.ebodac.ParticipantEditPage;
import org.motechproject.ebodac.uitest.page.ebodac.ParticipantPage;
import org.openqa.selenium.By;

import static org.junit.Assert.assertTrue;

public class LanguageDisappearUiTest extends EbodacTestBase {

    private static final By LANGUAGE_BUTTON = By.xpath("//ng-form[@name='language']/div[1]/div/button");
    private static final By LANGUAGE_KRIO = By.xpath("//ng-form[@name='language']/div[1]/div/ul/li[2]/a/label");
    private static final By LANGUAGE_SUSU = By.xpath("//ng-form[@name='language']/div[1]/div/ul/li[3]/a/label");
    private static final By LANGUAGE_LIMBA = By.xpath("//ng-form[@name='language']/div[1]/div/ul/li[4]/a/label");
    private static final By LANGUAGE_ENGLISH = By.xpath("//ng-form[@name='language']/div[1]/div/ul/li[5]/a/label");
    private static final By LANGUAGE_TEMNE = By.xpath("//ng-form[@name='language']/div[1]/div/ul/li[6]/a/label");

    private ParticipantPage participantPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        EbodacPage ebodacPage = homePage.goToEbodacModule();

        participantPage = ebodacPage.goToParticipants();
    }

    @Test //EBODAC-538
    public void languagedisappearTest() throws InterruptedException {
        ParticipantEditPage participantEditPage = participantPage.editFirstParticipant();

        participantEditPage.clickWhenVisible(LANGUAGE_BUTTON);
        assertTrue(participantEditPage.findElement(LANGUAGE_KRIO).isDisplayed());
        assertTrue(participantEditPage.findElement(LANGUAGE_SUSU).isDisplayed());
        assertTrue(participantEditPage.findElement(LANGUAGE_LIMBA).isDisplayed());
        assertTrue(participantEditPage.findElement(LANGUAGE_ENGLISH).isDisplayed());
        assertTrue(participantEditPage.findElement(LANGUAGE_TEMNE).isDisplayed());
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
