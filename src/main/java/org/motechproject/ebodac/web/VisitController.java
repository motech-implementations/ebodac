package org.motechproject.ebodac.web;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.motechproject.ebodac.domain.Subject;
import org.motechproject.ebodac.domain.Visit;
import org.motechproject.ebodac.domain.VisitType;
import org.motechproject.ebodac.repository.SubjectDataService;
import org.motechproject.ebodac.repository.VisitDataService;
import org.motechproject.ebodac.web.domain.GridSettings;
import org.motechproject.ebodac.web.domain.Records;
import org.motechproject.mds.query.QueryParams;
import org.motechproject.mds.util.Constants;
import org.motechproject.mds.util.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class VisitController {

    @Autowired
    private VisitDataService visitDataService;

    @Autowired
    private SubjectDataService subjectDataService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/visitsRecords", method = RequestMethod.POST)
    @PreAuthorize(Constants.Roles.HAS_DATA_ACCESS)
    @ResponseBody
    public Records<?> getVisits(GridSettings settings) throws IOException {
        Order order = null;
        if (!settings.getSortColumn().isEmpty()) {
            order = new Order(settings.getSortColumn(), settings.getSortDirection());
        }

        QueryParams queryParams = new QueryParams(settings.getPage(), settings.getRows(), order);

        Subject subject;
        List<Subject> subjects;
        int startRow = (settings.getPage() - 1) * settings.getRows();
        int endRow = settings.getPage() * settings.getRows();
        long recordCount;
        int rowCount;

        DateTimeFormatter lookupDateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm Z");

        if (settings.getLookup() != null) {
            Map<String, Object> fields = getFields(settings);
            switch (settings.getLookup()) {
                case "Find Visit By Date":
                    DateTime date = DateTime.parse((String) fields.get("Date"), lookupDateTimeFormat);
                    List<Visit> visits = visitDataService.findVisitByDate(date, queryParams);

                    recordCount = visitDataService.countFindVisitByDate(date);
                    rowCount = (int) Math.ceil(recordCount / (double) settings.getRows());

                    return new Records<>(settings.getPage(), rowCount, (int) recordCount, visits);
                case "Find Visit By Type":
                    VisitType type = VisitType.getByValue((String) fields.get("Type"));
                    visits = visitDataService.findVisitByType(type, queryParams);

                    recordCount = visitDataService.countFindVisitByType(type);
                    rowCount = (int) Math.ceil(recordCount / (double) settings.getRows());

                    return new Records<>(settings.getPage(), rowCount, (int) recordCount, visits);
                case "Find Visit By SubjectId":
                    String subjectId = (String) fields.get("SubjectId");
                    subject = subjectDataService.findSubjectBySubjectId(subjectId);
                    visits = subject.getVisits();

                    recordCount = visits.size();
                    rowCount = (int) Math.ceil(recordCount / (double) settings.getRows());

                    if (recordCount < endRow) {
                        endRow = (int) recordCount;
                    }

                    visits = visits.subList(startRow, endRow);

                    return new Records<>(settings.getPage(), rowCount, (int) recordCount, visits);
                case "Find Visit By Subject Name":
                    String name = (String) fields.get("Name");
                    subjects = subjectDataService.findSubjectByName(name);

                    visits = new ArrayList<>();
                    for (Subject s : subjects) {
                        visits.addAll(s.getVisits());
                    }

                    recordCount = visits.size();
                    rowCount = (int) Math.ceil(recordCount / (double) settings.getRows());

                    if (recordCount < endRow) {
                        endRow = (int) recordCount;
                    }

                    visits = visits.subList(startRow, endRow);

                    return new Records<>(settings.getPage(), rowCount, (int) recordCount, visits);
                case "Find Visit By Subject Address":
                    String address = (String) fields.get("Address");
                    subjects = subjectDataService.findSubjectsByAddress(address);

                    visits = new ArrayList<>();
                    for (Subject s : subjects) {
                        visits.addAll(s.getVisits());
                    }

                    recordCount = visits.size();
                    rowCount = (int) Math.ceil(recordCount / (double) settings.getRows());

                    if (recordCount < endRow) {
                        endRow = (int) recordCount;
                    }

                    visits = visits.subList(startRow, endRow);

                    return new Records<>(settings.getPage(), rowCount, (int) recordCount, visits);
            }
        }

        recordCount = visitDataService.count();
        rowCount = (int) Math.ceil(recordCount / (double) settings.getRows());

        List<Visit> visits = visitDataService.retrieveAll(queryParams);
        return new Records<>(settings.getPage(), rowCount, (int) recordCount, visits);
    }

    private Map<String, Object> getFields(GridSettings gridSettings) throws IOException {
        return objectMapper.readValue(gridSettings.getFields(), new TypeReference<HashMap>() {});
    }
}
