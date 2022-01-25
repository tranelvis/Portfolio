package edu.ucsd.cse110.personalbestappteam24.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.ucsd.cse110.personalbestappteam24.storage.MockUserData;
import edu.ucsd.cse110.personalbestappteam24.R;
import edu.ucsd.cse110.personalbestappteam24.fitness.FitnessService;
import edu.ucsd.cse110.personalbestappteam24.fitness.FitnessServiceFactory;
import edu.ucsd.cse110.personalbestappteam24.fitness.GoogleFitAdapter;
import edu.ucsd.cse110.personalbestappteam24.observer.StepCountService;
import edu.ucsd.cse110.personalbestappteam24.user.User;
import edu.ucsd.cse110.personalbestappteam24.walk_run.WalkRun;

public class MainActivity extends AppCompatActivity implements Observer {
    private FitnessService fitnessService;
    private String fitnessServiceKey = "GOOGLE_FIT";
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    public static final String EMAIL_ADDRESS = "EMAIL_ADDRESS";

    public static final Calendar today = new GregorianCalendar();

    private static final String TAG = "MainActivity";

    public static User user;

    public StepCountService stepService;

    private AsyncBackgroundStepsUpdater bgStepUpdater;
    public static AsyncWalkUpdater walkUpdater;
    public static WalkRun currentWalkRun;


    private Button settingsButton;
    private Button statsButton;
    private Button historyButton;
    private Button startWalkButton;

    public static TextView homeScreenSteps;

