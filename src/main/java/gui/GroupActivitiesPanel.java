package gui;

import event.Observer;
import event.UpdateEvent;
import managers.ManagerFactory;
import managers.ResourceManager;
import managers.SettingsManager;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Class that displays pie chart of grouped activities.
 * @author Dimitrije Karanfilovic
 * @since 05.07.2020.
 * */

public class GroupActivitiesPanel extends JPanel implements Observer {
    private final JScrollPane pane;
    public final JButton backButton;
    public JFreeChart chart;
    public final JTextField totalField;
    private final JLabel totalLabel;
    private final SettingsManager settingsManager;
    private final ResourceManager resourceManager;

    GroupActivitiesPanel() throws SQLException, ClassNotFoundException {
        this.settingsManager = ManagerFactory.createSettingsManager();
        this.resourceManager = ManagerFactory.createResourceManager();
        this.settingsManager.addObserver(this);
        this.pane = new JScrollPane();
        this.backButton = new JButton(this.resourceManager.backIcon);
        this.totalLabel = new JLabel(this.settingsManager.getWord("total"));
        this.totalField = new JTextField(30);
        this.totalField.setEditable(false);
        JPanel panel = new JPanel(new MigLayout());
        panel.add(this.backButton, "split 4");
        panel.add(this.totalLabel);
        panel.add(this.totalField);
        this.add(this.pane, BorderLayout.CENTER);
        this.add(panel, BorderLayout.SOUTH);
    }

    /**
     * Initializes the chart.
     * @param groupedActivities map whose key is activity description and value is the sum of amounts of activities with that description
     * */
    public void setUpChart(HashMap<String, Double> groupedActivities){
        double sum = 0.0;
        DefaultPieDataset dataSet = new DefaultPieDataset();
        for(String key : groupedActivities.keySet()){
            dataSet.setValue(key, groupedActivities.get(key));
            sum += groupedActivities.get(key);
        }
        this.totalField.setText(sum + "");
        this.chart = ChartFactory.createPieChart(
          this.settingsManager.getWord("group_activities"),
          dataSet,
          true,
          true,
          false
        );

        PiePlot plot = (PiePlot)chart.getPlot();
        plot.setSectionOutlinesVisible(false);
        plot.setNoDataMessage("No data available");
        JPanel panel = new ChartPanel(this.chart);
        this.pane.getViewport().removeAll();
        this.pane.getViewport().add(panel);
        this.pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.pane.getViewport().setPreferredSize(new Dimension(780, 400));
    }

    @Override
    public void updatePerformed(UpdateEvent e) {
        this.totalLabel.setText(this.settingsManager.getWord("total"));
    }
}
