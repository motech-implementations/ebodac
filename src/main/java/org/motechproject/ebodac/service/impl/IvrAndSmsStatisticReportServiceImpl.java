package org.motechproject.ebodac.service.impl;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.motechproject.ebodac.constants.EbodacConstants;
import org.motechproject.ebodac.domain.Config;
import org.motechproject.ebodac.domain.IvrAndSmsStatisticReport;
import org.motechproject.ebodac.domain.Subject;
import org.motechproject.ebodac.repository.IvrAndSmsStatisticReportDataService;
import org.motechproject.ebodac.repository.SubjectDataService;
import org.motechproject.ebodac.service.ConfigService;
import org.motechproject.ebodac.service.IvrAndSmsStatisticReportService;
import org.motechproject.ivr.domain.CallDetailRecord;
import org.motechproject.ivr.repository.CallDetailRecordDataService;
import org.motechproject.mds.query.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ivrAndSmsStatisticReportService")
public class IvrAndSmsStatisticReportServiceImpl implements IvrAndSmsStatisticReportService {

    private IvrAndSmsStatisticReportDataService ivrAndSmsStatisticReportDataService;

    private CallDetailRecordDataService callDetailRecordDataService;

    private SubjectDataService subjectDataService;

    private ConfigService configService;

    private DateTimeFormatter simpleDateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public void generateDailyReports() {

        Config config = configService.getConfig();

        if(StringUtils.isNotBlank(config.getLastCalculationDateForIvrReports())) {
            LocalDate startDate = simpleDateTimeFormatter.parseLocalDate(config.getLastCalculationDateForIvrReports());
            generateDailyReportsFromDate(startDate);
        } else {
            generateDailyReportsFromDate(null);
        }

        config.setLastCalculationDateForIvrReports(LocalDate.now().toString(simpleDateTimeFormatter));
        configService.updateConfig(config);
    }

    @Override
    public void generateDailyReportsFromDate(LocalDate startDate) {

        List<CallDetailRecord> callDetailRecords = new ArrayList<>();
        LocalDate date = startDate;

        if(startDate == null){
            callDetailRecords = callDetailRecordDataService.findByCallStatus("INITIATED");
        } else {
            while (!date.isAfter(LocalDate.now())) {
                String dateString = simpleDateTimeFormatter.print(date);
                if (callDetailRecords.isEmpty()) {
                    callDetailRecords = callDetailRecordDataService.findByMotechTimestampAndCallStatus(dateString, "INITIATED");
                } else {
                    callDetailRecords.addAll(callDetailRecordDataService.findByMotechTimestampAndCallStatus(dateString, "INITIATED"));
                }
                date = date.plusDays(1);
            }
        }

        for (CallDetailRecord callDetailRecord : callDetailRecords){
            createReport(callDetailRecord.getProviderCallId());
        }
    }

    private void createReport(String providerCallId) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSSS");
        List<CallDetailRecord> callDetailRecords = callDetailRecordDataService.findByProviderCallId(providerCallId);
        IvrAndSmsStatisticReport ivrAndSmsStatisticReport;

        int attempts = 0;
        DateTime sendDate = null;
        DateTime receivedDate = null;
        DateTime smsReceivedDate = null;
        String messageId = "";
        double expectedDuration = 0;
        double timeListenedTo = 0;
        String sms = "no";
        String ids;
        List<Subject> subjectList = new ArrayList<>();

        for(CallDetailRecord callDetailRecord : callDetailRecordDataService.findByExactProviderCallId(providerCallId, QueryParams.ascOrder("motechTimestamp")))
        {
            if (callDetailRecord.getCallStatus().contains("INITIATED")) {
                ids = callDetailRecord.getProviderExtraData().get(EbodacConstants.SUBJECT_IDS);
                for (String id : ids.split(", ")) {
                    if(StringUtils.isNotBlank(id)) {
                        Subject subject = subjectDataService.findSubjectBySubjectId(id);
                        subjectList.add(subject);
                    }
                }

                messageId = callDetailRecord.getProviderExtraData().get(EbodacConstants.MESSAGE_ID);
                if(StringUtils.isNotBlank(callDetailRecord.getMotechTimestamp())) {
                    sendDate = new DateTime(DateTime.parse(callDetailRecord.getMotechTimestamp(), formatter));
                }
            } else if (callDetailRecord.getCallStatus().contains("Submitted")) {
                sms = "yes";
            } else if (callDetailRecord.getCallStatus().contains("Failed")) {
                if(StringUtils.isNotBlank(callDetailRecord.getMotechTimestamp())) {
                    receivedDate = new DateTime(DateTime.parse(callDetailRecord.getMotechTimestamp(), formatter));
                }
            } else if (callDetailRecord.getCallStatus().contains("Finished")) {
                if(sms == "yes") {
                    if(StringUtils.isNotBlank(callDetailRecord.getMotechTimestamp())) {
                        smsReceivedDate = DateTime.parse(callDetailRecord.getMotechTimestamp(), formatter);
                    }
                } else {
                    if(StringUtils.isNotBlank(callDetailRecord.getMotechTimestamp())) {
                        receivedDate = DateTime.parse(callDetailRecord.getMotechTimestamp(), formatter);
                    }
                }
                attempts = Integer.parseInt(callDetailRecord.getProviderExtraData().get("attempts"));
                expectedDuration = Double.parseDouble(callDetailRecord.getCallDuration());
                timeListenedTo = expectedDuration * Double.parseDouble(callDetailRecord.getMessagePercentListened());
            }
        }

        for(Subject subject : subjectList) {
            ivrAndSmsStatisticReport = new IvrAndSmsStatisticReport(subject, messageId, sendDate, expectedDuration, timeListenedTo, receivedDate,
                    attempts, sms, smsReceivedDate);
            ivrAndSmsStatisticReportDataService.create(ivrAndSmsStatisticReport);
        }
    }

    @Autowired
    public void setIvrAndSmsStatisticReportDataService(IvrAndSmsStatisticReportDataService ivrAndSmsStatisticReportDataService) {
        this.ivrAndSmsStatisticReportDataService = ivrAndSmsStatisticReportDataService;
    }

    @Autowired
    public void setCallDetailRecordDataService(CallDetailRecordDataService callDetailRecordDataService) {
        this.callDetailRecordDataService = callDetailRecordDataService;
    }

    @Autowired
    public void setSubjectDataService(SubjectDataService subjectDataService) {
        this.subjectDataService = subjectDataService;
    }

    @Autowired
    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }
}
