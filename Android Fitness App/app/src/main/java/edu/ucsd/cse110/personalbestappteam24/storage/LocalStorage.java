package edu.ucsd.cse110.personalbestappteam24.storage;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class LocalStorage  {
    private String emailAddress;
    private JSONObject userData;
    private JSONObject friendsData;
    private Context context;

    public final static String USER_DATA = "USER_DATA";
    public final static String FRIENDS_DATA = "FRIENDS_DATA";
    private String userDataKey;
    private String friendsDataKey;

    public LocalStorage() {

    }

    public LocalStorage(String emailAddress, Context context) {
        this.emailAddress = emailAddress;

        userDataKey = emailAddress + "." + USER_DATA;
        friendsDataKey = emailAddress + "." + FRIENDS_DATA;

        this.context = context;
        this.pullData();
    }

    /**
     * Sets the data field with data stored locally for this user. If the user doesn't exist locally,
     * then the state of data does not change.
     */
    private void pullData() {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(emailAddress, Context.MODE_PRIVATE);

        // Data for this user already exists on this device.
        if(sharedPreferences.contains(userDataKey)) {
            String jsonString = sharedPreferences.getString(userDataKey, null);
            try {
                userData = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Data for this user's friends already exists on this device.
        if(sharedPreferences.contains(friendsDataKey)) {
            String jsonString = sharedPreferences.getString(friendsDataKey, null);
            try {
                friendsData = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Serializes the currently held data objects and overwrites it to storage if data is not null.
     */
    public void pushData() {
        pushUserData();
        pushFriendsData();
    }

    public void pushFriendsData() {
        if(friendsData != null) {
            // Update the most recent storage time with the current unix time.
            try {
                friendsData.put(Storage.STORAGE_TIME, System.currentTimeMillis() / 1000L);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Push changes locally.
            SharedPreferences sharedPreferences = this.context.getSharedPreferences(emailAddress, Context.MODE_PRIVATE);
            String serializedData = this.friendsData.toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(friendsDataKey, serializedData);
            editor.apply();
        }
    }

    public void pushUserData() {
        if(userData != null) {
            // Update the most recent storage time with the current unix time.
            try {
                userData.put(Storage.STORAGE_TIME, System.currentTimeMillis() / 1000L);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Push changes locally.
            SharedPreferences sharedPreferences = this.context.getSharedPreferences(emailAddress, Context.MODE_PRIVATE);
            String serializedData = this.userData.toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(userDataKey, serializedData);
            editor.apply();
        }
    }

    public JSONObject getUserData() {
        return userData;
    }

    public void setUserData(JSONObject userData) {
        this.userData = userData;
    }

    public JSONObject getFriendsData() {
        return friendsData;
    }

    public void setFriendsData(JSONObject friendsData) {
        this.friendsData = friendsData;
    }

    public long latestUpdateTime() {
        long userTimestamp = this.latestUserUpdateTime();
        long friendsTimestamp = this.latestFriendUpdateTime();
        return Math.max(userTimestamp, friendsTimestamp);
    }

    public long latestUserUpdateTime() {
        long userTimestamp = -1;
        if(userData != null) {
            try {
                userTimestamp = userData.getLong(Storage.STORAGE_TIME);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return userTimestamp;
    }

    public long latestFriendUpdateTime() {
        long friendsTimestamp = -1;
        if(friendsData != null) {
            try {
                friendsTimestamp = friendsData.getLong(Storage.STORAGE_TIME);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return friendsTimestamp;
    }


    /**
     * Determines whether or not this email address has locally available storage.
     */
    public boolean exists() {
        return userData != null && friendsData != null;
    }
}
