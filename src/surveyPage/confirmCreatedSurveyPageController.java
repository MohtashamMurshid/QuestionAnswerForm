package surveyPage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class confirmCreatedSurveyPageController{

    @FXML
    private Button confirmButton;

    @FXML
    private Button discardButton;

    @FXML
    private TextArea surveyDescriptionTextArea;

    @FXML
    private TextField surveyIdTextField;

    @FXML
    private TextField surveyTitleTextField;

    private String scID;

    public void setSCIdString(String scString) {
        this.scID = scString;
    }

    public String getSCIdString() {
        return this.scID;
    }

    @FXML
    void confirmNewSurvey(ActionEvent event) throws IOException {
        File surveyDetails = new File("src/resources/databases/surveyDetails.txt");
        FileWriter fw = new FileWriter(surveyDetails, true);
        BufferedWriter bw = new BufferedWriter(fw);

        surveyDetailsClass sDC = new surveyDetailsClass();
        sDC.setSurveyTitle(createNewSurveyController.getSurveyTitleString());
        sDC.setSurveyDescription(createNewSurveyController.getSurveyDescString());
        sDC.setSurveyCreatorId(getSCIdString());

        String surveyNumb = String.valueOf(getSurveyNumber());

        String surveyTitleString = "", surveyDescString = "";
        
        if (sDC.getSurveyTitle().contains(" ")) {
            surveyTitleString += sDC.getSurveyTitle().replace(" ", "_");
        }
        else {
            surveyTitleString += sDC.getSurveyTitle();
        }
        if (sDC.getSurveyDescription().contains(" ")) {
            surveyDescString += sDC.getSurveyDescription().replace(" ", "_");
        }
        else {
            surveyDescString += sDC.getSurveyDescription();
        }

        sDC.setSurveyNumber(surveyNumb);

        bw.write(sDC.getSurveyCreatorId() + " " + sDC.getSurveyNumber() + " " + surveyTitleString + " " + surveyDescString + "\n");

        bw.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task successfully completed!");
        alert.setHeaderText("Congratulations!");
        alert.setContentText("Survey successfully created!");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage newWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
            newWindow.setTitle("Add Question Page");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/survey/addOpenEndedQuestionPage.fxml"));
            newWindow.setScene(new Scene(loader.load()));

            addOpenEndedQuestionController aOEQC = loader.getController();
            aOEQC.setQuestionNumber();
            aOEQC.setQuestionNumberLabel();
            aOEQC.setQuestionLine(sDC.getSurveyNumber());
            aOEQC.setSurveyCreatorId(sDC.getSurveyCreatorId());
            aOEQC.setSurveyNumber(sDC.getSurveyNumber());
            aOEQC.setSurveyTitle(surveyTitleString);
            aOEQC.setSurveyDescription(surveyDescString);

            newWindow.show();
        }
    }

    @FXML
    void discardSurvey(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Pane");
        alert.setHeaderText("Warning!");
        alert.setContentText("Are you sure you want to discard everything you've written on this page?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage newWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
            newWindow.setTitle("Create New Survey Page");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/survey/createSurveyPage.fxml"));
            newWindow.setScene(new Scene(loader.load()));
            newWindow.show();
        }
        else if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }

    int getSurveyNumber() throws IOException {
        File surveyDetailsFile = new File("src/resources/databases/surveyDetails.txt");

        FileReader fr = new FileReader(surveyDetailsFile);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        
        int i = 0;
        while (line != null) {
            i++;
            line = br.readLine();
        }
        br.close();

        return i;
    }

    void setSurveyDetails(String surveyTitle, String surveyDescription) throws IOException {
        String surveyNumber = String.valueOf(getSurveyNumber());

        surveyIdTextField.setText(surveyNumber);
        surveyTitleTextField.setText(surveyTitle);
        surveyDescriptionTextArea.setText(surveyDescription);
    }

}
