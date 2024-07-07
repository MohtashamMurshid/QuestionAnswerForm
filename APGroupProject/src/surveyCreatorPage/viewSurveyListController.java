package surveyCreatorPage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class viewSurveyListController {

    @FXML
    private ListView<String> commentsListView;

    @FXML
    private Button deleteSurveyButton;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Label errorLabel;

    @FXML
    private Button returnButton;

    @FXML
    private Button shareButton;

    @FXML
    private ComboBox<String> surveyComboBox;

    @FXML
    private Button viewSurveyButton;

    private String surveyCreatorID;

    private String questionType;

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
    
    public void setSurveyCreatorID(String scID) {
        this.surveyCreatorID = scID;
    }

    public String getSurveyCreatorID() {
        return this.surveyCreatorID;
    }

    public ArrayList<String> getUserSurveyList(String scID) throws IOException {
        File surveyDetailsFile = new File("src/resources/databases/surveyDetails.txt");

        FileReader fr = new FileReader(surveyDetailsFile);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();

        ArrayList<String> lineList = new ArrayList<>();
        ArrayList<String> surveyList = new ArrayList<>();

        while (line != null) {
            lineList.add(line);
            line = br.readLine();
        }

        br.close();

        for (String survey : lineList) {
            if (survey.contains(scID)) {
                surveyList.add(survey.replace(scID + " ", ""));
            }
        }
        return surveyList;
    }

    public ArrayList<String> getSurveyNumberList(ArrayList<String> userSurveyList) {
        ArrayList<String> surveyNumberList = new ArrayList<>();

        for (String surv : userSurveyList) {
            surveyNumberList.add(surv.split(" ")[0]);
        }

        return surveyNumberList;
    }
    
    public ArrayList<String> getSurveyTitleList(ArrayList<String> userSurveyList) {
        ArrayList<String> surveyTitleList = new ArrayList<>();

        for (String title : userSurveyList) {
            if (title.split(" ")[1].contains("_")) {
                surveyTitleList.add(title.split(" ")[1].replace("_", " "));
            }
            else {
                surveyTitleList.add(title.split(" ")[1]);
            }
        }

        return surveyTitleList;
    }

    public ArrayList<String> getSurveyDescriptionList(ArrayList<String> userSurveyList) {
        ArrayList<String> surveyDescriptionList = new ArrayList<>();

        for (String desc : userSurveyList) {
            if (desc.split(" ")[2].contains("_")) {
                surveyDescriptionList.add(desc.split(" ")[2].replace("_", " "));
            }
            else {
                surveyDescriptionList.add(desc.split(" ")[2]);
            }
        }
        return surveyDescriptionList;
    }

    public String getSurveyDescriptionString(String surveyTitle) throws IOException {
        String selectedDescription;

        Dictionary<String, String> titleDescDict = new Hashtable<>();

        for (int i = 0; i < getUserSurveyList(getSurveyCreatorID()).size(); i++) {
            titleDescDict.put(getSurveyTitleList(getUserSurveyList(getSurveyCreatorID())).get(i), getSurveyDescriptionList(getUserSurveyList(getSurveyCreatorID())).get(i));
        }

        selectedDescription = titleDescDict.get(surveyTitle);

        return selectedDescription;
    }

    public String getSurveyNumberString(String surveyTitle) throws IOException {
        String selectedSCID;
        Dictionary<String, String> titleSCIDDict = new Hashtable<>();

        for (int i = 0; i < getUserSurveyList(getSurveyCreatorID()).size(); i++) {
            titleSCIDDict.put(getSurveyTitleList(getUserSurveyList(getSurveyCreatorID())).get(i), getSurveyNumberList(getUserSurveyList(getSurveyCreatorID())).get(i));
        }

        selectedSCID = titleSCIDDict.get(surveyTitle);

        return selectedSCID;
    }

    public void setComboBox() throws IOException {
        for (String title : getSurveyTitleList(getUserSurveyList(getSurveyCreatorID()))) {
            surveyComboBox.getItems().add(title);
        }

        surveyComboBox.setPromptText(getSurveyTitleList(getUserSurveyList(getSurveyCreatorID())).get(0));
        surveyComboBox.getSelectionModel().selectFirst();
        descriptionTextArea.setText(getSurveyDescriptionList(getUserSurveyList(getSurveyCreatorID())).get(0));
    }

    public void setCommentsListView() throws IOException {
        ArrayList<String> commentsList = new ArrayList<>();
        String surveyTitle = surveyComboBox.getSelectionModel().getSelectedItem();
        String surveyCode = getSurveyNumberString(surveyTitle);
        
        File surveyCommentsFile = new File("src/resources/databases/surveyComments.txt");

        FileReader fr = new FileReader(surveyCommentsFile);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();

        while (line != null) {
            if (line.contains(surveyCode + " ")) {
                commentsList.add(line.replace(surveyCode + " ", ""));
            }
            line = br.readLine();
        }
        br.close();

        if (commentsList.isEmpty()) {
            commentsListView.getItems().add("No comments yet!");
        }
        else {
            for (String c : commentsList) {
                commentsListView.getItems().add(c);
            }
        }
    }

    public ArrayList<String> getSurveyQuestionList(String surveyTitle) throws IOException {
        ArrayList<String> lineList = new ArrayList<>();
        ArrayList<String> questionList = new ArrayList<>();
        File surveyQuestionsFile = new File("src/resources/databases/surveyQuestions.txt");

        FileReader fr = new FileReader(surveyQuestionsFile);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();

        while (line != null) {
            if (line.contains(getSurveyNumberString(surveyTitle) + " ")) {
                lineList.add(line.replace(getSurveyNumberString(surveyTitle) + " ", ""));
            }
            line = br.readLine();
        }
        br.close();

        for (int i = 0; i < lineList.get(0).split(" ").length; i++) {
            questionList.add(lineList.get(0).split(" ")[i]);
        }

        return questionList;
    }

    public void setQuestionType(String questionString) {
        int firstIndex = questionString.indexOf("(");
        int lastIndex = questionString.indexOf(")");
        this.questionType = questionString.substring(firstIndex, lastIndex + 1);
    }

    public String getQuestionType() {
        return this.questionType;
    }

    @FXML
    void deleteSurvey(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText("Warning!");
        alert.setContentText("Do you really want to delete your survey? Once you delete it, you can't get it back");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            int index = surveyComboBox.getSelectionModel().getSelectedIndex();

            if (surveyComboBox.getItems().size() == 1) {
                errorLabel.setText("Unable to delete! Please make another survey before being able to delete!");
            }
            else {

                File inFile = new File("src/resources/databases/surveyDetails.txt");

                if (!inFile.isFile()) {
                    System.out.println("Parameter is not an existing file");
                    return;
                }

                File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

                BufferedReader br = new BufferedReader(new FileReader("src/resources/databases/surveyDetails.txt"));
                PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
                String line = br.readLine();

                String surveyTitle = "";
                surveyTitle = surveyComboBox.getSelectionModel().getSelectedItem();

                String survTitle, survDesc;

                if (surveyTitle.contains(" ")) {
                    survTitle = surveyTitle.replace(" ", "_");
                }
                else {
                    survTitle = surveyTitle;
                }

                if (getSurveyDescriptionString(surveyTitle).contains(" ")) {
                    survDesc = getSurveyDescriptionString(surveyTitle).replace(" ", "_");
                }
                else {
                    survDesc = getSurveyDescriptionString(surveyTitle);
                }

                String lineToRemove = getSurveyCreatorID() + " " + getSurveyNumberString(surveyTitle) + " " + survTitle + " " + survDesc;

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

                errorLabel.setText("Successfully deleted survey!");
                errorLabel.setTextFill(Color.GREEN);

                if (index == 0) {
                    surveyComboBox.setPromptText(getSurveyTitleList(getUserSurveyList(getSurveyCreatorID())).get(index + 1));
                    descriptionTextArea.setText(getSurveyDescriptionString(getSurveyTitleList(getUserSurveyList(getSurveyCreatorID())).get(index + 1)));
                    surveyComboBox.getItems().remove(index);
                }
                else {
                    surveyComboBox.setPromptText(getSurveyTitleList(getUserSurveyList(getSurveyCreatorID())).get(index - 1));
                    descriptionTextArea.setText(getSurveyDescriptionString(getSurveyTitleList(getUserSurveyList(getSurveyCreatorID())).get(index - 1)));
                    surveyComboBox.getItems().remove(index);
                }
            }
        }
        else {
            alert.close();
        }

    }

    @FXML
    void returnToPrevPage(ActionEvent event) {
        Stage window = (Stage) returnButton.getScene().getWindow();
        window.close();
    }

    @FXML
    void shareSurveyCode(ActionEvent event) throws IOException {
        Stage newWindow = new Stage();
        newWindow.setTitle("Share Survey Code Page");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/surveyCreator/shareSurveyCodePage.fxml"));
        newWindow.setScene(new Scene(loader.load()));

        shareSurveyCodeController sSCC = loader.getController();
        sSCC.setSurveyCode(getSurveyNumberString(surveyComboBox.getSelectionModel().getSelectedItem()));
        sSCC.setSurveyCodeTextField();

        newWindow.show();
    }

    @FXML
    void updateSurveyDetails(ActionEvent event) throws IOException {
        descriptionTextArea.setText(getSurveyDescriptionString(surveyComboBox.getSelectionModel().getSelectedItem()));
        commentsListView.getItems().clear();
        setCommentsListView();
    }

    @FXML
    void viewSurvey(ActionEvent event) throws IOException {
        String surveyTitle = surveyComboBox.getSelectionModel().getSelectedItem();
        String surveyNumber = getSurveyNumberString(surveyTitle);
        setQuestionType(getSurveyQuestionList(surveyTitle).get(0));

        if (getQuestionType().equals("(OEQ)")) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Survey");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/surveyCreator/surveyCreatorViewOpenEndedQuestionPage.fxml"));
            window.setScene(new Scene(loader.load()));

            surveyCreatorViewOpenEndedQuestionController sCVOEQC = loader.getController();
            sCVOEQC.setQuestionType("(OEQ)");
            sCVOEQC.setSurveyCode(surveyNumber);
            sCVOEQC.setQuestionList(getSurveyQuestionList(surveyTitle));
            sCVOEQC.setCounter(1);
            sCVOEQC.setQuestionNumber();
            sCVOEQC.setQuestionNumberLabel();
            sCVOEQC.setQuestionLabel(sCVOEQC.getQuestionNumber());
            sCVOEQC.setNoOfQuestions(getSurveyQuestionList(surveyTitle).size());
            sCVOEQC.disablePreviousButton();
            sCVOEQC.setUsername(getUsername(getSurveyCreatorID()));
            sCVOEQC.setSurveyCreatorID(getSurveyCreatorID());

            if (sCVOEQC.getQuestionNumber() == sCVOEQC.getNoOfQuestions()) {
                sCVOEQC.disableNextButton();
            }

        }
        else if (getQuestionType().equals("(TFQ)")) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Survey");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/surveyCreator/surveyCreatorViewTrueFalseQuestionPage.fxml"));
            window.setScene(new Scene(loader.load()));

            surveyCreatorViewTrueFalseQuestionController sCVTFQC = loader.getController();
            sCVTFQC.setQuestionType("(TFQ)");
            sCVTFQC.setSurveyCode(surveyNumber);
            sCVTFQC.setQuestionList(getSurveyQuestionList(surveyTitle));
            sCVTFQC.setCounter(1);
            sCVTFQC.setQuestionNumber();
            sCVTFQC.setQuestionNumberLabel();
            sCVTFQC.setQuestionLabel(sCVTFQC.getQuestionNumber());
            sCVTFQC.setNoOfQuestions(getSurveyQuestionList(surveyTitle).size());
            sCVTFQC.disablePreviousButton();
            sCVTFQC.setUsername(getUsername(getSurveyCreatorID()));
            sCVTFQC.setSurveyCreatorID(getSurveyCreatorID());

            if (sCVTFQC.getQuestionNumber() == sCVTFQC.getNoOfQuestions()) {
                sCVTFQC.disableNextButton();
            }

        }
        else if (getQuestionType().equals("(MCQ)")) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Survey");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/surveyCreator/surveyCreatorViewMultipleChoiceQuestionPage.fxml"));
            window.setScene(new Scene(loader.load()));

            surveyCreatorViewMultipleChoiceQuestionController sCVMCQC = loader.getController();
            sCVMCQC.setQuestionType("(MCQ)");
            sCVMCQC.setSurveyCode(surveyNumber);
            sCVMCQC.setQuestionList(getSurveyQuestionList(surveyTitle));
            sCVMCQC.setCounter(1);
            sCVMCQC.setQuestionNumber();
            sCVMCQC.setQuestionNumberLabel();
            sCVMCQC.setQuestionLabel(sCVMCQC.getQuestionNumber());
            sCVMCQC.setNoOfQuestions(getSurveyQuestionList(surveyTitle).size());
            sCVMCQC.disablePreviousButton();
            sCVMCQC.setUsername(getUsername(getSurveyCreatorID()));
            sCVMCQC.setAnswerLabels(sCVMCQC.getMCQAnswers(getSurveyQuestionList(surveyTitle).get(0)));
            sCVMCQC.setSurveyCreatorID(getSurveyCreatorID());

            if (sCVMCQC.getQuestionNumber() == sCVMCQC.getNoOfQuestions()) {
                sCVMCQC.disableNextButton();
            }

        }
    }

}
