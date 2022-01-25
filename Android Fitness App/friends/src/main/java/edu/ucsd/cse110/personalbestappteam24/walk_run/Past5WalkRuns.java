package edu.ucsd.cse110.personalbestappteam24.walk_run;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;

public class Past5WalkRuns {

    WalkRun[] pastWalkRuns;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
    Calendar calendar;
    Date date;

    public Past5WalkRuns() {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        date = calendar.getTime();

        pastWalkRuns = new WalkRun[5];
        for (int i = 0; i < pastWalkRuns.length; i++) {
            pastWalkRuns[i] = null;
        }
    }

    public void addWalkRun(WalkRun walkRun) {
        updatePastWalkRuns();
        pastWalkRuns[0] = walkRun;
    }

    public void updatePastWalkRuns() {
        for (int i = pastWalkRuns.length-1; i > 0; i--) {
            if (pastWalkRuns[i-1] != null) {
                pastWalkRuns[i] = pastWalkRuns[i-1];
            }
        }
    }

    public WalkRun[] getPastWalkRuns() {
        return pastWalkRuns;
    }

    public void retrievePastWalkRuns() {
        int k = 0;
        //loops until date from the first week reaches today
        for (; k < 7; calendar.add(Calendar.DAY_OF_YEAR, 1), k++) {
            date = calendar.getTime();
            JSONArray walkRunsForDate = MainActivity.user.getWalksAndRuns().getWalksAndRunsForDate(dateFormatter.format(date));

            int length = 0;
            if (walkRunsForDate != null) {
                length = walkRunsForDate.length();
            }
            for (int i  = 0; i < length; i++) {
                try {
                    JSONObject walkRun = (JSONObject) walkRunsForDate.get(i);
                    WalkRun thisWalkRun = new WalkRun(walkRun);
                    addWalkRun(thisWalkRun);
                    System.out.println(length);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
