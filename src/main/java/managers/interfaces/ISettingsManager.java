package managers.interfaces;

public interface ISettingsManager {
    boolean loadSettings();
    void saveSettings(String lookAndFeel, int x, int y, String language);
    void addInitialSettings(String lookAndFeel, int x, int y, String language);
    void updateLocale(String language);
    String getWord(String key);

    }
