package edu.ucsd.cse110.personalbestappteam24.user;

public class FriendRequest {
    private String name;
    private String emailAddress;

    public FriendRequest(String emailAddress, String name) {
        this.emailAddress = emailAddress;
        this.name = name;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public String getName() {
        return this.name;
    }
}
