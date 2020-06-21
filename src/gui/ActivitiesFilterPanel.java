package gui;

import display.DateLabelFormatter;
import entities.Activity;
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
import java.util.Locale;
import java.util.Properties;

public class ActivitiesFilterPanel extends AbstractChildPanel implements Observer {
    private ArrayList<String> currencies;
    private ManagerFactory managerFactory;
    private JComboBox<String> activitiesBox, currenciesBox;
    public JButton okBtn, cancelBtn;
    public JDatePickerImpl datePicker, datePicker1;
    public JTextField searchField;
    public ArrayList<Activity> activities;
    public String incomeString = "income";
    public String expenseString = "expense";

    public JLabel activityLabel, fromLabel, toLabel, timeLabel, currencyLabel, descriptionLabel;


    public ActivitiesFilterPanel(ManagerFactory managerFactory) {
        super();
        this.managerFactory = managerFactory;
        this.setLayout(new MigLayout());
        this.managerFactory.currencyManager.addObserver(this);
        this.currencies = this.managerFactory.currencyManager.getCurrencies();
        this.activities = new ArrayList<>();
        this.currenciesBox = new JComboBox<>();
        this.activitiesBox = new JComboBox<>();
        for (String s : this.currencies)
            this.currenciesBox.addItem(s);
        this.currenciesBox.addItem("");

        this.activityLabel = new JLabel("Activity");


        activitiesBox.addItem(incomeString);
        activitiesBox.addItem(expenseString);
        activitiesBox.addItem("");


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

        this.okBtn = new JButton(this.managerFactory.resourceManager.okIcon);
        this.cancelBtn = new JButton(this.managerFactory.resourceManager.backIcon);

        this.searchField = new JTextField(20);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel panel1 = new JPanel(new MigLayout());
        panel1.add(activityLabel, "wrap");
        panel1.add(this.activitiesBox);

        JPanel panel2 = new JPanel(new MigLayout());

        Dimension d = new Dimension(30, 28);
        this.fromLabel = new JLabel("from");
        this.toLabel = new JLabel("to");

        this.fromLabel.setMinimumSize(d);
        this.fromLabel.setMaximumSize(d);

        this.toLabel.setMinimumSize(d);
        this.toLabel.setMaximumSize(d);

        this.timeLabel = new JLabel("Time");

        panel2.add(this.timeLabel, "wrap");
        panel2.add(fromLabel, "split");
        panel2.add(datePicker, "wrap");
        panel2.add(toLabel, "split 2");
        panel2.add(datePicker1, "wrap");

        this.currencyLabel = new JLabel("Currency");
        JPanel panel3 = new JPanel(new MigLayout());
        panel3.add(this.currencyLabel, "wrap");
        panel3.add(this.currenciesBox);

        JPanel panel4 = new JPanel(new MigLayout());

        this.descriptionLabel = new JLabel("Description");
        panel4.add(new JLabel("Description"), "split 2");
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

    @Override
    public void updatePerformed(UpdateEvent e) {
        this.currenciesBox.removeAllItems();
        this.currencies = this.managerFactory.currencyManager.getCurrencies();
        for (String s : currencies)
            this.currenciesBox.addItem(s);
        this.currenciesBox.addItem("");
    }

    public void search() {
        this.activities = this.managerFactory.activityManager.getActivities((String) activitiesBox.getSelectedItem(), this.datePicker.getJFormattedTextField().getText(), this.datePicker1.getJFormattedTextField().getText(), (String) currenciesBox.getSelectedItem(), this.searchField.getText());
    }

    @Override
    public void updateLocale(Locale l) {

    }
}
