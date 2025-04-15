package mfu.oodp.view.client;

import mfu.oodp.controller.ClientController;
import mfu.oodp.model.Client.Client;
import mfu.oodp.service.ClientService;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXTextField;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class ClientFrame extends JXFrame {
    private final ClientController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    public ClientFrame() {
        super("Client Management");
        this.controller = new ClientController(new ClientService());
        initUI();
        loadClients();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // ==== Header ====
        JXLabel title = new JXLabel("Client Management");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // ==== Table ====
        tableModel = new DefaultTableModel(new String[]{"ID", "First Name", "Last Name", "Email", "Phone"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // ==== Buttons ====
        JPanel btnPanel = new JPanel(new FlowLayout());

        JXButton addBtn = new JXButton("Register");
        JXButton searchBtn = new JXButton("Search");
        JXButton updateBtn = new JXButton("Update");
        JXButton deleteBtn = new JXButton("Delete");
        JXButton refreshBtn = new JXButton("Refresh");

        btnPanel.add(addBtn);
        btnPanel.add(searchBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(refreshBtn);

        add(btnPanel, BorderLayout.SOUTH);

        // ==== Actions ====
        addBtn.addActionListener(e -> openClientForm(null));
        searchBtn.addActionListener(e -> searchClient());
        updateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                UUID id = UUID.fromString((String) tableModel.getValueAt(row, 0));
                Client client = controller.getClient(id);
                openClientForm(client);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a client to update");
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                UUID id = UUID.fromString((String) tableModel.getValueAt(row, 0));
                boolean deleted = controller.deleteClient(id);
                if (deleted) {
                    JOptionPane.showMessageDialog(this, "Client deleted");
                    loadClients();
                }
            }
        });

        refreshBtn.addActionListener(e -> loadClients());

        setSize(800, 500);
        setLocationRelativeTo(null);
    }

    private void loadClients() {
        tableModel.setRowCount(0);
        List<Client> clients = controller.listClients();
        for (Client c : clients) {
            tableModel.addRow(new Object[]{
                    c.getId().toString(),
                    c.getFirstName(),
                    c.getLastName(),
                    c.getEmail(),
                    c.getPhoneNumber()
            });
        }
    }

    private void searchClient() {
        JDialog dialog = new JDialog(this, "Search Clients", true);
        dialog.setLayout(new GridLayout(6, 2, 10, 10));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
    
        JXTextField emailField = new JXTextField(); emailField.setPrompt("Email");
        JXTextField firstNameField = new JXTextField(); firstNameField.setPrompt("First Name");
        JXTextField lastNameField = new JXTextField(); lastNameField.setPrompt("Last Name");
        JXTextField phoneField = new JXTextField(); phoneField.setPrompt("Phone");
        JXTextField addressField = new JXTextField(); addressField.setPrompt("Address");
    
        dialog.add(new JLabel("First Name:")); dialog.add(firstNameField);
        dialog.add(new JLabel("Last Name:")); dialog.add(lastNameField);
        dialog.add(new JLabel("Email:")); dialog.add(emailField);
        dialog.add(new JLabel("Phone:")); dialog.add(phoneField);
        dialog.add(new JLabel("Address:")); dialog.add(addressField);
    
        JButton searchBtn = new JButton("Search");
        dialog.add(new JLabel());
        dialog.add(searchBtn);
    
        searchBtn.addActionListener(e -> {
            String first = firstNameField.getText().toLowerCase();
            String last = lastNameField.getText().toLowerCase();
            String email = emailField.getText().toLowerCase();
            String phone = phoneField.getText().toLowerCase();
            String addr = addressField.getText().toLowerCase();
    
            List<Client> allClients = controller.listClients();
            tableModel.setRowCount(0); // clear table
    
            for (Client c : allClients) {
                if ((first.isEmpty() || c.getFirstName().toLowerCase().contains(first)) &&
                    (last.isEmpty() || c.getLastName().toLowerCase().contains(last)) &&
                    (email.isEmpty() || c.getEmail().toLowerCase().contains(email)) &&
                    (phone.isEmpty() || c.getPhoneNumber().toLowerCase().contains(phone)) &&
                    (addr.isEmpty() || c.getAddress().toLowerCase().contains(addr))) {
                    tableModel.addRow(new Object[]{
                        c.getId().toString(),
                        c.getFirstName(),
                        c.getLastName(),
                        c.getEmail(),
                        c.getPhoneNumber()
                    });
                }
            }
    
            dialog.dispose();
        });
    
        dialog.setVisible(true);
    }
    

    private void openClientForm(Client existing) {
        JDialog dialog = new JDialog(this, "Client Form", true);
        dialog.setLayout(new GridLayout(7, 2, 10, 10));
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);

        JXTextField emailField = new JXTextField(); emailField.setPrompt("Email");
        JXTextField firstNameField = new JXTextField(); firstNameField.setPrompt("First Name");
        JXTextField lastNameField = new JXTextField(); lastNameField.setPrompt("Last Name");
        JXTextField phoneField = new JXTextField(); phoneField.setPrompt("Phone");
        JXTextField addressField = new JXTextField(); addressField.setPrompt("Address");
        JXDatePicker dobPicker = new JXDatePicker();

        if (existing != null) {
            emailField.setText(existing.getEmail());
            firstNameField.setText(existing.getFirstName());
            lastNameField.setText(existing.getLastName());
            phoneField.setText(existing.getPhoneNumber());
            addressField.setText(existing.getAddress());
            dobPicker.setDate(existing.getDateOfBirth());
        }

        dialog.add(new JLabel("Email:")); dialog.add(emailField);
        dialog.add(new JLabel("First Name:")); dialog.add(firstNameField);
        dialog.add(new JLabel("Last Name:")); dialog.add(lastNameField);
        dialog.add(new JLabel("Phone:")); dialog.add(phoneField);
        dialog.add(new JLabel("Address:")); dialog.add(addressField);
        dialog.add(new JLabel("Date of Birth:")); dialog.add(dobPicker);

        JButton saveBtn = new JButton("Save");
        dialog.add(new JLabel()); // Empty
        dialog.add(saveBtn);

        saveBtn.addActionListener(e -> {
            String email = emailField.getText();
            String first = firstNameField.getText();
            String last = lastNameField.getText();
            String phone = phoneField.getText();
            String addr = addressField.getText();
            Timestamp dob = new Timestamp(dobPicker.getDate().getTime());

            if (existing == null) {
                controller.registerClient(email, first, last, dob, phone, addr);
                JOptionPane.showMessageDialog(this, "Client registered.");
            } else {
                existing.setEmail(email);
                existing.setFirstName(first);
                existing.setLastName(last);
                existing.setPhoneNumber(phone);
                existing.setAddress(addr);
                existing.setDateOfBirth(dob);
                controller.deleteClient(existing.getId()); // Simple: remove & re-add
                controller.registerClient(email, first, last, dob, phone, addr);
                JOptionPane.showMessageDialog(this, "Client updated.");
            }

            dialog.dispose();
            loadClients();
        });

        dialog.setVisible(true);
    }
}
