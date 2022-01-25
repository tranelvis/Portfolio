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

import edu.ucsd.cse110.personalbestappteam24.activities.ChatWithFriendActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.FriendActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.SettingActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.WalkOrRunActivity;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class MessageTest {

    String email;

    @Before
    public void setup() {

    }

    @Test
    public void testMessage() {
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        email = MainActivity.user.getEmailAddress();
        MainActivity.user.sendFriendRequest(email, email);
        MainActivity.user.acceptFriendRequest(email,email);
        MainActivity.user.sendMessage("testing messaging",email);
        String result = mainActivity.getContent(email);
        assertEquals(result, "testing messaging");
    }

    @Test
    public void testReceive() {
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        email = MainActivity.user.getEmailAddress();
        MainActivity.user.sendFriendRequest(email, email);
        MainActivity.user.acceptFriendRequest(email,email);
        MainActivity.user.sendMessage("testing receiving",email);
        String result = mainActivity.getContent(email);
        assertEquals(result, "testing receiving");
    }
}
