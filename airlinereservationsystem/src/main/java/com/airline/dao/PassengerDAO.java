package com.airline.dao;

import com.airline.db.DBConnection;
import com.airline.models.Passenger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassengerDAO {

    public void addPassenger(Passenger p) {
        String sql = "INSERT INTO Passenger(first_name, last_name, email) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getFirstName());
            ps.setString(2, p.getLastName());
            ps.setString(3, p.getEmail());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) p.setPassengerId(rs.getInt(1));
            System.out.println("Passenger added successfully! ID: " + p.getPassengerId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Passenger getById(int id) {
        String sql = "SELECT * FROM Passenger WHERE passenger_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Passenger p = new Passenger();
                p.setPassengerId(rs.getInt("passenger_id"));
                p.setFirstName(rs.getString("first_name"));
                p.setLastName(rs.getString("last_name"));
                p.setEmail(rs.getString("email"));
                return p;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public List<Passenger> getAll() {
        List<Passenger> list = new ArrayList<>();
        String sql = "SELECT * FROM Passenger";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Passenger p = new Passenger();
                p.setPassengerId(rs.getInt("passenger_id"));
                p.setFirstName(rs.getString("first_name"));
                p.setLastName(rs.getString("last_name"));
                p.setEmail(rs.getString("email"));
                list.add(p);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
