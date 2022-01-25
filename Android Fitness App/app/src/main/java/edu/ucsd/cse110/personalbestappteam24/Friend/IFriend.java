package edu.ucsd.cse110.personalbestappteam24.Friend;

/* object for each friend of user */
public interface IFriend {
    public String getEmail();
    public IChatHistory getChatHistory();
    public void storeFriend();

}

