package edu.ucsd.cse110.personalbestappteam24.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.ucsd.cse110.personalbestappteam24.R;

public class GoalAchieveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_achieve);

        TextView text = findViewById(R.id.textView);
        Button btnYes = findViewById(R.id.button_yes);
        Button btnNo = findViewById(R.id.button_no);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currGoal = (int)MainActivity.user.getStepGoal() + 500;
                MainActivity.user.setStepGoal(currGoal);
                finish();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.user.setNotified();
                finish();
            }
        });

    }
}
