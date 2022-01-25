package edu.ucsd.cse110.personalbestappteam24.walk_run;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Calendar;

public class Timer {

    private long startTime;

    public Timer() {
        startTime = Calendar.getInstance().getTimeInMillis();
    }

    /**
     *
     * @return A formatted HH/MM/SS string of the elapsed time since the start time.
     */
    public String getElapsedTimeString() {
        return convertToHMS(Calendar.getInstance().getTimeInMillis() - startTime);
    }

    public long getElapsedSeconds() {
        return (Calendar.getInstance().getTimeInMillis() - startTime) / 1000;
    }

    private static String convertToHMS(long time) {
        int seconds = (int) (time / 1000);
        int minutes = seconds / 60 % 60;
        int hours = minutes / 60 % 60;
        seconds = seconds % 60;
        return "" + String.format("%02d:%02d:%02d", hours,minutes, seconds);
    }
}
