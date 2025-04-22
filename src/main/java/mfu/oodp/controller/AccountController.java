package mfu.oodp.controller;

import mfu.oodp.model.Account;
import mfu.oodp.operation.AccountOperation;
import mfu.oodp.service.AccountService;

import java.util.List;

public class AccountController extends BaseController implements AccountOperation {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public Account createAccount(String id, String name, Account.AccountType type, double initialBalance) {
        return accountService.createAccount(id, name, type, initialBalance);
    }

    @Override
    public Account getAccount(String id) {
        return accountService.getAccount(id);
    }

    @Override
    public boolean updateBalance(String id, double newBalance) {
        return accountService.updateBalance(id, newBalance);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @Override
    public boolean deleteAccount(String id) {
        return accountService.deleteAccount(id);
    }
}
