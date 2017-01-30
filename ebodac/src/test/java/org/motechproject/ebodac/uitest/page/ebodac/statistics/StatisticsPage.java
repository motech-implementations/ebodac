package org.motechproject.ebodac.uitest.page.ebodac.statistics;

import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StatisticsPage extends EbodacPage {
    public static final String URL_PATH = "/home#/ebodac/statistics";

    private static final By IVR_GRAPHS = By.linkText("IVR Graphs");
    private static final By SMS_GRAPHS = By.linkText("SMS Graphs");
    private static final By IVR_KPIS = By.linkText("IVR KPIs");
    private static final By SMS_KPIS = By.linkText("SMS KPIs");

    public StatisticsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return getServerURL() + URL_PATH;
    }

    public IVRKPIsPage goToIVRKPIs() throws InterruptedException {
        clickWhenVisible(IVR_KPIS);
        return new IVRKPIsPage(getDriver());
    }

    public SMSKPIsPage goToSMSKPIs() throws InterruptedException {
        clickWhenVisible(SMS_KPIS);
        return new SMSKPIsPage(getDriver());
    }

    public IVRGraphsPage goToIVRGraphs() throws InterruptedException {
        clickWhenVisible(IVR_GRAPHS);
        return new IVRGraphsPage(getDriver());
    }

    public SMSGraphsPage goToSMSGraphs() throws InterruptedException {
        clickWhenVisible(SMS_GRAPHS);
        return new SMSGraphsPage(getDriver());
    }
}
