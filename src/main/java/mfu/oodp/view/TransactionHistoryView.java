package mfu.oodp.view;

import mfu.oodp.controller.TransactionController;
import mfu.oodp.model.Transaction.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXFrame;
import java.awt.*;
import java.util.List;

public class TransactionHistoryView extends JXFrame {
    private final TransactionController txController;

    public TransactionHistoryView(TransactionController controller) {
        super("Transaction History");
        this.txController = controller;

        setSize(800, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] columns = {"ID", "From", "To", "Amount", "Type", "Time"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        List<Transaction> txs = txController.getAllTransactions();
        for (Transaction tx : txs) {
            model.addRow(new Object[]{
                    tx.getTransactionId(),
                    tx.getAccountIdFrom() != null ? tx.getAccountIdFrom().getAccountId() : "-",
                    tx.getAccountIdTo() != null ? tx.getAccountIdTo().getAccountId() : "-",
                    tx.getAmount(),
                    tx.getTransactionType(),
                    tx.getTransactionTime()
            });
        }

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);
    }
}
