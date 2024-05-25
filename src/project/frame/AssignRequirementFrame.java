package project.frame;

import com.toedter.calendar.JDateChooser;
import project.ProjectEvent;
import project.EventManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.IOException;

public class AssignRequirementFrame extends JFrame {
    private int projectId;
    private ProjectEvent projectEvent;
    private EventManager eventManager;
    private JTable requirementsTable;
    private DefaultTableModel requirementsTableModel;

    public AssignRequirementFrame(ProjectEvent projectEvent, int projectId, EventManager eventManager) {
        this.projectId = projectId;
        this.projectEvent = projectEvent;
        this.eventManager = eventManager;
        setTitle("Assign Requirement");  
        setSize(800, 600);  // Set the size of the frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setResizable(false);  

        // Subscribe to requirement updates
        eventManager.subscribe("requirementUpdated", data -> {
            loadRequirements();  // Reload requirements when an update occurs
        });

        initializeComponents();  // Initialize UI components
        loadRequirements();  // Load existing requirements
    }

    // Initialize UI components
    private void initializeComponents() {
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
                            eventManager.notify("requirementUpdated", null);  // Notify listeners about the new assignment
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

        // Add components to the assignRequirementPanel
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

        // View requirements panel
        JPanel viewRequirementsPanel = new JPanel();
        viewRequirementsPanel.setLayout(new BorderLayout());

        requirementsTable = new JTable();
        requirementsTableModel = new DefaultTableModel(
            new Object[]{"Requirement", "Assigned To", "Status", "Due Date", "File Path"}, 0
        );
        requirementsTable.setModel(requirementsTableModel);
        requirementsTable.removeColumn(requirementsTable.getColumnModel().getColumn(4));  // Hide file path column

        JScrollPane requirementsTableScrollPane = new JScrollPane(requirementsTable);

        JButton viewFileButton = new JButton("View File");
        viewFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = requirementsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String filePath = (String) requirementsTableModel.getValueAt(selectedRow, 4);
                    if (filePath != null && !filePath.isEmpty()) {
                        try {
                            Desktop.getDesktop().open(new File(filePath));
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(AssignRequirementFrame.this, "Unable to open file.");
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(AssignRequirementFrame.this, "No file uploaded for this requirement.");
                    }
                } else {
                    JOptionPane.showMessageDialog(AssignRequirementFrame.this, "Please select a requirement to view the file.");
                }
            }
        });

        viewRequirementsPanel.add(requirementsTableScrollPane, BorderLayout.CENTER);
        viewRequirementsPanel.add(viewFileButton, BorderLayout.SOUTH);
        add(viewRequirementsPanel, BorderLayout.CENTER);
    }

    // Load requirements from the database
    private void loadRequirements() {
        requirementsTableModel.setRowCount(0);  // Clear existing data
        try {
            ResultSet rs = projectEvent.getRequirementsByProjectId(projectId);
            while (rs.next()) {
                String requirement = rs.getString("title");
                String assignedTo = rs.getString("assigned_user_id");
                ResultSet userRs = projectEvent.getUserById(Integer.parseInt(assignedTo));
                if (userRs.next()) {
                    assignedTo = userRs.getString("name");
                }
                String status = rs.getString("status");
                String dueDate = rs.getString("due_date");
                String filePath = rs.getString("file_path");

                requirementsTableModel.addRow(new Object[]{requirement, assignedTo, status, dueDate, filePath});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
