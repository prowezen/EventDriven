package project.frame;

import project.ProjectEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;

public class UploadFileFrame extends JFrame {
    private int userId;
    private String requirementTitle;
    private ProjectEvent projectEvent;

    public UploadFileFrame(ProjectEvent projectEvent, int userId, String requirementTitle) {
        this.userId = userId;
        this.requirementTitle = requirementTitle;
        this.projectEvent = projectEvent;
        setTitle("Upload File");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel uploadPanel = new JPanel();
        uploadPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JLabel requirementLabel = new JLabel("Requirement: " + requirementTitle);
        JButton uploadButton = new JButton("Upload File");
        JFileChooser fileChooser = new JFileChooser();

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(UploadFileFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        projectEvent.uploadFile(userId, requirementTitle, selectedFile.getAbsolutePath());
                        JOptionPane.showMessageDialog(UploadFileFrame.this, "File Uploaded Successfully!");
                        dispose();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        uploadPanel.add(requirementLabel);
        uploadPanel.add(uploadButton);

        add(uploadPanel, BorderLayout.CENTER);
    }
}
