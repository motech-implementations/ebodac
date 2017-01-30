package org.motechproject.ebodac.uitest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.ebodac.uitest.page.HomePage;

import static org.junit.Assert.assertFalse;

public class AnalystAccessOnlyToEbodacUiTest extends EbodacTestBase {
    private HomePage homePage;

    @Before
    public void setUp() {
        homePage = loginAsAnalyst();
    }

    @Test // Test for EBODAC-528
    public void analystAccessOnlyToEbodacUITest() throws InterruptedException {
        assertFalse(homePage.isDataServicesModulePresent());
    }

    @After
    public void tearDown() throws InterruptedException {
        logout();
    }
}
