package surveyCreatorPage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class surveyCreatorPageController {
    @FXML
    private Button createNewSurveyButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private Button viewSurveysButton;

    private String scID;

    public void setSCID(String scID) {
        this.scID = scID;
    }

    public void setSCIdString(String username) throws IOException {
        this.scID = "";

        File surveyCreatorAccountsFile = new File("src/resources/databases/surveyCreatorAccounts.txt");

        FileReader fr = new FileReader(surveyCreatorAccountsFile);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();

        ArrayList<String> lineList = new ArrayList<>();
        Dictionary<String, String> accList = new Hashtable<>();

        while (line != null) {
            lineList.add(line);
            line = br.readLine();
        }

        br.close();

        for (String acc : lineList) {
            accList.put(acc.split(" ")[1], acc.split(" ")[0]);
        }

        scID = accList.get(username);
    }

    String getSCIdString() {
        return this.scID;
    }

    ArrayList<String> getSurveyDetails(String scID) throws IOException {
        File surveyDetailsFile = new File("src/resources/databases/surveyDetails.txt");

        FileReader fr = new FileReader(surveyDetailsFile);
        BufferedReader br = new BufferedReader(fr);

        ArrayList<String> lineList = new ArrayList<>();
        ArrayList<String> surveyDetailsList = new ArrayList<>();
        Dictionary<String, ArrayList<String>> surveyDetailsDict = new Hashtable<>();

        String line = br.readLine();

        while (line != null) {
            if (line.contains(scID)) {
                lineList.add(line);
            }
            line = br.readLine();
        }

        for (String details : lineList) {
            surveyDetailsList.add(details.split(" ")[2] + " " + details.split(" ")[3]);
        }

        surveyDetailsDict.put(scID, surveyDetailsList);

        br.close();

        return surveyDetailsDict.get(scID);
    }

    public void setScUsername(String username) {
        usernameLabel.setText(username + "!");
    }

    @FXML
    void createNewSurvey(ActionEvent event) throws IOException {
        Stage newWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
        newWindow.setTitle("Create New Survey Page");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/survey/createSurveyPage.fxml"));
        newWindow.setScene(new Scene(loader.load()));

        surveyPage.createNewSurveyController cNSC = loader.getController();
        cNSC.setSCIdString(getSCIdString());

        newWindow.show();
    }

    @FXML
    void exitProgram(ActionEvent event) {
        Platform.exit();

    }

    @FXML
    void logout(ActionEvent event) throws IOException { 
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/mainPage.fxml"));
        Stage window;
        window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("Admin login page");
        window.show();
    }

    @FXML
    void viewSurveys(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task failed!");
        alert.setHeaderText("Task has failed!");
        alert.setContentText("You have not created a survey yet!");

        Stage newWindow = new Stage();
        newWindow.setTitle("View Own Surveys Page");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/surveyCreator/viewOwnSurveysPage.fxml"));
        newWindow.setScene(new Scene(loader.load()));

        viewSurveyListController vSLC = loader.getController();
        vSLC.setSurveyCreatorID(getSCIdString());

        if (vSLC.getUserSurveyList(getSCIdString()).isEmpty()) {
            alert.showAndWait();
        }
        else {
            vSLC.setComboBox();
            vSLC.setCommentsListView();
            newWindow.show();
        }
    }
}
