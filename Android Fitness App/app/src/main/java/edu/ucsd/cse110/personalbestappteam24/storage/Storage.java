package edu.ucsd.cse110.personalbestappteam24.storage;

import android.content.Context;
import android.util.Log;

import com.google.firebase.FirebaseApp;

import org.json.JSONException;
import org.json.JSONObject;

import edu.ucsd.cse110.personalbestappteam24.user.User;

public class Storage {
    private JSONObject userData;
    private JSONObject friendsData;
    protected LocalStorage localStorage;
    protected CloudStorage cloudStorage;
    protected String emailAddress;

    public final static String STORAGE_TIME = "STORAGE_TIME";


    public Storage(String emailAddress, Context context) {
        this.emailAddress = emailAddress;
        localStorage = new LocalStorage(emailAddress, context);
        FirebaseApp.initializeApp(context);
        cloudStorage = new CloudStorage(emailAddress, this);
        this.setupData();
    }

    /**
     * This method is a necessary callback from cloud storage which this class observes. Once
     * cloud storage gets its update, then this class can update its friend data to be that of
     * the cloud.
     */
    public void setupFriendData() {

        friendsData = cloudStorage.getFriendsData();
        Log.d("SETUP FRIEND DATA", friendsData.toString());

        // Cloud data has ground truth friend data.
        if(!localStorage.getFriendsData().equals(cloudStorage.getFriendsData())) {
            localStorage.setFriendsData(cloudStorage.getFriendsData());
            localStorage.pushData();
        }

        // Local storage has ground truth user data.
        if(!localStorage.getUserData().equals(cloudStorage.getUserData())) {
            cloudStorage.setUserData(localStorage.getUserData());
            cloudStorage.pushData();
        }
    }

    protected void setupData() {
        // At this point, cloud storage is not fully initialized, so get whatever we can from
        // local storage.
        if(localStorage.exists()) {
            userData = localStorage.getUserData();
            friendsData = localStorage.getFriendsData();
        }

        // Neither forms of storage exist, this is a new user. Note that this eliminates the
        // possibility for having a new user on a new device.
        else {
            try {
                userData = new JSONObject("{}");
                friendsData = new JSONObject("{}");

                // Set data to a default user with step goal of 5000.
                putIntoUserData(User.HEIGHT, -1);
                putIntoUserData(User.CURR_STEP_GOAL, User.DEFAULT_STEP_GOAL);
                putIntoUserData(User.EMAIL_ADDRESS, emailAddress);
                putIntoUserData(User.ALL_TIME_STEPS, new JSONObject("{}"));
                putIntoUserData(User.ALL_WALKS_AND_RUNS, new JSONObject("{}"));
                putIntoUserData(User.ALL_TIME_STEP_GOALS, new JSONObject("{}"));

                // Set up user to have no friends.
                putIntoFriendsData(User.EMAIL_ADDRESS, emailAddress);
                putIntoFriendsData(User.FRIENDS_LIST, new JSONObject("{}"));
                putIntoFriendsData(User.SENT_FRIEND_REQUESTS, new JSONObject("{}"));
                putIntoFriendsData(User.RECEIVED_FRIEND_REQUESTS, new JSONObject("{}"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            updateStorage();
        }
    }

    protected void updateStorage() {
        updateFriendStorage();
        updateUserStorage();
    }

    protected void updateFriendStorage() {
        localStorage.setFriendsData(friendsData);
        localStorage.pushFriendsData();

        cloudStorage.setFriendsData(friendsData);
        cloudStorage.pushFriendsData();
    }

    protected void updateUserStorage() {
        localStorage.setUserData(userData);
        localStorage.pushUserData();

        cloudStorage.setUserData(userData);
        cloudStorage.pushUserData();
    }

    /**
     * Overwrite any previous value associated with this key with a new provided value.
     * @param key the key to set new data to.
     * @param value the new value associated with this key.
     */
    public <T> void putIntoUserData(String key, T value) {
        try {
            userData.put(key, value);
            updateUserStorage();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public <T> T getFromUserData(String key) {
        try {
            return (T) userData.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public long getLongFromUserData(String key) {
        try {
            return userData.getLong(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public <T> void putIntoFriendsData(String key, T value) {
        try {
            friendsData.put(key, value);
            updateFriendStorage();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public <T> T getFromFriendsData(String key) {
        try {
            return (T) friendsData.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public long getLongFromFriendsData(String key) {
        try {
            return userData.getLong(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void printStorage() {
        System.out.println(userData.toString());
        System.out.println(friendsData.toString());
    }

}
