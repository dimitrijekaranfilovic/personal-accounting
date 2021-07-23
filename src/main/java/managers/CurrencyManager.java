package managers;

import entities.Publisher;
import event.Observer;
import event.UpdateEvent;
import managers.interfaces.ICurrencyManager;
import util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles operations tied with currencies.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */


public class CurrencyManager implements Publisher, ICurrencyManager {
    /**
     * indicates that currency abbreviation to be added doesn't consist of 3 characters
     * */
    public static final int NUM_CHARACTERS = 0;
    /**
     * indicates that balance amount is not a number
     * */
    public static final int NOT_NUMBER = 1;
    /**
     * indicates that currency abbreviation contains numbers
     * */
    public static final int NOT_CHARACTER = 2;
    /**
     * indicates that adding the currency was successful
     * */
    public static final int OK = 3;
    /**
     * indicates that adding the currency failed
     * */
    public static final int WRONG = 4;

    /**
     * list of observers
     * */
    private List<Observer> observers;
    /**
     * enables indirect communication with the database
     * */
    private final DatabaseManager databaseManager;

    /**
     * Class constructor.
     * @param databaseManager  parameter for databaseManager field.
     * */
    public CurrencyManager(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }

    /**
     * Function that adds new currency. {@link CurrencyManager#notifyObservers()} is called
     * after currency is successfully added.
     * @param currency  currency abbreviation
     * @param balance string representation of the currency's balance
     * @return number which indicates which message will be displayed.
     * */
    @Override
    public int addCurrency(String currency, String balance){
        //boolean res = true;
        if(currency.length() != 3)
          return NUM_CHARACTERS;
        else{
            if(currency.matches(".*\\d.*"))
                return NOT_CHARACTER;
            if(Util.isNotNumeric(balance))
                return NOT_NUMBER;
            int amount = Util.parseString(balance);

           if(this.databaseManager.addCurrency(currency) && this.databaseManager.addBalance(currency, amount) && this.databaseManager.addCurrentBalance(currency, amount)){
               notifyObservers();
               return OK;
           }
           else{
               return WRONG;
           }
        }

    }

    /**
     * Function that counts how many currencies are there.
     * @throws SQLException if counting doesn't go like intended
     * @return number of currencies
     * */
    @Override
    public int countCurrencies() throws SQLException {
        ResultSet rs = this.databaseManager.countCurrencies();
        if(rs == null)
            return 0;
        else{
            return rs.getInt("num");
        }

    }

    /**
     * Function that returns list of currencies.
     * @return list of currencies
     * */
    public ArrayList<String> getCurrencies() {
            ResultSet rs = this.databaseManager.getCurrencies();
            if(rs == null)
                return null;
            else{
                ArrayList<String> currencies = new ArrayList<>();
                try{
                    while(rs.next()){
                        currencies.add(rs.getString("abbreviation"));
                    }
                }
                catch (SQLException e){
                    return null;
                }

                return currencies;
            }
    }


    @Override
    public void addObserver(Observer observer) {
        if (null == observers)
            observers = new ArrayList<>();
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        if (null == observers)
            return;
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        UpdateEvent e = new UpdateEvent(this);
        for (Observer observer : observers) {
            observer.updatePerformed(e);
        }
    }
}
