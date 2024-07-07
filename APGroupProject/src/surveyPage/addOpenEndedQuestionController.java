package surveyPage;

import java.util.Optional;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addOpenEndedQuestionController {

    @FXML
    private Button addButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button completeButton;

    @FXML
    private Button discardButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Button mcqButton;

    @FXML
    private Button openEndedButton;

    @FXML
    private Label questionLabel;

    @FXML
    private TextField questionTextField;

    @FXML
    private Button trueFalseButton;

    private int questionNumber;

    private String questionLine;

    private String surveyNumber;

    private String surveyCreatorId;

    private String surveyTitle;

    private String surveyDescription;

    public void setSurveyCreatorId(String scID) {
        this.surveyCreatorId = scID;
    }

    public String getSurveyCreatorId() {
        return this.surveyCreatorId;
    }

    public void setSurveyNumber(String surveyNumber) {
        this.surveyNumber = surveyNumber;
    }

    public String getSurveyNumber() {
        return this.surveyNumber;
    }

    public void setSurveyTitle(String survTitle) {
        this.surveyTitle = survTitle;
    }

    public String getSurveyTitle() {
        return this.surveyTitle;
    }

    public void setSurveyDescription(String survDesc) {
        this.surveyDescription = survDesc;
    }

    public String getSurveyDescription() {
        return this.surveyDescription;
    }

    public void setQuestionNumber() {
        this.questionNumber = 1;
    }

    public void setNewQuestionNumber(int qNumb) {
        this.questionNumber = qNumb;
    }

    public int getQuestionNumber() {
        return this.questionNumber;
    }

    public void updateQuestionNumber() {
        this.questionNumber++;
    }

    public void revertQuestionNumber() {
        this.questionNumber--;
    }

    public String getQuestionType() {
        return "(OEQ)";
    }

    public void setQuestionLine(String surveyNumber) {
        this.questionLine = surveyNumber;
    }

    public void updateQuestionLine(String question) {
        char lastChar = this.questionLine.charAt(questionLine.length() - 1);
        if (lastChar == ' ') {
            this.questionLine += getQuestionType() + question;
        }
        else {
            this.questionLine += " " + getQuestionType() + question;
        }
    }

    public String getQuestionLine() {
        return this.questionLine;
    }

    public void revertQuestionLine() {
        ArrayList<String> questLine = new ArrayList<>();

        for (String q : this.questionLine.split(" ")) {
            questLine.add(q);
        }
        questLine.remove(questLine.size() - 1);
        this.questionLine = "";

        for (String a : questLine) {
            this.questionLine += a + " ";
        }
    }

    public void setQuestionNumberLabel() {
        questionLabel.setText(String.valueOf(getQuestionNumber()));
    }

    public void updateQuestionNumberLabel(int questionNumber) {
        questionLabel.setText(String.valueOf(questionNumber));
    }

    public void clearAllFields() {
        questionTextField.setText("");
    }

    @FXML
    void addQuestion(ActionEvent event) {
        if (questionTextField.getText().isEmpty()) {
            errorLabel.setText("Please fill in all empty fields!");
        }
        else {
            if (questionTextField.getText().contains(" ")) {
                updateQuestionLine(questionTextField.getText().replace(" ", "_"));
            }
            else {
                updateQuestionLine(questionTextField.getText());
            }
            updateQuestionNumber();
            updateQuestionNumberLabel(getQuestionNumber());
            clearAllFields();
        }
    }

    @FXML
    void changeToMCQ(ActionEvent event) throws IOException {
        Stage newWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
        newWindow.setTitle("Add Multiple Choice Question");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/survey/addMultipleChoiceQuestionPage.fxml"));
        newWindow.setScene(new Scene(loader.load()));

        addMultipleChoiceQuestionController aMCQC = loader.getController();
        aMCQC.setQuestionLine(getQuestionLine());
        aMCQC.setQuestionNumber(getQuestionNumber());
        aMCQC.setQuestionNumberLabel();
        aMCQC.setSurveyCreatorId(getSurveyCreatorId());
        aMCQC.setSurveyNumber(getSurveyNumber());
        aMCQC.setSurveyTitle(getSurveyTitle());
        aMCQC.setSurveyDescription(getSurveyDescription());

        newWindow.show();
    }

    @FXML
    void changeToTrueFalse(ActionEvent event) throws IOException {
        Stage newWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
        newWindow.setTitle("Add True/False Question");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/survey/addTrueFalseQuestionPage.fxml"));
        newWindow.setScene(new Scene(loader.load()));

        addTrueFalseQuestionController aTFQC = loader.getController();
        aTFQC.setQuestionLine(getQuestionLine());
        aTFQC.setQuestionNumber(getQuestionNumber());
        aTFQC.setQuestionNumberLabel();
        aTFQC.setSurveyCreatorId(getSurveyCreatorId());
        aTFQC.setSurveyNumber(getSurveyNumber());
        aTFQC.setSurveyTitle(getSurveyTitle());
        aTFQC.setSurveyDescription(getSurveyDescription());

        newWindow.show();
    }

    @FXML
    void clearAllFields(ActionEvent event) {
        questionTextField.setText("");
    }

    @FXML
    void completeSurvey(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Pane");
        alert.setHeaderText("Warning!");
        alert.setContentText("Are you sure you want to complete the survey? Once you submit, you cannot edit the survey!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            File surveyQuestionsFile = new File("src/resources/databases/surveyQuestions.txt");

            FileWriter fw = new FileWriter(surveyQuestionsFile, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(getQuestionLine() + "\n");
            bw.close();

            Stage newWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
            newWindow.setTitle("Survey Creator Page");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/surveyCreator/surveyCreatorPage.fxml"));
            newWindow.setScene(new Scene(loader.load()));

            surveyCreatorPage.surveyCreatorPageController sCPC = loader.getController();
            sCPC.setSCID(getSurveyCreatorId());

            newWindow.show();
        }
        else if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }

    @FXML
    void returnToPrev(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Pane");
        alert.setHeaderText("Warning!");
        alert.setContentText("Are you sure you want to discard everything you've written on this page?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            clearAllFields();
            revertQuestionNumber();
            updateQuestionNumberLabel(getQuestionNumber());
            revertQuestionLine();

            if (getQuestionNumber() == 0) {
                Stage newWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
                newWindow.setTitle("Survey Creator Page");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/surveyCreator/surveyCreatorPage.fxml"));
                newWindow.setScene(new Scene(loader.load()));
                
                File inFile = new File("src/resources/databases/surveyDetails.txt");

                if (!inFile.isFile()) {
                    System.out.println("Parameter is not an existing file");
                    return;
                }

                File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

                BufferedReader br = new BufferedReader(new FileReader("src/resources/databases/surveyDetails.txt"));
                PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
                String line = br.readLine();

                String lineToRemove = getSurveyCreatorId() + " " + getSurveyNumber() + " " + getSurveyTitle() + " " + getSurveyDescription();

                while (line != null) {
                    if (!line.trim().equals(lineToRemove)) {
                        pw.println(line);
                        pw.flush();
                    }
                    line = br.readLine();
                }
                pw.close();
                br.close();

                if (!inFile.delete()) {
                    System.out.println("Could not delete file");
                    return;
                }
            
                //Rename the new file to the filename the original file had.
                if (!tempFile.renameTo(inFile)) {
                    System.out.println("Could not rename file");
                }

                surveyCreatorPage.surveyCreatorPageController sCPC = loader.getController();
                sCPC.setSCID(getSurveyCreatorId());

                newWindow.show();
            }
        }
        else if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }
}