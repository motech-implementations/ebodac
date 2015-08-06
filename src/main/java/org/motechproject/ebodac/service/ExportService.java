package org.motechproject.ebodac.service;


import org.motechproject.mds.query.QueryParams;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public interface ExportService {

    void exportDailyClinicVisitScheduleReportToPDF(OutputStream outputStream, String lookup,
                                                   String lookupFields, QueryParams queryParams) throws IOException;

    void exportDailyClinicVisitScheduleReportToPDF(OutputStream outputStream) throws IOException;

    void exportDailyClinicVisitScheduleReportToCSV(Writer writer, String lookup,
                                                   String lookupFields, QueryParams queryParams) throws IOException;

    void exportDailyClinicVisitScheduleReportToCSV(Writer writer) throws IOException;
}
