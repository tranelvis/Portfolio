package edu.ucsd.cse110.personalbestappteam24.report;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;
import edu.ucsd.cse110.personalbestappteam24.user.DailySteps;
import edu.ucsd.cse110.personalbestappteam24.user.StepGoals;
import edu.ucsd.cse110.personalbestappteam24.user.User;
import edu.ucsd.cse110.personalbestappteam24.user.WalksAndRuns;

public class DayData {

    User user;
    long daySteps;
    long stepGoal;
    long intentionalSteps;
    WalksAndRuns walksAndRuns;
    String date;

    /*public DayData( User user, String date ) {
        this.user = user;
        daySteps = user.getDailySteps().getStepsForDate( date );
        stepGoal = user.getStepGoals().getStepGoalForDate( date );
        intentionalSteps = getIntentionalStepsForDate(date);
        this.date = date;
    }*/

    public DayData(long daySteps, long stepGoal, WalksAndRuns walksAndRuns, String date) {
        this.daySteps = daySteps;
        this.stepGoal = stepGoal;
        this.date = date;
        this.walksAndRuns = walksAndRuns;
        intentionalSteps = getIntentionalStepsForDate(date);
    }

    public long getDaySteps() { return this.daySteps; }
    public long getDayStepGoal() { return this.stepGoal; }
    public String getDate() { return date; }
    public long getDayIntentionalSteps() {
        return intentionalSteps;
    }
    public void setDaySteps( long daySteps ) { this.daySteps = daySteps; }
    public void setStepGoal( long stepGoal ) { this.stepGoal = stepGoal; }

    public void setIntentionalSteps(long intentionalSteps) {
        this.intentionalSteps = intentionalSteps;
    }

    public long getIntentionalStepsForDate(String date) {
        long totalSteps = 0;
        JSONArray walkRunsForDate = walksAndRuns.getWalksAndRunsForDate(date);
        int length = 0;
        if (walkRunsForDate != null) {
            length = walkRunsForDate.length();
        }
        for (int i = 0; i < length; i++) {
            try {
                JSONObject walkRun = (JSONObject) walkRunsForDate.get(i);
                totalSteps += walkRun.getLong("WALK_RUN_STEPS_ELAPSED");
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return totalSteps;
    }

}
