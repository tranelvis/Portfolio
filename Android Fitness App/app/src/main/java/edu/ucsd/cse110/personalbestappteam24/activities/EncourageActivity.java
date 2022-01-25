package edu.ucsd.cse110.personalbestappteam24.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.ucsd.cse110.personalbestappteam24.R;

public class EncourageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encourage);

        TextView text = findViewById(R.id.textView_encourage);
        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                finish();
            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String today = formatter.format(calendar.getTime());
        calendar.add(Calendar.DATE, -1);
        String yesterday = formatter.format(calendar.getTime());
        calendar.add(Calendar.DATE, -1);
        String dayBeforeYesterday = formatter.format(calendar.getTime());

        int yesterdayStep = (int)MainActivity.user.getDailySteps().getStepsForDate(yesterday);
        int dayBeforeYesterdayStep = (int)MainActivity.user.getDailySteps().getStepsForDate(dayBeforeYesterday);

        int yesterdayStepGoal = (int)MainActivity.user.getStepGoals().getStepGoalForDate(yesterday);
        //int dayBeforeYesterdayStepGoal = (int)MainActivity.user.getStepGoals().getStepGoalForDate(dayBeforeYesterday);

        if (yesterdayStep >= yesterdayStepGoal) {
            text.setText("Nice! You have met your step goal yesterday!");
        }
        else if (yesterdayStep >= dayBeforeYesterdayStep+1000) {
            text.setText("Good! You have made a big progress than the day before!");
        }
        else {
            text.setText("Don't give up! Keep working towards your step goal!");
        }
    }
}
