package entities;

import java.time.LocalDateTime;

public class Activity implements Comparable<Activity>{
    private String description;
    private LocalDateTime time;
    private int amount;
    private String currency;
    private String activityVersion;

    public Activity(String description, LocalDateTime time, int amount, String currency, String activityVersion) {
        this.description = description;
        this.time = time;
        this.amount = amount;
        this.currency = currency;
        this.activityVersion = activityVersion;
    }
    public Activity(String currency, int amount, String activity){
        this.currency = currency;
        this.amount = amount;
        this.activityVersion = activity;
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

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setActivityVersion(String activityVersion) {
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

    public String getCurrency() {
        return currency;
    }

    public String getActivityVersion() {
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
