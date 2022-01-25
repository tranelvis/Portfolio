package edu.ucsd.cse110.personalbestappteam24.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import android.widget.TextView;

import edu.ucsd.cse110.personalbestappteam24.R;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;

import org.w3c.dom.Text;

public class SettingActivity extends AppCompatActivity {

    Activity settingActivity = this;
    TextView textGoal;
    private String fitnessServiceKey = "GOOGLE_FIT";
    boolean areMocking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent = getIntent();

        areMocking = intent.getBooleanExtra("areMocking", false);


        Button btnSignOut = findViewById(R.id.button_signout);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                launchLoginActivity();
            }
        });

        final SeekBar barChangeGoal = findViewById(R.id.seekBar_goal);
        final TextView labelGoal = findViewById(R.id.label_goal);
        barChangeGoal.setMax(50000);
        barChangeGoal.setProgress((int)MainActivity.user.getStepGoal());
        labelGoal.setText(Integer.toString((int)MainActivity.user.getStepGoal()));

        barChangeGoal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                 @Override
                 public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (true) {
                        MainActivity.user.setStepGoal(progress);
                        MainActivity.updateUI();
                    }
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
        int curSteps = (int)MainActivity.user.getDailySteps().getSteps();
        label_step.setText(Integer.toString(curSteps) + " steps");
        button_change_steps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (areMocking) {
                    int nxtSteps = (int) MainActivity.user.getDailySteps().getSteps() + 500;
                    MainActivity.user.setDailySteps(nxtSteps);
                    MainActivity.updateUI();
                    label_step.setText(Integer.toString(nxtSteps) + " steps");
                }
            }
        });
    }

    private void signOut() {
        Fitness.getConfigClient(this, GoogleSignIn.getLastSignedInAccount(this)).disableFit();
    }

    public void launchLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
