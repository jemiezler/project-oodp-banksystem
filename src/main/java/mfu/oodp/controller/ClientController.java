package mfu.oodp.controller;

import mfu.oodp.model.Client.Client;
import mfu.oodp.service.ClientService;
import mfu.oodp.operation.ClientOperation;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class ClientController extends BaseController implements ClientOperation {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public Client registerClient(String email, String firstName, String lastName, Timestamp dob, String phone, String address) {
        logAction("📥 Registering client: " + email);
        return clientService.registerClient(email, firstName, lastName, dob, phone, address);
    }

    @Override
    public Client getClient(UUID id) {
        return clientService.getClientById(id);
    }

    @Override
    public List<Client> listClients() {
        return clientService.getAllClients();
    }

    @Override
    public boolean deleteClient(UUID id) {
        logAction("🗑️ Deleting client with ID: " + id);
        return clientService.deleteClient(id);
    }
}
