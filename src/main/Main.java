package main;

import gui.CreateAccountPanel;
import gui.LoginPanel;
import gui.MainFrame;
import managers.DatabaseManager;

import javax.swing.*;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
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
       // setUIFont(new javax.swing.plaf.FontUIResource("Serif", Font.PLAIN, 14));
        DatabaseManager dbm = new DatabaseManager();
        //dbm.display();
        //LoginFrame lf = new LoginFrame(dbm);
        //CreateAccountPanel caf = new CreateAccountPanel(dbm);
        MainFrame mf = new MainFrame(dbm);
        //LoginPanel lp = new LoginPanel(dbm);
        //lp.setVisible(true);


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
