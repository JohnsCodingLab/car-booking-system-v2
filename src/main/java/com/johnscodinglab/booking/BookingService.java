package com.johnscodinglab.booking;

import com.johnscodinglab.car.Car;
import com.johnscodinglab.car.CarService;
import com.johnscodinglab.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookingService {
    private final CarService carService;
    private final BookingDAO bookingDAO;

    public BookingService(BookingDAO bookingDAO, CarService carService) {
        this.bookingDAO = bookingDAO;
        this.carService = carService;
    }

    public UUID bookCar(User user, String plateNumber) {
        List<Car> availableCars = getAvailableCars();

        return availableCars.stream()
                .filter(car -> car.getPlateNumber().equals(plateNumber))
                .findFirst()
                .map(car -> {
                    UUID bookingId = UUID.randomUUID();
                    bookingDAO.book(new Booking(bookingId, user, car,
                            LocalDateTime.now(), false));
                    return bookingId;
                }).orElseThrow(() -> new IllegalStateException("Already booked car with plate number " + plateNumber));
    }

    public List<Car> getUserBookedCars(UUID userID) {
        List<Booking> carBooking = bookingDAO.getBookings();

        return carBooking.stream()
                .filter(booking -> booking != null && booking.getUser().getID().equals(userID))
                .map(Booking::getCar)
                .collect(Collectors.toList());
    }

    public List<Booking> getBookings() {
        return bookingDAO.getBookings();
    }

    public List<Car> getAvailableCars() {
        return getCars(carService.getAllCars());
    }

    public List<Car> getAvailableElectricCars() {
        return getCars(carService.getAllElectricCars());
    }

    private List<Car> getCars(List<Car> cars) {
//        Check if there is a car in the system
        if (cars.isEmpty()) {
            return Collections.emptyList();
        }

//        Check if there is a car booking if not all cars are available
        List<Booking> bookings = bookingDAO.getBookings();
        if (bookings.isEmpty()) {
            return cars;
        }

        List<Car> availableCars = new ArrayList<>();

//        check if the car is booked or not (if it's not booked then its available)
        for (Car car : cars) {
            boolean booked = false;
            for (Booking booking : bookings) {
                if (booking == null || !booking.getCar().equals(car)) {
                    continue;
                }
                booked = true;
            }

            if (!booked) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }
}