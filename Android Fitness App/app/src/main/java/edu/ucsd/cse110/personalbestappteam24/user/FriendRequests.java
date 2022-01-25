package edu.ucsd.cse110.personalbestappteam24.user;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class FriendRequests {

    private JSONObject friendRequestsData;

    public FriendRequests(JSONObject sentFriendRequestsData) {
        this.friendRequestsData = sentFriendRequestsData;
    }

    /**
     * Returns a list of FriendRequest objects which represent users that the
     * current user has sent friend requests to.
     * @return
     */
    public ArrayList<FriendRequest> getFriendRequests() {
        ArrayList<FriendRequest> toReturn = new ArrayList<>();
        Iterator<String> emailAddresses = friendRequestsData.keys();

        while(emailAddresses.hasNext()) {
            String emailAddress = emailAddresses.next();
            String name = "";
            try {
                name = friendRequestsData.getString(emailAddress);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            toReturn.add(new FriendRequest(emailAddress, name));

        }

        return toReturn;
    }

    public void addFriendRequest(String emailAddress, String name) {
        try {
            friendRequestsData.put(emailAddress, name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void removeFriendRequest(String emailAddress) {
        friendRequestsData.remove(emailAddress);
    }

    public JSONObject getFriendRequestsData() {
        return friendRequestsData;
    }
}
