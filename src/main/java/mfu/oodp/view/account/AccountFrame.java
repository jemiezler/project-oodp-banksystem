package mfu.oodp.view.account;

import mfu.oodp.controller.AccountController;
import mfu.oodp.controller.ClientController;
import mfu.oodp.model.Account;
import mfu.oodp.model.Client.Client;
import mfu.oodp.service.AccountService;
import mfu.oodp.service.ClientService;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXLabel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class AccountFrame extends JXFrame {
    private final AccountController accountController;
    private final ClientController clientController;
    private JTable table;
    private DefaultTableModel tableModel;

    public AccountFrame() {
        super("Account Management");
        this.accountController = new AccountController(new AccountService());
        this.clientController = new ClientController(new ClientService());
        initUI();
        loadAccounts();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JXLabel title = new JXLabel("Account Management");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Type", "Balance"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JXButton createBtn = new JXButton("Create Account");
        JXButton updateBtn = new JXButton("Update Balance");
        JXButton deleteBtn = new JXButton("Delete Account");
        JXButton refreshBtn = new JXButton("Refresh");

        buttonPanel.add(createBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        createBtn.addActionListener(e -> openAccountForm());
        updateBtn.addActionListener(e -> updateBalance());
        deleteBtn.addActionListener(e -> deleteAccount());
        refreshBtn.addActionListener(e -> loadAccounts());

        setSize(700, 400);
        setLocationRelativeTo(null);
    }

    private void loadAccounts() {
        tableModel.setRowCount(0);
        List<Account> accounts = accountController.getAllAccounts();
        for (Account acc : accounts) {
            tableModel.addRow(new Object[]{
                    acc.getAccountId(),
                    acc.getAccountName(),
                    acc.getAccountType(),
                    acc.getBalance()
            });
        }
    }

    private void openAccountForm() {
        JDialog dialog = new JDialog(this, "Create Account", true);
        dialog.setLayout(new GridLayout(4, 2, 10, 10));
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);

        JTextField nameField = new JTextField();
        JComboBox<Account.AccountType> typeBox = new JComboBox<>(Account.AccountType.values());
        JTextField balanceField = new JTextField();

        // 🔍 Search Client By Name
        JButton searchClientBtn = new JButton("Search Client");
        JTextField clientIdField = new JTextField();
        clientIdField.setEditable(false);

        searchClientBtn.addActionListener(e -> {
            String keyword = JOptionPane.showInputDialog(dialog, "Enter client name keyword:");
            if (keyword != null && !keyword.isEmpty()) {
                List<Client> clients = clientController.listClients().stream()
                        .filter(c -> (c.getFirstName() + " " + c.getLastName()).toLowerCase().contains(keyword.toLowerCase()))
                        .toList();
                if (clients.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "No matching clients found.");
                } else {
                    Client selected = (Client) JOptionPane.showInputDialog(
                            dialog,
                            "Select a client:",
                            "Client Search",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            clients.toArray(),
                            clients.get(0)
                    );
                    if (selected != null) {
                        nameField.setText(selected.getFirstName() + " " + selected.getLastName());
                        clientIdField.setText(selected.getId().toString());
                    }
                }
            }
        });

        dialog.add(new JLabel("Client Name:")); dialog.add(nameField);
        dialog.add(new JLabel("Account Type:")); dialog.add(typeBox);
        dialog.add(new JLabel("Initial Balance:")); dialog.add(balanceField);
        dialog.add(searchClientBtn); dialog.add(clientIdField);

        JButton createBtn = new JButton("Create");
        dialog.add(new JLabel());
        dialog.add(createBtn);

        createBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                Account.AccountType type = (Account.AccountType) typeBox.getSelectedItem();
                double balance = Double.parseDouble(balanceField.getText());
                String id = clientIdField.getText();

                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please select a client first.");
                    return;
                }
                String accountId;
                Random random = new Random();
                do {
                    accountId = String.format("%010d", Math.abs(random.nextLong()) % 1_000_000_0000L);
                } while (accountController.getAccount(accountId) != null);
                Account acc = accountController.createAccount(accountId, name, type, balance);
                JOptionPane.showMessageDialog(dialog, "Account created: " + acc.getAccountId());
                dialog.dispose();
                loadAccounts();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });

        dialog.setVisible(true);
    }

    private void updateBalance() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String id = (String) tableModel.getValueAt(row, 0);
            String name = (String) tableModel.getValueAt(row, 1);
            String input = JOptionPane.showInputDialog(this, "New balance for " + name + ":");
            try {
                double newBalance = Double.parseDouble(input);
                accountController.updateBalance(id, newBalance);
                loadAccounts();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid balance input");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an account");
        }
    }

    private void deleteAccount() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String id = (String) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete account " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                accountController.deleteAccount(id);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an account");
        }
    }
}
