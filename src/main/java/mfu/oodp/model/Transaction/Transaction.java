package mfu.oodp.model.Transaction;

import java.sql.Timestamp;

import mfu.oodp.model.Account;
import mfu.oodp.model.Agent.Agent;

public class Transaction {
    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER
    }

    private String transactionId;
    private Timestamp transactionTime;
    private Account accountIdFrom;
    private Account accountIdTo;
    private double amount;
    private TransactionType transactionType;
    private Agent agentId;

    public Transaction(String transactionId, Timestamp transactionTime, Account accountIdFrom, Account accountIdTo, double amount, TransactionType transactionType, Agent agentId) {
        this.transactionId = transactionId;
        this.transactionTime = transactionTime;
        this.accountIdFrom = accountIdFrom;
        this.accountIdTo = accountIdTo;
        this.amount = amount;
        this.transactionType = transactionType;
        this.agentId = agentId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Timestamp getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Timestamp transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Account getAccountIdFrom() {
        return accountIdFrom;
    }

    public void setAccountIdFrom(Account accountIdFrom) {
        this.accountIdFrom = accountIdFrom;
    }

    public Account getAccountIdTo() {
        return accountIdTo;
    }

    public void setAccountIdTo(Account accountIdTo) {
        this.accountIdTo = accountIdTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Agent getAgentId() {
        return agentId;
    }

    public void setAgentId(Agent agentId) {
        this.agentId = agentId;
    }
    
}
