package mfu.oodp.view;

import mfu.oodp.controller.*;
import mfu.oodp.model.Agent.Agent;

import javax.swing.*;
import org.jdesktop.swingx.JXFrame;
import java.awt.*;

public class AdminDashboardView extends JXFrame {
    private final Agent currentAgent;

    public AdminDashboardView(Agent currentAgent,
                              AgentController agentController,
                              ClientController clientController,
                              AccountController accountController,
                              TransactionController txController) {
        super("Admin Dashboard - Welcome " + currentAgent.getFirstName());
        this.currentAgent = currentAgent;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new GridLayout(6, 1));

        JButton registerAgentBtn = new JButton("➕ Register New Agent");
        JButton registerClientBtn = new JButton("👤 Register New Client");
        JButton createAccountBtn = new JButton("🏦 Create New Account");
        JButton makeTxBtn = new JButton("💸 Perform Transaction");
        JButton viewTxBtn = new JButton("📄 View Transaction History");
        JButton logoutBtn = new JButton("🚪 Logout");

        add(registerAgentBtn);
        add(registerClientBtn);
        add(createAccountBtn);
        add(makeTxBtn);
        add(viewTxBtn);
        add(logoutBtn);

        registerAgentBtn.addActionListener(e -> new AgentRegistrationView(agentController).setVisible(true));
        registerClientBtn.addActionListener(e -> new ClientRegistrationView(clientController).setVisible(true));
        createAccountBtn.addActionListener(e -> new AccountCreationView(accountController).setVisible(true));
        makeTxBtn.addActionListener(e -> new TransactionView(txController, currentAgent).setVisible(true)); // ✅ Fix here
        viewTxBtn.addActionListener(e -> new TransactionHistoryView(txController).setVisible(true));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginView(agentController, clientController, accountController, txController).setVisible(true);
        });
    }
}

