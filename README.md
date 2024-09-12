# JavaFX Survey Application

## Overview

This JavaFX application is designed for managing and creating surveys with various question types, including multiple choice, open-ended, and true/false questions. It features different user roles, such as admins and users, with secure login mechanisms and encrypted passwords.

## Features

- **Admin Login Page**: Allows administrators to log in and manage surveys and user accounts.
- **Survey Creator Login Page**: Provides access to survey creation functionalities.
- **Guest Access**: Enables guests to view surveys without logging in.
- **Different Question Types**: Supports multiple choice, open-ended, and true/false questions.
- **Encrypted Passwords**: Passwords are securely handled and encrypted in text fields.
- **Account Management**: Admins can view and manage user accounts.

## File Structure

- **`Main.java`**: The main entry point of the application.
- **`adminAccounts.java`**: Handles admin account functionalities.
- **`adminLoginPage.java`**: Manages the admin login page.
- **`adminPageController.java`**: Controller for the admin page.
- **`adminViewMultipleChoiceQuestionController.java`**: Controller for viewing and managing multiple choice questions.
- **`adminViewOpenEndedQuestionController.java`**: Controller for viewing and managing open-ended questions.
- **`adminViewTrueFalseQuestionController.java`**: Controller for viewing and managing true/false questions.
- **`mainPage.java`**: Manages navigation from the main page.
- **`registerAdminPageController.java`**: Controller for the admin registration page.
- **`userCreatedSurveysController.java`**: Manages surveys created by users.
- **`viewAccountsController.java`**: Controller for viewing user accounts.
- **`viewAccountsDetailsController.java`**: Provides detailed information on user accounts.

## Installation

1. Ensure you have JavaFX installed on your system. You can download it from the [JavaFX website](https://openjfx.io/).
2. Clone the repository or download the source code.
3. Open the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse).
4. Build the project to ensure all dependencies are resolved.

## Running the Application

1. Open the `Main.java` class in your IDE.
2. Run the application. This will start the JavaFX application and display the main page with navigation options.

## How to Use

- **Admin Login**: Click the "Admin Login" button to navigate to the admin login page.
- **Survey Creator Login**: Click the "User Login" button to navigate to the survey creator login page.
- **Guest Login**: Click the "Guest" button to view the guest survey page.
- **Exit Program**: Click the "Exit Program" button to close the application.

## Code Explanation

- **`adminLoginPage.java`**: Handles the admin login process.
- **`adminPageController.java`**: Manages the admin page functionality and navigation.
- **`adminViewMultipleChoiceQuestionController.java`**: Allows admins to view and manage multiple choice questions.
- **`adminViewOpenEndedQuestionController.java`**: Allows admins to view and manage open-ended questions.
- **`adminViewTrueFalseQuestionController.java`**: Allows admins to view and manage true/false questions.
- **`registerAdminPageController.java`**: Manages admin registration.
- **`userCreatedSurveysController.java`**: Handles surveys created by users.
- **`viewAccountsController.java`**: Manages account viewing.
- **`viewAccountsDetailsController.java`**: Provides detailed account information.

## Dependencies

- JavaFX SDK
- Any additional libraries for encryption and FXML 

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For any questions or issues, please contact mohtasahmuseless@gmail.com or open an issue on the project's repository.

---

