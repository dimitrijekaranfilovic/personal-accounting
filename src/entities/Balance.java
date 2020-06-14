package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Balance implements  Comparable<Balance>{
    private LocalDateTime time;
    private ArrayList<Integer> currencyBalances;
    //TODO: treba mu se proslediti lista valuta, ili da ima uvid u staticku listu valuta
    public Balance(LocalDateTime time) {
        this.time = time;
        this.currencyBalances = new ArrayList<>();
        /*for(int i = 0; i < Currency.values().length;i++){
            this.currencyBalances.add(0);
        }*/
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
