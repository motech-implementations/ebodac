package org.motechproject.ebodac.domain;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.LocalDate;
import org.motechproject.ebodac.util.CustomDateDeserializer;
import org.motechproject.ebodac.util.CustomDateSerializer;
import org.motechproject.mds.annotations.Entity;
import org.motechproject.mds.annotations.Field;
import org.motechproject.mds.annotations.NonEditable;
import org.motechproject.mds.annotations.UIDisplayable;

import javax.jdo.annotations.Unique;

@Entity
public abstract class ReportVaccinationAbstract {

    public static final String DATE_PROPERTY_NAME = "date";

    @NonEditable
    @Unique
    @UIDisplayable(position = 0)
    @Field
    private LocalDate date;

    @NonEditable
    @UIDisplayable(position = 2)
    @Field
    private Integer adultMales;

    @NonEditable
    @UIDisplayable(position = 3)
    @Field
    private Integer adultFemales;

    @NonEditable
    @UIDisplayable(position = 8)
    @Field(displayName = "Children 1-5")
    private Integer children_1_5;

    @NonEditable
    @UIDisplayable(position = 7)
    @Field(displayName = "Children 6-11")
    private Integer children_6_11;

    @NonEditable
    @UIDisplayable(position = 6)
    @Field(displayName = "Children 12-17")
    private Integer children_12_17;

    @NonEditable
    @UIDisplayable(position = 4)
    @Field
    private Integer adultUnidentified;

    @NonEditable
    @UIDisplayable(position = 5)
    @Field
    private Integer adultUndifferentiated;

    @NonEditable(display = false)
    @Field
    private String owner;

    public ReportVaccinationAbstract() {
    }

    public ReportVaccinationAbstract(LocalDate date, Integer adultMales, Integer adultFemales, Integer children_1_5, Integer children_6_11,
                                     Integer children_12_17, Integer adultUnidentified, Integer adultUndifferentiated) {
        this.date = date;
        this.adultMales = adultMales;
        this.adultFemales = adultFemales;
        this.children_1_5 = children_1_5;
        this.children_6_11 = children_6_11;
        this.children_12_17 = children_12_17;
        this.adultUnidentified = adultUnidentified;
        this.adultUndifferentiated = adultUndifferentiated;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public LocalDate getDate() {
        return date;
    }

    @JsonDeserialize(using = CustomDateDeserializer.class)
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getAdultMales() {
        return adultMales;
    }

    public void setAdultMales(Integer adultMales) {
        this.adultMales = adultMales;
    }

    public Integer getAdultFemales() {
        return adultFemales;
    }

    public void setAdultFemales(Integer adultFemales) {
        this.adultFemales = adultFemales;
    }

    public Integer getChildren_1_5() {
        return children_1_5;
    }

    public void setChildren_1_5(Integer children_1_5) {
        this.children_1_5 = children_1_5;
    }

    public Integer getChildren_6_11() {
        return children_6_11;
    }

    public void setChildren_6_11(Integer children_6_11) {
        this.children_6_11 = children_6_11;
    }

    public Integer getChildren_12_17() {
        return children_12_17;
    }

    public void setChildren_12_17(Integer children_12_17) {
        this.children_12_17 = children_12_17;
    }

    public Integer getAdultUnidentified() {
        return adultUnidentified;
    }

    public void setAdultUnidentified(Integer adultUnidentified) {
        this.adultUnidentified = adultUnidentified;
    }

    public Integer getAdultUndifferentiated() {
        return adultUndifferentiated;
    }

    public void setAdultUndifferentiated(Integer adultUndifferentiated) {
        this.adultUndifferentiated = adultUndifferentiated;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void updateReportData(Integer adultMales, Integer adultFemales, Integer children_1_5, Integer children_6_11,
                                 Integer children_12_17, Integer adultUnidentified, Integer adultUndifferentiated) {
        this.adultMales = adultMales;
        this.adultFemales = adultFemales;
        this.children_1_5 = children_1_5;
        this.children_6_11 = children_6_11;
        this.children_12_17 = children_12_17;
        this.adultUnidentified = adultUnidentified;
        this.adultUndifferentiated = adultUndifferentiated;
    }
}
