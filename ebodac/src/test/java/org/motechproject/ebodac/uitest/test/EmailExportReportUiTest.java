package org.motechproject.ebodac.uitest.test;

import org.motechproject.uitest.page.LoginPage;
import org.motechproject.uitest.TestBase;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.helper.TestParticipant;
import org.motechproject.ebodac.uitest.helper.UITestHttpClientHelper;

import org.motechproject.ebodac.uitest.page.EBODACPage;
import org.motechproject.ebodac.uitest.page.EmailExportReportPage;

import org.motechproject.ebodac.uitest.page.HomePage;
import static org.junit.Assert.assertTrue;

public class EmailExportReportUiTest extends TestBase {

    private static final String TITLE = "title";
    private static final String RECIPIENT = "recipient";
    private static final String SUBJECT = "subject";
    private static final String CONTENT = "content";
    private static final String ENTITY = "entity";
    private static final String ENTITYFIELD = "entityfield";
    private static final String TESTEMAIL = "testemail";
    private static final int SLEEP_2SEG = 2000;
    private static final int SLEEP_4SEG = 4000;
    private static final long SLEEP_12SEG = 12000;
    private static final CharSequence LOCAL_TEST_MACHINE = "localhost";
    private static final Map<String, String> MAPREPFIELDS = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;
        {
            put(TITLE, "Test Report");
            put(RECIPIENT, "Test123");
            put(TESTEMAIL, "test123@test.com");
            put(SUBJECT, "Test Email Subject");
            put(CONTENT, "Test Content Mail");
            put(ENTITY, "Participant");
            put(ENTITYFIELD, "Participant Id");
        }
    };


    private LoginPage loginPage;
    private HomePage homePage;
    private EBODACPage ebodacPage;
    private String url = "";
    private EmailExportReportPage emailExportReportPage;

    @Before
    public void setUp() throws Exception {
        try {
            String user = getTestProperties().getUserName();
            String password = getTestProperties().getPassword();
            loginPage = new LoginPage(getDriver());
            homePage = new HomePage(getDriver());
            ebodacPage = new EBODACPage(getDriver());
            emailExportReportPage = new EmailExportReportPage(getDriver());
            url = getServerUrl();
            if (url.contains(LOCAL_TEST_MACHINE)) {
                UITestHttpClientHelper httpClientHelper = new UITestHttpClientHelper(url);
                httpClientHelper.addParticipant(new TestParticipant(), user, password);
                loginPage.goToPage();
                loginPage.login(user, password);
            } else if (homePage.expectedUrlPath() != currentPage().urlPath()) {
                loginPage.goToPage();
                loginPage.login(user, password);
            }
        } catch (NullPointerException e) {
            getLogger().error("setup - NPE. Reason : " + e.getLocalizedMessage(), e);
        } catch (Exception e) {
            getLogger().error("setup - Exc. Reason : " + e.getLocalizedMessage(), e);
        }
    }

    @Test
    public void checkTheCreationReportTest() throws Exception {
        try {
            // We access to the edit page of the participant
            homePage.openEBODACModule();
            ebodacPage.sleep(SLEEP_12SEG);
            ebodacPage.showEmailExport();
            emailExportReportPage.sleep(SLEEP_2SEG);
            emailExportReportPage.closeReport();
            emailExportReportPage.sleep(SLEEP_4SEG);
            emailExportReportPage.clickAddNewReport();
            emailExportReportPage.sleep(SLEEP_2SEG);
            emailExportReportPage.setTitleReport(MAPREPFIELDS.get(TITLE));

            if (!emailExportReportPage.existsTestEmailAccount(MAPREPFIELDS.get(RECIPIENT))) {
                emailExportReportPage.sleep(SLEEP_2SEG);
                emailExportReportPage.createRecipient(MAPREPFIELDS.get(RECIPIENT), MAPREPFIELDS.get(TESTEMAIL));
            }
            emailExportReportPage.sleep(SLEEP_2SEG);
            emailExportReportPage.setRecipents(MAPREPFIELDS.get(RECIPIENT));
            emailExportReportPage.sleep(SLEEP_2SEG);
            emailExportReportPage.setEmailSubject(MAPREPFIELDS.get(SUBJECT));
            emailExportReportPage.sleep(SLEEP_2SEG);
            emailExportReportPage.setMessageContent(MAPREPFIELDS.get(CONTENT));
            emailExportReportPage.sleep(SLEEP_2SEG);
            emailExportReportPage.setEntity(MAPREPFIELDS.get(ENTITY));
            emailExportReportPage.sleep(SLEEP_4SEG);
            emailExportReportPage.setEntityField(MAPREPFIELDS.get(ENTITYFIELD));
            emailExportReportPage.sleep(SLEEP_2SEG);
            emailExportReportPage.setScheduleTime();
            emailExportReportPage.sleep(SLEEP_4SEG);
            assertTrue(emailExportReportPage.saveReport());
            // We remove the report
            emailExportReportPage.sleep(SLEEP_4SEG);
            emailExportReportPage.deleteReport();

        } catch (InterruptedException e) {
            getLogger().error("changeLanguageTest - IEx. Reason : " + e.getLocalizedMessage(), e);
        } catch (Exception e) {
            getLogger().error("changeLanguageTest - Exc. Reason : " + e.getLocalizedMessage(), e);
        }
    }

    @After
    public void tearDown() throws Exception {
        try {
            // Logout
            logout();
        } catch (InterruptedException e) {
            getLogger().error("tearDown - IEx. Reason : " + e.getLocalizedMessage(), e);
        } catch (Exception e) {
            getLogger().error("tearDown - Exc. Reason : " + e.getLocalizedMessage(), e);
        }
    }

}
