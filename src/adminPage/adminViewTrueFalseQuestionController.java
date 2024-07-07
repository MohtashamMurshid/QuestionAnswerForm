package adminPage;

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

public class adminViewTrueFalseQuestionController {

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

    public void disablePreviousButton() {
        this.previousButton.setDisable(true);
    } // Disables the previous button

    public void enablePreviousButton() {
        this.previousButton.setDisable(false);
    } // Enables the previous button

    public void setCounter(int n) {
        this.counter = n;
    } // Sets the counter

    public int getCounter() {
        return this.counter;
    }

    public void setQuestionList(ArrayList<String> qList) {
        this.questionList = qList;
    } // Sets the question list of the survey

    public ArrayList<String> getQuestionList() {
        return this.questionList;
    }

    public int index() {
        return this.questionNumber - 1;
    }

    public void setQuestionNumber() {
        this.questionNumber = getCounter();
    } // Sets the question number of the current question

    public void updateQuestionNumber() {
        this.questionNumber++;
    } // Increments the question number by 1

    public void revertQuestionNumber() {
        this.questionNumber--;
    } // Decrements the question by 1

    public int getQuestionNumber() {
        return this.questionNumber;
    }

    public void setQuestionType(String questionString) {
        int firstIndex = questionString.indexOf("(");
        int lastIndex = questionString.indexOf(")");
        this.questionType = questionString.substring(firstIndex, lastIndex + 1);
    } // Sets the question type of the next or previous question

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
    } // Sets the question number label of the current question

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
    } // Sets the question label of the current question.

    public void disableNextButton() {
        this.nextButton.setDisable(true);
    } // Disable next button

    public void enableNextButton() {
        this.nextButton.setDisable(false);
    } // Enable next button

    public void setSurveyCode(String surveyNumber) {
        this.surveyCode = surveyNumber;
    }

    public String getSurveyCode() {
        return this.surveyCode;
    }

    @FXML
    void nextQuestion(ActionEvent event) throws IOException {
        updateQuestionNumber();
        setCounter(getQuestionNumber());
        setQuestionType(getQuestionList().get(index()));

        if (getQuestionType().equals("(OEQ)")) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Survey");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/adminPage/adminViewOpenEndedQuestionPage.fxml"));
            window.setScene(new Scene(loader.load()));

            adminViewOpenEndedQuestionController aVOEQC = loader.getController();
            aVOEQC.setQuestionType(getQuestionType());
            aVOEQC.setQuestionList(getQuestionList());
            aVOEQC.setCounter(getCounter());
            aVOEQC.setQuestionNumber();
            aVOEQC.setQuestionNumberLabel();
            aVOEQC.setQuestionLabel(aVOEQC.getQuestionNumber());
            aVOEQC.setNoOfQuestions(getQuestionList().size());
            aVOEQC.enablePreviousButton();
            
            if (aVOEQC.getQuestionNumber() == aVOEQC.getNoOfQuestions()) {
                aVOEQC.disableNextButton();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/adminPage/adminViewMultipleChoiceQuestionPage.fxml"));
            window.setScene(new Scene(loader.load()));

            adminViewMultipleChoiceQuestionController aVMCQC = loader.getController();
            aVMCQC.setQuestionType(getQuestionType());
            aVMCQC.setQuestionList(getQuestionList());
            aVMCQC.setCounter(getCounter());
            aVMCQC.setQuestionNumber();
            aVMCQC.setQuestionNumberLabel();
            aVMCQC.setQuestionLabel(aVMCQC.getQuestionNumber());
            aVMCQC.setNoOfQuestions(getQuestionList().size());
            aVMCQC.enablePreviousButton();
            aVMCQC.setAnswerLabels(aVMCQC.getMCQAnswers(getQuestionList().get(index())));

            if (aVMCQC.getQuestionNumber() == aVMCQC.getNoOfQuestions()) {
                aVMCQC.disableNextButton();
            }
        }
    } // Moves the user to the next question of the survey as according to the question type.

    @FXML
    void previousQuestion(ActionEvent event) throws IOException {
        revertQuestionNumber();
        setCounter(getQuestionNumber());
        setQuestionType(getQuestionList().get(index()));

        if (getQuestionType().equals("(OEQ)")) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Survey");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/adminPage/adminViewOpenEndedQuestionPage.fxml"));
            window.setScene(new Scene(loader.load()));

            adminViewOpenEndedQuestionController aVOEQC = loader.getController();
            aVOEQC.setQuestionType(getQuestionType());
            aVOEQC.setQuestionList(getQuestionList());
            aVOEQC.setCounter(getCounter());
            aVOEQC.setQuestionNumber();
            aVOEQC.setQuestionNumberLabel();
            aVOEQC.setQuestionLabel(aVOEQC.getQuestionNumber());
            aVOEQC.setNoOfQuestions(getQuestionList().size());
            aVOEQC.enablePreviousButton();
            
            if (aVOEQC.getQuestionNumber() == aVOEQC.getNoOfQuestions()) {
                aVOEQC.disableNextButton();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/adminPage/adminViewMultipleChoiceQuestionPage.fxml"));
            window.setScene(new Scene(loader.load()));

            adminViewMultipleChoiceQuestionController aVMCQC = loader.getController();
            aVMCQC.setQuestionType(getQuestionType());
            aVMCQC.setQuestionList(getQuestionList());
            aVMCQC.setCounter(getCounter());
            aVMCQC.setQuestionNumber();
            aVMCQC.setQuestionNumberLabel();
            aVMCQC.setQuestionLabel(aVMCQC.getQuestionNumber());
            aVMCQC.setNoOfQuestions(getQuestionList().size());
            aVMCQC.enablePreviousButton();
            aVMCQC.setAnswerLabels(aVMCQC.getMCQAnswers(getQuestionList().get(index())));

            if (aVMCQC.getQuestionNumber() == 1) {
                aVMCQC.disablePreviousButton();
            }

            if (aVMCQC.getQuestionNumber() == aVMCQC.getNoOfQuestions()) {
                aVMCQC.enableNextButton();
            }

        }
    } // Moves the user to the previous question of the survey as according to the question type.

    @FXML
    void returnToAdminPage(ActionEvent event) {
        Stage window = (Stage) returnButton.getScene().getWindow();
        window.close();
    } // Moves the user to the user to the previous page.

}
