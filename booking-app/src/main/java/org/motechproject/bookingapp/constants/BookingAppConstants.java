package org.motechproject.bookingapp.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public final class BookingAppConstants {

    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";

    public static final String SCREENING_REPORT_NAME = "Screening";

    public static final Map<String, String> SCREENING_REPORT_MAP = new LinkedHashMap<String, String>() {
        {
            put("Booking Id",        "volunteer.id");
            put("Volunteer Name",    "volunteer.name");
            put("Site Id",           "site.siteId");
            put("Clinic",            "clinic.location");
            put("Room",              "room.number");
            put("Date",              "date");
            put("Start Time",        "startTime");
            put("End Time",          "endTime");
        }
    };

    private BookingAppConstants(){};

}
