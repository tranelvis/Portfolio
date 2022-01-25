package edu.ucsd.cse110.personalbestappteam24.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.CombinedChart;

import edu.ucsd.cse110.personalbestappteam24.R;
import edu.ucsd.cse110.personalbestappteam24.report.SetGraph;
import edu.ucsd.cse110.personalbestappteam24.report.WeeklyReport;
import edu.ucsd.cse110.personalbestappteam24.report.WeeklyReportFragment;
import edu.ucsd.cse110.personalbestappteam24.user.User;

public class MonthlyActivity extends AppCompatActivity {


    private final int daysToDisplay = 30;

    public WeeklyReport report;
    CombinedChart chart;
    SetGraph set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Button monthlyBtn = findViewById(R.id.monthlyBtn);
        monthlyBtn.setVisibility(View.GONE);
        chart = (CombinedChart) findViewById(R.id.chart1);
      
        User user = MainActivity.user;
      
        set = new SetGraph(this, 28, true, user.getDailySteps(), user.getStepGoals(), user.getWalksAndRuns());

        chart = set.getChart();
    }

    public CombinedChart getChart() {
        return chart;
    }

    public SetGraph getSetGraph() {
        return set;
    }
}
