package mfu.oodp.service;

import mfu.oodp.model.Client.Client;

import java.sql.Timestamp;
import java.util.*;

public class ClientService {

    private final Map<UUID, Client> clientDatabase = new HashMap<>();
    private final Map<String, Client> clients = new HashMap<>(); // Map of Account ID to Client

    public Client registerClient(String email, String firstName, String lastName, Timestamp dob, String phone, String address) {
        UUID id = UUID.randomUUID();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Client client = new Client(id, email, firstName, lastName, dob, phone, address, now);
        clientDatabase.put(id, client);
        return client;
    }

    public Client getClientById(UUID id) {
        return clientDatabase.get(id);
    }

    public List<Client> getAllClients() {
        return new ArrayList<>(clientDatabase.values());
    }

    public boolean deleteClient(UUID id) {
        return clientDatabase.remove(id) != null;
    }

     // Retrieve client by Account ID
     public Client getClientByAccountId(String accountId) {
        return clients.get(accountId);
    }

    // Add a client to the service
    public void addClient(String accountId, Client client) {
        clients.put(accountId, client);
    }
}

