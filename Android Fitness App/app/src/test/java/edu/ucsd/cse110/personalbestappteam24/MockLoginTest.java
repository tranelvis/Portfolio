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

import edu.ucsd.cse110.personalbestappteam24.activities.LoginActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.HeightActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.MonthlyActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.ReportActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.SettingActivity;
import edu.ucsd.cse110.personalbestappteam24.user.User;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class MockLoginTest {

    /* logging in with mock users */

    @Test
    public void loginTest() {

        LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);

        //login as A
        Button loginA = loginActivity.findViewById(R.id.buttonlogA);
        loginA.performClick();

        //emailAddress of user should be A
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        assertEquals(MainActivity.user.getEmailAddress(), "A");
        mainActivity.finish();



        //login as B
        Button loginB = loginActivity.findViewById(R.id.buttonlogB);
        loginB.performClick();

        //emailAddress should be B now
        mainActivity = Robolectric.setupActivity(MainActivity.class);
        assertEquals(MainActivity.user.getEmailAddress(), "B");

    }

}
