package managers.interfaces;

import entities.Activity;

import java.util.ArrayList;
import java.util.HashMap;

public interface IActivityManager {

    boolean addActivity(String description, String amount, String currency, String activity, String date, int hours, int minutes);
    ArrayList<Activity> getActivities(String activity, String fromDate, String toDate, String currency, String description);
    ArrayList<String> getActivitiesDescriptions();
    HashMap<String, Double> groupActivities(String activity, String fromDate, String toDate, String currency, String description);




    }
