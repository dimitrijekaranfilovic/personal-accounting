package gui;

import managers.CurrencyManager;
import managers.DatabaseManager;
import managers.UserManager;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class AddCurrencyBalanceFrame extends JPanel {
    //private DatabaseManager databaseManager;
    private UserManager userManager;
    private CurrencyManager currencyManager;
    public JButton finishBtn;
    private String user = null;


    AddCurrencyBalanceFrame(UserManager userManager, CurrencyManager currencyManager){
       // this.databaseManager = databaseManager;
        this.userManager = userManager;
        this.currencyManager = currencyManager;
        this.setLayout(new MigLayout());

        JLabel currencyLabel = new JLabel("Currency");
        JTextField currencyField = new JTextField(20);

        JLabel balanceLabel = new JLabel("Balance");
        JTextField balanceField = new JTextField(20);

        this.finishBtn = new JButton("Finish");
        JButton addBtn = new JButton("Add");

        this.add(currencyLabel, "split 2");
        this.add(currencyField, "wrap");

        this.add(balanceLabel, "split 2");
        this.add(balanceField, "wrap");

        this.add(addBtn, "split 2");
        this.add(finishBtn);

        addBtn.addActionListener(ae->
            this.currencyManager.addCurrency(currencyField.getText(), balanceField.getText(), this.userManager.createdUser)
        );


    }
    public void setUser(String user){
        this.user = user;
    }

}
