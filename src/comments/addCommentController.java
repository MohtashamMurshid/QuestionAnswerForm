package comments;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addCommentController {

    @FXML
    private Button addButton;

    @FXML
    private TextField commentTextArea;

    @FXML
    private Button discardButton;
    
    @FXML
    private Label errorLabel;

    private String surveyCode;

    private String username;

    public void setUsername(String user) {
        this.username = user;
    }

    public String getUsername() {
        return this.username;
    }

    public void setSurveyCode(String surveyNumber) {
        this.surveyCode = surveyNumber;
    }

    public String getSurveyCode() {
        return this.surveyCode;
    }

    @FXML
    void addComment(ActionEvent event) throws IOException {
        if (commentTextArea.getText().equals("")) {
            errorLabel.setText("Please fill in a comment before adding it!");
        }
        else {
            errorLabel.setText("");
            String comment = commentTextArea.getText();

            if (comment.contains(" ")) {
                comment = comment.replace(" ", "_");
            }

            String commentLine = getSurveyCode() + " " + comment + "\n";

            File surveyCommentsFile = new File("src/resources/databases/surveyComments.txt");

            FileWriter fw = new FileWriter(surveyCommentsFile, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(commentLine);
            bw.close();

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("View User Created Surveys Page");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/adminPage/adminViewSurveys.fxml"));
            window.setScene(new Scene(loader.load()));

            adminPage.userCreatedSurveysController uCSC = loader.getController();
            uCSC.setSCID(getUsername());
            uCSC.setUsername(getUsername());
            uCSC.setComboBox();
        }
    }

    @FXML
    void clearFields(ActionEvent event) {
        commentTextArea.setText("");
    }

    @FXML
    void discardComment(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText("Warning!");
        alert.setContentText("Are you sure you want to discard everything you've written?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("View User Created Surveys Page");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/adminPage/adminViewSurveys.fxml"));
            window.setScene(new Scene(loader.load()));

            adminPage.userCreatedSurveysController uCSC = loader.getController();
            uCSC.setSCID(getUsername());
            uCSC.setUsername(getUsername());
            uCSC.setComboBox();
        }
        else {
            alert.close();
        }
    }

}
