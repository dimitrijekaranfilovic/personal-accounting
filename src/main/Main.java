package main;

import gui.MainFrame;
import managers.ManagerFactory;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String mainLookAndFeel = "Nimbus";
        try {
            /*for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                //if (mainLookAndFeel.equals(info.getName())) {
                   // UIManager.setLookAndFeel(info.getClassName());
                    //break;
                    System.out.println(info.getClassName());
                //}
            }*/
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //windows default
            //UIManager.setLookAndFeel(mainLookAndFeel);
            //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            //UIManager.setLookAndFeel(new InfoNodeLookAndFeel());
            //UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel"); //sledeci najbolji, posle nimbusa
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");

        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
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

                System.out.println(rs.getString("currency") + " " + rs.getInt("amount") + " " + rs.getTimestamp("time").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
