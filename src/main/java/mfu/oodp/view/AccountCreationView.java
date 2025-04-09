package mfu.oodp.view;

import mfu.oodp.controller.AccountController;
import mfu.oodp.model.Account;

import javax.swing.*;
import org.jdesktop.swingx.JXFrame;
import java.awt.*;

public class AccountCreationView extends JXFrame {
    private final AccountController accountController;

    public AccountCreationView(AccountController controller) {
        super("Create New Account");
        this.accountController = controller;

        setSize(400, 300);
        setLayout(new GridLayout(6, 2));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTextField accountIdField = new JTextField();
        JTextField accountNameField = new JTextField();
        JComboBox<String> accountTypeBox = new JComboBox<>(new String[]{"SAVINGS", "CHECKING", "LOAN"});
        JTextField initialBalanceField = new JTextField();

        JButton createBtn = new JButton("Create Account");

        add(new JLabel("Account ID:"));
        add(accountIdField);
        add(new JLabel("Account Name:"));
        add(accountNameField);
        add(new JLabel("Account Type:"));
        add(accountTypeBox);
        add(new JLabel("Initial Balance:"));
        add(initialBalanceField);
        add(new JLabel(""));
        add(createBtn);

        createBtn.addActionListener(e -> {
            String id = accountIdField.getText();
            String name = accountNameField.getText();
            String typeStr = (String) accountTypeBox.getSelectedItem();
            double balance = Double.parseDouble(initialBalanceField.getText());

            Account acc = accountController.createAccount(id, name, Account.AccountType.valueOf(typeStr), balance);
            JOptionPane.showMessageDialog(this, "✅ Account Created: " + acc.getAccountId());
            dispose();
        });
    }
}
