package gui;

import entities.Balance;
import managers.ManagerFactory;
import models.BalanceModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;

public class DisplayBalancesPanel extends JPanel{
    private ArrayList<Balance> balances;
    private JTable table;
    private ManagerFactory managerFactory;
    public JButton backBtn;

    DisplayBalancesPanel(ManagerFactory managerFactory){
        this.managerFactory = managerFactory;
        //this.managerFactory.settingsManager.addObserver(this);
        this.balances = new ArrayList<>();
        this.table = new JTable(new BalanceModel(this.balances, this.managerFactory));
        this.table.getTableHeader().setReorderingAllowed(false);

        this.backBtn = new JButton(this.managerFactory.resourceManager.backIcon);

        this.setLayout(new BorderLayout());
        JScrollPane tablePane = new JScrollPane(this.table);
        this.add(tablePane, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel panel1 = new JPanel(new MigLayout());
        panel1.add(this.backBtn);
        mainPanel.add(panel1);
        this.add(mainPanel, BorderLayout.SOUTH);
    }

    public void setBalances(ArrayList<Balance> balances){
        this.balances = balances;
        BalanceModel bm = new BalanceModel(this.balances, this.managerFactory);
        bm.updateColumnNames();
        System.out.println("Kolone: " + bm.columnNames[BalanceModel.DATE_COLUMN] + "," + bm.columnNames[BalanceModel.AMOUNT_COLUMN] + "," + bm.columnNames[BalanceModel.CURRENCY_COLUMN]);
        TableRowSorter<BalanceModel> sorter = new TableRowSorter<>(bm);
        this.table.setRowSorter(sorter);
        this.table.setModel(bm);
    }
}
