package edu.ucsd.cse110.personalbestappteam24.user;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
    public static final String TIMESTAMP = "TIMESTAMP";
    public static final String CONTENT = "CONTENT";
    public static final String AUTHOR = "AUTHOR";
    private JSONObject messageData;
    private String content;
    private String authorEmailAddress;
    private long timestamp;

    // Initialize a new message.
    public Message(String content, String authorEmailAddress) {
        this.content = content;
        this.authorEmailAddress = authorEmailAddress;
        this.timestamp = System.currentTimeMillis() / 1000L;

        try {

            messageData = new JSONObject("{}");
            messageData.put(TIMESTAMP, this.timestamp);
            messageData.put(CONTENT, this.content);
            messageData.put(AUTHOR, this.authorEmailAddress);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Reinitialize an existing message.
    public Message(JSONObject messageData) {
        this.messageData = messageData;
        try {
            this.content = messageData.getString(CONTENT);
            this.authorEmailAddress = messageData.getString(AUTHOR);
            this.timestamp = messageData.getLong(TIMESTAMP);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public JSONObject getMessageData() {
        return this.messageData;
    }

    public String getContent() {
        return this.content;
    }

    public String getAuthor() {
        return this.authorEmailAddress;
    }

    public long getTimestamp() {
        return this.timestamp;
    }
}
