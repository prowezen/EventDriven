package project;

import java.sql.*;

public class ProjectEvent {
    private Connection connection; 
    private EventManager eventManager;  

    public ProjectEvent(EventManager eventManager) throws SQLException {
        this.eventManager = eventManager;
        this.connection = DatabaseConnection.getConnection();  // Initialize the database connection
    }

    // Methods that use EventManager

    // Add a new user and notify subscribers
    public void addUser(String name, String email, String password, String role) throws SQLException {
        String query = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, name);
        stmt.setString(2, email);
        stmt.setString(3, password);
        stmt.setString(4, role);
        stmt.executeUpdate();
        eventManager.notify("userAdded", null);  // Notify listeners that a user was added
    }

    // Add a new project and notify subscribers
    public void addProject(String title, String description, int organizerId) throws SQLException {
        String query = "INSERT INTO projects (title, description, organizer_id) VALUES (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, title);
        stmt.setString(2, description);
        stmt.setInt(3, organizerId);
        stmt.executeUpdate();
        eventManager.notify("projectAdded", null);  // Notify listeners that a project was added
    }

    // Assign a requirement and notify subscribers
    public void assignRequirement(int projectId, int userId, String title, String description, String dueDate) throws SQLException {
        String query = "INSERT INTO requirements (project_id, user_id, title, description, due_date, status) VALUES (?, ?, ?, ?, ?, 'pending')";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, projectId);
        stmt.setInt(2, userId);
        stmt.setString(3, title);
        stmt.setString(4, description);
        stmt.setString(5, dueDate);
        stmt.executeUpdate();
        eventManager.notify("requirementAssigned", null);  // Notify listeners that a requirement was assigned
    }

    // Upload a file for a requirement and notify subscribers
    public void uploadFile(int userId, String requirementTitle, String filePath) throws SQLException {
        String query = "UPDATE requirements SET file_path = ?, status = 'submitted' WHERE user_id = ? AND title = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, filePath);
        stmt.setInt(2, userId);
        stmt.setString(3, requirementTitle);
        stmt.executeUpdate();
        eventManager.notify("fileUploaded", null);  // Notify listeners that a file was uploaded
    }

    //--------------------------------------------------------------------------------------------------
    // Methods that do not use EventManager

    // Retrieve all users
    public ResultSet getUsers() throws SQLException {
        String query = "SELECT * FROM users";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    // Retrieve a user by name
    public ResultSet getUserByName(String name) throws SQLException {
        String query = "SELECT * FROM users WHERE name = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, name);
        return stmt.executeQuery();
    }

    // Retrieve a user by email and password
    public ResultSet getUserByEmailAndPassword(String email, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, email);
        stmt.setString(2, password);
        return stmt.executeQuery();
    }

    // Retrieve projects by organizer ID
    public ResultSet getProjectsByOrganizerId(int organizerId) throws SQLException {
        String query = "SELECT * FROM projects WHERE organizer_id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, organizerId);
        return stmt.executeQuery();
    }

    // Retrieve a project by ID
    public ResultSet getProjectById(int projectId) throws SQLException {
        String query = "SELECT * FROM projects WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, projectId);
        return stmt.executeQuery();
    }

    // Retrieve a project by title
    public ResultSet getProjectByTitle(String title) throws SQLException {
        String query = "SELECT * FROM projects WHERE title = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, title);
        return stmt.executeQuery();
    }

    // Retrieve requirements by project ID
    public ResultSet getRequirementsByProjectId(int projectId) throws SQLException {
        String query = "SELECT * FROM requirements WHERE project_id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, projectId);
        return stmt.executeQuery();
    }

    // Retrieve requirements by user ID
    public ResultSet getRequirementsByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM requirements WHERE user_id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, userId);
        return stmt.executeQuery();
    }

    // Retrieve a user by ID
    public ResultSet getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, userId);
        return stmt.executeQuery();
    }
}
