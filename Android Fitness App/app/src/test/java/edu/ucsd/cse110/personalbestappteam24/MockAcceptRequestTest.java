package edu.ucsd.cse110.personalbestappteam24;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import edu.ucsd.cse110.personalbestappteam24.activities.SettingActivity;
import edu.ucsd.cse110.personalbestappteam24.friends.Tab2Pending;
import edu.ucsd.cse110.personalbestappteam24.storage.MockFriendsData;
import edu.ucsd.cse110.personalbestappteam24.user.Friend;
import edu.ucsd.cse110.personalbestappteam24.user.FriendRequest;
import edu.ucsd.cse110.personalbestappteam24.user.FriendRequests;
import edu.ucsd.cse110.personalbestappteam24.user.User;

import static org.junit.Assert.assertEquals;

/* SCENARIO: USER A ACCEPTS USER B REQUEST */

@RunWith(RobolectricTestRunner.class)
public class MockAcceptRequestTest {

    MainActivity mainActivity;
    FriendActivity friendActivity;
    Button friend;

    int reqBefore;

    @Before
    public void sendRequest() {
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        SettingActivity settingActivity = Robolectric.setupActivity(SettingActivity.class);

        //give mock name
        EditText testName = settingActivity.findViewById(R.id.mockName);
        testName.setText("mockName");

        //store number of pending requests
        reqBefore = MainActivity.user.getPendingRequestListSize();

        //send mock request from settings
        Button mockReq = settingActivity.findViewById(R.id.mockRequestBtn);
        mockReq.performClick();

        //close activities
        friend = mainActivity.findViewById(R.id.button_friends);
        friend.performClick();

        friendActivity = Robolectric.buildActivity(FriendActivity.class).create().start().get();
        friendActivity.finish();
        mainActivity.finish();


    }

    /* test if request was received */
    @Test
    public void testReceivedRequest() {

        //open activities
        mainActivity = Robolectric.setupActivity(MainActivity.class);
        friend = mainActivity.findViewById(R.id.button_friends);
        friend.performClick();

        friendActivity = Robolectric.buildActivity(FriendActivity.class).create().start().get();
        friendActivity.finish();

        //check number of pending requests after mock request
        int reqAfter = MainActivity.user.getPendingRequestListSize();

        //should be one more than before
        assertEquals(reqAfter, reqBefore + 1);
    }

    /* accept request, test if friend is added */
    @Test
    public void testAcceptRequest() {

        //open activities
        mainActivity = Robolectric.setupActivity(MainActivity.class);
        friend = mainActivity.findViewById(R.id.button_friends);
        friend.performClick();

        friendActivity = Robolectric.buildActivity(FriendActivity.class).create().start().get();
        friendActivity.finish();

        //get list of received request, store most recent request
        ArrayList<FriendRequest> receivedRequests = MainActivity.user.getReceivedFriendRequests();
        FriendRequest friendRequest = receivedRequests.get(receivedRequests.size()-1);

        //store number of friends before accepting
        int friendsBefore = MainActivity.user.getFriendListSize();


        //accept most recent request
        MainActivity.user.acceptFriendRequest(friendRequest.getEmailAddress(), friendRequest.getEmailAddress());

        //check number of friends after
        int friendsAfter = MainActivity.user.getFriendListSize();

        //should have one more friend
        assertEquals(friendsAfter, friendsBefore + 1);
    }
}