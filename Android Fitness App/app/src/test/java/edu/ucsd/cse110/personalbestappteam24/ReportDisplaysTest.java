package edu.ucsd.cse110.personalbestappteam24;

import android.content.Context;
import android.widget.Button;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.CombinedChart;
import com.google.firebase.database.core.Repo;

import org.apache.tools.ant.Main;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.HeightActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.MonthlyActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.ReportActivity;
import edu.ucsd.cse110.personalbestappteam24.user.User;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class ReportDisplaysTest {


    /* SCENARIO: VIEWING FITNESS HISTORY */

    @Test
    public void ReportTest() {


        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        ReportActivity reportActivity = Robolectric.setupActivity(ReportActivity.class);
        MonthlyActivity monthlyActivity = Robolectric.setupActivity(MonthlyActivity.class);

        //open history page
        Button historyBtn = mainActivity.findViewById(R.id.button_stats);
        historyBtn.performClick();

        //test if weekly format, show last 7 days
        assertEquals(reportActivity.getSetGraph().getDaysToDisplay(), 7);


        //open monthly history
        Button month = reportActivity.findViewById(R.id.monthlyBtn);
        month.performClick();

        //test if last 28 days will be shown
        assertEquals(monthlyActivity.getSetGraph().getDaysToDisplay(), 28);

    }

}
