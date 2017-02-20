package org.motechproject.ebodac.uitest.page.ebodac.statistics;

import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class AbstractKPIsPage extends EbodacPage {

    private static final By STAT_PERIOD_BUTTON = By.id("dateFilterBtn");
    private static final By LAST_30_DAYS = By.linkText("Last 30 days");

    public AbstractKPIsPage(WebDriver driver) {
        super(driver);
    }

    public void showStatsFromLast30Days() throws InterruptedException {
        clickWhenVisible(STAT_PERIOD_BUTTON);
        clickWhenVisible(LAST_30_DAYS);
    }
}
