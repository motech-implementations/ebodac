package org.motechproject.bookingapp.template;

import com.itextpdf.text.Rectangle;
import org.motechproject.ebodac.template.PdfBasicTemplate;

import java.io.OutputStream;

public class PdfReportTemplate extends PdfBasicTemplate {

    private static final String TEMPLATE_PATH = "/report_template.pdf";
    private static final Rectangle FIRST_PAGE_RECTANGLE = new Rectangle(20, 36, 580, 580);

    public PdfReportTemplate(OutputStream outputStream) {
        super(TEMPLATE_PATH, FIRST_PAGE_RECTANGLE, outputStream);
    }

}
