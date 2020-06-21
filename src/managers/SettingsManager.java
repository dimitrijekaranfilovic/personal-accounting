package managers;

import entities.Publisher;
import event.Observer;
import event.UpdateEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class SettingsManager implements Publisher {
    private DatabaseManager databaseManager;
    public int x;
    public int y;
    public String style = "nimbus";
    public String currentLanguage = "en";
    public Locale currentLocale;
    public ResourceBundle bundle;
    private List<Observer> observers;


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
            //System.out.println(this.currentLanguage);
            if(this.currentLanguage == null)
                this.currentLanguage = "en";
            this.currentLocale = new Locale(this.currentLanguage, this.currentLanguage.toUpperCase());
            this.bundle = ResourceBundle.getBundle("languages/words", this.currentLocale);
            //System.out.println(this.bundle == null);
            //System.out.println("Ucitao sam \nkoordinate" + this.x + "," + this.y + "\nstil: " + this.style + "\njezik: " + this.currentLanguage);
            notifyObservers();
            return true;
        } catch (SQLException e) {
            //e.printStackTrace();
            return false;
        }

    }


    public void saveSettings(String lookAndFeel, int x, int y, String language){
        //System.out.println("Ucitao sam \nkoordinate" + this.x + "," + this.y + "\nstil: " + this.style + "\njezik: " + this.currentLanguage);
        this.databaseManager.saveSettings(lookAndFeel, x, y, language);
        //System.out.println("Cuvam " + this.currentLanguage);
    }

    public void addInitialSettings(String lookAndFeel, int x, int y){
        //System.out.println("Inicijalna podesavanja: " + x + "," + y);
        this.databaseManager.addInitialSettings(lookAndFeel, x, y);
    }

    public void updateLocale(String language){
        String abbreviation = null;
        System.out.println("Proslijedjeno: " + language + " Trenutni engleski: " + this.getWord("english"));
        System.out.println("Proslijedjeno: " + language + " Trenutni srpski: " + this.getWord("serbian"));

        if(language.equalsIgnoreCase(this.getWord("serbian"))) {
            abbreviation = "srb";
            //System.out.println("Mijenjam abbreviation u srb");
        }
        else if(language.equalsIgnoreCase(this.getWord("english"))){
            System.out.println("Mijenjam abbreviation u en");
            //abbreviation = "en";
        }

        this.currentLanguage = abbreviation;
        this.currentLocale = new Locale(language, language.toUpperCase());
        this.bundle = ResourceBundle.getBundle("languages/words", this.currentLocale);
        //System.out.println("Current abbreviation: " + this.currentLanguage);
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
