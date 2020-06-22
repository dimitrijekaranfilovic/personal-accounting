package models;

import display.Display;
import entities.Activity;
import managers.ManagerFactory;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ActivityModel extends AbstractTableModel {
    private static final int DESCRIPTION_COLUMN = 0;
    private static final int AMOUNT_COLUMN = 1;
    private static final int CURRENCY_COLUMN = 2;
    private static final int DATE_COLUMN = 3;
    private static final int VERSION_COLUMN = 4;

    public ArrayList<Activity> activities;
    private String[] columnNames = {"Description", "Amount", "Currency", "Date", "Version"};
    private ManagerFactory managerFactory;

    public ActivityModel(ArrayList<Activity> activities, ManagerFactory managerFactory) {
        this.activities = activities;
        this.managerFactory = managerFactory;
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
            case DESCRIPTION_COLUMN:
                return  activity.getDescription();
            case AMOUNT_COLUMN:
                return Display.amountDisplay(activity.getAmount());
            case CURRENCY_COLUMN:
                return activity.getCurrency();
            case DATE_COLUMN:
                return Display.dateDisplay(activity.getTime());
            case VERSION_COLUMN:
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

    public void updateColumnNames(){
        columnNames[CURRENCY_COLUMN] = this.managerFactory.settingsManager.getWord("currency");
        columnNames[DESCRIPTION_COLUMN] = this.managerFactory.settingsManager.getWord("description");
        columnNames[VERSION_COLUMN] = this.managerFactory.settingsManager.getWord("activity");
        columnNames[AMOUNT_COLUMN] = this.managerFactory.settingsManager.getWord("amount");
        columnNames[DATE_COLUMN] = this.managerFactory.settingsManager.getWord("date");
        fireTableStructureChanged();
    }
}
