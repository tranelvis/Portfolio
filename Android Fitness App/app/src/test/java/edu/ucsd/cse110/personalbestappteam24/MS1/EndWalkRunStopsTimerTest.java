package edu.ucsd.cse110.personalbestappteam24.MS1;

import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import edu.ucsd.cse110.personalbestappteam24.R;
import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.SettingActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.WalkOrRunActivity;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class EndWalkRunStopsTimerTest {


    TextView timeText;
    Button startWalk;
    Button endWalk;


    @Before
    public void setupUser() {
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        MainActivity.user.setHeight(60);
        WalkOrRunActivity walkOrRunActivity = Robolectric.setupActivity(WalkOrRunActivity.class);

        timeText = (TextView) walkOrRunActivity.findViewById(R.id.timeTextView);
        startWalk = (Button) mainActivity.findViewById(R.id.button_start);
        endWalk = (Button) walkOrRunActivity.findViewById(R.id.EndWalkButton);

    }

    @Test
    public void testEndWalkTimer() {

        startWalk.performClick();
        synchronized (this) {
            try {
                wait(2100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        endWalk.performClick();
        String timer = timeText.getText().toString();
        synchronized (this) {
            try {
                wait(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertEquals(timeText.getText(), timer);
    }
}
