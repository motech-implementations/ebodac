package org.motechproject.ebodac.uitest.page.bookingapp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ScreeningCardPage extends BookingAppPage {

    public static final String URL_PATH = "/booking-app/resources/partials/card/screeningCard.html";
    private static final By BOOKING_ID = By.id("bookingId");

    public ScreeningCardPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String expectedUrlPath() {
        return URL_PATH;
    }

    @Override
    public void goToPage() {
    }

    public String getBookingId() {
        return findElement(BOOKING_ID).getText();
    }
}
