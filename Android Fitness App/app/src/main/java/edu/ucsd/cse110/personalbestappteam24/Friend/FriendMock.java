package edu.ucsd.cse110.personalbestappteam24.Friend;

public class FriendMock implements IFriend {

    private IChatHistory chatHistory;
    private String email;

    public FriendMock(String email) {
        this.email = "yic258@ucsd.edu";
        chatHistory = new ChatHistoryMock();
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public IChatHistory getChatHistory() {
        return chatHistory;
    }

    @Override
    public void storeFriend() {

    }
}
