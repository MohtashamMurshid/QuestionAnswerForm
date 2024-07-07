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

public class surveyCreatorViewTrueFalseQuestionController {

    @FXML
    private Label errorLabel;

    @FXML
    private RadioButton falseRadioButton;

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

    @FXML
    private ToggleGroup trueFalseGroup;

    @FXML
    private RadioButton trueRadioButton;

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
        if (this.questionList.get(index).contains("(TFQ)")) {
            question = this.questionList.get(index).replace("(TFQ)", "");
            if (question.contains("_")) {
                question = question.replace("_", " ");
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
            enablePreviousButton();
            setQuestionNumber();
            setQuestionNumberLabel();
            setQuestionLabel(getQuestionNumber());

            if (getQuestionNumber() == getNoOfQuestions()) {
                disableNextButton();
            }
        }

        else if (getQuestionType().equals("(MCQ)")) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Survey");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/surveyCreator/surveyCreatorViewMultipleChoiceQuestionPage.fxml"));
            window.setScene(new Scene(loader.load()));

            surveyCreatorViewMultipleChoiceQuestionController sCVMCQC = loader.getController();
            sCVMCQC.setQuestionType(getQuestionType());
            sCVMCQC.setQuestionList(getQuestionList());
            sCVMCQC.setCounter(getCounter());
            sCVMCQC.setQuestionNumber();
            sCVMCQC.setQuestionNumberLabel();
            sCVMCQC.setQuestionLabel(sCVMCQC.getQuestionNumber());
            sCVMCQC.setNoOfQuestions(getQuestionList().size());
            sCVMCQC.enablePreviousButton();
            sCVMCQC.setAnswerLabels(sCVMCQC.getMCQAnswers(getQuestionList().get(index())));
            sCVMCQC.setUsername(getUsername());
            sCVMCQC.setSurveyCreatorID(getSurveyCreatorID());
            
            if (sCVMCQC.getQuestionNumber() == sCVMCQC.getNoOfQuestions()) {
                sCVMCQC.disableNextButton();
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
            setQuestionNumber();
            setQuestionNumberLabel();
            setQuestionLabel(getQuestionNumber());

            if (getQuestionNumber() == 1) {
                disablePreviousButton();
            }

            if (getQuestionNumber() != getNoOfQuestions()) {
                enableNextButton();
            }

        }

        else if (getQuestionType().equals("(MCQ)")) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Survey");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/surveyCreator/surveyCreatorViewMultipleChoiceQuestionPage.fxml"));
            window.setScene(new Scene(loader.load()));

            surveyCreatorViewMultipleChoiceQuestionController sCVMCQC = loader.getController();
            sCVMCQC.setQuestionType(getQuestionType());
            sCVMCQC.setQuestionList(getQuestionList());
            sCVMCQC.setCounter(getCounter());
            sCVMCQC.setQuestionNumber();
            sCVMCQC.setQuestionNumberLabel();
            sCVMCQC.setQuestionLabel(sCVMCQC.getQuestionNumber());
            sCVMCQC.setNoOfQuestions(getQuestionList().size());
            sCVMCQC.setAnswerLabels(sCVMCQC.getMCQAnswers(getQuestionList().get(index())));
            sCVMCQC.setUsername(getUsername());
            sCVMCQC.setSurveyCreatorID(getSurveyCreatorID());

            if (sCVMCQC.getQuestionNumber() == 1) {
                sCVMCQC.disablePreviousButton();
            }

            if (sCVMCQC.getQuestionNumber() != sCVMCQC.getNoOfQuestions()) {
                sCVMCQC.enableNextButton();
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
