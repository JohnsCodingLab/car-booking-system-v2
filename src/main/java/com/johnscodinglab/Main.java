package com.johnscodinglab;

import com.johnscodinglab.booking.Booking;
import com.johnscodinglab.booking.BookingDAO;
import com.johnscodinglab.booking.BookingService;
import com.johnscodinglab.car.Car;
import com.johnscodinglab.car.CarDAO;
import com.johnscodinglab.car.CarService;
import com.johnscodinglab.user.User;
import com.johnscodinglab.user.UserDAO;
import com.johnscodinglab.user.UserFileDataAccessService;
import com.johnscodinglab.user.UserService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CarDAO carDAO = new CarDAO();
        BookingDAO bookingDAO = new BookingDAO();
        UserDAO userDAO = new UserFileDataAccessService();

        UserService userService = new UserService(userDAO);
        CarService carService = new CarService(carDAO);
        BookingService bookingService = new BookingService(bookingDAO, carService);

        boolean keepGoing = true;

        while (keepGoing) {
            displayMenu();
            String choice = scanner.nextLine();
            switch (parseInt(choice)) {
                case 1 -> bookCar(userService, bookingService, carService, scanner);
                case 2 -> displayAllUserBookedCars(userService, bookingService, scanner);
                case 3 -> displayAllBooking(bookingService);
                case 4 -> displayAvailableCars(bookingService, false);
                case 5 -> displayAvailableCars(bookingService, true);
                case 6 -> displayAllUsers(userService);
                case 7 -> keepGoing = false;
            }

        }
    }

    private static void displayAllUsers(UserService userService) {
        List<User> users = userService.getUsers();

        if (users.isEmpty()) {
            System.out.println("No users found");
        }
        for (User user : users) {
            System.out.println(user);
        }
    }

    private static void displayAvailableCars(BookingService bookingService, boolean isElectric) {
        List<Car> availableCars = isElectric ? bookingService.getAvailableElectricCars() : bookingService.getAvailableCars();
        if (availableCars.isEmpty()) {
            System.out.println("No cars found");
            return;
        }
        for (Car car : availableCars) {
            System.out.println(car);
        }
    }

    private static void displayAllBooking(BookingService bookingService) {
        List<Booking> bookings = bookingService.getBookings();
        if (bookings.isEmpty()) {
            System.out.println("No bookings found");
        }
        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }

    private static void displayAllUserBookedCars(UserService userService, BookingService bookingService, Scanner scanner) {
        displayAllUsers(userService);
        System.out.println("Enter user ID");
        String userId = scanner.nextLine();
        User user = userService.getUser(UUID.fromString(userId));

        if (user == null) {
            System.out.println("User with ID " + userId + " not found");
            return;
        }

        List<Car> userBookedCars = bookingService.getUserBookedCars(user.getID());
        if (userBookedCars.isEmpty()) {
            System.out.println("User with ID " + userId + " has no booked cars");
        }
        for (Car car : userBookedCars) {
            System.out.println(car);
        }
    }

    private static void bookCar(UserService userService, BookingService bookingService, CarService carService, Scanner scanner) {
        displayAvailableCars(bookingService, false);
        System.out.println("Enter car plate number");
        String carPlateNumber = scanner.nextLine();

        System.out.println();

        displayAllUsers(userService);
        System.out.println("Enter user Id");
        String userId = scanner.nextLine();

        try {
            Car car = carService.getCar(carPlateNumber);
            User user = userService.getUser(UUID.fromString(userId));
            if (car == null) {
                System.out.println("Car with ID " + carPlateNumber + " not found");
            } if (user == null) {
                System.out.println("User with ID " + userId + " not found");
            } else {
                UUID bookingId = bookingService.bookCar(user, carPlateNumber);
                String confirmationMsg = """
                        Successfully booked car with plate number %s for user: %s with booking ref: %s
                        """.formatted(carPlateNumber, userId, bookingId);
                System.out.println(confirmationMsg);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void displayMenu() {
        System.out.println("""
                \n
                1️⃣ - Book Car
                2️⃣ - View All User Booked Cars
                3️⃣ - View All Bookings
                4️⃣ - View Available Cars
                5️⃣ - View Available Electric Cars
                6️⃣ - View all users
                7️⃣ - Exit
                """);
    }
}