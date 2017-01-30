package org.motechproject.ebodac.uitest.helper;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.motechproject.ebodac.client.EbodacHttpClient;
import org.motechproject.ebodac.web.domain.SubmitSubjectRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UITestHttpClientHelper extends EbodacHttpClient {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String URL_IMPORT_CSV = "/module/ebodac/web-api/import-csv";
    private static final String URL_PARTICIPANT_REGISTRATION = "/module/ebodac/registration/submit";

    private static final String PARTICIPANT_ID_PREFIX = "998";
    private static final int PARTICIPANT_ID_RAND_MIN = 1000000;
    private static final int PARTICIPANT_ID_RAND_MAX = 9999999;

    private static final String PHONE_NUM_PREFIX = "232000000000";
    private static final int PHONE_NUM_RAND_MIN = 100;
    private static final int PHONE_NUM_RAND_MAX = 999;

    private String zetesRegistrationURL;
    private String importCsvUrl;

    public UITestHttpClientHelper(String serverUrl) {
        if (StringUtils.isNotBlank(serverUrl)) {
            String serverURL = serverUrl;
            if (serverURL.endsWith("/")) {
                serverURL = serverURL.substring(0, serverURL.lastIndexOf("/"));
            }
            zetesRegistrationURL = serverURL + URL_PARTICIPANT_REGISTRATION;
            importCsvUrl = serverURL + URL_IMPORT_CSV;
        } else {
            throw new IllegalArgumentException("Server url cannot be empty");
        }
    }

    public String generateNewParticipantId() {
        return PARTICIPANT_ID_PREFIX + (Math.round(Math.random() * (PARTICIPANT_ID_RAND_MAX - PARTICIPANT_ID_RAND_MIN + 1)) + PARTICIPANT_ID_RAND_MIN);
    }

    public boolean addParticipantFromZetes(String user, String password, String participantId) throws IOException {
        SubmitSubjectRequest request = new SubmitSubjectRequest(generatePhoneNumber(), "test participant", "householdName", participantId,
                "address", "eng", "community", "B05-SL10001", "Test Clinic 1", "headOfHousehold", "chiefdom", "section", "district");
        String json = OBJECT_MAPPER.writeValueAsString(request);
        return null != sendJson(zetesRegistrationURL, json, user, password);
    }

    public boolean addVisitsFromRAVE(String userName, String password, String participantId) throws IOException {
        if (StringUtils.isBlank(participantId)) {
            return false;
        }

        String csv = generateCSVFileFromString(participantId);
        return null != sendCsvFile(importCsvUrl, userName, password, new ByteArrayInputStream(csv.getBytes("UTF-8")));
    }

    private String generateCSVFileFromString(String participantId) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/uiTestParticipant.csv");
        return String.format(IOUtils.toString(inputStream, "UTF-8"), participantId);
    }

    private String generatePhoneNumber() {
        return PHONE_NUM_PREFIX + (Math.round(Math.random() * (PHONE_NUM_RAND_MAX - PHONE_NUM_RAND_MIN + 1)) + PHONE_NUM_RAND_MIN);
    }
}
