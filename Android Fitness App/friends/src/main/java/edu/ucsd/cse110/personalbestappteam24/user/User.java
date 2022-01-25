package edu.ucsd.cse110.personalbestappteam24.user;

import android.content.Context;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import edu.ucsd.cse110.personalbestappteam24.storage.MockUserData;

import edu.ucsd.cse110.personalbestappteam24.storage.LocalStorage;
import edu.ucsd.cse110.personalbestappteam24.storage.Storage;
import edu.ucsd.cse110.personalbestappteam24.walk_run.Past5WalkRuns;
import edu.ucsd.cse110.personalbestappteam24.walk_run.WalkRun;

public class User {
    private String emailAddress;
    //private Storage storage;
    private Storage storage;
    private DailySteps dailySteps;
    private StepGoals stepGoals;
    private boolean isNotified;
    private String lastNotifiedDate;
    private String lastEncourageDate;
    private WalksAndRuns walksAndRuns;
    private Past5WalkRuns pastWalkRuns;

    public static final String ALL_TIME_STEPS = "ALL_TIME_STEPS";
    public static final String CURR_STEP_GOAL = "CURR_STEP_GOAL";
    public static final String EMAIL_ADDRESS = "EMAIL_ADDRESS";
    public static final String HEIGHT = "HEIGHT";
    public static final String ALL_WALKS_AND_RUNS = "ALL_WALKS_AND_RUNS";
    public static final String ALL_TIME_STEP_GOALS = "ALL_TIME_STEP_GOALS";
    public static final long DEFAULT_STEP_GOAL = 5000;


    public User(String emailAddress, Context context) {
        this.emailAddress = emailAddress;

        // Initialize storage.
        Intent intent = new Intent(context, LocalStorage.class);

        //storage = new Storage(this.emailAddress, context);
        storage = new MockUserData(this.emailAddress, context);

        // Initialize daily steps.
        JSONObject alltimeSteps = this.storage.get(ALL_TIME_STEPS);
        dailySteps = new DailySteps(alltimeSteps);

        // Initialize step goals.
        JSONObject alltimeStepGoals = this.storage.get(ALL_TIME_STEP_GOALS);
        stepGoals = new StepGoals(alltimeStepGoals);

        // Set the lastEncourageDate to today
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        lastEncourageDate = formatter.format(calendar.getTime());

        // Set the lastNotifiedDate to yesterday
        calendar.add(Calendar.DATE, -1);
        lastNotifiedDate = formatter.format(calendar.getTime());

        // User is not notified his goal achievement yet when initializing
        isNotified = false;
      
        // Initialize walks and runs.
        JSONObject alltimeWalksAndRuns = this.storage.get(ALL_WALKS_AND_RUNS);
        walksAndRuns = new WalksAndRuns(alltimeWalksAndRuns);
        pastWalkRuns = new Past5WalkRuns();
    }

    /**
     * Retrieves the current step goal for a user. If it is not set, returns the default step
     * goal.
     * @return user's current step goal of default step goal value.
     */
    public long getStepGoal() {
        return this.storage.getLong(CURR_STEP_GOAL);
    }

    /**
     * Sets the step goal to a given valid value. If the value is invalid, the step
     * goal does not change from what it was previously.
     * @param stepGoal a new step goal to set to.
     */
    public void setStepGoal(long stepGoal) {
        if(stepGoal > 0) {
            // Update the current step goal as well as in alltimestepgoals object.
            this.storage.put(CURR_STEP_GOAL, stepGoal);
            this.stepGoals.updateStepGoal(stepGoal);
            this.clearNotified();
        }
    }

    public StepGoals getStepGoals() {
        return this.stepGoals;
    }

    public DailySteps getDailySteps() {
        return this.dailySteps;
    }

    public void setDailySteps(long totalDailySteps) {
        // Updates the steps taken for this day
        this.dailySteps.updateSteps(totalDailySteps);
        this.storage.put(ALL_TIME_STEPS, dailySteps.getAlltimeSteps());

        // Stores the step goal that took place today (even if it did not change from previous days)
        if(!stepGoals.isStepGoalSet()) {
            stepGoals.updateStepGoal(getStepGoal());
            this.storage.put(ALL_TIME_STEP_GOALS, stepGoals.getAlltimeStepGoals());
        }
    }

    public void setHeight(int height) {
        this.storage.put(HEIGHT, height);
    }

    public int getHeight() {
        return this.storage.get(HEIGHT);
    }

    public double getStride() {
        double inches = 0.413 * getHeight();
        double feet = inches / 12;
        double miles = feet / 5280;
        return miles;
    }


    public void updateLastEncourageDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        lastEncourageDate = formatter.format(calendar.getTime());
    }

    public String getLastEncourageDate() {
        return lastEncourageDate;
    }

    public void updateLastNotifiedDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        lastNotifiedDate = formatter.format(calendar.getTime());
    }

    public String getLastNotifiedDate() {
        return lastNotifiedDate;
    }

    public void setNotified() {
        isNotified = true;
        updateLastNotifiedDate();
        //System.out.println(lastNotifiedDate);
    }

    public void clearNotified() { isNotified = false; }

    public boolean isNotified() { return isNotified; }

    public void addWalkRun(JSONObject walkRunData) {
        JSONObject allWalksAndRuns = this.storage.get(ALL_WALKS_AND_RUNS);
        String currentDate = dailySteps.getDate();

        // If the user has not taken a walk today, add a new empty array of walks.
        if (!allWalksAndRuns.has(currentDate)) {
            try {
                allWalksAndRuns.put(ALL_WALKS_AND_RUNS, new JSONArray());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void addWalkRun(WalkRun walkRun) {
        walksAndRuns.addWalkRun(walkRun);
        this.storage.put(ALL_WALKS_AND_RUNS, walksAndRuns.getAlltimeWalksAndRuns());


        //Log.d("TESTING", walksAndRuns.getWalksAndRuns().toString());
    }

    //TESTING
    public void addWalkRun(WalkRun walkRun, String date) {
        walksAndRuns.addWalkRun(walkRun, date);
        this.storage.put(ALL_WALKS_AND_RUNS, walksAndRuns.getAlltimeWalksAndRuns());
        //this.getPastWalkRuns().addWalkRun(walkRun);
    }

    public WalksAndRuns getWalksAndRuns() {
        return this.walksAndRuns;
    }

    public Past5WalkRuns getPastWalkRuns() {
        return pastWalkRuns;
    }

    //TESTING
    public Storage getStorage() {
        return storage;
    }


}
