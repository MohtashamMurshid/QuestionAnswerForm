package surveyPage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import surveyCreatorPage.surveyCreatorPageController;

public class createNewSurveyController implements Initializable{

    @FXML
    private Button clearButton;

    @FXML
    private Button createSurveyButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Button returnButton;

    @FXML
    private TextArea surveyDescriptionTextArea;

    @FXML
    private TextField surveyTitleTextfield;

    private static String surveyTitleString;

    private static String surveyDescString;

    private String scID;

    public String getUsername(String scID) throws IOException {
        File surveyCreatorAccountsFile = new File("src/resources/databases/surveyCreatorAccounts.txt");

        FileReader fr = new FileReader(surveyCreatorAccountsFile);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();

        String username = "";

        while (line != null) {
            if (line.contains(scID + " ")) {
                username = line.split(" ")[1];
            }
            line = br.readLine();
        }
        br.close();

        return username;
    }

    public void setSCIdString(String scString) {
        this.scID = scString;
    }

    public String getSCIdString() {
        return this.scID;
    }

    public static String getSurveyTitleString() {
        return surveyTitleString;
    }

    public static String getSurveyDescString() {
        return surveyDescString;
    }

    @FXML
    void clearAllFields(ActionEvent event) {
        surveyTitleTextfield.setText("");
        surveyDescriptionTextArea.setText("");
    }

    @FXML
    void createNewSurvey(ActionEvent event) throws IOException {

        Stage newWindow = (Stage)((Node)event.getSource()).getScene().getWindow();;
        newWindow.setTitle("Confirm Create New Survey Page");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/survey/confirmCreatedSurveyPage.fxml"));
        newWindow.setScene(new Scene(loader.load()));

        confirmCreatedSurveyPageController cCSPC = loader.getController();
        cCSPC.setSurveyDetails(surveyTitleTextfield.getText(), surveyDescriptionTextArea.getText());
        cCSPC.setSCIdString(getSCIdString());

        surveyTitleString = surveyTitleTextfield.getText();
        surveyDescString = surveyDescriptionTextArea.getText();
        
        newWindow.show();
    }

    @FXML
    void returnToPrev(ActionEvent event) throws IOException {
        Stage newWindow = (Stage)((Node)event.getSource()).getScene().getWindow();;
        newWindow.setTitle("Survey Creator Page");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/surveyCreator/surveyCreatorPage.fxml"));
        newWindow.setScene(new Scene(loader.load()));

        surveyCreatorPageController sCPC = loader.getController();
        sCPC.setScUsername(getUsername(this.scID));
        sCPC.setSCIdString(getUsername(this.scID));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File surveyDetailsFile = new File("src/resources/databases/surveyDetails.txt");
        if (!surveyDetailsFile.exists()) {
            errorLabel.setText("Missing files! Please reinstall the program!");
        }
    }

}
