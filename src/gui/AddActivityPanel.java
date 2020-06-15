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

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));


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

        /*JPanel panel1 = new JPanel(new MigLayout());
        panel1.add(descriptionLabel, "split 2");
        panel1.add(descriptionField, "wrap");

        JPanel panel2 = new JPanel(new MigLayout());
        panel2.add(amountLabel, "split 2");
        panel2.add(amountField, "wrap");

        JPanel panel3 = new JPanel(new MigLayout());
        panel3.add(currencyLabel, "split 2");
        panel3.add(this.currenciesBox, "wrap");

        JPanel panel4 = new JPanel(new MigLayout());
        panel4.add(dateLabel, "split 2");
        panel4.add(datePicker, "wrap");

        JPanel panel5 = new JPanel(new MigLayout());
        panel5.add(timeLabel, "split 5");
        panel5.add(hourSpinner);
        panel5.add(new JLabel("h"));
        panel5.add(minuteSpinner);
        panel5.add(new JLabel("min"), "wrap");

        JPanel panel6 = new JPanel(new MigLayout());
        panel6.add(this.okBtn, "split 2");
        panel6.add(this.cancelBtn);

        mainPanel.add(panel1);
        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainPanel.add(panel2);
        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainPanel.add(panel3);
        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainPanel.add(panel4);
        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainPanel.add(panel5);
        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainPanel.add(panel6);

        this.add(mainPanel, BorderLayout.CENTER);*/

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
