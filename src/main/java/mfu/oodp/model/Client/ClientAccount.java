package mfu.oodp.model.Client;

public class ClientAccount {
    private Client clientId;
    private String accountId;
    public ClientAccount(Client clientId ,String accountId) {
        this.clientId = clientId;
        this.accountId = accountId;
    }
    public Client getClientId() {
        return clientId;
    }
    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
