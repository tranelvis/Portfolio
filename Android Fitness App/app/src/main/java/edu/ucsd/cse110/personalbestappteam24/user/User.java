package edu.ucsd.cse110.personalbestappteam24.user;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import edu.ucsd.cse110.personalbestappteam24.activities.DisplayFriendActivity;
import edu.ucsd.cse110.personalbestappteam24.report.WeeklyReport;
import edu.ucsd.cse110.personalbestappteam24.storage.LocalStorage;
import edu.ucsd.cse110.personalbestappteam24.storage.Storage;
import edu.ucsd.cse110.personalbestappteam24.walk_run.Past5WalkRuns;
import edu.ucsd.cse110.personalbestappteam24.walk_run.WalkRun;

public class User {
    private String emailAddress;
    //private Storage storage;
    private Storage storage;
    private DailySteps dailySteps;
    private StepGoals stepGoals;
    private boolean isNotified;
    private String lastNotifiedDate;
    private String lastEncourageDate;
    private WalksAndRuns walksAndRuns;
    private Past5WalkRuns pastWalkRuns;
    private Friends friends;
    private FriendRequests sentFriendRequests;
    private FriendRequests receivedFriendRequests;
    private FriendRequestMediator friendRequestMediator;
    private MessageMediator messageMediator;
    private Context context;
    private ReportMediator reportMediator;

    public static final String ALL_TIME_STEPS = "ALL_TIME_STEPS";
    public static final String CURR_STEP_GOAL = "CURR_STEP_GOAL";
    public static final String EMAIL_ADDRESS = "EMAIL_ADDRESS";
    public static final String HEIGHT = "HEIGHT";
    public static final String ALL_WALKS_AND_RUNS = "ALL_WALKS_AND_RUNS";
    public static final String ALL_TIME_STEP_GOALS = "ALL_TIME_STEP_GOALS";
    public static final String FRIENDS_LIST = "FRIENDS_LIST";
    public static final String SENT_FRIEND_REQUESTS = "SENT_FRIEND_REQUESTS";
    public static final String RECEIVED_FRIEND_REQUESTS = "RECEIVED_FRIEND_REQUESTS";
    public static final long DEFAULT_STEP_GOAL = 5000;


    public User(String emailAddress, Context context) {
        this.emailAddress = emailAddress;
        this.context = context;
        // Initialize storage.
        Intent intent = new Intent(context, LocalStorage.class);

        storage = new Storage(this.emailAddress, context);
        //storage = new MockUserData(this.emailAddress, context);

        // Initialize daily steps.
        JSONObject alltimeSteps = this.storage.getFromUserData(ALL_TIME_STEPS);
        dailySteps = new DailySteps(alltimeSteps);

        // Initialize step goals.
        JSONObject alltimeStepGoals = this.storage.getFromUserData(ALL_TIME_STEP_GOALS);
        stepGoals = new StepGoals(alltimeStepGoals);

        // Set the lastEncourageDate to today
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        lastEncourageDate = formatter.format(calendar.getTime());

        // Set the lastNotifiedDate to yesterday
        calendar.add(Calendar.DATE, -1);
        lastNotifiedDate = formatter.format(calendar.getTime());

        // User is not notified his goal achievement yet when initializing
        isNotified = false;

        // Initialize walks and runs.
        JSONObject alltimeWalksAndRuns = this.storage.getFromUserData(ALL_WALKS_AND_RUNS);
        walksAndRuns = new WalksAndRuns(alltimeWalksAndRuns);
        pastWalkRuns = new Past5WalkRuns();

        //Initialize friends.
        JSONObject allFriends = this.storage.getFromFriendsData(FRIENDS_LIST);
        friends = new Friends(allFriends);

        // Sent friend requests.
        JSONObject sentFriendRequestData = this.storage.getFromFriendsData(SENT_FRIEND_REQUESTS);
        sentFriendRequests = new FriendRequests(sentFriendRequestData);

        // Received friend requests.
        JSONObject receivedFriendRequestData = this.storage.getFromFriendsData(RECEIVED_FRIEND_REQUESTS);
        receivedFriendRequests = new FriendRequests(receivedFriendRequestData);


        friendRequestMediator = new FriendRequestMediator();
        messageMediator = new MessageMediator();
        reportMediator = new ReportMediator();
    }

    public void updateUser() {
        Log.d("UPDATING", " Updating user friends data.");
        //Initialize friends.
        JSONObject allFriends = this.storage.getFromFriendsData(FRIENDS_LIST);
        friends = new Friends(allFriends);

        // Sent friend requests.
        JSONObject sentFriendRequestData = this.storage.getFromFriendsData(SENT_FRIEND_REQUESTS);
        sentFriendRequests = new FriendRequests(sentFriendRequestData);

        // Received friend requests.
        JSONObject receivedFriendRequestData = this.storage.getFromFriendsData(RECEIVED_FRIEND_REQUESTS);
        receivedFriendRequests = new FriendRequests(receivedFriendRequestData);
    }

    /**
     * Retrieves the current step goal for a user. If it is not set, returns the default step
     * goal.
     * @return user's current step goal of default step goal value.
     */
    public long getStepGoal() {
        return this.storage.getLongFromUserData(CURR_STEP_GOAL);
    }

    /**
     * Sets the step goal to a given valid value. If the value is invalid, the step
     * goal does not change from what it was previously.
     * @param stepGoal a new step goal to set to.
     */
    public void setStepGoal(long stepGoal) {
        if(stepGoal > 0) {
            // Update the current step goal as well as in alltimestepgoals object.
            this.storage.putIntoUserData(CURR_STEP_GOAL, stepGoal);
            this.stepGoals.updateStepGoal(stepGoal);
            this.clearNotified();
        }
    }

