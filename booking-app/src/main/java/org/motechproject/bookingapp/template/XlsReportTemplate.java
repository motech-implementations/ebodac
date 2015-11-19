package org.motechproject.bookingapp.template;

import org.motechproject.ebodac.template.XlsBasicTemplate;

import java.io.OutputStream;

public class XlsReportTemplate extends XlsBasicTemplate {

    private static final String XLS_TEMPLATE_PATH = "/report_template.xls";
    private static final int INDEX_OF_HEADER_ROW = 9;

    public XlsReportTemplate(OutputStream outputStream) {
        super(XLS_TEMPLATE_PATH, INDEX_OF_HEADER_ROW, outputStream);
    }
}
