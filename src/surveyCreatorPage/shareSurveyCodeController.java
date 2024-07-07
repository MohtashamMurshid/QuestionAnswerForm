package surveyCreatorPage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class shareSurveyCodeController {

    @FXML
    private Button closeButton;

    @FXML
    private TextField surveyCodeTextField;

    private String surveyCode;

    public void setSurveyCode(String surveyNumber) {
        this.surveyCode = surveyNumber;
    }

    public String getSurveyCode() {
        return this.surveyCode;
    }

    public void setSurveyCodeTextField() {
        surveyCodeTextField.setText(getSurveyCode());
    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage window = (Stage) closeButton.getScene().getWindow();
        window.close();
    }

}
