package org.motechproject.ebodac.domain.enums;

import org.apache.commons.lang.StringUtils;

public enum VisitType {
    SCREENING("Screening"),
    PRIME_VACCINATION_DAY("Prime Vaccination Day"),
    PRIME_VACCINATION_FIRST_FOLLOW_UP_VISIT("Prime Vaccination First Follow-up visit"),
    PRIME_VACCINATION_SECOND_FOLLOW_UP_VISIT("Prime Vaccination Second Follow-up visit"),
    BOOST_VACCINATION_DAY("Boost Vaccination Day"),
    BOOST_VACCINATION_FIRST_FOLLOW_UP_VISIT("Boost Vaccination First Follow-up visit"),
    BOOST_VACCINATION_SECOND_FOLLOW_UP_VISIT("Boost Vaccination Second Follow-up visit"),
    BOOST_VACCINATION_THIRD_FOLLOW_UP_VISIT("Boost Vaccination Third Follow-up visit"),
    FIRST_LONG_TERM_FOLLOW_UP_VISIT("First Long-term Follow-up visit"),
    SECOND_LONG_TERM_FOLLOW_UP_VISIT("Second Long-term Follow-up visit"),
    THIRD_LONG_TERM_FOLLOW_UP_VISIT("Third Long-term Follow-up visit", "LFU3 (D540PP)"),
    FOURTH_LONG_TERM_FOLLOW_UP_VISIT("Fourth Long-term Follow-up visit", "LFU4 (18MPP)", "LFU4 (2YPP)"),
    FIFTH_LONG_TERM_FOLLOW_UP_VISIT("Fifth Long-term Follow-up visit", "LFU5 (24MPP)"),
    SIXTH_LONG_TERM_FOLLOW_UP_VISIT("Sixth Long-term Follow-up visit", "LFU6 (30MPP)"),
    SEVENTH_LONG_TERM_FOLLOW_UP_VISIT("Seventh Long-term Follow-up visit", "LFU7 (36MPP)"),
    THIRD_VACCINATION_DAY("Third Vaccination Day", "2YPP"),
    FIRST_POST_THIRD_VACCINATION_VISIT("First Post Third Vaccination visit", "D5P3V"),
    SECOND_POST_THIRD_VACCINATION_VISIT("Second Post Third Vaccination visit", "D8P3V"),
    THIRD_POST_THIRD_VACCINATION_VISIT("Third Post Third Vaccination visit", "D22P3V"),
    FOURTH_POST_THIRD_VACCINATION_VISIT("Fourth Post Third Vaccination visit", "6MP3V"),
    FIFTH_POST_THIRD_VACCINATION_VISIT("Fifth Post Third Vaccination visit", "D360P3V"),
    UNSCHEDULED_VISIT("Unscheduled Visit");

    private String motechValue;
    private String[] typeValues;

    VisitType(String... values) {
        this.typeValues = values;
        this.motechValue = values[0];
    }

    public static VisitType getByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        if (value.startsWith(UNSCHEDULED_VISIT.getMotechValue())) {
            return UNSCHEDULED_VISIT;
        }
        for (VisitType visitType : VisitType.values()) {
            for (String typeValue : visitType.typeValues) {
                if (value.toLowerCase().startsWith(typeValue.toLowerCase())) {
                    return visitType;
                }
            }
        }
        return null;
    }

    public String getMotechValue() {
        return motechValue;
    }
}
