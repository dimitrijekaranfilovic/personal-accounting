package gui;

import managers.CurrencyManager;
import managers.DatabaseManager;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    private JPanel mainPanel;
    //private UserManager userManager;
    private CurrencyManager currencyManager;
    private DatabaseManager databaseManager;

    public  MainFrame(DatabaseManager dbm){
        this.databaseManager = dbm;
        //this.userManager = new UserManager(this.databaseManager);
        this.currencyManager = new CurrencyManager(this.databaseManager);

        //LoginPanel loginPanel = new LoginPanel(this.userManager);
        //CreateAccountPanel createAccountPanel = new CreateAccountPanel(this.userManager);
        AddCurrencyBalanceFrame addCurrencyBalanceFrame = new AddCurrencyBalanceFrame(this.currencyManager);

        mainPanel = new JPanel(new CardLayout());
       // mainPanel.add(loginPanel, "Log In");
       // mainPanel.add(createAccountPanel, "Create Account");
        mainPanel.add(addCurrencyBalanceFrame, "Add currencies and their balances");

        this.add(mainPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Log In");
        this.setResizable(false);

       /* loginPanel.createAccountBtn.addActionListener(ae-> showCard("Create Account"));
        createAccountPanel.backBtn.addActionListener(ae-> showCard("Log In"));
        addCurrencyBalanceFrame.finishBtn.addActionListener(ae-> {
            if(addCurrencyBalanceFrame.currencyAdded){
                showCard("Log In");
                addCurrencyBalanceFrame.currencyAdded = false;
            }

        });*/
        /*createAccountPanel.nextBtn.addActionListener(ae-> {
            if(createAccountPanel.createdUser){
                System.out.println("TREBA DA PREDJEM!");
                showCard("Add currencies and their balances");
                createAccountPanel.createdUser = false;
            }
        });*/
    }

    private void showCard(String name){
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, name);
        this.setTitle(name);
    }
}
