package mfu.oodp.model;

public class Account {
    public enum AccountType {
        SAVINGS,
        CHECKING,
        LOAN
    }

    public enum AccountStatus {
        ACTIVE,
        INACTIVE,
        CLOSED
    }
    private String accountId;
    private String accountName;
    private AccountType accountType;
    private double balance;
    private AccountStatus accountStatus;

    public Account(String accountId, String accountName, AccountType accountType, double balance, AccountStatus accountStatus) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.accountType = accountType;
        this.balance = balance;
        this.accountStatus = accountStatus;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }
}
