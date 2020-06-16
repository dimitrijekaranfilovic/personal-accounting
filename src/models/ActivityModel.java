package models;

import display.Display;
import entities.Activity;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ActivityModel extends AbstractTableModel {
    public ArrayList<Activity> activities;
    private String[] columnNames = {"Description", "Amount", "Currency", "Date", "Version"};

    public ActivityModel(ArrayList<Activity> activities) {
        this.activities = activities;
    }

    public Activity getActivity(int row){
        return  this.activities.get(row);
    }

    public ArrayList<Activity> getActivities(){
        return  this.activities;
    }

    @Override
    public int getRowCount() {
        return this.activities.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Activity activity = this.activities.get(rowIndex);
        switch (columnIndex){
            case 0:
                return  activity.getDescription();
            case 1:
                return Display.amountDisplay(activity.getAmount());
            case 2:
                return activity.getCurrency();
            case 3:
                return Display.dateDisplay(activity.getTime());
            case 4:
                return activity.getActivityVersion();
            default:
                break;
        }

        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return this.getValueAt(0, columnIndex).getClass();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
