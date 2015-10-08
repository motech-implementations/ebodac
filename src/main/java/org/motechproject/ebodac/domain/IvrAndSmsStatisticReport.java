package org.motechproject.ebodac.domain;

import org.joda.time.DateTime;
import org.motechproject.mds.annotations.Access;
import org.motechproject.mds.annotations.Entity;
import org.motechproject.mds.annotations.Field;
import org.motechproject.mds.annotations.NonEditable;
import org.motechproject.mds.util.SecurityMode;

@Access(value = SecurityMode.PERMISSIONS, members = {"manageEbodac"})
@Entity
public class IvrAndSmsStatisticReport {

    private Long id;

    @Field(displayName = "participant")
    private Subject subject;

    @Field
    private String messageID;

    @NonEditable
    @Field
    private DateTime sendDate;

    @NonEditable
    @Field
    private double expectedDuration;

    @Field
    private double timeListenedTo;

    @NonEditable
    @Field
    private DateTime receivedDate;

    @NonEditable
    @Field
    private int numberOfAttempts;

    @NonEditable
    @Field
    private String sms;

    @NonEditable
    @Field
    private DateTime smsReceivedDate;

    public IvrAndSmsStatisticReport(Subject subject, String messageID, DateTime sendDate, double expectedDuration, double timeListenedTo, DateTime receivedDate, int numberOfAttempts, String sms, DateTime smsReceivedDate) {
        this.subject = subject;
        this.messageID = messageID;
        this.sendDate = sendDate;
        this.expectedDuration = expectedDuration;
        this.timeListenedTo = timeListenedTo;
        this.receivedDate = receivedDate;
        this.numberOfAttempts = numberOfAttempts;
        this.sms = sms;
        this.smsReceivedDate = smsReceivedDate;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public DateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(DateTime sendDate) {
        this.sendDate = sendDate;
    }

    public double getExpectedDuration() {
        return expectedDuration;
    }

    public void setExpectedDuration(double expectedDuration) {
        this.expectedDuration = expectedDuration;
    }

    public double getTimeListenedTo() {
        return timeListenedTo;
    }

    public void setTimeListenedTo(double timeListenedTo) {
        this.timeListenedTo = timeListenedTo;
    }

    public DateTime getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(DateTime receivedDate) {
        this.receivedDate = receivedDate;
    }

    public int getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public void setNumberOfAttempts(int numberOfAttempts) {
        this.numberOfAttempts = numberOfAttempts;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public DateTime getSmsReceivedDate() {
        return smsReceivedDate;
    }

    public void setSmsReceivedDate(DateTime smsReceivedDate) {
        this.smsReceivedDate = smsReceivedDate;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

}
