package managers;

import entities.Balance;

import java.sql.SQLException;

public class ManagerFactory {
    public DatabaseManager databaseManager;
    public CurrencyManager currencyManager;
    public BalanceManager balanceManager;
    public ActivityManager activityManager;


    public ManagerFactory() throws SQLException, ClassNotFoundException {
        this.databaseManager = new DatabaseManager();
        this.databaseManager.getConnection();
        this.currencyManager = new CurrencyManager(this.databaseManager);
        this.balanceManager = new BalanceManager(this.databaseManager);
        this.activityManager = new ActivityManager(this.databaseManager);
    }

}
