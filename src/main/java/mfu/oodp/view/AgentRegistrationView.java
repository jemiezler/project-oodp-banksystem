package mfu.oodp.view;

import mfu.oodp.controller.AgentController;
import mfu.oodp.model.Agent.Agent;

import javax.swing.*;
import org.jdesktop.swingx.JXFrame;
import java.awt.*;

public class AgentRegistrationView extends JXFrame {
    private final AgentController agentController;

    public AgentRegistrationView(AgentController controller) {
        super("Register New Agent");
        this.agentController = controller;

        setSize(400, 300);
        setLayout(new GridLayout(6, 2));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField emailField = new JTextField();
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();

        JButton registerBtn = new JButton("Register");

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("First Name:"));
        add(firstNameField);
        add(new JLabel("Last Name:"));
        add(lastNameField);
        add(new JLabel(""));
        add(registerBtn);

        registerBtn.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            String email = emailField.getText();
            String fname = firstNameField.getText();
            String lname = lastNameField.getText();

            Agent agent = agentController.register(user, pass, email, fname, lname);
            JOptionPane.showMessageDialog(this, "✅ Agent registered: " + agent.getUsername());
            dispose();
        });
    }
}
