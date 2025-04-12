package mfu.oodp.controller;

import mfu.oodp.model.Agent.Agent;
import mfu.oodp.model.Transaction.Transaction;
import mfu.oodp.model.Transaction.Transaction.TransactionType;
import mfu.oodp.service.TransactionService;

import java.util.List;

public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Transaction performTransaction(String id, String from, String to, double amount, TransactionType type, Agent agent) {
        return transactionService.createTransaction(id, from, to, amount, type, agent);
    }

    public List<Transaction> getAllTransactions() {
        return transactionService.getTransactionHistory();
    }
}
