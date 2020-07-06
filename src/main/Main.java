package main;

import gui.MainFrame;
import managers.ManagerFactory;

import java.sql.SQLException;

/**
 * Program's entry point.
 * */

//TODO: testiraj ove promjene za look and feel. Postavi nove instalere.

public class Main {
    public static void main(String[] args) {
        try {
            //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

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
    /**
     * checks whether the given string is a number
     * @param str string to be checked
     * @return indicator whether a given string is a number
     * */
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
