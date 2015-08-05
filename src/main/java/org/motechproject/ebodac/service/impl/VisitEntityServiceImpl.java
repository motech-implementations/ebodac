package org.motechproject.ebodac.service.impl;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.motechproject.commons.api.Range;
import org.motechproject.ebodac.domain.Visit;
import org.motechproject.ebodac.domain.VisitType;
import org.motechproject.ebodac.repository.VisitDataService;
import org.motechproject.ebodac.service.VisitEntityService;
import org.motechproject.ebodac.web.domain.Records;
import org.motechproject.mds.query.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("visitEntityService")
public class VisitEntityServiceImpl implements VisitEntityService {

    @Autowired
    private VisitDataService visitDataService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Records<Visit> getVisits(String lookup, String lookupFields, QueryParams queryParams) throws IOException {
        DateTimeFormatter lookupDateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd");

        List<Visit> visits;
        long recordCount;
        int rowCount;

        if (lookup != null && queryParams != null) {
            Map<String, Object> fields = getFields(lookupFields);
            switch (lookup) {
                case "Find Visit By Date":
                    LocalDate date = LocalDate.parse((String) fields.get("Date"), lookupDateTimeFormat);
                    visits = visitDataService.findVisitByDate(date, queryParams);

                    recordCount = visitDataService.countFindVisitByDate(date);
                    rowCount = (int) Math.ceil(recordCount / (double) queryParams.getPageSize());

                    return new Records<>(queryParams.getPage(), rowCount, (int) recordCount, visits);
                case "Find Visit By Date And Type":
                    date = LocalDate.parse((String) fields.get("Date"), lookupDateTimeFormat);
                    VisitType type = VisitType.getByValue((String) fields.get("Type"));

                    visits = visitDataService.findVisitByDateAndType(date, type, queryParams);
                    recordCount = visitDataService.countFindVisitByDateAndType(date, type);
                    rowCount = (int) Math.ceil(recordCount / (double) queryParams.getPageSize());

                    return new Records<>(queryParams.getPage(), rowCount, (int) recordCount, visits);
                case "Find Visits By Date Range":
                    Map<String, Object> rangeMap = (Map<String, Object>) fields.get("Date Range");
                    LocalDate min = LocalDate.parse((String)rangeMap.get("min"));
                    LocalDate max = LocalDate.parse((String)rangeMap.get("max"));
                    Range<LocalDate> range = new Range<>(min, max);

                    visits = visitDataService.findVisitsByDateRange(range, queryParams);
                    recordCount = visitDataService.countFindVisitsByDateRange(range);
                    rowCount = (int) Math.ceil(recordCount / (double) queryParams.getPageSize());

                    return new Records<>(queryParams.getPage(), rowCount, (int) recordCount, visits);
                case "Find Visits By Date Range And Type":
                    rangeMap = (Map<String, Object>) fields.get("Date Range");
                    min = LocalDate.parse((String)rangeMap.get("min"));
                    max = LocalDate.parse((String)rangeMap.get("max"));
                    type = VisitType.getByValue((String) fields.get("Type"));
                    range = new Range<>(min, max);

                    visits = visitDataService.findVisitsByDateRangeAndType(range,type,queryParams);
                    recordCount = visitDataService.countFindVisitsByDateRangeAndType(range, type);
                    rowCount = (int) Math.ceil(recordCount / (double) queryParams.getPageSize());

                    return new Records<>(queryParams.getPage(), rowCount, (int) recordCount, visits);
                case "Find Visit By Type":
                    type = VisitType.getByValue((String) fields.get("Type"));
                    visits = visitDataService.findVisitByType(type, queryParams);

                    recordCount = visitDataService.countFindVisitByType(type);
                    rowCount = (int) Math.ceil(recordCount / (double) queryParams.getPageSize());

                    return new Records<>(queryParams.getPage(), rowCount, (int) recordCount, visits);
                case "Find Visit By SubjectId":
                    String subjectId = (String) fields.get("SubjectId");
                    visits = visitDataService.findVisitBySubjectId(subjectId, queryParams);

                    recordCount = visitDataService.countFindVisitBySubjectId(subjectId);
                    rowCount = (int) Math.ceil(recordCount / (double) queryParams.getPageSize());

                    return new Records<>(queryParams.getPage(), rowCount, (int) recordCount, visits);
                case "Find Visit By Subject Name":
                    String name = (String) fields.get("Name");
                    visits = visitDataService.findVisitBySubjectName(name, queryParams);

                    recordCount = visitDataService.countFindVisitBySubjectName(name);
                    rowCount = (int) Math.ceil(recordCount / (double) queryParams.getPageSize());

                    return new Records<>(queryParams.getPage(), rowCount, (int) recordCount, visits);
                case "Find Visit By Subject Address":
                    String address = (String) fields.get("Address");
                    visits = visitDataService.findVisitBySubjectAddress(address, queryParams);

                    recordCount = visitDataService.countFindVisitBySubjectAddress(address);
                    rowCount = (int) Math.ceil(recordCount / (double) queryParams.getPageSize());

                    return new Records<>(queryParams.getPage(), rowCount, (int) recordCount, visits);
            }
        }
        recordCount = visitDataService.count();
        int page;
        if(queryParams.getPageSize() != null && queryParams.getPage() != null) {
            rowCount = (int) Math.ceil(recordCount / (double) queryParams.getPageSize());
            page = queryParams.getPage();
            visits = visitDataService.retrieveAll(queryParams);
        } else {
            rowCount = (int) recordCount;
            page = 1;
            visits = visitDataService.retrieveAll(queryParams);
        }
        return new Records<>(page, rowCount, (int) recordCount, visits);
    }

    private Map<String, Object> getFields(String lookupFields) throws IOException {
        return objectMapper.readValue(lookupFields, new TypeReference<HashMap>() {});
    }
}
