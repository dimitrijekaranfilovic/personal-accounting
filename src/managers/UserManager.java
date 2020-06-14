package managers;

public class UserManager {
    private DatabaseManager databaseManager;
    public static final int USERNAME_TAKEN = 0;
    public static final int FIELD_EMPTY = 1;
    public static final int OK = 2;
    public static final int WRONG = 3;


    public UserManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public int addUser(String username, char[] password){
        if(checkCredentials(username, password)) {
            if (this.databaseManager.addUser(username, new String(password))) {
                return OK;
            }
            else {
                return USERNAME_TAKEN;
            }
        }
        else {
            return FIELD_EMPTY;
        }
    }

    public int checkLogin(String username, char[] password){
        if(this.databaseManager.checkLogin(username, password)){
            return OK;
        }
        else{
            return WRONG;
        }
    }

    private  boolean checkCredentials(String username, char[] password){
        return !username.equalsIgnoreCase("") || password.length == 0;
    }
}
