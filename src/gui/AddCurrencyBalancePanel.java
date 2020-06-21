package gui;

import event.Observer;
import event.UpdateEvent;
import managers.CurrencyManager;
import managers.ManagerFactory;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;



public class AddCurrencyBalancePanel extends JPanel implements Observer {
    private ManagerFactory managerFactory;
    public JButton finishBtn;
    private JLabel currencyLabel, balanceLabel;

    AddCurrencyBalancePanel(ManagerFactory managerFactory){
        this.managerFactory = managerFactory;
        this.setLayout(new MigLayout());

        this.managerFactory.settingsManager.addObserver(this);
        this.currencyLabel = new JLabel(this.managerFactory.settingsManager.getWord("currency"));
        JTextField currencyField = new JTextField(20);

        this.balanceLabel = new JLabel(this.managerFactory.settingsManager.getWord("balance"));
        JTextField balanceField = new JTextField(20);

        Dimension d = new Dimension(70, 30);
        currencyLabel.setMinimumSize(d);
        currencyLabel.setMaximumSize(d);
        balanceLabel.setMinimumSize(d);
        balanceLabel.setMaximumSize(d);

        this.finishBtn = new JButton();
        JButton addBtn = new JButton(this.managerFactory.resourceManager.addIcon);

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
                    JOptionPane.showMessageDialog(null, this.managerFactory.settingsManager.getWord("currency_warning_1"), this.managerFactory.settingsManager.getWord("warning"), JOptionPane.WARNING_MESSAGE);
                    break;
                case CurrencyManager.NOT_CHARACTER:
                    JOptionPane.showMessageDialog(null, this.managerFactory.settingsManager.getWord("currency_warning_2"), this.managerFactory.settingsManager.getWord("warning"), JOptionPane.WARNING_MESSAGE);
                    break;
                case CurrencyManager.NOT_NUMBER:
                    JOptionPane.showMessageDialog(null, this.managerFactory.settingsManager.getWord("currency_warning_3"), this.managerFactory.settingsManager.getWord("warning"), JOptionPane.WARNING_MESSAGE);
                    break;
                case CurrencyManager.OK:
                    JOptionPane.showMessageDialog(null, this.managerFactory.settingsManager.getWord("currency_information"), this.managerFactory.settingsManager.getWord("warning"), JOptionPane.INFORMATION_MESSAGE);
                    currencyField.setText("");
                    balanceField.setText("");
                    break;
                case CurrencyManager.WRONG:
                    JOptionPane.showMessageDialog(null, this.managerFactory.settingsManager.getWord("currency_error"), this.managerFactory.settingsManager.getWord("warning"), JOptionPane.ERROR_MESSAGE);
                    break;
            }
        });
    }

    public void updateIcon(boolean firstTimeSetup){
        if(firstTimeSetup)
            this.finishBtn.setIcon(this.managerFactory.resourceManager.nextIcon);
        else
            this.finishBtn.setIcon(this.managerFactory.resourceManager.backIcon);
    }

    @Override
    public void updatePerformed(UpdateEvent e) {
        this.currencyLabel.setText(this.managerFactory.settingsManager.getWord("currency"));
        this.balanceLabel.setText(this.managerFactory.settingsManager.getWord("balance"));
    }
}
