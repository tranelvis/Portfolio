package edu.ucsd.cse110.personalbestappteam24.user;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Friend {
    public static final String EMAIL_ADDRESS = "EMAIL_ADDRESS";
    public static final String NAME = "NAME";
    public static final String CONVERSATION = "CONVERSATION";

    JSONObject friendData;
    private String friendEmailAddress;
    private String friendName;
    private Conversation conversation;

    // This friend is being created for the first time.
    public Friend(String friendEmailAddress, String friendName) {
        this.friendName = friendName;
        this.friendEmailAddress = friendEmailAddress;
        this.conversation = new Conversation();

        try {
            // Create a new friend with a name, email address, and empty conversation.
            friendData = new JSONObject("{}");
            friendData.put(EMAIL_ADDRESS, friendEmailAddress);
            friendData.put(NAME, friendName);
            friendData.put(CONVERSATION, this.conversation.getConversationData());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // This friend has been created before and is being reinstantiated.
    public Friend(JSONObject friendData) {

        this.friendData = friendData;
        try {
            this.conversation = new Conversation(friendData.getJSONObject(CONVERSATION));
            this.friendEmailAddress = friendData.getString(EMAIL_ADDRESS);
            this.friendName = friendData.getString(NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getFriendData() {
        return this.friendData;
    }

    public Conversation getConversation() {
        return this.conversation;
    }

    public String getEmailAddress() {
        return this.friendEmailAddress;
    }

    public String getFriendName() {
        return this.friendName;
    }
}
