package org.motechproject.ebodac.uitest.helper;

import org.motechproject.uitest.page.TestProperties;

public class EbodacTestProperties extends TestProperties {

    private static final EbodacTestProperties SINGLETON = new EbodacTestProperties();

    private static final String L1ADMIN_PASSWORD_PROPERTY = "admin.password";

    private static final String L1ANALYST_PASSWORD_PROPERTY = "analyst.password";

    private static final String DEFAULT_L1ADMIN_PASSWORD = "testadmin";

    private static final String DEFAULT_L1ANALYST_PASSWORD = "testanalyst";

    private static final String L1ADMIN_USERNAME_PROPERTY = "admin.login";

    private static final String L1ANALYST_USERNAME_PROPERTY = "analyst.login";

    private static final String DEFAULT_L1ADMIN_USERNAME = "admin";

    private static final String DEFAULT_L1ANALYST_USERNAME = "analyst";

    private static final String CLERK_PASSWORD_PROPERTY = "clerk.password";

    private static final String DEFAULT_CLERK_PASSWORD = "clerk123";

    private static final String DEFAULT_CLERK_USERNAME = "clerk";

    private static final String CLERK_USERNAME_PROPERTY = "clerk.username";

    public static EbodacTestProperties instance() {
        return SINGLETON;
    }

    public EbodacTestProperties() {
    }

    public String getAdminUserName() {
        return getProperty(L1ADMIN_USERNAME_PROPERTY, DEFAULT_L1ADMIN_USERNAME);
    }

    public String getAdminPassword() {
        return getProperty(L1ADMIN_PASSWORD_PROPERTY, DEFAULT_L1ADMIN_PASSWORD);
    }

    public String getAnalystUserName() {
        return getProperty(L1ANALYST_USERNAME_PROPERTY, DEFAULT_L1ANALYST_USERNAME);
    }

    public String getAnalystPassword() {
        return getProperty(L1ANALYST_PASSWORD_PROPERTY, DEFAULT_L1ANALYST_PASSWORD);
    }

    public String getClerkUserName() {
        return getProperty(CLERK_USERNAME_PROPERTY, DEFAULT_CLERK_USERNAME);
    }

    public String getClerkPassword() {
        return getProperty(CLERK_PASSWORD_PROPERTY, DEFAULT_CLERK_PASSWORD);
    }
}
