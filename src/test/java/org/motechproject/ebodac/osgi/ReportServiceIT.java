package org.motechproject.ebodac.osgi;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.ebodac.constants.EbodacConstants;
import org.motechproject.ebodac.domain.*;
import org.motechproject.ebodac.repository.ReportBoosterVaccinationDataService;
import org.motechproject.ebodac.repository.ReportPrimerVaccinationDataService;
import org.motechproject.ebodac.repository.SubjectDataService;
import org.motechproject.ebodac.service.*;
import org.motechproject.testing.osgi.BasePaxIT;
import org.motechproject.testing.osgi.container.MotechNativeTestContainerFactory;
import org.ops4j.pax.exam.ExamFactory;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerSuite;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerSuite.class)
@ExamFactory(MotechNativeTestContainerFactory.class)
public class ReportServiceIT extends BasePaxIT{

    @Inject
    private SubjectDataService subjectDataService;

    @Inject
    private ConfigService configService;

    @Inject
    private ReportPrimerVaccinationDataService primerVaccinationDataService;

    @Inject
    private ReportBoosterVaccinationDataService boosterVaccinationDataService;

    @Inject
    private RaveImportService raveImportService;

    @Inject
    private ReportUpdateService reportUpdateService;

    @Inject
    private ReportService reportService;

    private Config savedConfig;

    private Config config;

    @Before
    public void setUp() throws Exception {
        savedConfig = configService.getConfig();
        subjectDataService.deleteAll();
        boosterVaccinationDataService.deleteAll();
        primerVaccinationDataService.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
        configService.updateConfig(savedConfig);
        subjectDataService.deleteAll();
        boosterVaccinationDataService.deleteAll();
        primerVaccinationDataService.deleteAll();
    }

    @Test
    public void shouldGenerateNewReports() throws IOException{
        DateTimeFormatter formatter = DateTimeFormat.forPattern(EbodacConstants.REPORT_DATE_FORMAT);
        InputStream in = getClass().getResourceAsStream("/sample.csv");
        raveImportService.importCsv(new InputStreamReader(in));
        in.close();

        assertEquals(0, primerVaccinationDataService.retrieveAll().size());
        assertEquals(0, primerVaccinationDataService.retrieveAll().size());

        reportService.generateDailyReports();

        assertEquals(9, primerVaccinationDataService.retrieveAll().size());
        assertEquals(9, boosterVaccinationDataService.retrieveAll().size());

        boosterVaccinationDataService.deleteAll();
        primerVaccinationDataService.deleteAll();

        config = configService.getConfig();
        config.setLastReportDate(DateTime.now().minusDays(31).toString(formatter));
        config.setGenerateReports(true);
        configService.updateConfig(config);

        reportService.generateDailyReports();
        assertEquals(30, primerVaccinationDataService.retrieveAll().size());
        assertEquals(30, boosterVaccinationDataService.retrieveAll().size());
    }

    @Test
    public void shouldUpdateDailyReports() throws IOException{
        InputStream in = getClass().getResourceAsStream("/sample.csv");
        raveImportService.importCsv(new InputStreamReader(in));
        in.close();

        reportService.generateDailyReports();
        assertEquals(0, reportUpdateService.getPrimerVaccinationReportsToUpdate().size());
        assertEquals(0, reportUpdateService.getBoosterVaccinationReportsToUpdate().size());

        in = getClass().getResourceAsStream("/sample2.csv");
        raveImportService.importCsv(new InputStreamReader(in));
        in.close();

        assertEquals(8, reportUpdateService.getPrimerVaccinationReportsToUpdate().size());
        assertEquals(0, reportUpdateService.getBoosterVaccinationReportsToUpdate().size());

        reportService.generateDailyReports();
        assertEquals(0, reportUpdateService.getPrimerVaccinationReportsToUpdate().size());
        assertEquals(0, reportUpdateService.getBoosterVaccinationReportsToUpdate().size());

        }
    }

