package edu.ucsd.cse110.personalbestappteam24.friends;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import edu.ucsd.cse110.personalbestappteam24.R;
import edu.ucsd.cse110.personalbestappteam24.activities.ChatWithFriendActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;
import edu.ucsd.cse110.personalbestappteam24.activities.ViewFriendActivity;
import edu.ucsd.cse110.personalbestappteam24.user.Friend;
import edu.ucsd.cse110.personalbestappteam24.user.Friends;
import edu.ucsd.cse110.personalbestappteam24.user.User;

public class Tab1Friends extends Fragment {

    static ListView listView;
    static ArrayList<String> friendList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.tab1friends, container, false);
        listView = (ListView) rootView.findViewById(R.id.FriendsListView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent launchResult = new Intent(getActivity(), ViewFriendActivity.class);
               launchResult.putExtra( "email", friendList.get(position) );
               startActivity(launchResult);
            }
        });


        //AsyncUpdater asyncUpdater = new AsyncUpdater(getContext());
        //asyncUpdater.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        updateUI(getContext());

        return rootView;
    }

    public static void updateUI(Context context) {
        //MainActivity.user.updateUser();

        Friends fren = MainActivity.user.getFriends();

        JSONArray arr = fren.getFriendsData().names();
        HashMap<String, Friend> friendHM = new HashMap<>();

        if( arr != null && arr.length() != 0 ) {
            friendHM = MainActivity.user.getFriends().getFriends();
        }

        Set<String> emailAddresses = friendHM.keySet();
        friendList = new ArrayList<String>();
        for( String email : emailAddresses ) {
            friendList.add( friendHM.get(email).getFriendName() );
        }
        ArrayAdapter arrAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, friendList);
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
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "";
        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Tab1Friends.updateUI(parentContext);
        }
    }

}
