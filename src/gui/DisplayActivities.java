package gui;

import entities.Activity;
import entities.SortingDirection;
import managers.ManagerFactory;
import models.ActivityModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DisplayActivities extends JPanel {
    private ArrayList<Activity> activities;
    private JTable table;
    private JRadioButton[] directionArray;
    public JButton backBtn;
    public JButton printBtn;
    private ManagerFactory managerFactory;
    private ArrayList<String> currencies;

    public DisplayActivities(ManagerFactory managerFactory){
        this.managerFactory = managerFactory;
        this.activities = new ArrayList<>();
        this.table = new JTable(new ActivityModel(this.activities));
        this.table.getTableHeader().setReorderingAllowed(false);

        this.setLayout(new BorderLayout());

        JScrollPane tablePane = new JScrollPane(this.table);
        this.add(tablePane, BorderLayout.CENTER);

        JRadioButton ascending = new JRadioButton("");

    }

    public void setActivities(ArrayList<Activity> activities){
        this.activities = activities;
        this.table.setModel(new ActivityModel(this.activities));
    }

    private void refresh(){
        ActivityModel am = (ActivityModel) this.table.getModel();
        am.fireTableDataChanged();
    }
}
