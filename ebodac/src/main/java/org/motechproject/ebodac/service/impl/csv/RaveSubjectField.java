package org.motechproject.ebodac.service.impl.csv;


public enum RaveSubjectField {
    SiteNumber("siteId"),
    Site("siteName"),
    Subject("subjectId"),
    PHONE("phoneNumber"),
    LANG("language"),
    BRTHDT("dateOfBirth"),
    SEX("gender"),
    STAGE("stageId"),
    PRMDT("primerVaccinationDate"),
    BOOSTDT("boosterVaccinationDate"),
    VACDSDT("dateOfDisconVac"),
    TRDSDT("dateOfDisconStd");

    private String value;

    private RaveSubjectField(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
