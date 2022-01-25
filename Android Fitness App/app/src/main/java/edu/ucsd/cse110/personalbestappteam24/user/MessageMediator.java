package edu.ucsd.cse110.personalbestappteam24.user;

import java.util.ArrayList;

import edu.ucsd.cse110.personalbestappteam24.storage.CloudStorage;

public class MessageMediator {
    public static final String ADD_MESSAGE = "ADD_MESSAGE";
    private ArrayList<CloudStorage> storage;

    public MessageMediator() {
        storage = new ArrayList<>();
    }

    public void addMessage(String content, String toEmailAddress, String fromEmailAddress) {
        this.storage.add(new CloudStorage(content, toEmailAddress, fromEmailAddress, ADD_MESSAGE));
    }
}
