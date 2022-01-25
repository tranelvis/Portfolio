package edu.ucsd.cse110.personalbestappteam24;

import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.SettingActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.WalkOrRunActivity;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class MockAddingStepsWalkRunTest {

    TextView stepsText;
    Button startWalk;
    Button addSteps;

    @Before
    public void setup() {

        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        MainActivity.user.setHeight(60);
        WalkOrRunActivity walkOrRunActivity = Robolectric.setupActivity(WalkOrRunActivity.class);
        SettingActivity settingActivity = Robolectric.setupActivity(SettingActivity.class);

        stepsText = (TextView) walkOrRunActivity.findViewById(R.id.stepsTextView);
        startWalk = (Button) mainActivity.findViewById(R.id.button_start);
        addSteps = (Button) settingActivity.findViewById(R.id.button_change_steps);
    }

    @Test
    public void testWalkRunTimer() {

        startWalk.performClick();
        addSteps.performClick();
        addSteps.performClick();

        assertEquals(stepsText.getText(), "1000");
    }
}
