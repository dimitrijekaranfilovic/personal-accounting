package gui;
import display.Display;
import entities.Balance;
import event.Observer;
import event.UpdateEvent;
import managers.ActivityManager;
import managers.CurrencyManager;
import managers.ManagerFactory;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HomePanel extends JPanel implements Observer {
    private ManagerFactory managerFactory;
    private ArrayList<String> currencies;
    public JButton addActivityBtn;
    public JButton balancesHistoryBtn;
    public JButton activitiesHistoryBtn;
    public JButton addCurrencyButton;
    public JComboBox<String> currenciesBox;
    public JTextField balanceField;
    public Balance currentBalance;
    //private HashMap<String, Integer> currentBalanceValues;



    public HomePanel(ManagerFactory managerFactory){
        this.currentBalance = new Balance();
        this.managerFactory = managerFactory;
        this.addActivityBtn = new JButton("Add activity");
        this.balancesHistoryBtn = new JButton("Balances history");
        this.activitiesHistoryBtn = new JButton("Activities history");
        this.addCurrencyButton = new JButton("Add currency");
        //this.currentBalanceValues =new HashMap<>();
        this.currencies = this.managerFactory.currencyManager.getCurrencies();
        this.managerFactory.currencyManager.addObserver(this);
        this.managerFactory.activityManager.addObserver(this);

        JButton helpBtn = new JButton("Help");
        this.balanceField = new JTextField(36);
        Dimension d = new Dimension(150, 30);
        this.balanceField.setMinimumSize(d);
        this.balanceField.setMaximumSize(d);

        this.currenciesBox = new JComboBox<>();
        for(String s : this.currencies){
            this.currenciesBox.addItem(s);
            this.currentBalance.currencyValueMap.put(s, this.managerFactory.balanceManager.getLatestBalance(s));
        }
        if(this.currencies.size() > 0){
            balanceField.setText(Display.amountDisplay(this.managerFactory.balanceManager.getLatestBalance(this.currencies.get(0))));
        }
        this.balanceField.setEditable(false);

        this.currenciesBox.addItemListener(ie->{
            //System.out.println("USAO SAM U LISTENER!");
            String currency = (String)ie.getItem();
            //int balance = this.managerFactory.balanceManager.getLatestBalance(currency);
            //int balance = this.currentBalance.currencyValueMap.get(currency);
            //System.out.println("Balance: " + this.currentBalance.currencyValueMap.get(currency));
            this.balanceField.setText(Display.amountDisplay(this.currentBalance.currencyValueMap.get(currency)));
        });

        //this.add(new JLabel("Current balance"), "split 3");
        this.setLayout(new MigLayout());
        this.add(this.balanceField, "split 2");
        this.add(this.currenciesBox, "wrap");

        this.add(this.addActivityBtn, "split 3");
        this.add(this.activitiesHistoryBtn);
        this.add(this.balancesHistoryBtn, "wrap");

        this.add(this.addCurrencyButton, "split 2");
        this.add(helpBtn);

        helpBtn.addActionListener(ae-> JOptionPane.showMessageDialog(null, "Dimitrije Karanfilovic 2020", "Help", JOptionPane.INFORMATION_MESSAGE));


    }

    @Override
    public void updatePerformed(UpdateEvent e) {
        if(e.getSource() instanceof CurrencyManager){
            //System.out.println("REAGUJEM NA DODAVANJE VALUTE!");
            //System.out.println("MICEM IZ COMBO BOX-A");
            this.currenciesBox.removeAllItems();
            this.currencies = this.managerFactory.currencyManager.getCurrencies();
            //System.out.println("BROJ VALUTA: " + this.managerFactory.currencyManager.getCurrencies().size());
            for(String s : currencies){
                this.currentBalance.currencyValueMap.put(s, this.managerFactory.balanceManager.getLatestBalance(s));
                this.currenciesBox.addItem(s);
                //if(!currentBalance.currencyValueMap.containsKey(s))
                //System.out.println("DODAJEM VALUTU " + s + " SA VRIJEDNOSCU " + this.managerFactory.balanceManager.getLatestBalance(s) + " U MAPU");
            }
            balanceField.setText(Display.amountDisplay(this.managerFactory.balanceManager.getLatestBalance(this.currencies.get(0))));
        }
        else if(e.getSource() instanceof ActivityManager){
                ActivityManager am = (ActivityManager)e.getSource();
                int newValue = 0;
                if(am.activity.getActivityVersion().equalsIgnoreCase("expense"))
                    newValue = this.currentBalance.currencyValueMap.get(am.activity.getCurrency()) - am.activity.getAmount();
                else if(am.activity.getActivityVersion().equalsIgnoreCase("income"))
                    newValue = this.currentBalance.currencyValueMap.get(am.activity.getCurrency()) + am.activity.getAmount();
                this.currentBalance.currencyValueMap.put(am.activity.getCurrency(), newValue);
                int balance = this.currentBalance.currencyValueMap.get((String)currenciesBox.getSelectedItem());
                this.balanceField.setText(Display.amountDisplay(balance));
        }
    }
}
