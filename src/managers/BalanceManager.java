package managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.DoubleAccumulator;

public class BalanceManager  {
    private DatabaseManager databaseManager;

    public BalanceManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public int getBalance(String currency){
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
    }

}
