package mfu.oodp.service;

import mfu.oodp.model.Account;

import java.util.*;

public class AccountService {

    private final Map<String, Account> accountDatabase = new HashMap<>();

    public Account createAccount(String accountId, String accountName, Account.AccountType type, double initialBalance) {
        Account account = new Account(accountId, accountName, type, initialBalance, Account.AccountStatus.ACTIVE);
        accountDatabase.put(accountId, account);
        return account;
    }
    
    // Retrieve an account by ID
    public Account getAccount(String accountId) {
        return accountDatabase.get(accountId);
    }

    public boolean updateBalance(String accountId, double newBalance) {
        Account acc = accountDatabase.get(accountId);
        if (acc != null) {
            acc.setBalance(newBalance);
            return true;
        }
        return false;
    }

    public List<Account> getAllAccounts() {
        return new ArrayList<>(accountDatabase.values());
    }

    private final Map<String, Account> accounts = new HashMap<>();

    // Add an account to the service
    public void addAccount(Account account) {
        accounts.put(account.getAccountId(), account);
    }

    

  
   
    
}


