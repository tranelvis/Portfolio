package edu.ucsd.cse110.personalbestappteam24;
import android.content.Context;

import edu.ucsd.cse110.personalbestappteam24.report.DayData;


import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;


import org.robolectric.Robolectric;

import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;

import edu.ucsd.cse110.personalbestappteam24.storage.Storage;

import edu.ucsd.cse110.personalbestappteam24.walk_run.WalkRun;

import static junit.framework.TestCase.assertEquals;


public class WeeklySummaryTests {

    private Context context;
    MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
    String emailAddress = "yic258@email.com";
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");

    public void testDayData() {
        Calendar cal = Calendar.getInstance();
        MainActivity.user.setDailySteps(5000);
        Date date = cal.getTime();
        DayData day1 = new DayData(MainActivity.user, dateFormatter.format(date) );
        assertEquals( day1.getDayIntentionalSteps(), MainActivity.user.getDailySteps());
        Storage storage = MainActivity.user.getStorage();
        WalkRun w1 = new WalkRun(0.05, "00:22:53", 500, cal.getTime().toString(), 1.1);
        MainActivity.user.addWalkRun( w1, dateFormatter.format(date) );
        assertEquals( MainActivity.user.getDailySteps(), 5500 ) ;
        assertEquals( day1.getDaySteps(), MainActivity.user.getDailySteps().getStepsForDate(dateFormatter.format(date)) );
    }

    public void testWeeklyReport() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        String date = format.format(calendar.getTime());

        for( int i = 0, stepgoal = 5000; i < 7; i++, calendar.add(Calendar.DAY_OF_YEAR, 1), stepgoal += 500) {

            MainActivity.user.getDailySteps().storeSteps(date, 2000);
            MainActivity.user.getStepGoals().storeStepGoal(date, stepgoal);
        }

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.add(Calendar.DAY_OF_YEAR, 4);
        DayData Thursday = new DayData( MainActivity.user, date);
        assertEquals( Thursday.getDaySteps(), MainActivity.user.getDailySteps().getStepsForDate(date));

    }
}
