package edu.ucsd.cse110.personalbestappteam24;

import android.content.Context;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.tools.ant.Main;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import edu.ucsd.cse110.personalbestappteam24.activities.HistoryActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.HeightActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.WalkOrRunActivity;
import edu.ucsd.cse110.personalbestappteam24.user.User;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class HistoryTests {

    HistoryActivity historyActivity;
    WalkOrRunActivity walkOrRunActivity;
    MainActivity mainActivity;
    TextView walkRun[] = new TextView[5];
    Button gotoHistoryActivity;
    Button startRun;
    Button endRun;

    @Before
    public void resetUserHistoryData() {

        mainActivity = Robolectric.setupActivity(MainActivity.class);
        historyActivity = Robolectric.setupActivity(HistoryActivity.class);

        walkRun[0] = (TextView) historyActivity.findViewById(R.id.walkRun1);
        walkRun[1] = (TextView) historyActivity.findViewById(R.id.walkRun2);
        walkRun[2] = (TextView) historyActivity.findViewById(R.id.walkRun3);
        walkRun[3] = (TextView) historyActivity.findViewById(R.id.walkRun4);
        walkRun[4] = (TextView) historyActivity.findViewById(R.id.walkRun5);

        gotoHistoryActivity = mainActivity.findViewById(R.id.button_history);
        startRun = mainActivity.findViewById(R.id.button_start);
    }

    @Test
    public void submitHistory() {

        int beforeRunHistoryCount = 0;
        for (int i = 0 ; i < walkRun.length ; i++) {
            if (walkRun[i].getText().equals("") == false) {
                beforeRunHistoryCount++;
            }
        }

        startRun.performClick();

        walkOrRunActivity = Robolectric.setupActivity(WalkOrRunActivity.class);
        endRun = walkOrRunActivity.findViewById(R.id.EndWalkButton);

        endRun.performClick();


        historyActivity = Robolectric.setupActivity(HistoryActivity.class);

        walkRun[0] = (TextView) historyActivity.findViewById(R.id.walkRun1);
        walkRun[1] = (TextView) historyActivity.findViewById(R.id.walkRun2);
        walkRun[2] = (TextView) historyActivity.findViewById(R.id.walkRun3);
        walkRun[3] = (TextView) historyActivity.findViewById(R.id.walkRun4);
        walkRun[4] = (TextView) historyActivity.findViewById(R.id.walkRun5);

        int afterRunHistoryCount = 0;
        for (int i = 0 ; i < walkRun.length ; i++) {
            if (walkRun[i].getText().equals("") == false) {
                afterRunHistoryCount++;
            }
        }

        assertEquals(beforeRunHistoryCount, 0);
        assertEquals(afterRunHistoryCount, 1);

    }

}
