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

public class adminViewMultipleChoiceQuestionController {

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

    public void disablePreviousButton() {
        this.previousButton.setDisable(true);
    } // Disables the preivous button

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
    } // Sets the questionList that is used

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
    } // Sets the questionType of the next question

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
    } // Sets the question number label.

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
    } // Sets the text of the question label.

    public void disableNextButton() {
        this.nextButton.setDisable(true);
    } // Disabled the button

    public void enableNextButton() {
        this.nextButton.setDisable(false);
    } // Enables the button

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
    } // Takes all the mcq options set by the survey creator, and appends it to a String attribute

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
    } // Sets the text of all the answer labels.

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
            setQuestionNumber();
            setQuestionNumberLabel();
            setQuestionLabel(getQuestionNumber());
            enablePreviousButton();
            setAnswerLabels(getMCQAnswers(getQuestionList().get(index())));

            if (getQuestionNumber() == getNoOfQuestions()) {
                disableNextButton();
            }
        }
    } // Move to the next Question as according to the question type.

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

            if (aVOEQC.getQuestionNumber() == 1) {
                aVOEQC.disablePreviousButton();
            }

            if (aVOEQC.getQuestionNumber() == aVOEQC.getNoOfQuestions()) {
                aVOEQC.enableNextButton();
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
    } // Move to the previous question as according to the question type of the previous question.

    @FXML
    void returnToAdminPage(ActionEvent event) {
        Stage window = (Stage) returnButton.getScene().getWindow();
        window.close();
    } // Move back to the previous page

}
