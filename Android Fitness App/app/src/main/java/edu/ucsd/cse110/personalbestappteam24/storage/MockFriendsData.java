package edu.ucsd.cse110.personalbestappteam24.storage;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import edu.ucsd.cse110.personalbestappteam24.Friend.FriendOld;
import edu.ucsd.cse110.personalbestappteam24.Friend.FriendsManager;
import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;
import edu.ucsd.cse110.personalbestappteam24.user.Friend;
import edu.ucsd.cse110.personalbestappteam24.user.Message;
import edu.ucsd.cse110.personalbestappteam24.user.User;

public class MockFriendsData {

    HashMap<String, Friend> friendList;

    public MockFriendsData() {

    }

    public void createMockFriendData() {

        addFriend("aditi");
        addFriend("arvind");
        addFriend("elvis");
        addFriend("mohit");
        logFriendList();

        addMessage("mohit", "hi", "mohit");
        addMessage("mohit", "hello", "eric");

        addFriend("kai");
        logFriendList();

        logConversation("mohit");
        addMessage("kai", "sup", "eric");
        logConversation("kai");

    }

    public void addFriend(String friendName) {
        MainActivity.user.getFriends().addFriend(new Friend(friendName, friendName));
    }

    public void addMessage(String friendName, String message, String sender) {
        friendList = MainActivity.user.getFriends().getFriends();
        friendList.get(friendName).getConversation().addMessage(message, sender);
    }

    public void logConversation(String friendName) {
        friendList = MainActivity.user.getFriends().getFriends();
        ArrayList<Message> convo = friendList.get(friendName).getConversation().getMessageList();
        for (int i = 0; i < convo.size(); i++) {
            Log.d("Conversation with " + friendName, convo.get(i).getContent() + " " + convo.get(i).getAuthor() + "; ");
        }
    }

    public void logFriendList() {
        friendList = MainActivity.user.getFriends().getFriends();
        Iterator it = friendList.keySet().iterator();
        String friends = "";
        while (it.hasNext()) {
            friends += " " + it.next().toString();
        }
        Log.d("Friend List", friends);
    }

    public void sendMockRequest(String friendName) {
        User friend = new User(friendName, MainActivity.user.getContext());
        friend.sendFriendRequest(MainActivity.user.getEmailAddress(), MainActivity.user.getEmailAddress());
    }

}
