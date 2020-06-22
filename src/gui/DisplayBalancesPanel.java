package gui;

import entities.Balance;
import managers.ManagerFactory;
import models.BalanceModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class that represents panel used for displaying filtered balances.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */


public class DisplayBalancesPanel extends JPanel{
    private ArrayList<Balance> balances;
    private JTable table;
    private ManagerFactory managerFactory;
    public JButton backBtn, printBtn;

    DisplayBalancesPanel(ManagerFactory managerFactory){
        this.managerFactory = managerFactory;
        this.balances = new ArrayList<>();
        this.table = new JTable(new BalanceModel(this.balances, this.managerFactory));
        this.table.getTableHeader().setReorderingAllowed(false);

        this.backBtn = new JButton(this.managerFactory.resourceManager.backIcon);
        this.printBtn = new JButton(this.managerFactory.resourceManager.printIcon);

        this.setLayout(new BorderLayout());
        JScrollPane tablePane = new JScrollPane(this.table);
        this.add(tablePane, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel panel1 = new JPanel(new MigLayout());
        panel1.add(this.backBtn, "split 2");
        panel1.add(this.printBtn, "wrap");
        mainPanel.add(panel1);

        this.printBtn.setEnabled(false);
        this.add(mainPanel, BorderLayout.SOUTH);
    }


    /**
     * Function called after {@link BalancesFilterPanel#search()} function finishes and
     * if the {@link BalancesFilterPanel#balances} size is larger than zero.
     * @param balances ArrayList<Balance> : activities to be displayed
     * */
    public void setBalances(ArrayList<Balance> balances){
        this.balances = balances;
        BalanceModel bm = new BalanceModel(this.balances, this.managerFactory);
        bm.updateColumnNames();
        TableRowSorter<BalanceModel> sorter = new TableRowSorter<>(bm);
        this.table.setRowSorter(sorter);
        this.table.setModel(bm);
    }
}
