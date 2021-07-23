package managers.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ICurrencyManager {
    int addCurrency(String currency, String balance);
    int countCurrencies() throws SQLException;
    ArrayList<String> getCurrencies();

}
