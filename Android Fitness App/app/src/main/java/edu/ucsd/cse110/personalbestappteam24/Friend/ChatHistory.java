/* each friend has corresponding chatHistory with user */

package edu.ucsd.cse110.personalbestappteam24.Friend;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class ChatHistory implements IChatHistory {

    CollectionReference chat;

    static String COLLECTION_KEY = "chats";
    static String MESSAGES_KEY = "messages";
    static String FROM_KEY = "from";
    static String TEXT_KEY = "text";
    static String TIMESTAMP_KEY = "timestamp";
    static int currentDocumentKey = 1;
    
    //CollectionReference chat;
    private String DOCUMENT_KEY;

    public ChatHistory() {
        DOCUMENT_KEY = "chat" + currentDocumentKey;
        currentDocumentKey += 1;

        chat = FirebaseFirestore.getInstance()
                .collection(COLLECTION_KEY)
                .document(DOCUMENT_KEY)
                .collection(MESSAGES_KEY);
        //create collectionReference?  and document key
    }

    // get data directly from firebase
    //public ChatHistory(CollectionReference chat) {}

    public ChatHistory (JSONObject chatHistory) {
        //get data directly from storage
        //getdocumentkey too
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
    public void subscribe() { }

    @Override
    public void storeChatHistory() {
        //put in storage / firebase
    }

    @Override
    public String getDocumentKey() {
        return DOCUMENT_KEY;
    }

    @Override
    public void printMsg() {

    }
}
