package surveyPage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class guestViewSurveyController {

    @FXML
    private Button returnButton;

    @FXML
    private TextField surveyCodeTextField;

    @FXML
    private Button viewButton;

    @FXML
    private Label errorLabel;

    private String surveyCode;

    private String questionLine;

    private String questionType;

    public void setQuestionType(String questionString) {
        int firstIndex = questionString.indexOf("(");
        int lastIndex = questionString.indexOf(")");
        this.questionType = questionString.substring(firstIndex, lastIndex + 1);
    }

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
    }

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
    }

    @FXML
    void returnToPrev(ActionEvent event) throws IOException {
        Stage newWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
        newWindow.setTitle("Main Page");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/mainPage.fxml"));
        newWindow.setScene(new Scene(loader.load()));
    }

    @FXML
    void viewSurvey(ActionEvent event) throws IOException {
        try {
            setSurveyCode(surveyCodeTextField.getText());
            setQuestionLine(getSurveyCode());
            setQuestionType(getQuestionList(getQuestionLine()).get(0));

            if (questionType.equals("(OEQ)")) {
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setTitle("Survey");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/survey/showOpenEndedQuestionPage.fxml"));
                window.setScene(new Scene(loader.load()));

                showOpenEndedQuestionController sOEQC = loader.getController();
                sOEQC.setQuestionType("(OEQ)");
                sOEQC.setSurveyCode(getSurveyCode());
                sOEQC.setQuestionList(getQuestionList(questionLine));
                sOEQC.setCounter(1);
                sOEQC.setQuestionNumber();
                sOEQC.setQuestionNumberLabel();
                sOEQC.setQuestionLabel(sOEQC.getQuestionNumber());
                sOEQC.setNoOfQuestions(getQuestionList(questionLine).size());
                sOEQC.disablePreviousButton();
                sOEQC.setAnswerLine(getSurveyCode());

                if (sOEQC.getQuestionNumber() == sOEQC.getNoOfQuestions()) {
                    sOEQC.disableNextButton();
                    sOEQC.enableCompleteButton();
                }
                window.show();
            }
            else if (questionType.equals("(TFQ)")) {
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setTitle("Survey");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/survey/showTrueFalseQuestionPage.fxml"));
                window.setScene(new Scene(loader.load()));

                showTrueFalseQuestionController sTFQC = loader.getController();
                sTFQC.setQuestionType("(TFQ)");
                sTFQC.setQuestionList(getQuestionList(questionLine));
                sTFQC.setCounter(1);
                sTFQC.setQuestionNumber();
                sTFQC.setQuestionNumberLabel();
                sTFQC.setQuestionLabel(sTFQC.getQuestionNumber());
                sTFQC.setNoOfQuestions(getQuestionList(questionLine).size());
                sTFQC.disablePreviousButton();
                sTFQC.setAnswerLine(getSurveyCode());

                if (sTFQC.getQuestionNumber() == sTFQC.getNoOfQuestions()) {
                    sTFQC.disableNextButton();
                    sTFQC.enableCompleteButton();
                }
                window.show();
            }
            else if (questionType.equals("(MCQ)")) {
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setTitle("Survey");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/survey/showMultipleChoiceQuestionPage.fxml"));
                window.setScene(new Scene(loader.load()));

                showMultipleChoiceQuestionController sMCQC = loader.getController();
                sMCQC.setQuestionType("(MCQ)");
                sMCQC.setQuestionList(getQuestionList(questionLine));
                sMCQC.setCounter(1);
                sMCQC.setQuestionNumber();
                sMCQC.setQuestionNumberLabel();
                sMCQC.setQuestionLabel(sMCQC.getQuestionNumber());
                sMCQC.setNoOfQuestions(getQuestionList(questionLine).size());
                sMCQC.disablePreviousButton();
                sMCQC.setAnswerLine(getSurveyCode());
                sMCQC.setAnswerLabels(sMCQC.getMCQAnswers(getQuestionLine()));

                if (sMCQC.getQuestionNumber() == sMCQC.getNoOfQuestions()) {
                    sMCQC.disableNextButton();
                    sMCQC.enableCompleteButton();
                }
                window.show();
            }

        }
        catch (Exception e) {
            errorLabel.setText("Survey doesn't exist!");
        }
    }
}
