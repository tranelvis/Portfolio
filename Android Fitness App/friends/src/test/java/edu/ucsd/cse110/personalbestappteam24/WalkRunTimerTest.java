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
public class WalkRunTimerTest {

    TextView timeText;
    Button startWalk;
    Button endWalk;

    @Before
    public void setup() {

        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        MainActivity.user.setHeight(60);
        WalkOrRunActivity walkOrRunActivity = Robolectric.setupActivity(WalkOrRunActivity.class);

        timeText = (TextView) walkOrRunActivity.findViewById(R.id.timeTextView);
        startWalk = (Button) mainActivity.findViewById(R.id.button_start);
        endWalk = (Button) walkOrRunActivity.findViewById(R.id.EndWalkButton);
    }

    @Test
    public void testWalkRunTimer() {

        startWalk.performClick();
        synchronized (this) {
            try {
                wait(10100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        endWalk.performClick();
        assertEquals(timeText.getText(), "00:00:10");
    }
}
