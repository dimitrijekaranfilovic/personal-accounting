package gui;

import display.DateLabelFormatter;
import entities.Balance;
import event.Observer;
import event.UpdateEvent;
import managers.*;
import net.miginfocom.swing.MigLayout;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Class that represents a panel which is used for choosing filters
 * for balances' filtering.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */


public class BalancesFilterPanel extends JPanel implements Observer {
    private final JComboBox<String> currenciesBox;
    /**
     * available currencies
     * */
    private ArrayList<String> currencies;
    public final JButton okBtn;
    public final JButton cancelBtn;
    public ArrayList<Balance> balances;
    private final JDatePickerImpl datePicker, datePicker1;
    private final JLabel currencyLabel, fromLabel, toLabel, timeLabel;
    public String currency;
    private final ResourceManager resourceManager;
    private final CurrencyManager currencyManager;
    private final SettingsManager settingsManager;
    private final BalanceManager balanceManager;

    public BalancesFilterPanel() throws SQLException, ClassNotFoundException {
        this.currenciesBox = new JComboBox<>();
        this.resourceManager = ManagerFactory.createResourceManager();
        this.currencyManager = ManagerFactory.createCurrencyManager();
        this.settingsManager = ManagerFactory.createSettingsManager();
        this.balanceManager = ManagerFactory.createBalanceManager();
        this.okBtn = new JButton(this.resourceManager.okIcon);
        this.cancelBtn = new JButton(this.resourceManager.backIcon);

        this.currencies = this.currencyManager.getCurrencies();
        this.currencyManager.addObserver(this);
        this.settingsManager.addObserver(this);
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

        this.currencyLabel = new JLabel(this.settingsManager.getWord("currency"));

        JPanel panel1 = new JPanel(new MigLayout());
        panel1.add(this.currencyLabel, "wrap");
        panel1.add(currenciesBox, "wrap");

        Dimension d = new Dimension(30, 28);
        this.fromLabel = new JLabel(this.settingsManager.getWord("from"));
        this.toLabel = new JLabel(this.settingsManager.getWord("to"));
        this.timeLabel = new JLabel(this.settingsManager.getWord("time"));
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
        this.currency = (String)currenciesBox.getSelectedItem();
        this.balances = this.balanceManager.getBalances(currency, this.datePicker.getJFormattedTextField().getText(), this.datePicker1.getJFormattedTextField().getText());
    }


    /**
     * Function that needs to be defined after implementing the {@link event.Observer} interface.
     * @param e  if the event's source is {@link managers.CurrencyManager} the
     * content of combo boxes is updated. If the source is {@link managers.SettingsManager},
     * the text inside labels is updated according to the specified language.
     * */
    @Override
    public void updatePerformed(UpdateEvent e) {
        if(e.getSource() instanceof CurrencyManager){
            this.currenciesBox.removeAllItems();
            this.currencies = this.currencyManager.getCurrencies();
            for(String s : this.currencies)
                this.currenciesBox.addItem(s);
            this.currenciesBox.addItem("");
        }
        else if(e.getSource() instanceof SettingsManager){
            this.currencyLabel.setText(this.settingsManager.getWord("currency"));
            this.fromLabel.setText(this.settingsManager.getWord("from"));
            this.toLabel.setText(this.settingsManager.getWord("to"));
            this.timeLabel.setText(this.settingsManager.getWord("time"));
        }
    }
}
