package adminPage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class userCreatedSurveysController {
    
    @FXML
    private Button addButton;
    
    @FXML
    private Button returnButton;

    @FXML
    private ListView<String> commentsListView;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Button returnToPrevPage;

    @FXML
    private ComboBox<String> surveyComboBox;

    @FXML
    private Button viewSurveyButton;

    @FXML
    private Label errorLabel;

    private String scID;

    private String surveyCode;

    private String questionLine;

    private String questionType;

    private int counter;

    private String username;

    public void setUsername(String user) {
        this.username = user;
    }

    public String getUsername() {
        return this.username;
    }

    public void setCounter() {
        this.counter = 0;
    }

    public int getCounter() {
        return this.counter;
    }

    public void setQuestionType(String questionString) {
        int firstIndex = questionString.indexOf("(");
        int lastIndex = questionString.indexOf(")");
        this.questionType = questionString.substring(firstIndex, lastIndex + 1);
    } // Sets the question type of the next or previous question

    public String getQuestionType() {
        return this.questionType;
    }

    public void setQuestionLine(String surveyCode) throws IOException {
        File surveyQuestionsFile = new File("src/resources/databases/surveyQuestions.txt");

        FileReader fr = new FileReader(surveyQuestionsFile);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();

        while (line != null) {
            if (line.contains(getSurveyCode())) {
                this.questionLine = line.replace(getSurveyCode() + " ", "");
            }
            line = br.readLine();
        }
        br.close();
    } // Reads the specific line within the surveyQuestions database, and sets the question Line of the survey.

    public String getQuestionLine() {
        return this.questionLine;
    }

    public void setSurveyCode(String surveyNumber) {
        this.surveyCode = surveyNumber;
    }

    public String getSurveyCode() {
        return this.surveyCode;
    }

    public ArrayList<String> getQuestionList(String qLine) {
        ArrayList<String> questionList = new ArrayList<>();
        for (int i = 0; i < qLine.split(" ").length; i++) {
            questionList.add(qLine.split(" ")[i]);
        }
        return questionList;
    } // Takes the question line, and splits it. And adds each element into the questionList array list.

    public void setSCID(String user) throws IOException {
        File surveyCreatorAccountsFile = new File("src/resources/databases/surveyCreatorAccounts.txt");

        FileReader fr = new FileReader(surveyCreatorAccountsFile);
        BufferedReader br = new BufferedReader(fr); 
        String line = br.readLine();

        ArrayList<String> lineList = new ArrayList<>();
        Dictionary<String, String> accDict = new Hashtable<>();

        while (line != null) {
            lineList.add(line);
            line = br.readLine();
        }

        br.close();

        for (String acc : lineList) {
            accDict.put(acc.split(" ")[1], acc.split(" ")[0]);
        }

        this.scID = accDict.get(user);
    } // Get the survey creator as according to the username

    public String getSCID() {
        return this.scID;
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
    } // Gets all the details of the survey, and adds it into its own array list

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

        for (int i = 0; i < getUserSurveyList(getSCID()).size(); i++) {
            titleDescDict.put(getSurveyTitleList(getUserSurveyList(getSCID())).get(i), getSurveyDescriptionList(getUserSurveyList(getSCID())).get(i));
        }

        selectedDescription = titleDescDict.get(surveyTitle);

        return selectedDescription;
    }

    public String getSurveyNumberString(String surveyTitle) throws IOException {
        String selectedSCID;
        Dictionary<String, String> titleSCIDDict = new Hashtable<>();

        for (int i = 0; i < getUserSurveyList(getSCID()).size(); i++) {
            titleSCIDDict.put(getSurveyTitleList(getUserSurveyList(getSCID())).get(i), getSurveyNumberList(getUserSurveyList(getSCID())).get(i));
        }

        selectedSCID = titleSCIDDict.get(surveyTitle);

        return selectedSCID;
    }

    public void setComboBox() throws IOException {
        for (String title : getSurveyTitleList(getUserSurveyList(getSCID()))) {
            surveyComboBox.getItems().add(title);
        }
        surveyComboBox.setPromptText(getSurveyTitleList(getUserSurveyList(scID)).get(0));
        surveyComboBox.getSelectionModel().selectFirst();
        descriptionTextArea.setText(getSurveyDescriptionList(getUserSurveyList(scID)).get(0));
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
                if (c.contains(" ")) {
                    commentsListView.getItems().add(c.replace(" ", ""));
                }
                else {
                    if (c.contains(" ")) {
                        commentsListView.getItems().add(c.replace(" ", ""));
                    }
                    else {
                        commentsListView.getItems().add(c);
                    }
                }
            }
        }
    } // Sets all the comments added as according to the selected survey.

    @FXML
    void updateSurveyDetails(ActionEvent event) throws IOException {
        descriptionTextArea.setText(getSurveyDescriptionString(surveyComboBox.getSelectionModel().getSelectedItem()));
        commentsListView.getItems().clear();
        setCommentsListView();
    } // Updates the description and comments as according to the selected item in the combo box.

    @FXML
    void viewSurvey(ActionEvent event) throws IOException {
        String surveyTitle = "";
        surveyTitle = surveyComboBox.getSelectionModel().getSelectedItem();
        setSurveyCode(getSurveyNumberString(surveyTitle));
        setQuestionLine(getSurveyCode());
        setQuestionType(getQuestionList(getQuestionLine()).get(0));

        if (getQuestionType().equals("(OEQ)")) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Survey");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/adminPage/adminViewOpenEndedQuestionPage.fxml"));
            window.setScene(new Scene(loader.load()));

            adminViewOpenEndedQuestionController aVOEQC = loader.getController();
            aVOEQC.setQuestionType("(OEQ)");
            aVOEQC.setSurveyCode(getSurveyCode());
            aVOEQC.setQuestionList(getQuestionList(getQuestionLine()));
            aVOEQC.setCounter(1);
            aVOEQC.setQuestionNumber();
            aVOEQC.setQuestionNumberLabel();
            aVOEQC.setQuestionLabel(aVOEQC.getQuestionNumber());
            aVOEQC.setNoOfQuestions(getQuestionList(getQuestionLine()).size());
            aVOEQC.disablePreviousButton();

            if (aVOEQC.getQuestionNumber() == aVOEQC.getNoOfQuestions()) {
                aVOEQC.disableNextButton();
            }
        }
        
        else if (getQuestionType().equals(("(TFQ)"))) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Survey");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/adminPage/adminViewTrueFalseQuestionPage.fxml"));
            window.setScene(new Scene(loader.load()));

            adminViewTrueFalseQuestionController aVTFQC = loader.getController();
            aVTFQC.setQuestionType("(TFQ)");
            aVTFQC.setSurveyCode(getSurveyCode());
            aVTFQC.setQuestionList(getQuestionList(getQuestionLine()));
            aVTFQC.setCounter(1);
            aVTFQC.setQuestionNumber();
            aVTFQC.setQuestionNumberLabel();
            aVTFQC.setQuestionLabel(aVTFQC.getQuestionNumber());
            aVTFQC.setNoOfQuestions(getQuestionList(getQuestionLine()).size());
            aVTFQC.disablePreviousButton();

            if (aVTFQC.getQuestionNumber() == aVTFQC.getNoOfQuestions()) {
                aVTFQC.disableNextButton();
            }
        }

        else if (getQuestionType().equals("(MCQ)")) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Survey");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/adminPage/adminViewMultipleChoiceQuestionPage.fxml"));
            window.setScene(new Scene(loader.load()));

            adminViewMultipleChoiceQuestionController aVMCQC = loader.getController();
            aVMCQC.setQuestionType(("(MCQ)"));
            aVMCQC.setSurveyCode(getSurveyCode());
            aVMCQC.setQuestionList(getQuestionList(getQuestionLine()));
            aVMCQC.setCounter(1);
            aVMCQC.setQuestionNumber();
            aVMCQC.setQuestionNumberLabel();
            aVMCQC.setQuestionLabel(aVMCQC.getQuestionNumber());
            aVMCQC.setNoOfQuestions(getQuestionList(getQuestionLine()).size());
            aVMCQC.disablePreviousButton();
            aVMCQC.setAnswerLabels(aVMCQC.getMCQAnswers(getQuestionList(getQuestionLine()).get(0)));

            if (aVMCQC.getQuestionNumber() == aVMCQC.getNoOfQuestions()) {
                aVMCQC.disableNextButton();
            }

        }
    }

    @FXML
    void returnToPrevPage(ActionEvent event) {
        Stage window = (Stage) returnButton.getScene().getWindow();
        window.close();
    } // Closes the current window.

    @FXML
    void addComment(ActionEvent event) throws IOException {
        String surveyTitle = "";
        surveyTitle = surveyComboBox.getSelectionModel().getSelectedItem();

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Survey");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/comments/addCommentPage.fxml"));
        window.setScene(new Scene(loader.load()));

        comments.addCommentController aCC = loader.getController();
        aCC.setSurveyCode(getSurveyNumberString(surveyTitle));
        aCC.setUsername(getUsername());
    }
    
    @FXML
    void deleteComment(ActionEvent event) throws IOException {
        File inFile = new File("src/resources/databases/surveyComments.txt");

        if (!inFile.isFile()) {
            System.out.println("Parameter is not an existing file");
            return;
        }

        File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

        BufferedReader br = new BufferedReader(new FileReader("src/resources/databases/surveyComments.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
        String line = br.readLine();

        String surveyTitle = surveyComboBox.getSelectionModel().getSelectedItem();

        String surveyNumber = getSurveyNumberString(surveyTitle);

        String comment = commentsListView.getSelectionModel().getSelectedItem();

        String lineToRemove = surveyNumber + " " + comment;

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

        commentsListView.getItems().clear();
        setCommentsListView();
    } // Deletes the selected comment.
    
    @FXML
    void deleteSurvey(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText("Warning!");
        alert.setContentText("Do you really want to delete your survey? Once you delete it, you can't get it back");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            try {

                int index = surveyComboBox.getSelectionModel().getSelectedIndex();

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

                String lineToRemove = getSCID() + " " + getSurveyNumberString(surveyTitle) + " " + survTitle + " " + survDesc;

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

                surveyComboBox.setPromptText(getSurveyTitleList(getUserSurveyList(scID)).get(index - 1));
                descriptionTextArea.setText(getSurveyDescriptionString(getSurveyTitleList(getUserSurveyList(scID)).get(index - 1)));
                surveyComboBox.getItems().remove(index);

            }
            catch (Exception e) {
                errorLabel.setText("Unable to delete survey when there is only one left!");
                errorLabel.setTextFill(Color.RED);
            }
        }
        else {
            alert.close();
        }
    }// Deletes the selected survey
}