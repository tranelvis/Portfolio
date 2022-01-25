package edu.ucsd.cse110.personalbestappteam24.user;

import org.json.JSONObject;

import edu.ucsd.cse110.personalbestappteam24.activities.DisplayFriendActivity;
import edu.ucsd.cse110.personalbestappteam24.storage.CloudStorage;

class ReportMediator {
    private JSONObject data;
    private CloudStorage storage;

    public void setUserReport(int week, String emailAddress, DisplayFriendActivity displayFriendActivity) {
        storage = new CloudStorage(week, emailAddress, displayFriendActivity);
    }
}
