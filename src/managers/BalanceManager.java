package managers;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BalanceManager  {
    private DatabaseManager databaseManager;

    public BalanceManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    /*public int getBalance(String currency){
        ResultSet rs = this.databaseManager.getBalance(currency);
        if(rs == null)
            return 0;
        else{
            int balance = 0;
            try {
                balance = rs.getInt("amount");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return balance;
        }
    }*/

    public int getLatestBalance(String currency){
        ResultSet rs = this.databaseManager.getLatestBalance(currency);
        if(rs == null)
            return 0;
        else{
            int balance = 0;
            try {
                balance = rs.getInt("amount");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return balance;
        }
    }

    public boolean addBalance(String currency, int amount){
        return this.databaseManager.addBalance(currency, amount);
    }

    public boolean updateCurrentBalance(String currency, int amount){
        return this.databaseManager.updateCurrentBalance(currency, amount);
    }

}
