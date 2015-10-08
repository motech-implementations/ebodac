package org.motechproject.ebodac.template;

import java.io.OutputStream;

/**
 * Created by mikolaj on 15.10.15.
 */
public class XlsTemplateC extends XlsBasicTemplate{

    private static final String XLS_TEMPLATE_PATH = "";
    private static final int INDEX_OF_HEADER_ROW = 1;

    public XlsTemplateC(OutputStream outputStream) {
        super(XLS_TEMPLATE_PATH, INDEX_OF_HEADER_ROW, outputStream);
    }
}
