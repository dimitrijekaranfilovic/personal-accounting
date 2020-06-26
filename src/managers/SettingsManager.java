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
    /**
     * enables indirect communication with the database
     * */
    private DatabaseManager databaseManager;
    /**
     * frame's x coordinate
     * */
    public int x;
    /**
     * frame's y coordinate
     * */
    public int y;
    /**
     * frame's current lookAndFeel
     * */
    public String style = "nimbus";
    /**
     * current language
     * */
    public String currentLanguage = "en";
    /**
     * bundle to be used to fetch words
     * */
    public ResourceBundle bundle;
    /**
     * list of observers
     * */
    private List<Observer> observers;
    /**
     * map of locales(key: locale abbreviation, value: locale)
     * */
    private HashMap<String, Locale> localeHashMap;
    /**
     * relative path to properties files
     * */
    private String basePath = "languages/words";

    /**
     * Class constructor. Initializes map of locales and active ResourceBundle.
     * @param databaseManager DatabaseManager : database manager
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
     * @param lookAndFeel  current LookAndFeel
     * @param  x  frame's x coordinate
     * @param y  frame's y coordinate
     * @param  language  current language
     * */
    public void saveSettings(String lookAndFeel, int x, int y, String language){
        this.databaseManager.saveSettings(lookAndFeel, x, y, language);
    }
    /**
     * Function that saves initial settings.
     * @param lookAndFeel  initial LookAndFeel
     * @param x  frame's initial x coordinate
     * @param y  frame's initial y coordinate
     * @param language  initial language
     * */
    public void addInitialSettings(String lookAndFeel, int x, int y, String language){
        this.databaseManager.addInitialSettings(lookAndFeel, x, y, language);
    }

    /**
     * Function that updates app's language. {@link SettingsManager#notifyObservers()} is called.
     * @param language  language's code
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
     * @param key  word's key in the resource file from languages package.
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
