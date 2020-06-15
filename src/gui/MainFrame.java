package gui;

import managers.ManagerFactory;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MainFrame extends JFrame{
    private JPanel mainPanel;
    private ManagerFactory managerFactory;

    private static MainFrame instance = null;

    public static MainFrame getInstance(ManagerFactory managerFactory) throws SQLException, ClassNotFoundException {
        if(instance == null)
            instance = new MainFrame(managerFactory);
        return instance;
    }
    private  MainFrame(ManagerFactory managerFactory) throws SQLException, ClassNotFoundException {
        this.managerFactory = managerFactory;

        this.managerFactory.databaseManager.getConnection();



        AddCurrencyBalanceFrame addCurrencyBalanceFrame = new AddCurrencyBalanceFrame(this.managerFactory);
        HomePanel homePanel = new HomePanel(this.managerFactory);
        AddActivityPanel addActivityPanel = new AddActivityPanel(this.managerFactory);
        ActivitiesFilterPanel activitiesFilterPanel = new ActivitiesFilterPanel(this.managerFactory);

        mainPanel = new JPanel(new CardLayout());
        mainPanel.add(addCurrencyBalanceFrame, "Add currencies");
        mainPanel.add(homePanel, "Home");
        mainPanel.add(addActivityPanel, "Add activity");
        mainPanel.add(activitiesFilterPanel, "Choose filters");


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
        homePanel.addCurrencyButton.addActionListener(ae->showCard("Add currencies", true));
        homePanel.addActivityBtn.addActionListener(ae->showCard("Add activity", true));
        homePanel.activitiesHistoryBtn.addActionListener(ae->showCard("Choose filters", true));
        addActivityPanel.cancelBtn.addActionListener(ae->showCard("Home", true));
        activitiesFilterPanel.cancelBtn.addActionListener(ae->showCard("Home", true));



    }

    private void showCard(String name, boolean title){
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, name);
        //this.setSize(width, height);
        if(name.equalsIgnoreCase("home"))
            this.setSize(370, 150);
        else if(name.equalsIgnoreCase("add activity"))
            this.setSize(370, 240);
        else if(name.equalsIgnoreCase("choose filters"))
            this.setSize(270, 370);
        if(title)
            this.setTitle(name);
    }
}
