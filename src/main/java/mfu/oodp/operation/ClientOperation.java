package mfu.oodp.operation;

import mfu.oodp.model.Client.Client;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface ClientOperation {
    Client registerClient(String email, String firstName, String lastName, Timestamp dob, String phone, String address);
    Client getClient(UUID id);
    List<Client> listClients();
    boolean deleteClient(UUID id);
}
