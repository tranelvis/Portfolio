package edu.ucsd.cse110.personalbestappteam24.storage;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class CloudStorage {
    private JSONObject data;
    private DatabaseReference db;

    public CloudStorage() {

    }

    public CloudStorage(String emailAddress) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        db = database.getReference(Integer.toString(emailAddress.hashCode()));
        pullData();
    }

    /**
     * Sets the data field with data stored on the cloud for this user. If the user doesn't exist,
     * then the state of data does not change.
     */
    private void pullData() {
        ValueEventListener dataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Object value = dataSnapshot.getValue();

                    // Only update data if it was null to begin with. If the cloud ever changes,
                    // it would already be up to date with data.
                    if(value != null && data == null) {
                        data = new JSONObject((String) value);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        db.addValueEventListener(dataListener);
    }

    /**
     * Serializes the currently held data object and overwrites it to cloud storage if data is not null.
     */
    public void pushData(){
        if(data != null) {
            // Update the most recent storage time with the current unix time.
            try {
                data.put(Storage.STORAGE_TIME, System.currentTimeMillis() / 1000L);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Push changes to the cloud.
            db.setValue(data.toString());
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
