package main;

import gui.LoginFrame;
import managers.DatabaseManager;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args){
	// write your code here
        //System.out.println("Hello world!");
        /*DatabaseManager dbm = new DatabaseManager();
        //dbm.addUser("dimitrije", "aaaa");
            ResultSet rs = dbm.display();
            while(rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2));
                //System.out.println("TEST");
            }*/
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        DatabaseManager dbm = new DatabaseManager();
        LoginFrame lf = new LoginFrame(dbm);

    }
}
