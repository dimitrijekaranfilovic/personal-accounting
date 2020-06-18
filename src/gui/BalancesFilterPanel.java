package gui;

import display.DateLabelFormatter;
import entities.Balance;
import event.Observer;
import event.UpdateEvent;
import managers.ManagerFactory;
import net.miginfocom.swing.MigLayout;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Properties;

public class BalancesFilterPanel extends JPanel implements Observer {
    private ManagerFactory managerFactory;
    private JComboBox<String> currenciesBox;
    private ArrayList<String> currencies;
    public JButton okBtn;
    public JButton cancelBtn;
    public ArrayList<Balance> balances;
    private JDatePickerImpl datePicker;
    private JDatePickerImpl datePicker1;

    public BalancesFilterPanel(ManagerFactory managerFactory){
        this.managerFactory = managerFactory;
        this.currenciesBox = new JComboBox<>();
        this.okBtn = new JButton("Ok");
        this.cancelBtn = new JButton("Back");
        this.currencies = this.managerFactory.currencyManager.getCurrencies();
        this.managerFactory.currencyManager.addObserver(this);
        for(String s : this.currencies)
            this.currenciesBox.addItem(s);
        this.currenciesBox.addItem("");

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        this.datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        UtilDateModel model1 = new UtilDateModel();
        Properties p1 = new Properties();
        p1.put("text.today", "Today");
        p1.put("text.month", "Month");
        p1.put("text.year", "Year");
        JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p1);
        this.datePicker1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel panel1 = new JPanel(new MigLayout());
        panel1.add(new JLabel("Currency"), "wrap");
        panel1.add(currenciesBox, "wrap");

        Dimension d = new Dimension(30, 28);
        JLabel fromLabel = new JLabel("from");
        JLabel toLabel = new JLabel("to");

        fromLabel.setMinimumSize(d);
        fromLabel.setMaximumSize(d);

        toLabel.setMinimumSize(d);
        toLabel.setMaximumSize(d);

        JPanel panel2 = new JPanel(new MigLayout());
        panel2.add(new JLabel("Time"), "wrap");
        panel2.add(fromLabel, "split");
        panel2.add(this.datePicker, "wrap");
        panel2.add(toLabel, "split 2");
        panel2.add(this.datePicker1, "wrap");

        JPanel panel3 = new JPanel(new MigLayout());
        panel3.add(this.okBtn, "split 2");
        panel3.add(this.cancelBtn, "wrap");

        mainPanel.add(panel1);
        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainPanel.add(panel2);
        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainPanel.add(panel3);

        this.add(mainPanel, BorderLayout.CENTER);

    }

    public void search()
    {
        this.balances = this.managerFactory.balanceManager.getBalances((String)currenciesBox.getSelectedItem(), this.datePicker.getJFormattedTextField().getText(), this.datePicker1.getJFormattedTextField().getText());
    }

    @Override
    public void updatePerformed(UpdateEvent e) {
        this.currenciesBox.removeAllItems();
        this.currencies = this.managerFactory.currencyManager.getCurrencies();
        for(String s : this.currencies)
            this.currenciesBox.addItem(s);
        this.currenciesBox.addItem("");
    }
}
