package edu.ucsd.cse110.personalbestappteam24.storage;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import edu.ucsd.cse110.personalbestappteam24.activities.DisplayFriendActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.ReportActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.ViewFriendActivity;
import edu.ucsd.cse110.personalbestappteam24.report.SetGraph;
import edu.ucsd.cse110.personalbestappteam24.user.DailySteps;
import edu.ucsd.cse110.personalbestappteam24.user.Friend;
import edu.ucsd.cse110.personalbestappteam24.user.FriendRequestMediator;
import edu.ucsd.cse110.personalbestappteam24.user.FriendRequests;
import edu.ucsd.cse110.personalbestappteam24.user.Friends;
import edu.ucsd.cse110.personalbestappteam24.user.MessageMediator;
import edu.ucsd.cse110.personalbestappteam24.user.StepGoals;
import edu.ucsd.cse110.personalbestappteam24.user.User;
import edu.ucsd.cse110.personalbestappteam24.user.WalksAndRuns;

public class CloudStorage {
    private JSONObject userData;
    private JSONObject friendsData;
    private DatabaseReference userDB;
    private DatabaseReference friendsDB;

    public final static String USER_DB = "USER_DB";
    public final static String FRIENDS_DB = "FRIENDS_DB";

    // Constructor for getting friend fitness data.
    public CloudStorage(int week, String emailAddress, DisplayFriendActivity displayFriendActivity) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        userDB = database.getReference(USER_DB).child(Integer.toString(emailAddress.hashCode()));
        setFriendSummaryListener(week, displayFriendActivity);
    }

    private void setFriendSummaryListener(final int week, final DisplayFriendActivity displayFriendActivity) {
        ValueEventListener userDataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Object value = dataSnapshot.getValue();

                    // Only update data if it was null to begin with. If the cloud ever changes,
                    // it would already be up to date with data.
                    if(value != null && userData == null) {
                        userData = new JSONObject((String) value);

                        JSONObject alltimeSteps = userData.getJSONObject(User.ALL_TIME_STEPS);
                        DailySteps dailySteps = new DailySteps(alltimeSteps);

                        // Initialize step goals.
                        JSONObject alltimeStepGoals = userData.getJSONObject(User.ALL_TIME_STEP_GOALS);
                        StepGoals stepGoals = new StepGoals(alltimeStepGoals);

                        JSONObject alltimeWalksAndRuns = userData.getJSONObject(User.ALL_WALKS_AND_RUNS);
                        WalksAndRuns walksAndRuns = new WalksAndRuns(alltimeWalksAndRuns);

                        SetGraph set = new SetGraph(displayFriendActivity, week * 7, false, dailySteps, stepGoals, walksAndRuns);
                        displayFriendActivity.updateUI(set);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        userDB.addValueEventListener(userDataListener);
    }


    // Constructor for adding messages.
    public CloudStorage(String content, String toEmailAddress, String fromEmailAddress, String operation) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        if(operation.equals(MessageMediator.ADD_MESSAGE)) {
            friendsDB = database.getReference(FRIENDS_DB).child(Integer.toString(toEmailAddress.hashCode()));
            this.setAddMessageListener(content, fromEmailAddress);
        }
    }


    private void setAddMessageListener(final String content, final String fromEmailAddress) {
        ValueEventListener friendsDataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Object value = dataSnapshot.getValue();

                    // Only update data if it was null to begin with. If the cloud ever changes,
                    // it would already be up to date with data.
                    if(value != null && friendsData == null) {
                        friendsData = new JSONObject((String) value);

                        // Add message from from user in conversation between these two friends.
                        Friends friends = new Friends(friendsData.getJSONObject(User.FRIENDS_LIST));
                        friends.getFriend(fromEmailAddress).getConversation().addMessage(content, fromEmailAddress);

                        // Put updated friends data.
                        friendsData.put(User.FRIENDS_LIST, friends.getFriendsData());
                        pushFriendsData();

                        Log.d("SENT_MESSAGE", "Message sent from " + fromEmailAddress + ": " + content);

                        friendsDB.removeEventListener(this);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        friendsDB.addValueEventListener(friendsDataListener);
    }


    // Constructor for making changes to friends through accepting and adding friend requests.
    public CloudStorage(String toEmailAddress, String toName, String fromEmailAddress, String fromName, String operation) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        if(operation.equals(FriendRequestMediator.ACCEPT_FRIEND_REQUEST)) {
            friendsDB = database.getReference(FRIENDS_DB).child(Integer.toString(fromEmailAddress.hashCode()));
            this.setAcceptFriendRequestListener(toEmailAddress, toName);
        }

        if(operation.equals(FriendRequestMediator.ADD_FRIEND_REQUEST)) {
            friendsDB = database.getReference(FRIENDS_DB).child(Integer.toString(toEmailAddress.hashCode()));
            this.setAddFriendRequestListener(fromEmailAddress, fromName);
        }

    }


    // For person that sent us a request, update his sent list and friend list.
    private void setAcceptFriendRequestListener(final String toEmailAddress, final String toName) {
        ValueEventListener friendsDataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Object value = dataSnapshot.getValue();

                    // Only update data if it was null to begin with. If the cloud ever changes,
                    // it would already be up to date with data.
                    if(value != null && friendsData == null) {
                        friendsData = new JSONObject((String) value);

                        // Get friend request data for sent from user.
                        JSONObject sentFriendRequestData = friendsData.getJSONObject(User.SENT_FRIEND_REQUESTS);
                        FriendRequests sentFriendRequests = new FriendRequests(sentFriendRequestData);

                        // Remove pending sent request from sent from user.
                        sentFriendRequests.removeFriendRequest(toEmailAddress);
                        sentFriendRequestData = sentFriendRequests.getFriendRequestsData();

                        // Put updated friend request data.
                        friendsData.put(User.SENT_FRIEND_REQUESTS, sentFriendRequestData);


                        // Getting friends list of sent from user
                        JSONObject allFriends = friendsData.getJSONObject(User.FRIENDS_LIST);
                        Friends friends = new Friends(allFriends);


                        // Adding to user as friend of from user.
                        friends.addFriend(new Friend(toEmailAddress, toName));
                        allFriends = friends.getFriendsData();

                        // Put updated friend request data.
                        friendsData.put(User.FRIENDS_LIST, allFriends);

                        pushFriendsData();

                        Log.d("ACCEPT_FRIEND_REQUEST", "Friend request updated on cloud");

                        friendsDB.removeEventListener(this);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        friendsDB.addValueEventListener(friendsDataListener);
    }

    private void setAddFriendRequestListener(final String fromEmailAddress, final String fromName) {
        ValueEventListener friendsDataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Object value = dataSnapshot.getValue();

                    // Only update data if it was null to begin with. If the cloud ever changes,
                    // it would already be up to date with data.
                    if(value != null && friendsData == null) {
                        friendsData = new JSONObject((String) value);

                        // Add 'from' user as a new received request in 'to' user data and update.
                        // Get friend request data for sent to user.
                        JSONObject receivedFriendRequestData = friendsData.getJSONObject(User.RECEIVED_FRIEND_REQUESTS);
                        FriendRequests receivedFriendRequests = new FriendRequests(receivedFriendRequestData);

                        // Add sent from user's friend request.
                        receivedFriendRequests.addFriendRequest(fromEmailAddress, fromName);
                        receivedFriendRequestData = receivedFriendRequests.getFriendRequestsData();

                        // Put updated friend request data.
                        friendsData.put(User.RECEIVED_FRIEND_REQUESTS, receivedFriendRequestData);
                        pushFriendsData();

                        Log.d("ADD_FRIEND_REQUEST", "Friend request updated on cloud");

                        friendsDB.removeEventListener(this);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        friendsDB.addValueEventListener(friendsDataListener);
    }

    private Storage storageSubject;
    public CloudStorage(String emailAddress, Storage storageSubject) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        this.storageSubject = storageSubject;

        userDB = database.getReference(USER_DB).child(Integer.toString(emailAddress.hashCode()));
        friendsDB = database.getReference(FRIENDS_DB).child(Integer.toString(emailAddress.hashCode()));
        pullData();
    }

    /**
     * Sets the data field with data stored on the cloud for this user. If the user doesn't exist,
     * then the state of data does not change.
     */
    private void pullData() {
        ValueEventListener userDataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Object value = dataSnapshot.getValue();

                    // Only update data if it was null to begin with. If the cloud ever changes,
                    // it would already be up to date with data.
                    if(value != null && userData == null) {
                        userData = new JSONObject((String) value);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ValueEventListener friendsDataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Object value = dataSnapshot.getValue();

                    // Only update data if it was null to begin with. If the cloud ever changes,
                    // it would already be up to date with data.
                    if(value != null) {
                        friendsData = new JSONObject((String) value);
                        storageSubject.setupFriendData();
                        Log.d("FRIEND DATA PULLED", "Friend data pulled from the cloud");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FRIEND DATA PULL ERR", "Error with pulling friend data from the cloud");

            }
        };

        userDB.addValueEventListener(userDataListener);
        friendsDB.addValueEventListener(friendsDataListener);
    }

    /**
     * Serializes the currently held data object and overwrites it to cloud storage if data is not null.
     */
    public void pushData(){
        pushUserData();
        pushFriendsData();
    }

    public void pushUserData() {
        if(userDB != null) {
            // Update the most recent storage time with the current unix time.
            try {
                userData.put(Storage.STORAGE_TIME, System.currentTimeMillis() / 1000L);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Push changes to the cloud.
            userDB.setValue(userData.toString());
        }
    }

    public void pushFriendsData() {
        if(friendsDB != null) {
            // Update the most recent storage time with the current unix time.
            try {
                friendsData.put(Storage.STORAGE_TIME, System.currentTimeMillis() / 1000L);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Push changes to the cloud.
            friendsDB.setValue(friendsData.toString());
        }
    }


    public JSONObject getUserData() {
        return userData;
    }

    public void setUserData(JSONObject userData) {
        this.userData = userData;
    }

    public JSONObject getFriendsData() {
        return friendsData;
    }

    public void setFriendsData(JSONObject friendsData) {
        this.friendsData = friendsData;
    }

    public long latestUpdateTime() {
        long userTimestamp = this.latestUserUpdateTime();
        long friendsTimestamp = this.latestFriendUpdateTime();
        return Math.max(userTimestamp, friendsTimestamp);
    }

    public long latestUserUpdateTime() {
        long userTimestamp = -1;
        if(userData != null) {
            try {
                userTimestamp = userData.getLong(Storage.STORAGE_TIME);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return userTimestamp;
    }

    public long latestFriendUpdateTime() {
        long friendsTimestamp = -1;
        if(friendsData != null) {
            try {
                friendsTimestamp = friendsData.getLong(Storage.STORAGE_TIME);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return friendsTimestamp;
    }

    /**
     * Determines whether or not this email address has locally available storage.
     */
    public boolean exists() {
        return userData != null && friendsData != null;
    }
}
