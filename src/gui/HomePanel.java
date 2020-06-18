package gui;

import display.Display;
import event.Observer;
import event.UpdateEvent;
import managers.ActivityManager;
import managers.CurrencyManager;
import managers.ManagerFactory;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

//import entities.Balance;

public class HomePanel extends JPanel implements Observer {
    private ManagerFactory managerFactory;
    private ArrayList<String> currencies;
    public JButton addActivityBtn;
    public JButton balancesHistoryBtn;
    public JButton activitiesHistoryBtn;
    public JButton addCurrencyButton;
    public JComboBox<String> currenciesBox;
    public JTextField balanceField;
    //public Balance currentBalance;
    public HashMap<String, Integer> currencyValueMap;
    private JFrame frame;

    public HomePanel(ManagerFactory managerFactory){
        //this.currentBalance = new Balance();
        //this.frame = frame;
        this.currencyValueMap = new HashMap<>();
        this.managerFactory = managerFactory;
        this.addActivityBtn = new JButton("Add activity");
        this.balancesHistoryBtn = new JButton("Balances history");
        this.activitiesHistoryBtn = new JButton("Activities history");
        this.addCurrencyButton = new JButton("Add currency");
        this.currencies = this.managerFactory.currencyManager.getCurrencies();
        this.managerFactory.currencyManager.addObserver(this);
        this.managerFactory.activityManager.addObserver(this);

        //setPreferredSize(new Dimension(370, 150));

        JButton helpBtn = new JButton("Help");
        this.balanceField = new JTextField(36);
        Dimension fieldDimension = new Dimension(280, 30);
        this.balanceField.setMinimumSize(fieldDimension);
        this.balanceField.setMaximumSize(fieldDimension);
        //this.balanceField.setPreferredSize(fieldDimension);

       /* Dimension buttonDimension = new Dimension(50, 20);
        this.addActivityBtn.setPreferredSize(buttonDimension);
        this.balancesHistoryBtn.setPreferredSize(buttonDimension);
        this.activitiesHistoryBtn.setPreferredSize(buttonDimension);
        this.addCurrencyButton.setPreferredSize(buttonDimension);
        helpBtn.setPreferredSize(buttonDimension);*/

        this.currenciesBox = new JComboBox<>();
        for(String s : this.currencies){
            this.currenciesBox.addItem(s);
            this.currencyValueMap.put(s, this.managerFactory.balanceManager.getLatestBalance(s));
        }
        if(this.currencies.size() > 0){
            balanceField.setText(Display.amountDisplay(this.managerFactory.balanceManager.getLatestBalance(this.currencies.get(0))));
        }
        this.balanceField.setEditable(false);

        this.currenciesBox.addItemListener(ie->{
            String currency = (String)ie.getItem();
            this.balanceField.setText(Display.amountDisplay(this.currencyValueMap.get(currency)));

        });
        helpBtn.addActionListener(ae-> JOptionPane.showMessageDialog(null, "Dimitrije Karanfilovic 2020", "Help", JOptionPane.INFORMATION_MESSAGE));

        this.setLayout(new MigLayout());
        this.add(this.balanceField, "split 2");
        this.add(this.currenciesBox, "wrap");

        this.add(this.addActivityBtn, "split 3");
        this.add(this.activitiesHistoryBtn);
        this.add(this.balancesHistoryBtn, "wrap");

        this.add(this.addCurrencyButton, "split 2");
        this.add(helpBtn);
        this.setPreferredSize(this.getPreferredSize());


    }

    @Override
    public void updatePerformed(UpdateEvent e) {
        if(e.getSource() instanceof CurrencyManager){
            this.currenciesBox.removeAllItems();
            this.currencies = this.managerFactory.currencyManager.getCurrencies();
            for(String s : currencies){
                this.currencyValueMap.put(s, this.managerFactory.balanceManager.getLatestBalance(s));
                this.currenciesBox.addItem(s);
            }
            balanceField.setText(Display.amountDisplay(this.managerFactory.balanceManager.getLatestBalance(this.currencies.get(0))));
        }
        else if(e.getSource() instanceof ActivityManager){
                ActivityManager am = (ActivityManager)e.getSource();
                int newValue = 0;
                if(am.activity.getActivityVersion().equalsIgnoreCase("expense")){
                    newValue = this.currencyValueMap.get(am.activity.getCurrency()) - am.activity.getAmount();

                }
                else if(am.activity.getActivityVersion().equalsIgnoreCase("income")){
                    newValue = this.currencyValueMap.get(am.activity.getCurrency()) + am.activity.getAmount();
                }
                this.currencyValueMap.put(am.activity.getCurrency(), newValue);
                int balance = this.currencyValueMap.get((String)currenciesBox.getSelectedItem());
                this.balanceField.setText(Display.amountDisplay(balance));
        }
    }
}
