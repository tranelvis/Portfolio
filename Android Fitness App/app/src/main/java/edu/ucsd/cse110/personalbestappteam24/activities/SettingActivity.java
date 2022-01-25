package edu.ucsd.cse110.personalbestappteam24.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import android.widget.TextView;

import edu.ucsd.cse110.personalbestappteam24.R;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;

import static edu.ucsd.cse110.personalbestappteam24.activities.MainActivity.user;

public class SettingActivity extends AppCompatActivity {

    Activity settingActivity = this;
    TextView textGoal;
    private String fitnessServiceKey = "GOOGLE_FIT";
    boolean areMocking;
    static SeekBar barChangeGoal;
    static TextView labelGoal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent = getIntent();

        areMocking = intent.getBooleanExtra("areMocking", false);
        areMocking = true;

        Button btnSignOut = findViewById(R.id.button_signout);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                launchLoginActivity();
            }
        });

        barChangeGoal = findViewById(R.id.seekBar_goal);
        labelGoal = findViewById(R.id.label_goal);
        barChangeGoal.setMax(50000);
        updateGoalUI();

        barChangeGoal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                 @Override
                 public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                     user.setStepGoal(progress);
                     MainActivity.updateUI();
                     labelGoal.setText(Integer.toString(progress));
                 }

                 @Override
                 public void onStartTrackingTouch(SeekBar seekBar) {

                 }

                 @Override
                 public void onStopTrackingTouch(SeekBar seekBar) {

                 }
             }
        );

        final TextView label_step = findViewById(R.id.label_step);
        Button button_change_steps = findViewById(R.id.button_change_steps);
        int curSteps = (int)user.getDailySteps().getSteps();
        label_step.setText(Integer.toString(curSteps) + " steps");
        button_change_steps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (areMocking) {
                    int nxtSteps = (int) user.getDailySteps().getSteps() + 500;
                    user.setDailySteps(nxtSteps);
                    MainActivity.updateUI();
                    label_step.setText(Integer.toString(nxtSteps) + " steps");
                }
            }
        });

        Button changeNameBtn = findViewById(R.id.mockRequestBtn);
        final EditText newName = findViewById(R.id.mockName);

        changeNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (areMocking) {
                    MainActivity.sendMockRequest(newName.getText().toString());
                }

            }
        });
    }

    public static void updateGoalUI() {
        barChangeGoal.setProgress((int)user.getStepGoal());
        labelGoal.setText(Integer.toString((int)user.getStepGoal()));
    }

    private void signOut() {
        Fitness.getConfigClient(this, GoogleSignIn.getLastSignedInAccount(this)).disableFit();
    }

    public void launchLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
