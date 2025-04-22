    package mfu.oodp.operation;

    import mfu.oodp.model.Account;
    import java.util.List;

    public interface AccountOperation {
        Account createAccount(String id, String name, Account.AccountType type, double initialBalance);
        Account getAccount(String id);
        boolean updateBalance(String id, double newBalance);
        List<Account> getAllAccounts();
        boolean deleteAccount(String id);
    }
