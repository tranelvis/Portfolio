package edu.ucsd.cse110.personalbestappteam24;

import android.app.Activity;
import android.support.design.widget.TabItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.apache.tools.ant.Main;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.ucsd.cse110.personalbestappteam24.activities.ChatWithFriendActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.EncourageActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.FriendActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.SettingActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.WalkOrRunActivity;
import edu.ucsd.cse110.personalbestappteam24.user.DailySteps;
import edu.ucsd.cse110.personalbestappteam24.user.StepGoals;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class EncouragementTest {

    TextView text;
    String today;
    String yesterday;
    String dayBeforeYesterday;
    DailySteps ds;
    StepGoals sg;

    @Before
    public void setup() {
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        today = formatter.format(calendar.getTime());
        calendar.add(Calendar.DATE, -1);
        yesterday = formatter.format(calendar.getTime());
        calendar.add(Calendar.DATE, -1);
        dayBeforeYesterday = formatter.format(calendar.getTime());
        ds = MainActivity.user.getDailySteps();
        sg = MainActivity.user.getStepGoals();
        ds.storeSteps(dayBeforeYesterday, 1000);
        ds.storeSteps(yesterday, 1500);
        ds.storeSteps(today, 3000);
        sg.storeStepGoal(yesterday, 1000);
    }

    @Test
    public void testCaseOne() {
        EncourageActivity encourageActivity = Robolectric.setupActivity(EncourageActivity.class);
        text = encourageActivity.findViewById(R.id.textView_encourage);
        assertEquals(text.getText().toString(), "Nice! You have met your step goal yesterday!");
    }

    @Test
    public void testCaseTwo() {
        ds.storeSteps(yesterday, 2500);
        sg.storeStepGoal(yesterday, 3000);
        EncourageActivity encourageActivity = Robolectric.setupActivity(EncourageActivity.class);
        text = encourageActivity.findViewById(R.id.textView_encourage);
        assertEquals(text.getText().toString(), "Good! You have made a big progress than the day before!");
    }

    @Test
    public void testCaseThree() {
        ds.storeSteps(yesterday, 1500);
        sg.storeStepGoal(yesterday, 2000);
        EncourageActivity encourageActivity = Robolectric.setupActivity(EncourageActivity.class);
        text = encourageActivity.findViewById(R.id.textView_encourage);
        assertEquals(text.getText().toString(), "Don't give up! Keep working towards your step goal!");
    }
}
