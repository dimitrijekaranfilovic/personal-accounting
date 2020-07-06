package main;

import gui.MainFrame;
import managers.ManagerFactory;

import java.sql.SQLException;

/**
 * Program's entry point.
 * */

public class Main {
    public static void main(String[] args) {
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
