package gui;

import display.DateLabelFormatter;
import event.Observer;
import event.UpdateEvent;
import managers.*;
import net.miginfocom.swing.MigLayout;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;


/**
 * Class that represents a panel which is used for adding a new activity.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */

public class AddActivityPanel extends JPanel implements Observer {
    /**
     * available currencies
     * */
    private ArrayList<String> currencies;
    private final JComboBox<String> currenciesBox;
    private final JTextField descriptionField, amountField;
    public final JButton okBtn;
    public final JButton cancelBtn;
    public final JComboBox<String> activitiesBox;
    public final JLabel activitiesLabel;
    public final JLabel currencyLabel;
    public final JLabel dateLabel;
    public final JLabel timeLabel;
    public final JLabel descriptionLabel;
    public final JLabel amountLabel;
    private final CurrencyManager currencyManager;
    private final ResourceManager resourceManager;
    private final SettingsManager settingsManager;
    private final ActivityManager activityManager;

    public AddActivityPanel() throws SQLException, ClassNotFoundException {
        this.currencyManager = ManagerFactory.createCurrencyManager();
        this.resourceManager = ManagerFactory.createResourceManager();
        this.settingsManager = ManagerFactory.createSettingsManager();
        this.activityManager = ManagerFactory.createActivityManager();
        this.currencies = this.currencyManager.getCurrencies();
        this.currenciesBox = new JComboBox<>();

        this.okBtn = new JButton(this.resourceManager.okIcon);
        this.cancelBtn = new JButton(this.resourceManager.backIcon);

        this.currencyManager.addObserver(this);
        this.settingsManager.addObserver(this);
        for(String s : currencies)
            this.currenciesBox.addItem(s);

        this.activitiesBox = new JComboBox<>();
        activitiesBox.addItem(this.settingsManager.getWord("income"));
        activitiesBox.addItem(this.settingsManager.getWord("expense"));

        this.activitiesLabel = new JLabel(this.settingsManager.getWord("activity"));

        this.descriptionLabel = new JLabel(this.settingsManager.getWord("description"));
        this.descriptionField = new JTextField(20);
        AutoCompleteDecorator.decorate(this.descriptionField, this.activityManager.getActivitiesDescriptions(), false);

        this.amountLabel = new JLabel(this.settingsManager.getWord("amount"));
        this.amountField = new JTextField(20);

        this.currencyLabel = new JLabel(this.settingsManager.getWord("currency"));
        this.dateLabel = new JLabel(this.settingsManager.getWord("date"));
        this.timeLabel = new JLabel(this.settingsManager.getWord("time"));

        Dimension d = new Dimension(70, 25);
        descriptionLabel.setMinimumSize(d);
        descriptionLabel.setMaximumSize(d);

        amountLabel.setMinimumSize(d);
        amountLabel.setMaximumSize(d);

        currencyLabel.setMinimumSize(d);
        currencyLabel.setMaximumSize(d);

        dateLabel.setMinimumSize(d);
        dateLabel.setMaximumSize(d);

        timeLabel.setMinimumSize(d);
        timeLabel.setMaximumSize(d);

        activitiesLabel.setMinimumSize(d);
        activitiesLabel.setMaximumSize(d);

        SpinnerModel spinnerHourModel = new SpinnerNumberModel(
                LocalDateTime.now().getHour(),
                0,
                23,
                1
        );

        SpinnerModel spinnerMinuteModel = new SpinnerNumberModel(
                LocalDateTime.now().getMinute(),
                0,
                59,
                1
        );

        JSpinner hourSpinner = new JSpinner(spinnerHourModel);
        JSpinner minuteSpinner = new JSpinner(spinnerMinuteModel);

        JFormattedTextField text1 = ((JSpinner.DefaultEditor)hourSpinner.getEditor()).getTextField();
        text1.setEditable(false);

        JFormattedTextField text2 = ((JSpinner.DefaultEditor)minuteSpinner.getEditor()).getTextField();
        text2.setEditable(false);


        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        this.setLayout(new MigLayout());
        this.add(descriptionLabel, "split 2");
        this.add(this.descriptionField, "wrap");

        this.add(amountLabel, "split 2");
        this.add(this.amountField, "wrap");

        this.add(currencyLabel, "split 2");
        this.add(this.currenciesBox, "wrap");

        this.add(activitiesLabel, "split 2");
        this.add(activitiesBox, "wrap");

        this.add(dateLabel, "split 2");
        this.add(datePicker, "wrap");

        this.add(timeLabel, "split 5");
        this.add(hourSpinner);
        this.add(new JLabel("h"));
        this.add(minuteSpinner);
        this.add(new JLabel("min"), "wrap");

        this.add(this.okBtn, "split 2");
        this.add(this.cancelBtn);


         /* This listener checks the content of combo boxes and text fields, and if all is in
          order it adds a new activity.*/
       this.okBtn.addActionListener(ae->{
            String sign;
            if(activitiesBox.getSelectedItem().toString().equalsIgnoreCase(this.settingsManager.getWord("income")))
                sign = "+";
            else
                sign = "-";

           if(this.activityManager.addActivity(descriptionField.getText(), amountField.getText(),
                (String)currenciesBox.getSelectedItem(), sign, datePicker.getJFormattedTextField().getText(),
                (Integer) hourSpinner.getValue(), (Integer) minuteSpinner.getValue())){


               JOptionPane.showMessageDialog(null, this.settingsManager.getWord("activity_information"), this.settingsManager.getWord("information"), JOptionPane.INFORMATION_MESSAGE);
               this.descriptionField.setText("");
               this.amountField.setText("");

           }
           else{
               JOptionPane.showMessageDialog(null, this.settingsManager.getWord("activity_error"), this.settingsManager.getWord("error"), JOptionPane.ERROR_MESSAGE);
           }
       });
    }

    /**
     * Function that needs to be defined after implementing {@link event.Observer} interface.
     * @param e  if the event's source is {@link managers.CurrencyManager}
     *  it updates currencies combo box. If the event's source id {@link managers.SettingsManager}
     *  it updates the text inside combo boxes and labels accoring to specified language
     * */
    @Override
    public void updatePerformed(UpdateEvent e) {
        if(e.getSource() instanceof CurrencyManager){
            this.currenciesBox.removeAllItems();
            this.currencies = this.currencyManager.getCurrencies();
            for(String s : currencies)
                this.currenciesBox.addItem(s);
        }
        else if(e.getSource() instanceof SettingsManager){
            this.descriptionLabel.setText(this.settingsManager.getWord("description"));
            this.timeLabel.setText(this.settingsManager.getWord("time"));
            this.amountLabel.setText(this.settingsManager.getWord("amount"));
            this.dateLabel.setText(this.settingsManager.getWord("date"));
            this.currencyLabel.setText(this.settingsManager.getWord("currency"));
            this.activitiesLabel.setText(this.settingsManager.getWord("activity"));

            this.activitiesBox.removeAllItems();

            this.activitiesBox.addItem(this.settingsManager.getWord("income"));
            this.activitiesBox.addItem(this.settingsManager.getWord("expense"));
        }
    }
}
