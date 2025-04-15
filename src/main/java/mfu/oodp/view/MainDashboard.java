package mfu.oodp.view;

import mfu.oodp.model.Agent.AgentActionLog;
import mfu.oodp.service.AccountService;
import mfu.oodp.service.AgentService;
import mfu.oodp.service.TransactionService;
import mfu.oodp.view.account.AccountFrame;
import mfu.oodp.view.agent.AgentLogViewer;
import mfu.oodp.view.client.ClientFrame;
import mfu.oodp.view.transaction.TransactionFrame;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXLabel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainDashboard extends JXFrame {

    public MainDashboard() {
        super("Banking System Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== Header =====
        JXLabel title = new JXLabel("🏦 Banking System Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // ===== Button Grid =====
        JPanel grid = new JPanel(new GridLayout(2, 2, 20, 20));
        grid.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        // ปุ่มต่าง ๆ
        JXButton clientBtn = new JXButton("👥 Manage Clients");
        JXButton accountBtn = new JXButton("💳 Manage Accounts");
        JXButton transactionBtn = new JXButton("💸 Manage Transactions");
        JXButton viewLogsBtn = new JXButton("📜 View Agent Logs"); // ✅ ปุ่มใหม่

        // Action Listeners
        clientBtn.addActionListener(e -> new ClientFrame().setVisible(true));
        accountBtn.addActionListener(e -> new AccountFrame().setVisible(true));
        transactionBtn.addActionListener(e -> new TransactionFrame().setVisible(true));
        viewLogsBtn.addActionListener(e -> AgentLogViewer.show(this));

        // Add ปุ่มเข้า grid
        grid.add(clientBtn);
        grid.add(accountBtn);
        grid.add(transactionBtn);
        grid.add(viewLogsBtn); // ✅ ปุ่มใหม่

        add(grid, BorderLayout.CENTER);

        // ===== Window Setup =====
        setSize(700, 450);
        setLocationRelativeTo(null);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainDashboard().setVisible(true));
    }
}
