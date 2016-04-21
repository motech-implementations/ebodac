package org.motechproject.ebodac.uitest.helper;

/**
 * Created by tomasz on 30.09.15.
 */
public class TestParticipant {
    private String id;
    private String name;
    private String language;
    private String phoneNumber;
    private String siteId;
    private String householdName;
    private String headOfHousehold;
    private String community;
    private String address;
    private TestParticipant() {
        id = "1110079999";
        name = "TestTest";
        language = "English";
        phoneNumber = "999888777";
        siteId = "OTHER";
        householdName = "Kingdom";
        headOfHousehold = "King";
        community = "Klingon";
        address = "Nowa str.";
    }
}
