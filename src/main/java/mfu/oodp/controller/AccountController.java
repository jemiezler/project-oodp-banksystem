package mfu.oodp.controller;

import mfu.oodp.model.Account;
import mfu.oodp.service.AccountService;

import java.util.List;

public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public Account createAccount(String id, String name, Account.AccountType type, double initialBalance) {
        return accountService.createAccount(id, name, type, initialBalance);
    }

    public Account getAccount(String id) {
        return accountService.getAccount(id);
    }

    public boolean updateBalance(String id, double newBalance) {
        return accountService.updateBalance(id, newBalance);
    }

    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }  

    public boolean deleteAccount(String id) {
        return accountService.deleteAccount(id);
    }
}
