package project.frame;

import com.toedter.calendar.JDateChooser;
import project.ProjectEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

public class AssignRequirementFrame extends JFrame {
    private int projectId;
    private ProjectEvent projectEvent;

    public AssignRequirementFrame(ProjectEvent projectEvent, int projectId) {
        this.projectId = projectId;
        this.projectEvent = projectEvent;
        setTitle("Assign Requirement");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel assignRequirementPanel = new JPanel();
        assignRequirementPanel.setLayout(new GridLayout(7, 2, 10, 10));

        JComboBox<String> userComboBox = new JComboBox<>();
        userComboBox.addItem("Select User");

        JTextField requirementTitleField = new JTextField("Requirement Title");
        JTextArea requirementDescriptionField = new JTextArea("Requirement Description");
        JDateChooser dueDateChooser = new JDateChooser();
        dueDateChooser.setDateFormatString("yyyy-MM-dd");
        JButton assignButton = new JButton("Assign Requirement");

        // Populate userComboBox with user names
        try {
            ResultSet rsUsers = projectEvent.getUsers();
            while (rsUsers.next()) {
                userComboBox.addItem(rsUsers.getString("name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedUser = (String) userComboBox.getSelectedItem();
                    if (!selectedUser.equals("Select User")) {
                        ResultSet rs = projectEvent.getUserByName(selectedUser);
                        if (rs.next()) {
                            int userId = rs.getInt("id");
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String dueDate = sdf.format(dueDateChooser.getDate());
                            projectEvent.assignRequirement(
                                    projectId,
                                    userId,
                                    requirementTitleField.getText(),
                                    requirementDescriptionField.getText(),
                                    dueDate
                            );
                            JOptionPane.showMessageDialog(AssignRequirementFrame.this, "Requirement Assigned Successfully!");
                            dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(AssignRequirementFrame.this, "Please select a user");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        assignRequirementPanel.add(new JLabel("User:"));
        assignRequirementPanel.add(userComboBox);
        assignRequirementPanel.add(new JLabel("Requirement Title:"));
        assignRequirementPanel.add(requirementTitleField);
        assignRequirementPanel.add(new JLabel("Requirement Description:"));
        assignRequirementPanel.add(new JScrollPane(requirementDescriptionField));
        assignRequirementPanel.add(new JLabel("Due Date:"));
        assignRequirementPanel.add(dueDateChooser);
        assignRequirementPanel.add(new JLabel());
        assignRequirementPanel.add(assignButton);

        add(assignRequirementPanel, BorderLayout.NORTH);

        // Add a table to display requirements and their status
        JPanel viewRequirementsPanel = new JPanel();
        viewRequirementsPanel.setLayout(new BorderLayout());

        JTable requirementsTable = new JTable();
        DefaultTableModel requirementsTableModel = new DefaultTableModel(
            new Object[]{"Requirement", "Assigned To", "Status", "Due Date"}, 0
        );

        // Populate the table with requirements of the selected project
        try {
            ResultSet rs = projectEvent.getRequirementsByProjectId(projectId);
            while (rs.next()) {
                String requirement = rs.getString("title");
                String assignedTo = rs.getString("user_id");
                ResultSet userRs = projectEvent.getUserById(Integer.parseInt(assignedTo));
                if (userRs.next()) {
                    assignedTo = userRs.getString("name");
                }
                String status = rs.getString("status");
                String dueDate = rs.getString("due_date");

                requirementsTableModel.addRow(new Object[]{requirement, assignedTo, status, dueDate});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        requirementsTable.setModel(requirementsTableModel);
        JScrollPane requirementsTableScrollPane = new JScrollPane(requirementsTable);

        viewRequirementsPanel.add(requirementsTableScrollPane, BorderLayout.CENTER);
        add(viewRequirementsPanel, BorderLayout.CENTER);
    }
}
