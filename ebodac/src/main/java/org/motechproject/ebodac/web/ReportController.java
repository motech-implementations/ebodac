package org.motechproject.ebodac.web;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.motechproject.ebodac.constants.EbodacConstants;
import org.motechproject.ebodac.domain.IvrAndSmsStatisticReport;
import org.motechproject.ebodac.dto.IvrAndSmsStatisticReportDto;
import org.motechproject.ebodac.dto.MissedVisitsReportDto;
import org.motechproject.ebodac.dto.OptsOutOfMotechMessagesReportDto;
import org.motechproject.ebodac.domain.Subject;
import org.motechproject.ebodac.domain.SubjectEnrollments;
import org.motechproject.ebodac.domain.Visit;
import org.motechproject.ebodac.exception.EbodacLookupException;
import org.motechproject.ebodac.helper.DtoLookupHelper;
import org.motechproject.ebodac.service.ConfigService;
import org.motechproject.ebodac.service.LookupService;
import org.motechproject.ebodac.service.ReportService;
import org.motechproject.ebodac.util.QueryParamsBuilder;
import org.motechproject.ebodac.web.domain.GridSettings;
import org.motechproject.ebodac.web.domain.Records;
import org.motechproject.mds.dto.LookupDto;
import org.motechproject.mds.query.QueryParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private LookupService lookupService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ConfigService configService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/generateReports", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('mdsDataAccess', 'manageEbodac')")
    @ResponseBody
    public ResponseEntity<String> generateReports(@RequestBody String startDate) {

        try {
            LocalDate date = LocalDate.parse(startDate, DateTimeFormat.forPattern(EbodacConstants.REPORT_DATE_FORMAT));
            reportService.generateDailyReportsFromDate(date);
            LOGGER.info("Reports generated by custom request from date: {}",
                    date.toString(DateTimeFormat.forPattern(EbodacConstants.REPORT_DATE_FORMAT)));
        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid date format", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error("Fatal error raised during creating reports", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/generateIvrAndSmsStatisticReports", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('mdsDataAccess', 'manageEbodac')")
    @ResponseBody
    public ResponseEntity<String> generateIvrAndSmsStatisticReports(@RequestBody String startDate) {

        try {
            if (StringUtils.isNotBlank(startDate)) {
                LocalDate date = LocalDate.parse(startDate, DateTimeFormat.forPattern(EbodacConstants.REPORT_DATE_FORMAT));
                reportService.generateIvrAndSmsStatisticReportsFromDate(date);
                LOGGER.info("Reports generated by custom request from date: {}",
                        date.toString(DateTimeFormat.forPattern(EbodacConstants.REPORT_DATE_FORMAT)));
            } else {
                reportService.generateIvrAndSmsStatisticReportsFromDate(null);
            }
        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid date format", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error("Fatal error raised during creating reports", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/getReport/{reportType}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('mdsDataAccess', 'manageEbodac')")
    @ResponseBody
    public Records<?> getReport(@PathVariable String reportType, GridSettings settings) {
        switch (reportType) {
            case "dailyClinicVisitScheduleReport" :
                return getDailyClinicVisitScheduleReport(settings);
            case "followupsAfterPrimeInjectionReport" :
                return getFollowupsAfterPrimeInjectionReport(settings);
            case "followupsMissedClinicVisitsReport" :
                return getFollowupsMissedClinicVisitsReport(settings);
            case "MandEMissedClinicVisitsReport" :
                return getMandEMissedClinicVisitsReport(settings);
            case "optsOutOfMotechMessagesReport" :
                return getOptsOutOfMotechMessagesReport(settings);
            case "ivrAndSmsStatisticReport" :
                return getIvrAndSmsStatisticReport(settings);
            case "screeningReport" :
                return getScreeningReport(settings);
            case "day8AndDay57Report":
                return getDay8AndDay57Report(settings);
            default:
                return null;
        }
    }

    @RequestMapping(value = "/getReportModel/{reportType}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('mdsDataAccess', 'manageEbodac')")
    @ResponseBody
    public String getReportModel(@PathVariable String reportType) throws IOException {
        String json = IOUtils.toString(getClass().getResourceAsStream("/reportModels.json"), "UTF-8");
        Map<String, Object> modelsMap = getFields(json);
        return objectMapper.writeValueAsString(modelsMap.get(reportType));
    }

    @RequestMapping(value = "/getLookupsForDailyClinicVisitScheduleReport", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('mdsDataAccess', 'manageEbodac')")
    @ResponseBody
    public List<LookupDto> getLookupsForDailyClinicVisitScheduleReport() {
        List<LookupDto> ret = new ArrayList<>();
        List<LookupDto> availableLookups;
        try {
            availableLookups = lookupService.getAvailableLookups(Visit.class.getName());
        } catch (EbodacLookupException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        List<String> lookupList = configService.getConfig().getAvailableLookupsForDailyClinicVisitScheduleReport();
        for (LookupDto lookupDto : availableLookups) {
            if (lookupList.contains(lookupDto.getLookupName())) {
                ret.add(lookupDto);
            }
        }
        return ret;
    }

    @RequestMapping(value = "/getLookupsForScreeningReport", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('mdsDataAccess', 'manageEbodac')")
    @ResponseBody
    public List<LookupDto> getLookupsForScreeningReport() {
        List<LookupDto> ret = new ArrayList<>();
        List<LookupDto> availableLookups;
        try {
            availableLookups = lookupService.getAvailableLookups(Visit.class.getName());
        } catch (EbodacLookupException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        List<String> lookupList = configService.getConfig().getAvailableLookupsForScreeningReport();
        for (LookupDto lookupDto : availableLookups) {
            if (lookupList.contains(lookupDto.getLookupName())) {
                ret.add(lookupDto);
            }
        }
        return ret;
    }

    @RequestMapping(value = "/getLookupsForFollowupsAfterPrimeInjectionReport", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('mdsDataAccess', 'manageEbodac')")
    @ResponseBody
    public List<LookupDto> getLookupsForFollowupsAfterPrimeInjectionReport() {
        List<LookupDto> ret = new ArrayList<>();
        List<LookupDto> availableLookups;
        try {
            availableLookups = lookupService.getAvailableLookups(Visit.class.getName());
        } catch (EbodacLookupException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        List<String> lookupList = configService.getConfig().getAvailableLookupsForFollowupsAfterPrimeInjectionReport();
        for (LookupDto lookupDto : availableLookups) {
            if (lookupList.contains(lookupDto.getLookupName())) {
                ret.add(lookupDto);
            }
        }
        return ret;
    }

    @RequestMapping(value = "/getLookupsForFollowupsMissedClinicVisitsReport", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('mdsDataAccess', 'manageEbodac')")
    @ResponseBody
    public List<LookupDto> getLookupsForFollowupsMissedClinicVisitsReport() {
        List<LookupDto> ret = new ArrayList<>();
        List<LookupDto> availableLookups;
        try {
            availableLookups = lookupService.getAvailableLookups(Visit.class.getName());
        } catch (EbodacLookupException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        List<String> lookupList = configService.getConfig().getAvailableLookupsForFollowupsMissedClinicVisitsReport();
        for (LookupDto lookupDto : availableLookups) {
            if (lookupList.contains(lookupDto.getLookupName())) {
                ret.add(lookupDto);
            }
        }
        return ret;
    }

    @RequestMapping(value = "/getLookupsForMandEMissedClinicVisitsReport", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('mdsDataAccess', 'manageEbodac')")
    @ResponseBody
    public List<LookupDto> getLookupsForMandEMissedClinicVisitsReport() {
        List<LookupDto> ret = new ArrayList<>();
        List<LookupDto> availableLookups;
        try {
            availableLookups = lookupService.getAvailableLookups(Visit.class.getName());
        } catch (EbodacLookupException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        List<String> lookupList = configService.getConfig().getAvailableLookupsForMandEMissedClinicVisitsReport();
        for (LookupDto lookupDto : availableLookups) {
            if (lookupList.contains(lookupDto.getLookupName())) {
                ret.add(lookupDto);
            }
        }
        return ret;
    }

    @RequestMapping(value = "/getLookupsForOptsOutOfMotechMessagesReport", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('mdsDataAccess', 'manageEbodac')")
    @ResponseBody
    public List<LookupDto> getLookupsForOptsOutOfMotechMessagesReport() {
        List<LookupDto> ret = new ArrayList<>();
        List<LookupDto> availableLookups;
        try {
            availableLookups = lookupService.getAvailableLookups(SubjectEnrollments.class.getName());
        } catch (EbodacLookupException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        DtoLookupHelper.addLookupForOptsOutOfMotechMessagesReport(availableLookups);
        List<String> lookupList = configService.getConfig().getAvailableLookupsForOptsOutOfMotechMessagesReport();

        for (LookupDto lookupDto : availableLookups) {
            if (lookupList.contains(lookupDto.getLookupName())) {
                ret.add(lookupDto);
            }
        }
        return ret;
    }

    @RequestMapping(value = "/getLookupsForIvrAndSmsStatisticReport", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('mdsDataAccess', 'manageEbodac')")
    @ResponseBody
    public List<LookupDto> getLookupsForIvrAndSmsStatisticReport() {
        List<LookupDto> ret = new ArrayList<>();
        List<LookupDto> availableLookups;
        try {
            availableLookups =  lookupService.getAvailableLookups(IvrAndSmsStatisticReport.class.getName());
        } catch (EbodacLookupException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        List<String> lookupList = configService.getConfig().getAvailableLookupsForIvrAndSmsStatisticReport();
        for (LookupDto lookupDto : availableLookups) {
            if (lookupList.contains(lookupDto.getLookupName())) {
                ret.add(lookupDto);
            }
        }
        return ret;
    }

    @RequestMapping(value = "/getLookupsForVisits", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('mdsDataAccess', 'manageEbodac')")
    @ResponseBody
    public List<LookupDto> getLookupsForVisits() {
        List<LookupDto> ret = new ArrayList<>();
        List<LookupDto> availableLookups;
        try {
            availableLookups = lookupService.getAvailableLookups(Visit.class.getName());
        } catch (EbodacLookupException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        List<String> lookupList = configService.getConfig().getAvailableLookupsForVisits();
        for (LookupDto lookupDto : availableLookups) {
            if (lookupList.contains(lookupDto.getLookupName())) {
                ret.add(lookupDto);
            }
        }
        return ret;
    }

    @RequestMapping(value = "/getLookupsForSubjects", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('mdsDataAccess', 'manageEbodac')")
    @ResponseBody
    public List<LookupDto> getLookupsForSubjects() {
        List<LookupDto> ret = new ArrayList<>();
        List<LookupDto> availableLookups;
        try {
            availableLookups = lookupService.getAvailableLookups(Subject.class.getName());
        } catch (EbodacLookupException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        List<String> lookupList = configService.getConfig().getAvailableLookupsForSubjects();
        for (LookupDto lookupDto : availableLookups) {
            if (lookupList.contains(lookupDto.getLookupName())) {
                ret.add(lookupDto);
            }
        }
        return ret;
    }

    @RequestMapping(value = "/getLookupsForDay8AndDay57Report", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('mdsDataAccess', 'manageEbodac')")
    @ResponseBody
    public List<LookupDto> getLookupsForDay8AndDay57Report() {
        List<LookupDto> ret = new ArrayList<>();
        List<LookupDto> availableLookups;
        try {
            availableLookups = lookupService.getAvailableLookups(Visit.class.getName());
        } catch (EbodacLookupException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        List<String> lookupList = configService.getConfig().getAvailableLookupsForDay8AndDay57Report();
        for (LookupDto lookupDto : availableLookups) {
            if (lookupList.contains(lookupDto.getLookupName())) {
                ret.add(lookupDto);
            }
        }
        return DtoLookupHelper.changeLookupFieldsForDay8AndDay57Report(ret);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return e.getMessage();
    }

    private Records<?> getDailyClinicVisitScheduleReport(GridSettings settings) {
        try {
            QueryParams queryParams = QueryParamsBuilder.buildQueryParams(settings, getFields(settings.getFields()));
            return lookupService.getEntities(Visit.class, settings.getLookup(), settings.getFields(), queryParams);
        } catch (IOException | EbodacLookupException e) {
            LOGGER.error(e.getMessage(), e);
            return new Records<Object>(null);
        }
    }

    private Records<?> getFollowupsAfterPrimeInjectionReport(GridSettings settings) {
        GridSettings newSettings = settings;
        try {
            QueryParams queryParams = QueryParamsBuilder.buildQueryParams(newSettings, getFields(newSettings.getFields()));
            newSettings = DtoLookupHelper.changeLookupForFollowupsAfterPrimeInjectionReport(settings);
            if (newSettings == null) {
                return new Records<Object>(null);
            }
            return lookupService.getEntities(Visit.class, newSettings.getLookup(), newSettings.getFields(), queryParams);
        } catch (IOException | EbodacLookupException e) {
            LOGGER.error(e.getMessage(), e);
            return new Records<Object>(null);
        }
    }

    private Records<?> getScreeningReport(GridSettings settings) {
        GridSettings newSettings = settings;
        try {
            QueryParams queryParams = QueryParamsBuilder.buildQueryParams(newSettings, getFields(newSettings.getFields()));
            newSettings = DtoLookupHelper.changeLookupForScreeningReport(settings);
            if (newSettings == null) {
                return new Records<Object>(null);
            }
            return lookupService.getEntities(Visit.class, newSettings.getLookup(), newSettings.getFields(), queryParams);
        } catch (IOException | EbodacLookupException e) {
            LOGGER.error(e.getMessage(), e);
            return new Records<Object>(null);
        }
    }

    private Records<?> getFollowupsMissedClinicVisitsReport(GridSettings settings) {
        GridSettings newSettings = settings;
        try {
            newSettings = DtoLookupHelper.changeLookupAndOrderForFollowupsMissedClinicVisitsReport(settings);
            if (newSettings == null) {
                return new Records<Object>(null);
            }
            QueryParams queryParams = QueryParamsBuilder.buildQueryParams(newSettings, getFields(newSettings.getFields()));
            return lookupService.getEntities(MissedVisitsReportDto.class, Visit.class, newSettings.getLookup(), newSettings.getFields(), queryParams);
        } catch (IOException | EbodacLookupException e) {
            LOGGER.error(e.getMessage(), e);
            return new Records<Object>(null);
        }
    }

    private Records<?> getMandEMissedClinicVisitsReport(GridSettings settings) {
        GridSettings newSettings = settings;
        try {
            newSettings = DtoLookupHelper.changeLookupAndOrderForMandEMissedClinicVisitsReport(settings);
            if (newSettings == null) {
                return new Records<Object>(null);
            }
            QueryParams queryParams = QueryParamsBuilder.buildQueryParams(newSettings, getFields(newSettings.getFields()));
            return lookupService.getEntities(MissedVisitsReportDto.class, Visit.class, newSettings.getLookup(), newSettings.getFields(), queryParams);
        } catch (IOException | EbodacLookupException e) {
            LOGGER.error(e.getMessage(), e);
            return new Records<Object>(null);
        }
    }

    private Records<?> getOptsOutOfMotechMessagesReport(GridSettings settings) {
        GridSettings newSettings = settings;
        try {
            newSettings = DtoLookupHelper.changeLookupAndOrderForOptsOutOfMotechMessagesReport(settings);
            if (newSettings == null) {
                return new Records<Object>(null);
            }
            QueryParams queryParams = QueryParamsBuilder.buildQueryParams(newSettings, getFields(newSettings.getFields()));
            return lookupService.getEntities(OptsOutOfMotechMessagesReportDto.class, SubjectEnrollments.class,
                    newSettings.getLookup(), newSettings.getFields(), queryParams);
        } catch (IOException | EbodacLookupException e) {
            LOGGER.error(e.getMessage(), e);
            return new Records<Object>(null);
        }
    }

    private Records<?> getIvrAndSmsStatisticReport(GridSettings settings) {
        GridSettings newSettings = settings;
        try {
            newSettings = DtoLookupHelper.changeLookupAndOrderForIvrAndSmsStatisticReport(settings);
            if (newSettings == null) {
                return new Records<Object>(null);
            }
            QueryParams queryParams = QueryParamsBuilder.buildQueryParams(newSettings, getFields(newSettings.getFields()));
            return lookupService.getEntities(IvrAndSmsStatisticReportDto.class, IvrAndSmsStatisticReport.class,
                    newSettings.getLookup(), newSettings.getFields(), queryParams);
        } catch (IOException | EbodacLookupException e) {
            LOGGER.error(e.getMessage(), e);
            return new Records<Object>(null);
        }
    }


    private Records<?> getDay8AndDay57Report(GridSettings settings) {
        GridSettings newSettings = settings;
        try {
            QueryParams queryParams = QueryParamsBuilder.buildQueryParams(newSettings, getFields(newSettings.getFields()));
            newSettings = DtoLookupHelper.changeLookupForDay8AndDay57Report(settings);
            if (newSettings == null) {
                return new Records<Object>(null);
            }
            return lookupService.getEntities(Visit.class, newSettings.getLookup(), newSettings.getFields(), queryParams);
        } catch (IOException | EbodacLookupException e) {
            LOGGER.error(e.getMessage(), e);
            return new Records<Object>(null);
        }
    }

    private Map<String, Object> getFields(String json) throws IOException {
        if (json == null) {
            return null;
        } else {
            return objectMapper.readValue(json, new TypeReference<LinkedHashMap>() {}); //NO CHECKSTYLE WhitespaceAround
        }
    }
}
