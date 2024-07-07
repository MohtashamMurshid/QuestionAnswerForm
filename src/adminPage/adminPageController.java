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

public class adminPageController {

    @FXML
    private Button createAdminAccountButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button loginPageButton;

    @FXML
    private Button viewAccountsButton;


    @FXML
    void createAdminAccount(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/adminPage/registerAdminPage.fxml"));
        Stage window;
        window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("Admin Sign Up Page");
        window.show();
    } // Brings the administrator to the registration page.

    @FXML
    void exitProgram(ActionEvent event) {
        Platform.exit();
    } // Shuts down the program.

    @FXML
    void loginPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/adminPage/adminLoginPage.fxml"));
        Stage window;
        window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("Admin login page");
        window.show();
    } // Returns the user back to the previous page.

    @FXML
    void viewAccounts(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/adminPage/viewAccountsPage.fxml"));
        Stage window;
        window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("View User Account Details Page");
        window.show();
    } // Brings the user to the page 

}
