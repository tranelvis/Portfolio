package edu.ucsd.cse110.personalbestappteam24;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import org.apache.tools.ant.Main;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;

import edu.ucsd.cse110.personalbestappteam24.activities.FriendActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.HeightActivity;
import edu.ucsd.cse110.personalbestappteam24.friends.Tab2Pending;
import edu.ucsd.cse110.personalbestappteam24.storage.MockFriendsData;
import edu.ucsd.cse110.personalbestappteam24.user.Friend;
import edu.ucsd.cse110.personalbestappteam24.user.FriendRequest;
import edu.ucsd.cse110.personalbestappteam24.user.FriendRequests;
import edu.ucsd.cse110.personalbestappteam24.user.User;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class SendFriendRequestTest {

    /* Scenario: USER A SENDS REQUEST TO USER B */

    MainActivity mainActivity;
    FriendActivity friendActivity;
    Button friends;
    Button add;
    TextView friendEmail;
    int sentBefore;

    @Before
    public void setUp() {
        //Open friends page from home screen
        mainActivity = Robolectric.setupActivity(MainActivity.class);
        friends = mainActivity.findViewById(R.id.button_friends);
        friends.performClick();


        friendActivity = Robolectric.buildActivity(FriendActivity.class).create().start().get();
        add = friendActivity.findViewById(R.id.addBtn);
        friendEmail = friendActivity.findViewById(R.id.friendemail);

        //number of sent requests before
        sentBefore = MainActivity.user.getSentRequestListSize();

    }

    @Test
    public void testSendRequest() {

        //send new friend request
        String friendName = "test1";
        friendEmail.setText(friendName);
        add.performClick();

        //number of sent requests after
        int sentAfter = MainActivity.user.getSentRequestListSize();

        //before should be 1 less than after
        assertEquals(sentBefore + 1, sentAfter);

        //get list of sent requests, last one should be request just sent out
        ArrayList<FriendRequest> sentRequests = MainActivity.user.getSentFriendRequests();
        assertEquals(friendName, sentRequests.get(sentRequests.size()-1).getEmailAddress());


    }
}