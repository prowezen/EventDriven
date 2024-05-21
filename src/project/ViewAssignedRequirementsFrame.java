package project;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class ViewAssignedRequirementsFrame extends JFrame {
    private int userId;
    private ProjectEvent projectEvent;

    public ViewAssignedRequirementsFrame(ProjectEvent projectEvent, int userId) {
        this.userId = userId;
        this.projectEvent = projectEvent;
        setTitle("View Assigned Requirements");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTable requirementsTable = new JTable();
        DefaultTableModel requirementsTableModel = new DefaultTableModel(
            new Object[]{"Requirement", "Project", "Status", "Due Date"}, 0
        );

        // Populate the table with requirements assigned to the user
        try {
            ResultSet rs = projectEvent.getRequirementsByUserId(userId);
            while (rs.next()) {
                String requirement = rs.getString("title");
                ResultSet projectRs = projectEvent.getProjectById(rs.getInt("project_id"));
                String project = "";
                if (projectRs.next()) {
                    project = projectRs.getString("title");
                }
                String status = rs.getString("status");
                String dueDate = rs.getString("due_date");

                requirementsTableModel.addRow(new Object[]{requirement, project, status, dueDate});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        requirementsTable.setModel(requirementsTableModel);
        JScrollPane requirementsTableScrollPane = new JScrollPane(requirementsTable);

        JButton uploadFileButton = new JButton("Upload File");
        uploadFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = requirementsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String requirementTitle = (String) requirementsTableModel.getValueAt(selectedRow, 0);
                    new UploadFileFrame(projectEvent, userId, requirementTitle).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(ViewAssignedRequirementsFrame.this, "Please select a requirement to upload the file");
                }
            }
        });

        panel.add(requirementsTableScrollPane, BorderLayout.CENTER);
        panel.add(uploadFileButton, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);
    }
}
