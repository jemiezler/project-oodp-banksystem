package mfu.oodp.controller;

import mfu.oodp.model.Account;
import mfu.oodp.model.Agent.Agent;
import mfu.oodp.model.Transaction.Transaction;
import mfu.oodp.model.Transaction.Transaction.TransactionType;
import mfu.oodp.service.AccountService;
import mfu.oodp.service.TransactionService;
import mfu.oodp.operation.TransactionOperation;

import java.util.List;

public class TransactionController extends BaseController implements TransactionOperation {
    private final TransactionService transactionService;
    private final AccountService accountService;

    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @Override
    public Account getAccount(String accountId) {
        logAction("📂 Fetching account " + accountId);
        return accountService.getAccount(accountId);
    }

    @Override
    public Transaction performTransaction(String id, String from, String to, double amount, TransactionType type, Agent agent) {
        logAction("💸 Performing transaction " + id);
        return transactionService.createTransaction(id, from, to, amount, type, agent);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        logAction("📜 Fetching transaction history");
        return transactionService.getTransactionHistory();
    }
}
