package gui;

import managers.ManagerFactory;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MainFrame extends JFrame{
    private JPanel mainPanel;
    private ManagerFactory managerFactory;

    public  MainFrame(ManagerFactory managerFactory) throws SQLException, ClassNotFoundException {
        this.managerFactory = managerFactory;
        this.managerFactory.databaseManager.getConnection();
        AddCurrencyBalanceFrame addCurrencyBalanceFrame = new AddCurrencyBalanceFrame(this.managerFactory);
        HomePanel homePanel = new HomePanel(this.managerFactory);

        mainPanel = new JPanel(new CardLayout());
        mainPanel.add(addCurrencyBalanceFrame, "Add currencies");
        mainPanel.add(homePanel, "Home");


        this.add(mainPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
        if(this.managerFactory.currencyManager.countCurrencies() > 0){
            showCard("Home");
            this.setTitle("Home");
        }
        else {
            showCard("Add currencies");
            this.setTitle("Initial setup");
        }


    }

    private void showCard(String name){
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, name);
        //this.setTitle(name);
    }
}
