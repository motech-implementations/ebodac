package org.motechproject.ebodac.uitest.page;

import java.util.List;
import org.motechproject.uitest.page.AbstractBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class EmailExportReportPage extends AbstractBasePage {

    private static final By XPATH_SELECT_RECIPIENTS = By.xpath(".//ng-form/div/div[2]/div/select");
    private static final String INNER_HTML = "innerHTML";

    private static final By XPATH_MODAL = By.xpath("//*[@id='addRecipientModal']");
    private static final By XPATH_ADD_RECIPIENT_BUTTON = By.xpath(".//ng-form/div/div[2]/div[2]/button");
    private static final By XPATH_MODAL_OK_BUTTON = By.xpath(".//div[2]/div/div[3]/button[3]");
    private static final By XPATH_MODAL_SAVE_BUTTON = By.xpath(".//div[2]/div/div[3]/button[1]");
    private static final By XPATH_MODAL_EMAIL = By.xpath(".//*[@id='emailAddress']");
    private static final By XPATH_MODAL_NAME = By.xpath(".//*[@id='name']");

    private static final By XPATH_FIELD_TIME = By.xpath(".//ng-form/div/div[9]/div/input");
    private static final String URL_PATH = "/home#/ebodac/emailReports";

    private static final By XPATH_SELECTED_NEW_PANEL = By.xpath("//*[@class='panel panel-default ng-scope']");
    private static final By XPATH_LIST_PANELS = By.xpath("//*[@id='main-content']/div/div/div[3]/form/div");

    private static final By XPATH_ADD_NEW_REPORT = By.xpath("//*[@id='main-content']/div/div/div[2]/div/button");

    private static final By XPATH_TITLE_REPORT = By.xpath(".//ng-form/div/div[1]/div/input");

    private static final By XPATH_EMAIL_RECIPIENTS = By.xpath(".//ng-form/div/div[2]/div/div/ul/li/input");
    private static final By XPATH_EMAIL_RECIPIENTS_SELECTED = By.xpath("//*[@id='select2-drop']/ul/li[1]/div");

    private static final By XPATH_EMAIL_SUBJECT = By.xpath(".//ng-form/div/div[3]/div/input");
    private static final By XPATH_EMAIL_CONTENT = By.xpath(".//ng-form/div/div[4]/div/textarea");
    private static final By XPATH_EMAIL_ENTITY = By.xpath(".//ng-form/div/div[5]/div/select");

    private static final By XPATH_EMAIL_ENTITY_FIELD = By.xpath(".//ng-form/div/div[6]/div/div/ul/li/input");
    private static final By XPATH_EMAIL_ENTITY_FIELD_SELECTED = By.xpath("//*[@id='select2-drop']/ul/li[1]/div");

    private static final By XPATH_TIME_FIELD = XPATH_FIELD_TIME;
    private static final By XPATH_SCHEDULE_HOUR = By.xpath("//*[@id='ui-datepicker-div']/div[2]/dl/dd[2]/div/span");
    private static final By XPATH_SCHEDULE_MINUTE = By.xpath("//*[@id='ui-datepicker-div']/div[2]/dl/dd[3]/div/span");
    private static final By XPATH_DATEPICKER = By.xpath("//*[@id='ui-datepicker-div']");
    private static final int ZERO = 0;
    private static final int SLIDE_HOUR = 17;
    private static final int SLIDE_MINUTE = 25;

    private static final By XPATH_POPUP_OK = By.xpath("//*[@id='popup_ok']");
    private static final By XPATH_DELETE_BUTTON = By.xpath(".//ng-form/div/div[11]/div/div/button[3]");
    private static final By XPATH_CLOSE_REPORT = By
            .xpath("//*[@id='main-content']/div/div/div[3]/form/div/div[1]/div[1]/i");
    private static final String XPATH_SAVE_BUTTON = ".//ng-form/div/div[11]/div/div/button[4]";

    private static final int SLEEP_2SEG = 2000;

    private static final By XPATH_DONE_BUTTON = By.xpath("//*[@id='ui-datepicker-div']/div[3]/button[2]");

    private WebElement panel;

    public EmailExportReportPage(WebDriver driver) {
        super(driver);

    }

    public WebElement getPanel() {
        return this.panel;
    }

    public void setPanel(WebElement panel) {
        this.panel = panel;
    }

    @Override
    public String expectedUrlPath() {
        return getServerURL() + URL_PATH;
    }

    @Override
    public void goToPage() {

    }

    public void clickAddNewReport() {

        try {
            clickWhenVisible(XPATH_ADD_NEW_REPORT);
            List<WebElement> listPanels = (findElement(XPATH_LIST_PANELS)).findElements(XPATH_SELECTED_NEW_PANEL);
            this.setPanel(listPanels.get(listPanels.size() - 1));

        } catch (Exception e) {
            getLogger().error("clickAddNewReport - Exc. Reason :" + e.getLocalizedMessage(), e);
        }

    }

    public void setTitleReport(String title) {
        try {

            if (null != this.getPanel()) {
                this.getPanel().findElement(XPATH_TITLE_REPORT).sendKeys(title);
            }
        } catch (Exception e) {
            getLogger().error("setTitleReport - Exc. Reason :" + e.getLocalizedMessage(), e);
        }

    }

    public void setRecipents(String recipient) {
        try {
            if (null != this.getPanel()) {
                sleep(SLEEP_2SEG);
                this.getPanel().findElement(XPATH_EMAIL_RECIPIENTS).sendKeys(recipient);
                sleep(SLEEP_2SEG);
                this.getPanel().findElement(XPATH_EMAIL_RECIPIENTS_SELECTED).click();
            }

        } catch (Exception e) {
            getLogger().error("setRecipents - Exc. Reason :" + e.getLocalizedMessage(), e);
        }

    }

    public void setEmailSubject(String subject) {
        try {
            if (null != this.getPanel()) {
                this.getPanel().findElement(XPATH_EMAIL_SUBJECT).sendKeys(subject);
            }
        } catch (Exception e) {
            getLogger().error("setEmailSubject - Exc. Reason :" + e.getLocalizedMessage(), e);
        }

    }

    public void setMessageContent(String content) {
        try {
            if (null != this.getPanel()) {
                this.getPanel().findElement(XPATH_EMAIL_CONTENT).sendKeys(content);
            }
        } catch (Exception e) {
            getLogger().error("setEmailSubject - Exc. Reason :" + e.getLocalizedMessage(), e);
        }

    }

    public void setEntity(String entity) {
        try {
            if (null != this.getPanel()) {
                Select select = new Select(this.getPanel().findElement(XPATH_EMAIL_ENTITY));
                sleep(SLEEP_2SEG);
                select.selectByVisibleText(entity);
            }

        } catch (Exception e) {
            getLogger().error("setEmailSubject - Exc. Reason :" + e.getLocalizedMessage(), e);
        }

    }

    public void setEntityField(String entityField) {
        try {
            if (null != this.getPanel()) {
                this.getPanel().findElement(XPATH_EMAIL_ENTITY_FIELD).sendKeys(entityField);
                sleep(SLEEP_2SEG);
                findElement(XPATH_EMAIL_ENTITY_FIELD_SELECTED).click();
            }
        } catch (Exception e) {
            getLogger().error("setEntityField - Exc. Reason :" + e.getLocalizedMessage(), e);
        }

    }

    public void setScheduleTime() {
        try {
            if (null != this.getPanel()) {
                this.getPanel().findElement(XPATH_TIME_FIELD).click();
                if (findElement(XPATH_DATEPICKER).isDisplayed()) {
                    sleep(SLEEP_2SEG);
                    // Move hour
                    Actions move = new Actions(getDriver());
                    Action action = move.dragAndDropBy(findElement(XPATH_SCHEDULE_HOUR), SLIDE_HOUR, ZERO).build();
                    action.perform();
                    sleep(SLEEP_2SEG);
                    // Move minute
                    move = new Actions(getDriver());
                    action = move.dragAndDropBy(findElement(XPATH_SCHEDULE_MINUTE), SLIDE_MINUTE, ZERO).build();
                    action.perform();
                    sleep(SLEEP_2SEG);
                    findElement(XPATH_DONE_BUTTON).click();

                }

            }
        } catch (

        Exception e) {
            getLogger().error("setScheduleTime - Exc. Reason :" + e.getLocalizedMessage(), e);
        }

    }

    public boolean saveReport() {
        boolean status = false;
        try {

            if (null != this.getPanel()) {
                status = this.getPanel().findElement(By.xpath(XPATH_SAVE_BUTTON)).isEnabled();
                if (status) {
                    this.getPanel().findElement(By.xpath(XPATH_SAVE_BUTTON)).click();
                    sleep(SLEEP_2SEG);
                    clickPopUpOK();
                } else {
                    getLogger().error("saveReport - Button is disable . Report cannot be saved. ");
                }

            }
        } catch (Exception e) {
            status = false;
            getLogger().error("saveReport - Exc. Reason :" + e.getLocalizedMessage(), e);
        }
        return status;

    }

    public void sleep(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            getLogger().error("sleep - Exc. Reason :" + e.getLocalizedMessage(), e);
        }

    }

    public void closeReport() {
        try {
            clickWhenVisible(XPATH_CLOSE_REPORT);
        } catch (InterruptedException e) {
            getLogger().error("closeOpenReport - IEx. Reason :" + e.getLocalizedMessage(), e);
        }
    }

    public void clickPopUpOK() {
        try {
            clickWhenVisible(XPATH_POPUP_OK);
        } catch (Exception e) {
            getLogger().error("clickPopUpOK - Exc. Reason :" + e.getLocalizedMessage(), e);
        }
    }

    public void deleteReport() {
        try {
            List<WebElement> deleteButtons = findElements(XPATH_DELETE_BUTTON);
            deleteButtons.get(deleteButtons.size() - 1).click();
            sleep(SLEEP_2SEG);
            clickPopUpOK();

        } catch (Exception e) {
            getLogger().error("deleteReport - Exc. Reason :" + e.getLocalizedMessage(), e);
        }

    }

    public boolean existsTestEmailAccount(String account) {
        boolean status = false;
        try {
            Select select = new Select(this.getPanel().findElement(XPATH_SELECT_RECIPIENTS));

            for (WebElement recipient : select.getOptions()) {
                if (!status) {
                    status = recipient.getAttribute(INNER_HTML).contains(account);
                }
            }

        } catch (Exception e) {
            status = false;
            getLogger().error("existsTestEmailAccount - Exception . Reason : " + e.getLocalizedMessage(), e);
        }
        return status;

    }

    public void createRecipient(String name, String email) {
        try {

            this.getPanel().findElement(XPATH_ADD_RECIPIENT_BUTTON).click();
            WebElement modalAddRecipient = findElement(XPATH_MODAL);
            modalAddRecipient.findElement(XPATH_MODAL_NAME).sendKeys(name);
            sleep(SLEEP_2SEG);
            modalAddRecipient.findElement(XPATH_MODAL_EMAIL).sendKeys(email);
            sleep(SLEEP_2SEG);
            modalAddRecipient.findElement(XPATH_MODAL_SAVE_BUTTON).click();
            sleep(SLEEP_2SEG);
            modalAddRecipient.findElement(XPATH_MODAL_OK_BUTTON).click();

        } catch (Exception e) {
            getLogger().error("createRecipient - Exc . Reason : " + e.getLocalizedMessage(), e);
        }

    }

}
