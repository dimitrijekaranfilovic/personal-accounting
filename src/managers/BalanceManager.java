package managers;

//import entities.Balance;

import entities.Balance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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

    public ArrayList<Balance> getBalances(String currency, String fromDate, String toDate){
        ArrayList<Balance> balances = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        LocalDateTime from = LocalDateTime.parse(fromDate + " 00:01", dateTimeFormatter);
        LocalDateTime to = LocalDateTime.parse(toDate + " 23:59", dateTimeFormatter);
        ResultSet resultSet = this.databaseManager.getBalances(currency, from, to);
        if(resultSet == null)
            return null;
        else{
            try{
                while(resultSet.next()){
                    balances.add(new Balance(resultSet.getTimestamp("time").toLocalDateTime(), resultSet.getString("currency"), resultSet.getInt("amount")));
                }
            }
            catch (SQLException e) {
                return null;
            }
        }
        return balances;
    }
}
