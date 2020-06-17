package gui;

import entities.Activity;
import managers.ManagerFactory;
import models.ActivityModel;
import net.miginfocom.swing.MigLayout;
import sorters.ActivitySorter;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DisplayActivitiesPanel extends JPanel {
    private ArrayList<Activity> activities;
    private JTable table;
    private JRadioButton[] directionArray;
    public JButton backBtn;
    public JButton printBtn;
    private ManagerFactory managerFactory;
    private ArrayList<String> currencies;
    //ArrayList<Integer> sortingDirections;
    private int[] sortingDirections = {1, 1, 1, 1, 1};

    public DisplayActivitiesPanel(ManagerFactory managerFactory){
        this.managerFactory = managerFactory;
        this.activities = new ArrayList<>();
        this.table = new JTable(new ActivityModel(this.activities));
        this.table.getTableHeader().setReorderingAllowed(false);
        //this.sortingDirections = new ArrayList<>();

       /* for(int i = 0; i < 5;i++){
            this.sortingDirections.add(1);
        }*/
        this.backBtn = new JButton("Back");
        this.printBtn = new JButton("Print");

        this.setLayout(new BorderLayout());

        JScrollPane tablePane = new JScrollPane(this.table);
        this.add(tablePane, BorderLayout.CENTER);

        //JRadioButton ascending = new JRadioButton("ascending");

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
        //setTableHeader();
        ActivityModel am = new ActivityModel(this.activities);
        TableRowSorter<ActivityModel> sorter = new TableRowSorter<>(am);
        this.table.setRowSorter(sorter);
        this.table.setModel(am);


    }

    private void refresh(){
        ActivityModel am = (ActivityModel) this.table.getModel();
        am.fireTableDataChanged();
    }

    private void setTableHeader(){
        this.table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = table.columnAtPoint(e.getPoint());
                sortingDirections[column] *= -1;
                String name = table.getColumnName(column);
                //List.sort(activities, new ActivitySorter(sortingDirections[column], name));
                activities.sort(new ActivitySorter(sortingDirections[column], name));
                refresh();
            }
        });

    }
}
