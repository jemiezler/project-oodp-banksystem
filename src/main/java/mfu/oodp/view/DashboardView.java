package mfu.oodp.view;

import mfu.oodp.controller.*;
import mfu.oodp.model.Agent.Agent;

import javax.swing.*;
import org.jdesktop.swingx.JXFrame;
import java.awt.*;

public class DashboardView extends JXFrame {
    private final Agent agent;
    private final AgentController agentController;
    private final ClientController clientController;
    private final AccountController accountController;
    private final TransactionController txController;

    public DashboardView(Agent agent,
            AgentController agentController,
            ClientController clientController,
            AccountController accountController,
            TransactionController txController) {

        super("Dashboard - Welcome " + agent.getFirstName());
        this.agent = agent;
        this.agentController = agentController;
        this.clientController = clientController;
        this.accountController = accountController;
        this.txController = txController;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new GridLayout(4, 1));

        JButton accountBtn = new JButton("Create Account");
        JButton txBtn = new JButton("Make Transaction");
        JButton searchClientBtn = new JButton("🔍 Search Client");
        JButton logoutBtn = new JButton("Logout");
        searchClientBtn.addActionListener(e -> new ClientSearchView(clientController).setVisible(true));

        accountBtn.addActionListener(e -> new AccountCreationView(accountController).setVisible(true));

        txBtn.addActionListener(e -> new TransactionView(txController, agent).setVisible(true));

        logoutBtn.addActionListener(e -> {
            new LoginView(agentController, clientController, accountController, txController).setVisible(true);
            this.dispose();
        });

        add(accountBtn);
        add(txBtn);
        add(searchClientBtn);
        add(logoutBtn);
    }
}
