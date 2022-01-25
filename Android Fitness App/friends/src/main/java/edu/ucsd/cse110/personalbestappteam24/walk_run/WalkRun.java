package edu.ucsd.cse110.personalbestappteam24.walk_run;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;

public class WalkRun {
    public static final String WALK_RUN_DISTANCE = "WALK_RUN_DISTANCE";
    public static final String WALK_RUN_STEPS_ELAPSED = "WALK_RUN_STEPS_ELAPSED";
    public static final String WALK_RUN_TIME_ELAPSED = "WALK_RUN_TIME_ELAPSED";
    public static final String WALK_RUN_START_TIME = "WALK_RUN_START_TIME";
    public static final String WALK_RUN_SPEED = "WALK_RUN_SPEED";


    private Timer timer;
    private long startingSteps;

    JSONObject walkrunData;

    public WalkRun() {
        // Set fields to default values.
        startingSteps = MainActivity.user.getDailySteps().getSteps();

        String startTime = Calendar.getInstance().getTime().toString();

        // Begin the timer.
        timer = new Timer();

        try {
            walkrunData = new JSONObject("{}");
            walkrunData.put(WALK_RUN_DISTANCE, 0);
            walkrunData.put(WALK_RUN_TIME_ELAPSED, "0");
            walkrunData.put(WALK_RUN_STEPS_ELAPSED, 0);
            walkrunData.put(WALK_RUN_START_TIME, startTime);
            walkrunData.put(WALK_RUN_SPEED, 0);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public WalkRun(JSONObject walkrunData) {
        this.walkrunData = walkrunData;
    }

    //TESTING
    public WalkRun(double distance, String time, long steps, String startTime, double speed) {
        // Set fields to default values

        try {
            walkrunData = new JSONObject("{}");
            walkrunData.put(WALK_RUN_DISTANCE, distance);
            walkrunData.put(WALK_RUN_TIME_ELAPSED, time);
            walkrunData.put(WALK_RUN_STEPS_ELAPSED, steps);
            walkrunData.put(WALK_RUN_START_TIME, startTime);
            walkrunData.put(WALK_RUN_SPEED, speed);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Public access getters
    public String getTime() {
        try {
            return walkrunData.getString(WALK_RUN_TIME_ELAPSED);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "00:00:00";
    }


    public double getDistance() {
        try {
            return walkrunData.getDouble(WALK_RUN_DISTANCE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public double computeSpeed() {
        long elapsedSeconds = timer.getElapsedSeconds();
        if(elapsedSeconds == 0) return 0;
        return 3600 * getDistance() / elapsedSeconds;
    }

    public double getSpeed() {
        try {
            return walkrunData.getDouble(WALK_RUN_SPEED);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    public long getSteps() {
        try {
            return walkrunData.getLong(WALK_RUN_STEPS_ELAPSED);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public String getStartTime() {
        try {
            return walkrunData.getString(WALK_RUN_START_TIME);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "0";
    }


    public JSONObject getWalkRunData() {
        return walkrunData;
    }

    // Public access setter
    public void updateData() {
        updateSteps();
        updateDistance();
        updateTime();
    }

    // Private field update members.
    private void updateDistance() {
        double updatedDistance = getSteps() * MainActivity.user.getStride();

        try {
            walkrunData.put(WALK_RUN_DISTANCE, updatedDistance);
            walkrunData.put(WALK_RUN_SPEED, computeSpeed());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateTime() {
        String updatedTime = timer.getElapsedTimeString();

        try {
            walkrunData.put(WALK_RUN_TIME_ELAPSED, updatedTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateSteps() {
        long updatedSteps = MainActivity.user.getDailySteps().getSteps() - startingSteps;

        try {
            walkrunData.put(WALK_RUN_STEPS_ELAPSED, updatedSteps);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
