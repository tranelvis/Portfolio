package edu.ucsd.cse110.personalbestappteam24.user;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class stores the total step count taken for each day that the user has taken steps. It
 * does not take into account what kind of walk the user has been going on.
 */
public class DailySteps {
    private JSONObject alltimeSteps;
    private String date;

    public DailySteps(JSONObject alltimeSteps) {
        // Get the current date.
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        date = formatter.format(calendar.getTime());

        this.alltimeSteps = alltimeSteps;


        // Add this date to the alltimeSteps object if it has not already
        try {
            if(!this.alltimeSteps.has(date)) {
                this.alltimeSteps.put(date, 0);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the total steps for this day.
     */
    public void updateSteps(long steps) {
        try {
            alltimeSteps.put(date, steps);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the total steps for this day.
     */
    public long getSteps() {
        try {
            return alltimeSteps.getLong(date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Returns the total steps for a specific day.
     */
    public long getStepsForDate(String date) {
        try {
            return alltimeSteps.getLong(date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Returns a JSONObject containing all daily step counts ever for this user.
     */
    public JSONObject getAlltimeSteps() {
        return this.alltimeSteps;
    }

    /**
     * Returns the current date string.
     */
    public String getDate() {
        return this.date;
    }


    //TESTING
    public void storeSteps(String date, long steps) {
        try {
            alltimeSteps.put(date, steps);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
