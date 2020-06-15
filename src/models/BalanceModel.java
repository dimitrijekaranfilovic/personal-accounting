package models;

import entities.Balance;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class BalanceModel extends AbstractTableModel {
    public ArrayList<Balance> balances;
    private ArrayList<String> columnNames;

    //TODO: mora se voditi racuna o tome da se mogu dodavati valute
    public BalanceModel(ArrayList<Balance> balances) {
        this.balances = balances;
        this.columnNames = new ArrayList<>();
        columnNames.add("Date");
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
        return this.columnNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Balance b = this.balances.get(rowIndex);
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return this.getValueAt(0, columnIndex).getClass();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }
}