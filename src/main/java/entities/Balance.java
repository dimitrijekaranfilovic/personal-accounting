package entities;

import java.time.LocalDateTime;


/**
 * Class that represents balance(how much money in which currency at given time)
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */

public class Balance implements Comparable<Balance>{
    private LocalDateTime dateTime;
    private String currency;
    private int amount;

    /**
     * Class constructor.
     * @param dateTime  time when balance was saved
     * @param currency  balance currency
     * @param amount  balance amount
     * */
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