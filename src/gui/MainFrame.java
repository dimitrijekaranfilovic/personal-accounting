package gui;

import managers.ManagerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class MainFrame extends JFrame{
    private JPanel mainPanel;
    private ManagerFactory managerFactory;
    private HomePanel homePanel;

    private static MainFrame instance = null;

    public static MainFrame getInstance(ManagerFactory managerFactory) throws SQLException {
        if(instance == null)
            instance = new MainFrame(managerFactory);
        return instance;
    }
    private  MainFrame(ManagerFactory managerFactory) throws SQLException {
        this.managerFactory = managerFactory;
        //this.managerFactory.databaseManager.getConnection();
        AddCurrencyBalanceFrame addCurrencyBalanceFrame = new AddCurrencyBalanceFrame(this.managerFactory);
        this.homePanel = new HomePanel(this.managerFactory);
        AddActivityPanel addActivityPanel = new AddActivityPanel(this.managerFactory);
        ActivitiesFilterPanel activitiesFilterPanel = new ActivitiesFilterPanel(this.managerFactory);
        BalancesFilterPanel balancesFilterPanel = new BalancesFilterPanel(this.managerFactory);
        //DisplayActivitiesPanel displayActivitiesPanel = new DisplayActivitiesPanel(this.managerFactory);
        DisplayBalancesPanel displayBalancesPanel = new DisplayBalancesPanel(this.managerFactory);


        mainPanel = new JPanel(new CardLayout());
        mainPanel.add(this.homePanel, "Home");
        mainPanel.add(addCurrencyBalanceFrame, "Add currencies");
        mainPanel.add(addActivityPanel, "Add activity");
        mainPanel.add(activitiesFilterPanel, "Choose filters");
        mainPanel.add(balancesFilterPanel, "Choose balances filters");
        //mainPanel.add(displayActivitiesPanel, "Display activities");
        //mainPanel.add(displayBalancesPanel, "Display balances");


        this.add(mainPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);

        if(this.managerFactory.currencyManager.countCurrencies() > 0){
            showCard("Home", true);
        }
        else {
            showCard("Add currencies", false);
            this.setTitle("Initial setup");
        }
        addCurrencyBalanceFrame.finishBtn.addActionListener(ae->{
            try {
                if(this.managerFactory.currencyManager.countCurrencies() == 0)
                    JOptionPane.showMessageDialog(null, "You have to enter at least one currency!", "Warning", JOptionPane.WARNING_MESSAGE);
                else
                    showCard("Home", true);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for(String key : homePanel.currencyValueMap.keySet()){
                    managerFactory.balanceManager.addBalance(key, homePanel.currencyValueMap.get(key));
                    managerFactory.balanceManager.updateCurrentBalance(key, homePanel.currencyValueMap.get(key));
                }
            }
        });

        this.homePanel.addCurrencyButton.addActionListener(ae->showCard("Add currencies", true));
        this.homePanel.addActivityBtn.addActionListener(ae->showCard("Add activity", true));
        this.homePanel.activitiesHistoryBtn.addActionListener(ae->showCard("Choose filters", true));
        this.homePanel.balancesHistoryBtn.addActionListener(ae->showCard("Choose balances filters", true));
        addActivityPanel.cancelBtn.addActionListener(ae->showCard("Home", true));
        activitiesFilterPanel.cancelBtn.addActionListener(ae->showCard("Home", true));
        balancesFilterPanel.cancelBtn.addActionListener(ae->showCard("Home", true));
        //activitiesFilterPanel.okBtn.addActionListener(ae->showCard("Display activities", true)); //TODO: treba dobaviti aktivnosti u zavisnosti od filtera
        //displayActivitiesPanel.backBtn.addActionListener(ae->showCard("Choose filters", true));
        //balancesFilterPanel.okBtn.addActionListener(ae->showCard("Display balances", true)); //TODO: treba dobaviti stanja u zavisnosti od filtera
        //displayBalancesPanel.backBtn.addActionListener(ae->showCard("Choose balances filters", true));

    }

    private void showCard(String name, boolean title){
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, name);
        if(name.equalsIgnoreCase("home"))
            this.setSize(370, 150);
        else if(name.equalsIgnoreCase("add activity"))
            this.setSize(330, 270);
        else if(name.equalsIgnoreCase("choose filters"))
            this.setSize(270, 370);
        else if(name.equalsIgnoreCase("choose balances filters"))
            this.setSize(270, 250);
        else if(name.equalsIgnoreCase("display activities"))
            this.setSize(350, 300);
        else if(name.equalsIgnoreCase("add currencies"))
            this.setSize(370, 150);
        if(title)
            this.setTitle(name);
    }
}
