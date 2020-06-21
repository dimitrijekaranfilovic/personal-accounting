package main;

import gui.MainFrame;
import managers.ManagerFactory;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {
        //TODO: odradi da se imena kolona u modelima mijenjaju u runtime
        String mainLookAndFeel = "Nimbus";
        try {

            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //windows default
            //UIManager.setLookAndFeel(mainLookAndFeel);
            //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
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

       /* try {
            ManagerFactory mf = new ManagerFactory();
            ResultSet rs = mf.databaseManager.display();
            while(rs.next()){
                System.out.println(rs.getString("language"));
            }
        }
        catch (SQLException | ClassNotFoundException se){
            se.printStackTrace();
        }*/
    }
    private static void getWord(Locale locale, String key){
        ResourceBundle words = ResourceBundle.getBundle("languages/words", locale);
        String value = words.getString(key);
        System.out.printf("Locale: %s, Value: %s %n", locale.toString(), value);

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
