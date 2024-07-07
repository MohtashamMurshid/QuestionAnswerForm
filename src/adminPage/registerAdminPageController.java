package adminPage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class registerAdminPageController {

    @FXML
    private PasswordField confirmPasswordInput;

    @FXML
    private Button createAdmin;

    @FXML
    private Label errorLabel;

    @FXML
    private Button exitButton;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Button returnButton;

    @FXML
    private TextField usernameInput;

    boolean containCharacters(String password) throws IOException {
        boolean result = false;
        FileReader fr = new FileReader("src/resources/databases/characters.txt");
        BufferedReader br = new BufferedReader(fr);
        ArrayList<String> characterList = new ArrayList<>();
        String line = br.readLine();

        while (line != null) {
            characterList.add(line);
            line = br.readLine();
        }
        br.close();

        for (int i = 0; i < characterList.size(); i++) {
            if (password.contains(characterList.get(i))) {
                result = true;
                break;
            }
        }
        return result;
    } // Checks if the password contains special characters.

    boolean containNumerics(String password) {
        boolean result = false;
        int number = 0;
        while (number != 10) {
            if (password.contains(Integer.toString(number))) {
                result = true;
                break;
            }
            number++;
        }
        return result;
    } // Check if the password contains numbers

    boolean containAccount(String username) throws IOException {
        boolean result = false;
        FileReader fr = new FileReader("src/resources/databases/adminAccounts.txt");
        BufferedReader br = new BufferedReader(fr);

        ArrayList<String> lineList = new ArrayList<>();
        ArrayList<String> accountList = new ArrayList<>();
        String line = br.readLine();

        while (line != null) {
            lineList.add(line);
            line = br.readLine();
        }

        for (String acc : lineList) {
            accountList.add(acc.split(" ")[0]);
        }
    
        br.close();

        if (accountList.contains(username)) {
            result = true;
        }
        return result;
    } // Checks if the username already exists

    boolean isEmpty() {
        boolean result = false;
        if (usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty() || confirmPasswordInput.getText().isEmpty()) {
            result = true;
        }
        return result;
    } // Checks if the textfields are empty

    void createAccount(String username, String password) throws IOException {
        adminAccounts aC = new adminAccounts();
        FileWriter fw = new FileWriter("src/resources/databases/adminAccounts.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);

        if (password.contains(" ")) {
            password = password.replace(" ", "_");
        }
        
        aC.setAdminUsername(username);
        aC.setAdminPassword(password);

        bw.write(aC.getAdminUsername() + " " + aC.getAdminPassword() + "\n");
        bw.close();
    } // Creates the account, and writes it to the database.

    @FXML
    void createAdminAccount(ActionEvent event) throws IOException{        
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        String confirmPassword = confirmPasswordInput.getText();
        if (isEmpty()) {
            errorLabel.setText("Please make sure all values are entered!");
            errorLabel.setTextFill(Color.RED);
        }
        else {
            if (username.contains(" ")) {
                errorLabel.setText("Username must not contain a space!");
                errorLabel.setTextFill(Color.RED);
            }
            else {
                if (containAccount(username)) {
                    errorLabel.setText("Username already exists!");
                    errorLabel.setTextFill(Color.RED);
                }
                else {
                    if (!containCharacters(password)) {
                        errorLabel.setText("Password must contain at least 1 special character!");
                        errorLabel.setTextFill(Color.RED);
                    }
                    else {
                        if (!containNumerics(password)) {
                            errorLabel.setText("Password must contain at least 1 numeric!");
                            errorLabel.setTextFill(Color.RED);   
                        }
                        else {
                            if (confirmPassword.equals(password)) {
                                createAccount(username, password);
                                errorLabel.setText("Successfully created a new account!");
                                errorLabel.setTextFill(Color.GREEN);
                            }
                            else {
                                errorLabel.setText("Please make sure your password is correct!");
                                errorLabel.setTextFill(Color.RED);
                            }
                        }
                    }
                }
            }
        }
    } // Creates the admin account upon button click when all conditions are satisfied.

    @FXML
    void exitProgram(ActionEvent event) {
        Platform.exit();
    } // Shuts down the program

    @FXML
    void returnToPreviousPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/adminPage/adminPage.fxml"));
        Stage window;
        window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("Admin page");
        window.show();
    } // Moves the user back to the admin page.

}