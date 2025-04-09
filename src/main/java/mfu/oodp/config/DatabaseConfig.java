package mfu.oodp.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
    private static final String DB_URL = "jdbc:h2:./bankdb;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASS = "";

    static {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // 🔸 Table: clients
            stmt.execute("CREATE TABLE IF NOT EXISTS clients (" +
                    "id UUID PRIMARY KEY," +
                    "email VARCHAR(255) NOT NULL," +
                    "first_name VARCHAR(100) NOT NULL," +
                    "last_name VARCHAR(100) NOT NULL," +
                    "date_of_birth TIMESTAMP," +
                    "phone_number VARCHAR(50)," +
                    "address VARCHAR(255)," +
                    "created_at TIMESTAMP)");

            // 🔸 Table: agents
            stmt.execute("CREATE TABLE IF NOT EXISTS agents (" +
                    "id UUID PRIMARY KEY," +
                    "username VARCHAR(100) UNIQUE NOT NULL," +
                    "password VARCHAR(255) NOT NULL," +
                    "email VARCHAR(255) NOT NULL," +
                    "first_name VARCHAR(100)," +
                    "last_name VARCHAR(100)," +
                    "created_at TIMESTAMP," +
                    "last_login TIMESTAMP," +
                    "is_active BOOLEAN)");

            // 🔸 Table: accounts
            stmt.execute("CREATE TABLE IF NOT EXISTS accounts (" +
                    "account_id VARCHAR(50) PRIMARY KEY," +
                    "account_name VARCHAR(100) NOT NULL," +
                    "account_type VARCHAR(20) NOT NULL," +
                    "balance DOUBLE NOT NULL," +
                    "account_status VARCHAR(20) NOT NULL)");

            // 🔸 Table: transactions
            stmt.execute("CREATE TABLE IF NOT EXISTS transactions (" +
                    "transaction_id VARCHAR(50) PRIMARY KEY," +
                    "transaction_time TIMESTAMP NOT NULL," +
                    "account_id_from VARCHAR(50)," +
                    "account_id_to VARCHAR(50)," +
                    "amount DOUBLE NOT NULL," +
                    "transaction_type VARCHAR(20) NOT NULL," +
                    "agent_id UUID," +
                    "FOREIGN KEY (account_id_from) REFERENCES accounts(account_id)," +
                    "FOREIGN KEY (account_id_to) REFERENCES accounts(account_id)," +
                    "FOREIGN KEY (agent_id) REFERENCES agents(id))");

        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to initialize database", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
