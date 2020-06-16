package entities;

import java.time.LocalDateTime;

public class Balance implements Comparable<Balance>{
    private LocalDateTime dateTime;
    private String currency;
    private int amount;

    public Balance(LocalDateTime dateTime, String currency, int amount) {
        this.dateTime = dateTime;
        this.currency = currency;
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public int compareTo(Balance o) {
        return 0;
    }
}

/*public class Balance implements  Comparable<Balance>{
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
}*/
