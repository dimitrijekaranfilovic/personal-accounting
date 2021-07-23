package managers.interfaces;

import entities.Balance;

import java.util.ArrayList;

public interface IBalanceManager {

    int getLatestBalance(String currency);
    boolean addBalance(String currency, int amount);
    boolean updateCurrentBalance(String currency, int amount);
    ArrayList<Balance> getBalances(String currency, String fromDate, String toDate);


    }
