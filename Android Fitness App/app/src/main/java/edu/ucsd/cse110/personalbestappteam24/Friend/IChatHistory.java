package edu.ucsd.cse110.personalbestappteam24.Friend;

import com.google.firebase.firestore.Query;

import java.util.Map;

public interface IChatHistory {
    void add(Map<String, String> msg);
    Query orderBy(String field, Query.Direction direction);
    void subscribe();
    public void storeChatHistory();
    public String getDocumentKey();
    public void printMsg();
}
