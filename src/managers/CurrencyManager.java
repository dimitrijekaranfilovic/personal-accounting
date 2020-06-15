package managers;

import main.Main;

import java.awt.image.WritableRenderedImage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CurrencyManager {
    public static final int NUM_CHARACTERS = 0; //currency abbreviation must be exactly 3 characters
    public static final int NOT_NUMBER = 1;
    public static final int NOT_CHARACTER = 2;
    public static final int OK = 3;
    public static final int WRONG = 4;


    private DatabaseManager databaseManager;

    public CurrencyManager(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }


    public int addCurrency(String currency, String balance){
        //boolean res = true;
        if(currency.length() != 3)
          return NUM_CHARACTERS;
        else{
            if(currency.matches(".*\\d.*"))
                return NOT_CHARACTER;
            if(!Main.isNumeric(balance))
                return NOT_NUMBER;
            int amount = 0;
            String[] tokens = balance.split("\\.");
            amount += 100 * Integer.parseInt(tokens[0]);
            if(tokens.length == 2){
                String parts = tokens[1];
                if(parts.length() == 1){
                    amount += Integer.parseInt(parts) * 10;
                }
                else if(parts.length() == 2){
                    amount += Integer.parseInt(parts);
                }
            }

           if(this.databaseManager.addCurrency(currency) && this.databaseManager.addBalance(currency, amount)){
               return OK;
           }
           else{
               return WRONG;
           }
        }

    }

    public int countCurrencies() throws SQLException {
        ResultSet rs = this.databaseManager.countCurrencies();
        if(rs == null)
            return 0;
        else{
            return rs.getInt("num");
        }

    }

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
}
