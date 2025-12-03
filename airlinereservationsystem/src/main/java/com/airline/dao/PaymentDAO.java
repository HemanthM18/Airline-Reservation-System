package com.airline.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PaymentDAO {
    public void createPayment(Connection conn, int bookingId, double amount, String method, String transactionId) throws Exception {
        String sql = "INSERT INTO Payment(booking_id, amount, method, transaction_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ps.setDouble(2, amount);
            ps.setString(3, method);
            ps.setString(4, transactionId);
            ps.executeUpdate();
        }
    }
}
