package surveyPage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class showTrueFalseQuestionController {

    @FXML
    private Button clearButton;

    @FXML
    private Button completeButton;

    @FXML
    private Button discardButton;

    @FXML
    private Label errorLabel;

    @FXML
    private RadioButton falseRadioButton;

    @FXML
    private Button nextButton;

    @FXML
    private Label questionLabel;

    @FXML
    private Label questionNumberLabel;

    @FXML
    private ToggleGroup trueFalseGroup;

    @FXML
    private RadioButton trueRadioButton;

    @FXML
    private Button previousButton;

    private int questionNumber;

    private String answerLine;

    private String questionType;

    private int noOfQuestions;

    private int counter;

    private ArrayList<String> questionList;

    private String surveyCode;

    public void setSurveyCode(String surveyNumber) {
        this.surveyCode = surveyNumber;
    }

    public String getSurveyCode() {
        return this.surveyCode;
    }

    public void disablePreviousButton() {
        this.previousButton.setDisable(true);
    }

    public void enablePreviousButton() {
        this.previousButton.setDisable(false);
    }

    public void enableCompleteButton() {
        this.completeButton.setDisable(false);
    }

    public void disableCompleteButton() {
        this.completeButton.setDisable(true);
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

    public void setAnswerLine(String surveyNumber) {
        this.answerLine = surveyNumber;
    }

    public String getAnswerLine() {
        return this.answerLine;
    }

    public void updateAnswerLine(String answerString) {
        char lastChar = getAnswerLine().charAt(getAnswerLine().length() - 1);
        if (lastChar == ' ') {
            this.answerLine += getQuestionType() + answerString;
        }
        else {
            this.answerLine += " " + getQuestionType() + answerString;
        }
    }

    public void revertAnswerLine() {
        ArrayList<String> ansLine = new ArrayList<>();

        for (String ans : this.answerLine.split(" ")) {
            ansLine.add(ans);
        }
        ansLine.remove(ansLine.size() - 1);
        this.answerLine = ansLine.get(0);
        ansLine.remove(0);

        for (String a : ansLine) {
            this.answerLine += " " + a;
        }
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

    @FXML
    void clearAllFields(ActionEvent event) {
        trueRadioButton.setSelected(false);
        falseRadioButton.setSelected(false);
    }

    @FXML
    void completeSurvey(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Pane");
        alert.setHeaderText("Warning!");
        alert.setContentText("Are you sure you are ready to complete the survey? You will not be able to edit nor delete your answers!");
        
        String answer = "";

        if (trueRadioButton.isSelected()) {
            answer = "True";
        }
        else {
            answer = "False";
        }
        updateAnswerLine(answer);

        File surveyAnswersFile = new File("src/resources/databases/surveyAnswers.txt");
        FileWriter fw = new FileWriter(surveyAnswersFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(getAnswerLine() + "\n");
        bw.close();

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage newWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
            newWindow.setTitle("Main Page");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/mainPage.fxml"));
            newWindow.setScene(new Scene(loader.load()));
        }
        else {
            alert.close();
        }
    }

    @FXML
    void discardAll(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Pane");
        alert.setHeaderText("Warning!");
        alert.setContentText("Are you sure you want to discard everything you've written on this page?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage newWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
            newWindow.setTitle("Main Page");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/mainPage.fxml"));
            newWindow.setScene(new Scene(loader.load()));
        }

    }

    @FXML
    void nextQuestion(ActionEvent event) throws IOException {
        if (!trueRadioButton.isSelected() && !falseRadioButton.isSelected()) {
            errorLabel.setText("Please select at least one answer!");
        }
        else {
            errorLabel.setText("");
            updateQuestionNumber();
            setCounter(getQuestionNumber());
            setQuestionType(getQuestionList().get(getQuestionNumber() - 1));

            String answer = "";
            if (trueRadioButton.isSelected()) {
                answer = "True";
            }
            else {
                answer = "False";
            }
            updateAnswerLine(answer);

            if (getQuestionType().equals("(OEQ)")) {
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setTitle("Survey");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/survey/showOpenEndedQuestionPage.fxml"));
                window.setScene(new Scene(loader.load()));

                showOpenEndedQuestionController sOEQC = loader.getController();
                sOEQC.setQuestionType(getQuestionType());
                sOEQC.setQuestionList(getQuestionList());
                sOEQC.setCounter(getQuestionNumber());
                sOEQC.setQuestionNumber();
                sOEQC.setQuestionNumberLabel();
                sOEQC.setQuestionLabel(sOEQC.getQuestionNumber());
                sOEQC.setNoOfQuestions(getQuestionList().size());
                sOEQC.enablePreviousButton();
                sOEQC.setAnswerLine(getAnswerLine());
    
                if (sOEQC.getQuestionNumber() == sOEQC.getNoOfQuestions()) {
                    sOEQC.disableNextButton();
                    sOEQC.enableCompleteButton();
                }

            }
            else if (getQuestionType().equals("(TFQ)")) {
                enablePreviousButton();
                setQuestionNumber();
                setQuestionNumberLabel();
                trueRadioButton.setSelected(false);
                falseRadioButton.setSelected(false);
                setQuestionLabel(getQuestionNumber());

                if (getQuestionNumber() == getNoOfQuestions()) {
                    disableNextButton();
                    enableCompleteButton();
                }
            }
            
            else if (getQuestionType().equals("(MCQ)")) {
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setTitle("Survey");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/survey/showMultipleChoiceQuestionPage.fxml"));
                window.setScene(new Scene(loader.load()));

                showMultipleChoiceQuestionController sMCQC = loader.getController();
                sMCQC.setQuestionType(getQuestionType());
                sMCQC.setQuestionList(getQuestionList());
                sMCQC.setCounter(getQuestionNumber());
                sMCQC.setQuestionNumber();
                sMCQC.setQuestionNumberLabel();
                sMCQC.setQuestionLabel(sMCQC.getQuestionNumber());
                sMCQC.setNoOfQuestions(getQuestionList().size());
                sMCQC.enablePreviousButton();
                sMCQC.setAnswerLine(getAnswerLine());
                sMCQC.setAnswerLabels(sMCQC.getMCQAnswers(getQuestionList().get(getQuestionNumber() - 1)));

                if (sMCQC.getQuestionNumber() == sMCQC.getNoOfQuestions()) {
                    sMCQC.disableNextButton();
                    sMCQC.enableCompleteButton();
                }
            }
        }
    }

    @FXML
    void previousQuestion(ActionEvent event) throws IOException {
        revertQuestionNumber();
        setCounter(getQuestionNumber());
        revertAnswerLine();
        setQuestionType(getQuestionList().get(getQuestionNumber() - 1));

        if (getQuestionType().equals("(OEQ)")) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Survey");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/survey/showOpenEndedQuestionPage.fxml"));
            window.setScene(new Scene(loader.load()));

            showOpenEndedQuestionController sOEQC = loader.getController();
            sOEQC.setQuestionType(getQuestionType());
            sOEQC.setQuestionList(getQuestionList());
            sOEQC.setCounter(getQuestionNumber());
            sOEQC.setQuestionNumber();
            sOEQC.setQuestionNumberLabel();
            sOEQC.setQuestionLabel(sOEQC.getQuestionNumber());
            sOEQC.setNoOfQuestions(getQuestionList().size());
            sOEQC.enablePreviousButton();
            sOEQC.setAnswerLine(getAnswerLine());

            if (sOEQC.getQuestionNumber() == 1) {
                sOEQC.disablePreviousButton();
            }

            if (sOEQC.getQuestionNumber() != sOEQC.getNoOfQuestions()) {
                sOEQC.enableNextButton();
                sOEQC.disableCompleteButton();
            }
            
        }
        else if (getQuestionType().equals("(TFQ)")) {
            setQuestionNumber();
            setQuestionNumberLabel();
            trueRadioButton.setSelected(false);
            falseRadioButton.setSelected(false);
            setQuestionLabel(getQuestionNumber());

            if (getQuestionNumber() == 1) {
                disablePreviousButton();
            }
            
            if (getQuestionNumber() != getNoOfQuestions()) {
                enableNextButton();
                disableCompleteButton();
            }
        }
            
        else if (getQuestionType().equals("(MCQ)")) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Survey");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/survey/showMultipleChoiceQuestionPage.fxml"));
            window.setScene(new Scene(loader.load()));

            showMultipleChoiceQuestionController sMCQC = loader.getController();
            sMCQC.setQuestionType(getQuestionType());
            sMCQC.setQuestionList(getQuestionList());
            sMCQC.setCounter(getQuestionNumber());
            sMCQC.setQuestionNumber();
            sMCQC.setQuestionNumberLabel();
            sMCQC.setQuestionLabel(sMCQC.getQuestionNumber());
            sMCQC.setNoOfQuestions(getQuestionList().size());
            sMCQC.setAnswerLine(getAnswerLine());
            sMCQC.setAnswerLabels(sMCQC.getMCQAnswers(getQuestionList().get(getQuestionNumber() - 1)));

            if (sMCQC.getQuestionNumber() == 1) {
                sMCQC.disablePreviousButton();
            }

            if (sMCQC.getQuestionNumber() != sMCQC.getNoOfQuestions()) {
                sMCQC.enableNextButton();
                sMCQC.disableCompleteButton();
            }
        }
    }
}
