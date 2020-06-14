package gui;

import managers.DatabaseManager;
import managers.UserManager;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private UserManager userManager;
    public JButton createAccountBtn;

    public LoginPanel(UserManager um){
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
        JButton cancelBtn = new JButton("Cancel");

        this.createAccountBtn = new JButton("Create account");

        JPanel panel = new JPanel(mig);
        panel.add(usernameLabel, "split 2");
        panel.add(usernameField, "wrap");

        panel.add(passwordLabel, "split 2");
        panel.add(passwordField, "wrap");

        panel.add(showPassword, "wrap");

        panel.add(okBtn, "split 3");
        panel.add(cancelBtn);
        panel.add(this.createAccountBtn);

        this.add(panel);

        showPassword.addActionListener(ae -> {
            JCheckBox c = (JCheckBox) ae.getSource();
            passwordField.setEchoChar(c.isSelected() ? '\u0000' : '*');
        });

        okBtn.addActionListener(ae->{
            switch (this.userManager.checkLogin(usernameField.getText(), passwordField.getPassword())){
                case UserManager.OK:
                    JOptionPane.showMessageDialog(null, "Successfully logged in!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case UserManager.WRONG:
                    JOptionPane.showMessageDialog(null, "Wrong username or password!", "Warning", JOptionPane.WARNING_MESSAGE);
                    break;
            }

        });

        cancelBtn.addActionListener(ae-> System.exit(0));
    }
}
