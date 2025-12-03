package com.airline.service;

import com.airline.db.DBConnection;
import com.airline.dao.*;
import com.airline.models.*;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class BookingService {

    private BookingDAO bookingDAO = new BookingDAO();
    private FlightDAO flightDAO = new FlightDAO();
    private TicketDAO ticketDAO = new TicketDAO();
    private PaymentDAO paymentDAO = new PaymentDAO();

    /**
     * Book a seat on flightId for passengerId.
     * Returns booking id or -1 on failure.
     */
    public int bookSeat(int passengerId, int flightId, String payMethod) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // Lock flight row
            Flight flight = flightDAO.getByIdForUpdate(conn, flightId);
            if (flight == null) {
                System.out.println("Flight not found.");
                conn.rollback();
                return -1;
            }

            if (flight.getSeatsBooked() >= flight.getSeatsTotal()) {
                System.out.println("No seats available.");
                conn.rollback();
                return -1;
            }

            // 1) Create booking
            int bookingId = bookingDAO.createBooking(conn, passengerId);

            // 2) increment seats_booked
            flightDAO.incrementSeatsBooked(conn, flightId);

            // 3) compute fare and create ticket
            double fare = flight.getBasePrice(); // simple fare
            Ticket ticket = new Ticket();
            ticket.setTicketNo("TKT" + UUID.randomUUID().toString().substring(0,6).toUpperCase());
            ticket.setBookingId(bookingId);
            ticket.setFlightId(flightId);
            ticket.setPassengerId(passengerId);
            ticket.setSeatNo(null);
            ticket.setFare(fare);
            ticket.setStatus("ISSUED");
            ticketDAO.createTicket(conn, ticket);

            // 4) create payment
            String txn = "TXN" + UUID.randomUUID().toString().substring(0,8);
            paymentDAO.createPayment(conn, bookingId, fare, payMethod, txn);

            // 5) update booking
            bookingDAO.updateBookingStatusAndAmount(conn, bookingId, "CONFIRMED", fare);

            conn.commit();
            System.out.println("Booking successful. Booking ID: " + bookingId + ", Ticket: " + ticket.getTicketNo());
            return bookingId;

        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception ignored) {}
            return -1;
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (Exception ignored) {}
        }
    }
}
