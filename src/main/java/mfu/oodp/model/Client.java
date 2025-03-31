package mfu.oodp.model;

import java.io.Serializable;

public class Client implements BankOperations, Serializable {
    private String name;
    private double balance;

    public Client(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (balance >= amount) {
            balance -= amount;
        } else {
            throw new InsufficientFundsException("Insufficient funds for withdrawal.");
        }
    }

    @Override
    public void displayBalance() {
        System.out.println("Client: " + name + ", Balance: " + balance);
    }
}