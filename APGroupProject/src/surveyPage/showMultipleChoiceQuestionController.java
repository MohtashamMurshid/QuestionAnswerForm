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

public class showMultipleChoiceQuestionController {

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
    private Button clearButton;

    @FXML
    private Button completeButton;

    @FXML
    private Button discardButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Button nextButton;

    @FXML
    private Button previousButton;

    @FXML
    private Label questionLabel;

    @FXML
    private Label questionNumberLabel;private int questionNumber;

    private String answerLine;

    private String questionType;

    private int noOfQuestions;

    private int counter;

    private ArrayList<String> questionList;

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
            this.answerLine += " " + a                                                                          ;
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

    public void disableNextButton() {
        this.nextButton.setDisable(true);
    }

    public void enableNextButton() {
        this.nextButton.setDisable(false);
    }

    boolean emptyAnswers() {
        boolean result = false;

        if (!answerARadioButton.isSelected() && !answerBRadioButton.isSelected() && !answerCRadioButton.isSelected() && !answerDRadioButton.isSelected()) {
            result = true;
        }

        return result;
    }
    @FXML
    void clearAllFields(ActionEvent event) {
        answerARadioButton.setSelected(false);
        answerBRadioButton.setSelected(false);
        answerCRadioButton.setSelected(false);
        answerDRadioButton.setSelected(false);
    }

    @FXML
    void completeSurvey(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Pane");
        alert.setHeaderText("Warning!");
        alert.setContentText("Are you sure you are ready to complete the survey? You will not be able to edit nor delete your answers!");

        String answer = "";
        if (answerARadioButton.isSelected()) {
            answer = answerALabel.getText();
        }
        else if (answerBRadioButton.isSelected()) {
            answer = answerBLabel.getText();
        }
        else if (answerCRadioButton.isSelected()) {
            answer = answerCLabel.getText();
        }
        else {
            answer = answerDLabel.getText();
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
        if (emptyAnswers()) {
            errorLabel.setText("Please select an answer!");
        }
        else {
            errorLabel.setText("");
            updateQuestionNumber();
            setCounter(getQuestionNumber());
            setQuestionType(getQuestionList().get(getQuestionNumber() - 1));

            String answer = "";

            if (answerARadioButton.isSelected()) {
                if (answerALabel.getText().contains(" ")) {
                    answer = answerALabel.getText().replace(" ", "_");
                }
                else {
                    answer = answerALabel.getText();
                }
            }

            if (answerBRadioButton.isSelected()) {
                if (answerBLabel.getText().contains(" ")) {
                    answer = answerBLabel.getText().replace(" ", "_");
                }
                else {
                    answer = answerBLabel.getText();
                }
            }

            if (answerCRadioButton.isSelected()) {
                if (answerCLabel.getText().contains(" ")) {
                    answer = answerCLabel.getText().replace(" ", "_");
                }
                else {
                    answer = answerCLabel.getText();
                }
            }

            if (answerDRadioButton.isSelected()) {
                if (answerALabel.getText().contains(" ")) {
                    answer = answerDLabel.getText().replace(" ", "_");
                }
                else {
                    answer = answerDLabel.getText();
                }
            }

            updateAnswerLine(answer);

            if (getQuestionType().equals("(OEQ)")) {
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setTitle("Survey");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/survey/showOpenEndedQuestionPage.fxml"));
                window.setScene(new Scene(loader.load()));

                showOpenEndedQuestionController sOEQC = loader.getController();
                sOEQC.setQuestionType(getQuestionType());
                sOEQC.setAnswerLine(getAnswerLine());
                sOEQC.setQuestionList(getQuestionList());
                sOEQC.setCounter(getCounter());
                sOEQC.setQuestionNumber();
                sOEQC.setQuestionNumberLabel();
                sOEQC.setQuestionLabel(sOEQC.getQuestionNumber());
                sOEQC.setNoOfQuestions(getQuestionList().size());
                sOEQC.enablePreviousButton();

                if (sOEQC.getQuestionNumber() == sOEQC.getNoOfQuestions()) {
                    sOEQC.disableNextButton();
                    sOEQC.enableCompleteButton();
                }
                window.show();
            }

            else if (getQuestionType().equals("(TFQ)")) {
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setTitle("Survey");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/survey/showTrueFalseQuestionPage.fxml"));
                window.setScene(new Scene(loader.load()));

                showTrueFalseQuestionController sTFQC = loader.getController();
                sTFQC.setQuestionType(getQuestionType());
                sTFQC.setAnswerLine(getAnswerLine());
                sTFQC.setQuestionList(getQuestionList());
                sTFQC.setCounter(getCounter());
                sTFQC.setQuestionNumber();
                sTFQC.setQuestionNumberLabel();
                sTFQC.setQuestionLabel(sTFQC.getQuestionNumber());
                sTFQC.setNoOfQuestions(getQuestionList().size());
                sTFQC.enablePreviousButton();

                if (sTFQC.getQuestionNumber() == sTFQC.getNoOfQuestions()) {
                    sTFQC.disableNextButton();
                    sTFQC.enableCompleteButton();
                }

                window.show();
                
            }
            else if (getQuestionType().equals("(MCQ)")) {
                setQuestionNumber();
                setQuestionNumberLabel();
                setQuestionLabel(getQuestionNumber());
                enablePreviousButton();
                answerARadioButton.setSelected(false);
                answerBRadioButton.setSelected(false);
                answerCRadioButton.setSelected(false);
                answerDRadioButton.setSelected(false);
                setAnswerLabels(getMCQAnswers(getQuestionList().get(getQuestionNumber() - 1)));

                if (getQuestionNumber() == getNoOfQuestions()) {
                    disableNextButton();
                    enableCompleteButton();
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
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Survey");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/survey/showTrueFalseQuestionPage.fxml"));
            window.setScene(new Scene(loader.load()));

            showTrueFalseQuestionController sTFQC = loader.getController();
            sTFQC.setQuestionType(getQuestionType());
            sTFQC.setQuestionList(getQuestionList());
            sTFQC.setCounter(getQuestionNumber());
            sTFQC.setQuestionNumber();
            sTFQC.setQuestionNumberLabel();
            sTFQC.setQuestionLabel(sTFQC.getQuestionNumber());
            sTFQC.setNoOfQuestions(getQuestionList().size());
            sTFQC.setAnswerLine(getAnswerLine());

            if (sTFQC.getQuestionNumber() == 1) {
                sTFQC.disablePreviousButton();
            }

            if (sTFQC.getQuestionNumber() != sTFQC.getNoOfQuestions()) {
                sTFQC.enableNextButton();
                sTFQC.disableCompleteButton();
            }
            
        }

        else if (getQuestionType().equals("(MCQ)")) {
            setQuestionNumber();
            setQuestionNumberLabel();
            setQuestionLabel(getQuestionNumber());
            answerARadioButton.setSelected(false);
            answerBRadioButton.setSelected(false);
            answerCRadioButton.setSelected(false);
            answerDRadioButton.setSelected(false);
            setAnswerLabels(getMCQAnswers(getQuestionList().get(getQuestionNumber() - 1)));

            if (getQuestionNumber() == 1) {
                disablePreviousButton();
            }
            
            if (getQuestionNumber() != getNoOfQuestions()) {
                enableNextButton();
                disableCompleteButton();
            }
        }
    }

}