    public String getEmailAddress() { return this.emailAddress; }

    public StepGoals getStepGoals() {
        return this.stepGoals;
    }

    public Context getContext() { return this.context; }

    public DailySteps getDailySteps() {
        return this.dailySteps;
    }

    public void setDailySteps(long totalDailySteps) {
        // Updates the steps taken for this day
        this.dailySteps.updateSteps(totalDailySteps);
        this.storage.putIntoUserData(ALL_TIME_STEPS, dailySteps.getAlltimeSteps());

        // Stores the step goal that took place today (even if it did not change from previous days)
        if(!stepGoals.isStepGoalSet()) {
            stepGoals.updateStepGoal(getStepGoal());
            this.storage.putIntoUserData(ALL_TIME_STEP_GOALS, stepGoals.getAlltimeStepGoals());
        }
    }

    public void setHeight(int height) {
        this.storage.putIntoUserData(HEIGHT, height);
    }

    public int getHeight() {
        return this.storage.getFromUserData(HEIGHT);
    }

    public double getStride() {
        double inches = 0.413 * getHeight();
        double feet = inches / 12;
        double miles = feet / 5280;
        return miles;
    }


    public void updateLastEncourageDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        lastEncourageDate = formatter.format(calendar.getTime());
    }

    public String getLastEncourageDate() {
        return lastEncourageDate;
    }

    public void updateLastNotifiedDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        lastNotifiedDate = formatter.format(calendar.getTime());
    }

    public String getLastNotifiedDate() {
        return lastNotifiedDate;
    }

    public void setNotified() {
        isNotified = true;
        updateLastNotifiedDate();
        //System.out.println(lastNotifiedDate);
    }

    public void clearNotified() { isNotified = false; }

    public boolean isNotified() { return isNotified; }

    public void addWalkRun(JSONObject walkRunData) {
        JSONObject allWalksAndRuns = this.storage.getFromUserData(ALL_WALKS_AND_RUNS);
        String currentDate = dailySteps.getDate();


        // If the user has not taken a walk today, add a new empty array of walks.
        if (!allWalksAndRuns.has(currentDate)) {
            try {
                allWalksAndRuns.put(ALL_WALKS_AND_RUNS, new JSONArray());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void addWalkRun(WalkRun walkRun) {
        walksAndRuns.addWalkRun(walkRun);
        this.storage.putIntoUserData(ALL_WALKS_AND_RUNS, walksAndRuns.getAlltimeWalksAndRuns());


        //Log.d("TESTING", walksAndRuns.getWalksAndRuns().toString());
    }

    //TESTING
    public void addWalkRun(WalkRun walkRun, String date) {
        walksAndRuns.addWalkRun(walkRun, date);
        this.storage.putIntoUserData(ALL_WALKS_AND_RUNS, walksAndRuns.getAlltimeWalksAndRuns());
        //this.getPastWalkRuns().addWalkRun(walkRun);
    }

    public WalksAndRuns getWalksAndRuns() {
        return this.walksAndRuns;
    }

    public Past5WalkRuns getPastWalkRuns() {
        return pastWalkRuns;
    }

    public Friends getFriends() {
        return friends;
    }

    public ArrayList<FriendRequest> getSentFriendRequests() {
        return this.sentFriendRequests.getFriendRequests();
    }

    public ArrayList<FriendRequest> getReceivedFriendRequests() {
        return this.receivedFriendRequests.getFriendRequests();
    }

    public void sendFriendRequest(String emailAddress, String name) {
        // Add the sent friend request to the this user's sent friend requests.
        this.sentFriendRequests.addFriendRequest(emailAddress, name);
        this.storage.putIntoFriendsData(SENT_FRIEND_REQUESTS, sentFriendRequests.getFriendRequestsData());

        // Send the friend request to the other user through the mediator.
        // TODO only using email address as name for now.
        friendRequestMediator.addFriendRequest(emailAddress, name, this.emailAddress, this.emailAddress);
    }

    public void acceptFriendRequest(String emailAddress, String name) {
        // Remove the friend from pending received requests.
        this.receivedFriendRequests.removeFriendRequest(emailAddress);
        this.storage.putIntoFriendsData(RECEIVED_FRIEND_REQUESTS, receivedFriendRequests.getFriendRequestsData());

        // Add this user as current friend
        this.friends.addFriend(new Friend(emailAddress, name));
        this.storage.putIntoFriendsData(FRIENDS_LIST, friends.getFriendsData());


        // Send the request to the other user through the mediator.
        // TODO: Only using email address as name for now.
        friendRequestMediator.acceptFriendRequest(this.emailAddress, this.emailAddress, emailAddress, name);
    }

    public void sendMessage(String content, String emailAddress) {
        // Update messages locally
        this.friends.getFriend(emailAddress).getConversation().addMessage(content, this.emailAddress);
        this.storage.putIntoFriendsData(FRIENDS_LIST, this.friends.getFriendsData());

        // Update messages on cloud for other user
        this.messageMediator.addMessage(content, emailAddress, this.emailAddress);
    }

    public int getFriendListSize() {
        return getFriends().getFriends().size();
    }

    public int getPendingRequestListSize() {
        return getReceivedFriendRequests().size();
    }

    public int getSentRequestListSize() {
        return getSentFriendRequests().size();
    }

    public void setReportForFriend(int week, String emailAddress, DisplayFriendActivity displayFriendActivity) {
        this.reportMediator.setUserReport(week, emailAddress, displayFriendActivity);
    }

    //TESTING
    public Storage getStorage() {
        return storage;
    }


}
