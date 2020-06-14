package main;

import managers.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
	// write your code here
        //System.out.println("Hello world!");
        DatabaseManager dbm = new DatabaseManager();
        //dbm.addUser("dimitrije", "123456");
            ResultSet rs = dbm.display();
            while(rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2));
                //System.out.println("TEST");
            }
    }
}
