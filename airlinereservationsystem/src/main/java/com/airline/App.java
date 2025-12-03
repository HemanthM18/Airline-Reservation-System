package com.airline;

import com.airline.dao.FlightDAO;
import com.airline.dao.PassengerDAO;
import com.airline.models.Flight;
import com.airline.models.Passenger;
import com.airline.service.BookingService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final PassengerDAO passengerDAO = new PassengerDAO();
    private static final FlightDAO flightDAO = new FlightDAO();
    private static final BookingService bookingService = new BookingService();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            printMenu();
            int choice;
            try { choice = Integer.parseInt(sc.nextLine().trim()); } catch (Exception e) { choice = -1; }
            switch (choice) {
                case 1: addPassenger(sc); break;
                case 2: addFlight(sc); break;
                case 3: searchFlights(sc); break;
                case 4: bookFlight(sc); break;
                case 5: viewPassengers(); break;
                case 6: System.out.println("Bye"); sc.close(); return;
                default: System.out.println("Invalid option"); break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Airline Reservation ---");
        System.out.println("1. Add Passenger");
        System.out.println("2. Add Flight");
        System.out.println("3. Search Flights");
        System.out.println("4. Book Flight");
        System.out.println("5. View All Passengers");
        System.out.println("6. Exit");
        System.out.print("Choose: ");
    }

    private static void addPassenger(Scanner sc) {
        System.out.print("First Name: "); String fn = sc.nextLine();
        System.out.print("Last Name: "); String ln = sc.nextLine();
        System.out.print("Email: "); String em = sc.nextLine();
        Passenger p = new Passenger(fn, ln, em);
        passengerDAO.addPassenger(p);
    }

    private static void addFlight(Scanner sc) {
        try {
            System.out.print("Flight No: "); String fno = sc.nextLine();
            System.out.print("Origin: "); String origin = sc.nextLine();
            System.out.print("Destination: "); String dest = sc.nextLine();
            System.out.print("Departure (YYYY-MM-DD HH:MM:SS): "); String dt = sc.nextLine();
            System.out.print("Base Price: "); double price = Double.parseDouble(sc.nextLine());
            System.out.print("Seats Total: "); int seats = Integer.parseInt(sc.nextLine());

            Flight f = new Flight();
            f.setFlightNo(fno);
            f.setOrigin(origin);
            f.setDestination(dest);
            f.setDepartureTime(Timestamp.valueOf(dt));
            f.setBasePrice(price);
            f.setSeatsTotal(seats);
            flightDAO.addFlight(f);
        } catch (Exception e) {
            System.out.println("Invalid input. Use correct formats.");
        }
    }

    private static void searchFlights(Scanner sc) {
        System.out.print("Origin: "); String o = sc.nextLine();
        System.out.print("Destination: "); String d = sc.nextLine();
        List<Flight> list = flightDAO.searchFlights(o, d);
        if (list.isEmpty()) System.out.println("No flights.");
        else {
            for (Flight f : list) {
                System.out.printf("%d | %s | %s -> %s | dep: %s | price: %.2f | seats avail: %d\n",
                        f.getFlightId(), f.getFlightNo(), f.getOrigin(), f.getDestination(),
                        f.getDepartureTime(), f.getBasePrice(), f.getSeatsTotal() - f.getSeatsBooked());
            }
        }
    }

    private static void bookFlight(Scanner sc) {
        try {
            System.out.print("Passenger ID: "); int pid = Integer.parseInt(sc.nextLine());
            System.out.print("Flight ID: "); int fid = Integer.parseInt(sc.nextLine());
            System.out.print("Payment method (CARD/UPI): "); String method = sc.nextLine();
            int bookingId = bookingService.bookSeat(pid, fid, method);
            if (bookingId > 0) System.out.println("Booked. ID: " + bookingId);
            else System.out.println("Booking failed.");
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    private static void viewPassengers() {
        List<Passenger> list = passengerDAO.getAll();
        for (Passenger p : list) {
            System.out.printf("%d | %s %s | %s\n", p.getPassengerId(), p.getFirstName(), p.getLastName(), p.getEmail());
        }
    }
}
