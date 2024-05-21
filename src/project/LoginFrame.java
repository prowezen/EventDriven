package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    public LoginFrame(ProjectEvent projectEvent) {
        setTitle("Login");
        setSize(400, 150);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2, 10, 10));
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String email = emailField.getText();
                    String password = new String(passwordField.getPassword());
                    ResultSet rs = projectEvent.getUserByEmailAndPassword(email, password);

                    if (rs.next()) {
                        int userId = rs.getInt("id");
                        String role = rs.getString("role");
                        dispose();
                        new UserDashboardFrame(projectEvent, userId, role).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Invalid email or password");
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
