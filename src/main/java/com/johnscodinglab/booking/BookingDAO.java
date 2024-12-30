package com.johnscodinglab.booking;

import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    private static final List<Booking> bookings;

    static {
        bookings = new ArrayList<>();
    }

    public void book(Booking carBooking) {
        bookings.add(carBooking);
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}
