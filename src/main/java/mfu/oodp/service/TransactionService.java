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
        Account from = accountService.getAccount(fromId);
        Account to = accountService.getAccount(toId);

        // Validate balance
        if (type != TransactionType.DEPOSIT && (from == null || from.getBalance() < amount)) {
            throw new IllegalArgumentException("Insufficient balance or invalid source account.");
        }

        // Process transaction
        if (type == TransactionType.TRANSFER || type == TransactionType.WITHDRAWAL) {
            from.setBalance(from.getBalance() - amount);
        }

        if (type == TransactionType.TRANSFER || type == TransactionType.DEPOSIT) {
            if (to == null) throw new IllegalArgumentException("Target account not found.");
            to.setBalance(to.getBalance() + amount);
        }

        Transaction transaction = new Transaction(
                transactionId,
                new Timestamp(System.currentTimeMillis()),
                from,
                to,
                amount,
                type,
                agent
        );

        transactionHistory.add(transaction);
        return transaction;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}
