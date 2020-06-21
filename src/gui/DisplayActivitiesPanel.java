package gui;

import entities.Activity;
import managers.ManagerFactory;
import models.ActivityModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;

public class DisplayActivitiesPanel extends AbstractChildPanel {
    private ArrayList<Activity> activities;
    private JTable table;
    public JButton backBtn;
    public JButton printBtn;
    private ManagerFactory managerFactory;

    public DisplayActivitiesPanel(ManagerFactory managerFactory){
        this.managerFactory = managerFactory;
        this.activities = new ArrayList<>();
        this.table = new JTable(new ActivityModel(this.activities));
        this.table.getTableHeader().setReorderingAllowed(false);

        this.backBtn = new JButton(this.managerFactory.resourceManager.backIcon);
        this.printBtn = new JButton(this.managerFactory.resourceManager.printIcon);

        this.setLayout(new BorderLayout());

        JScrollPane tablePane = new JScrollPane(this.table);
        this.add(tablePane, BorderLayout.CENTER);

        JPanel panel1 = new JPanel(new MigLayout());
        JPanel panel2 = new JPanel(new MigLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        panel2.add(this.backBtn, "split 2");
        panel2.add(this.printBtn);

        mainPanel.add(panel1);
        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainPanel.add(panel2);

        this.add(mainPanel, BorderLayout.SOUTH);

    }

    public void setActivities(ArrayList<Activity> activities){
        this.activities = activities;
        this.table.setModel(new ActivityModel(this.activities));
        ActivityModel am = new ActivityModel(this.activities);
        TableRowSorter<ActivityModel> sorter = new TableRowSorter<>(am);
        this.table.setRowSorter(sorter);
        this.table.setModel(am);
    }

    @Override
    public void updateLocale(Locale l) {

    }
}
