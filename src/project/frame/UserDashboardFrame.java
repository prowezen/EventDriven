package project.frame;

import project.ProjectEvent;
import project.EventManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDashboardFrame extends JFrame {
    private int userId;
    private String role;

    public UserDashboardFrame(ProjectEvent projectEvent, int userId, String role, EventManager eventManager) {
        this.userId = userId;
        this.role = role;
        setTitle("User Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if ("organizer".equals(role)) {
            JButton createProjectButton = new JButton("Create Project");
            createProjectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            createProjectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new AddProjectFrame(projectEvent, userId, eventManager).setVisible(true);
                }
            });
            menuPanel.add(createProjectButton);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JButton viewRequirementsButton = new JButton("View Assigned Requirements");
        viewRequirementsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewRequirementsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewAssignedRequirementsFrame(projectEvent, userId, eventManager).setVisible(true);
            }
        });
        menuPanel.add(viewRequirementsButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        if ("organizer".equals(role)) {
            JButton manageProjectsButton = new JButton("Manage Organized Projects");
            manageProjectsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            manageProjectsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    manageOrganizedProjects(projectEvent, eventManager);
                }
            });
            menuPanel.add(manageProjectsButton);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JButton logoutButton = new JButton("Logout");
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginFrame(projectEvent, eventManager).setVisible(true);
            }
        });
        menuPanel.add(logoutButton);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(menuPanel);
        centerPanel.add(Box.createVerticalGlue());

        add(centerPanel, BorderLayout.CENTER);
    }

    private void manageOrganizedProjects(ProjectEvent projectEvent, EventManager eventManager) {
        JFrame frame = new JFrame("Organized Projects");
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JComboBox<String> projectComboBox = new JComboBox<>();
        JButton selectProjectButton = new JButton("Select Project");

        // Populate projectComboBox
        try {
            ResultSet rs = projectEvent.getProjectsByOrganizerId(userId);
            while (rs.next()) {
                projectComboBox.addItem(rs.getString("title"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        selectProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProjectTitle = (String) projectComboBox.getSelectedItem();
                if (selectedProjectTitle != null) {
                    try {
                        ResultSet rs = projectEvent.getProjectByTitle(selectedProjectTitle);
                        if (rs.next()) {
                            int projectId = rs.getInt("id");
                            new AssignRequirementFrame(projectEvent, projectId, eventManager).setVisible(true);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JPanel panel = new JPanel();
        panel.add(projectComboBox);
        panel.add(selectProjectButton);
        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
