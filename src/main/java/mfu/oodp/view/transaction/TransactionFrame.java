package mfu.oodp.view.transaction;

import mfu.oodp.model.Agent.Agent;
import mfu.oodp.model.Transaction.Transaction;
import mfu.oodp.model.Transaction.Transaction.TransactionType;
import mfu.oodp.service.AccountService;
import mfu.oodp.service.TransactionService;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXTextField;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class TransactionFrame extends JXFrame {
    private final TransactionService transactionService;
    private final AccountService accountService;
    private JTable table;
    private DefaultTableModel tableModel;

    public TransactionFrame() {
        super("Transaction Management");
        this.accountService = new AccountService();
        this.transactionService = new TransactionService(accountService);
        initUI();
        loadTransactions();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JXLabel title = new JXLabel("Transaction Management");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{
                "ID", "Time", "From", "To", "Amount", "Type", "Agent"
        }, 0);

        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JXButton createBtn = new JXButton("Create");
        JXButton editBtn = new JXButton("Edit");
        JXButton deleteBtn = new JXButton("Delete");
        JXButton refreshBtn = new JXButton("Refresh");

        createBtn.addActionListener(e -> openCreateTransactionDialog());
        editBtn.addActionListener(e -> openEditTransactionDialog());
        deleteBtn.addActionListener(e -> deleteSelectedTransaction());
        refreshBtn.addActionListener(e -> loadTransactions());

        bottomPanel.add(createBtn);
        bottomPanel.add(editBtn);
        bottomPanel.add(deleteBtn);
        bottomPanel.add(refreshBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        setSize(900, 550);
        setLocationRelativeTo(null);
    }

    private void loadTransactions() {
        tableModel.setRowCount(0);
        List<Transaction> transactions = transactionService.getTransactionHistory();

        for (Transaction tx : transactions) {
            String from = tx.getFromAccount() != null ? tx.getFromAccount().getAccountId() : "-";
            String to = tx.getToAccount() != null ? tx.getToAccount().getAccountId() : "-";
            String agentName = tx.getAgentId() != null ? tx.getAgentId().getFirstName() + " " + tx.getAgentId().getLastName() : "-";
            tableModel.addRow(new Object[]{
                    tx.getTransactionId(),
                    tx.getTransactionTime(),
                    from,
                    to,
                    tx.getAmount(),
                    tx.getTransactionType().name(),
                    agentName
            });
        }
    }

    private void openCreateTransactionDialog() {
        JDialog dialog = new JDialog(this, "Create Transaction", true);
        dialog.setLayout(new GridLayout(6, 2, 10, 10));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JXTextField fromField = new JXTextField(); fromField.setPrompt("From Account ID");
        JXTextField toField = new JXTextField(); toField.setPrompt("To Account ID");
        JXTextField amountField = new JXTextField(); amountField.setPrompt("Amount");
        JComboBox<TransactionType> typeBox = new JComboBox<>(TransactionType.values());

        dialog.add(new JLabel("From Account:")); dialog.add(fromField);
        dialog.add(new JLabel("To Account:")); dialog.add(toField);
        dialog.add(new JLabel("Amount:")); dialog.add(amountField);
        dialog.add(new JLabel("Type:")); dialog.add(typeBox);

        JButton submitBtn = new JButton("Submit");
        submitBtn.addActionListener(e -> {
            try {
                String from = fromField.getText().isBlank() ? null : fromField.getText();
                String to = toField.getText().isBlank() ? null : toField.getText();
                double amount = Double.parseDouble(amountField.getText());
                TransactionType type = (TransactionType) typeBox.getSelectedItem();
                Agent dummyAgent = new Agent(UUID.randomUUID(), "sys", "", "sys@example.com", "System", "Agent", new Timestamp(System.currentTimeMillis()), null, true);
                Transaction tx = transactionService.createTransaction(UUID.randomUUID().toString(), from, to, amount, type, dummyAgent);
                JOptionPane.showMessageDialog(this, "Transaction successful: " + tx.getTransactionId());
                dialog.dispose();
                loadTransactions();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        dialog.add(new JLabel());
        dialog.add(submitBtn);
        dialog.setVisible(true);
    }

    private void openEditTransactionDialog() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a transaction to edit.");
            return;
        }

        String transactionId = (String) tableModel.getValueAt(row, 0);
        String currentAmount = String.valueOf(tableModel.getValueAt(row, 4));
        String currentType = (String) tableModel.getValueAt(row, 5);

        JXTextField amountField = new JXTextField(currentAmount);
        JComboBox<TransactionType> typeBox = new JComboBox<>(TransactionType.values());
        typeBox.setSelectedItem(TransactionType.valueOf(currentType));

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Amount:")); panel.add(amountField);
        panel.add(new JLabel("Type:")); panel.add(typeBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Transaction", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                double newAmount = Double.parseDouble(amountField.getText());
                TransactionType newType = (TransactionType) typeBox.getSelectedItem();
                transactionService.updateTransaction(transactionId, newAmount, newType);
                JOptionPane.showMessageDialog(this, "Transaction updated.");
                loadTransactions();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating transaction: " + ex.getMessage());
            }
        }
    }

    private void deleteSelectedTransaction() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a transaction to delete.");
            return;
        }
        String transactionId = (String) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete transaction " + transactionId + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            transactionService.deleteTransaction(transactionId);
            JOptionPane.showMessageDialog(this, "Transaction deleted.");
            loadTransactions();
        }
    }
}