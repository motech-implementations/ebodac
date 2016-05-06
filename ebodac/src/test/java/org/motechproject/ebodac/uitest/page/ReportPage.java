package org.motechproject.ebodac.uitest.page;

import org.mockito.asm.tree.TryCatchBlockNode;
import org.mockito.internal.matchers.Null;
import org.motech.page.AbstractBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class ReportPage extends AbstractBasePage {

    public static final String URL_PATH = "/home#/mds/dataBrowser";

    static final By MEMISSEDCLINICVISITSREPORT = By.linkText("M&E Missed Clinic Visits Report");

    static final By DAILYCLINICVISITSCHEDULEREPORT = By.linkText("Daily Clinic Visit Schedule Report");

    static final By FOLLOW_UPS_AFTER_PRIME_INJECTION_REPORT = By.linkText("Follow-ups After Prime Injection Report");

    static final By CALL_DETAIL_REPORT = By.linkText("Call Detail Record");

    //TABLE
    static final String CALL_DETAIL_REPORT_TABLE_ID="jqgh_instancesTable_id";

    static final String CALL_DETAIL_REPORT_TABLE_CONFIG_NAME="instancesTable_configName";

    static final String CALL_DETAIL_REPORT_TABLE_FROM="instancesTable_configName";

    static final String CALL_DETAIL_REPORT_TABLE_TO="instancesTable_to";

    static final String CALL_DETAIL_REPORT_TABLE_CALL_DIRECTION="instancesTable_callDirection";

    static final String CALL_DETAIL_REPORT_TABLE_CALL_STATUS="instancesTable_callStatus";

    static final String CALL_DETAIL_REPORT_TABLE_TEMPLATE_NAME="instancesTable_templateName";

    static final String CALL_DETAIL_REPORT_TABLE_PROVIDER_EXTRA_DATA="instancesTable_providerExtraData";

    static final String CALL_DETAIL_REPORT_TABLE_MOTECH_CALL_ID="instancesTable_motechCallId";

    static final String CALL_DETAIL_REPORT_TABLE_PROVIDER_CALL_ID="instancesTable_providerCallId";

    static final String CALL_DETAIL_REPORT_TABLE_MOTECH_TIMESTAMP="instancesTable_motechTimestamp";

    static final String CALL_DETAIL_REPORT_TABLE_PROVIDER_TIMESTAMP="instancesTable_providerTimestamp";

    static final String CALL_DETAIL_REPORT_TABLE_CALL_DURATION="instancesTable_callDuration";

    static final String CALL_DETAIL_REPORT_TABLE_MESSAGE_PERCENT_LISTENED="instancesTable_messagePercentListened";


    public ReportPage(WebDriver driver) {
        super(driver);
    }

    public void showMEMissedClinicVisitsReport() throws InterruptedException {
        clickWhenVisible(MEMISSEDCLINICVISITSREPORT);
    }

    public void showDailyClinicVisitReportSchedule() throws InterruptedException {
        clickWhenVisible(DAILYCLINICVISITSCHEDULEREPORT);
    }

    public void showFollowUpsAfterPrimeInjectionReport() throws InterruptedException {
        clickWhenVisible(FOLLOW_UPS_AFTER_PRIME_INJECTION_REPORT);
    }

    public void showCallDetailRecord() throws InterruptedException{
        clickWhenVisible(CALL_DETAIL_REPORT);
    }

    public boolean checkIfTableOfCallDetailRecordInstancesIsVisible() throws InterruptedException {
        boolean result=true;
        String name[] = new String[14];

        name[0]=String.valueOf(CALL_DETAIL_REPORT_TABLE_ID);
        name[1]=String.valueOf(CALL_DETAIL_REPORT_TABLE_CONFIG_NAME);
        name[2]=String.valueOf(CALL_DETAIL_REPORT_TABLE_FROM);
        name[3]=String.valueOf(CALL_DETAIL_REPORT_TABLE_TO);
        name[4]=String.valueOf(CALL_DETAIL_REPORT_TABLE_CALL_DIRECTION);
        name[5]=String.valueOf(CALL_DETAIL_REPORT_TABLE_CALL_STATUS);
        name[6]=String.valueOf(CALL_DETAIL_REPORT_TABLE_TEMPLATE_NAME);
        name[7]=String.valueOf(CALL_DETAIL_REPORT_TABLE_PROVIDER_EXTRA_DATA);
        name[8]=String.valueOf(CALL_DETAIL_REPORT_TABLE_MOTECH_CALL_ID);
        name[9]=String.valueOf(CALL_DETAIL_REPORT_TABLE_PROVIDER_CALL_ID);
        name[10]=String.valueOf(CALL_DETAIL_REPORT_TABLE_MOTECH_TIMESTAMP);
        name[11]=String.valueOf(CALL_DETAIL_REPORT_TABLE_PROVIDER_TIMESTAMP);
        name[12]=String.valueOf(CALL_DETAIL_REPORT_TABLE_CALL_DURATION);
        name[13]=String.valueOf(CALL_DETAIL_REPORT_TABLE_MESSAGE_PERCENT_LISTENED);

        for(int i=0;i<14;i++)
        {
            String check="";
            check=findElement(By.id(name[i])).getText();
            if(check=="")
            {
                result=false;
            }
        }
        return result;
    }

    @Override
    public String expectedUrlPath() {
        return URL_ROOT + URL_PATH;
    }
}
