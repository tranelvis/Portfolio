package edu.ucsd.cse110.personalbestappteam24.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class Friends {
    private JSONObject friendsData;

    public Friends(JSONObject friendsData) {
        this.friendsData = friendsData;
    }

    public void addFriend(Friend friend) {
        // TODO: Note that user is only added after a request has been accepted.
        JSONObject friendData = friend.getFriendData();

        // Add friend data as a new friend keyed by this email address.
        try {
            String friendEmailAddress = friendData.getString(Friend.EMAIL_ADDRESS);
            this.friendsData.put(friendEmailAddress, friendData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getFriendsData() {
        return friendsData;
    }

    /**
     * Returns a mapping from email address of a friend to their corresponding friend object.
     * Can easily check whether or not an email address is a friend using this object.
     */
    public HashMap<String, Friend> getFriends() {
        HashMap<String, Friend> toReturn = new HashMap<String, Friend>();
        Iterator<String> emailAddresses = this.friendsData.keys();

        while(emailAddresses.hasNext()) {
            try {
                String emailAddress = emailAddresses.next();
                Friend friend = new Friend(this.friendsData.getJSONObject(emailAddress));
                toReturn.put(emailAddress, friend);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toReturn;
    }

    public Friend getFriend(String emailAddress) {
        return this.getFriends().get(emailAddress);
    }
}