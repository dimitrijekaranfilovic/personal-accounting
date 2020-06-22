package managers;

import entities.Activity;
import entities.Publisher;
import event.Observer;
import event.UpdateEvent;
import gui.HomePanel;
import main.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles operations tied with activities.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */


public class ActivityManager implements Publisher {
    private DatabaseManager databaseManager;
    private List<Observer> observers;
    public Activity activity;

    public ActivityManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    /**
     * Function that takes all parameters needed to add an activity, checks them, parses if needed and
     * calls {@link managers.DatabaseManager#addActivity(String, int, String, String, LocalDateTime)} method.
     * {@link ActivityManager#notifyObservers()} is called after successfully adding the activity.
     * @param description String : activity's description
     * @param amount String : string representation of the activity's amount
     * @param currency String : activity's currency
     * @param activity String : activity's version(income or expense)
     * @param  date String : string representation of activity's date
     * @param hours int : activity's hour
     * @param minutes int : activity's minutes
     * @return indicator whether the activity was successfully added
     * */

    public boolean addActivity(String description, String amount, String currency, String activity, String date, int hours, int minutes){
        if(description.length() > 40)
            return false;
        if(!Main.isNumeric(amount))
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

        int amountNum = 0;
        String[] tokens = amount.split("\\.");
        amountNum += 100 * Integer.parseInt(tokens[0]);
        if(tokens.length == 2){
            String parts = tokens[1];
            if(parts.length() == 1){
                amountNum += Integer.parseInt(parts) * 10;
            }
            else if(parts.length() == 2){
                amountNum += Integer.parseInt(parts);
            }
        }

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
     * @param activity String : activity's version
     * @param fromDate String : activities after this date
     * @param toDate String : activities before this date
     * @param currency String : activity's currency
     * @param description String : activity's description
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

    //TODO: implementirati kada provalis kako scrollpane da stavis u displayActivitiesPanel
    public int getActivitiesSum(String activity, String fromDate, String toDate, String currency, String description){
        int result;
        if(fromDate.equalsIgnoreCase("") || toDate.equalsIgnoreCase(""))
            return 0;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        LocalDateTime from = LocalDateTime.parse(fromDate + " 00:01", dateTimeFormatter);
        LocalDateTime to = LocalDateTime.parse(toDate + " 23:59", dateTimeFormatter);

        ResultSet rs = this.databaseManager.getActivitiesSum(activity, from, to, currency, description);
        try{
            result = rs.getInt("total");
        }
        catch (SQLException se){
            return 0;
        }

        return result;
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
