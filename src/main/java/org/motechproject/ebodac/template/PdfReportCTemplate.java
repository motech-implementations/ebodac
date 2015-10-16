package org.motechproject.ebodac.template;

import com.itextpdf.text.Rectangle;

import java.io.OutputStream;

/**
 * Created by mikolaj on 15.10.15.
 */
public class PdfReportCTemplate extends PdfBasicTemplate {

    private static final String TEMPLATE_PATH = "/report_templateC.pdf";
    private static final Rectangle FIRST_PAGE_RECTANGLE = new Rectangle(20, 36, 580, 475);

    public PdfReportCTemplate(OutputStream outputStream) {
        super(TEMPLATE_PATH, FIRST_PAGE_RECTANGLE, outputStream);
        setNextPageRectangle(FIRST_PAGE_RECTANGLE);
    }

}
