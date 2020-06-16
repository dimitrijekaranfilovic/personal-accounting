package managers;

import entities.Activity;
import entities.Publisher;
import event.Observer;
import event.UpdateEvent;
import gui.HomePanel;
import main.Main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ActivityManager implements Publisher {
    private DatabaseManager databaseManager;
    private List<Observer> observers;
    public Activity activity;

    public ActivityManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public boolean addActivity(String description, String amount, String currency, String activity, String date, int hours, int minutes){
        if(description.length() > 40)
            return false;
        if(!Main.isNumeric(amount))
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

    public ArrayList<Activity> getActivities(String activity, String fromDate, String toDate, String currency, String description)
    {
        ArrayList<Activity> activities = new ArrayList<>();
        return activities;
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
