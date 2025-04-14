package mfu.oodp.controller;

import mfu.oodp.model.Account;
import mfu.oodp.model.Agent.Agent;
import mfu.oodp.model.Transaction.Transaction;
import mfu.oodp.model.Transaction.Transaction.TransactionType;
import mfu.oodp.service.AccountService;
import mfu.oodp.service.TransactionService;

import java.util.List;

public class TransactionController {
    private final TransactionService transactionService;
    private final AccountService accountService;

    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    public Account getAccount(String accountId) {
        return accountService.getAccount(accountId);
    }

    public Transaction performTransaction(String id, String from, String to, double amount, TransactionType type,
            Agent agent) {
        return transactionService.createTransaction(id, from, to, amount, type, agent);
    }

    public List<Transaction> getAllTransactions() {
        return transactionService.getTransactionHistory();
    }

}
