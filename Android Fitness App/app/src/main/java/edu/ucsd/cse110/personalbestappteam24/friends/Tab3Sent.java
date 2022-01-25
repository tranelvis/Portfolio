package edu.ucsd.cse110.personalbestappteam24.friends;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import edu.ucsd.cse110.personalbestappteam24.R;
import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;
import edu.ucsd.cse110.personalbestappteam24.user.FriendRequest;
import edu.ucsd.cse110.personalbestappteam24.user.User;

public class Tab3Sent extends Fragment {

    static ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab3sent, container, false);
        listView = (ListView) rootView.findViewById(R.id.SentListView);

        //AsyncUpdater asyncUpdater = new AsyncUpdater(getContext());
        //asyncUpdater.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        updateUI(getContext());
        return rootView;
    }

    public static void updateUI(Context context) {
        ArrayList<FriendRequest> sentList = MainActivity.user.getSentFriendRequests();
        ArrayList<String> content = new ArrayList<>();

        for(FriendRequest fr : sentList) {
            content.add(fr.getEmailAddress());
        }

        ArrayAdapter arrAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, content);
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
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "";
        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Tab3Sent.updateUI(parentContext);
        }
    }
}
