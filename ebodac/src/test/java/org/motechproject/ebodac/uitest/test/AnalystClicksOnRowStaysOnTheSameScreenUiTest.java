package org.motechproject.ebodac.uitest.test;

import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.ebodac.EnrollmentPage;
import org.motechproject.ebodac.uitest.page.HomePage;

import static org.junit.Assert.assertFalse;

public class AnalystClicksOnRowStaysOnTheSameScreenUiTest extends EbodacTestBase {

    private EbodacPage ebodacPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = loginAsAnalyst();
        homePage.resizePage();

        ebodacPage = homePage.goToEbodacModule();
    }

    @Test //EBODAC-529
    public void analystClicksOnRowStaysOnTheSameScreenTest() throws InterruptedException {
        EnrollmentPage enrollmentsPage = ebodacPage.goToEnrollment();

        enrollmentsPage.selectFirstEnrollment();
        // We should not be able to see the advance page for enrollment.
        assertFalse(enrollmentsPage.isOnEnrollmentAdvancedScreen());
    }
}
