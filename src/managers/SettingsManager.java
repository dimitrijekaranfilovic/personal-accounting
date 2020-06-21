package managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class SettingsManager {
    private DatabaseManager databaseManager;
    public int x;
    public int y;
    public String style = "nimbus";
    public String currentLanguage = "en";
    public Locale currentLocale;
    public ResourceBundle bundle;


    public SettingsManager(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
        this.currentLocale = new Locale(this.currentLanguage, this.currentLanguage.toUpperCase());
        this.bundle = ResourceBundle.getBundle("languages/words",this.currentLocale);
    }


    public boolean loadSettings(){
        ResultSet rs = this.databaseManager.loadSettings();
        try {
            this.style = rs.getString("style");
            this.x = rs.getInt("lastX");
            this.y = rs.getInt("lastY");
            this.currentLanguage = rs.getString("language");
            System.out.println(this.x);
            this.currentLocale = new Locale(this.currentLanguage, this.currentLanguage.toUpperCase());
            this.bundle = ResourceBundle.getBundle("languages/words", this.currentLocale);
            System.out.println(this.bundle == null);
            //System.out.println("Ucitao sam \nkoordinate" + this.x + "," + this.y + "\nstil: " + this.style + "\njezik: " + this.currentLanguage);
            return true;
        } catch (SQLException e) {
            //e.printStackTrace();
            return false;
        }

    }


    public void saveSettings(String lookAndFeel, int x, int y, String language){
        //System.out.println("Ucitao sam \nkoordinate" + this.x + "," + this.y + "\nstil: " + this.style + "\njezik: " + this.currentLanguage);
        this.databaseManager.saveSettings(lookAndFeel, x, y, language);
    }

    public void addInitialSettings(String lookAndFeel, int x, int y){
        //System.out.println("Inicijalna podesavanja: " + x + "," + y);
        this.databaseManager.addInitialSettings(lookAndFeel, x, y);
    }

    public void updateLocale(String language){
        String abbreviation = null;
        if(language.equalsIgnoreCase(this.getWord("serbian")))
            abbreviation = "srb";
        else if(language.equalsIgnoreCase(this.getWord("english")))
            abbreviation = "en";

        this.currentLanguage = abbreviation;
        this.currentLocale = new Locale(language, language.toUpperCase());
        this.bundle = ResourceBundle.getBundle("languages/words", this.currentLocale);
    }


    public String getWord(String key){
        //System.out.println(this.bundle == null);
        return this.bundle.getString(key);
    }

}
