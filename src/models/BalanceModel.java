package models;

import entities.Balance;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class BalanceModel extends AbstractTableModel {
    public ArrayList<Balance> balances;
    private String[] columnNames;

    //TODO: mora se voditi racuna o tome da se mogu dodavati valute
    public BalanceModel(ArrayList<Balance> balances) {
        this.balances = balances;
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
        return null;
    }
}
