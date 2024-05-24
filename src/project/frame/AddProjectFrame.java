package project.frame;

import project.ProjectEvent;
import project.EventManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddProjectFrame extends JFrame {
    private int userId;

    public AddProjectFrame(ProjectEvent projectEvent, int userId, EventManager eventManager) {
        this.userId = userId;
        setTitle("Add Project");  
        setSize(400, 300);  
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setResizable(false);  

        JPanel addProjectPanel = new JPanel();  // Create a panel for the add project form
        addProjectPanel.setLayout(new GridLayout(5, 1, 10, 10)); 

        JTextField projectTitleField = new JTextField("Project Title"); 
        JTextArea projectDescriptionField = new JTextArea("Project Description");  
        JComboBox<String> organizerComboBox = new JComboBox<>(); 
        organizerComboBox.addItem("Select Organizer"); 

        // Populate organizerComboBox with user names
        try {
            ResultSet rs = projectEvent.getUsers();  // Retrieve all users from the database
            while (rs.next()) {
                organizerComboBox.addItem(rs.getString("name"));  // Add each user's name to the combo box
            }
        } catch (SQLException ex) {
            ex.printStackTrace();  
        }

        JButton addProjectButton = new JButton("Add Project");  
        
        // Add components to the addProjectPanel
        addProjectPanel.add(new JLabel("Project Title:"));  
        addProjectPanel.add(projectTitleField);  
        addProjectPanel.add(new JLabel("Project Description:")); 
        addProjectPanel.add(new JScrollPane(projectDescriptionField)); 
        addProjectPanel.add(new JLabel("Organizer:"));  
        addProjectPanel.add(organizerComboBox);  
        addProjectPanel.add(addProjectButton);  

        add(addProjectPanel, BorderLayout.CENTER);  

        // Action Listener for addProjectButton
        addProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedOrganizer = (String) organizerComboBox.getSelectedItem();
                    if (!selectedOrganizer.equals("Select Organizer")) {
                        ResultSet rs = projectEvent.getUserByName(selectedOrganizer);  // Retrieve the selected organizer's details from the database
                        if (rs.next()) {
                            int organizerId = rs.getInt("id");
                            projectEvent.addProject(
                                    projectTitleField.getText(),
                                    projectDescriptionField.getText(),
                                    organizerId
                            );  // Add the project to the database
                            JOptionPane.showMessageDialog(AddProjectFrame.this, "Project Added Successfully!");
                            eventManager.notify("projectAdded", null);  
                            dispose(); 
                        }
                    } else {
                        JOptionPane.showMessageDialog(AddProjectFrame.this, "Please select an organizer");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();  
                }
            }
        });
    }
}
