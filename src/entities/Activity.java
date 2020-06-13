package entities;

import java.time.LocalDateTime;

public class Activity implements Comparable<Activity>{
    private String description;
    private LocalDateTime time;
    private int amount;
    private Currency currency;
    private ActivityVersion activityVersion;

    public Activity(String description, LocalDateTime time, int amount, Currency currency, ActivityVersion activityVersion) {
        this.description = description;
        this.time = time;
        this.amount = amount;
        this.currency = currency;
        this.activityVersion = activityVersion;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setActivityVersion(ActivityVersion activityVersion) {
        this.activityVersion = activityVersion;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public int getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public ActivityVersion getActivityVersion() {
        return activityVersion;
    }

    @Override
    public int compareTo(Activity o) {
        return 0;
    }

    public boolean isBetween(LocalDateTime fromDate, LocalDateTime toDate){
        return this.time.isAfter(fromDate) && this.time.isBefore(toDate);
    }
}
