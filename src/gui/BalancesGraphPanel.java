package gui;

import display.Display;
import entities.Balance;
import managers.ManagerFactory;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BalancesGraphPanel extends JPanel {
    private ManagerFactory managerFactory;
    private JScrollPane pane;
    public JButton backButton, saveImageBtn;
    public JFreeChart chart;

    public BalancesGraphPanel(ManagerFactory managerFactory){
        this.managerFactory = managerFactory;
        this.pane = new JScrollPane();
        this.backButton = new JButton(this.managerFactory.resourceManager.backIcon);
        this.saveImageBtn = new JButton(this.managerFactory.resourceManager.saveIcon);
        JPanel panel = new JPanel(new MigLayout());
        panel.add(this.backButton, "split 2");
        panel.add(this.saveImageBtn);
        this.add(this.pane, BorderLayout.CENTER);
        this.add(panel, BorderLayout.SOUTH);
    }

    public void setUpChart(ArrayList<Balance> balances){
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        for(Balance balance : balances){
            dataSet.addValue((double)balance.getAmount() / 100, this.managerFactory.settingsManager.getWord("balance"), Display.shortenedDisplay(balance.getDateTime()));
        }
        JFreeChart chart = ChartFactory.createLineChart(
                this.managerFactory.settingsManager.getWord("balances_history"),
                this.managerFactory.settingsManager.getWord("time"),
                this.managerFactory.settingsManager.getWord("balance"),
                dataSet,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel panel = new ChartPanel(chart);
        this.pane.getViewport().removeAll();
        this.pane.getViewport().add(panel);
    }


}
