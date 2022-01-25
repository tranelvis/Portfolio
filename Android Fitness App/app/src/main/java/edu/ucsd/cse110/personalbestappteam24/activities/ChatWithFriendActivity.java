package edu.ucsd.cse110.personalbestappteam24.activities;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import edu.ucsd.cse110.personalbestappteam24.R;
import edu.ucsd.cse110.personalbestappteam24.user.Conversation;
import edu.ucsd.cse110.personalbestappteam24.user.Friend;
import edu.ucsd.cse110.personalbestappteam24.user.Message;
import edu.ucsd.cse110.personalbestappteam24.user.User;

import static edu.ucsd.cse110.personalbestappteam24.activities.MainActivity.user;

public class ChatWithFriendActivity extends AppCompatActivity {

    static ListView display;
    static ArrayList<Message> messages;
    Button sendBtn;
    EditText typedMsg;
    static Friend friend;
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_with_friend);

        display = findViewById(R.id.chatview);

        Intent intent = this.getIntent();
        final String friendEmail = intent.getStringExtra("email");
        final String entryMessage = intent.getStringExtra("message");

        if(!entryMessage.equals("")) {
            user.sendMessage(entryMessage, friendEmail);
            updateUI(getApplicationContext(), friendEmail);
        }




        // Start message updater
        AsyncMessageUpdater messageUpdater = new AsyncMessageUpdater(this, friendEmail);
        messageUpdater.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        sendBtn = findViewById(R.id.button_chatbox_send);
        typedMsg = findViewById(R.id.edittext_chatbox);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotification();
                String message = typedMsg.getText().toString();
                user.sendMessage(message, friendEmail);
                typedMsg.setText("");
                updateUI(getApplicationContext(), friendEmail);
            }
        });

    }

    /*
    public String getContent(String email) {
        Friend friend = user.getFriends().getFriend(email);

        Conversation conversation = friend.getConversation();
        messages = conversation.getMessageList();
        ArrayList<String> content = new ArrayList<>();
        Message message = messages.get(0);
        return message.getContent();
    }
    */

    public static void updateUI(Context context, String email) {
        // Get the latest user data.
        user.updateUser();
        friend = user.getFriends().getFriend(email);

        Conversation conversation = friend.getConversation();
        messages = conversation.getMessageList();
        ArrayList<String> content = new ArrayList<>();
        for (Message message : messages) {
            content.add(message.getAuthor() + ": " + message.getContent());
        }
        ArrayAdapter arrAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, content);
        display.setAdapter(arrAdapter);
    }

    /**
     * This asyc task updates the user interface of the walkorrun activity every second with updated
     * walk run data.
     */
    private class AsyncMessageUpdater extends AsyncTask<String, String, String> {
        private Context parentContext;
        private String email;

        public AsyncMessageUpdater(Context context, String friendEmail) {
            email = friendEmail;
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
            ChatWithFriendActivity.updateUI(parentContext, email);
        }
    }

    private void addNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this.getApplicationContext(), "notify_001");
        Intent ii = new Intent(this.getApplicationContext(), ChatWithFriendActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        //bigText.bigText(verseurl);
        bigText.setBigContentTitle("You have received a new message!");
        bigText.setSummaryText("Someone sent you a message just now.");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("You have received a new message!");
        mBuilder.setContentText("Someone sent you a message just now.");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "YOUR_CHANNEL_ID";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());
    }

}
