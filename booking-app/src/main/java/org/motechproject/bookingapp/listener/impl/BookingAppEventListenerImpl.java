package org.motechproject.bookingapp.listener.impl;


import org.motechproject.bookingapp.domain.VisitBookingDetails;
import org.motechproject.bookingapp.listener.BookingAppEventListener;
import org.motechproject.bookingapp.repository.VisitBookingDetailsDataService;
import org.motechproject.ebodac.domain.Visit;
import org.motechproject.ebodac.repository.VisitDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bookingAppEventListener")
public class BookingAppEventListenerImpl implements BookingAppEventListener{

    @Autowired
    private VisitBookingDetailsDataService visitBookingDetailsDataService;

    @Autowired
    private VisitDataService visitDataService;

    @Override
    public void createVisitBookingDetails(Visit visit) {
        VisitBookingDetails visitBookingDetails = new VisitBookingDetails();
        visitBookingDetails.setVisit(visit);
        visitBookingDetailsDataService.create(visitBookingDetails);
    }
}
