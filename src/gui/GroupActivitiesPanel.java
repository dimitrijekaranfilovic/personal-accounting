package gui;

import managers.ManagerFactory;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Class that displays pie chart of grouped activities.
 * @author Dimitrije Karanfilovic
 * @since 05.07.2020.
 * */

public class GroupActivitiesPanel extends JPanel {
    private ManagerFactory managerFactory;
    private JScrollPane pane;
    public JButton backButton;

    GroupActivitiesPanel(ManagerFactory managerFactory){
        this.managerFactory = managerFactory;
        this.pane = new JScrollPane();
        this.backButton = new JButton(this.managerFactory.resourceManager.backIcon);
        JPanel panel = new JPanel(new MigLayout());
        panel.add(this.backButton, "wrap");
        this.add(this.pane, BorderLayout.CENTER);
        this.add(panel, BorderLayout.SOUTH);
    }

    /**
     * Initializes the chart.
     * @param groupedActivities map whose key is activity description and value is the sum of amounts of activities with that description
     * */
    public void setUpChart(HashMap<String, Double> groupedActivities){
        DefaultPieDataset dataSet = new DefaultPieDataset();
        for(String key : groupedActivities.keySet()){
            dataSet.setValue(key, groupedActivities.get(key));
        }
        JFreeChart chart = ChartFactory.createPieChart(
          this.managerFactory.settingsManager.getWord("group_activities"),
          dataSet,
          true,
          true,
          false
        );

        PiePlot plot = (PiePlot)chart.getPlot();
        plot.setSectionOutlinesVisible(false);
        plot.setNoDataMessage("No data available");
        JPanel panel = new ChartPanel(chart);
        this.pane.getViewport().removeAll();
        this.pane.getViewport().add(panel);
    }


}
