package managers;

public class CurrencyManager {
    public static final int NUM_CHARACTERS = 0; //currency abbreviation must be exactly 3 characters
    public static final int NOT_NUMBER = 1;
    public static final int OK = 2;


    private DatabaseManager databaseManager;

    public CurrencyManager(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }


    public int addCurrency(String currency, String balance){
        //boolean res = true;
        if(currency.length() != 3)
          return NUM_CHARACTERS;
        else{
            int amount = 0;
            String[] tokens = balance.split("\\.");
            for(String token : tokens){
                try {
                    int a = Integer.parseInt(token);
                }
                catch (NumberFormatException e){
                    return NOT_NUMBER;
                }
            }
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


            return OK;
        }

    }

}
