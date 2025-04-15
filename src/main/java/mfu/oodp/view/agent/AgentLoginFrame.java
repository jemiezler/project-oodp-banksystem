package mfu.oodp.view.agent;

import mfu.oodp.controller.AgentController;
import mfu.oodp.model.Agent.Agent;
import mfu.oodp.service.AgentService;
import mfu.oodp.view.MainDashboard;

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
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));

        // Form Panel
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JXTextField usernameField = new JXTextField();
        usernameField.setPrompt("Username");
        JPasswordField passwordField = new JPasswordField();

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("👤 Username:"), gbc);
        gbc.gridx = 1;
        form.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("🔒 Password:"), gbc);
        gbc.gridx = 1;
        form.add(passwordField, gbc);

        container.add(form);

        // Buttons Panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JXButton loginBtn = new JXButton("▶ Login");
        loginBtn.setPreferredSize(new Dimension(120, 45));
        loginBtn.setBackground(new Color(0, 123, 255));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JXButton registerBtn = new JXButton("📝 Register");
        registerBtn.setPreferredSize(new Dimension(120, 45));
        registerBtn.setBackground(new Color(40, 167, 69));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));

        btnPanel.add(loginBtn);
        btnPanel.add(registerBtn);
        container.add(btnPanel);

        loginBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            Agent agent = controller.login(username, password);
            if (agent != null) {
                JOptionPane.showMessageDialog(this, "✅ Login Success! Welcome " + agent.getFirstName());
                dispose();
                new MainDashboard().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "❌ Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerBtn.addActionListener(e -> {
            new AgentRegisterFrame().setVisible(true);
            dispose();
        });

        add(titleLabel, BorderLayout.NORTH);
        add(container, BorderLayout.CENTER);

        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}