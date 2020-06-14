package gui;

import managers.DatabaseManager;
import managers.UserManager;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;

public class CreateAccountPanel extends JPanel {
    //private DatabaseManager databaseManager;
    private UserManager userManager;
    JButton backBtn;

    public CreateAccountPanel(UserManager um){
        this.userManager = um;
        MigLayout mig = new MigLayout();

        JLabel usernameLabel = new JLabel("Username");
        JTextField usernameField = new JTextField(50);

        JLabel passwordLabel = new JLabel("Password");

        JPasswordField passwordField = new JPasswordField(50);
        passwordField.setEchoChar('*');

        Dimension d = new Dimension(70, 20);
        usernameLabel.setMinimumSize(d);
        usernameLabel.setMaximumSize(d);
        passwordLabel.setMinimumSize(d);
        passwordLabel.setMaximumSize(d);

        Dimension d2 = new Dimension(200, 25);
        usernameField.setMinimumSize(d2);
        usernameField.setMaximumSize(d2);
        passwordField.setMinimumSize(d2);
        passwordField.setMaximumSize(d2);
        JCheckBox showPassword = new JCheckBox("Show Password");

        JButton okBtn = new JButton("Ok");

        this.backBtn = new JButton("Back");

        JPanel panel = new JPanel(mig);
        panel.add(usernameLabel, "split 2");
        panel.add(usernameField, "wrap");

        panel.add(passwordLabel, "split 2");
        panel.add(passwordField, "wrap");

        panel.add(showPassword, "wrap");

        panel.add(okBtn, "split 2");
        panel.add(this.backBtn);

        this.add(panel);

        showPassword.addActionListener(ae -> {
            JCheckBox c = (JCheckBox) ae.getSource();
            passwordField.setEchoChar(c.isSelected() ? '\u0000' : '*');
        });

        okBtn.addActionListener(ae->{
            switch (userManager.addUser(usernameField.getText(), passwordField.getPassword()))
            {
                case UserManager.OK:
                    JOptionPane.showMessageDialog(null, "Account successfully created!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case UserManager.USERNAME_TAKEN:
                    JOptionPane.showMessageDialog(null, "Username is taken!", "Warning", JOptionPane.WARNING_MESSAGE);
                    break;
                case UserManager.FIELD_EMPTY:
                    JOptionPane.showMessageDialog(null, "Both fields are mandatory!", "Warning", JOptionPane.WARNING_MESSAGE);
                    break;
            }
        });
    }
}
