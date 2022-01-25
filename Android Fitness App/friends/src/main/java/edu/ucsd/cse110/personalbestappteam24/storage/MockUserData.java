package edu.ucsd.cse110.personalbestappteam24.storage;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;
import edu.ucsd.cse110.personalbestappteam24.report.DayData;
import edu.ucsd.cse110.personalbestappteam24.storage.CloudStorage;
import edu.ucsd.cse110.personalbestappteam24.storage.LocalStorage;
import edu.ucsd.cse110.personalbestappteam24.storage.Storage;
import edu.ucsd.cse110.personalbestappteam24.user.DailySteps;
import edu.ucsd.cse110.personalbestappteam24.user.User;
import edu.ucsd.cse110.personalbestappteam24.walk_run.WalkRun;

public class MockUserData extends Storage {


    public MockUserData(String emailAddress, Context context) {
        super(emailAddress, context);
    }

    public void createMockData() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        String date = format.format(calendar.getTime());

        MainActivity.user.getDailySteps().storeSteps(date, 1111);
        MainActivity.user.getStepGoals().storeStepGoal(date, 6000);
        DayData d1 = new DayData(MainActivity.user, date);
        if( !(d1.getIntentionalStepsForDate(date) > 0)) {
            WalkRun w1 = new WalkRun(0.05, "00:22:53", 500, calendar.getTime().toString(), 1.1);
            MainActivity.user.addWalkRun(w1, date);
        }

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        date = format.format(calendar.getTime());
        MainActivity.user.getDailySteps().storeSteps(date, 2222);
        MainActivity.user.getStepGoals().storeStepGoal(date, 5000);
        DayData d2 = new DayData(MainActivity.user, date);
        if( !(d2.getIntentionalStepsForDate(date) > 0)) {
            WalkRun w2 = new WalkRun(0.12, "00:12:53", 780, calendar.getTime().toString(), 10.5);
            MainActivity.user.addWalkRun(w2, date);
        }

        calendar.add(Calendar.DAY_OF_YEAR,      1);
        date = format.format(calendar.getTime());
        MainActivity.user.getDailySteps().storeSteps(date, 3333);
        MainActivity.user.getStepGoals().storeStepGoal(date, 5500);
        DayData d3 = new DayData(MainActivity.user, date);
        if( !(d3.getIntentionalStepsForDate(date) > 0)) {
            WalkRun w3 = new WalkRun(0.24, "01:12:53", 1500, calendar.getTime().toString(), 4.3);
            MainActivity.user.addWalkRun(w3, date);
        }
    }
}

