// === AgentService.java (migrated to SQLite) ===
package mfu.oodp.service;

import mfu.oodp.config.DatabaseConfig;
import mfu.oodp.model.Agent.Agent;
import mfu.oodp.model.Agent.AgentActionLog;

import java.sql.*;
import java.util.*;

public class AgentService {

    public Agent registerAgent(String username, String password, String email, String firstName, String lastName) {
        UUID id = UUID.randomUUID();
        Timestamp now = new Timestamp(System.currentTimeMillis());

        String sql = "INSERT INTO agents (id, username, password, email, first_name, last_name, created_at, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id.toString());
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setString(4, email);
            stmt.setString(5, firstName);
            stmt.setString(6, lastName);
            stmt.setTimestamp(7, now);
            stmt.setBoolean(8, true);
            stmt.executeUpdate();

            Agent agent = new Agent(id, username, password, email, firstName, lastName, now, null, true);
            logAction(agent, "REGISTER", "Agent registered");
            return agent;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Agent login(String username, String password) {
        String sql = "SELECT * FROM agents WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                Timestamp lastLogin = new Timestamp(System.currentTimeMillis());

                // Update last login
                PreparedStatement update = conn.prepareStatement("UPDATE agents SET last_login = ? WHERE id = ?");
                update.setTimestamp(1, lastLogin);
                update.setString(2, id.toString());
                update.executeUpdate();

                Agent agent = new Agent(
                        id,
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getTimestamp("created_at"),
                        lastLogin,
                        rs.getBoolean("is_active")
                );
                logAction(agent, "LOGIN", "Agent logged in");
                return agent;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void logAction(Agent agent, String action, String description) {
        String sql = "INSERT INTO agent_action_logs (id, agent_id, action, timestamp, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, agent.getId().toString());
            stmt.setString(3, action);
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            stmt.setString(5, description);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<AgentActionLog> getActionLogs() {
        List<AgentActionLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM agent_action_logs";
        try (Connection conn = DatabaseConfig.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                logs.add(new AgentActionLog(
                        UUID.fromString(rs.getString("id")),
                        getAgentById(UUID.fromString(rs.getString("agent_id"))),
                        rs.getString("action"),
                        rs.getTimestamp("timestamp"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    public Agent getAgentById(UUID id) {
        String sql = "SELECT * FROM agents WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Agent(
                        id,
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("last_login"),
                        rs.getBoolean("is_active")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}