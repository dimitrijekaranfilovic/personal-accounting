package main;

import gui.MainFrame;
import managers.ManagerFactory;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        //TODO: odradi da se sumiraju aktivnosti prilikom njihovog prikaza
        try {

            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            //UIManager.setLookAndFeel(new InfoNodeLookAndFeel());
            //UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel"); //sledeci najbolji, posle nimbusa
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");

        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel
        }
        try {
            ManagerFactory mf = new ManagerFactory();
            MainFrame.getInstance(mf);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

       /*try {
            ManagerFactory mf = new ManagerFactory();
            ResultSet rs = mf.databaseManager.display();
            while(rs.next()){
                System.out.println(rs.getString("description") + " " + rs.getInt("amount") + " " + rs.getString("currency") + " " + rs.getString("activity") + " " +rs.getTimestamp("time").toLocalDateTime());
            }
        }
        catch (SQLException | ClassNotFoundException se){
            se.printStackTrace();
        }*/
    }
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
