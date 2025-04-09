package mfu.oodp.view;

import mfu.oodp.controller.*;
import mfu.oodp.model.Agent.Agent;

import javax.swing.*;
import org.jdesktop.swingx.JXFrame;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginView extends JXFrame {
    private final AgentController agentController;
    private final ClientController clientController;
    private final AccountController accountController;
    private final TransactionController txController;

    public LoginView(AgentController agentController, ClientController clientController,
                     AccountController accountController, TransactionController txController) {
        super("Bank System - Login");
        this.agentController = agentController;
        this.clientController = clientController;
        this.accountController = accountController;
        this.txController = txController;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(5, 1));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register"); // ✅ เพิ่มปุ่ม Register

        add(new JLabel("Username"));
        add(usernameField);
        add(new JLabel("Password"));
        add(passwordField);
        add(loginBtn);
        add(registerBtn); // ✅ เพิ่มปุ่มลงใน UI

        loginBtn.addActionListener((ActionEvent e) -> {
            Agent agent = agentController.login(usernameField.getText(), new String(passwordField.getPassword()));
            if (agent != null) {
                JOptionPane.showMessageDialog(this, "✅ Login success!");
                new DashboardView(agent, agentController, clientController, accountController, txController).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Login failed.");
            }
        });

        // ✅ เปิดหน้าสมัคร Agent
        registerBtn.addActionListener(e -> {
            new AgentRegistrationView(agentController).setVisible(true);
        });
    }
}
