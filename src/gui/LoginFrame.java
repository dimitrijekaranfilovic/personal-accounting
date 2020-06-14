package gui;

import managers.DatabaseManager;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class LoginFrame extends JFrame {
    private DatabaseManager databaseManager;


    public LoginFrame(DatabaseManager dbm){
        this.databaseManager = dbm;
        MigLayout mig = new MigLayout();

        JLabel usernameLabel = new JLabel("Username");
        JTextField usernameField = new JTextField(50);

        JLabel passwordLabel = new JLabel("Password");

        Dimension d = new Dimension(70, 20);
        usernameLabel.setMinimumSize(d);
        usernameLabel.setMaximumSize(d);
        passwordLabel.setMinimumSize(d);
        passwordLabel.setMaximumSize(d);

        JPasswordField passwordField = new JPasswordField(50);
        passwordField.setEchoChar('*');

        JCheckBox showPassword = new JCheckBox("Show Password");

        JButton okBtn = new JButton("Ok");
        JButton cancelBtn = new JButton("Cancel");

        JButton createAccountBtn = new JButton("Create account");

        JPanel panel = new JPanel(mig);
        panel.add(usernameLabel, "split 2");
        panel.add(usernameField, "wrap");

        panel.add(passwordLabel, "split 2");
        panel.add(passwordField, "wrap");

        panel.add(showPassword, "wrap");

        panel.add(okBtn, "split 3");
        panel.add(cancelBtn);
        panel.add(createAccountBtn);


        this.setLocationRelativeTo(null);
        this.setSize(350, 165);
        this.add(panel);
        this.setResizable(false);
        this.setTitle("Log In");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        showPassword.addActionListener(ae -> {
            JCheckBox c = (JCheckBox) ae.getSource();
            passwordField.setEchoChar(c.isSelected() ? '\u0000' : '*');
        });

        okBtn.addActionListener(ae->{
            if(databaseManager.checkLogin(usernameField.getText(), passwordField.getPassword())){
                System.out.println("PRIJAVA!");
            }
            else
                JOptionPane.showMessageDialog(null, "Wrong username or password!", "Warning", JOptionPane.WARNING_MESSAGE);


        });
        cancelBtn.addActionListener(ae->{
            System.exit(0);
        });
    }

}
