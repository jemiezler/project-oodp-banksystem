package mfu.oodp.view.agent;

import mfu.oodp.controller.AgentController;
import mfu.oodp.service.AgentService;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXTextField;

import javax.swing.*;
import java.awt.*;

public class AgentRegisterFrame extends JXFrame {
    private final AgentController controller;

    public AgentRegisterFrame() {
        super("Register Agent");
        this.controller = new AgentController(new AgentService());
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JXLabel title = new JXLabel("🔐 Agent Registration");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        JXTextField usernameField = new JXTextField(); usernameField.setPrompt("Username");
        JPasswordField passwordField = new JPasswordField();
        JXTextField emailField = new JXTextField(); emailField.setPrompt("Email");
        JXTextField firstNameField = new JXTextField(); firstNameField.setPrompt("First Name");
        JXTextField lastNameField = new JXTextField(); lastNameField.setPrompt("Last Name");

        JLabel[] labels = {
            new JLabel("Username:"), new JLabel("Password:"), new JLabel("Email:"),
            new JLabel("First Name:"), new JLabel("Last Name:")
        };
        Component[] fields = {
            usernameField, passwordField, emailField, firstNameField, lastNameField
        };

        for (int i = 0; i < labels.length; i++) {
            gbc.gridy = i;
            gbc.gridx = 0;
            formPanel.add(labels[i], gbc);
            gbc.gridx = 1;
            formPanel.add(fields[i], gbc);
        }

        JXButton registerBtn = new JXButton("✅ Register");
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        registerBtn.setBackground(new Color(40, 167, 69));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setPreferredSize(new Dimension(120, 40));
        registerBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String email = emailField.getText();
            String first = firstNameField.getText();
            String last = lastNameField.getText();

            controller.register(username, password, email, first, last);
            JOptionPane.showMessageDialog(this, "🎉 Agent registered successfully!");
            new AgentLoginFrame().setVisible(true);
            dispose();
        });

        JPanel footerPanel = new JPanel();
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        footerPanel.add(registerBtn);

        add(title, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        setSize(500, 400);
        setLocationRelativeTo(null);
    }
}