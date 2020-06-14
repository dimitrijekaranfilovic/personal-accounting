package managers;

import java.sql.*;

public class DatabaseManager {
    private Connection connection;
    private boolean hasData = false;

    public void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        String url = System.getProperty("user.home") + System.getProperty("file.separator") + "personal-accounting-database.db";
        connection = DriverManager.getConnection("jdbc:sqlite:" + url);
        initialize();
    }



    public ResultSet display() throws SQLException, ClassNotFoundException {
        if(connection == null){
            getConnection();
        }
        Statement statement = connection.createStatement();
        return statement.executeQuery("select * from users;");
    }

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

                createTables.execute("create table balances(" +
                        "time Date primary key" +
                        ");");

                createTables.execute("create table currencies(" +
                        "abbreviation varchar(3) primary key" +
                        ");");
            }
        }
    }

    public boolean addUser(String username, String password) {
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
            //e.printStackTrace();
            return false;
        }
    }
}
