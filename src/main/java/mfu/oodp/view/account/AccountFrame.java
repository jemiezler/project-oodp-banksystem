package mfu.oodp.view.account;

import mfu.oodp.controller.AccountController;
import mfu.oodp.controller.ClientController;
import mfu.oodp.model.Account;
import mfu.oodp.model.Client.Client;
import mfu.oodp.service.AccountService;
import mfu.oodp.service.AgentService;
import mfu.oodp.service.ClientService;
import mfu.oodp.view.session.SessionContext;

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
        tableModel = new DefaultTableModel(new String[] { "ID", "Name", "Type", "Balance" }, 0);
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
            tableModel.addRow(new Object[] {
                    acc.getAccountId(),
                    acc.getAccountName(),
                    acc.getAccountType(),
                    acc.getBalance()
            });
        }
    }

    private void openAccountForm() {
        JDialog dialog = new JDialog(this, "Create Account", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(500, 350);
        dialog.setLocationRelativeTo(this);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
    
        // Components
        JLabel nameLabel = new JLabel("Client Name:");
        JTextField nameField = new JTextField();
        nameField.setEditable(false);
    
        JButton searchClientBtn = new JButton("Search Client");
        JTextField clientIdField = new JTextField();
        clientIdField.setEditable(false);
    
        JLabel typeLabel = new JLabel("Account Type:");
        JComboBox<Account.AccountType> typeBox = new JComboBox<>(Account.AccountType.values());
    
        JLabel balanceLabel = new JLabel("Initial Balance:");
        JTextField balanceField = new JTextField();
    
        JButton createBtn = new JButton("✅ Create Account");
    
        // Row 0: Client Name
        dialog.add(nameLabel, gbc);
        gbc.gridx = 1;
        dialog.add(nameField, gbc);
    
        // Row 1: Search Client button
        gbc.gridx = 0;
        gbc.gridy++;
        dialog.add(searchClientBtn, gbc);
        gbc.gridx = 1;
        dialog.add(clientIdField, gbc);
    
        // Row 2: Account Type
        gbc.gridx = 0;
        gbc.gridy++;
        dialog.add(typeLabel, gbc);
        gbc.gridx = 1;
        dialog.add(typeBox, gbc);
    
        // Row 3: Balance
        gbc.gridx = 0;
        gbc.gridy++;
        dialog.add(balanceLabel, gbc);
        gbc.gridx = 1;
        dialog.add(balanceField, gbc);
    
        // Row 4: Create button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(createBtn, gbc);
    
        // 🔍 Search Client Logic
        searchClientBtn.addActionListener(e -> {
            String keyword = JOptionPane.showInputDialog(dialog, "Enter client name keyword:");
            if (keyword != null && !keyword.isEmpty()) {
                List<Client> clients = clientController.listClients().stream()
                        .filter(c -> (c.getFirstName() + " " + c.getLastName()).toLowerCase().contains(keyword.toLowerCase()))
                        .toList();
        
                if (clients.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "No matching clients found.");
                    return;
                }
        
                // === Show result table ===
                JDialog resultDialog = new JDialog(dialog, "Select Client", true);
                resultDialog.setSize(700, 300);
                resultDialog.setLayout(new BorderLayout());
                resultDialog.setLocationRelativeTo(dialog);
        
                String[] columns = {"ID", "First Name", "Last Name", "Email", "Phone", "Address"};
                DefaultTableModel model = new DefaultTableModel(columns, 0);
                JTable clientTable = new JTable(model);
        
                for (Client c : clients) {
                    model.addRow(new Object[]{
                        c.getId().toString(),
                        c.getFirstName(),
                        c.getLastName(),
                        c.getEmail(),
                        c.getPhoneNumber(),
                        c.getAddress()
                    });
                }
        
                JScrollPane scrollPane = new JScrollPane(clientTable);
                resultDialog.add(scrollPane, BorderLayout.CENTER);
        
                JButton selectBtn = new JButton("Select");
                resultDialog.add(selectBtn, BorderLayout.SOUTH);
        
                selectBtn.addActionListener(ev -> {
                    int selectedRow = clientTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        String selectedId = (String) model.getValueAt(selectedRow, 0);
                        String fullName = model.getValueAt(selectedRow, 1) + " " + model.getValueAt(selectedRow, 2);
        
                        nameField.setText(fullName);
                        clientIdField.setText(selectedId);
        
                        resultDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(resultDialog, "Please select a client.");
                    }
                });
        
                resultDialog.setVisible(true);
            }
        });
        
        // ✅ Create Account Logic
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
    
                // ✅ Log
                AgentService agentService = new AgentService();
                agentService.logAction(SessionContext.currentAgent, "CREATE_ACCOUNT",
                        "Created account: " + acc.getAccountId());
    
                JOptionPane.showMessageDialog(dialog, "🎉 Account created: " + acc.getAccountId());
                dialog.dispose();
                loadAccounts();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "❌ Error: " + ex.getMessage());
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
                AgentService agentService = new AgentService();
                agentService.logAction(SessionContext.currentAgent, "UPDATE_BALANCE",
                        "Updated balance for account: " + id);
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
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete account " + id + "?",
                    "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                accountController.deleteAccount(id);
                AgentService agentService = new AgentService();
                agentService.logAction(SessionContext.currentAgent, "DELETE_ACCOUNT", "Deleted account: " + id);
                loadAccounts();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an account");
        }
    }
}
