package surveyCreatorPage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class createSurveyCreatorAccountController implements Initializable{

    @FXML
    private Button clearButton;

    @FXML
    private Button createAccountButton;

    @FXML
    private TextField emailAddressTextField;

    @FXML
    private Label errorLabel;
    
    @FXML
    private PasswordField confirmPasswordTextField;

    @FXML
    private TextField facultyTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField genderTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private Button returnButton;

    @FXML
    private TextField scIdTextField;

    @FXML
    private TextField usernameTextField;

    boolean isEmpty() {
        boolean result = false;
        if (usernameTextField.getText().equals("") || passwordTextField.getText().equals("") || confirmPasswordTextField.getText().equals("") || firstNameTextField.getText().equals("") || lastNameTextField.getText().equals("") || facultyTextField.getText().equals("") || genderTextField.getText().equals("") || emailAddressTextField.getText().equals("") || phoneNumberTextField.getText().equals("")) {
            result = true;
        }
        return result;
    }

    boolean containNumerics(String fieldString) {
        boolean result = false;
        int number = 0;
        while (number != 10) {
            if (fieldString.contains(Integer.toString(number))) {
                result = true;
                break;
            }
            number++;
        }
        return result;
    }

    boolean containCharacters(String fieldString) throws IOException {
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
            if (fieldString.contains(characterList.get(i))) {
                result = true;
                break;
            }
        }
        return result;
    }

    boolean containAlphabets(String fieldString) throws IOException {
        boolean result = false;
        
        File alphabetsFile = new File("src/resources/databases/alphabets.txt");

        FileReader fr = new FileReader(alphabetsFile);
        BufferedReader br = new BufferedReader(fr);

        ArrayList<String> alphabetsList = new ArrayList<>();
        String line = br.readLine();

        while (line != null) {
            alphabetsList.add(line);
            line = br.readLine();
        }
        br.close();

        for (int i = 0; i < alphabetsList.size(); i++) {
            if (fieldString.contains(alphabetsList.get(i))) {
                result = true;
                break;
            }
        }
        
        return result;
    }

    boolean emailContains(String emailAddString) {
        boolean result = false;
        if (!emailAddString.contains("@") || !emailAddString.contains(".")) {
            result = true;
        }
        return result;
    }

    boolean containAccount(String username) throws IOException {
        boolean result = false;
        FileReader fr = new FileReader("src/resources/databases/surveyCreatorAccounts.txt");
        BufferedReader br = new BufferedReader(fr);

        ArrayList<String> lineList = new ArrayList<>();
        ArrayList<String> usernameList = new ArrayList<>();
        String line = br.readLine();

        while (line != null) {
            lineList.add(line);
            line = br.readLine();
        }

        for (String acc : lineList) {
            usernameList.add(acc.split(" ")[1]);
        }
    
        br.close();

        if (usernameList.contains(username)) {
            result = true;
        }
        return result;
    }

    void createAccount(String scIDString, String scUsernameString, String scPasswordString, String scFirstName, String scSurname, String scFaculty, String scEmailAddress, String scGenderString, String scPhoneNumber) throws IOException {

        File surveyCreatorAccountsFile = new File("src/resources/databases/surveyCreatorAccounts.txt");

        FileWriter fw = new FileWriter(surveyCreatorAccountsFile, true);
        BufferedWriter bw = new BufferedWriter(fw);

        surveyCreatorAccounts scAccounts = new surveyCreatorAccounts();

        if (scFirstName.contains(" ")) {
            scFirstName = scFirstName.replace(" ", "_");
        }

        if (scSurname.contains(" ")) {
            scSurname = scSurname.replace(" ", "_");
        }

        if (scFaculty.contains(" ")) {
            scFaculty = scFaculty.replace(" ", "_");
        }

        scAccounts.setScId(scIDString);
        scAccounts.setScUsername(scUsernameString);
        scAccounts.setScPassword(scPasswordString);
        scAccounts.setScGivenName(scFirstName);
        scAccounts.setScSurName(scSurname);
        scAccounts.setScFaculty(scFaculty);
        scAccounts.setScEmailAdress(scEmailAddress);
        scAccounts.setScGender(scGenderString);
        scAccounts.setScPhoneNumber(scPhoneNumber);

        bw.write(scAccounts.getScId() + " " + scAccounts.getScUsername() + " " + scAccounts.getScPassword() + " " + scAccounts.getScGivenName() + " " + scAccounts.getScSurName() + " " + scAccounts.getScFaculty() + " " + scAccounts.getScEmailAdress() + " " + scAccounts.getScGender() + " " + scAccounts.getScPhoneNumber() + "\n");

        bw.close();
    }

    @FXML
    void clearFields(ActionEvent event) {
        usernameTextField.setText("");
        passwordTextField.setText("");
        confirmPasswordTextField.setText("");
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        facultyTextField.setText("");
        emailAddressTextField.setText("");
        phoneNumberTextField.setText("");
        genderTextField.setText("");
    }

    int getSurveyCreatorId() throws IOException {
        File surveyCreatorAccounts = new File("src/resources/databases/surveyCreatorAccounts.txt");

        int i = 0;
        
        FileReader fr = new FileReader(surveyCreatorAccounts);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();

        ArrayList<String> lineList = new ArrayList<>();
        ArrayList<String> scIDList = new ArrayList<>();

        if (line == null) {
            i = 0;
        }
        else {
            while (line != null) {
                lineList.add(line);
                line = br.readLine();
            }
            br.close();

            for (String scId : lineList) {
                if (scId.split(" ")[0].contains("SC")) {
                    scIDList.add(scId.split(" ")[0].replace("SC", ""));
                }
            }
            i = Integer.parseInt(scIDList.get(scIDList.size()-1));
            i++;
        }
        return i;
    }

    @FXML
    void createAccount(ActionEvent event) throws IOException {
        String scID;
        if (scIdTextField.getText().contains("SC")) {
            scID = scIdTextField.getText().replace("SC", "");
        }
        else {
            scID = scIdTextField.getText();
        }
        String scUsername = usernameTextField.getText();
        String scPassword = passwordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();
        String scGivenName = firstNameTextField.getText();
        String scSurName = lastNameTextField.getText();
        String scFaculty = facultyTextField.getText();
        String scEmailAddress = emailAddressTextField.getText();
        String scGender = genderTextField.getText();
        String scPhoneNo = phoneNumberTextField.getText();

        if (isEmpty()) {
            errorLabel.setText("Please fill in empty fields!");
            errorLabel.setTextFill(Color.RED);
        }
        else {
            if (containAccount(scUsername)) {
                errorLabel.setText("Username already exists!");
                errorLabel.setTextFill(Color.RED);
            }
            else {
                if (containAlphabets(scPhoneNo) || containCharacters(scPhoneNo)) {
                    errorLabel.setText("Phone number must only contain numbers!");
                    errorLabel.setTextFill(Color.RED);
                }
                else {
                    if (emailContains(scEmailAddress)) {
                        errorLabel.setText("Email address must contain a domain!");
                        errorLabel.setTextFill(Color.RED);
                    }
                    else {
                        if (scEmailAddress.contains(" ")) {
                            errorLabel.setText("Email address must not contain a space!");
                            errorLabel.setTextFill(Color.RED);
                        }
                        else {
                            if (scUsername.contains(" ")) {
                                errorLabel.setText("Username must not contain a space!");
                                errorLabel.setTextFill(Color.RED);
                            }
                            else {
                                if (!containCharacters(scPassword)) {
                                    errorLabel.setText("Password must contain at least 1 Special Character!");
                                    errorLabel.setTextFill(Color.RED);
                                }
                                else {
                                    if (!containNumerics(scPassword)) {
                                        errorLabel.setText("Password must contain at least 1 number!");
                                        errorLabel.setTextFill(Color.RED);
                                    }
                                    else {
                                        if (!confirmPassword.equals(scPassword)) {
                                            errorLabel.setText("Passwords must match!");
                                            errorLabel.setTextFill(Color.RED);
                                        }
                                        else {
                                            errorLabel.setText("Successfully created account!");
                                            errorLabel.setTextFill(Color.GREEN);

                                            createAccount(scID, scUsername, scPassword, scGivenName, scSurName, scFaculty, scEmailAddress, scGender, scPhoneNo);
                                        }
                                    }                        
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @FXML
    void returnToPrev(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/surveyCreator/surveyCreatorLoginPage.fxml"));
        Stage window;
        window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("Survey Creator Login Page");
        window.show();
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            scIdTextField.setText("SC" + String.valueOf(getSurveyCreatorId()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
