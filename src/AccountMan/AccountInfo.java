package AccountMan;

public class AccountInfo{
    public AccountInfo(){};
    AccountInfo(String userName, String password, String aPIKey, String server, String sessionID, String sessionDate, Boolean defaultAccount){
        this.userName = userName;
        this.password = password;
        this.aPIKey = aPIKey;
        this.server = server;
        this.sessionID = sessionID;
        this.sessionDate = sessionDate;
        this.defaultAccount = defaultAccount;
    }
    public String userName, password, aPIKey, server, sessionID, sessionDate;
    public Boolean defaultAccount;
}
