package project;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDashboardFrame extends JFrame {
    private int userId;
    private String role;

    public UserDashboardFrame(ProjectEvent projectEvent, int userId, String role) {
        this.userId = userId;
        this.role = role;
        setTitle("User Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        JButton createProjectButton = new JButton("Create Project");
        createProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddProjectFrame(projectEvent, userId).setVisible(true);
            }
        });
        menuPanel.add(createProjectButton);

        JButton viewRequirementsButton = new JButton("View Assigned Requirements");
        viewRequirementsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewAssignedRequirementsFrame(projectEvent, userId).setVisible(true);
            }
        });
        menuPanel.add(viewRequirementsButton);

        JButton manageProjectsButton = new JButton("Manage Organized Projects");
        manageProjectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manageOrganizedProjects(projectEvent);
            }
        });
        menuPanel.add(manageProjectsButton);

        add(menuPanel, BorderLayout.WEST);
    }

    private void manageOrganizedProjects(ProjectEvent projectEvent) {
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
                            new AssignRequirementFrame(projectEvent, projectId).setVisible(true);
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
