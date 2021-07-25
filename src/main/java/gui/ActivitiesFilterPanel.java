package gui;

import display.DateLabelFormatter;
import entities.Activity;
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
import java.util.HashMap;
import java.util.Properties;

/**
 * Class that represents panel in which filters for activities search are chosen.
 * It is an observer to CurrencyManager {@link managers.CurrencyManager} and SettingsManager {@link managers.SettingsManager}.
 *
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 */


public class ActivitiesFilterPanel extends JPanel implements Observer {
    /**
     * Available currencies.
     */
    private ArrayList<String> currencies;
    //private final ManagerFactory managerFactory;
    private final CurrencyManager currencyManager;
    private final SettingsManager settingsManager;
    private final ResourceManager resourceManager;
    private final ActivityManager activityManager;
    private final JComboBox<String> activitiesBox, currenciesBox;
    public final JButton okBtn;
    public final JButton cancelBtn;

    /**
     * Date pickers for date range.
     */
    public final JDatePickerImpl datePicker;
    public final JDatePickerImpl datePicker1;
    public final JTextField searchField;
    public ArrayList<Activity> activities;
    public HashMap<String, Double> groupedActivities;
    public String sign, currency;
    public final JLabel activityLabel;
    public final JLabel fromLabel;
    public final JLabel toLabel;
    public final JLabel timeLabel;
    public final JLabel currencyLabel;
    public final JLabel descriptionLabel;

    public ActivitiesFilterPanel() throws SQLException, ClassNotFoundException {
        super();
        this.setLayout(new MigLayout());
        this.currencyManager = ManagerFactory.createCurrencyManager();
        this.settingsManager = ManagerFactory.createSettingsManager();
        this.resourceManager = ManagerFactory.createResourceManager();
        this.activityManager = ManagerFactory.createActivityManager();
        this.currencyManager.addObserver(this);
        this.settingsManager.addObserver(this);
        this.currencies = this.currencyManager.getCurrencies();
        this.activities = new ArrayList<>();
        this.currenciesBox = new JComboBox<>();
        this.activitiesBox = new JComboBox<>();
        for (String s : this.currencies)
            this.currenciesBox.addItem(s);
        this.currenciesBox.addItem("");

        activitiesBox.addItem(this.settingsManager.getWord("income"));
        activitiesBox.addItem(this.settingsManager.getWord("expense"));
        activitiesBox.addItem("");

        this.timeLabel = new JLabel(this.settingsManager.getWord("time"));
        this.descriptionLabel = new JLabel(this.settingsManager.getWord("description"));
        this.currencyLabel = new JLabel(this.settingsManager.getWord("currency"));
        this.fromLabel = new JLabel(this.settingsManager.getWord("from"));
        this.toLabel = new JLabel(this.settingsManager.getWord("to"));
        this.activityLabel = new JLabel(this.settingsManager.getWord("activity"));

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

        this.okBtn = new JButton(this.resourceManager.okIcon);
        this.cancelBtn = new JButton(this.resourceManager.backIcon);

        this.searchField = new JTextField(20);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel panel1 = new JPanel(new MigLayout());
        panel1.add(activityLabel, "wrap");
        panel1.add(this.activitiesBox);

        JPanel panel2 = new JPanel(new MigLayout());

        Dimension d = new Dimension(30, 28);

        this.fromLabel.setMinimumSize(d);
        this.fromLabel.setMaximumSize(d);

        this.toLabel.setMinimumSize(d);
        this.toLabel.setMaximumSize(d);

        panel2.add(this.timeLabel, "wrap");
        panel2.add(fromLabel, "split");
        panel2.add(datePicker, "wrap");
        panel2.add(toLabel, "split 2");
        panel2.add(datePicker1, "wrap");

        JPanel panel3 = new JPanel(new MigLayout());
        panel3.add(this.currencyLabel, "wrap");
        panel3.add(this.currenciesBox);

        JPanel panel4 = new JPanel(new MigLayout());

        panel4.add(this.descriptionLabel, "split 2");
        panel4.add(searchField, "wrap");


        JPanel panel5 = new JPanel(new MigLayout());
        panel5.add(this.okBtn, "split 2");
        panel5.add(this.cancelBtn, "wrap");

        mainPanel.add(panel1);
        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainPanel.add(panel2);
        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainPanel.add(panel3);
        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainPanel.add(panel4);
        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainPanel.add(panel5);

        this.add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Function that needs to be defined after implementing {@link event.Observer} interface.
     *
     * @param e if the event comes from {@link managers.CurrencyManager} it
     *          updates combo box containing all currencies. If the event comes from {@link managers.SettingsManager},
     *          it changes the text inside combo boxes and labels according to the specified language using.
     */

    @Override
    public void updatePerformed(UpdateEvent e) {
        if (e.getSource() instanceof CurrencyManager) {
            this.currenciesBox.removeAllItems();
            this.currencies = this.currencyManager.getCurrencies();
            for (String s : currencies)
                this.currenciesBox.addItem(s);
            this.currenciesBox.addItem("");
        } else if (e.getSource() instanceof SettingsManager) {
            this.descriptionLabel.setText(this.settingsManager.getWord("description"));
            this.currencyLabel.setText(this.settingsManager.getWord("currency"));
            this.timeLabel.setText(this.settingsManager.getWord("time"));
            this.fromLabel.setText(this.settingsManager.getWord("from"));
            this.toLabel.setText(this.settingsManager.getWord("to"));
            this.activityLabel.setText(this.settingsManager.getWord("activity"));

            activitiesBox.removeAllItems();
            activitiesBox.addItem(this.settingsManager.getWord("income"));
            activitiesBox.addItem(this.settingsManager.getWord("expense"));
            activitiesBox.addItem("");
        }
    }

    /**
     * Function that filters activities based on content in text fields, combo boxes and date pickers.
     */
    public void search() {
        this.sign = "";
        if (activitiesBox.getSelectedItem().toString().equalsIgnoreCase(this.settingsManager.getWord("income")))
            this.sign = "+";
        else if (activitiesBox.getSelectedItem().toString().equalsIgnoreCase(this.settingsManager.getWord("expense")))
            this.sign = "-";
        this.activities = this.activityManager.getActivities(sign, this.datePicker.getJFormattedTextField().getText(), this.datePicker1.getJFormattedTextField().getText(), (String) currenciesBox.getSelectedItem(), this.searchField.getText());
        this.currency = (String) currenciesBox.getSelectedItem();
        if (!this.sign.equalsIgnoreCase("") && !currency.equalsIgnoreCase(""))
            this.groupedActivities = this.activityManager.groupActivities(this.sign, this.datePicker.getJFormattedTextField().getText(), this.datePicker1.getJFormattedTextField().getText(), this.currency, this.searchField.getText());
    }
}
