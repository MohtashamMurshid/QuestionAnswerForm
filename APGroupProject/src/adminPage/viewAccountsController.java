package adminPage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class viewAccountsController implements Initializable{

    @FXML
    private ListView<String> accountsListView;

    @FXML
    private Button deleteAccountButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Button returnToAdminPage;

    @FXML
    private Button viewAccountDetailsButton;

    @FXML
    private Button viewCreatedSurveysButton;

    String getSCID(String username) throws IOException {
        FileReader fr = new FileReader("src/resources/databases/surveyCreatorAccounts.txt");
        BufferedReader br = new BufferedReader(fr);

        ArrayList<String> lineList = new ArrayList<>();
        Dictionary<String, String> accountsDict = new Hashtable<>();
        String line = br.readLine();
        
        while (line != null) {
            lineList.add(line);
            line = br.readLine();
        }

        br.close();
        
        for (String acc : lineList) {
            accountsDict.put(acc.split(" ")[1], acc.split(" ")[0]);
        }

        String scId = accountsDict.get(username);

        return scId;
    }

    String getPassword(String username) throws IOException{
        FileReader fr = new FileReader("src/resources/databases/surveyCreatorAccounts.txt");
        BufferedReader br = new BufferedReader(fr);

        ArrayList<String> lineList = new ArrayList<>();
        Dictionary<String, String> accountsDict = new Hashtable<>();
        String line = br.readLine();
        
        while (line != null) {
            lineList.add(line);
            line = br.readLine();
        }

        br.close();
        
        for (String acc : lineList) {
            accountsDict.put(acc.split(" ")[1], acc.split(" ")[2]);
        }

        String pass = accountsDict.get(username);

        return pass;
    }

    String getGivenName(String username) throws IOException {
        FileReader fr = new FileReader("src/resources/databases/surveyCreatorAccounts.txt");
        BufferedReader br = new BufferedReader(fr);

        ArrayList<String> lineList = new ArrayList<>();
        Dictionary<String, String> accountsDict = new Hashtable<>();
        String line = br.readLine();
        
        while (line != null) {
            lineList.add(line);
            line = br.readLine();
        }

        br.close();
        
        for (String acc : lineList) {
            accountsDict.put(acc.split(" ")[1], acc.split(" ")[3]);
        }

        String name = accountsDict.get(username);

        return name;
    }

    String getSurname(String username) throws IOException {
        FileReader fr = new FileReader("src/resources/databases/surveyCreatorAccounts.txt");
        BufferedReader br = new BufferedReader(fr);

        ArrayList<String> lineList = new ArrayList<>();
        Dictionary<String, String> accountsDict = new Hashtable<>();
        String line = br.readLine();
        
        while (line != null) {
            lineList.add(line);
            line = br.readLine();
        }

        br.close();
        
        for (String acc : lineList) {
            accountsDict.put(acc.split(" ")[1], acc.split(" ")[4]);
        }

        String surname = accountsDict.get(username);

        return surname;
    }

    String getFaculty(String username) throws IOException {
        FileReader fr = new FileReader("src/resources/databases/surveyCreatorAccounts.txt");
        BufferedReader br = new BufferedReader(fr);

        ArrayList<String> lineList = new ArrayList<>();
        Dictionary<String, String> accountsDict = new Hashtable<>();
        String line = br.readLine();
        
        while (line != null) {
            lineList.add(line);
            line = br.readLine();
        }

        br.close();
        
        for (String acc : lineList) {
            accountsDict.put(acc.split(" ")[1], acc.split(" ")[5]);
        }

        String faculty = accountsDict.get(username);

        return faculty;
    }

    String getEmailAddress(String username) throws IOException {
        FileReader fr = new FileReader("src/resources/databases/surveyCreatorAccounts.txt");
        BufferedReader br = new BufferedReader(fr);

        ArrayList<String> lineList = new ArrayList<>();
        Dictionary<String, String> accountsDict = new Hashtable<>();
        String line = br.readLine();
        
        while (line != null) {
            lineList.add(line);
            line = br.readLine();
        }

        br.close();
        
        for (String acc : lineList) {
            accountsDict.put(acc.split(" ")[1], acc.split(" ")[6]);
        }

        String emailAddress = accountsDict.get(username);

        return emailAddress;
    }

    String getGender(String username) throws IOException {
        FileReader fr = new FileReader("src/resources/databases/surveyCreatorAccounts.txt");
        BufferedReader br = new BufferedReader(fr);

        ArrayList<String> lineList = new ArrayList<>();
        Dictionary<String, String> accountsDict = new Hashtable<>();
        String line = br.readLine();
        
        while (line != null) {
            lineList.add(line);
            line = br.readLine();
        }

        br.close();
        
        for (String acc : lineList) {
            accountsDict.put(acc.split(" ")[1], acc.split(" ")[7]);
        }

        String gender = accountsDict.get(username);

        return gender;
    }

    String getPhoneNumber(String username) throws IOException {
        FileReader fr = new FileReader("src/resources/databases/surveyCreatorAccounts.txt");
        BufferedReader br = new BufferedReader(fr);

        ArrayList<String> lineList = new ArrayList<>();
        Dictionary<String, String> accountsDict = new Hashtable<>();
        String line = br.readLine();
        
        while (line != null) {
            lineList.add(line);
            line = br.readLine();
        }

        br.close();
        
        for (String acc : lineList) {
            accountsDict.put(acc.split(" ")[1], acc.split(" ")[8]);
        }

        String phoneNo = accountsDict.get(username);

        return phoneNo;
    }

    ArrayList<String> getAccountList() throws IOException {
        ArrayList<String> userList = new ArrayList<>();
        ArrayList<String> lineList = new ArrayList<>();
        FileReader fr = new FileReader("src/resources/databases/surveyCreatorAccounts.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();

        while (line != null) {
            lineList.add(line);
            line = br.readLine();
        }
        br.close();

        for (String acc : lineList) {
            userList.add(acc.split(" ")[1]);
        }

        return userList;
    }

    @FXML
    void adminPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/adminPage/adminPage.fxml"));
        Stage window;
        window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("Admin Page");
        window.show();
    } // Moves the user back to the admin Page

    @FXML
    void deleteAccount(ActionEvent event) throws IOException {
        int index = accountsListView.getSelectionModel().getSelectedIndex();
        File inFile = new File("src/resources/databases/surveyCreatorAccounts.txt");

        if (!inFile.isFile()) {
            System.out.println("Parameter is not an existing file");
            return;
        }

        File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

        BufferedReader br = new BufferedReader(new FileReader("src/resources/databases/surveyCreatorAccounts.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
        String line = br.readLine();

        String user = "";
        ObservableList<String> users;
        users = accountsListView.getSelectionModel().getSelectedItems();

        for (String u : users) {
            user += u;
        }

        String lineToRemove = getSCID(user) + " " + user + " " + getPassword(user) + " " + getGivenName(user) + " " + getSurname(user) + " " + getFaculty(user) + " " + getEmailAddress(user) + " " + getGender(user) + " " + getPhoneNumber(user);

        while (line != null) {
            if (!line.trim().equals(lineToRemove)) {
                pw.println(line);
                pw.flush();
            }
            line = br.readLine();
        }
        pw.close();
        br.close();

        if (!inFile.delete()) {
            System.out.println("Could not delete file");
            return;
        }
    
          //Rename the new file to the filename the original file had.
        if (!tempFile.renameTo(inFile)) {
            System.out.println("Could not rename file");
        }

        errorLabel.setText("Successfully deleted account!");
        errorLabel.setTextFill(Color.GREEN);

        accountsListView.getItems().remove(index);
    } // Deletes the selected survey creator account

    // https://www.youtube.com/watch?v=wxhGKR3PQpo&t=344s

    @FXML
    void viewAccountDetails(ActionEvent event) throws IOException {
        Stage newWindow = new Stage();
        newWindow.setTitle("View Account Details Page");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/adminPage/viewAccountsDetailsPage.fxml"));
        newWindow.setScene(new Scene(loader.load()));

        String user = "";
        viewAccountsDetailsController detailsController = loader.getController();

        ObservableList<String> users;
        users = accountsListView.getSelectionModel().getSelectedItems();

        for (String u : users) {
            user = u;
        }

        detailsController.setDetails(user);
        newWindow.show();
    } // Creates a new window that displays all the details of the selected survey creator account.
    
    @FXML
    void viewSurvey(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task failed!");
        alert.setHeaderText("Task has failed!");
        alert.setContentText("User has not created a survey!");

        Stage newWindow = new Stage();
        newWindow.setTitle("View User Created Surveys Page");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/adminPage/adminViewSurveys.fxml"));
        newWindow.setScene(new Scene(loader.load()));

        String user = "";
        userCreatedSurveysController userSurvey = loader.getController();

        ObservableList<String> users;
        users = accountsListView.getSelectionModel().getSelectedItems();

        for (String u : users) {
            user = u;
        }

        userSurvey.setSCID(user);
        userSurvey.setUsername(user);

        if (userSurvey.getUserSurveyList(userSurvey.getSCID()).isEmpty()) {
            alert.showAndWait();
        }
        else {
            userSurvey.setComboBox();
            userSurvey.setCommentsListView();
            newWindow.show();
        } // Views all the surveys the user has made.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            for (String user : getAccountList()) {
                accountsListView.getItems().add(user);
                accountsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            }
        } catch (IOException e) {
        }
    } // Adds the usernames of all the accounts to the list view

    // https://www.youtube.com/watch?v=Z7th7RSRitw&t=256s

}
