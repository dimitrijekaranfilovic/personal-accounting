package models;

import display.Display;
import entities.Activity;
import managers.ManagerFactory;
import managers.SettingsManager;

import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class that creates a model to be used when displaying activities.
 * @author Dimitrije Karanfilovic
 * @since 26.06.2020.
 * */


public class ActivityModel extends AbstractTableModel {
    private static final int DESCRIPTION_COLUMN = 0;
    private static final int AMOUNT_COLUMN = 1;
    private static final int CURRENCY_COLUMN = 2;
    private static final int DATE_COLUMN = 3;
    private static final int VERSION_COLUMN = 4;

    public final ArrayList<Activity> activities;
    private final String[] columnNames = {"Description", "Amount", "Currency", "Date", "Version"};
    private final SettingsManager settingsManager;

    public ActivityModel(ArrayList<Activity> activities) throws SQLException, ClassNotFoundException {
        this.activities = activities;
        this.settingsManager = ManagerFactory.createSettingsManager();
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
        columnNames[CURRENCY_COLUMN] = this.settingsManager.getWord("currency");
        columnNames[DESCRIPTION_COLUMN] = this.settingsManager.getWord("description");
        columnNames[VERSION_COLUMN] = this.settingsManager.getWord("activity");
        columnNames[AMOUNT_COLUMN] = this.settingsManager.getWord("amount");
        columnNames[DATE_COLUMN] = this.settingsManager.getWord("date");
        fireTableStructureChanged();
    }
}
