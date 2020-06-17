package gui;

import managers.CurrencyManager;
import managers.ManagerFactory;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class AddCurrencyBalanceFrame extends JPanel {
    private ManagerFactory managerFactory;
    public JButton finishBtn;

    AddCurrencyBalanceFrame(ManagerFactory managerFactory){
        this.managerFactory = managerFactory;
        this.setLayout(new MigLayout());

        JLabel currencyLabel = new JLabel("Currency");
        JTextField currencyField = new JTextField(20);

        JLabel balanceLabel = new JLabel("Balance");
        JTextField balanceField = new JTextField(20);

        Dimension d = new Dimension(70, 20);
        currencyLabel.setMinimumSize(d);
        currencyLabel.setMaximumSize(d);
        balanceLabel.setMinimumSize(d);
        balanceLabel.setMaximumSize(d);

        this.finishBtn = new JButton("Finish");
        JButton addBtn = new JButton("Add");

        this.add(currencyLabel, "split 2");
        this.add(currencyField, "wrap");

        this.add(balanceLabel, "split 2");
        this.add(balanceField, "wrap");

        this.add(addBtn, "split 2");
        this.add(finishBtn);

        addBtn.addActionListener(ae->{
            int result = this.managerFactory.currencyManager.addCurrency(currencyField.getText(), balanceField.getText());
            switch (result){
                case CurrencyManager.NUM_CHARACTERS:
                    JOptionPane.showMessageDialog(null, "Currency abbreviation must be exactly 3 characters!", "Warning", JOptionPane.WARNING_MESSAGE);
                    break;
                case CurrencyManager.NOT_CHARACTER:
                    JOptionPane.showMessageDialog(null, "Currency abbreviation can containt only characters!", "Warning", JOptionPane.WARNING_MESSAGE);
                    break;
                case CurrencyManager.NOT_NUMBER:
                    JOptionPane.showMessageDialog(null, "Balance amount is not a number!", "Warning", JOptionPane.WARNING_MESSAGE);
                    break;
                case CurrencyManager.OK:
                    JOptionPane.showMessageDialog(null, "Currency successfully added!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case CurrencyManager.WRONG:
                    JOptionPane.showMessageDialog(null, "Error adding currency!", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        });


    }

}
