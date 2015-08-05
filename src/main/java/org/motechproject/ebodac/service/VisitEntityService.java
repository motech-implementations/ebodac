package org.motechproject.ebodac.service;


import org.motechproject.ebodac.domain.Visit;
import org.motechproject.ebodac.web.domain.Records;
import org.motechproject.mds.query.QueryParams;

import java.io.IOException;

public interface VisitEntityService {

    Records<Visit> getVisits(String lookup, String fields, QueryParams queryParams) throws IOException;
}
