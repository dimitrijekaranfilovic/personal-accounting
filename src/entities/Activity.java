package entities;

import java.time.LocalDateTime;

/**
 * Class that represents an activity(income or expense).
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */


public class Activity implements Comparable<Activity>{
    /**
     * activity description
     */
    private String description;
    /**
     * time when activity was made
     */
    private LocalDateTime time;
    /**
     * activity amount
     */
    private int amount;
    /**
     * activity currency
     */
    private String currency;
    /**
     * version(income or expense)
     */
    private String activityVersion;


    /**
     * Class constructor.
     * @param description activity description
     * @param time  time when activity was made
     * @param amount activity amount
     * @param currency  activity currency
     * @param activityVersion  version(income or expense)
     * */
    public Activity(String description, LocalDateTime time, int amount, String currency, String activityVersion) {
        this.description = description;
        this.time = time;
        this.amount = amount;
        this.currency = currency;
        this.activityVersion = activityVersion;
    }

    /**
     * Class constructor.
     * @param currency  activity currency
     * @param amount  activity amount
     * @param activity  version(income or expense)
     * */
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

    /**
     * Function that calculates whether the date is in the specified range.
     * @param fromDate lower date bound
     * @param toDate upper date bound
     * @return boolean value, true if the time attribute is between fromDate and toDate
     */

    public boolean isBetween(LocalDateTime fromDate, LocalDateTime toDate){
        return this.time.isAfter(fromDate) && this.time.isBefore(toDate);
    }
}
