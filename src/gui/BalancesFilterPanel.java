package gui;

import display.DateLabelFormatter;
import entities.Balance;
import event.Observer;
import event.UpdateEvent;
import managers.CurrencyManager;
import managers.ManagerFactory;
import managers.SettingsManager;
import net.miginfocom.swing.MigLayout;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Class that represents a panel which is used for choosing filters
 * for balances' filtering.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */


public class BalancesFilterPanel extends JPanel implements Observer {
    private ManagerFactory managerFactory;
    private JComboBox<String> currenciesBox;
    /**
     * available currencies
     * */
    private ArrayList<String> currencies;
    public JButton okBtn, cancelBtn;
    public ArrayList<Balance> balances;
    private JDatePickerImpl datePicker, datePicker1;
    private JLabel currencyLabel, fromLabel, toLabel, timeLabel;

    public BalancesFilterPanel(ManagerFactory managerFactory){
        this.managerFactory = managerFactory;
        this.currenciesBox = new JComboBox<>();

        this.okBtn = new JButton(this.managerFactory.resourceManager.okIcon);
        this.cancelBtn = new JButton(this.managerFactory.resourceManager.backIcon);

        this.currencies = this.managerFactory.currencyManager.getCurrencies();
        this.managerFactory.currencyManager.addObserver(this);
        this.managerFactory.settingsManager.addObserver(this);
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

        this.currencyLabel = new JLabel(this.managerFactory.settingsManager.getWord("currency"));

        JPanel panel1 = new JPanel(new MigLayout());
        panel1.add(this.currencyLabel, "wrap");
        panel1.add(currenciesBox, "wrap");

        Dimension d = new Dimension(30, 28);
        this.fromLabel = new JLabel(this.managerFactory.settingsManager.getWord("from"));
        this.toLabel = new JLabel(this.managerFactory.settingsManager.getWord("to"));
        this.timeLabel = new JLabel(this.managerFactory.settingsManager.getWord("time"));
        fromLabel.setMinimumSize(d);
        fromLabel.setMaximumSize(d);

        toLabel.setMinimumSize(d);
        toLabel.setMaximumSize(d);

        JPanel panel2 = new JPanel(new MigLayout());
        panel2.add(this.timeLabel, "wrap");
        panel2.add(this.fromLabel, "split");
        panel2.add(this.datePicker, "wrap");
        panel2.add(this.toLabel, "split 2");
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

    /**
     * Function that filters balances based on parameters specified in combo boxes and text fields.
     * */
    public void search() {
        this.balances = this.managerFactory.balanceManager.getBalances((String)currenciesBox.getSelectedItem(), this.datePicker.getJFormattedTextField().getText(), this.datePicker1.getJFormattedTextField().getText());
    }


    /**
     * Function that needs to be defined after implementing the {@link event.Observer} interface.
     * @param e UpdateEvent : if the event's source is {@link managers.CurrencyManager} the
     * content of combo boxes is updated. If the source is {@link managers.SettingsManager},
     * the text inside labels is updated according to the specified language.
     * */
    @Override
    public void updatePerformed(UpdateEvent e) {
        if(e.getSource() instanceof CurrencyManager){
            this.currenciesBox.removeAllItems();
            this.currencies = this.managerFactory.currencyManager.getCurrencies();
            for(String s : this.currencies)
                this.currenciesBox.addItem(s);
            this.currenciesBox.addItem("");
        }
        else if(e.getSource() instanceof SettingsManager){
            this.currencyLabel.setText(this.managerFactory.settingsManager.getWord("currency"));
            this.fromLabel.setText(this.managerFactory.settingsManager.getWord("from"));
            this.toLabel.setText(this.managerFactory.settingsManager.getWord("to"));
            this.timeLabel.setText(this.managerFactory.settingsManager.getWord("time"));
        }
    }
}
