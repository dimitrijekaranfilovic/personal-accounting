package gui;

import entities.Activity;
import managers.ManagerFactory;
import models.ActivityModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class that represents panel used for displaying filtered activities.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */


public class DisplayActivitiesPanel extends JPanel {
    /**
     * list of activities to be displayed
     * */
    private ArrayList<Activity> activities;
    private JTable table;
    public JButton backBtn, printBtn;
    private ManagerFactory managerFactory;

    public DisplayActivitiesPanel(ManagerFactory managerFactory){
        this.managerFactory = managerFactory;
        this.activities = new ArrayList<>();
        this.table = new JTable(new ActivityModel(this.activities, this.managerFactory));
        this.table.getTableHeader().setReorderingAllowed(false);

        this.backBtn = new JButton(this.managerFactory.resourceManager.backIcon);
        this.printBtn = new JButton(this.managerFactory.resourceManager.printIcon);

        this.setLayout(new BorderLayout());

        JScrollPane tablePane = new JScrollPane(this.table);
        this.add(tablePane, BorderLayout.CENTER);
        JPanel panel2 = new JPanel(new MigLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        panel2.add(this.backBtn, "split 2");
        panel2.add(this.printBtn);

        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainPanel.add(panel2);

        this.printBtn.setEnabled(false);

        this.add(mainPanel, BorderLayout.SOUTH);
    }


    /**
     * Function called after {@link ActivitiesFilterPanel#search()} function finishes and
     * if the {@link ActivitiesFilterPanel#activities} size is larger than zero.
     * @param activities ArrayList : activities to be displayed
     * */
    public void setActivities(ArrayList<Activity> activities){
        this.activities = activities;
        ActivityModel am = new ActivityModel(this.activities, this.managerFactory);
        am.updateColumnNames();
        TableRowSorter<ActivityModel> sorter = new TableRowSorter<>(am);
        this.table.setRowSorter(sorter);
        this.table.setModel(am);
    }
}
