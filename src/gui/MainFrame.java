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
        AddCurrencyBalancePanel addCurrencyBalancePanel = new AddCurrencyBalancePanel(this.managerFactory);
        AddActivityPanel addActivityPanel = new AddActivityPanel(this.managerFactory);
        ActivitiesFilterPanel activitiesFilterPanel = new ActivitiesFilterPanel(this.managerFactory);
        BalancesFilterPanel balancesFilterPanel = new BalancesFilterPanel(this.managerFactory);
        DisplayActivitiesPanel displayActivitiesPanel = new DisplayActivitiesPanel(this.managerFactory);
        DisplayBalancesPanel displayBalancesPanel = new DisplayBalancesPanel(this.managerFactory);
        WelcomePanel welcomePanel = new WelcomePanel(this.managerFactory);
        SettingsPanel settingsPanel = new SettingsPanel(this.managerFactory, this);


        mainPanel = new JPanel(new CardLayout());
        mainPanel.add(this.homePanel, "home");
        mainPanel.add(addCurrencyBalancePanel, "add_currency");
        mainPanel.add(addActivityPanel, "add_activity");
        mainPanel.add(activitiesFilterPanel, "activities_filter");
        mainPanel.add(balancesFilterPanel, "balances_filter");
        mainPanel.add(displayActivitiesPanel, "display_activities");
        mainPanel.add(displayBalancesPanel, "display_balances");
        mainPanel.add(welcomePanel, "welcome");
        mainPanel.add(settingsPanel, "settings");

        this.add(mainPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        //this.setLocationRelativeTo(null);

        if(this.managerFactory.currencyManager.countCurrencies() > 0){
            this.managerFactory.settingsManager.loadSettings();
            //this.managerFactory.lookAndFeelManager.changeLookAndFeel(this, this.managerFactory.settingsManager.style);
            //System.out.println("Treba da postavim: " + this.managerFactory.settingsManager.style);
            this.setLocation(this.managerFactory.settingsManager.x, this.managerFactory.settingsManager.y);
            showCard("home", true);
        }
        else {
            showCard("welcome", true);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.setLocation((int)(screenSize.getWidth() - this.getWidth()) / 2, (int)(screenSize.getHeight() - this.getHeight()) / 2);
            this.managerFactory.settingsManager.addInitialSettings(this.managerFactory.settingsManager.style, (int)(screenSize.getWidth() - this.getWidth()) / 2, (int)(screenSize.getHeight() - this.getHeight()) / 2);
        }
        addCurrencyBalancePanel.finishBtn.addActionListener(ae->{
            try {
                if(this.managerFactory.currencyManager.countCurrencies() == 0)
                    JOptionPane.showMessageDialog(null, this.managerFactory.settingsManager.getWord("currency_warning_4"), this.managerFactory.settingsManager.getWord("information"), JOptionPane.WARNING_MESSAGE);
                else
                    showCard("home", true);
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
                    managerFactory.settingsManager.saveSettings(managerFactory.settingsManager.style, getX(), getY(), managerFactory.settingsManager.currentLanguage);


                }
            }
        });

        this.homePanel.addCurrencyButton.addActionListener(ae->{
            addCurrencyBalancePanel.updateIcon(false);
            showCard("add_currency", true);
        });
        this.homePanel.addActivityBtn.addActionListener(ae->showCard("add_activity", true));
        this.homePanel.activitiesHistoryBtn.addActionListener(ae->showCard("activities_filter", true));
        this.homePanel.balancesHistoryBtn.addActionListener(ae->showCard("balances_filter", true));
        this.homePanel.settingsButton.addActionListener(ae->showCard("settings", true));
        addActivityPanel.cancelBtn.addActionListener(ae->showCard("home", true));
        activitiesFilterPanel.cancelBtn.addActionListener(ae->showCard("home", true));
        balancesFilterPanel.cancelBtn.addActionListener(ae->showCard("home", true));
        displayActivitiesPanel.backBtn.addActionListener(ae->showCard("activities_filter", true));
        displayBalancesPanel.backBtn.addActionListener(ae->showCard("balances_filter", true));
        settingsPanel.backBtn.addActionListener(ae->showCard("home", true));

        welcomePanel.nextBtn.addActionListener(ae->{
            addCurrencyBalancePanel.updateIcon(true);
            showCard("add_currency", true);
        });

        activitiesFilterPanel.okBtn.addActionListener(ae->{
            activitiesFilterPanel.search();
            if(activitiesFilterPanel.activities == null)
                JOptionPane.showMessageDialog(null, this.managerFactory.settingsManager.getWord("check_parameters"), this.managerFactory.settingsManager.getWord("error"), JOptionPane.ERROR_MESSAGE);
            else if(activitiesFilterPanel.activities.size() == 0)
                JOptionPane.showMessageDialog(null, this.managerFactory.settingsManager.getWord("no_result"), this.managerFactory.settingsManager.getWord("information"), JOptionPane.INFORMATION_MESSAGE);
            else {
                displayActivitiesPanel.setActivities(activitiesFilterPanel.activities);
                showCard("display_activities", true);
            }

        });

        balancesFilterPanel.okBtn.addActionListener(ae->{
            balancesFilterPanel.search();
            if(balancesFilterPanel.balances == null)
                JOptionPane.showMessageDialog(null, this.managerFactory.settingsManager.getWord("check_parameters"), this.managerFactory.settingsManager.getWord("error"), JOptionPane.ERROR_MESSAGE);
            else if(balancesFilterPanel.balances.size() == 0)
                JOptionPane.showMessageDialog(null, this.managerFactory.settingsManager.getWord("no_result"), this.managerFactory.settingsManager.getWord("information"), JOptionPane.INFORMATION_MESSAGE);
            else{
                displayBalancesPanel.setBalances(balancesFilterPanel.balances);
                showCard("display_balances", true);
            }
        });


    }

    private void showCard(String name, boolean title){
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, name);
        if(name.equalsIgnoreCase("home"))
            this.setSize(this.managerFactory.lookAndFeelManager.homeDimension);
            //this.setSize(180, 150); //width - 370 original
        else if(name.equalsIgnoreCase("add_activity"))
            //this.setSize(330, 270);
            this.setSize(this.managerFactory.lookAndFeelManager.addActivityDimension);
        else if(name.equalsIgnoreCase("activities_filter"))
            //this.setSize(270, 370);
            this.setSize(this.managerFactory.lookAndFeelManager.activityHistoryDimension);
        else if(name.equalsIgnoreCase("balances_filter"))
            //this.setSize(270, 250);
            this.setSize(this.managerFactory.lookAndFeelManager.balanceHistoryDimension);
        else if(name.equalsIgnoreCase("display_activities"))
            //this.setSize(500, 400);
            this.setSize(this.managerFactory.lookAndFeelManager.displayActivitiesDimension);
        else if(name.equalsIgnoreCase("add_currency"))
            //this.setSize(370, 150);
            this.setSize(this.managerFactory.lookAndFeelManager.addCurrencyDimension);
        else if(name.equalsIgnoreCase("display_balances"))
            //this.setSize(400, 300);
            this.setSize(this.managerFactory.lookAndFeelManager.displayBalancesDimension);
        else if(name.equalsIgnoreCase("welcome"))
            //this.setSize(380, 180);
            this.setSize(this.managerFactory.lookAndFeelManager.welcomeDimension);
        else if(name.equalsIgnoreCase("settings"))
            this.setSize(this.managerFactory.lookAndFeelManager.settingsDimension);
        //this.pack();
        if(title) //kad se stavlja naslov, trazi u trenutnom Locale
            //this.setTitle(name);
            this.setTitle(this.managerFactory.settingsManager.getWord(name));
    }
}
