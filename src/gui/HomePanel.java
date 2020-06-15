package gui;
import managers.ManagerFactory;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class HomePanel extends JPanel {
    private ManagerFactory managerFactory;
    private ArrayList<String> currencies;

    public HomePanel(ManagerFactory managerFactory) {
        //this.currencyManager = currencyManager;
        this.managerFactory = managerFactory;

        JTextField balanceField = new JTextField(30);

        JComboBox<String> currenciesBox = new JComboBox<>();
        this.currencies = this.managerFactory.currencyManager.getCurrencies();
        for(String s : this.currencies)
            currenciesBox.addItem(s);

        currenciesBox.addItemListener(ie->{
            String currency = (String)ie.getItem();
            int balance = this.managerFactory.balanceManager.getBalance(currency);
            balanceField.setText(balance + "");
        });






    }





}
