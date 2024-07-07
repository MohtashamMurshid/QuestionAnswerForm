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
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class adminViewOpenEndedQuestionController {

    @FXML
    private TextArea answerTextArea;

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

    public void disablePreviousButton() {
        this.previousButton.setDisable(true);
    } // Disables the previous button

    public void enablePreviousButton() {
        this.previousButton.setDisable(false);
    }// Enables the previous button

    public void setCounter(int n) {
        this.counter = n;
    }

    public int getCounter() {
        return this.counter;
    }

    public void setQuestionList(ArrayList<String> qList) {
        this.questionList = qList;
    } // Sets the question list that contains all the questions of the survey

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
    } // Sets the question type of either the next or previous question

    public String getQuestionType() {
        return this.questionType;
    }

    public void setNoOfQuestions(int qSize) {
        this.noOfQuestions = qSize;
    } // Sets the number of questions of the survey

    public int getNoOfQuestions() {
        return this.noOfQuestions;
    }

    public void setQuestionNumberLabel() {
        questionNumberLabel.setText(String.valueOf(getQuestionNumber()));
    } // Sets the question number label of the current question.

    public void setQuestionLabel(int questionNumber) {
        int index = questionNumber - 1;
        String question = "";
        if (this.questionList.get(index).contains("(OEQ)")) {
            question = this.questionList.get(index).replace("(OEQ)", "");
            if (question.contains("_")) {
                question = question.replace("_", " ");
            }
        }
        this.questionLabel.setText(question);
    } // Sets the question label of the current question.

    public void disableNextButton() {
        this.nextButton.setDisable(true);
    } // Disables the next button

    public void enableNextButton() {
        this.nextButton.setDisable(false);
    } // Enables the next button

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
            enablePreviousButton();
            setQuestionNumber();
            setQuestionNumberLabel();
            setQuestionLabel(getQuestionNumber());

            if (getQuestionNumber() == getNoOfQuestions()) {
                disableNextButton();
            }
        }
        else if (getQuestionType().equals("(TFQ)")) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Survey");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/adminPage/adminViewTrueFalseQuestionPage.fxml"));
            window.setScene(new Scene(loader.load()));

            adminViewTrueFalseQuestionController aVTFQC = loader.getController();
            aVTFQC.setQuestionType(getQuestionType());
            aVTFQC.setQuestionList(getQuestionList());
            aVTFQC.setCounter(getCounter());
            aVTFQC.setQuestionNumber();
            aVTFQC.setQuestionNumberLabel();
            aVTFQC.setQuestionLabel(aVTFQC.getQuestionNumber());
            aVTFQC.setNoOfQuestions(getQuestionList().size());
            aVTFQC.enablePreviousButton();
            
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
    } // Moves the user to the next question of the survey as according to the question type of the next question

    @FXML
    void previousQuestion(ActionEvent event) throws IOException {
        revertQuestionNumber();
        setCounter(getQuestionNumber());
        setQuestionType(getQuestionList().get(index()));

        if (getQuestionType().equals("(OEQ)")) {
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

        else if (getQuestionType().equals("(TFQ)")) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Survey");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/adminPage/adminViewTrueFalseQuestionPage.fxml"));
            window.setScene(new Scene(loader.load()));

            adminViewTrueFalseQuestionController aVTFQC = loader.getController();
            aVTFQC.setQuestionType(getQuestionType());
            aVTFQC.setQuestionList(getQuestionList());
            aVTFQC.setCounter(getCounter());
            aVTFQC.setQuestionNumber();
            aVTFQC.setQuestionNumberLabel();
            aVTFQC.setQuestionLabel(aVTFQC.getQuestionNumber());
            aVTFQC.setNoOfQuestions(getQuestionList().size());

            if (aVTFQC.getQuestionNumber() == 1) {
                aVTFQC.disablePreviousButton();
            }

            if (aVTFQC.getQuestionNumber() == aVTFQC.getNoOfQuestions()) {
                aVTFQC.enableNextButton();
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
            aVMCQC.setAnswerLabels(aVMCQC.getMCQAnswers(getQuestionList().get(index())));

            if (aVMCQC.getQuestionNumber() == 1) {
                aVMCQC.disablePreviousButton();
            }

            if (aVMCQC.getQuestionNumber() == aVMCQC.getNoOfQuestions()) {
                aVMCQC.enableNextButton();
            }
        }
    } // Moves the user to the previous question as according to the question type of the previous question

    @FXML
    void returnToAdminPage(ActionEvent event) throws IOException {
        Stage window = (Stage) returnButton.getScene().getWindow();
        window.close();
    } // Moves the user back to the admin page
}
