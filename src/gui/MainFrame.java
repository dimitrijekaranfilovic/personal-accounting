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
        this.homePanel = new HomePanel(this.managerFactory);
        AddCurrencyBalanceFrame addCurrencyBalancePanel = new AddCurrencyBalanceFrame(this.managerFactory);
        AddActivityPanel addActivityPanel = new AddActivityPanel(this.managerFactory);
        ActivitiesFilterPanel activitiesFilterPanel = new ActivitiesFilterPanel(this.managerFactory);
        BalancesFilterPanel balancesFilterPanel = new BalancesFilterPanel(this.managerFactory);
        DisplayActivitiesPanel displayActivitiesPanel = new DisplayActivitiesPanel(this.managerFactory);
        DisplayBalancesPanel displayBalancesPanel = new DisplayBalancesPanel(this.managerFactory);
        WelcomePanel welcomePanel = new WelcomePanel(this.managerFactory);
        SettingsPanel settingsPanel = new SettingsPanel(this.managerFactory, this);


        mainPanel = new JPanel(new CardLayout());
        mainPanel.add(this.homePanel, "Home");
        mainPanel.add(addCurrencyBalancePanel, "Add currencies");
        mainPanel.add(addActivityPanel, "Add activity");
        mainPanel.add(activitiesFilterPanel, "Choose filters");
        mainPanel.add(balancesFilterPanel, "Choose balances filters");
        mainPanel.add(displayActivitiesPanel, "Display activities");
        mainPanel.add(displayBalancesPanel, "Display balances");
        mainPanel.add(welcomePanel, "Welcome");
        mainPanel.add(settingsPanel, "Settings");

        this.add(mainPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        if(this.managerFactory.currencyManager.countCurrencies() > 0){
            //this.managerFactory.settingsManager.loadSettings();
            //this.managerFactory.lookAndFeelManager.changeLookAndFeel(this, this.managerFactory.settingsManager.style);
            //System.out.println("Treba da postavim: " + this.managerFactory.settingsManager.style);
            //this.setLocation(this.managerFactory.settingsManager.x, this.managerFactory.settingsManager.y);
            showCard("Home", true);
        }
        else {
            showCard("Welcome", true);
            //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            //this.setLocation((int)(screenSize.getWidth() - this.getWidth()) / 2, (int)(screenSize.getHeight() - this.getHeight()) / 2);
            //this.managerFactory.settingsManager.addInitialSettings(this.managerFactory.lookAndFeelManager.currentLookAndFeel, (int)(screenSize.getWidth() - this.getWidth()) / 2, (int)(screenSize.getHeight() - this.getHeight()) / 2);
        }
        addCurrencyBalancePanel.finishBtn.addActionListener(ae->{
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
                    //managerFactory.settingsManager.saveSettings(managerFactory.lookAndFeelManager.currentLookAndFeel, getX(), getY());

                }
            }
        });

        this.homePanel.addCurrencyButton.addActionListener(ae->{
            addCurrencyBalancePanel.updateIcon(false);
            showCard("Add currencies", true);
        });
        this.homePanel.addActivityBtn.addActionListener(ae->showCard("Add activity", true));
        this.homePanel.activitiesHistoryBtn.addActionListener(ae->showCard("Choose filters", true));
        this.homePanel.balancesHistoryBtn.addActionListener(ae->showCard("Choose balances filters", true));
        this.homePanel.settingsButton.addActionListener(ae->showCard("Settings", true));
        addActivityPanel.cancelBtn.addActionListener(ae->showCard("Home", true));
        activitiesFilterPanel.cancelBtn.addActionListener(ae->showCard("Home", true));
        balancesFilterPanel.cancelBtn.addActionListener(ae->showCard("Home", true));
        displayActivitiesPanel.backBtn.addActionListener(ae->showCard("Choose filters", true));
        displayBalancesPanel.backBtn.addActionListener(ae->showCard("Choose balances filters", true));
        settingsPanel.backBtn.addActionListener(ae->showCard("Home", true));

        welcomePanel.nextBtn.addActionListener(ae->{
            addCurrencyBalancePanel.updateIcon(true);
            showCard("Add currencies", true);
        });

        activitiesFilterPanel.okBtn.addActionListener(ae->{
            activitiesFilterPanel.search();
            if(activitiesFilterPanel.activities == null)
                JOptionPane.showMessageDialog(null, "Error: Check parameters.", "Error", JOptionPane.ERROR_MESSAGE);
            else if(activitiesFilterPanel.activities.size() == 0)
                JOptionPane.showMessageDialog(null, "No results.", "Information", JOptionPane.INFORMATION_MESSAGE);
            else {
                displayActivitiesPanel.setActivities(activitiesFilterPanel.activities);
                showCard("Display activities", true);
            }

        });

        balancesFilterPanel.okBtn.addActionListener(ae->{
            balancesFilterPanel.search();
            if(balancesFilterPanel.balances == null)
                JOptionPane.showMessageDialog(null, "Error: Check parameters.", "Error", JOptionPane.ERROR_MESSAGE);
            else if(balancesFilterPanel.balances.size() == 0)
                JOptionPane.showMessageDialog(null, "No results.", "Information", JOptionPane.INFORMATION_MESSAGE);
            else{
                displayBalancesPanel.setBalances(balancesFilterPanel.balances);
                showCard("Display balances", true);
            }
        });


    }

    private void showCard(String name, boolean title){
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, name);
        if(name.equalsIgnoreCase("home"))
            //this.setSize(180, 150); //width - 370 original
            this.setSize(this.managerFactory.lookAndFeelManager.homeDimension);
        else if(name.equalsIgnoreCase("add activity"))
            //this.setSize(330, 270);
            this.setSize(this.managerFactory.lookAndFeelManager.addActivityDimension);
        else if(name.equalsIgnoreCase("choose filters"))
            //this.setSize(270, 370);
            this.setSize(this.managerFactory.lookAndFeelManager.activityHistoryDimension);
        else if(name.equalsIgnoreCase("choose balances filters"))
            //this.setSize(270, 250);
            this.setSize(this.managerFactory.lookAndFeelManager.balanceHistoryDimension);
        else if(name.equalsIgnoreCase("display activities"))
            //this.setSize(500, 400);
            this.setSize(this.managerFactory.lookAndFeelManager.displayActivitiesDimension);
        else if(name.equalsIgnoreCase("add currencies"))
            //this.setSize(370, 150);
            this.setSize(this.managerFactory.lookAndFeelManager.addCurrencyDimension);
        else if(name.equalsIgnoreCase("display balances"))
            //this.setSize(400, 300);
            this.setSize(this.managerFactory.lookAndFeelManager.displayBalancesDimension);
        else if(name.equalsIgnoreCase("welcome"))
            //this.setSize(380, 180);
            this.setSize(this.managerFactory.lookAndFeelManager.welcomeDimension);
        else if(name.equalsIgnoreCase("settings"))
            this.setSize(this.managerFactory.lookAndFeelManager.settingsDimension);
        //this.pack();
        if(title)
            this.setTitle(name);
    }
}
