package gui;

import display.Display;
import entities.Balance;
import managers.ManagerFactory;
import managers.ResourceManager;
import managers.SettingsManager;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class BalancesGraphPanel extends JPanel {
    private final JScrollPane pane;
    public final JButton backButton;
    public JFreeChart chart;
    private final ResourceManager resourceManager;
    private final SettingsManager settingsManager;

    public BalancesGraphPanel() throws SQLException, ClassNotFoundException {
        this.resourceManager = ManagerFactory.createResourceManager();
        this.settingsManager = ManagerFactory.createSettingsManager();
        this.pane = new JScrollPane();
        this.backButton = new JButton(this.resourceManager.backIcon);
        JPanel panel = new JPanel(new MigLayout());
        panel.add(this.backButton, "split 2");
        this.add(this.pane, BorderLayout.CENTER);
        this.add(panel, BorderLayout.SOUTH);
    }

    public void setUpChart(ArrayList<Balance> balances){
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        for(Balance balance : balances){
            dataSet.addValue((double)balance.getAmount() / 100, this.settingsManager.getWord("balance"), Display.shortenedDisplay(balance.getDateTime()));
        }
        JFreeChart chart = ChartFactory.createLineChart(
                this.settingsManager.getWord("balances_history"),
                this.settingsManager.getWord("time"),
                this.settingsManager.getWord("balance"),
                dataSet,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel panel = new ChartPanel(chart);
        this.pane.getViewport().removeAll();
        this.pane.getViewport().add(panel);
        this.pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.pane.getViewport().setPreferredSize(new Dimension(780, 400));
    }


}
