package managers;

import entities.Balance;
import managers.interfaces.IBalanceManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Class that handles operations tied with balances.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 *
 * */


public class BalanceManager  implements IBalanceManager {
    /**
     * enables indirect communication with the database
     * */
    private final DatabaseManager databaseManager;

    public BalanceManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    /**
     * Function that fetches latest balance for the desired currency.
     * @param currency  string whose balance is wanted
     * @return amount for desired currency
     * */
    @Override
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

    /**
     * Function that adds balance in balances history.
     * @param currency  currency
     * @param amount  amount of the currency
     * @return indicator whether the balance was successfully added
     * */
    @Override
    public boolean addBalance(String currency, int amount){
        return this.databaseManager.addBalance(currency, amount);
    }


    /**
     * Function that updates currenct balance.
     * @param currency  currency
     * @param amount  new amount for the desired currency
     * @return indicator whether the balance was successfully updated
     * */
    @Override
    public boolean updateCurrentBalance(String currency, int amount){
        return this.databaseManager.updateCurrentBalance(currency, amount);
    }

    /**
     * Function that returns list of balances that fulfill the conditions.
     * @param  currency  desired currency
     * @param  fromDate  string representation of the date which is starting date of time span
     * @param toDate  string representation of the date which is ending date of time span
     * @return list of balances
     * */
    @Override
    public ArrayList<Balance> getBalances(String currency, String fromDate, String toDate){
        ArrayList<Balance> balances = new ArrayList<>();
        if(fromDate.equalsIgnoreCase("") || toDate.equalsIgnoreCase(""))
            return null;
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
