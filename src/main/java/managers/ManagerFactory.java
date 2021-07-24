package managers;

import java.sql.SQLException;

/**
 * Wrapper class for managers. Managers aren't created one by one separately,
 * All existing managers all this class' fields.
 * they are created together.
 * @author Dimitrije Kaanfilovic
 * @since 22.06.2020.
 * */


public class ManagerFactory {
    /**
     * parameter for all other managers
     * */
    private static DatabaseManager databaseManager = null;
    private static CurrencyManager currencyManager = null;
    private static BalanceManager balanceManager = null;
    private static ActivityManager activityManager = null;
    private static ResourceManager resourceManager = null;
    private static LookAndFeelManager lookAndFeelManager = null;
    private static SettingsManager settingsManager = null;

    public static DatabaseManager createDatabaseManager() throws SQLException, ClassNotFoundException {
        if(databaseManager == null){
            databaseManager = new DatabaseManager();
            databaseManager.getConnection();
        }
        return databaseManager;
    }

    public static CurrencyManager createCurrencyManager() throws SQLException, ClassNotFoundException {
        if(currencyManager == null){
            currencyManager = new CurrencyManager(createDatabaseManager());
        }
        return currencyManager;
    }

    public static BalanceManager createBalanceManager() throws SQLException, ClassNotFoundException {
        if(balanceManager == null){
            balanceManager = new BalanceManager(createDatabaseManager());
        }
        return balanceManager;
    }

    public static ActivityManager createActivityManager() throws SQLException, ClassNotFoundException{
        if(activityManager == null){
            activityManager = new ActivityManager(createDatabaseManager());
        }
        return activityManager;
    }

    public static ResourceManager createResourceManager(){
        if(resourceManager == null){
            resourceManager = new ResourceManager();
        }
        return resourceManager;
    }

    public static LookAndFeelManager createLookAndFeelManager(){
        if(lookAndFeelManager == null){
            lookAndFeelManager = new LookAndFeelManager();
        }
        return lookAndFeelManager;
    }

    public static SettingsManager createSettingsManager() throws SQLException, ClassNotFoundException {
        if(settingsManager == null){
            settingsManager = new SettingsManager(createDatabaseManager());
        }
        return settingsManager;
    }



}
