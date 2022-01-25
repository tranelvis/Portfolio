package edu.ucsd.cse110.personalbestappteam24.MS1;

import android.content.Context;
import android.widget.Button;
import android.widget.Spinner;

import org.apache.tools.ant.Main;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import edu.ucsd.cse110.personalbestappteam24.R;
import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.HeightActivity;
import edu.ucsd.cse110.personalbestappteam24.user.User;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class HeightTests {

    Spinner feetSelection;
    Spinner inchSelection;
    Button submitHeight;

    @Before
    public void resetUserHeightData() {

        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);

        String emailAddress = "aditi@h.com";
        MainActivity.user.setHeight(-1);

        HeightActivity display = Robolectric.setupActivity(HeightActivity.class);
        feetSelection = (Spinner) display.findViewById(R.id.footDropDown);
        inchSelection = (Spinner) display.findViewById(R.id.inchDropDown);
        submitHeight = (Button) display.findViewById(R.id.submitHeight);
    }

    @Test
    public void submitHeight() {
        feetSelection.setSelection(5);
        inchSelection.setSelection(4);
        submitHeight.performClick();

        assertEquals(MainActivity.user.getHeight(), 64);
    }

}
