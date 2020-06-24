package managers;

import entities.Publisher;
import event.Observer;
import event.UpdateEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Class that handles all settings relevant for the app.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */


public class SettingsManager implements Publisher {
    private DatabaseManager databaseManager;
    public int x;
    public int y;
    public String style = "nimbus";
    public String currentLanguage = "en";
    public ResourceBundle bundle;
    private List<Observer> observers;
    private HashMap<String, Locale> localeHashMap;
    private String basePath = "languages/words";

    /**
     * Class constructor. Initializes map of locales and active ResourceBundle.
     * */
    public SettingsManager(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
        this.localeHashMap = new HashMap<>();
        this.localeHashMap.put("en", new Locale("en", "EN"));
        this.localeHashMap.put("srb", Locale.forLanguageTag("sr-Latn-RS"));
        this.localeHashMap.put("srb_CYR", new Locale("srb_CYR", "SRB_CYR"));
        this.bundle = ResourceBundle.getBundle(basePath,this.localeHashMap.get(this.currentLanguage));
        Locale.setDefault(this.localeHashMap.get(this.currentLanguage));
    }


    /**
     * Function that loads all relevant settings. {@link SettingsManager#notifyObservers()} is called
     * if the loading was successful
     * @return indicator whether the loading was successful
     * */
    public boolean loadSettings(){
        ResultSet rs = this.databaseManager.loadSettings();
        try {
            this.style = rs.getString("style");
            this.x = rs.getInt("lastX");
            this.y = rs.getInt("lastY");
            this.currentLanguage = rs.getString("language");
            this.bundle = ResourceBundle.getBundle(basePath, this.localeHashMap.get(this.currentLanguage));
            Locale.setDefault(this.localeHashMap.get(this.currentLanguage));
            notifyObservers();
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    /**
     * Function that saves settings.
     * @param lookAndFeel String : current LookAndFeel
     * @param  x int : frame's x coordinate
     * @param y int : frame's y coordinate
     * @param  language String : current language
     * */
    public void saveSettings(String lookAndFeel, int x, int y, String language){
        this.databaseManager.saveSettings(lookAndFeel, x, y, language);
    }
    /**
     * Function that saves initial settings.
     * @param lookAndFeel String : initial LookAndFeel
     * @param x int : frame's initial x coordinate
     * @param y int : frame's initial y coordinate
     * */
    public void addInitialSettings(String lookAndFeel, int x, int y, String language){
        this.databaseManager.addInitialSettings(lookAndFeel, x, y, language);
    }

    /**
     * Function that updates app's language. {@link SettingsManager#notifyObservers()} is called.
     * @param language String : languages code
     * */
    public void updateLocale(String language){
        String abbreviation = null;
        if(language.equalsIgnoreCase(this.getWord("serbian")))
            abbreviation = "srb";
        else if(language.equalsIgnoreCase(this.getWord("english")))
            abbreviation = "en";
        else if(language.equalsIgnoreCase(this.getWord("serbian_cyrillic")))
            abbreviation = "srb_CYR";
        this.currentLanguage = abbreviation;
        this.bundle = ResourceBundle.getBundle(basePath, this.localeHashMap.get(this.currentLanguage));
        Locale.setDefault(this.localeHashMap.get(this.currentLanguage));
        notifyObservers();
    }

    /**
     * Function that fetches word from the current language's resource file.
     * @param key String : word's key in the resource file from {@link languages}.
     * @return desired word
     * */
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
