package managers;

import entities.ActivityVersion;
import java.sql.*;
import java.time.LocalDateTime;

public class DatabaseManager {
    private Connection connection;
    private boolean hasData = false;

    private void getConnection() throws ClassNotFoundException, SQLException {
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
        return statement.executeQuery("select * from users;");
    }

    //creates tables if there are none
    private void initialize() throws SQLException {
        if(!hasData){
            hasData = true;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select name from sqlite_master where type='table' and name='users'");
            if(!resultSet.next())
            {
                Statement createTables = connection.createStatement();

                //build 'users' table
                createTables.execute("create table users(" +
                        "username varchar(30) primary key," +
                        "password varchar(100) not null" +
                        ");");

                //build 'activities' table
                createTables.execute("create table activities(" +
                        "description varchar(40)," +
                        "time Date," +
                        "amount integer," +
                        "currency varchar(3),"+
                        "activity varchar(10)," +
                        "actor varchar(30)," +
                        "constraint actFK foreign key (actor) references users(username)," +
                        "constraint actPK primary key (description, amount, time)" +
                        "" +
                        "" +
                        ");");

                //build 'balances' table
                createTables.execute("create table balances(" +
                        "time Date primary key," +
                        "actor varchar(30)," +
                        "constraint balanceFK foreign key (actor) references users(username)" +
                        "" +
                        ");");

                //build 'currencies' table
                createTables.execute("create table currencies(" +
                        "abbreviation varchar(3)," +
                        "actor varchar(30)," +
                        "constraint currPK primary key (abbreviation, actor)," +
                        "constraint currFK foreign key (actor) references users(username)" +
                        ");");
            }
        }
    }

    boolean addUser(String username, String password) {
        try {
            if(connection == null){
                getConnection();
            }
            PreparedStatement ps = connection.prepareStatement("insert into users(username, password) values(?, ?);");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.execute();
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    boolean checkLogin(String username, char[] password){
            try {
                if(connection == null)
                    getConnection();
                PreparedStatement ps = connection.prepareStatement("select * from users where username=? and password=?;");
                ps.setString(1, username);
                ps.setString(2, new String(password));
                ps.execute();
                return ps.getResultSet().next();
            } catch (ClassNotFoundException | SQLException e) {
                //e.printStackTrace();
                return false;
            }
    }
}
