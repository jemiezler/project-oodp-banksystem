package mfu.oodp.controller;

import mfu.oodp.model.Client.Client;
import mfu.oodp.service.ClientService;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    public Client registerClient(String email, String firstName, String lastName, Timestamp dob, String phone, String address) {
        return clientService.registerClient(email, firstName, lastName, dob, phone, address);
    }

    public Client getClient(UUID id) {
        return clientService.getClientById(id);
    }

    public List<Client> listClients() {
        return clientService.getAllClients();
    }

    public boolean deleteClient(UUID id) {
        return clientService.deleteClient(id);
    }

     // Retrieve client by Account ID
     public Client getClientByAccountId(String accountId) {
        return clientService.getClientByAccountId(accountId);
    }
}

