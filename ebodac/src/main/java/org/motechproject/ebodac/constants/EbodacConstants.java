package org.motechproject.ebodac.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public final class EbodacConstants {

    public static final int LOGIN_WAIT_TIME = 1000;

    public static final String SUBJECT_ID_FIELD_NAME = "subjectId";
    public static final String SUBJECT_ID_FIELD_DISPLAY_NAME = "Participant Id";

    public static final String ZETES_UPDATE_EVENT = "zetes_update";
    public static final String EMAIL_CHECK_EVENT = "email_check";

    public static final String ZETES_URL = "zetes_url";
    public static final String ZETES_USERNAME = "zetes_username";
    public static final String ZETES_PASSWORD = "zetes_password";

    public static final String FTP_FILE_SEPARATOR = "/";

    public static final Pattern CSV_FILENAME_PATTERN = Pattern.compile(".*_(.*_.*)\\.csv");
    public static final String CSV_DATE_FORMAT = "yyyyMMdd_HHmmss";

    public static final String FETCH_CSV_START_DATE_FORMAT = "yyyy-MM-dd";

    public static final String REPORT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DAILY_REPORT_EVENT = "daily_report_event";
    public static final String DAILY_REPORT_EVENT_START_DATE = "daily_report_event_start_date";
    public static final String DAILY_REPORT_EVENT_START_HOUR = "00:01";

    public static final String BOOSTER_RELATED_MESSAGES = "Booster related messages";

    public static final String PDF_EXPORT_FORMAT = "pdf";
    public static final String CSV_EXPORT_FORMAT = "csv";
    public static final String XLS_EXPORT_FORMAT = "xls";

    public static final String DATE_PICKER_DATE_FORMAT = "yyyy-MM-dd";

    public static final String API_KEY = "api_key";
    public static final String MESSAGE_ID = "message_id";
    public static final String STATUS_CALLBACK_URL = "status_callback_url";
    public static final String SUBSCRIBERS = "subscribers";
    public static final String PHONE = "phone";
    public static final String LANGUAGE = "language";
    public static final String SEND_SMS_IF_VOICE_FAILS = "send_sms_if_voice_fails";
    public static final String DETECT_VOICEMAIL = "detect_voicemail_action";
    public static final String RETRY_ATTEMPTS_SHORT = "retry_attempts_short";
    public static final String RETRY_DELAY_SHORT = "retry_delay_short";
    public static final String RETRY_ATTEMPTS_LONG = "retry_attempts_long";
    public static final String RETRY_ATTEMPTS_LONG_DEFAULT = "1";
    public static final String SUBJECT_IDS = "subject_ids";
    public static final String SUBJECT_PHONE_NUMBER = "subscriber_phone";

    public static final List<String> AVAILABLE_CAMPAIGNS = new ArrayList<>(Arrays.asList("Screening", "Prime Vaccination Day",
            "Booster related messages", "Prime Vaccination First Follow-up visit", "Boost Vaccination Day", "Boost Vaccination First Follow-up visit",
            "Boost Vaccination Second Follow-up visit", "Boost Vaccination Third Follow-up visit", "First Long-term Follow-up visit",
            "Second Long-term Follow-up visit", "Third Long-term Follow-up visit", "Fourth Long-term Follow-up visit",
            "Fifth Long-term Follow-up visit", "Sixth Long-term Follow-up visit", "Seventh Long-term Follow-up visit",
            "Third Vaccination Day", "First Post Third Vaccination visit", "Second Post Third Vaccination visit",
            "Third Post Third Vaccination visit", "Fourth Post Third Vaccination visit", "Fifth Post Third Vaccination visit",
            "Prime Vaccination Day - stage 2", "Booster related messages - stage 2", "Prime Vaccination First Follow-up visit - stage 2",
            "Prime Vaccination Second Follow-up visit - stage 2", "Boost Vaccination Day - stage 2", "Boost Vaccination First Follow-up visit - stage 2",
            "Boost Vaccination Second Follow-up visit - stage 2", "Boost Vaccination Third Follow-up visit - stage 2",
            "First Long-term Follow-up visit - stage 2", "Second Long-term Follow-up visit - stage 2",
            "Third Long-term Follow-up visit - stage 2", "Fourth Long-term Follow-up visit - stage 2"));

    public static final String STAGE = " - stage ";

    public static final String IVR_CALL_DETAIL_RECORD_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSS";
    public static final String IVR_CALL_DETAIL_RECORD_MOTECH_TIMESTAMP_FIELD = "motechTimestamp";

    public static final String IVR_CALL_DETAIL_RECORD_STATUS_INITIATED = "INITIATED";
    public static final String IVR_CALL_DETAIL_RECORD_STATUS_FINISHED = "Finished";
    public static final String IVR_CALL_DETAIL_RECORD_STATUS_FAILED = "Failed";
    public static final String IVR_CALL_DETAIL_RECORD_STATUS_SUBMITTED = "Submitted";
    public static final String IVR_CALL_DETAIL_RECORD_NUMBER_OF_ATTEMPTS = "attempts";
    public static final String IVR_CALL_DETAIL_RECORD_END_TIMESTAMP = "end_timestamp";
    public static final String IVR_CALL_DETAIL_RECORD_START_TIMESTAMP = "start_timestamp";
    public static final String IVR_CALL_DETAIL_RECORD_MESSAGE_SECOND_COMPLETED = "message_seconds_completed";

    public static final String VOTO_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String SITE_ID_CHANGED_EVENT = "siteIdChangedEvent";
    public static final String SUBJECT_ID = "subjectId";
    public static final String SITE_ID = "siteId";

    public static final String SEND_EMAIL_REPORT_EVENT = "send_email_report_event";
    public static final String SEND_EMAIL_REPORT_EVENT_REPORT_ID = "send_email_report_event_report_id";

    public static final String PRIMER_VACCINATION_REPORT_NAME = "PrimerVaccinationReport";
    public static final String BOOSTER_VACCINATION_REPORT_REPORT_NAME = "BoosterVaccinationReport";
    public static final String DAILY_CLINIC_VISIT_SCHEDULE_REPORT_NAME = "DailyClinicVisitScheduleReport";
    public static final String FOLLOW_UPS_AFTER_PRIME_INJECTION_REPORT_NAME = "FollowupsAfterPrimeInjectionReport";
    public static final String FOLLOW_UPS_MISSED_CLINIC_VISITS_REPORT_NAME = "FollowupsMissedClinicVisitsReport";
    public static final String M_AND_E_MISSED_CLINIC_VISITS_REPORT_NAME = "MandEMissedClinicVisitsReport";
    public static final String OPTS_OUT_OF_MOTECH_MESSAGES_REPORT_NAME = "ParticipantsWhoOptOutOfReceivingMotechMessagesReport";
    public static final String SCREENING_REPORT_NAME = "ScreeningReport";
    public static final String IVR_AND_SMS_STATISTIC_REPORT_NAME = "NumberOfTimesParticipantsListenedToEachMessageReport";
    public static final String DAY_8_AND_DAY_57_REPORT_NAME = "PrimeVac1stFollow-upAndBoostVacDayReport";
    public static final String SUBJECT_ENROLLMENTS_NAME = "ParticipantEnrollments";
    public static final String IVR_KPI_NAME = "IvrKpi";
    public static final String SMS_KPI_NAME = "SmsKpi";
    public static final String IVR_ENGAGEMENT_NAME = "IvrEngagement";

    public static final Map<String, String> DAILY_CLINIC_VISIT_SCHEDULE_REPORT_MAP = new LinkedHashMap<String, String>() {
        {
            put("Planned Visit Date", "motechProjectedDate");
            put("Participant ID",    "subject.subjectId");
            put("Name",              "subject.name");
            put("Phone Number",      "subject.phoneNumber");
            put("Address",           "subject.address");
            put("Visit type",        "type");
            put("Community",         "subject.community");
            put("Stage ID",          "subject.stageId");
            put("Site Name",         "subject.siteName");
        }
    };

    public static final Map<String, String> FOLLOW_UPS_AFTER_PRIME_INJECTION_REPORT_MAP = new LinkedHashMap<String, String>() {
        {
            put("Name",                     "subject.name");
            put("Household name",           "subject.householdName");
            put("Head Of Household",        "subject.headOfHousehold");
            put("DOB",                      "subject.dateOfBirth");
            put("Gender",                   "subject.gender");
            put("Address",                  "subject.address");
            put("Primer Vaccination Date",  "subject.primerVaccinationDate");
            put("Booster Vaccination Date", "subject.boosterVaccinationDate");
            put("Date Of Follow-up Visit",  null);
            put("Community",                "subject.community");
            put("Stage ID",                 "subject.stageId");
            put("Site Name",                "subject.siteName");
        }
    };

    public static final Map<String, String> FOLLOW_UPS_MISSED_CLINIC_VISITS_REPORT_MAP = new LinkedHashMap<String, String>() {
        {
            put("Name",                     "subject.name");
            put("Household name",           "subject.householdName");
            put("Head Of Household",        "subject.headOfHousehold");
            put("DOB",                      "subject.dateOfBirth");
            put("Gender",                   "subject.gender");
            put("Address",                  "subject.address");
            put("Visit type",               "type");
            put("Planned Visit Date",       "motechProjectedDate");
            put("No Of Days Exceeded Visit", "noOfDaysExceededVisit");
            put("Community",                "subject.community");
            put("Stage ID",                 "subject.stageId");
            put("Site Name",                "subject.siteName");
        }
    };

    public static final Map<String, String> M_AND_E_MISSED_CLINIC_VISITS_REPORT_MAP = new LinkedHashMap<String, String>() {
        {
            put("Participant Id",           "subject.subjectId");
            put("Name",                     "subject.name");
            put("Household name",           "subject.householdName");
            put("Head Of Household",        "subject.headOfHousehold");
            put("DOB",                      "subject.dateOfBirth");
            put("Gender",                   "subject.gender");
            put("Address",                  "subject.address");
            put("Phone",                    "subject.phoneNumber");
            put("Visit type",               "type");
            put("Planned Visit Date",       "motechProjectedDate");
            put("No Of Days Exceeded Visit", "noOfDaysExceededVisit");
            put("Community",                "subject.community");
            put("Stage ID",                 "subject.stageId");
            put("Site Name",                "subject.siteName");
        }
    };

    public static final Map<String, String> SCREENING_REPORT_MAP = new LinkedHashMap<String, String>() {
        {
            put("Participant Id",           "subject.subjectId");
            put("Actual Screening Date",    "date");
            put("Stage Id",                 "subject.stageId");
            put("Site Name",                "subject.siteName");
        }
    };

    public static final Map<String, String> PRIMER_VACCINATION_REPORT_MAP = new LinkedHashMap<String, String>() {
        {
            put("Date",                     "date");
            put("People Vaccinated",        "peopleVaccinated");
            put("Adult Males",              "adultMales");
            put("Adult Females",            "adultFemales");
            put("Adult Unidentified",       "adultUnidentified");
            put("Adult Undifferentiated",   "adultUndifferentiated");
            put("Children 12-17",           "children_12_17");
            put("Children 6-11",            "children_6_11");
            put("Children 1-5",             "children_1_5");
        }
    };

    public static final Map<String, String> BOOSTER_VACCINATION_REPORT_MAP = new LinkedHashMap<String, String>() {
        {
            put("Date",                     "date");
            put("People Boostered",         "peopleBoostered");
            put("Adult Males",              "adultMales");
            put("Adult Females",            "adultFemales");
            put("Adult Unidentified",       "adultUnidentified");
            put("Adult Undifferentiated",   "adultUndifferentiated");
            put("Children 12-17",           "children_12_17");
            put("Children 6-11",            "children_6_11");
            put("Children 1-5",             "children_1_5");
        }
    };

    public static final Map<String, String> OPTS_OUT_OF_MOTECH_MESSAGES_REPORT_MAP = new LinkedHashMap<String, String>() {
        {
            put("Participant Id",           "subject.subjectId");
            put("Gender",                   "subject.gender");
            put("Age",                      "age");
            put("Date of Unenrollment",     "dateOfUnenrollment");
            put("Stage ID",                 "subject.stageId");
            put("Site Name",                "subject.siteName");
        }
    };


    public static final Map<String, String> IVR_AND_SMS_STATISTIC_REPORT_MAP = new LinkedHashMap<String, String>() {
        {
            put("Participant Id",           "subject.subjectId");
            put("Phone",                    "subject.phoneNumber");
            put("Gender",                   "subject.gender");
            put("Age",                      "age");
            put("Location (Community)",     "subject.community");
            put("Message ID",               "messageId");
            put("Sent Date",                "sendDate");
            put("Expected Duration",        "expectedDuration");
            put("Time Listened To",         "timeListenedTo");
            put("Percent Listened",         "messagePercentListened");
            put("Received Date",            "receivedDate");
            put("No. of Attempts",          "numberOfAttempts");
            put("SMS",                      "sms");
            put("SMS Received Date",        "smsReceivedDate");
            put("Stage ID",                 "subject.stageId");
        }
    };

    public static final Map<String, String> DAY_8_AND_DAY_57_REPORT_MAP = new LinkedHashMap<String, String>() {
        {
            put("Participant Id",           "subject.subjectId");
            put("Visit type",               "type");
            put("Planned Visit Date",       "motechProjectedDate");
            put("Actual Visit Date",        "date");
            put("Stage ID",                 "subject.stageId");
            put("Site Name",                "subject.siteName");
        }
    };

    public static final Map<String, String> SUBJECT_ENROLLMENTS_MAP = new LinkedHashMap<String, String>() {
        {
            put("Participant Id",           "subject.subjectId");
            put("Name",                     "subject.name");
            put("Stage ID",                 "subject.stageId");
            put("Status",                   "status");
        }
    };

    public static final Map<String, String> IVR_KPI_MAP = new LinkedHashMap<String, String>() {
        {
            put("Date",                     "date");
            put("Total Calls",              "totalAmount");
            put("Total Pending",            "totalPending");
            put("Total Failed",             "totalFailed");
            put("Total Succeed",            "totalSucceed");
            put("Call To Men",              "sendToMen");
            put("Call To Women",            "sendToWomen");
            put("Successful call To Men",   "successfulSendToMen");
            put("Successful call To Women", "successfulSendToWomen");
        }
    };

    public static final Map<String, String> SMS_KPI_MAP = new LinkedHashMap<String, String>() {
        {
            put("Date",                     "date");
            put("Total SMS Sent",           "totalAmount");
            put("Total Pending",            "totalPending");
            put("Total Failed",             "totalFailed");
            put("Total Succeed",            "totalSucceed");
            put("SMS To Men",               "sendToMen");
            put("SMS To Women",             "sendToWomen");
            put("Successful SMS To Men",    "successfulSendToMen");
            put("Successful SMS To Women",  "successfulSendToWomen");
        }
    };

    public static final Map<String, String> IVR_ENGAGEMENT_MAP = new LinkedHashMap<String, String>() {
        {
            put("Participant ID",           "subjectId");
            put("Stage ID",                 "stageId");
            put("Calls Expected",           "callsExpected");
            put("Pushed Successfully",      "pushedSuccessfully");
            put("Received",                 "received");
            put("Actively Listened",        "activelyListened");
            put("Failed",                   "failed");
        }
    };

    public static final Map<String, Float> REPORT_COLUMN_WIDTHS = new LinkedHashMap<String, Float>() {
        {
            put("Participant Id", 64f); //NO CHECKSTYLE MagicNumber
            put("Stage ID", 32f);
            put("Gender", 48f); //NO CHECKSTYLE MagicNumber
            put("Age", 24f);
            put("SMS", 32f);
        }
    };

    public static final String VISIT_ENTITY = "Visit";
    public static final String SUBJECT_FIELD = "subject";
    public static final String SUBJECT_ID_FIELD = "subject.subjectId";
    public static final String STAGE_ID_FIELD = "subject.stageId";
    public static final String STAGE_ID_FIELD_DISPLAY_NAME = "Stage ID";

    public static final String VISIT_EXPORT_FILE_NAME = "Visit";
    public static final Map<String, String> VISIT_AVAILABLE_FIELDS_MAP = new LinkedHashMap<String, String>() {
        {
            put("Participant",          "subject.subjectId");
            put("Stage ID",             "subject.stageId");
            put("Planned Visit Date",   "motechProjectedDate");
            put("Actual Visit Date",    "date");
            put("Visit Type",           "type");
            put("Modification Date",    "modificationDate");
            put("Creation Date",        "creationDate");
            put("Modified By",          "modifiedBy");
            put("Created By",           "creator");
        }
    };

    public static final String MANAGE_EBODAC_PERMISSION = "manageEbodac";
    public static final String EMAIL_REPORTS_TAB_PERMISSION = "emailReports";

    public static final String HAS_EMAIL_REPORTS_TAB_ROLE = "hasRole('" + EMAIL_REPORTS_TAB_PERMISSION + "')";

    private EbodacConstants() {
    }
}
