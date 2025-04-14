package mfu.oodp;

import javax.swing.*;

import mfu.oodp.controller.*;
import mfu.oodp.service.*;
import mfu.oodp.view.LoginView;

public class MainApp {
    public static void main(String[] args) {
        // ✅ Init all Services
        AgentService agentService = new AgentService();
        ClientService clientService = new ClientService();
        AccountService accountService = new AccountService();
        TransactionService transactionService = new TransactionService(accountService);

        // ✅ Init all Controllers
        AgentController agentController = new AgentController(agentService);
        ClientController clientController = new ClientController(clientService);
        AccountController accountController = new AccountController(accountService);
        TransactionController txController = new TransactionController(transactionService, accountService); // Fix here

        // ✅ Launch Login View (pass all controllers)
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView(
                    agentController,
                    clientController,
                    accountController,
                    txController
            );
            loginView.setVisible(true);
        });
    }
}