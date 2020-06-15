package main;

import gui.MainFrame;
import managers.DatabaseManager;
import javax.swing.*;

public class Main {
//TODO: mozda stavi da se sifre hash-uju kad se unose
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
        DatabaseManager dbm = new DatabaseManager();
        /*try {
            dbm.display();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        new MainFrame(dbm);
        //int a = Integer.parseInt("aaa");
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
