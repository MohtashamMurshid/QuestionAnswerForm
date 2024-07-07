package surveyCreatorPage;

public class surveyCreatorAccounts {
    
    private String scId;
    private String scUsername;
    private String scPassword;
    private String scGivenName;
    private String scSurName;
    private String scFaculty;
    private String scEmailAdress;
    private String scGender;
    private String scPhoneNumber;

    public surveyCreatorAccounts() {}

    public String getScId() {
        return ("SC" + this.scId);
    }

    public void setScId(String scIDString) {
        this.scId = scIDString;
    }

    public String getScUsername() {
        return scUsername;
    }

    public void setScUsername(String scUsername) {
        this.scUsername = scUsername;
    }
    
    public String getScPassword() {
        return scPassword;
    }

    public void setScPassword(String scPassword) {
        this.scPassword = scPassword;
    }
    
    public String getScGivenName() {
        return scGivenName;
    }

    public void setScGivenName(String scGivenName) {
        this.scGivenName = scGivenName;
    }
    
    public String getScSurName() {
        return scSurName;
    }

    public void setScSurName(String scSurName) {
        this.scSurName = scSurName;
    }

    public String getScFaculty() {
        return scFaculty;
    }

    public void setScFaculty(String scFaculty) {
        this.scFaculty = scFaculty;
    }

    public String getScEmailAdress() {
        return scEmailAdress;
    }

    public void setScEmailAdress(String scEmailAdress) {
        this.scEmailAdress = scEmailAdress;
    }
    
    public String getScGender() {
        return scGender;
    }

    public void setScGender(String scGender) {
        this.scGender = scGender;
    }
    
    public String getScPhoneNumber() {
        return scPhoneNumber;
    }

    public void setScPhoneNumber(String scPhoneNumber) {
        this.scPhoneNumber = scPhoneNumber;
    }
}
