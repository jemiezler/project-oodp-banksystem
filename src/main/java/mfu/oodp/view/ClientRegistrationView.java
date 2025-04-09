package mfu.oodp.view;

import mfu.oodp.controller.ClientController;
import mfu.oodp.model.Client.Client;

import javax.swing.*;
import org.jdesktop.swingx.JXFrame;
import java.awt.*;
import java.sql.Timestamp;
import java.util.UUID;

public class ClientRegistrationView extends JXFrame {
    private final ClientController clientController;

    public ClientRegistrationView(ClientController controller) {
        super("Register New Client");
        this.clientController = controller;

        setSize(400, 350);
        setLayout(new GridLayout(8, 2));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTextField emailField = new JTextField();
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField dobField = new JTextField(); // Format: yyyy-MM-dd
        JTextField phoneField = new JTextField();
        JTextField addressField = new JTextField();

        JButton registerBtn = new JButton("Register Client");

        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("First Name:"));
        add(firstNameField);
        add(new JLabel("Last Name:"));
        add(lastNameField);
        add(new JLabel("Date of Birth (yyyy-MM-dd):"));
        add(dobField);
        add(new JLabel("Phone Number:"));
        add(phoneField);
        add(new JLabel("Address:"));
        add(addressField);
        add(new JLabel(""));
        add(registerBtn);

        registerBtn.addActionListener(e -> {
            try {
                String email = emailField.getText();
                String fname = firstNameField.getText();
                String lname = lastNameField.getText();
                Timestamp dob = Timestamp.valueOf(dobField.getText() + " 00:00:00");
                String phone = phoneField.getText();
                String address = addressField.getText();

                Client client = clientController.registerClient(email, fname, lname, dob, phone, address);
                JOptionPane.showMessageDialog(this, "✅ Client registered: " + client.getId());
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        });
    }
}
