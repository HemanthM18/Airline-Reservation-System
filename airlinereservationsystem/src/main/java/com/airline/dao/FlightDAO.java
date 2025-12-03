package com.airline.dao;

import com.airline.db.DBConnection;
import com.airline.models.Flight;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO {

    public void addFlight(Flight f) {
        String sql = "INSERT INTO Flight(flight_no, origin, destination, departure_time, base_price, seats_total) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getFlightNo());
            ps.setString(2, f.getOrigin());
            ps.setString(3, f.getDestination());
            ps.setTimestamp(4, f.getDepartureTime());
            ps.setDouble(5, f.getBasePrice());
            ps.setInt(6, f.getSeatsTotal());
            ps.executeUpdate();
            System.out.println("Flight added.");
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Flight> searchFlights(String origin, String destination) {
        List<Flight> list = new ArrayList<>();
        String sql = "SELECT * FROM Flight WHERE origin=? AND destination=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, origin);
            ps.setString(2, destination);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Flight f = new Flight();
                f.setFlightId(rs.getInt("flight_id"));
                f.setFlightNo(rs.getString("flight_no"));
                f.setOrigin(rs.getString("origin"));
                f.setDestination(rs.getString("destination"));
                f.setDepartureTime(rs.getTimestamp("departure_time"));
                f.setBasePrice(rs.getDouble("base_price"));
                f.setSeatsTotal(rs.getInt("seats_total"));
                f.setSeatsBooked(rs.getInt("seats_booked"));
                list.add(f);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public Flight getByIdForUpdate(Connection conn, int flightId) throws SQLException {
        String sql = "SELECT * FROM Flight WHERE flight_id=? FOR UPDATE";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, flightId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Flight f = new Flight();
                f.setFlightId(rs.getInt("flight_id"));
                f.setFlightNo(rs.getString("flight_no"));
                f.setOrigin(rs.getString("origin"));
                f.setDestination(rs.getString("destination"));
                f.setDepartureTime(rs.getTimestamp("departure_time"));
                f.setBasePrice(rs.getDouble("base_price"));
                f.setSeatsTotal(rs.getInt("seats_total"));
                f.setSeatsBooked(rs.getInt("seats_booked"));
                return f;
            }
        }
        return null;
    }

    public void incrementSeatsBooked(Connection conn, int flightId) throws SQLException {
        String sql = "UPDATE Flight SET seats_booked = seats_booked + 1 WHERE flight_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, flightId);
            ps.executeUpdate();
        }
    }
}
