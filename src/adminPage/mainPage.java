package adminPage;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class mainPage {

    @FXML
    private Button adminLoginButton;

    @FXML
    private Button exitProgramButton;

    @FXML
    private Button guestButton;

    @FXML
    private Button userLoginButton;

    @FXML
    void adminLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/adminPage/adminLoginPage.fxml"));
        Stage window;
        window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("Admin login page");
        window.show();
    } // Moves the user to the admin login page

    @FXML
    void exitProgram(ActionEvent event) {
        Platform.exit();
    } // Shuts down the program

    @FXML
    void userLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/surveyCreator/surveyCreatorLoginPage.fxml"));
        Stage window;
        window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("Survey Creator Login Page");
        window.show();
    } // Moves the user to the survey creator login page.

    @FXML
    void guestLogin(ActionEvent event) throws IOException {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Guest Page");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/survey/guestViewSurvey.fxml"));
        window.setScene(new Scene(loader.load()));
        window.show();
    } // Moves the user to the guest page.

}
