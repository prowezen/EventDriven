package project.frame;

import project.ProjectEvent;
import project.EventManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

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

        JPanel menuPanel = createMenuPanel(projectEvent, eventManager);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(menuPanel);
        centerPanel.add(Box.createVerticalGlue());

        add(centerPanel, BorderLayout.CENTER);

        // Subscribe to the events
        subscribeToEvents(eventManager);
    }

    // Method to create menu panel
    private JPanel createMenuPanel(ProjectEvent projectEvent, EventManager eventManager) {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if ("organizer".equals(role)) {
            JButton createProjectButton = createButton("Create Project", e -> new AddProjectFrame(projectEvent, userId, eventManager).setVisible(true));
            menuPanel.add(createProjectButton);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JButton viewRequirementsButton = createButton("View Assigned Requirements", e -> new ViewAssignedRequirementsFrame(projectEvent, userId, eventManager).setVisible(true));
        menuPanel.add(viewRequirementsButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        if ("organizer".equals(role)) {
            JButton manageProjectsButton = createButton("Manage Organized Projects", e -> manageOrganizedProjects(projectEvent, eventManager));
            menuPanel.add(manageProjectsButton);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JButton logoutButton = createButton("Logout", e -> {
            dispose();
            new LoginFrame(projectEvent, eventManager).setVisible(true);
        });
        menuPanel.add(logoutButton);

        return menuPanel;
    }

    // Method to create a button with an action listener
    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);
        return button;
    }

    // Method to manage organized projects
    private void manageOrganizedProjects(ProjectEvent projectEvent, EventManager eventManager) {
        JFrame frame = new JFrame("Organized Projects");
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JComboBox<String> projectComboBox = new JComboBox<>();
        JButton selectProjectButton = createButton("Select Project", e -> {
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
        });

        // Populate projectComboBox
        try {
            ResultSet rs = projectEvent.getProjectsByOrganizerId(userId);
            while (rs.next()) {
                projectComboBox.addItem(rs.getString("title"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JPanel panel = new JPanel();
        panel.add(projectComboBox);
        panel.add(selectProjectButton);
        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    // Method to subscribe to events
    private void subscribeToEvents(EventManager eventManager) {
        eventManager.subscribe("userAdded", onUserAdded);
        eventManager.subscribe("projectAdded", onProjectAdded);
        eventManager.subscribe("requirementAssigned", onRequirementAssigned);
        eventManager.subscribe("fileUploaded", onFileUploaded);
    }

    // Event handlers
    private final Consumer<Object> onUserAdded = (data) -> {
        // Handle the user added event (refresh the user list)
        System.out.println("User added event received in UserDashboardFrame");
    };

    private final Consumer<Object> onProjectAdded = (data) -> {
        // Handle the project added event (refresh project list)
        System.out.println("Project added event received in UserDashboardFrame");
    };

    private final Consumer<Object> onRequirementAssigned = (data) -> {
        // Handle the requirement assigned event (refresh requirements)
        System.out.println("Requirement assigned event received in UserDashboardFrame");
    };

    private final Consumer<Object> onFileUploaded = (data) -> {
        // Handle the file uploaded event (update file status)
        System.out.println("File uploaded event received in UserDashboardFrame");
    };
}
