package managers;

import java.sql.SQLException;

/**
 * Wrapper class for managers. Managers aren't created one by one separately,
 * they are created together.
 * @author Dimitrije Kaanfilovic
 * @since 22.06.2020.
 * */


public class ManagerFactory {
    public DatabaseManager databaseManager;
    public CurrencyManager currencyManager;
    public BalanceManager balanceManager;
    public ActivityManager activityManager;
    public ResourceManager resourceManager;
    public LookAndFeelManager lookAndFeelManager;
    public SettingsManager settingsManager;


    /**
     * Class constructor which initializes all managers.
     * */
    public ManagerFactory() throws SQLException, ClassNotFoundException {
        this.databaseManager = new DatabaseManager();
        this.databaseManager.getConnection();
        this.currencyManager = new CurrencyManager(this.databaseManager);
        this.balanceManager = new BalanceManager(this.databaseManager);
        this.activityManager = new ActivityManager(this.databaseManager);
        this.resourceManager = new ResourceManager();
        this.lookAndFeelManager = new LookAndFeelManager();
        this.settingsManager = new SettingsManager(this.databaseManager);
    }

}
