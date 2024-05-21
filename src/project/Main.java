package project;

import java.sql.SQLException;

import javax.swing.*;
import project.frame.LoginFrame;
import project.frame.RegisterFrame;

public class Main {
    public static void main(String[] args) {
        try {
            EventManager eventManager = new EventManager();
            ProjectEvent projectEvent = new ProjectEvent(eventManager);

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JFrame choiceFrame = new JFrame("Welcome");
                    choiceFrame.setSize(300, 200);
                    choiceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    choiceFrame.setResizable(false);

                    JPanel panel = new JPanel();
                    choiceFrame.add(panel);
                    placeComponents(panel, projectEvent);

                    choiceFrame.setVisible(true);
                }

                private void placeComponents(JPanel panel, ProjectEvent projectEvent) {
                    panel.setLayout(null);

                    JButton loginButton = new JButton("Login");
                    loginButton.setBounds(100, 50, 100, 25);
                    panel.add(loginButton);

                    JButton registerButton = new JButton("Register");
                    registerButton.setBounds(100, 100, 100, 25);
                    panel.add(registerButton);

                    loginButton.addActionListener(e -> {
                        new LoginFrame(projectEvent).setVisible(true);
                        ((JFrame) SwingUtilities.getWindowAncestor(panel)).dispose();
                    });

                    registerButton.addActionListener(e -> {
                        new RegisterFrame(projectEvent).setVisible(true);
                        ((JFrame) SwingUtilities.getWindowAncestor(panel)).dispose();
                    });
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
