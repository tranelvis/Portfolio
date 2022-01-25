package edu.ucsd.cse110.personalbestappteam24.friends;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import edu.ucsd.cse110.personalbestappteam24.R;
import edu.ucsd.cse110.personalbestappteam24.activities.ChatWithFriendActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;
import edu.ucsd.cse110.personalbestappteam24.user.Friend;
import edu.ucsd.cse110.personalbestappteam24.user.FriendRequest;
import edu.ucsd.cse110.personalbestappteam24.user.FriendRequests;
import edu.ucsd.cse110.personalbestappteam24.user.Friends;
import edu.ucsd.cse110.personalbestappteam24.user.User;

public class Tab2Pending  extends Fragment {

    public static ListView listView;
    static ArrayList<FriendRequest> pendingList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2pending, container, false);



        listView = (ListView) rootView.findViewById(R.id.PendingListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FriendRequest request = pendingList.get(position);
                MainActivity.user.acceptFriendRequest(request.getEmailAddress(), request.getEmailAddress());
                updateUI(getContext());
            }
        });



        updateUI(getContext());
        //AsyncUpdater asyncUpdater = new AsyncUpdater(getContext());
        //asyncUpdater.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        return rootView;
    }

    public static void updateUI(Context context) {
        pendingList = MainActivity.user.getReceivedFriendRequests();
        ArrayList<String> content = new ArrayList<>();
        for(FriendRequest fr : pendingList) {
            content.add(fr.getEmailAddress());
        }

        ArrayAdapter arrAdapter = new ArrayAdapter( context, android.R.layout.simple_list_item_1, content);
        listView.setAdapter(arrAdapter);

    }

    /**
     * This asyc task updates the user interface of the walkorrun activity every second with updated
     * walk run data.
     */
    private class AsyncUpdater extends AsyncTask<String, String, String> {
        private Context parentContext;

        public AsyncUpdater(Context context) {
            parentContext = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            while( !isCancelled()) {

                publishProgress("...");

                // Wait 1 second.
                try {
                    Thread.sleep(4500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "";
        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Tab2Pending.updateUI(parentContext);
        }
    }
}

