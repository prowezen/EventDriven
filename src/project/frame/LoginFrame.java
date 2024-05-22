package project.frame;

import project.ProjectEvent;
import project.EventManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    public LoginFrame(ProjectEvent projectEvent, EventManager eventManager) {
        setTitle("Login");
        setSize(400, 250);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        //Frame Components
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");
        
        JButton registerButton = new JButton("Don't have an account? Register");

        JPanel bottomPanel = new JPanel();
        
        
      //Add Components to Panel
        loginPanel.add(emailLabel);
        loginPanel.add(emailField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);
        bottomPanel.add(registerButton);

        add(loginPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        
        
      //Action Listener
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
                        new UserDashboardFrame(projectEvent, userId, role, eventManager).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Invalid credentials");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

    
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterFrame(projectEvent, eventManager).setVisible(true);
                dispose();
            }
        });

        
        
    }
}
