package managers;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingsManager {
    private DatabaseManager databaseManager;
    public int x;
    public int y;
    public String style;


    public SettingsManager(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }


    public boolean loadSettings(){
        ResultSet rs = this.databaseManager.loadSettings();
        try {
            this.style = rs.getString("style");
            this.x = rs.getInt("lastX");
            this.y = rs.getInt("lastY");
            //System.out.println("Ucitao sam " + this.style);
            return true;
        } catch (SQLException e) {
            //e.printStackTrace();
            return false;
        }

    }


    public void saveSettings(String lookAndFeel, int x, int y){
        //System.out.println("Cuvam " + lookAndFeel);
        this.databaseManager.saveSettings(lookAndFeel, x, y);
    }

    public void addInitialSettings(String lookAndFeel, int x, int y){
        //System.out.println("Inicijalna podesavanja: " + x + "," + y);
        this.databaseManager.addInitialSettings(lookAndFeel, x, y);
    }


}
