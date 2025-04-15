package mfu.oodp.service;

import mfu.oodp.config.DatabaseConfig;
import mfu.oodp.model.Account;

import java.sql.*;
import java.util.*;

public class AccountService {

    public Account createAccount(String accountId, String accountName, Account.AccountType type, double initialBalance) {
        String sql = "INSERT INTO accounts (account_id, account_name, account_type, balance, account_status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            stmt.setString(2, accountName);
            stmt.setString(3, type.name());
            stmt.setDouble(4, initialBalance);
            stmt.setString(5, Account.AccountStatus.ACTIVE.name());
            stmt.executeUpdate();
            return new Account(accountId, accountName, type, initialBalance, Account.AccountStatus.ACTIVE);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Account getAccount(String accountId) {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Account(
                    rs.getString("account_id"),
                    rs.getString("account_name"),
                    Account.AccountType.valueOf(rs.getString("account_type")),
                    rs.getDouble("balance"),
                    Account.AccountStatus.valueOf(rs.getString("account_status"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateBalance(String accountId, double newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newBalance);
            stmt.setString(2, accountId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Account> getAllAccounts() {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        try (Connection conn = DatabaseConfig.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Account(
                    rs.getString("account_id"),
                    rs.getString("account_name"),
                    Account.AccountType.valueOf(rs.getString("account_type")),
                    rs.getDouble("balance"),
                    Account.AccountStatus.valueOf(rs.getString("account_status"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteAccount(String accountId) {
        String sql = "DELETE FROM accounts WHERE account_id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}