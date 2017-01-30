package org.motechproject.ebodac.uitest.test;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.ebodac.EbodacPage;
import org.motechproject.ebodac.uitest.page.HomePage;
import org.motechproject.ebodac.uitest.page.ebodac.VisitEditPage;
import org.motechproject.ebodac.uitest.page.ebodac.VisitPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UnableToChangeVisitDateUiTest extends EbodacTestBase {

    private VisitPage visitPage;

    @Before
    public void setUp() throws InterruptedException {
        HomePage homePage = login();
        homePage.resizePage();

        EbodacPage ebodacPage = homePage.goToEbodacModule();
        visitPage = ebodacPage.goToVisit();
    }

    @Test //EBODAC-469
    public void unableToChangVisitDateTest() throws InterruptedException {
        visitPage.findTestVisit();
        String oldDate = visitPage.getTestVisitPlannedDate();
        VisitEditPage visitEditPage = visitPage.editTestVisit();
        String date = LocalDate.now().toString("yyyy-MM-dd");
        visitEditPage.changePlannedDate(date);
        assertTrue(visitEditPage.saveVisit());

        visitPage.findTestVisit();
        String newDate = visitPage.getTestVisitPlannedDate();
        visitEditPage = visitPage.editTestVisit();
        assertEquals(date, newDate);
        visitEditPage.changePlannedDate(oldDate);
        assertTrue(visitEditPage.saveVisit());
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
