package surveyCreatorPage;

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
import javafx.stage.Stage;

public class surveyCreatorLoginPageController implements Initializable{

    @FXML
    private Button createButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Button exitButton;

    @FXML
    private Button returnButton;

    @FXML
    private PasswordField scPasswordTextField;

    @FXML
    private TextField scUsernameTextField;

    @FXML
    private Button surveyCreatorLoginButton;

    boolean containAccount(String username) throws IOException {
        boolean result = false;

        File surveyCreatorAccountsFile = new File("src/resources/databases/surveyCreatorAccounts.txt");

        FileReader fr = new FileReader(surveyCreatorAccountsFile);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();

        ArrayList<String> usernameList = new ArrayList<>();
        ArrayList<String> lineList = new ArrayList<>();

        while (line != null) {
            lineList.add(line);
            line = br.readLine();
        }

        br.close();

        for (String user : lineList) {
            usernameList.add(user.split(" ")[1]);
        }

        if (usernameList.contains(username)) {
            result = true;
        }

        return result;
    }

    boolean checkPassword(String username, String password) throws IOException {
        boolean result = false;

        File surveyCreatorAccountsFile = new File("src/resources/databases/surveyCreatorAccounts.txt");

        FileReader fr = new FileReader(surveyCreatorAccountsFile);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();

        Dictionary<String, String> accountsDict = new Hashtable<>();
        ArrayList<String> lineList = new ArrayList<>();

        while (line != null) {
            lineList.add(line);
            line = br.readLine();
        }

        br.close();

        for (String acc : lineList) {
            accountsDict.put(acc.split(" ")[1], acc.split(" ")[2]);
        }

        if (accountsDict.get(username).equals(password)) {
            result = true;
        }

        return result;
    }

    boolean isEmpty() {
        boolean result = false;
        if (scUsernameTextField.getText().isEmpty() || scPasswordTextField.getText().isEmpty()) {
            result = true;
        }
        return result;
    }

    String getSCID(String username) throws IOException {
        String scID;

        File surveyCreatorAccountsFile = new File("src/resources/databases/surveyCreatorAccounts.txt");

        FileReader fr = new FileReader(surveyCreatorAccountsFile);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();

        ArrayList<String> lineList = new ArrayList<>();
        Dictionary<String, String> accList = new Hashtable<>();

        while (line != null) {
            lineList.add(line);
            line = br.readLine();
        }

        br.close();

        for (String acc : lineList) {
            accList.put(acc.split(" ")[1], acc.split(" ")[0]);
        }

        scID = accList.get(username);
        
        return scID;
    }

    @FXML
    void exitProgram(ActionEvent event) {
        Platform.exit();
    } // Shuts down the program

    @FXML
    void registerPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/surveyCreator/createSurveyCreatorAccountPage.fxml"));
        Stage window;
        window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("Main Page");
        window.show();

    }

    @FXML
    void returnToPrev(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/mainPage.fxml"));
        Stage window;
        window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("Main Page");
        window.show();
    }

    @FXML
    void surveyCreatorPage(ActionEvent event) throws IOException {
        String username = scUsernameTextField.getText();
        String password = scPasswordTextField.getText();

        if (isEmpty()) {
            errorLabel.setText("Please fill in all fields!");
        }
        else {
            if (containAccount(username)) {
                if (checkPassword(username, password)) {

                    Stage newWindow = (Stage)((Node)event.getSource()).getScene().getWindow();;
                    newWindow.setTitle("Survey Creator Page");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/surveyCreator/surveyCreatorPage.fxml"));
                    newWindow.setScene(new Scene(loader.load()));

                    surveyCreatorPageController sCPC = loader.getController();
                    sCPC.setScUsername(username);
                    sCPC.setSCIdString(username);

                }
                else {
                    errorLabel.setText("Wrong password!");
                }
            }
            else {
                errorLabel.setText("Account does not exist!");
            }
        }
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File scAccountsFile = new File("src/resources/databases/surveyCreatorAccounts.txt");
        File charactersFile = new File("src/resources/databases/characters.txt");
        if (!scAccountsFile.exists() || !charactersFile.exists()) {
            errorLabel.setText("Missing files! Please reinstall the program!");
        }
    }

}


