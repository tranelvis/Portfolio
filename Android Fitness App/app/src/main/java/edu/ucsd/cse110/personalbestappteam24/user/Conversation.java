package edu.ucsd.cse110.personalbestappteam24.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Conversation {
    public static final String MESSAGES = "MESSAGES";
    private JSONObject conversationData;
    private JSONArray messageDataList;

    // Creates an empty conversation with no messages.
    public Conversation() {

        try {
            this.messageDataList = new JSONArray();
            this.conversationData = new JSONObject("{}");
            this.conversationData.put(MESSAGES, messageDataList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Reinitializes a conversation.
    public Conversation(JSONObject conversationData) {
        this.conversationData = conversationData;
        try {
            this.messageDataList = conversationData.getJSONArray(MESSAGES);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addMessage(String content, String authorEmailAddress) {
        JSONObject messageData = (new Message(content, authorEmailAddress)).getMessageData();
        this.messageDataList.put(messageData);
        try {
            this.conversationData.put(MESSAGES, messageDataList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getConversationData() {
        return conversationData;
    }

    /**
     * Returns an ordered list of messages where earlier the index, earlier the message timestamp.
     * @return
     */
    public ArrayList<Message> getMessageList() {
        ArrayList<Message> toReturn = new ArrayList<>();
        for(int i = 0; i < messageDataList.length(); i++) {
            try {
                toReturn.add(new Message(messageDataList.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return toReturn;
    }
}
