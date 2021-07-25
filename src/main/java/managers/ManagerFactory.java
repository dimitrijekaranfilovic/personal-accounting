package managers;

import java.sql.SQLException;

/**
 * Factory of managers. Each manager is implemented as a singleton.
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


    /**
     * Creates database manager.
     * @return database manager
     * */

    public static DatabaseManager createDatabaseManager() throws SQLException, ClassNotFoundException {
        if(databaseManager == null){
            databaseManager = new DatabaseManager();
            databaseManager.getConnection();
        }
        return databaseManager;
    }


    /**
     * Creates currency manager.
     * @return currency manager
     * */

    public static CurrencyManager createCurrencyManager() throws SQLException, ClassNotFoundException {
        if(currencyManager == null){
            currencyManager = new CurrencyManager(createDatabaseManager());
        }
        return currencyManager;
    }


    /**
     * Creates balance manager.
     * @return balance manager
     * */

    public static BalanceManager createBalanceManager() throws SQLException, ClassNotFoundException {
        if(balanceManager == null){
            balanceManager = new BalanceManager(createDatabaseManager());
        }
        return balanceManager;
    }


    /**
     * Creates activity manager.
     * @return activity manager
     * */

    public static ActivityManager createActivityManager() throws SQLException, ClassNotFoundException{
        if(activityManager == null){
            activityManager = new ActivityManager(createDatabaseManager());
        }
        return activityManager;
    }


    /**
     * Creates resource manager.
     * @return resource manager
     * */

    public static ResourceManager createResourceManager(){
        if(resourceManager == null){
            resourceManager = new ResourceManager();
        }
        return resourceManager;
    }



    /**
     * Creates look and feel manager.
     * @return look and feel manager
     * */

    public static LookAndFeelManager createLookAndFeelManager(){
        if(lookAndFeelManager == null){
            lookAndFeelManager = new LookAndFeelManager();
        }
        return lookAndFeelManager;
    }


    /**
     * Creates settings manager.
     * @return settings manager
     * */

    public static SettingsManager createSettingsManager() throws SQLException, ClassNotFoundException {
        if(settingsManager == null){
            settingsManager = new SettingsManager(createDatabaseManager());
        }
        return settingsManager;
    }



}
