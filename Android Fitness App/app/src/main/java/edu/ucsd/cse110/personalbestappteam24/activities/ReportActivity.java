package edu.ucsd.cse110.personalbestappteam24.activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import edu.ucsd.cse110.personalbestappteam24.R;
import edu.ucsd.cse110.personalbestappteam24.report.WeeklyReport;

import android.view.View;
import android.widget.Button;

import edu.ucsd.cse110.personalbestappteam24.report.SetGraph;
import edu.ucsd.cse110.personalbestappteam24.user.User;

import com.github.mikephil.charting.charts.CombinedChart;


public class ReportActivity extends AppCompatActivity {
    public WeeklyReport report;
    CombinedChart chart;
    SetGraph set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Button monthlyBtn = findViewById(R.id.monthlyBtn);

        chart = (CombinedChart) findViewById(R.id.chart1);
        User user = MainActivity.user;

        set = new SetGraph(this, 7, false, user.getDailySteps(), user.getStepGoals(), user.getWalksAndRuns());
        chart = set.getChart();
        monthlyBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                launchMonthlyPage();
            }
        });
    }
    public void launchMonthlyPage() {
        Intent intent = new Intent(this, MonthlyActivity.class);
        startActivity(intent);
    }
    public CombinedChart getChart() {
        return chart;
    }

    public SetGraph getSetGraph() {
        return set;
    }
}
