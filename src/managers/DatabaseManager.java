package managers;

import entities.ActivityVersion;
import java.sql.*;
import java.time.LocalDate;
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
        //return statement.executeQuery("select * from balances;");
        return statement.executeQuery("select * from (select * from balances where currency='eur' order by time desc) limit 1");
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

                //build 'users' table
                /*createTables.execute("create table users(" +
                        "username varchar(30) primary key," +
                        "password varchar(100) not null" +
                        ");");*/

                //build 'activities' table
                createTables.execute("create table activities(" +
                        "description varchar(40)," +
                        "time Date," +
                        "amount integer," +
                        "currency varchar(3),"+
                        "activity varchar(10)," +
                        "constraint actPK primary key (description, amount, time)," +
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
            }
        }
    }

    ResultSet getLatestBalance(String currency){
        try {
            if(connection == null){
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("select * from (select * from balances where currency=? order by time desc) limit 1");
            ps.setString(1, currency);
            ps.execute();
            return ps.getResultSet();
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }

    }
    ResultSet getBalance(String currency){
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
    }


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

    boolean addBalance(String currency, int amount){
        try {
            if(connection == null){
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("insert into balances(time, currency, amount) values(?, ?, ?);");
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
           // ps.setString(2, user);
            ps.setString(2, currency);
            ps.setInt(3, amount);
            ps.execute();

            return true;

        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    boolean addCurrency(String abbreviation){
        try {
            if(connection == null){
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("insert into currencies(abbreviation) values(?);");
            ps.setString(1, abbreviation);
            //ps.setString(2, user);
            ps.execute();

            return true;

        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }
}
