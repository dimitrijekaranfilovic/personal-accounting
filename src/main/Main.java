package main;

import gui.MainFrame;
import managers.DatabaseManager;
import managers.ManagerFactory;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
	// write your code here
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
       // setUIFont(new javax.swing.plaf.FontUIResource("Serif", Font.PLAIN, 14));
        //DatabaseManager dbm = new DatabaseManager();
        ManagerFactory mf = new ManagerFactory();
        /*try {
            ResultSet rs = mf.databaseManager.display();
            while(rs.next()){

                System.out.println(rs.getString("currency") + " " + rs.getInt("amount") + " " + rs.getTime("time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        try {
            MainFrame.getInstance(mf);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

       /* String str = "08.04.1986. 01:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        System.out.println(dateTime);*/
    }
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    /*public static void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }*/
}
