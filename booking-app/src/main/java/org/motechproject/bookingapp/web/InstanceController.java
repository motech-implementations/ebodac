package org.motechproject.bookingapp.web;


import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.motechproject.bookingapp.constants.BookingAppConstants;
import org.motechproject.bookingapp.domain.Screening;
import org.motechproject.bookingapp.template.PdfReportTemplate;
import org.motechproject.bookingapp.template.XlsReportTemplate;
import org.motechproject.ebodac.constants.EbodacConstants;
import org.motechproject.ebodac.exception.EbodacExportException;
import org.motechproject.ebodac.exception.EbodacLookupException;
import org.motechproject.ebodac.service.ExportService;
import org.motechproject.ebodac.template.PdfBasicTemplate;
import org.motechproject.ebodac.template.XlsBasicTemplate;
import org.motechproject.ebodac.util.QueryParamsBuilder;
import org.motechproject.ebodac.web.domain.GridSettings;
import org.motechproject.mds.query.QueryParams;
import org.motechproject.mds.service.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.apache.commons.lang.CharEncoding.UTF_8;

@Controller
public class InstanceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceController.class);

    @Autowired
    private EntityService entityService;

    @Autowired
    private ExportService exportService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/exportInstances/screening", method = RequestMethod.GET)
    public void exportScreening(GridSettings settings,
                                                     @RequestParam String exportRecords,
                                                     @RequestParam String outputFormat,
                                                     HttpServletResponse response) throws IOException {

        exportEntity(settings, exportRecords, outputFormat, response, BookingAppConstants.SCREENING_REPORT_NAME,
                null, Screening.class, BookingAppConstants.SCREENING_REPORT_MAP, settings.getFields());
    }

    private void exportEntity(GridSettings settings, String exportRecords, String outputFormat, HttpServletResponse response,
                              String fileNameBeginning, Class<?> entityDtoType, Class<?> entityType, Map<String, String> headerMap,
                              String oldLookupFields) throws IOException {

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");
        final String fileName = fileNameBeginning + "_" + DateTime.now().toString(dateTimeFormatter);

        if (EbodacConstants.PDF_EXPORT_FORMAT.equals(outputFormat)) {
            response.setContentType("application/pdf");
        } else if(EbodacConstants.CSV_EXPORT_FORMAT.equals(outputFormat)) {
            response.setContentType("text/csv");
        } else if(EbodacConstants.XLS_EXPORT_FORMAT.equals(outputFormat)) {
            response.setContentType("application/vnd.ms-excel");
        } else {
            throw new IllegalArgumentException("Invalid export format: " + outputFormat);
        }
        response.setCharacterEncoding(UTF_8);
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=" + fileName + "." + outputFormat.toLowerCase());

        QueryParams queryParams = new QueryParams(1, StringUtils.equalsIgnoreCase(exportRecords, "all") ? null : Integer.valueOf(exportRecords),
                QueryParamsBuilder.buildOrderList(settings, getFields(settings)));

        try {
            if (EbodacConstants.PDF_EXPORT_FORMAT.equals(outputFormat)) {
                PdfBasicTemplate template = new PdfReportTemplate(response.getOutputStream());

                exportService.exportEntityToPDF(template, entityDtoType, entityType, headerMap,
                        settings.getLookup(), settings.getFields(), queryParams);
            } else if(EbodacConstants.CSV_EXPORT_FORMAT.equals(outputFormat)) {
                exportService.exportEntityToCSV(response.getWriter(), entityDtoType, entityType, headerMap,
                        settings.getLookup(), settings.getFields(), queryParams);
            } else if(EbodacConstants.XLS_EXPORT_FORMAT.equals(outputFormat)) {
                XlsBasicTemplate template = new XlsReportTemplate(response.getOutputStream());

                exportService.exportEntityToExcel(template, entityDtoType, entityType, headerMap,
                        settings.getLookup(), settings.getFields(), queryParams);
            }
        } catch (IOException | EbodacLookupException | EbodacExportException e) {
            LOGGER.debug(e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private Map<String, Object> getFields(GridSettings gridSettings) throws IOException {
        if (gridSettings.getFields() == null) {
            return null;
        } else {
            return objectMapper.readValue(gridSettings.getFields(), new TypeReference<LinkedHashMap>() {});
        }
    }

}
