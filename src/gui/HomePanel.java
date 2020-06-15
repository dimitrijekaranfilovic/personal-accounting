package gui;
import display.Display;
import event.Observer;
import event.UpdateEvent;
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



    public HomePanel(ManagerFactory managerFactory){
        this.managerFactory = managerFactory;
        this.addActivityBtn = new JButton("Add activity");
        this.balancesHistoryBtn = new JButton("Balances history");
        this.activitiesHistoryBtn = new JButton("Activities history");
        this.addCurrencyButton = new JButton("Add currency");
        this.currencies = this.managerFactory.currencyManager.getCurrencies();
        this.managerFactory.currencyManager.addObserver(this);

        JButton helpBtn = new JButton("Help");
        this.balanceField = new JTextField(36);
        Dimension d = new Dimension(150, 30);
        this.balanceField.setMinimumSize(d);
        this.balanceField.setMaximumSize(d);

        this.currenciesBox = new JComboBox<>();
        for(String s : this.currencies)
            this.currenciesBox.addItem(s);
        if(this.currencies.size() > 0)
            balanceField.setText(Display.amountDisplay(this.managerFactory.balanceManager.getBalance(this.currencies.get(0))));
        this.balanceField.setEditable(false);

        this.currenciesBox.addItemListener(ie->{
            String currency = (String)ie.getItem();
            int balance = this.managerFactory.balanceManager.getBalance(currency);
            //System.out.println("Balance: " + balance);
            this.balanceField.setText(Display.amountDisplay(balance));
            //this.balanceField.setText(balance + "");
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
        this.currenciesBox.removeAllItems();
        this.currencies = this.managerFactory.currencyManager.getCurrencies();
        for(String s : currencies)
            this.currenciesBox.addItem(s);
        balanceField.setText(Display.amountDisplay(this.managerFactory.balanceManager.getBalance(this.currencies.get(0))));

    }
}
