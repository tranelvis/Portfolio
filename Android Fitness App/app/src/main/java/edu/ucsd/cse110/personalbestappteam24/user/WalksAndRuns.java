package edu.ucsd.cse110.personalbestappteam24.user;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.ucsd.cse110.personalbestappteam24.walk_run.WalkRun;

public class WalksAndRuns {
    private JSONObject alltimeWalksAndRuns;
    private String date;

    public WalksAndRuns(JSONObject alltimeWalksAndRuns) {
        // Get the current date.
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        date = formatter.format(calendar.getTime());

        this.alltimeWalksAndRuns = alltimeWalksAndRuns;

        // If the user has not taken a walk today, add a new empty array of walks.
        if(!this.alltimeWalksAndRuns.has(date)) {
            try {
                this.alltimeWalksAndRuns.put(date, new JSONArray());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Add a walk run to the array of walks and runs.
     */
    public void addWalkRun(WalkRun walkRun) {
        JSONObject walkRunData = walkRun.getWalkRunData();

        try {
            // Add the walk run data to the existing walk run array for this date.
            JSONArray walksAndRunsForDate = (JSONArray) this.alltimeWalksAndRuns.get(date);
            walksAndRunsForDate.put(walkRunData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //TESTING
    public void addWalkRun(WalkRun walkRun, String date) {
        if(!this.alltimeWalksAndRuns.has(date)) {
            try {
                this.alltimeWalksAndRuns.put(date, new JSONArray());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        JSONObject walkRunData = walkRun.getWalkRunData();

        try {
            // Add the walk run data to the existing walk run array for this date.
            JSONArray walksAndRunsForDate = (JSONArray) this.alltimeWalksAndRuns.get(date);
            walksAndRunsForDate.put(walkRunData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Get the array of walks and runs for this current date.
     */
    public JSONArray getWalksAndRuns() {
        return getWalksAndRunsForDate(date);
    }

    /**
     * Get the array of walks and runs for a specific date.
     */
    public JSONArray getWalksAndRunsForDate(String date) {
        try {
            return (JSONArray) alltimeWalksAndRuns.get(date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getAlltimeWalksAndRuns() {
        return alltimeWalksAndRuns;
    }
}
