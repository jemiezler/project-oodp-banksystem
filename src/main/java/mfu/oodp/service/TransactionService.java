package mfu.oodp.service;

import mfu.oodp.model.Account;
import mfu.oodp.model.Agent.Agent;
import mfu.oodp.model.Transaction.Transaction;
import mfu.oodp.model.Transaction.Transaction.TransactionType;

import java.sql.Timestamp;
import java.util.*;

public class TransactionService {

    private final List<Transaction> transactionHistory = new ArrayList<>();
    private final AccountService accountService;

    public TransactionService(AccountService accountService) {
        this.accountService = accountService;
    }

    public Transaction createTransaction(String transactionId, String fromId, String toId, double amount, TransactionType type, Agent agent) {
        Account from = fromId != null && !fromId.isEmpty() ? accountService.getAccount(fromId) : null;
        Account to = toId != null && !toId.isEmpty() ? accountService.getAccount(toId) : null;
    
        // Debugging
        System.out.println("Debug: fromId = " + fromId + ", from = " + from);
        System.out.println("Debug: toId = " + toId + ", to = " + to);
    
        // Validate balance
        if (type != TransactionType.DEPOSIT && (from == null || from.getBalance() < amount)) {
            throw new IllegalArgumentException("Insufficient balance or invalid source account.");
        }
    
        // Validate accounts
        if ((type == TransactionType.TRANSFER || type == TransactionType.WITHDRAWAL) && from == null) {
            throw new IllegalArgumentException("Source account not found.");
        }
    
        if ((type == TransactionType.TRANSFER || type == TransactionType.DEPOSIT) && to == null) {
            throw new IllegalArgumentException("Target account not found.");
        }
    
        // Process transaction
        if (type == TransactionType.TRANSFER || type == TransactionType.WITHDRAWAL) {
            from.setBalance(from.getBalance() - amount);
        }
    
        if (type == TransactionType.TRANSFER || type == TransactionType.DEPOSIT) {
            to.setBalance(to.getBalance() + amount);
        }
    
        // Create the Transaction object
        Transaction transaction = new Transaction(
                transactionId,
                new Timestamp(System.currentTimeMillis()),
                from,
                to,
                amount,
                type,
                agent
        );
    
        // Add the transaction to the history
        transactionHistory.add(transaction);
        return transaction;
    }
    public List<Transaction> getTransactionHistory() {
        // Placeholder implementation, replace with actual logic
        return new ArrayList<>();
    }
}
