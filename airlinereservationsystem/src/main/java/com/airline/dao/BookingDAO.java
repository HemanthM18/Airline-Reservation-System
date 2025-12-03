package com.airline.dao;

import com.airline.db.DBConnection;
import com.airline.models.Booking;

import java.sql.*;

public class BookingDAO {

    public int createBooking(Connection conn, int passengerId) throws SQLException {
        String sql = "INSERT INTO Booking( passenger_id, status, total_amount ) VALUES (?, 'PENDING', 0)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, passengerId);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    public void updateBookingStatusAndAmount(Connection conn, int bookingId, String status, double amount) throws SQLException {
        String sql = "UPDATE Booking SET status=?, total_amount=? WHERE booking_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setDouble(2, amount);
            ps.setInt(3, bookingId);
            ps.executeUpdate();
        }
    }
}
