package gui;

import display.DateLabelFormatter;
import display.Display;
import event.Observer;
import event.UpdateEvent;
import managers.ManagerFactory;
import net.miginfocom.swing.MigLayout;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;

public class AddActivityPanel extends JPanel implements Observer {
    private ManagerFactory managerFactory;
    private ArrayList<String> currencies;
    private JComboBox<String> currenciesBox;
    public JButton okBtn;
    public JButton cancelBtn;

    public AddActivityPanel(ManagerFactory managerFactory){
        this.managerFactory = managerFactory;
        this.currencies = this.managerFactory.currencyManager.getCurrencies();
        this.currenciesBox = new JComboBox<>();
        this.okBtn = new JButton("Confirm");
        this.cancelBtn = new JButton("Back");
        this.managerFactory.currencyManager.addObserver(this);
        for(String s : currencies)
            this.currenciesBox.addItem(s);


        JLabel descriptionLabel = new JLabel("Description");
        JTextField descriptionField = new JTextField(20);

        JLabel amountLabel = new JLabel("Amount");
        JTextField amountField = new JTextField(20);

        JLabel currencyLabel = new JLabel("Currency");
        JLabel dateLabel = new JLabel("Date");
        JLabel timeLabel = new JLabel("Time");

        Dimension d = new Dimension(70, 28);
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

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());


        this.setLayout(new MigLayout());
        this.add(descriptionLabel, "split 2");
        this.add(descriptionField, "wrap");
        this.add(amountLabel, "split 2");
        this.add(amountField, "wrap");
        this.add(currencyLabel, "split 2");
        this.add(this.currenciesBox, "wrap");
        this.add(dateLabel, "split 2");
        this.add(datePicker, "wrap");
        this.add(timeLabel, "split 5");
        this.add(hourSpinner);
        this.add(new JLabel("h"));
        this.add(minuteSpinner);
        this.add(new JLabel("min"), "wrap");
        this.add(this.okBtn, "split 2");
        this.add(this.cancelBtn);

    }

    @Override
    public void updatePerformed(UpdateEvent e) {
        this.currenciesBox.removeAllItems();
        this.currencies = this.managerFactory.currencyManager.getCurrencies();
        for(String s : currencies)
            this.currenciesBox.addItem(s);
        //balanceField.setText(Display.amountDisplay(this.managerFactory.balanceManager.getBalance(this.currencies.get(0))));
    }
}
