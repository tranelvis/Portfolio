package edu.ucsd.cse110.personalbestappteam24.MS1;

import android.app.Activity;

import org.apache.tools.ant.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;
import edu.ucsd.cse110.personalbestappteam24.user.User;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class HeightToStrideTest {

    @Before
    public void initUser() {
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        MainActivity.user.setHeight(72);
    }

    @Test
    public void conversionTest() {
        double inches = 0.413 * 72;
        double feet = inches / 12;
        double miles = feet / 5280;

        assertEquals(MainActivity.user.getStride(), miles, 0.1);
    }
}
