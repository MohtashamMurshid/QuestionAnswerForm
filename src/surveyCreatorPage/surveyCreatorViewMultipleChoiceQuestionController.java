package surveyCreatorPage;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class surveyCreatorViewMultipleChoiceQuestionController {

    @FXML
    private Label answerALabel;

    @FXML
    private RadioButton answerARadioButton;

    @FXML
    private Label answerBLabel;

    @FXML
    private RadioButton answerBRadioButton;

    @FXML
    private Label answerCLabel;

    @FXML
    private RadioButton answerCRadioButton;

    @FXML
    private Label answerDLabel;

    @FXML
    private RadioButton answerDRadioButton;

    @FXML
    private ToggleGroup answerGroup;

    @FXML
    private Label errorLabel;

    @FXML
    private Button nextButton;

    @FXML
    private Button previousButton;

    @FXML
    private Label questionLabel;

    @FXML
    private Label questionNumberLabel;

    @FXML
    private Button returnButton;

    private String surveyCode;

    private int questionNumber;

    private String questionType;

    private int noOfQuestions;

    private int counter;

    private ArrayList<String> questionList;

    private String surveyCreatorID;

    private String username;

    public void disablePreviousButton() {
        this.previousButton.setDisable(true);
    }

    public void enablePreviousButton() {
        this.previousButton.setDisable(false);
    }

    public void setCounter(int n) {
        this.counter = n;
    }

    public int getCounter() {
        return this.counter;
    }

    public void setQuestionList(ArrayList<String> qList) {
        this.questionList = qList;
    }

    public ArrayList<String> getQuestionList() {
        return this.questionList;
    }

    public int index() {
        return this.questionNumber - 1;
    }

    public void setQuestionNumber() {
        this.questionNumber = getCounter();
    }

    public void updateQuestionNumber() {
        this.questionNumber++;
    }

    public void revertQuestionNumber() {
        this.questionNumber--;
    }

    public int getQuestionNumber() {
        return this.questionNumber;
    }

    public void setQuestionType(String questionString) {
        int firstIndex = questionString.indexOf("(");
        int lastIndex = questionString.indexOf(")");
        this.questionType = questionString.substring(firstIndex, lastIndex + 1);
    }

    public String getQuestionType() {
        return this.questionType;
    }

    public void setNoOfQuestions(int qSize) {
        this.noOfQuestions = qSize;
    }

    public int getNoOfQuestions() {
        return this.noOfQuestions;
    }

    public void setQuestionNumberLabel() {
        questionNumberLabel.setText(String.valueOf(getQuestionNumber()));
    }

    public void setQuestionLabel(int questionNumber) {
        int index = questionNumber - 1;
        String question = "";
        if (this.questionList.get(index).contains("(MCQ)")) {
            question = this.questionList.get(index).split(",")[0];
            if (question.contains("(MCQ)")) {
                question = question.replace("(MCQ)", "");
                if (question.contains("_")) {
                    question = question.replace("_", " ");
                }
            }
        }
        this.questionLabel.setText(question);
    }

    public void disableNextButton() {
        this.nextButton.setDisable(true);
    }

    public void enableNextButton() {
        this.nextButton.setDisable(false);
    }

    public void setSurveyCode(String surveyNumber) {
        this.surveyCode = surveyNumber;
    }

    public String getSurveyCode() {
        return this.surveyCode;
    }

    public String getMCQAnswers(String qListLine) {
        String mcqAnswerLine = "";
        for (int i = 1; i < qListLine.split(",").length; i++) {
            mcqAnswerLine += qListLine.split(",")[i] + " ";
        }
        return mcqAnswerLine;
    }

    public void setAnswerLabels(String mcqAnsLine) {
        if (mcqAnsLine.split(" ")[0].contains("_")) {
            answerALabel.setText(mcqAnsLine.split(" ")[0].replace("_", " "));
        }
        else {
            answerALabel.setText(mcqAnsLine.split(" ")[0]);
        }

        if (mcqAnsLine.split(" ")[1].contains("_")) {
            answerBLabel.setText(mcqAnsLine.split(" ")[1].replace("_", " "));
        }
        else {
            answerBLabel.setText(mcqAnsLine.split(" ")[1]);
        }

        if (mcqAnsLine.split(" ")[2].contains("_")) {
            answerCLabel.setText(mcqAnsLine.split(" ")[2].replace("_", " "));
        }
        else {
            answerCLabel.setText(mcqAnsLine.split(" ")[2]);
        }

        if (mcqAnsLine.split(" ")[3].contains("_")) {
            answerDLabel.setText(mcqAnsLine.split(" ")[3].replace("_", " "));
        }
        else {
            answerDLabel.setText(mcqAnsLine.split(" ")[3]);
        }
    }

    public void setSurveyCreatorID(String scID) {
        this.surveyCreatorID = scID;
    }

    public String getSurveyCreatorID() {
        return this.surveyCreatorID;
    }

    public void setUsername(String user) {
        this.username = user;
    }

    public String getUsername() {
        return this.username;
    }

    @FXML
    void nextQuestion(ActionEvent event) throws IOException {
        updateQuestionNumber();
        setCounter(getQuestionNumber());
        setQuestionType(getQuestionList().get(index()));

        if (getQuestionType().equals("(OEQ)")) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Survey");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/surveyCreator/surveyCreatorViewOpenEndedQuestionPage.fxml"));
            window.setScene(new Scene(loader.load()));

            surveyCreatorViewOpenEndedQuestionController sCVOEQC = loader.getController();
            sCVOEQC.setQuestionType(getQuestionType());
            sCVOEQC.setQuestionList(getQuestionList());
            sCVOEQC.setCounter(getCounter());
            sCVOEQC.setQuestionNumber();
            sCVOEQC.setQuestionNumberLabel();
            sCVOEQC.setQuestionLabel(sCVOEQC.getQuestionNumber());
            sCVOEQC.setNoOfQuestions(getQuestionList().size());
            sCVOEQC.enablePreviousButton();
            sCVOEQC.setUsername(getUsername());
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
            sCVTFQC.setQuestionType(getQuestionType());
            sCVTFQC.setQuestionList(getQuestionList());
            sCVTFQC.setCounter(getCounter());
            sCVTFQC.setQuestionNumber();
            sCVTFQC.setQuestionNumberLabel();
            sCVTFQC.setQuestionLabel(sCVTFQC.getQuestionNumber());
            sCVTFQC.setNoOfQuestions(getQuestionList().size());
            sCVTFQC.enablePreviousButton();
            sCVTFQC.setUsername(getUsername());
            sCVTFQC.setSurveyCreatorID(getSurveyCreatorID());
            
            if (sCVTFQC.getQuestionNumber() == sCVTFQC.getNoOfQuestions()) {
                sCVTFQC.disableNextButton();
            }

        }

        else if (getQuestionType().equals("(MCQ)")) {
            enablePreviousButton();
            setQuestionNumber();
            setQuestionNumberLabel();
            setQuestionLabel(getQuestionNumber());
            setAnswerLabels(getMCQAnswers(getQuestionList().get(index())));

            if (getQuestionNumber() == getNoOfQuestions()) {
                disableNextButton();
            }
        }

    }

    @FXML
    void previousQuestion(ActionEvent event) throws IOException {
        revertQuestionNumber();
        setCounter(getQuestionNumber());
        setQuestionType(getQuestionList().get(index()));

        if (getQuestionType().equals("(OEQ)")) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Survey");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/surveyCreator/surveyCreatorViewOpenEndedQuestionPage.fxml"));
            window.setScene(new Scene(loader.load()));

            surveyCreatorViewOpenEndedQuestionController sCVOEQC = loader.getController();
            sCVOEQC.setQuestionType(getQuestionType());
            sCVOEQC.setQuestionList(getQuestionList());
            sCVOEQC.setCounter(getCounter());
            sCVOEQC.setQuestionNumber();
            sCVOEQC.setQuestionNumberLabel();
            sCVOEQC.setQuestionLabel(sCVOEQC.getQuestionNumber());
            sCVOEQC.setNoOfQuestions(getQuestionList().size());
            sCVOEQC.setUsername(getUsername());
            sCVOEQC.setSurveyCreatorID(getSurveyCreatorID());

            if (sCVOEQC.getQuestionNumber() == 1) {
                sCVOEQC.disablePreviousButton();
            }

            if (sCVOEQC.getQuestionNumber() != sCVOEQC.getNoOfQuestions()) {
                sCVOEQC.enableNextButton();
            }
        }

        else if (getQuestionType().equals("(TFQ)")) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Survey");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/surveyCreator/surveyCreatorViewTrueFalseQuestionPage.fxml"));
            window.setScene(new Scene(loader.load()));

            surveyCreatorViewTrueFalseQuestionController sCVTFQC = loader.getController();
            sCVTFQC.setQuestionType(getQuestionType());
            sCVTFQC.setQuestionList(getQuestionList());
            sCVTFQC.setCounter(getCounter());
            sCVTFQC.setQuestionNumber();
            sCVTFQC.setQuestionNumberLabel();
            sCVTFQC.setQuestionLabel(sCVTFQC.getQuestionNumber());
            sCVTFQC.setNoOfQuestions(getQuestionList().size());
            sCVTFQC.setUsername(getUsername());
            sCVTFQC.setSurveyCreatorID(getSurveyCreatorID());

            if (sCVTFQC.getQuestionNumber() == 1) {
                sCVTFQC.disablePreviousButton();
            }

            if (sCVTFQC.getQuestionNumber() != sCVTFQC.getNoOfQuestions()) {
                sCVTFQC.enableNextButton();
            }

        }

        else if (getQuestionType().equals("(MCQ)")) {
            setQuestionNumber();
            setQuestionNumberLabel();
            setQuestionLabel(getQuestionNumber());
            setAnswerLabels(getMCQAnswers(getQuestionList().get(index())));

            if (getQuestionNumber() == 1) {
                disablePreviousButton();
            }

            if (getQuestionNumber() != getNoOfQuestions()) {
                enableNextButton();
            }
        }

    }

    @FXML
    void returnToSurveyCreatorPage(ActionEvent event) throws IOException {
        Stage newWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
        newWindow.setTitle("View Own Surveys Page");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/surveyCreator/viewOwnSurveysPage.fxml"));
        newWindow.setScene(new Scene(loader.load()));

        viewSurveyListController vSLC = loader.getController();
        vSLC.setSurveyCreatorID(getSurveyCreatorID());
        vSLC.setComboBox();
        vSLC.setCommentsListView();

    }

}
