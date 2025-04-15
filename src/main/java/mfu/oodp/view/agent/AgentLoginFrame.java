package mfu.oodp.view.agent;

import mfu.oodp.controller.AgentController;
import mfu.oodp.model.Agent.Agent;
import mfu.oodp.service.AgentService;
import mfu.oodp.view.MainDashboard;
import mfu.oodp.view.session.SessionContext;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXTextField;

import javax.swing.*;
import java.awt.*;

public class AgentLoginFrame extends JXFrame {
    private final AgentController controller;

    public AgentLoginFrame() {
        super("Agent Login");
        this.controller = new AgentController(new AgentService());
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JXLabel titleLabel = new JXLabel("Welcome Back, Agent");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30)); // ใหญ่ขึ้น
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 10, 20, 10)); // เพิ่ม padding

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100)); // เพิ่มความกว้าง

        // Form Panel
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // ช่องห่างเยอะขึ้น
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JXTextField usernameField = new JXTextField();
        usernameField.setPrompt("Enter your username");
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // ใหญ่ขึ้น
        usernameField.setPreferredSize(new Dimension(300, 40));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        passwordField.setPreferredSize(new Dimension(300, 40));

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        form.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        form.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        form.add(passwordField, gbc);

        container.add(form);

        // Buttons Panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));
        JXButton loginBtn = new JXButton("Login");
        loginBtn.setPreferredSize(new Dimension(160, 50)); // ใหญ่ขึ้น
        loginBtn.setBackground(new Color(0, 123, 255));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JXButton registerBtn = new JXButton("Register");
        registerBtn.setPreferredSize(new Dimension(160, 50));
        registerBtn.setBackground(new Color(40, 167, 69));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));

        btnPanel.add(loginBtn);
        btnPanel.add(registerBtn);
        container.add(btnPanel);

        // Actions
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            Agent agent = controller.login(username, password);
        
            AgentService agentService = new AgentService(); // สำหรับ logAction
        
            if (agent != null) {
                // ✅ Log login success
                agentService.logAction(agent, "LOGIN_SUCCESS", "Agent logged in via login form");
                SessionContext.currentAgent = agent;
                JOptionPane.showMessageDialog(this, "✅ Login Success! Welcome " + agent.getFirstName());
                dispose();
                new MainDashboard().setVisible(true);
            } else {
                // ❌ Log login fail (อาจจะไม่มี Agent object, ใช้ UUID null หรือไม่ log ได้เช่นกัน)
                // แนะนำ: log แบบไม่ระบุตัวตน หรือใช้ log แยกสำหรับ failed attempt
                JOptionPane.showMessageDialog(this, "❌ Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
                // Optional: log fail anonymously
                agentService.logAction(
                    new Agent(null, username, "", "", "", "", null, null, false),
                    "LOGIN_FAILED",
                    "Failed login attempt with username: " + username
                );
            }
        });
        

        registerBtn.addActionListener(e -> {
            new AgentRegisterFrame().setVisible(true);
            dispose();
        });

        add(titleLabel, BorderLayout.NORTH);
        add(container, BorderLayout.CENTER);

        setSize(900, 650); // ใหญ่ขึ้น
        setResizable(false);
        setLocationRelativeTo(null);
    }

}