package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Balance implements  Comparable<Balance>{
    private LocalDateTime time;
    private ArrayList<Integer> currencyBalances;
    public HashMap<String, Integer> currencyValueMap;

    //TODO: treba mu se proslediti lista valuta, ili da ima uvid u staticku listu valuta
    public Balance() {
        this.time = LocalDateTime.now();
        this.currencyBalances = new ArrayList<>();
        this.currencyValueMap = new HashMap<>();
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public ArrayList<Integer> getCurrencyBalances() {
        return currencyBalances;
    }

    public void setCurrencyBalances(ArrayList<Integer> currencyBalances) {
        this.currencyBalances = currencyBalances;
    }

    @Override
    public int compareTo(Balance o) {
        return 0;
    }
}
