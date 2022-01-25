package edu.ucsd.cse110.personalbestappteam24.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import edu.ucsd.cse110.personalbestappteam24.R;
import edu.ucsd.cse110.personalbestappteam24.report.DayData;
import edu.ucsd.cse110.personalbestappteam24.report.WeeklyReport;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import static edu.ucsd.cse110.personalbestappteam24.activities.MainActivity.user;


public class ReportActivity extends AppCompatActivity {

    /*@Override
    public String getFormattedValue(float value, AxisBase axis) {
        return xAxisLabel.get((int) value);
    }
    }*/

    private final int daysToDisplay = 7;

    public WeeklyReport report;
    CombinedChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        chart = (CombinedChart) findViewById(R.id.chart1);

        ArrayList<BarEntry> dailySteps = new ArrayList<>();
        ArrayList<BarEntry> intentionalSteps = new ArrayList<>();
        LineData goals;
        ArrayList<Entry> dailyStepGoal = new ArrayList<Entry>();
        ArrayList<String> labels = new ArrayList<String>();
        WeeklyReport thisWeek = new WeeklyReport( user );
        DayData[] dayInAWeek = thisWeek.reportFromFirstDayOfWeek();
        //setContentView(chart);

        float xAxis = 0;
        for( int day = 0; day < daysToDisplay; day++, xAxis++ ) {
            Long firstInput = dayInAWeek[day].getDaySteps();
            Long secondInput = dayInAWeek[day].getDayIntentionalSteps();
            Long thirdInput = dayInAWeek[day].getDayStepGoal();
            dailySteps.add(new BarEntry( xAxis, firstInput.floatValue() ));
            intentionalSteps.add(new BarEntry( xAxis, secondInput.floatValue() ));
            dailyStepGoal.add(new BarEntry( xAxis, thirdInput.floatValue() ));
        }
        BarDataSet dailyStepData = new BarDataSet(dailySteps, "# of Steps");
        dailyStepData.setColor(Color.rgb(102, 178, 255));
        dailyStepData.setValueTextColor(Color.rgb(60, 220, 78));
        dailyStepData.setValueTextSize(10f);
        dailyStepData.setAxisDependency(YAxis.AxisDependency.LEFT);

        BarDataSet intentionalStepData = new BarDataSet(intentionalSteps, "Intentional Steps");
        intentionalStepData.setColor(Color.rgb(255, 102, 178));
        intentionalStepData.setValueTextColor(Color.rgb(255, 255, 50));
        intentionalStepData.setValueTextSize(10f);
        intentionalStepData.setHighLightColor(70);
        intentionalStepData.setAxisDependency(YAxis.AxisDependency.LEFT);

        float barWidth = 0.45f; // x2 dataset


        LineDataSet stepGoalData = new LineDataSet(dailyStepGoal, "Step Goals");
        stepGoalData.setLineWidth(2.5f);
        stepGoalData.setCircleColor(Color.rgb(240, 238, 70));
        stepGoalData.setCircleRadius(5f);
        stepGoalData.setFillColor(Color.rgb(240, 238, 70));
        stepGoalData.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        stepGoalData.setDrawValues(true);
        stepGoalData.setValueTextSize(10f);
        stepGoalData.setValueTextColor(Color.rgb(80, 30, 150));

        stepGoalData.setAxisDependency(YAxis.AxisDependency.LEFT);
        goals = new LineData(stepGoalData);

        labels.add("Sunday");
        labels.add("Monday");
        labels.add("Tuesday");
        labels.add("Wednes.");
        labels.add("Thursday");
        labels.add("Friday");
        labels.add("Saturday");

        BarData data = new BarData(dailyStepData, intentionalStepData);
        data.setBarWidth(0.9f);
        chart.setDrawGridBackground(false);
        chart.getDescription().setText("");
        //chart.setDrawBarShadow(true);
        //chart.setHighlightFullBarEnabled(true);

        CombinedData LineAndBar = new CombinedData();
        LineAndBar.setData(data);
        LineAndBar.setData(goals);
        XAxis xaxis = chart.getXAxis();
        xaxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xaxis.setAxisMinimum(0f);
        xaxis.setGranularity(1f);
        xaxis.setLabelCount(7);
        xaxis.setAxisMinimum(data.getXMin()-.5f);
        xaxis.setAxisMaximum(data.getXMax()+.5f);
        xaxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        YAxis yaxis = chart.getAxisRight();
        yaxis.setDrawGridLines(false);
        yaxis.setAxisMinimum(0);
        chart.setData(LineAndBar);
        chart.setFitsSystemWindows(true);
        chart.setX(25);
        chart.invalidate();

    }
}
