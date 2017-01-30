package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.SMSPage;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.ebodac.EnrollmentPage;
import org.motechproject.ebodac.uitest.page.ebodac.ParticipantEditPage;
import org.motechproject.ebodac.uitest.page.ebodac.ParticipantPage;
import org.motechproject.ebodac.uitest.page.ebodac.VisitEditPage;
import org.motechproject.ebodac.uitest.page.ebodac.VisitPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.BoosterVaccinationReportPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.CallDetailRecordPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.DailyClinicVisitScheduleReportPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.FollowupsAfterPrimeInjectionReportPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.FollowupsMissedClinicVisitsReportPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.MEMissedClinicVisitsReportPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.NumberOfTimesListenedReportPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.ParticipantsWhoOptOutOfMessagesReportPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.PrimeFollowAndBoostReportPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.PrimerVaccinationReportPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.ReportsPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.SMSLogReportPage;
import org.motechproject.ebodac.uitest.page.ebodac.reports.ScreeningReportPage;
import org.motechproject.ebodac.uitest.page.ivr.IVREditPage;
import org.motechproject.ebodac.uitest.page.ivr.IVRLogPage;
import org.motechproject.ebodac.uitest.page.ivr.IVRPage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AdminAccessToEbodacTabsUiTest extends EbodacTestBase {

    // Variables for the pages.
    private HomePage homePage;
    private EbodacPage ebodacPage;

    @Before
    public void setUp() throws InterruptedException {
        // We close the session with admin user to try to log in with admin user.
        homePage = loginAsAdmin();

        homePage.resizePage();

        ebodacPage = homePage.goToEbodacModule();
    }

    @Test // Test for EBODAC-531
    public void adminAccessOnlyToEbodacUiTest() throws InterruptedException {
        // Participants Asserts
        testAdminParticipantsTab();

        // Visits Asserts
        testAdminVisitsTab();

        // Report Asserts
        testAdminWithReports();

        // Enrolment Tab
        testAdminEnrolmentTab();

        // IVR Module
        testAdminIVRModule();

        // SMS Module
        testAdminSMSModule();
    }

    /**
     * This method check if the EBODAC page opens and if it is possible if there
     * are name, house hold and head of household .
     *
     * @throws InterruptedException
     */
    public void testAdminParticipantsTab() throws InterruptedException {
        ParticipantPage participantPage = ebodacPage.goToParticipants();
        ParticipantEditPage participantEditPage = participantPage.editFirstParticipant();
        assertTrue(participantEditPage.isNameEditable());
        assertTrue(participantEditPage.isHouseholdNameEditable());
        assertTrue(participantEditPage.isHeadOfHouseholdEditable());
    }

    private void testAdminVisitsTab() throws InterruptedException {
        VisitPage visitPage = ebodacPage.goToVisit();

        VisitEditPage visitEditPage = visitPage.editFirstVisit();
        assertTrue(visitEditPage.isPlannedVisitDateEditable());
        assertFalse(visitEditPage.isActualVisitDateEditable());
        assertFalse(visitEditPage.isVisitTypeEditable());
    }

    private void testAdminEnrolmentTab() throws InterruptedException {
        EnrollmentPage enrollmentPage = ebodacPage.goToEnrollment();
        // It should be allowed to enrol unenroll participants.
        assertTrue(enrollmentPage.enrollParticipant());
        assertTrue(enrollmentPage.unenrollParticipant());
    }

    private void testAdminWithReports() throws InterruptedException {
        ReportsPage reportsPage = ebodacPage.gotoReports();
        PrimerVaccinationReportPage primerVaccinationReportPage = reportsPage.goToPrimeVaccinationReport();
        assertTrue(primerVaccinationReportPage.existTable());

        reportsPage = ebodacPage.gotoReports();
        BoosterVaccinationReportPage boosterVaccinationReportPage = reportsPage.goToBoostVaccinationReport();
        assertTrue(boosterVaccinationReportPage.existTable());

        reportsPage = ebodacPage.gotoReports();
        DailyClinicVisitScheduleReportPage dailyClinicVisitScheduleReportPage = reportsPage.goToDailyClinicVisitReportSchedule();
        assertTrue(dailyClinicVisitScheduleReportPage.existTable());

        reportsPage = ebodacPage.gotoReports();
        FollowupsAfterPrimeInjectionReportPage followupsAfterPrimeInjectionReportPage = reportsPage.goToFollowUpsAfterPrimeInjectionReport();
        assertTrue(followupsAfterPrimeInjectionReportPage.existTable());

        reportsPage = ebodacPage.gotoReports();
        ParticipantsWhoOptOutOfMessagesReportPage participantsWhoOptOutOfMessagesReportPage = reportsPage.goToParticipantsWhoOptOutOfMessages();
        assertTrue(participantsWhoOptOutOfMessagesReportPage.existTable());

        // Report FollowupsMissedClinicVisits
        reportsPage = ebodacPage.gotoReports();
        FollowupsMissedClinicVisitsReportPage followupsMissedClinicVisitsReportPage = reportsPage.goToFollowUpsMissedClinicReport();
        assertTrue(followupsMissedClinicVisitsReportPage.existTable());

        // Report MEMissed Clinics Visits Reports.
        reportsPage = ebodacPage.gotoReports();
        MEMissedClinicVisitsReportPage meMissedClinicVisitsReportPage = reportsPage.goToMEMissedClinicVisitsReport();
        assertTrue(meMissedClinicVisitsReportPage.existTable());

        reportsPage = ebodacPage.gotoReports();
        NumberOfTimesListenedReportPage numberOfTimesListenedReportPage = reportsPage.goToNumberOfTimesReport();
        assertTrue(numberOfTimesListenedReportPage.existTable());

        reportsPage = ebodacPage.gotoReports();
        ScreeningReportPage screeningReportPage = reportsPage.goToScreeningReport();
        assertTrue(screeningReportPage.existTable());

        reportsPage = ebodacPage.gotoReports();
        PrimeFollowAndBoostReportPage primeFollowAndBoostReportPage = reportsPage.goToPrimeFollowAndBoostReport();
        assertTrue(primeFollowAndBoostReportPage.existTable());

        reportsPage = ebodacPage.gotoReports();
        CallDetailRecordPage callDetailRecordPage = reportsPage.goToCallDetailRecord();
        assertTrue(callDetailRecordPage.existTable());

        reportsPage = ebodacPage.gotoReports();
        SMSLogReportPage smsLogReportPage = reportsPage.goToSMSLog();
        assertTrue(smsLogReportPage.existTable());
    }

    private void testAdminIVRModule() throws InterruptedException {
        IVRPage ivrPage = homePage.goToIVRModule();
        IVRLogPage ivrLogPage = ivrPage.goToLogPage();
        IVREditPage ivrEditPage = ivrLogPage.editFirstRecord();
        assertFalse(ivrEditPage.isSaveButtonVisible());
    }

    private void testAdminSMSModule() throws InterruptedException {
        SMSPage smsPage = homePage.goToSMSModule();
        assertTrue(smsPage.logExists());
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
