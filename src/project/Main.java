package project;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            EventManager eventManager = new EventManager();
            ProjectEvent projectEvent = new ProjectEvent(eventManager);

            SwingUtilities.invokeLater(() -> {
                JFrame choiceFrame = new JFrame("Welcome");
                choiceFrame.setSize(300, 200);
                choiceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                choiceFrame.setResizable(false);

                JPanel panel = new JPanel();
                choiceFrame.add(panel);
                placeComponents(panel, projectEvent, eventManager);

                choiceFrame.setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void placeComponents(JPanel panel, ProjectEvent projectEvent, EventManager eventManager) {
        panel.setLayout(null);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 50, 100, 25);
        panel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(100, 100, 100, 25);
        panel.add(registerButton);

        loginButton.addActionListener(e -> {
            new project.frame.LoginFrame(projectEvent, eventManager).setVisible(true);
            ((JFrame) SwingUtilities.getWindowAncestor(panel)).dispose();
        });

        registerButton.addActionListener(e -> {
            new project.frame.RegisterFrame(projectEvent, eventManager).setVisible(true);
            ((JFrame) SwingUtilities.getWindowAncestor(panel)).dispose();
        });
    }
}
