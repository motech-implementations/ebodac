package org.motechproject.ebodac.service.impl;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.motechproject.ebodac.constants.EbodacConstants;
import org.motechproject.ebodac.domain.Visit;
import org.motechproject.ebodac.service.ExportService;
import org.motechproject.ebodac.service.VisitEntityService;
import org.motechproject.ebodac.web.domain.Records;
import org.motechproject.mds.query.QueryParams;
import org.motechproject.mds.service.impl.csv.writer.CsvTableWriter;
import org.motechproject.mds.service.impl.csv.writer.PdfTableWriter;
import org.motechproject.mds.service.impl.csv.writer.TableWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("exportService")
public class ExportServiceImpl implements ExportService {

    @Autowired
    private VisitEntityService visitEntityService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void exportDailyClinicVisitScheduleReportToPDF(OutputStream outputStream, String lookup,
                                                          String lookupFields, QueryParams queryParams) throws IOException {
        PdfTableWriter tableWriter = new PdfTableWriter(outputStream);
        exportDailyClinicVisitScheduleReport(tableWriter,lookup,lookupFields,queryParams);
    }

    @Override
    public void exportDailyClinicVisitScheduleReportToPDF(OutputStream outputStream) throws IOException {
        this.exportDailyClinicVisitScheduleReportToPDF(outputStream, null, null, null);
    }

    @Override
    public void exportDailyClinicVisitScheduleReportToCSV(Writer writer, String lookup,
                                                          String lookupFields, QueryParams queryParams) throws IOException {
        CsvTableWriter tableWriter = new CsvTableWriter(writer);
        exportDailyClinicVisitScheduleReport(tableWriter,lookup,lookupFields,queryParams);
    }

    @Override
    public void exportDailyClinicVisitScheduleReportToCSV(Writer writer) throws IOException {
        this.exportDailyClinicVisitScheduleReportToCSV(writer, null, null, null);
    }

    private void exportDailyClinicVisitScheduleReport(TableWriter tableWriter,String lookup,
                                                      String lookupFields, QueryParams queryParams) throws IOException {
        Records<Visit> records = visitEntityService.getVisits(lookup, lookupFields, queryParams);
        List<Visit> visits = records.getRows();
        Set<String> keys = EbodacConstants.DAILY_CLINIC_VISIT_SCHEDULE_REPORT_MAP.keySet();
        String[] fields = keys.toArray(new String[keys.size()]);
        try {
            tableWriter.writeHeader(fields);
            for (Visit visit : visits) {
                Map<String, String> row = buildRow(visit, EbodacConstants.DAILY_CLINIC_VISIT_SCHEDULE_REPORT_MAP);
                tableWriter.writeRow(row,fields);
            }
        }catch (IOException e) {
            throw new IOException("IO Error when writing data", e);
        }finally {
            tableWriter.close();
        }
    }

    private Map<String, String> buildRow(Visit visit, Map<String, String> headerMap) throws IOException {
        String json = objectMapper.writeValueAsString(visit);
        Map<String, Object> visitMap = objectMapper.readValue(json, new TypeReference<HashMap>() {});
        Map<String, Object> subjectMap = (Map<String, Object>)visitMap.get("subject");
        Map<String, String> row = new HashMap<>();

        for(Map.Entry<String, String> entry : headerMap.entrySet()) {
            String value = (String)visitMap.get(entry.getValue());
            if(value == null) {
                value = (String)subjectMap.get(entry.getValue());
            }
            row.put(entry.getKey(), value);
        }
        return  row;
    }
}
