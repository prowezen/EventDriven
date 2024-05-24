package project;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Create an instance of EventManager which will handle event subscriptions and notifications.
            EventManager eventManager = new EventManager();
            
            // Create an instance of ProjectEvent which will handle business logic and generate events.
            ProjectEvent projectEvent = new ProjectEvent(eventManager);

           
            SwingUtilities.invokeLater(() -> {
                //  create the JFrame 
                JFrame choiceFrame = new JFrame("Welcome");
                choiceFrame.setSize(300, 200);
                choiceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                choiceFrame.setResizable(false);

                
                JPanel panel = new JPanel();
                choiceFrame.add(panel);
                
                // Call the method to place components (login and register buttons) on the panel.
                placeComponents(panel, projectEvent, eventManager);

                // Make the JFrame visible.
                choiceFrame.setVisible(true);
            });
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }

    private static void placeComponents(JPanel panel, ProjectEvent projectEvent, EventManager eventManager) {
        // layout null to manually place components.
        panel.setLayout(null);

        // Create login button on the panel.
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 50, 100, 25);
        panel.add(loginButton);

        // Create register button on the panel.
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(100, 100, 100, 25);
        panel.add(registerButton);

        // actionListener for login button.
        loginButton.addActionListener(e -> {
            
            new project.frame.LoginFrame(projectEvent, eventManager).setVisible(true);
            ((JFrame) SwingUtilities.getWindowAncestor(panel)).dispose();
        });

        // actionListener for register button.
        registerButton.addActionListener(e -> {
            
            new project.frame.RegisterFrame(projectEvent, eventManager).setVisible(true);
            ((JFrame) SwingUtilities.getWindowAncestor(panel)).dispose();
        });
    }
}
