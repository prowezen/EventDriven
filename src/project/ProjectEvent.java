package project;

import java.sql.*;

public class ProjectEvent {
    private Connection connection;
    private EventManager eventManager;

    public ProjectEvent(EventManager eventManager) throws SQLException {
        this.eventManager = eventManager;
        this.connection = DatabaseConnection.getConnection();
    }

    public void addUser(String name, String email, String password, String role) throws SQLException {
        String query = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, role);
            stmt.executeUpdate();
        }
    }

    public void addProject(String title, String description, int organizerId) throws SQLException {
        String query = "INSERT INTO projects (title, description, organizer_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setInt(3, organizerId);
            stmt.executeUpdate();
        }
    }

    public void assignRequirement(int projectId, int userId, String title, String description, String dueDate) throws SQLException {
        String query = "INSERT INTO requirements (project_id, user_id, title, description, due_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, projectId);
            stmt.setInt(2, userId);
            stmt.setString(3, title);
            stmt.setString(4, description);
            stmt.setString(5, dueDate);
            stmt.executeUpdate();
        }
    }

    public ResultSet getUserByEmailAndPassword(String email, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, email);
        stmt.setString(2, password);
        return stmt.executeQuery();
    }

    public ResultSet getUserByName(String name) throws SQLException {
        String query = "SELECT * FROM users WHERE name = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, name);
        return stmt.executeQuery();
    }

    public ResultSet getUsers() throws SQLException {
        String query = "SELECT * FROM users";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    public ResultSet getProjectsByOrganizerId(int organizerId) throws SQLException {
        String query = "SELECT * FROM projects WHERE organizer_id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, organizerId);
        return stmt.executeQuery();
    }

    public ResultSet getProjectByTitle(String title) throws SQLException {
        String query = "SELECT * FROM projects WHERE title = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, title);
        return stmt.executeQuery();
    }

    public ResultSet getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, userId);
        return stmt.executeQuery();
    }

    public ResultSet getRequirementsByProjectId(int projectId) throws SQLException {
        String query = "SELECT * FROM requirements WHERE project_id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, projectId);
        return stmt.executeQuery();
    }

    public ResultSet getProjectById(int projectId) throws SQLException {
        String query = "SELECT * FROM projects WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, projectId);
        return stmt.executeQuery();
    }

    public ResultSet getRequirementsByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM requirements WHERE user_id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, userId);
        return stmt.executeQuery();
    }

    public void uploadFile(int userId, String requirementTitle, String filePath) throws SQLException {
        String query = "UPDATE requirements SET file_path = ?, status = 'submitted' WHERE user_id = ? AND title = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, filePath);
        stmt.setInt(2, userId);
        stmt.setString(3, requirementTitle);
        stmt.executeUpdate();
    }
    
    
}
