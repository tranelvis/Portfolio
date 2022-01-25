package edu.ucsd.cse110.personalbestappteam24;

import android.widget.SeekBar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.SettingActivity;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class ManualStepGoalTest {

    SeekBar slider;

    @Before
    public void setupUser() {
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        SettingActivity settingActivity = Robolectric.setupActivity(SettingActivity.class);

        slider = settingActivity.findViewById(R.id.seekBar_goal);

    }

    @Test
    public void setNewGoalTest() {
        slider.setProgress(5786);

        assertEquals(MainActivity.user.getStepGoal(), 5786);
    }
}
