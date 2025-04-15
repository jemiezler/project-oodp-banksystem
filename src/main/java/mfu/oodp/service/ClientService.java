package mfu.oodp.service;

import mfu.oodp.config.DatabaseConfig;
import mfu.oodp.model.Client.Client;

import java.sql.*;
import java.util.*;

public class ClientService {

    public Client registerClient(String email, String firstName, String lastName, Timestamp dob, String phone, String address) {
        String sql = "INSERT INTO clients (id, email, first_name, last_name, date_of_birth, phone_number, address, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        UUID id = UUID.randomUUID();
        Timestamp createdAt = new Timestamp(System.currentTimeMillis());

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id.toString());
            stmt.setString(2, email);
            stmt.setString(3, firstName);
            stmt.setString(4, lastName);
            stmt.setTimestamp(5, dob);
            stmt.setString(6, phone);
            stmt.setString(7, address);
            stmt.setTimestamp(8, createdAt);
            stmt.executeUpdate();

            return new Client(id, email, firstName, lastName, dob, phone, address, createdAt);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Client getClientById(UUID id) {
        String sql = "SELECT * FROM clients WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Client(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("email"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getTimestamp("date_of_birth"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Client> getAllClients() {
        List<Client> list = new ArrayList<>();
        String sql = "SELECT * FROM clients";

        try (Connection conn = DatabaseConfig.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Client(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("email"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getTimestamp("date_of_birth"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteClient(UUID id) {
        String sql = "DELETE FROM clients WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id.toString());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
