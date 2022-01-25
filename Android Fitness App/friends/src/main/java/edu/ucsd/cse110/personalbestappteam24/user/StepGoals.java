package edu.ucsd.cse110.personalbestappteam24.user;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This class stores all step goals that the user has had.
 */
public class StepGoals {
    private JSONObject alltimeStepGoals;
    private String date;

    public StepGoals(JSONObject alltimeStepGoals) {
        // Get the current date.
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        date = formatter.format(calendar.getTime());

        this.alltimeStepGoals = alltimeStepGoals;
    }

    public boolean isStepGoalSet() {
        return alltimeStepGoals.has(date);
    }

    /**
     * Updates the step goal for the current date.
     */
    public void updateStepGoal(long stepGoal) {
        try {
            alltimeStepGoals.put(date, stepGoal);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the step goal for the current date.
     */
    public long getCurrentStepGoal() {
        try {
            return alltimeStepGoals.getLong(date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Gets the step goal for a specific date.
     */
    public long getStepGoalForDate(String date) {
        try {
            return alltimeStepGoals.getLong(date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public JSONObject getAlltimeStepGoals() {
        return alltimeStepGoals;
    }

    public String getDate() {
        return date;
    }


    //for testing
    public void storeStepGoal(String date, long steps) {
        try {
            alltimeStepGoals.put(date, steps);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
