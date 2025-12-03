package com.airline.dao;

import com.airline.models.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class TicketDAO {

    public void createTicket(Connection conn, Ticket t) throws Exception {
        String sql = "INSERT INTO Ticket(ticket_no, booking_id, flight_id, passenger_id, seat_no, fare, status) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getTicketNo());
            ps.setInt(2, t.getBookingId());
            ps.setInt(3, t.getFlightId());
            ps.setInt(4, t.getPassengerId());
            ps.setString(5, t.getSeatNo());
            ps.setDouble(6, t.getFare());
            ps.setString(7, t.getStatus());
            ps.executeUpdate();
        }
    }
}
