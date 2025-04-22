package mfu.oodp.operation;

import mfu.oodp.model.Account;
import mfu.oodp.model.Agent.Agent;
import mfu.oodp.model.Transaction.Transaction;
import mfu.oodp.model.Transaction.Transaction.TransactionType;

import java.util.List;

public interface TransactionOperation {
    Account getAccount(String accountId);
    Transaction performTransaction(String id, String from, String to, double amount, TransactionType type, Agent agent);
    List<Transaction> getAllTransactions();
}
