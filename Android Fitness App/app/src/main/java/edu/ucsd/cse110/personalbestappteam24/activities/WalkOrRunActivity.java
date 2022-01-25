package edu.ucsd.cse110.personalbestappteam24.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import edu.ucsd.cse110.personalbestappteam24.R;

public class WalkOrRunActivity extends AppCompatActivity {
    private static String TAG = "WalkOrRunActivity";
    static TextView walkSteps;
    static TextView walkDistance;
    static TextView walkTime;
    static TextView walkSpeed;
    Button endWalkRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_or_run);


        // UI element definitions.
        walkSpeed = (TextView) findViewById(R.id.speedTextView);
        walkSteps = (TextView) findViewById(R.id.stepsTextView);
        walkDistance = (TextView) findViewById(R.id.distanceTextView);
        walkTime = (TextView) findViewById(R.id.timeTextView);
        endWalkRun = (Button) findViewById(R.id.EndWalkButton);

        // Button listeners/callbacks
        endWalkRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(MainActivity.currentWalkRun != null) {
                Toast.makeText(WalkOrRunActivity.this, "Your Walk/Run has ended", Toast.LENGTH_SHORT).show();


                // End all processes/objects that relate to this walk/run.
                MainActivity.user.addWalkRun(MainActivity.currentWalkRun);
                MainActivity.user.getPastWalkRuns().addWalkRun(MainActivity.currentWalkRun);
                MainActivity.currentWalkRun = null;
                MainActivity.walkUpdater.cancel(true);

                //TESTING
                MainActivity.user.getStorage().printStorage();
                //MainActivity.user.getStorage().printStorage();

                Log.d(TAG, "Walk/Run Ended");
            }
            }
        });
    }

    /**
     * Updates the WalkOrRunActivity's UI to reflect the data stored within currentWalkRun.
     */
    public static void updateUI() {
        updateStepText();
        updateTimeText();
        updateDistanceText();
        updateSpeedText();
    }

    public static void updateStepText() {
        if(walkSteps != null) {
            String stepCountString = "" + MainActivity.currentWalkRun.getSteps();
            walkSteps.setText(stepCountString);
        }
    }

    public static void updateDistanceText() {
        if(walkDistance != null) {
            double distance = MainActivity.currentWalkRun.getDistance();
            String distanceString = new DecimalFormat("#.##").format(distance) + " mi";
            walkDistance.setText(distanceString);
        }
    }

    public static void updateSpeedText() {
        if(walkSpeed != null) {
            String speed = new DecimalFormat("#.##").format(MainActivity.currentWalkRun.computeSpeed()) + " mph";
            walkSpeed.setText(speed);
        }
    }

    public static void updateTimeText() {
        if(walkTime != null) {
            walkTime.setText(MainActivity.currentWalkRun.getTime());
        }
    }
}
