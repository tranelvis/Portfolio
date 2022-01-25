package edu.ucsd.cse110.personalbestappteam24.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.CombinedChart;

import edu.ucsd.cse110.personalbestappteam24.report.SetGraph;
import edu.ucsd.cse110.personalbestappteam24.report.WeeklyReport;
import edu.ucsd.cse110.personalbestappteam24.user.User;

import edu.ucsd.cse110.personalbestappteam24.R;


public class DisplayFriendActivity extends AppCompatActivity {

    public WeeklyReport report;
    CombinedChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_friend);
        Button monthlyBtn = findViewById(R.id.monthlyBtn);

        chart = (CombinedChart) findViewById(R.id.chart1);

        Intent intent = this.getIntent();
        final String friendEmail = intent.getStringExtra("email");
        final int week = Integer.parseInt(intent.getStringExtra("week"));

        User user = MainActivity.user;

        MainActivity.user.setReportForFriend(week, friendEmail, this);



    }

    public void updateUI(SetGraph set) {
        chart = set.getChart();
    }


}
