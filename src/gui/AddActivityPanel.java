package gui;

import display.DateLabelFormatter;
import event.Observer;
import event.UpdateEvent;
import managers.ManagerFactory;
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

    public AddActivityPanel(ManagerFactory managerFactory){
        this.managerFactory = managerFactory;
        this.currencies = this.managerFactory.currencyManager.getCurrencies();
        this.currenciesBox = new JComboBox<>();

        this.okBtn = new JButton(this.managerFactory.resourceManager.okIcon);
        this.cancelBtn = new JButton(this.managerFactory.resourceManager.backIcon);

        this.okBtn.setToolTipText("Confirm");
        this.cancelBtn.setToolTipText("Back");


        this.managerFactory.currencyManager.addObserver(this);
        for(String s : currencies)
            this.currenciesBox.addItem(s);

        JComboBox<String> activitiesBox = new JComboBox<>();
        activitiesBox.addItem("income");
        activitiesBox.addItem("expense");

        JLabel activitiesLabel = new JLabel("Activity");

        JLabel descriptionLabel = new JLabel("Description");
        this.descriptionField = new JTextField(20);
        AutoCompleteDecorator.decorate(this.descriptionField, this.managerFactory.activityManager.getActivitiesDescriptions(), false);

        JLabel amountLabel = new JLabel("Amount");
        this.amountField = new JTextField(20);

        JLabel currencyLabel = new JLabel("Currency");
        JLabel dateLabel = new JLabel("Date");
        JLabel timeLabel = new JLabel("Time");

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
                   if(this.managerFactory.activityManager.addActivity(descriptionField.getText(), amountField.getText(),
                        (String)currenciesBox.getSelectedItem(), (String)activitiesBox.getSelectedItem(), datePicker.getJFormattedTextField().getText(),
                        (Integer) hourSpinner.getValue(), (Integer) minuteSpinner.getValue())){
                       JOptionPane.showMessageDialog(null, "Activity successfully added.", "Information", JOptionPane.INFORMATION_MESSAGE);
                       this.descriptionField.setText("");
                       this.amountField.setText("");

                   }
                   else{
                       JOptionPane.showMessageDialog(null, "Error adding activity.", "Error", JOptionPane.ERROR_MESSAGE);
                   }
       });
    }

    @Override
    public void updatePerformed(UpdateEvent e) {
        this.currenciesBox.removeAllItems();
        this.currencies = this.managerFactory.currencyManager.getCurrencies();
        for(String s : currencies)
            this.currenciesBox.addItem(s);
    }
}
