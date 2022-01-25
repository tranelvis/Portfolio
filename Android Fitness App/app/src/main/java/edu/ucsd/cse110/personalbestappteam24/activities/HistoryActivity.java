package edu.ucsd.cse110.personalbestappteam24.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

import edu.ucsd.cse110.personalbestappteam24.R;
import edu.ucsd.cse110.personalbestappteam24.report.WeeklyReport;
import edu.ucsd.cse110.personalbestappteam24.walk_run.Past5WalkRuns;
import edu.ucsd.cse110.personalbestappteam24.walk_run.WalkRun;

public class HistoryActivity extends AppCompatActivity {
    Past5WalkRuns pastWalkRuns;
    TextView walkRun1;
    TextView walkRun2;
    TextView walkRun3;
    TextView walkRun4;
    TextView walkRun5;

    DecimalFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        pastWalkRuns = MainActivity.user.getPastWalkRuns();
        pastWalkRuns.retrievePastWalkRuns();
        format = new DecimalFormat("#.##");

        walkRun1 = (TextView) findViewById(R.id.walkRun1);
        walkRun2 = (TextView) findViewById(R.id.walkRun2);
        walkRun3 = (TextView) findViewById(R.id.walkRun3);
        walkRun4 = (TextView) findViewById(R.id.walkRun4);
        walkRun5 = (TextView) findViewById(R.id.walkRun5);

        updateTextViews();
    }





    public void updateTextViews() {
        WalkRun[] walkRuns = pastWalkRuns.getPastWalkRuns();

        if (walkRuns[0] != null) {
            walkRun1.setText(getWalkRunText(walkRuns[0]));
            walkRun1.setVisibility(TextView.VISIBLE);
        }
        else { walkRun1.setVisibility(TextView.INVISIBLE); }

        if (walkRuns[1] != null) {
            walkRun2.setText(getWalkRunText(walkRuns[1]));
            walkRun2.setVisibility(TextView.VISIBLE);
        }
        else { walkRun2.setVisibility(TextView.INVISIBLE); }

        if (walkRuns[2] != null) {
            walkRun3.setText(getWalkRunText(walkRuns[2]));
            walkRun3.setVisibility(TextView.VISIBLE);
        }
        else { walkRun3.setVisibility(TextView.INVISIBLE); }

        if (walkRuns[3] != null) {
            walkRun4.setText(getWalkRunText(walkRuns[3]));
            walkRun4.setVisibility(TextView.VISIBLE);
        }
        else { walkRun4.setVisibility(TextView.INVISIBLE); }

        if (walkRuns[4] != null) {
            walkRun5.setText(getWalkRunText(walkRuns[4]));
            walkRun5.setVisibility(TextView.VISIBLE);
        }
        else { walkRun5.setVisibility(TextView.INVISIBLE); }
    }

    public String getWalkRunText(WalkRun walkRun) {

         return "Start Time: " + walkRun.getStartTime() +
                "\nSteps: " + walkRun.getSteps() + "           Time: " + walkRun.getTime() +
                "\nDistance: " + format.format(walkRun.getDistance()) + " mi    Speed: " + format.format(walkRun.getSpeed()) + " mph";
    }
}
