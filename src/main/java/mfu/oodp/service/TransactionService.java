package mfu.oodp.service;

import mfu.oodp.config.DatabaseConfig;
import mfu.oodp.model.Account;
import mfu.oodp.model.Agent.Agent;
import mfu.oodp.model.Transaction.Transaction;
import mfu.oodp.model.Transaction.Transaction.TransactionType;

import java.sql.*;
import java.util.*;

public class TransactionService {

    private final AccountService accountService;

    public TransactionService(AccountService accountService) {
        this.accountService = accountService;
    }

    public Transaction createTransaction(String transactionId, String fromId, String toId, double amount, TransactionType type, Agent agent) {
        Account from = fromId != null && !fromId.isEmpty() ? accountService.getAccount(fromId) : null;
        Account to = toId != null && !toId.isEmpty() ? accountService.getAccount(toId) : null;

        if (type != TransactionType.DEPOSIT && (from == null || from.getBalance() < amount)) {
            throw new IllegalArgumentException("Insufficient balance or invalid source account.");
        }
        if ((type == TransactionType.TRANSFER || type == TransactionType.WITHDRAWAL) && from == null) {
            throw new IllegalArgumentException("Source account not found.");
        }
        if ((type == TransactionType.TRANSFER || type == TransactionType.DEPOSIT) && to == null) {
            throw new IllegalArgumentException("Target account not found.");
        }

        if (type == TransactionType.TRANSFER || type == TransactionType.WITHDRAWAL) {
            from.setBalance(from.getBalance() - amount);
            accountService.updateBalance(from.getAccountId(), from.getBalance());
        }
        if (type == TransactionType.TRANSFER || type == TransactionType.DEPOSIT) {
            to.setBalance(to.getBalance() + amount);
            accountService.updateBalance(to.getAccountId(), to.getBalance());
        }

        Timestamp now = new Timestamp(System.currentTimeMillis());

        String sql = "INSERT INTO transactions (transaction_id, transaction_time, account_id_from, account_id_to, amount, transaction_type, agent_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transactionId);
            stmt.setTimestamp(2, now);
            stmt.setString(3, fromId);
            stmt.setString(4, toId);
            stmt.setDouble(5, amount);
            stmt.setString(6, type.name());
            stmt.setString(7, agent != null ? agent.getId().toString() : null);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Transaction(transactionId, now, from, to, amount, type, agent);
    }

    public List<Transaction> getTransactionHistory() {
        List<Transaction> result = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        try (Connection conn = DatabaseConfig.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Account from = accountService.getAccount(rs.getString("account_id_from"));
                Account to = accountService.getAccount(rs.getString("account_id_to"));
                Agent agent = new AgentService().getAgentById(UUID.fromString(rs.getString("agent_id")));
    
                result.add(new Transaction(
                        rs.getString("transaction_id"),
                        rs.getTimestamp("transaction_time"),
                        from,
                        to,
                        rs.getDouble("amount"),
                        TransactionType.valueOf(rs.getString("transaction_type")),
                        agent
                ));
                System.out.println(result);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    

    public List<Transaction> getTransactionsByAccount(String accountId) {
        List<Transaction> result = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_id_from = ? OR account_id_to = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            stmt.setString(2, accountId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new Transaction(
                        rs.getString("transaction_id"),
                        rs.getTimestamp("transaction_time"),
                        accountService.getAccount(rs.getString("account_id_from")),
                        accountService.getAccount(rs.getString("account_id_to")),
                        rs.getDouble("amount"),
                        TransactionType.valueOf(rs.getString("transaction_type")),
                        null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void updateTransaction(String transactionId, double newAmount, TransactionType newType) {
        String sql = "UPDATE transactions SET amount = ?, transaction_type = ? WHERE transaction_id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newAmount);
            stmt.setString(2, newType.name());
            stmt.setString(3, transactionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTransaction(String transactionId) {
        String sql = "DELETE FROM transactions WHERE transaction_id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transactionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}