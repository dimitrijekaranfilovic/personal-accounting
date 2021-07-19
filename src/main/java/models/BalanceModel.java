package models;

import display.Display;
import entities.Balance;
import managers.ManagerFactory;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Class that creates a model to be used when displaying balances.
 * @author Dimitrije Karanfilovic
 * @since 26.06.2020.
 * */

public class BalanceModel extends AbstractTableModel{
    public static final int AMOUNT_COLUMN = 0;
    public static final int CURRENCY_COLUMN = 1;
    public static final int DATE_COLUMN = 2;

    public ArrayList<Balance> balances;
    public String[] columnNames = {"Amount", "Currency", "Date"};
    private ManagerFactory managerFactory;

    public BalanceModel(ArrayList<Balance> balances, ManagerFactory managerFactory) {
        this.balances = balances;
        this.managerFactory = managerFactory;
    }

    public Balance getBalance(int row){
        return this.balances.get(row);
    }

    public ArrayList<Balance> getBalances(){
        return this.balances;
    }

    @Override
    public int getRowCount() {
        return this.balances.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Balance balance = this.balances.get(rowIndex);
        switch (columnIndex){
            case AMOUNT_COLUMN:
                return Display.amountDisplay(balance.getAmount());
            case CURRENCY_COLUMN:
                return balance.getCurrency();
            case DATE_COLUMN:
                return Display.dateDisplay(balance.getDateTime());
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
        columnNames[DATE_COLUMN] = this.managerFactory.settingsManager.getWord("date");
        columnNames[CURRENCY_COLUMN] = this.managerFactory.settingsManager.getWord("currency");
        columnNames[AMOUNT_COLUMN] = this.managerFactory.settingsManager.getWord("amount");
        fireTableStructureChanged();
    }
}