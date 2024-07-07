package adminPage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class adminLoginPage implements Initializable {

    @FXML
    private Button adminLoginButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Button exitButton;

    @FXML
    private Button returnButton;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField usernameInput;

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
    } // Checks if the username is contained within array list, accountList.

    boolean checkPassword(String username, String password) throws IOException {
        boolean result = false;
        FileReader fr = new FileReader("src/resources/databases/adminAccounts.txt");
        BufferedReader br = new BufferedReader(fr);

        ArrayList<String> lineList = new ArrayList<>();
        Dictionary<String, String> accountsDict = new Hashtable<>();
        String line = br.readLine();

        while (line != null) {
            lineList.add(line);
            line = br.readLine();
        }

        for (String acc : lineList) {
            accountsDict.put(acc.split(" ")[0], acc.split(" ")[1]);
        }

        br.close();

        if (password.equals(accountsDict.get(username))) {
            result = true;
        }
        return result;
    } // Checks if the password passed into the method is equal to the password taken from the adminAccounts database.

    boolean isEmpty() {
        boolean result = false;
        if (usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty()) {
            result = true;
        }
        return result;
    }// Check if textFields are empty

    @FXML
    void adminPage(ActionEvent event) throws IOException {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        
        if (isEmpty()) {
            errorLabel.setText("Please fill in all fields!");
        }
        else {
            if (containAccount(username)) {
                if (checkPassword(username, password)) {
                    Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/adminPage/adminPage.fxml"));
                    Stage window;
                    window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    window.setScene(scene);
                    window.setTitle("Admin Page");
                    window.show();
                }
                errorLabel.setText("Wrong password!");
            }
            else {
                errorLabel.setText("Account does not exist!");
            }
        }
    } // Gives user access to the admin page upon a successful login.

    @FXML
    void exitProgram(ActionEvent event) {
        Platform.exit();
    } // Shuts down the program

    @FXML
    void returnToPrev(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/mainPage.fxml"));
        Stage window;
        window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("Main Page");
        window.show();
    } // Returns the user back to the previous page.

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File charactersFile = new File("src/resources/databases/characters.txt");
        File adminAccountsFile = new File("src/resources/databases/adminAccounts.txt");
        if (!charactersFile.exists() || !adminAccountsFile.exists()) {
            errorLabel.setText("Missing files! Please reinstall the program!");
            errorLabel.setFont(new Font("Arial", 12));
        }
    } // Check for the existence of files.
}
