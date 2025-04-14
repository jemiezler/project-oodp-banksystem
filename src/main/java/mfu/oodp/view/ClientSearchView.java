package mfu.oodp.view;

import mfu.oodp.controller.AccountController;
import mfu.oodp.controller.ClientController;
import mfu.oodp.model.Account;
import mfu.oodp.model.Client.Client;

import javax.swing.*;
import org.jdesktop.swingx.JXFrame;
import java.awt.*;

public class ClientSearchView extends JXFrame {
    private final ClientController clientController;
    private final AccountController accountController;

    public ClientSearchView(ClientController clientController, AccountController accountController) {
        super("🔍 Search Clients");
        this.clientController = clientController;
        this.accountController = accountController;

        setSize(450, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Top panel for search input
        JPanel topPanel = new JPanel(new FlowLayout());
        JTextField accountIdField = new JTextField(25); // Input field for Account ID
        JButton searchBtn = new JButton("Search");
        topPanel.add(new JLabel("Account ID:")); // Label for Account ID
        topPanel.add(accountIdField);
        topPanel.add(searchBtn);

        // Result panel for displaying client and account details
        JPanel resultPanel = new JPanel(new GridLayout(7, 2));
        JTextField emailField = new JTextField(); emailField.setEditable(false);
        JTextField fnameField = new JTextField(); fnameField.setEditable(false);
        JTextField lnameField = new JTextField(); lnameField.setEditable(false);
       
        resultPanel.add(new JLabel("Email:")); resultPanel.add(emailField);
        resultPanel.add(new JLabel("First Name:")); resultPanel.add(fnameField);
        resultPanel.add(new JLabel("Last Name:")); resultPanel.add(lnameField);
        
        // Search button action listener
        searchBtn.addActionListener(e -> {
            try {
                // Retrieve the Account ID from the input field
                String accountId = accountIdField.getText().trim();

                // Retrieve the account using the AccountController
                Account account = accountController.getAccount(accountId);

                if (account != null) {
                    // Retrieve the client associated with the account
                    Client client = clientController.getClientByAccountId(accountId);

                    if (client != null) {
                        // Populate the result fields with client data
                        emailField.setText(client.getEmail());
                        fnameField.setText(client.getFirstName());
                        lnameField.setText(client.getLastName());
                        

                        // Show a popup with the account details
                        JOptionPane.showMessageDialog(this,
                                "✅ Client Found!\n" +
                                        "Account ID: " + account.getAccountId() + "\n" +
                                        "Account Name: " + account.getAccountName() + "\n" +
                                        "Balance: " + account.getBalance());
                    } else {
                        JOptionPane.showMessageDialog(this, "❌ Client not found for this account.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Account not found.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        });

        add(topPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
    }
}