    // Controls wether we are using google API or our own button to update steps
    public static boolean areMocking = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create fitness service and set it up.
        FitnessServiceFactory.put(fitnessServiceKey, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(MainActivity mainActivity) {
                return new GoogleFitAdapter(mainActivity);
            }
        });
        fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);
        fitnessService.setup();


        GoogleSignInAccount lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(this);
        System.out.println(lastSignedInAccount);

        // User instantiation.
        //String emailAddress = GoogleSignIn.getLastSignedInAccount(this).getEmail();
        String emailAddress = "yic258@email.com";
        user = new User(emailAddress, this);

        //setting user's height
        System.err.println("This is the user's height" + user.getHeight());
        if (user.getHeight() == -1) {
            launchHeightPage();
        }

        // Observable class (Move to updateUI)
        stepService = new StepCountService();
        //GoalAchievementPrompt goalAchievementPrompt = new GoalAchievementPrompt(this);
        stepService.addObserver(this);

        // clearNotified when a new day comes
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String today = formatter.format(calendar.getTime());
        String lastNotifiedDay = user.getLastNotifiedDate();
        int todayInt = Integer.parseInt(today.substring(today.length() - 2));
        int lastNotifiedDayInt = Integer.parseInt(lastNotifiedDay.substring(lastNotifiedDay.length() - 2));
        if ( todayInt != lastNotifiedDayInt ) {
            user.clearNotified();
        }

        MockUserData data = (MockUserData) user.getStorage();
        data.createMockData();


        // Start the background step updater
        if ( !areMocking ) {
            bgStepUpdater = new AsyncBackgroundStepsUpdater();
            bgStepUpdater.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        // UI element definitions.
        settingsButton = (Button) findViewById(R.id.button_setting);
        statsButton = (Button) findViewById(R.id.button_stats);
        historyButton = (Button) findViewById(R.id.button_history);
        startWalkButton = (Button) findViewById(R.id.button_start);
        homeScreenSteps = (TextView) findViewById(R.id.homeScreenSteps);

        updateUI();

        // Button listeners/callbacks
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                launchSettingsPage();
            }
            });

         statsButton.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
        launchStatsPage();
        }
        });

         historyButton.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
        launchHistoryPage();
        }
        });

         startWalkButton.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
        launchWalkRunPage();
        }
        });
    }

    public void launchEncouragePage() {
        Intent intent = new Intent(this, EncourageActivity.class);
        startActivity(intent);
    }

    public void launchGoalAchievePage() {
        Intent intent = new Intent(this, GoalAchieveActivity.class);
        startActivity(intent);
    }

    public void launchSettingsPage() {
        Intent intent = new Intent(this, SettingActivity.class);
        intent.putExtra("areMocking", areMocking);
        startActivity(intent);
    }

    public void launchStatsPage() {
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
    }

    public void launchHistoryPage() {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void launchHeightPage() {
        Intent intent = new Intent(this, HeightActivity.class);
        startActivity(intent);
    }

    public void launchWalkRunPage() {
        // Start a new walk if one is not already started.
        if(walkUpdater == null || walkUpdater.isCancelled()) {
            Toast.makeText(MainActivity.this, "Starting your Walk/Run", Toast.LENGTH_SHORT).show();

            currentWalkRun = new WalkRun();
            walkUpdater = new AsyncWalkUpdater();
            walkUpdater.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        Intent intent = new Intent(this, WalkOrRunActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If authentication was required during google fit setup, this will be called after the user authenticates
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == fitnessService.getRequestCode()) {
                fitnessService.updateStepCount();
            }
        } else {
            Log.e(TAG, "ERROR, google fit result code: " + resultCode);
        }
    }


    /**
     * Callback for Fitness API to update user steps.
     * @param stepCount The total steps taken in this day.
     */
    public void setStepCount(long stepCount) {
        Log.d(TAG, "Step count updated to " + stepCount);
        user.setDailySteps(stepCount);
    }

    public static void updateUI() {
        updateTimeText();
    }

    public static void updateTimeText() {
        if(homeScreenSteps != null) {
            String totalSteps = Long.toString(MainActivity.user.getDailySteps().getSteps());
            String stepGoal = Long.toString(user.getStepGoal());
            String textToSet = totalSteps + "/" + stepGoal + " steps";
            homeScreenSteps.setText(textToSet);
        }
    }

    /**
     * This async task updates the user interface of the main activity with total step counts
     * periodically.
     */
    private class AsyncBackgroundStepsUpdater extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            while( !isCancelled() ) {
                // Update the user's step count.
                fitnessService.updateStepCount();

                // Update the UI for main activity
                MainActivity.this.updateUI();

                // GoalAchievement Observer
                MainActivity.this.stepService.notify((int)user.getDailySteps().getSteps());

                // Wait 5 seconds - less frequent because this is less important than the walk
                // step updater.
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "";
        }
    }

    /**
     * This asyc task updates the user interface of the walkorrun activity every second with updated
     * walk run data.
     */
    public class AsyncWalkUpdater extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            while( !isCancelled() && currentWalkRun != null) {
                // Update the walk run data.
                if (!areMocking) {
                    fitnessService.updateStepCount();
                }
                currentWalkRun.updateData();

                // Update the UI for the walk activity.
                WalkOrRunActivity.updateUI();

                // Wait 1 second.
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "";
        }
    }

    /**
     * Prevents user from going back to login activity.
     */
    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    public void update(Observable o, Object arg) {
        // Prompt for improvement of step goal
        int currentStep = (int) arg;
        if (currentStep >= user.getStepGoal() && !user.isNotified()) {
        //if (currentStep > -1 && !user.isNotified()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(MainActivity.this, "You have reached your step goal!", Toast.LENGTH_LONG).show();
                    launchGoalAchievePage();
                }
            });
        }

        // Send progress encouragement
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String today = formatter.format(calendar.getTime());
        String lastEncourageDay = user.getLastEncourageDate();
        int todayInt = Integer.parseInt(today.substring(today.length() - 2));
        int lastEncourageDayInt = Integer.parseInt(lastEncourageDay.substring(lastEncourageDay.length() - 2));
        if ( todayInt != lastEncourageDayInt ) {
            launchEncouragePage();
            user.updateLastEncourageDate();
        }
    }

    public void resetData() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(EMAIL_ADDRESS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
