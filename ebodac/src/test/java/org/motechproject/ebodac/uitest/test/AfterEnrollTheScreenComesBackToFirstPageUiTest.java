package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.ebodac.EnrollmentPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AfterEnrollTheScreenComesBackToFirstPageUiTest extends EbodacTestBase {

    private EbodacPage ebodacPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = loginAsAnalyst();
        homePage.resizePage();

        ebodacPage = homePage.goToEbodacModule();
    }

    @Test //EBODAC-828
    public void shouldNotComeBackToFirstPageAfterEnrolling() throws InterruptedException {
        EnrollmentPage enrollmentPage = ebodacPage.goToEnrollment();

        enrollmentPage.changeAmountOfResultsShownOnPage();
        int numberOfPages = enrollmentPage.getAmountPagesOfTable();

        if (numberOfPages > 1) {
            enrollmentPage.sortByParticipantId();
            enrollmentPage.goToLastPageInTable();

            String actualPage = enrollmentPage.getNumberOfActualPage();

            assertTrue(enrollmentPage.enrollTestParticipant());
            assertEquals(actualPage, enrollmentPage.getNumberOfActualPage());

            assertTrue(enrollmentPage.unenrollTestParticipant());
            assertEquals(actualPage, enrollmentPage.getNumberOfActualPage());
        } else {
            getLogger().error("Could not perform the test: not enough enrollment records");
        }
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
