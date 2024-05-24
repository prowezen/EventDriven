package project.frame;

import project.ProjectEvent;
import project.EventManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegisterFrame extends JFrame {
    public RegisterFrame(ProjectEvent projectEvent, EventManager eventManager) {
        setTitle("Register");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        //Frame Components
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridLayout(6, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);

        JLabel roleLabel = new JLabel("Role:");
        JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"organizer", "contributor"});

        JButton registerButton = new JButton("Register");
        
        //Add Components to Panel
        registerPanel.add(nameLabel);
        registerPanel.add(nameField);
        registerPanel.add(emailLabel);
        registerPanel.add(emailField);
        registerPanel.add(passwordLabel);
        registerPanel.add(passwordField);
        registerPanel.add(roleLabel);
        registerPanel.add(roleComboBox);
        registerPanel.add(new JLabel());
        registerPanel.add(registerButton);

        add(registerPanel, BorderLayout.CENTER);

        // action listener for register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Add the new user to the database
                    projectEvent.addUser(
                            nameField.getText(),
                            emailField.getText(),
                            new String(passwordField.getPassword()),
                            (String) roleComboBox.getSelectedItem()
                    );
                    // Show a success message
                    JOptionPane.showMessageDialog(RegisterFrame.this, "Registration Successful!");
                    // Open the login frame
                    new LoginFrame(projectEvent, eventManager).setVisible(true);
                    dispose();  
                } catch (SQLException ex) {
                    ex.printStackTrace();  
                }
            }
        });
    }
}
