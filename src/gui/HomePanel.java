package gui;
import managers.ManagerFactory;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class HomePanel extends JPanel {
    private ManagerFactory managerFactory;
    private ArrayList<String> currencies;
    public JButton addActivityBtn;
    public JButton balancesHistoryBtn;
    public JButton activitiesHistoryBtn;


    public HomePanel(ManagerFactory managerFactory) {
        //this.currencyManager = currencyManager;
        this.managerFactory = managerFactory;
        this.setLayout(new MigLayout());

        JTextField balanceField = new JTextField(36);
        Dimension d = new Dimension(150, 30);
        balanceField.setMinimumSize(d);
        balanceField.setMaximumSize(d);


        JComboBox<String> currenciesBox = new JComboBox<>();
        this.currencies = this.managerFactory.currencyManager.getCurrencies();
        for(String s : this.currencies)
            currenciesBox.addItem(s);

        balanceField.setText(this.managerFactory.balanceManager.getBalance(this.currencies.get(0)) + "");
       // balanceField.setFont(new FontUIResource("Serif", Font.BOLD, 20));

        balanceField.setEditable(false);

        currenciesBox.addItemListener(ie->{
            String currency = (String)ie.getItem();
            int balance = this.managerFactory.balanceManager.getBalance(currency);
            System.out.println("Balance: " + balance);
            balanceField.setText(balance + "");
        });

        //this.add(new JLabel("Current balance"), "split 3");
        this.add(balanceField, "split 2");
        this.add(currenciesBox, "wrap");





    }





}
