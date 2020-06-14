package gui;

import managers.DatabaseManager;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    private JPanel mainPanel;
    private DatabaseManager databaseManager;

    public  MainFrame(DatabaseManager dbm){
        this.databaseManager = dbm;
        //this.setLayout(new MigLayout());

        LoginPanel loginPanel = new LoginPanel(this.databaseManager);
        CreateAccountPanel createAccountPanel = new CreateAccountPanel(this.databaseManager);

        mainPanel = new JPanel(new CardLayout());
        mainPanel.add(loginPanel, "Log In");
        mainPanel.add(createAccountPanel, "Create Account");

        this.add(mainPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Log In");

        loginPanel.createAccountBtn.addActionListener(ae-> showCard("Create Account"));
        createAccountPanel.backBtn.addActionListener(ae-> showCard("Log In"));
    }

    private void showCard(String name){
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, name);
        this.setTitle(name);
    }
}
