package edu.ucsd.cse110.personalbestappteam24.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.ucsd.cse110.personalbestappteam24.R;

public class ViewFriendActivity extends AppCompatActivity {

    TextView friendname;
    Button weekone;
    Button weektwo;
    Button weekthree;
    Button weekfour;
    Button message;
    Button goodjob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friend);

        Intent intent = this.getIntent();
        final String friendEmail = intent.getStringExtra("email");

        friendname = findViewById(R.id.friendname);
        friendname.setText(friendEmail);

        weekone = findViewById(R.id.week1);
        weektwo = findViewById(R.id.week2);
        weekthree = findViewById(R.id.week3);
        weekfour = findViewById(R.id.week4);
        message = findViewById(R.id.message);
        goodjob = findViewById(R.id.goodjob);

        weekone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDisplayFriendStatsPage(1, friendEmail);
            }
        });

        weektwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDisplayFriendStatsPage(2, friendEmail);
            }
        });

        weekthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDisplayFriendStatsPage(3, friendEmail);
            }
        });

        weekfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDisplayFriendStatsPage(4, friendEmail);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchResult = new Intent(getApplicationContext(), ChatWithFriendActivity.class);
                launchResult.putExtra( "email", friendEmail);
                launchResult.putExtra("message", "");
                startActivity(launchResult);
            }
        });

        goodjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchResult = new Intent(getApplicationContext(), ChatWithFriendActivity.class);
                launchResult.putExtra( "email", friendEmail);
                launchResult.putExtra("message", "Good Job!");
                startActivity(launchResult);
            }
        });

    }

    public void launchDisplayFriendStatsPage(int week, String friendEmail) {
        Intent intent = new Intent(this, DisplayFriendActivity.class);
        intent.putExtra( "week", "" + week );
        intent.putExtra( "email", friendEmail );
        startActivity(intent);
    }
}
