/* object for each friend of user */
package edu.ucsd.cse110.personalbestappteam24.Friend;

import org.json.JSONObject;

public class FriendOld implements IFriend{

    private IChatHistory chatHistory;
    private String email;

    public FriendOld(String email) {
        this.email = email;
        chatHistory = new ChatHistoryMock();
    }

    public FriendOld(JSONObject friend) {
        //for getting friends from storage
        //should get chatHistory as well
    }

    public String getEmail() {
        return email;
    }

    public IChatHistory getChatHistory() {
        return chatHistory;
    }

    public void storeFriend() {
        //put friend in storage and storeChatHistory()
    }


}
