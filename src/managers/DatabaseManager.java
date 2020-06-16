package managers;

import java.sql.*;
import java.time.LocalDateTime;

public class DatabaseManager {
    private Connection connection;
    public boolean hasData = false;

    public void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        String url = System.getProperty("user.home") + System.getProperty("file.separator") + "personal-accounting-database.db";
        connection = DriverManager.getConnection("jdbc:sqlite:" + url);
        initialize();
    }


    //test function for the database
    public ResultSet display() throws SQLException, ClassNotFoundException {
        if(connection == null){
            getConnection();
        }
        Statement statement = connection.createStatement();
        return statement.executeQuery("select * from balances;");
    }

    //creates tables if there are none
    private void initialize() throws SQLException {
        if(!hasData){
            hasData = true;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select name from sqlite_master where type='table' and name='activities'");
            if(!resultSet.next())
            {
                Statement createTables = connection.createStatement();

                //build 'activities' table
                /*createTables.execute("create table activities(" +
                        "description varchar(40)," +
                        "time Date," +
                        "amount integer," +
                        "currency varchar(3),"+
                        "activity varchar(10)," +
                        "constraint actPK primary key (description, amount, time)," +
                        "constraint actFK foreign key (currency) references currency(abbreviation)" +
                        "" +
                        ");");*/

                createTables.execute("create table activities(" +
                        "description varchar(40)," +
                        "time Date," +
                        "amount integer," +
                        "currency varchar(3),"+
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
            }
        }
    }

    ResultSet getLatestBalance(String currency){
        try {
            if(connection == null){
                getConnection();
            }
            //PreparedStatement ps = connection.prepareStatement("select * from (select * from balances where currency=? order by time desc) limit 1");
            PreparedStatement ps = connection.prepareStatement("select amount from currentBalances where currency=?");
            ps.setString(1, currency);
            ps.execute();
            return ps.getResultSet();
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }

    }
    boolean addBalance(String currency, int amount){
        try {
            if(connection == null){
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("insert into currentBalances(currency, amount) values(?, ?);");
            Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
            //ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(1, currency);
            ps.setInt(2, amount);
            ps.execute();
            //System.out.println("DODAJEM BALANCE " + currency + " " + amount + " " + ts);
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    boolean addCurrentBalance(String currency, int amount){
        try {
            if(connection == null){
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("insert into balances(time, currency, amount) values(?, ?, ?);");
            Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(2, currency);
            ps.setInt(3, amount);
            ps.execute();
            //System.out.println("DODAJEM BALANCE " + currency + " " + amount + " " + ts);
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }

    }

    boolean updateCurrentBalance(String currency, int amount){
        try {
            if(connection == null){
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("update currentBalances set amount=? where currency=?;");
            Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
            //ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(1, amount);
            ps.setString(2, currency);
            ps.execute();
            //System.out.println("DODAJEM BALANCE " + currency + " " + amount + " " + ts);
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    ResultSet getBalances(String currency, LocalDateTime from, LocalDateTime to){
        try {
            if(connection == null){
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("select * from balances where time between ? and ? and currency like ? escape '!';");
            currency = currency
                    .replace("!", "!!")
                    .replace("%", "%%")
                    .replace("_", "!_")
                    .replace("[", "![");
            //Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
            //ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setTimestamp(1, Timestamp.valueOf(from));
            ps.setTimestamp(2, Timestamp.valueOf(to));
            ps.setString(3, "%" + currency + "%");
            ps.execute();
            return ps.getResultSet();
            //System.out.println("DODAJEM BALANCE " + currency + " " + amount + " " + ts);
            //return true;

        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }
    /*ResultSet getBalance(String currency){
        try {
            if(connection == null){
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("select amount from balances where currency=?;");
            ps.setString(1, currency);
            ps.execute();
            return ps.getResultSet();
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }*/


    ResultSet getCurrencies(){
        try {
            if(connection == null){
                getConnection();
            }
            Statement statement = connection.createStatement();
            return statement.executeQuery("select abbreviation from currencies;");
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    ResultSet countCurrencies(){
        try {
            if(connection == null){
                getConnection();
            }
            Statement statement = connection.createStatement();
            return statement.executeQuery("select count(abbreviation) as num from currencies;");
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }



    boolean addCurrency(String abbreviation){
        try {
            if(connection == null){
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

    ResultSet getActivitiesDescriptions(){
        try {
            if(connection == null){
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("select description from activities;");
            ps.execute();
            return ps.getResultSet();

        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    boolean addActivity(String description, int amount, String currency, String activity, LocalDateTime date){
        try {
            if(connection == null){
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("insert into activities(description, amount, currency, activity, time) values(?, ?, ?, ?, ?);");
            ps.setString(1, description);
            ps.setInt(2, amount);
            ps.setString(3, currency);
            ps.setString(4, activity);
            //ps.setTimestamp(5, Timestamp.valueOf(date));
            ps.setDate(5, Date.valueOf(date.toLocalDate()));
            ps.execute();
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    ResultSet getActivities(String activity, LocalDateTime from, LocalDateTime to, String currency, String description){
        try {
            if(connection == null){
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("select * from activities where time between ? and ? and activity like ? escape '!' and currency like ? escape '!' and description like ? escape '!';");
            activity = activity
                    .replace("!", "!!")
                    .replace("%", "%%")
                    .replace("_", "!_")
                    .replace("[", "![");

            currency = currency
                    .replace("!", "!!")
                    .replace("%", "%%")
                    .replace("_", "!_")
                    .replace("[", "![");

            description = description
                    .replace("!", "!!")
                    .replace("%", "%%")
                    .replace("_", "!_")
                    .replace("[", "![");


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
}
