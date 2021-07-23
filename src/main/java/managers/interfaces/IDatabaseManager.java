package managers.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public interface IDatabaseManager {

    void getConnection() throws ClassNotFoundException, SQLException;
    void initialize() throws SQLException;
    ResultSet getLatestBalance(String currency);
    boolean addBalance(String currency, int amount);
    boolean addCurrentBalance(String currency, int amount);
    boolean updateCurrentBalance(String currency, int amount);
    ResultSet getBalances(String currency, LocalDateTime from, LocalDateTime to);
    ResultSet getCurrencies();
    ResultSet countCurrencies();
    boolean addCurrency(String abbreviation);
    ResultSet getActivitiesDescriptions();
    boolean addActivity(String description, int amount, String currency, String activity, LocalDateTime date);
    ResultSet getActivities(String activity, LocalDateTime from, LocalDateTime to, String currency, String description);
    ResultSet groupActivities(String activity, LocalDateTime from, LocalDateTime to, String currency, String description);
    ResultSet loadSettings();
    boolean addInitialSettings(String lookAndFeel, int x, int y, String language);
    boolean saveSettings(String lookAndFeel, int x, int y, String language);
    ResultSet getAllBalances() throws SQLException, ClassNotFoundException;
    ResultSet getAllActivities() throws SQLException, ClassNotFoundException;
    }
