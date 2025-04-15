package mfu.oodp.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
    private static final String DB_URL = "jdbc:sqlite:banking.db";

    static {
        initializeDatabase();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

            // 🔸 clients
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS clients (
                    id TEXT PRIMARY KEY,
                    email TEXT NOT NULL,
                    first_name TEXT NOT NULL,
                    last_name TEXT NOT NULL,
                    date_of_birth TIMESTAMP,
                    phone_number TEXT,
                    address TEXT,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

            // 🔸 agents
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS agents (
                    id TEXT PRIMARY KEY,
                    username TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL,
                    email TEXT NOT NULL,
                    first_name TEXT,
                    last_name TEXT,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    last_login TIMESTAMP,
                    is_active BOOLEAN DEFAULT 1
                )
            """);

            // 🔸 agent_action_logs
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS agent_action_logs (
                    id TEXT PRIMARY KEY,
                    agent_id TEXT NOT NULL,
                    action TEXT NOT NULL,
                    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    description TEXT,
                    FOREIGN KEY (agent_id) REFERENCES agents(id)
                )
            """);

            // 🔸 accounts
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS accounts (
                    account_id TEXT PRIMARY KEY,
                    account_name TEXT NOT NULL,
                    account_type TEXT NOT NULL,
                    balance REAL NOT NULL,
                    account_status TEXT NOT NULL
                )
            """);

            // 🔸 transactions
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS transactions (
                    transaction_id TEXT PRIMARY KEY,
                    transaction_time TIMESTAMP NOT NULL,
                    account_id_from TEXT,
                    account_id_to TEXT,
                    amount REAL NOT NULL,
                    transaction_type TEXT NOT NULL,
                    agent_id TEXT,
                    FOREIGN KEY (account_id_from) REFERENCES accounts(account_id),
                    FOREIGN KEY (account_id_to) REFERENCES accounts(account_id),
                    FOREIGN KEY (agent_id) REFERENCES agents(id)
                )
            """);

            System.out.println("✅ SQLite DB Initialized: banking.db");

        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to initialize SQLite database", e);
        }
    }
}
