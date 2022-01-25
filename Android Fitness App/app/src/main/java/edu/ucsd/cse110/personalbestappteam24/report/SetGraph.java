package edu.ucsd.cse110.personalbestappteam24.report;

import android.app.Activity;
import android.graphics.Color;

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

import java.util.ArrayList;

import edu.ucsd.cse110.personalbestappteam24.R;
import edu.ucsd.cse110.personalbestappteam24.activities.MainActivity;
import edu.ucsd.cse110.personalbestappteam24.user.DailySteps;
import edu.ucsd.cse110.personalbestappteam24.user.StepGoals;
import edu.ucsd.cse110.personalbestappteam24.user.WalksAndRuns;

import static edu.ucsd.cse110.personalbestappteam24.activities.MainActivity.user;

public class SetGraph {

    BarDataSet intentionalStepData;
    BarDataSet dailyStepData;
    LineDataSet stepGoalData;
    XAxis xaxis;
    CombinedChart chart;
    private int daysToDisplay = 7;

    public SetGraph(Activity act, int daysAgo, boolean isMonth, DailySteps dailyStepsObject, StepGoals stepGoals, WalksAndRuns walksAndRuns) {
        chart = (CombinedChart) act.findViewById(R.id.chart1);
        ArrayList<BarEntry> dailySteps = new ArrayList<>();
        ArrayList<BarEntry> intentionalSteps = new ArrayList<>();
        LineData goals;
        ArrayList<Entry> dailyStepGoal = new ArrayList<Entry>();
        ArrayList<String> labels = new ArrayList<String>();
        DayData[] daysInAPeriod;


        WeeklyReport thisWeek = new WeeklyReport(dailyStepsObject, stepGoals, walksAndRuns, daysAgo);

        MonthlyReport thisMonth = new MonthlyReport(user);
        if( isMonth ) {
            //thisMonth = new MonthlyReport(user);
            daysToDisplay = 28;
            daysInAPeriod = thisMonth.reportFromFirstDayOfWeek();
        } else {
            //thisWeek = new WeeklyReport(user, daysAgo);
            daysInAPeriod = thisWeek.reportFromFirstDayOfWeek();
        }//setContentView(chart);

        float xAxis = 0;
        for( int day = 0; day < daysToDisplay; day++, xAxis++ ) {
            Long firstInput = daysInAPeriod[day].getDaySteps();
            Long secondInput = daysInAPeriod[day].getDayIntentionalSteps();
            Long thirdInput = daysInAPeriod[day].getDayStepGoal();
            dailySteps.add(new BarEntry( xAxis, firstInput.floatValue() ));
            intentionalSteps.add(new BarEntry( xAxis, secondInput.floatValue() ));
            dailyStepGoal.add(new BarEntry( xAxis, thirdInput.floatValue() ));
        }

//        BarDataSet dailyStepData = set.getDailyStepData();
//        BarDataSet intentionalStepData = set.getIntentionalStepData();
//        LineDataSet stepGoalData = set.getStepGoalData();
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

        if( isMonth ) {
            labels = thisMonth.getDates();
        } else {
            labels = thisWeek.getDates();
        }

        BarData data = new BarData(dailyStepData, intentionalStepData);
        data.setBarWidth(0.9f);
        chart.setDrawGridBackground(false);
        chart.getDescription().setText("");
        //chart.setDrawBarShadow(true);
        //chart.setHighlightFullBarEnabled(true);

        CombinedData LineAndBar = new CombinedData();
        LineAndBar.setData(data);
        LineAndBar.setData(goals);
        //XAxis xaxis = set.getXaxis();
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

    public BarDataSet getIntentionalStepData() {
        return intentionalStepData;
    }

    public BarDataSet getDailyStepData() {
        return dailyStepData;
    }

    public LineDataSet getStepGoalData() {
        return stepGoalData;
    }

    public XAxis getXaxis() {
        return xaxis;
    }

    public CombinedChart getChart() {
        return chart;
    }

    public int getDaysToDisplay() {
        return this.daysToDisplay;
    }
}
