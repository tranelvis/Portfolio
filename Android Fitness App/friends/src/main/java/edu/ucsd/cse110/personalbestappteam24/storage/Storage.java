package edu.ucsd.cse110.personalbestappteam24.storage;

import android.content.Context;

import com.google.firebase.FirebaseApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.ucsd.cse110.personalbestappteam24.user.DailySteps;
import edu.ucsd.cse110.personalbestappteam24.user.User;

public abstract class Storage {
    protected JSONObject data;
    protected LocalStorage localStorage;
    protected CloudStorage cloudStorage;
    protected String emailAddress;

    public final static String STORAGE_TIME = "STORAGE_TIME";

    public Storage(String emailAddress, Context context) {
        this.emailAddress = emailAddress;
        localStorage = new LocalStorage(emailAddress, context);
        FirebaseApp.initializeApp(context);
        cloudStorage = new CloudStorage(emailAddress);
        this.setupData();
    }

    protected void setupData() {
        // Both kinds of data exist for this user. Resolve based on latest upload date.
        if(localStorage.exists() && cloudStorage.exists()) {
            // Local storage is more recent.
            if(localStorage.latestUpdateTime() > cloudStorage.latestUpdateTime()) {
                data = localStorage.getData();

                if(!localStorage.getData().equals(cloudStorage.getData())) {
                    cloudStorage.setData(localStorage.getData());
                    cloudStorage.pushData();
                }
            }

            // Cloud storage is more recent.
            else {
                data = cloudStorage.getData();

                if(!localStorage.getData().equals(cloudStorage.getData())) {
                    localStorage.setData(cloudStorage.getData());
                    localStorage.pushData();
                }
            }
        }

        // This user hasn't been added to the cloud yet. Maybe they were offline at some point.
        else if(localStorage.exists()) {
            data = localStorage.getData();
            cloudStorage.setData(localStorage.getData());
            cloudStorage.pushData();
        }

        // Local storage does not exist - this must be a new device that needs its data to be
        // synced from the cloud.
        else if(cloudStorage.exists()) {
            data = cloudStorage.getData();
            localStorage.setData(cloudStorage.getData());
            localStorage.pushData();
        }

        // Neither forms of storage exist, this is a new user.
        else {
            try {
                // Set data to a default user with step goal of 5000.
                data = new JSONObject("{}");
                put(User.HEIGHT, -1);
                put(User.CURR_STEP_GOAL, User.DEFAULT_STEP_GOAL); // TODO this shouldn't be here. It should use the user method to set step goal so that things are consistent.
                put(User.EMAIL_ADDRESS, emailAddress);
                put(User.ALL_TIME_STEPS, new JSONObject("{}"));
                put(User.ALL_WALKS_AND_RUNS, new JSONObject("{}"));
                put(User.ALL_TIME_STEP_GOALS, new JSONObject("{}"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            updateStorage();
        }
    }

    // TODO: Add checks to see whether cloud can be updated. Depends on internet connection.
    protected void updateStorage() {
        localStorage.setData(data);
        localStorage.pushData();

        cloudStorage.setData(data);
        cloudStorage.pushData();
    }

    /**
     * Overwrite any previous value associated with this key with a new provided value.
     * @param key the key to set new data to.
     * @param value the new value associated with this key.
     */
    public <T> void put(String key, T value) {
        try {
            data.put(key, value);
            updateStorage();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public <T> T get(String key) {
        try {
            return (T) data.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public long getLong(String key) {
        try {
            return data.getLong(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void printStorage() {
        System.out.println(data.toString());
    }

}
