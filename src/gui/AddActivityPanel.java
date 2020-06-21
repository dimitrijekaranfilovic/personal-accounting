package gui;

import display.DateLabelFormatter;
import event.Observer;
import event.UpdateEvent;
import managers.CurrencyManager;
import managers.ManagerFactory;
import managers.SettingsManager;
import net.miginfocom.swing.MigLayout;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;

public class AddActivityPanel extends JPanel implements Observer {
    private ManagerFactory managerFactory;
    private ArrayList<String> currencies;
    private JComboBox<String> currenciesBox;
    private JTextField descriptionField;
    private JTextField amountField;
    public JButton okBtn;
    public JButton cancelBtn;
    public JComboBox<String> activitiesBox;
    public String incomeString = "income";
    public String expenseString = "expense";
    public JLabel activitiesLabel, currencyLabel, dateLabel, timeLabel, descriptionLabel, amountLabel;
    //public JLabel descriptionLabel;
   // public JLabel amountLabel;
    public String activityAdded = "Activity successfully added.";
    public String information = "Information";
    public String errorMessage = "Error adding activity!";


    public AddActivityPanel(ManagerFactory managerFactory){
        this.managerFactory = managerFactory;
        this.currencies = this.managerFactory.currencyManager.getCurrencies();
        this.currenciesBox = new JComboBox<>();

        this.okBtn = new JButton(this.managerFactory.resourceManager.okIcon);
        this.cancelBtn = new JButton(this.managerFactory.resourceManager.backIcon);

        this.managerFactory.currencyManager.addObserver(this);
        this.managerFactory.settingsManager.addObserver(this);
        for(String s : currencies)
            this.currenciesBox.addItem(s);

        this.activitiesBox = new JComboBox<>();
        activitiesBox.addItem(incomeString);
        activitiesBox.addItem(expenseString);

        this.activitiesLabel = new JLabel(this.managerFactory.settingsManager.getWord("activity"));

        this.descriptionLabel = new JLabel(this.managerFactory.settingsManager.getWord("description"));
        this.descriptionField = new JTextField(20);
        AutoCompleteDecorator.decorate(this.descriptionField, this.managerFactory.activityManager.getActivitiesDescriptions(), false);

        this.amountLabel = new JLabel(this.managerFactory.settingsManager.getWord("amount"));
        this.amountField = new JTextField(20);

        this.currencyLabel = new JLabel(this.managerFactory.settingsManager.getWord("currency"));
        this.dateLabel = new JLabel(this.managerFactory.settingsManager.getWord("date"));
        this.timeLabel = new JLabel(this.managerFactory.settingsManager.getWord("time"));

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

       this.okBtn.addActionListener(ae->{
                    String sign;
                    if(activitiesBox.getSelectedItem().toString().equalsIgnoreCase(this.managerFactory.settingsManager.getWord("income")))
                        sign = "+";
                    else
                        sign = "-";

                   if(this.managerFactory.activityManager.addActivity(descriptionField.getText(), amountField.getText(),
                        (String)currenciesBox.getSelectedItem(), sign, datePicker.getJFormattedTextField().getText(),
                        (Integer) hourSpinner.getValue(), (Integer) minuteSpinner.getValue())){


                       JOptionPane.showMessageDialog(null, this.managerFactory.settingsManager.getWord("activity_information"), this.managerFactory.settingsManager.getWord("information"), JOptionPane.INFORMATION_MESSAGE);
                       this.descriptionField.setText("");
                       this.amountField.setText("");

                   }
                   else{
                       JOptionPane.showMessageDialog(null, this.managerFactory.settingsManager.getWord("activity_error"), this.managerFactory.settingsManager.getWord("error"), JOptionPane.ERROR_MESSAGE);
                   }
       });
    }

    @Override
    public void updatePerformed(UpdateEvent e) {
        if(e.getSource() instanceof CurrencyManager){
            this.currenciesBox.removeAllItems();
            this.currencies = this.managerFactory.currencyManager.getCurrencies();
            for(String s : currencies)
                this.currenciesBox.addItem(s);
        }
        else if(e.getSource() instanceof SettingsManager){
            this.descriptionLabel.setText(this.managerFactory.settingsManager.getWord("description"));
            this.timeLabel.setText(this.managerFactory.settingsManager.getWord("time"));
            this.amountLabel.setText(this.managerFactory.settingsManager.getWord("amount"));
            this.dateLabel.setText(this.managerFactory.settingsManager.getWord("date"));
            this.currencyLabel.setText(this.managerFactory.settingsManager.getWord("currency"));
            this.activitiesLabel.setText(this.managerFactory.settingsManager.getWord("activity"));

            this.activitiesBox.removeAllItems();

            this.activitiesBox.addItem(this.managerFactory.settingsManager.getWord("income"));
            this.activitiesBox.addItem(this.managerFactory.settingsManager.getWord("expense"));

        }

    }


}
