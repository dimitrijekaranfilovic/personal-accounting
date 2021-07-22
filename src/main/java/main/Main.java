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

}
