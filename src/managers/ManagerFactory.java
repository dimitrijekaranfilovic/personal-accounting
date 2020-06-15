package managers;

import entities.Balance;

public class ManagerFactory {
    public DatabaseManager databaseManager;
    public CurrencyManager currencyManager;
    public BalanceManager balanceManager;


    public ManagerFactory(){
        this.databaseManager = new DatabaseManager();
        this.currencyManager = new CurrencyManager(this.databaseManager);
        this.balanceManager = new BalanceManager(this.databaseManager);

    }

}
