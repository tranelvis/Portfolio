package edu.ucsd.cse110.personalbestappteam24.user;

import android.content.Context;
import android.util.Log;

import com.google.firebase.FirebaseApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.ucsd.cse110.personalbestappteam24.storage.CloudStorage;

public class FriendRequestMediator {
    private ArrayList<CloudStorage> storage;
    public static final String ADD_FRIEND_REQUEST = "ADD_FRIEND_REQUEST";
    public static final String ACCEPT_FRIEND_REQUEST = "ACCEPT_FRIEND_REQUEST";

    public FriendRequestMediator() {
        storage = new ArrayList<>();
    }

    /**
     * Sends a friend request from the from user to the to user. Adds a pending received request on
     * the sent to user. Sends a notification to the sent to user that they have received a friend
     * request.
     *
     * @param toEmailAddress:  Email address that the request was sent to
     * @param toName:          Name of the user that the request was sent to
     * @param fromEmailAddress: Email address that the request was sent from
     * @param fromName:         Name that the request was sent from.
     */
    public void addFriendRequest(String toEmailAddress, String toName, String fromEmailAddress, String fromName) {
        //CloudStorage storage = new CloudStorage(toEmailAddress);
        this.storage.add(new CloudStorage(toEmailAddress, toName, fromEmailAddress, fromName, ADD_FRIEND_REQUEST));

        // TODO: Send notification to TO user.
    }

    /**
     * Accepts request that was sent from a user to a user. The pending sent request is removed from
     * the sent from user. Sends a notification to the sent from user that the request has been
     * accepted. Friend is added for both users.
     *
     * @param toEmailAddress:  Email address that the request was sent to
     * @param toName:          Name of the user that the request was sent to
     * @param fromEmailAddress: Email address that the request was sent from
     * @param fromName:         Name that the request was sent from.
     */
    public void acceptFriendRequest(String toEmailAddress, String toName, String fromEmailAddress, String fromName) {
        this.storage.add(new CloudStorage(toEmailAddress, toName, fromEmailAddress, fromName, ACCEPT_FRIEND_REQUEST));
        // TODO: Send notification to FROM user.
    }
}
