package mfu.oodp.view;

import mfu.oodp.controller.ClientController;
import mfu.oodp.model.Client.Client;

import javax.swing.*;
import org.jdesktop.swingx.JXFrame;
import java.awt.*;
import java.util.UUID;

public class ClientSearchView extends JXFrame {
    private final ClientController clientController;

    public ClientSearchView(ClientController controller) {
        super("🔍 Search Client");
        this.clientController = controller;

        setSize(450, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel(new FlowLayout());
        JTextField clientIdField = new JTextField(25);
        JButton searchBtn = new JButton("Search");
        topPanel.add(new JLabel("Client UUID:"));
        topPanel.add(clientIdField);
        topPanel.add(searchBtn);

        JPanel resultPanel = new JPanel(new GridLayout(7, 2));
        JTextField emailField = new JTextField(); emailField.setEditable(false);
        JTextField fnameField = new JTextField(); fnameField.setEditable(false);
        JTextField lnameField = new JTextField(); lnameField.setEditable(false);
        JTextField dobField = new JTextField(); dobField.setEditable(false);
        JTextField phoneField = new JTextField(); phoneField.setEditable(false);
        JTextField addressField = new JTextField(); addressField.setEditable(false);
        JTextField createdAtField = new JTextField(); createdAtField.setEditable(false);

        resultPanel.add(new JLabel("Email:")); resultPanel.add(emailField);
        resultPanel.add(new JLabel("First Name:")); resultPanel.add(fnameField);
        resultPanel.add(new JLabel("Last Name:")); resultPanel.add(lnameField);
        resultPanel.add(new JLabel("Date of Birth:")); resultPanel.add(dobField);
        resultPanel.add(new JLabel("Phone:")); resultPanel.add(phoneField);
        resultPanel.add(new JLabel("Address:")); resultPanel.add(addressField);
        resultPanel.add(new JLabel("Created At:")); resultPanel.add(createdAtField);

        searchBtn.addActionListener(e -> {
            try {
                UUID id = UUID.fromString(clientIdField.getText().trim());
                Client client = clientController.getClient(id);
                if (client != null) {
                    emailField.setText(client.getEmail());
                    fnameField.setText(client.getFirstName());
                    lnameField.setText(client.getLastName());
                    dobField.setText(String.valueOf(client.getDateOfBirth()));
                    phoneField.setText(client.getPhoneNumber());
                    addressField.setText(client.getAddress());
                    createdAtField.setText(String.valueOf(client.getCreatedAt()));
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Client not found");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "❌ Invalid UUID format");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        });

        add(topPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
    }
}
