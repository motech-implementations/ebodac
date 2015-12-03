package org.motechproject.bookingapp.listener;


import org.motechproject.ebodac.domain.Visit;
import org.motechproject.mds.annotations.InstanceLifecycleListener;
import org.motechproject.mds.domain.InstanceLifecycleListenerType;

public interface BookingAppEventListener {

    @InstanceLifecycleListener(value = InstanceLifecycleListenerType.POST_CREATE)
    void createVisitBookingDetails(Visit visit);
}
