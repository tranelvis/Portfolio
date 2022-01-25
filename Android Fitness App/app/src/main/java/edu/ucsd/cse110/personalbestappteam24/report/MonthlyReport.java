package edu.ucsd.cse110.personalbestappteam24.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import edu.ucsd.cse110.personalbestappteam24.user.DailySteps;
import edu.ucsd.cse110.personalbestappteam24.user.User;



public class MonthlyReport {

    Calendar calendar = Calendar.getInstance();
    DailySteps daySteps;
    DayData[] weekData = new DayData[28];
    Date date;
    User user;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat monthAndDayOnly = new SimpleDateFormat("MM/dd");
    ArrayList<String> labels = new ArrayList<String>();

    public MonthlyReport(User user) {

        //format date from the first day of the week
        this.user = user;
        //calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.add(Calendar.DAY_OF_MONTH, -27);
        date = calendar.getTime();
        updateReport();
        printReport();
    }

    public void updateReport() {

        int i = 0;
        //loops until date from the first week reaches today
        for (; i < 28; calendar.add(Calendar.DAY_OF_YEAR, 1), i++) {
            date = calendar.getTime();

            String stringDate = dateFormatter.format(date);
            long daySteps = user.getDailySteps().getStepsForDate(stringDate);
            long stepGoal = user.getStepGoals().getStepGoalForDate(stringDate);

            DayData oneDay = new DayData(daySteps, stepGoal, user.getWalksAndRuns(), stringDate);
            labels.add( monthAndDayOnly.format(date));
            //if there's no steps on that day
            if( oneDay.getDaySteps() == -1 ) {
                oneDay.setDaySteps( 0 );
                oneDay.setStepGoal(( 0 ));
                oneDay.setIntentionalSteps(0);
            }
            weekData[i] = oneDay;
        }
    }

    public DayData[] reportFromFirstDayOfWeek() {
        return this.weekData;
    }

    public void printReport() {
        for (int i = 0; i < weekData.length; i++) {
            System.out.println(weekData[i].getDate() +
                    "   " + weekData[i].getDaySteps() +
                    "   " + weekData[i].getDayStepGoal() +
                    "   " + weekData[i].getDayIntentionalSteps());
        }
    }

    public ArrayList<String> getDates() {
        return labels;
    }
}