package edu.ucsd.cse110.personalbestappteam24.storage;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class LocalStorage  {
    private String emailAddress;
    private JSONObject data;
    private Context context;

    public LocalStorage() {

    }

    public LocalStorage(String emailAddress, Context context) {
        this.emailAddress = emailAddress;
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
        if(sharedPreferences.contains(emailAddress)) {
            String jsonString = sharedPreferences.getString(emailAddress, null);
            try {
                data = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Serializes the currently held data object and overwrites it to storage if data is not null.
     */
    public void pushData() {
        if(data != null) {
            // Update the most recent storage time with the current unix time.
            try {
                data.put(Storage.STORAGE_TIME, System.currentTimeMillis() / 1000L);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Push changes locally.
            SharedPreferences sharedPreferences = this.context.getSharedPreferences(emailAddress, Context.MODE_PRIVATE);
            String serializedData = this.data.toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(emailAddress, serializedData);
            editor.apply();
        }
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public long latestUpdateTime() {
        long toReturn = -1;

        if(data == null) return toReturn;

        try {
            toReturn = data.getLong(Storage.STORAGE_TIME);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return toReturn;
    }

    /**
     * Determines whether or not this email address has locally available storage.
     */
    public boolean exists() {
        return data != null;
    }
}
