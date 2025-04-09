package mfu.oodp.view;

import mfu.oodp.controller.TransactionController;
import mfu.oodp.model.Agent.Agent;
import mfu.oodp.model.Transaction.Transaction;
import mfu.oodp.model.Transaction.Transaction.TransactionType;

import javax.swing.*;
import org.jdesktop.swingx.JXFrame;
import java.awt.*;

public class TransactionView extends JXFrame {
    private final TransactionController txController;
    private final Agent currentAgent;

    public TransactionView(TransactionController txController, Agent agent) {
        super("Perform Transaction");
        this.txController = txController;
        this.currentAgent = agent;

        setSize(500, 350);
        setLayout(new GridLayout(7, 2));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTextField txIdField = new JTextField();
        JTextField fromField = new JTextField();
        JTextField toField = new JTextField();
        JTextField amountField = new JTextField();
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"DEPOSIT", "WITHDRAWAL", "TRANSFER"});

        JButton submitBtn = new JButton("Submit Transaction");

        add(new JLabel("Transaction ID:"));
        add(txIdField);
        add(new JLabel("From Account ID:"));
        add(fromField);
        add(new JLabel("To Account ID:"));
        add(toField);
        add(new JLabel("Amount:"));
        add(amountField);
        add(new JLabel("Type:"));
        add(typeBox);
        add(new JLabel(""));
        add(submitBtn);

        submitBtn.addActionListener(e -> {
            try {
                String txId = txIdField.getText();
                String from = fromField.getText();
                String to = toField.getText();
                double amount = Double.parseDouble(amountField.getText());
                TransactionType type = TransactionType.valueOf((String) typeBox.getSelectedItem());

                Transaction tx = txController.performTransaction(txId, from, to, amount, type, currentAgent);
                JOptionPane.showMessageDialog(this, "✅ Transaction Success: " + tx.getTransactionId());
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        });
    }
}
