package managers;

import entities.Activity;
import entities.Publisher;
import event.Observer;
import event.UpdateEvent;
import gui.HomePanel;
import managers.interfaces.IActivityManager;
import util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that handles operations tied with activities.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */


public class ActivityManager implements Publisher, IActivityManager {
    /**
     * enables indirect communication with the database
     * */
    private final DatabaseManager databaseManager;
    /**
     * List of observers
     * */
    private List<Observer> observers;
    /**
     * Last added activity(used by this class' observers)
     * */
    public Activity activity;

    public ActivityManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    /**
     * Function that takes all parameters needed to add an activity, checks them, parses if needed and
     * calls {@link managers.DatabaseManager#addActivity(String, int, String, String, LocalDateTime)} method.
     * {@link ActivityManager#notifyObservers()} is called after successfully adding the activity.
     * @param description activity's description
     * @param amount string representation of the activity's amount
     * @param currency  activity's currency
     * @param activity  activity's version(income or expense)
     * @param  date  string representation of activity's date
     * @param hours  activity's hour
     * @param minutes  activity's minutes
     * @return indicator whether the activity was successfully added
     * */

    public boolean addActivity(String description, String amount, String currency, String activity, String date, int hours, int minutes){
        if(description.length() > 40)
            return false;
        //if(!Main.isNumeric(amount))
        if(Util.isNotNumeric(amount))
            return false;
        if(description.equalsIgnoreCase("") || amount.equalsIgnoreCase("") || date.equalsIgnoreCase(""))
            return false;
        StringBuilder dateToFormat = new StringBuilder();
        dateToFormat.append(date);
        dateToFormat.append(" ");
        if(hours / 10 == 0)
            dateToFormat.append("0");
        dateToFormat.append(hours);
        dateToFormat.append(":");
        if(minutes / 10 == 0)
            dateToFormat.append("0");

        dateToFormat.append(minutes);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateToFormat.toString(), formatter);

        int amountNum = Util.parseString(amount);

        if(this.databaseManager.addActivity(description, amountNum, currency, activity, dateTime)){
            this.activity = new Activity(currency, amountNum, activity);
            notifyObservers();
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * Function that returns activities that fulfill the specified parameters.
     * @param activity  activity's version
     * @param fromDate  activities after this date
     * @param toDate  activities before this date
     * @param currency  activity's currency
     * @param description  activity's description
     * @return list of activities
     * */

    public ArrayList<Activity> getActivities(String activity, String fromDate, String toDate, String currency, String description) {
        ArrayList<Activity> activities = new ArrayList<>();
        if(fromDate.equalsIgnoreCase("") || toDate.equalsIgnoreCase(""))
            return null;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        LocalDateTime from = LocalDateTime.parse(fromDate + " 00:01", dateTimeFormatter);
        LocalDateTime to = LocalDateTime.parse(toDate + " 23:59", dateTimeFormatter);

        ResultSet rs = this.databaseManager.getActivities(activity, from, to, currency, description);
        try{
            while(rs.next()){
                activities.add(new Activity(rs.getString("description"), rs.getTimestamp("time").toLocalDateTime(), rs.getInt("amount"), rs.getString("currency"), rs.getString("activity")));
            }
        }
        catch (SQLException se){
            return null;
        }


        return activities;
    }

    /**
     * Function that returns all descriptions of activities.
     * @return list of descriptions
     * */

    public ArrayList<String> getActivitiesDescriptions(){
        ArrayList<String> descriptions = new ArrayList<>();
        ResultSet rs = this.databaseManager.getActivitiesDescriptions();
        try{
            while(rs.next()){
                descriptions.add(rs.getString("description"));
            }
        }
        catch (SQLException se){
            return null;
        }
        return descriptions;
    }

    /**
     * Function that returns map of grouped activities(key: activity description, value: sum of amounts of activities with that description) that fulfill the specified parameters.
     * @param activity  activity's version
     * @param fromDate  activities after this date
     * @param toDate  activities before this date
     * @param currency  activity's currency
     * @param description  activity's description
     * @return list of activities
     * */
    public HashMap<String, Double> groupActivities(String activity, String fromDate, String toDate, String currency, String description){
        HashMap<String, Double> groupedActivities = new HashMap<>();
        if(fromDate.equalsIgnoreCase("") || toDate.equalsIgnoreCase(""))
            return null;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        LocalDateTime from = LocalDateTime.parse(fromDate + " 00:01", dateTimeFormatter);
        LocalDateTime to = LocalDateTime.parse(toDate + " 23:59", dateTimeFormatter);

        ResultSet rs = this.databaseManager.groupActivities(activity, from, to, currency, description);
        try{
            while(rs.next()){
                groupedActivities.put(rs.getString("description"), (double)rs.getInt("am") / 100);
            }
        }
        catch (SQLException se){
            return null;
        }
        return groupedActivities;
    }


    @Override
    public void addObserver(Observer observer) {
        if (null == observers)
            observers = new ArrayList<>();
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        if (null == observers)
            return;
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        UpdateEvent e = new UpdateEvent(this);
        for (Observer observer : observers) {
            if(observer instanceof HomePanel)
            observer.updatePerformed(e);
        }
    }
}
