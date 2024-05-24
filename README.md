Certainly! Here is the revised README file with improved formatting:

---

# Project Management System

## Description
This Project Management System is a Java-based application designed to help event organizers manage project requirements, such as project proposals and budgets. It allows users to register, log in, create projects, assign requirements to different users, upload files for each requirement, and track the status of these requirements. The system utilizes an event-driven architecture to ensure modularity and responsiveness.

## Features
- **User Registration and Login**: Users can register as either organizers or contributors and log in to the system.
- **Project Creation**: Organizers can create new projects and assign other users as organizers.
- **Requirement Assignment**: Organizers can assign specific requirements to users, set due dates, and track the status.
- **File Upload**: Users can upload files for their assigned requirements.
- **Status Tracking**: Track the status of each requirement (pending, submitted, reviewed, approved).
- **Event-Driven Architecture**: The system uses event-driven architecture to handle updates and notifications efficiently.

## Technologies Used
- **Java**: The main programming language used.
- **Swing**: For creating the graphical user interface.
- **MySQL**: For the database to store user, project, and requirement data.
- **Event-Driven Architecture**: To manage interactions between different components of the system.

## Installation

1. **Clone the repository**:
    ```
    git clone https://github.com/your-username/project-management-system.git
    cd project-management-system
    ```

2. **Set up the database**:
    - Import the provided SQL file (`database.sql`) into your MySQL database.
    - Update the `DatabaseConnection.java` file with your MySQL credentials.

3. **Build and run the project**:
    - Use an IDE like Eclipse or IntelliJ IDEA to open the project.
    - Ensure that all dependencies are resolved.
    - Run the `Main.java` file to start the application.

## Usage

### Register:
1. Open the application and click on the "Register" button.
2. Fill in the required details and select your role (organizer or contributor).
3. Click on "Register" to create a new account.

### Login:
1. Open the application and click on the "Login" button.
2. Enter your registered email and password.
3. Click on "Login" to access the dashboard.

### Create Project (Organizer only):
1. After logging in as an organizer, click on "Create Project".
2. Enter the project title, description, and select an organizer from the dropdown.
3. Click on "Add Project" to create the project.

### Assign Requirements (Organizer only):
1. Click on "Manage Organized Projects".
2. Select a project from the dropdown and click on "Select Project".
3. Fill in the requirement details and assign it to a user.
4. Click on "Assign Requirement" to assign the requirement.

### Upload File:
1. Click on "View Assigned Requirements".
2. Select a requirement and click on "Upload File".
3. Choose the file and click on "Upload".

### Track Status:
- Organizers can track the status of all requirements within a project.
- Users can view their assigned requirements and their statuses.

## Project Structure

```plaintext
project/
── DatabaseConnection.java
── EventManager.java
── Main.java
── ProjectEvent.java
── frame/
│   ── AddProjectFrame.java
│   ── AssignRequirementFrame.java
│   ── LoginFrame.java
│   ── RegisterFrame.java
│   ── UploadFileFrame.java
│   ── UserDashboardFrame.java
│   ── ViewAssignedRequirementsFrame.java
```

- **DatabaseConnection.java**: Manages the connection to the MySQL database.
- **EventManager.java**: Handles event subscriptions and notifications.
- **Main.java**: Entry point of the application.
- **ProjectEvent.java**: Contains business logic and interacts with the database.
- **frame/**: Contains all the GUI frames for different functionalities.

## Acknowledgements
- **Toedter Calendar**: For the JDateChooser component used in the project.

---

This README file provides a comprehensive overview of the project, its features, installation steps, usage instructions, project structure, and other relevant information. Adjust any details as needed to better fit your specific project setup.
