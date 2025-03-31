package mfu.oodp.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

class BankSystem {
    private static final String FILE_NAME = "clients.txt";
    private List<Client> clients = new ArrayList<>();
    private List<WithdrawalRequest> withdrawalRequests = new ArrayList<>();

    public void addClient(Client client) {
        clients.add(client);
    }

    public void saveClientsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(clients);
            System.out.println("Client data saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving client data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadClientsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            clients = (List<Client>) ois.readObject();
            System.out.println("Client data loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading client data: " + e.getMessage());
        }
    }

    public void displayAllClients() {
        for (Client client : clients) {
            client.displayBalance();
        }
    }

    public Client getClient(String name) {
        for (Client client : clients) {
            if (client.getName().equalsIgnoreCase(name)) {
                return client;
            }
        }
        return null;
    }

    public void submitWithdrawalRequest(String clientName, double amount) {
        withdrawalRequests.add(new WithdrawalRequest(clientName, amount));
        System.out.println("Withdrawal request submitted for " + amount);
    }

    public void viewWithdrawalRequests() {
        for (WithdrawalRequest request : withdrawalRequests) {
            System.out.println(request);
        }
    }

    public void approveWithdrawalRequest(String clientName) {
        for (WithdrawalRequest request : withdrawalRequests) {
            if (request.getClientName().equalsIgnoreCase(clientName) && request.getStatus().equals("Pending")) {
                Client client = getClient(clientName);
                if (client != null) {
                    if (client.getBalance() >= request.getAmount()) {
                        try {
                            client.withdraw(request.getAmount());
                            request.approve();
                            System.out.println("Withdrawal request for " + clientName + " has been approved.");
                            saveClientsToFile(); // Save updated balance to file
                        } catch (InsufficientFundsException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Insufficient balance to approve the withdrawal request.");
                    }
                } else {
                    System.out.println("Client not found.");
                }
                return;
            }
        }
        System.out.println("No pending withdrawal request found for " + clientName + ".");
    }
    
    public void rejectWithdrawalRequest(String clientName) {
        for (WithdrawalRequest request : withdrawalRequests) {
            if (request.getClientName().equalsIgnoreCase(clientName) && request.getStatus().equals("Pending")) {
                request.reject();
                System.out.println("Withdrawal request for " + clientName + " has been rejected.");
                return;
            }
        }
        System.out.println("No pending withdrawal request found for " + clientName + ".");
    }
}
