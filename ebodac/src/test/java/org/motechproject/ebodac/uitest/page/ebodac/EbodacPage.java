package org.motechproject.ebodac.uitest.page.ebodac;

import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.ReportsPage;
import org.motechproject.ebodac.uitest.page.ebodac.statistics.StatisticsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EbodacPage extends HomePage {
    private static final By PARTICIPANTS = By.id("subjects-tab");
    private static final By VISITS = By.id("visits-tab");
    private static final By REPORTS = By.id("reports-tab");
    private static final By ENROLLMENT = By.id("enrollment-tab");
    private static final By STATISTICS = By.id("statistics-tab");
    private static final By EMAIL_REPORTS = By.id("emailReports-tab");

    private static final By ENROLLMENTS_LOADING = By.id("load_enrollmentTable");

    public EbodacPage(WebDriver driver) {
        super(driver);
    }

    public ParticipantPage goToParticipants() throws InterruptedException {
        clickWhenVisible(PARTICIPANTS);
        waitUntilBlockUiIsGone();
        waitUntilInstancesTableLoadingIsGone();
        return new ParticipantPage(getDriver());
    }

    public VisitPage goToVisit() throws InterruptedException {
        clickWhenVisible(VISITS);
        waitUntilBlockUiIsGone();
        waitUntilInstancesTableLoadingIsGone();
        return new VisitPage(getDriver());
    }

    public ReportsPage gotoReports() throws InterruptedException {
        clickWhenVisible(REPORTS);
        return new ReportsPage(getDriver());
    }

    public EnrollmentPage goToEnrollment() throws InterruptedException {
        clickWhenVisible(ENROLLMENT);
        //wait for grid to load the data (when "Loading..." disappear)
        waitForElementToBeHidden(ENROLLMENTS_LOADING);
        return new EnrollmentPage(getDriver());
    }

    public StatisticsPage goToStatistics() throws InterruptedException {
        clickWhenVisible(STATISTICS);
        return new StatisticsPage(getDriver());
    }

    public void showEmailReports() throws InterruptedException {
        clickWhenVisible(EMAIL_REPORTS);
    }
}
