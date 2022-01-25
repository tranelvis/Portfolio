/* manage all friends of user */

package edu.ucsd.cse110.personalbestappteam24.Friend;

import java.util.ArrayList;

public class FriendsManager {

    private ArrayList<FriendOld> friendList;

    public FriendsManager() {
        friendList = new ArrayList<>();
    }

    public boolean addNewFriend(String email) {
//        if (friendList.size() == 0) {
//            friendList.add(new FriendOld(email));
//            return true;
//        }
        int index = findFriendIndex(email);
        if (index != -1) { //friend already exists
            return false;
        }
        else {
            FriendOld newFriend = new FriendOld(email);
            addFriendToList(newFriend);
            return true;
        }
    }


    public void removeFriend(String email) {
//        if (friendList.size() == 0) {
//            return;
//        }
        int index = findFriendIndex(email);
        if (index == -1) {
            return;
        }
        else {
            friendList.remove(index); //delete friend from list, shifts all elements left if possible
        }
    }

    /* adds friendOld to correct location in friendList */
    public void addFriendToList(FriendOld friendOld) {
        int l = 0;
        int r = (int)friendList.size() - 1;
        int mid = 0;
        if (friendList.size() == 0) {
            friendList.add(friendOld);
            return;
        }
        while (l < r) {
            mid = (l + r) / 2;
            if (friendList.get(mid).getEmail().compareTo(friendOld.getEmail()) < 0) {
                l = mid + 1;
            }
        else {
                r = mid - 1;
            }
        }
        if (friendList.get(l).getEmail().compareTo(friendOld.getEmail()) < 0) {
            friendList.add(l+1, friendOld);
        }
        else{
            friendList.add(l, friendOld);
        }
        //add friendOld to sorted location after binary search
    }

    public void retrieveFriends() {
        //get friends from storage, call addFriendToList to add to friendlist
        //use addFriendsToList(new FriendOld(JSON)) or something like that
    }

    /* finds index of friend in friendList, return -1 if not there*/
    public int findFriendIndex(String email) {
        //iterate through friend list, return index of friend
        int l = 0;
        int r = (int)friendList.size() -1;
        // binary search for finding the movie in the movielist
        while (l <= r) {
            int mid = (l + r) / 2;
            if (friendList.get(mid).getEmail().compareTo(email) == 0) {
                return mid;
            }
            if (friendList.get(mid).getEmail().compareTo(email)  < 0) {
                l = mid + 1;
            }
            else
                r = mid - 1;
        }
        return -1;
    }

    public ArrayList<FriendOld> getFriendList() {
        return friendList;
    }


    public void storeFriends() {
        //put friends into storage using friend.storeFriend()
    }

    //testing purposes
    public void printFriendList() {
        for (FriendOld friendOld : friendList) {
            System.out.print(friendOld.getEmail() + ", ");
        }
        System.out.println("");
    }

}
