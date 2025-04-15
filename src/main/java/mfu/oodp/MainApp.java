package mfu.oodp;

import mfu.oodp.config.DatabaseConfig;
import mfu.oodp.view.agent.AgentLoginFrame;
import java.sql.SQLException;

public class MainApp {
    public static void main(String[] args) {
        try {
            DatabaseConfig.getConnection(); // จะ initialize db อัตโนมัติ
            System.out.println("✅ Connected to SQLite!");
            new AgentLoginFrame().setVisible(true);
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to SQLite: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
