package managers;

import util.Util;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Class that directly communicates with the database.
 *
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 */


public class DatabaseManager {
    /**
     * currently established connection
     */
    private Connection connection;
    /**
     * flag that indicates whether tables have been created
     */
    public boolean hasData = false;

    public final boolean useTestDb = true;

    /**
     * Function that establishes a connection wih the database.
     *
     * @throws ClassNotFoundException if the driver cannot be found
     * @throws SQLException           if the tables cannot be created
     */
    public void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        String home = System.getProperty("user.home");
        String separator = System.getProperty("file.separator");
        String db = this.useTestDb ? "pa-test-db.db" : "personal-accounting-database.db";
        String url = home + separator + db;

        connection = DriverManager.getConnection("jdbc:sqlite:" + url);
        initialize();
    }


    //test function for the database
    public ResultSet display() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            getConnection();
        }
        Statement statement = connection.createStatement();
        return statement.executeQuery("select * from activities;");
    }

    //creates tables if there are none

    /**
     * Function that creates tables in the database if there are none.
     *
     * @throws SQLException if the tables cannot be created
     */
    private void initialize() throws SQLException {
        if (!hasData) {
            hasData = true;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select name from sqlite_master where type='table' and name='activities'");
            if (!resultSet.next()) {
                Statement createTables = connection.createStatement();

                //build 'activities' table
                createTables.execute("create table activities(" +
                        "description varchar(40)," +
                        "time Date," +
                        "amount integer," +
                        "currency varchar(3)," +
                        "activity varchar(10)," +
                        "constraint actFK foreign key (currency) references currency(abbreviation)" +
                        "" +
                        ");");


                //build 'balances' table
                createTables.execute("create table balances(" +
                        "time Date," +
                        "currency varchar(3)," +
                        "amount integer," +
                        "constraint balanceFK1 foreign key (currency) references currencies(abbreviation)," +
                        "constraint balancePK primary key (time, amount, currency)" +
                        ");");

                //build 'currencies' table
                createTables.execute("create table currencies(" +
                        "abbreviation varchar(3)," +
                        "constraint currPK primary key (abbreviation)" +
                        ");");

                //build the table that holds current balances
                createTables.execute("create table currentBalances(" +
                        "currency varchar(3) primary key," +
                        "amount integer," +
                        "constraint cbFK foreign key(currency) references currency(abbreviation)" +
                        ");");

                //build the table that holds app settings(lookAndFeel, last position)
                createTables.execute("create table settings(" +
                        "style varchar(20)," +
                        "lastX integer," +
                        "lastY integer," +
                        "language varchar(15)" +
                        ");");

            }
        }
    }

    /**
     * Function that returns the latest balance of the desired currency from the currentBalances table
     *
     * @param currency desired currency
     * @return ResultSet of the prepared statement
     */
    ResultSet getLatestBalance(String currency) {
        try {
            if (connection == null) {
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("select amount from currentBalances where currency=?");
            ps.setString(1, currency);
            ps.execute();
            return ps.getResultSet();
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }

    }

    /**
     * Function that adds balance in the balances table.
     *
     * @param currency currency whose balance it is
     * @param amount   balance amount
     * @return indicator whether adding was successful
     */
    boolean addBalance(String currency, int amount) {
        try {
            if (connection == null) {
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("insert into balances(time, currency, amount) values(?, ?, ?);");
            Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
            ps.setTimestamp(1, ts);
            ps.setString(2, currency);
            ps.setInt(3, amount);
            ps.execute();
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Function that adds current balance in the currentBalances table.
     *
     * @param currency currency whose balance it is
     * @param amount   balance amount
     * @return indicator whether adding was successful
     */
    boolean addCurrentBalance(String currency, int amount) {
        try {
            if (connection == null) {
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("insert into currentBalances(currency, amount) values(?, ?);");
            //Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
            //ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(1, currency);
            ps.setInt(2, amount);
            ps.execute();
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }

    }

    /**
     * Function that updates current balance.
     *
     * @param currency currency which is to be updated
     * @param amount   balance amount
     * @return indicator whether adding was successful
     */
    boolean updateCurrentBalance(String currency, int amount) {
        try {
            if (connection == null) {
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("update currentBalances set amount=? where currency=?;");
            //Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
            ps.setInt(1, amount);
            ps.setString(2, currency);
            ps.execute();
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Functions that fetches balances that fulfill the conditions.
     *
     * @param currency currency whose balances are to be fetched
     * @param from     starting date
     * @param to       ending date
     * @return ResultSet of the prepared statement
     */
    ResultSet getBalances(String currency, LocalDateTime from, LocalDateTime to) {
        try {
            if (connection == null) {
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("select * from balances where time between ? and ? and currency like ? escape '!';");

            currency = Util.formatStringForQuery(currency);

            ps.setTimestamp(1, Timestamp.valueOf(from));
            ps.setTimestamp(2, Timestamp.valueOf(to));
            ps.setString(3, "%" + currency + "%");
            ps.execute();
            return ps.getResultSet();

        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Function that fetches all currencies.
     *
     * @return ResultSet of the prepared statement.
     */
    ResultSet getCurrencies() {
        try {
            if (connection == null) {
                getConnection();
            }
            Statement statement = connection.createStatement();
            return statement.executeQuery("select abbreviation from currencies;");
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Function that counts how many currencies there are.
     *
     * @return ResultSet of the prepared statement
     */
    ResultSet countCurrencies() {
        try {
            if (connection == null) {
                getConnection();
            }
            Statement statement = connection.createStatement();
            return statement.executeQuery("select count(abbreviation) as num from currencies;");
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }


    /**
     * Function that adds new currency in the currencies table.
     *
     * @param abbreviation currency abbreviation
     * @return indicator whether the currency was successfully added/
     */
    boolean addCurrency(String abbreviation) {
        try {
            if (connection == null) {
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("insert into currencies(abbreviation) values(?);");
            ps.setString(1, abbreviation);
            ps.execute();

            return true;

        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Function that fetches all activities' descriptions.
     *
     * @return ResultSet of the prepared statement.
     */
    ResultSet getActivitiesDescriptions() {
        try {
            if (connection == null) {
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("select description from activities;");
            ps.execute();
            return ps.getResultSet();

        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Function that adds a new activity in the activities table.
     *
     * @param description activity description
     * @param amount      activity amount
     * @param currency    activity currency
     * @param activity    activity version
     * @param date        date and time of the activity
     * @return indicator whether the adding was successful
     */
    boolean addActivity(String description, int amount, String currency, String activity, LocalDateTime date) {
        try {
            if (connection == null) {
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("insert into activities(description, amount, currency, activity, time) values(?, ?, ?, ?, ?);");
            ps.setString(1, description);
            ps.setInt(2, amount);
            ps.setString(3, currency);
            ps.setString(4, activity);
            ps.setTimestamp(5, Timestamp.valueOf(date));
            ps.execute();
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }


    /**
     * Function that fetches activities that meet the conditions.
     *
     * @param activity    activity version
     * @param from        starting date and time
     * @param to          ending date and time
     * @param currency    currency
     * @param description activity description
     * @return ResultSet of the prepared statement
     */
    ResultSet getActivities(String activity, LocalDateTime from, LocalDateTime to, String currency, String description) {
        try {
            if (connection == null) {
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("select * from activities where time between ? and ? and activity like ? escape '!' and currency like ? escape '!' and description like ? escape '!';");
            activity = Util.formatStringForQuery(activity);
            currency = Util.formatStringForQuery(currency);
            description = Util.formatStringForQuery(description);

            ps.setTimestamp(1, Timestamp.valueOf(from));
            ps.setTimestamp(2, Timestamp.valueOf(to));
            ps.setString(3, "%" + activity + "%");
            ps.setString(4, "%" + currency + "%");
            ps.setString(5, "%" + description + "%");
            ps.execute();
            return ps.getResultSet();

        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Function that groups activities that meet the conditions.
     *
     * @param activity    activity version
     * @param from        starting date and time
     * @param to          ending date and time
     * @param currency    currency
     * @param description activity description
     * @return ResultSet of the prepared statement
     */
    ResultSet groupActivities(String activity, LocalDateTime from, LocalDateTime to, String currency, String description) {
        try {
            if (connection == null) {
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("select description, sum(amount) as am from activities where time between ? and ? and activity like ? escape '!' and currency like ? escape '!' and description like ? escape '!' group by description;");
            activity = Util.formatStringForQuery(activity);
            currency = Util.formatStringForQuery(currency);
            description = Util.formatStringForQuery(description);

            ps.setTimestamp(1, Timestamp.valueOf(from));
            ps.setTimestamp(2, Timestamp.valueOf(to));
            ps.setString(3, "%" + activity + "%");
            ps.setString(4, "%" + currency + "%");
            ps.setString(5, "%" + description + "%");
            ps.execute();
            return ps.getResultSet();

        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }

    }

    /**
     * Function that load all necessary settings.
     *
     * @return ResultSet of the prepared statement.
     */
    ResultSet loadSettings() {
        try {
            if (connection == null) {
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("select * from settings;");
            ps.execute();
            return ps.getResultSet();

        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Function that adds initial settings.
     *
     * @param lookAndFeel initial LookAndFeel
     * @param x           frame's initial x coordinate
     * @param y           frame's initial y coordinate
     * @param language    initial language
     * @return indicator whether the adding was successful
     */
    boolean addInitialSettings(String lookAndFeel, int x, int y, String language) {
        try {
            if (connection == null) {
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("insert into settings(style, lastX, lastY, language) values(?,?,?,?);");
            ps.setString(1, lookAndFeel);
            ps.setInt(2, x);
            ps.setInt(3, y);
            ps.setString(4, language);
            ps.execute();
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }

    }

    /**
     * Function that saves current settings.
     *
     * @param lookAndFeel initial LookAndFeel
     * @param x           frame's last x coordinate
     * @param y           frame's last y coordinate
     * @param language    last used language
     * @return indicator whether the saving was successful
     */
    boolean saveSettings(String lookAndFeel, int x, int y, String language) {
        try {
            if (connection == null) {
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("update settings set style=?, lastX=?, lastY=?, language=?;");
            ps.setString(1, lookAndFeel);
            ps.setInt(2, x);
            ps.setInt(3, y);
            ps.setString(4, language);
            ps.execute();
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }

    }

    ResultSet getAllBalances() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            getConnection();
        }
        Statement statement = connection.createStatement();
        return statement.executeQuery("select * from balances;");
    }

    ResultSet getAllActivities() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            getConnection();
        }
        Statement statement = connection.createStatement();
        return statement.executeQuery("select * from activities;");
    }
}
