package managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entities.Activity;
import entities.Balance;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class BackupRestoreManager {
    private DatabaseManager databaseManager;
    private ArrayList<Activity> activities;
    ArrayList<Balance>balances;
    ArrayList<String>  currencies;
    HashMap<String, Object> settings;

    public BackupRestoreManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.activities = new ArrayList<>();
        this.balances = new ArrayList<>();
        this.currencies = new ArrayList<>();
        this.settings = new HashMap<>();

    }

    public void backup(){
        getAllActivities();
        getAllBalances();
        getAllCurrencies();
        getAllSettings();
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String basePath = "./src/json/";
        try {
            mapper.writeValue(new File(basePath + "settings.json"), this.settings);
            mapper.writeValue(new File(basePath + "activities.json"), this.activities);
            mapper.writeValue(new File(basePath + "balances.json"), this.balances);
            mapper.writeValue(new File(basePath + "currencies.json"), this.currencies);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getAllBalances(){
        try {
            ResultSet rs = this.databaseManager.getAllBalances();
            while(rs.next()){
                balances.add(new Balance(rs.getTimestamp("time").toLocalDateTime(), rs.getString("currency"), rs.getInt("amount")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void getAllActivities(){
        try {
            ResultSet rs = this.databaseManager.getAllActivities();
            while(rs.next()){
                activities.add(new Activity(rs.getString("description"), rs.getTimestamp("time").toLocalDateTime(), rs.getInt("amount"), rs.getString("currency"), rs.getString("activity")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getAllSettings(){
        try {
            ResultSet rs = this.databaseManager.loadSettings();
            settings.put("style",rs.getString("style"));
            settings.put("lastX", rs.getString("lastX"));
            settings.put("lastY", rs.getString("lastY"));
            settings.put("language", rs.getString("language"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void getAllCurrencies(){
        try {
            ResultSet rs = this.databaseManager.getCurrencies();
            while(rs.next()){
                currencies.add(rs.getString("abbreviation"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
