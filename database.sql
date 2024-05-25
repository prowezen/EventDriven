-- Create the database
CREATE DATABASE project_mgmt_db;

-- Switch to the new database
USE project_mgmt_db;

-- Table to store user information
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('organizer', 'contributor') NOT NULL
);

-- Table to store project information
CREATE TABLE projects (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    organizer_id INT NOT NULL,
    FOREIGN KEY (organizer_id) REFERENCES users(id)
);

-- Table to store requirement information
CREATE TABLE requirements (
    id INT AUTO_INCREMENT PRIMARY KEY,
    project_id INT NOT NULL,
    assigned_user_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    file_path VARCHAR(255), -- Added file_path directly
    status ENUM('pending', 'submitted', 'reviewed', 'approved') DEFAULT 'pending', -- Modified ENUM values directly
    due_date DATE,
    FOREIGN KEY (project_id) REFERENCES projects(id),
    FOREIGN KEY (assigned_user_id) REFERENCES users(id)
);

-- Table to store file upload information
CREATE TABLE uploads (
    id INT AUTO_INCREMENT PRIMARY KEY,
    requirement_id INT NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (requirement_id) REFERENCES requirements(id)
);
