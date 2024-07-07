package adminPage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class viewAccountsDetailsController {

    @FXML
    private Button closeButton;

    @FXML
    private TextField emailAddressTextField;

    @FXML
    private TextField facultyTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField genderTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField scIdTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    void closeWindow(ActionEvent event) {
        Stage window = (Stage) closeButton.getScene().getWindow();
        window.close();
    }

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

    public void setDetails(String username) throws IOException {
        String scIdString = "";
        String usernameString = "";
        String passwordString = "";
        String firstNameString = "";
        String lastNameString = "";
        String facultyString = "";
        String emailAddressString = "";
        String genderString = "";
        String phoneNumberString = "";

        scIdString += getSCID(username);
        usernameString += username;
        passwordString += getPassword(username);

        if (getGivenName(username).contains("_")) {
            firstNameString += getGivenName(username).replace("_", " ");
        }
        else {
            firstNameString += getGivenName(username);
        }

        if (getSurname(username).contains("_")) {
            lastNameString += getSurname(username).replace("_", " ");
        }
        else {
            lastNameString += getSurname(username);
        }

        if (getFaculty(username).contains("_")) {
            facultyString += getFaculty(username).replace("_", " ");
        }
        else {
            facultyString += getFaculty(username);
        }

        if (getEmailAddress(username).contains("_")) {
            emailAddressString += getEmailAddress(username).replace("_", " ");
        }
        else {
            emailAddressString += getEmailAddress(username);
        }

        if (getGender(username).contains("_")) {
            genderString += getGender(username).replace("_", " ");
        }
        else {
            genderString += getGender(username);
        }

        if (getPhoneNumber(username).contains("_")) {
            phoneNumberString += getPhoneNumber(username).replace("_", " ");
        }
        else {
            phoneNumberString += getPhoneNumber(username);
        }
        
        scIdTextField.setText(scIdString);
        usernameTextField.setText(usernameString);
        passwordTextField.setText(passwordString);
        firstNameTextField.setText(firstNameString);
        lastNameTextField.setText(lastNameString);
        facultyTextField.setText(facultyString);
        emailAddressTextField.setText(emailAddressString);
        genderTextField.setText(genderString);
        phoneNumberTextField.setText(phoneNumberString);
    } // Sets the text of all the textfields
}
