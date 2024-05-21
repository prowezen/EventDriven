package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegisterFrame extends JFrame {
    public RegisterFrame(ProjectEvent projectEvent) {
        setTitle("Register");
        setSize(400, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridLayout(6, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);

        JLabel roleLabel = new JLabel("Role:");
        JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"organizer", "user"});

        JButton registerButton = new JButton("Register");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    projectEvent.addUser(
                            nameField.getText(),
                            emailField.getText(),
                            new String(passwordField.getPassword()),
                            (String) roleComboBox.getSelectedItem()
                    );
                    JOptionPane.showMessageDialog(RegisterFrame.this, "User Registered Successfully!");
                    new LoginFrame(projectEvent).setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

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
    }
}
