package edu.ucsd.cse110.personalbestappteam24.Friend;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatHistoryMock implements IChatHistory {

    CollectionReference chat;
    ArrayList<Map<String,String>> chatHistory = new ArrayList<Map<String,String>>();

    static String COLLECTION_KEY = "chats";
    static String MESSAGES_KEY = "messages";
    static String FROM_KEY = "from";
    static String TEXT_KEY = "text";
    static String TIMESTAMP_KEY = "timestamp";
    static int currentDocumentKey = 1;

    //CollectionReference chat;
    private String DOCUMENT_KEY;

    public ChatHistoryMock() {
        //DOCUMENT_KEY = "mockChat" + currentDocumentKey;
        //currentDocumentKey += 1;
        DOCUMENT_KEY = "chat1";



        chat = FirebaseFirestore.getInstance()
                .collection(COLLECTION_KEY)
                .document(DOCUMENT_KEY)
                .collection(MESSAGES_KEY);



        Map<String, String> newMessage = new HashMap<>();
        newMessage.put(FROM_KEY, "kai");
        newMessage.put(TEXT_KEY, "hey, eric!");

        add(newMessage);

        chatHistory.add(newMessage);




    }

    public void addMessage(String key, String msg) {
        Map<String, String> newMessage = new HashMap<>();
        newMessage.put(key, msg);
        add(newMessage);
    }

    @Override
    public void add(Map<String, String> msg) {
        chat.add(msg);
    }

    @Override
    public Query orderBy(String field, Query.Direction direction) {
        return chat.orderBy(field, direction);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void storeChatHistory() {

    }

    @Override
    public String getDocumentKey() {
        return null;
    }

    //Test
    public void printMsg() {
        for(int i = 0; i < chatHistory.size(); i++) {
            System.out.print(chatHistory.get(i).toString());
        }
    }

}
