package managers;

import entities.Publisher;
import event.Observer;
import event.UpdateEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SettingsManager implements Publisher {
    private DatabaseManager databaseManager;
    public int x;
    public int y;
    public String style = "nimbus";
    public String currentLanguage = "en";
    public ResourceBundle bundle;
    private List<Observer> observers;
    private HashMap<String, Locale> localeHashMap;


    public SettingsManager(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
        this.localeHashMap = new HashMap<>();
        this.localeHashMap.put("en", new Locale("en", "EN"));
        this.localeHashMap.put("srb", new Locale("srb", "SRB"));
        this.localeHashMap.put("srb_CYR", new Locale("srb_CYR", "SRB_CYR"));
        this.bundle = ResourceBundle.getBundle("languages/words",this.localeHashMap.get("en"));
    }


    public boolean loadSettings(){
        ResultSet rs = this.databaseManager.loadSettings();
        try {
            this.style = rs.getString("style");
            this.x = rs.getInt("lastX");
            this.y = rs.getInt("lastY");
            this.currentLanguage = rs.getString("language");
            this.bundle = ResourceBundle.getBundle("languages/words", this.localeHashMap.get(this.currentLanguage));
            notifyObservers();
            return true;
        } catch (SQLException e) {
            return false;
        }

    }


    public void saveSettings(String lookAndFeel, int x, int y, String language){
        this.databaseManager.saveSettings(lookAndFeel, x, y, language);
    }

    public void addInitialSettings(String lookAndFeel, int x, int y){
        this.databaseManager.addInitialSettings(lookAndFeel, x, y);
    }

    public void updateLocale(String language){
        String abbreviation = null;
        if(language.equalsIgnoreCase(this.getWord("serbian")))
            abbreviation = "srb";
        else if(language.equalsIgnoreCase(this.getWord("english")))
            abbreviation = "en";
        else if(language.equalsIgnoreCase(this.getWord("serbian_cyrillic")))
            abbreviation = "srb_CYR";
        this.currentLanguage = abbreviation;
        this.bundle = ResourceBundle.getBundle("languages/words", this.localeHashMap.get(this.currentLanguage));
        notifyObservers();
    }

    public String getWord(String key){
        return this.bundle.getString(key);
    }

    @Override
    public void addObserver(Observer observer) {
        if (null == observers)
            observers = new ArrayList<>();
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        if (null == observers)
            return;
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        UpdateEvent e = new UpdateEvent(this);
        for (Observer observer : observers) {
            observer.updatePerformed(e);
        }
    }
}
