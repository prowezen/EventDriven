package project.frame;

import project.ProjectEvent;
import project.UserDashboardFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    public LoginFrame(ProjectEvent projectEvent) {
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ResultSet rs = projectEvent.getUserByEmailAndPassword(
                            emailField.getText(),
                            new String(passwordField.getPassword())
                    );
                    if (rs.next()) {
                        int userId = rs.getInt("id");
                        String role = rs.getString("role");
                        new UserDashboardFrame(projectEvent, userId, role).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Invalid credentials");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        loginPanel.add(emailLabel);
        loginPanel.add(emailField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);

        add(loginPanel, BorderLayout.CENTER);
    }
}
