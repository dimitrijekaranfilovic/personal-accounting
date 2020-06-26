package main;

import gui.MainFrame;
import managers.ManagerFactory;

import javax.swing.*;
import java.sql.SQLException;

/**
 * Program's entry point.
 * */

public class Main {
    public static void main(String[] args) {
        //TODO: odradi da se sumiraju aktivnosti prilikom njihovog prikaza
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel
        }
        try {
            ManagerFactory mf = new ManagerFactory();
            MainFrame.getInstance(mf);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

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
