package org.motechproject.ebodac.service;

import org.joda.time.LocalDate;
import org.motechproject.ebodac.domain.Subject;
import org.motechproject.ebodac.domain.SubjectEnrollments;
import org.motechproject.ebodac.domain.Visit;

public interface EbodacEnrollmentService {

    void enrollScreening(Subject subject);

    void enrollSubject(Subject subject);

    void enrollSubject(String subjectId);

    void enrollSubjectToCampaign(String subjectId, String campaignName);

    void enrollSubjectToCampaignWithNewDate(String subjectId, String campaignName, LocalDate date);

    void enrollOrCompleteCampaignForSubject(Visit visit);

    void unenrollSubject(String subjectId);

    void unenrollSubject(String subjectId, String campaignName);

    void reenrollSubject(Visit visit);

    void reenrollSubjectWithNewDate(String subjectId, String campaignName, LocalDate date);

    void withdrawalOrEnrollSubject(Subject oldSubject, Subject newSubject);

    void completeCampaign(String subjectId, String campaignName);

    boolean checkIfEnrolledAndUpdateEnrollment(Visit visit);

    boolean isEnrolled(String subjectId);

    void createEnrollmentRecordsForSubject(Subject subject);

    void updateEnrollmentsWhenSubjectDataChanged(Subject newSubject, Subject oldSubject, boolean subjectImported);

    void unenrollAndRemoveEnrollment(Visit visit);

    void rollbackOrRemoveEnrollment(Visit visit);

    void enrollVisitRelatedCampaigns(Visit visit);

    SubjectEnrollments findSubjectEnrolments(String subjectId);
}
