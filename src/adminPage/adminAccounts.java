package adminPage;

public class adminAccounts {
    private String adminUsername;
    private String adminPassword;

    public adminAccounts() {
        this.adminUsername = null;
        this.adminPassword = null;
    }

    public adminAccounts(String username, String password) {
        this.adminUsername = username;
        this.adminPassword = password;
    }
    
    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
