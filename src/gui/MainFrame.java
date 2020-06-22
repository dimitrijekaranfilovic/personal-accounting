package gui;

import managers.ManagerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/**
 * Function that encompasses all other panels and controls conditions which need to be met
 * in order for panels to be displayed. This class is implemented as Singleton.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */


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
        this.setResizable(false);


        //if at least one currency is found, previous settings are loaded
        if(this.managerFactory.currencyManager.countCurrencies() > 0){
            this.managerFactory.settingsManager.loadSettings();
            this.setLocation(this.managerFactory.settingsManager.x, this.managerFactory.settingsManager.y);
            this.setVisible(true);
            showCard("home");
        }

        //if no currencies are found, initial settings are used and welcome panel is displayed.
        else {
            showCard("welcome");
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.setLocation((int)(screenSize.getWidth() - this.getWidth()) / 2, (int)(screenSize.getHeight() - this.getHeight()) / 2);
            this.setVisible(true);
            this.managerFactory.settingsManager.addInitialSettings(this.managerFactory.settingsManager.style, (int)(screenSize.getWidth() - this.getWidth()) / 2, (int)(screenSize.getHeight() - this.getHeight()) / 2);

        }

        //connect buttons that only initiate display of a certain panel
        this.homePanel.addActivityBtn.addActionListener(ae->showCard("add_activity"));
        this.homePanel.activitiesHistoryBtn.addActionListener(ae->showCard("activities_filter"));
        this.homePanel.balancesHistoryBtn.addActionListener(ae->showCard("balances_filter"));
        this.homePanel.settingsButton.addActionListener(ae->showCard("settings"));
        addActivityPanel.cancelBtn.addActionListener(ae->showCard("home"));
        activitiesFilterPanel.cancelBtn.addActionListener(ae->showCard("home"));
        balancesFilterPanel.cancelBtn.addActionListener(ae->showCard("home"));
        displayActivitiesPanel.backBtn.addActionListener(ae->showCard("activities_filter"));
        displayBalancesPanel.backBtn.addActionListener(ae->showCard("balances_filter"));
        settingsPanel.backBtn.addActionListener(ae->showCard("home"));

        //checks whether at least one currency was added in the initial setup
        addCurrencyBalancePanel.finishBtn.addActionListener(ae->{
            try {
                if(this.managerFactory.currencyManager.countCurrencies() == 0)
                    JOptionPane.showMessageDialog(null, this.managerFactory.settingsManager.getWord("currency_warning_4"), this.managerFactory.settingsManager.getWord("information"), JOptionPane.WARNING_MESSAGE);
                else
                    showCard("home");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        //saves current balance, adds it to balances history and saves current language used and window position right before closing
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

        //updates the addCurrencyPanel's icon
        this.homePanel.addCurrencyButton.addActionListener(ae->{
            addCurrencyBalancePanel.updateIcon(false);
            showCard("add_currency");
        });

        //updates the addCurrencyPanel's icon
        welcomePanel.nextBtn.addActionListener(ae->{
            addCurrencyBalancePanel.updateIcon(true);
            showCard("add_currency");
        });

        //calls activitiesFilterPanel search function and displays messages accordingly
        activitiesFilterPanel.okBtn.addActionListener(ae->{
            activitiesFilterPanel.search();
            if(activitiesFilterPanel.activities == null)
                JOptionPane.showMessageDialog(null, this.managerFactory.settingsManager.getWord("check_parameters"), this.managerFactory.settingsManager.getWord("error"), JOptionPane.ERROR_MESSAGE);
            else if(activitiesFilterPanel.activities.size() == 0)
                JOptionPane.showMessageDialog(null, this.managerFactory.settingsManager.getWord("no_result"), this.managerFactory.settingsManager.getWord("information"), JOptionPane.INFORMATION_MESSAGE);
            else {
                displayActivitiesPanel.setActivities(activitiesFilterPanel.activities);
                showCard("display_activities");
            }

        });

        //calls balancesFilterPanel search function and displays messages accordingly
        balancesFilterPanel.okBtn.addActionListener(ae->{
            balancesFilterPanel.search();
            if(balancesFilterPanel.balances == null)
                JOptionPane.showMessageDialog(null, this.managerFactory.settingsManager.getWord("check_parameters"), this.managerFactory.settingsManager.getWord("error"), JOptionPane.ERROR_MESSAGE);
            else if(balancesFilterPanel.balances.size() == 0)
                JOptionPane.showMessageDialog(null, this.managerFactory.settingsManager.getWord("no_result"), this.managerFactory.settingsManager.getWord("information"), JOptionPane.INFORMATION_MESSAGE);
            else{
                displayBalancesPanel.setBalances(balancesFilterPanel.balances);
                showCard("display_balances");
            }
        });
    }

    /**
     * Function that shows desired panel from frame's CardLayout and sets frame's size and title accordingly.
     * @param name String : card's name within the Cardlayout
     * */
    private void showCard(String name){
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, name);
        if(name.equalsIgnoreCase("home"))
            this.setSize(this.managerFactory.lookAndFeelManager.homeDimension);
        else if(name.equalsIgnoreCase("add_activity"))
            this.setSize(this.managerFactory.lookAndFeelManager.addActivityDimension);
        else if(name.equalsIgnoreCase("activities_filter"))
            this.setSize(this.managerFactory.lookAndFeelManager.activityHistoryDimension);
        else if(name.equalsIgnoreCase("balances_filter"))
            this.setSize(this.managerFactory.lookAndFeelManager.balanceHistoryDimension);
        else if(name.equalsIgnoreCase("display_activities"))
            this.setSize(this.managerFactory.lookAndFeelManager.displayActivitiesDimension);
        else if(name.equalsIgnoreCase("add_currency"))
            this.setSize(this.managerFactory.lookAndFeelManager.addCurrencyDimension);
        else if(name.equalsIgnoreCase("display_balances"))
            this.setSize(this.managerFactory.lookAndFeelManager.displayBalancesDimension);
        else if(name.equalsIgnoreCase("welcome"))
            this.setSize(this.managerFactory.lookAndFeelManager.welcomeDimension);
        else if(name.equalsIgnoreCase("settings"))
            this.setSize(this.managerFactory.lookAndFeelManager.settingsDimension);
        this.setTitle(this.managerFactory.settingsManager.getWord(name));
    }
}
