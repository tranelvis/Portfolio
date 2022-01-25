package edu.ucsd.cse110.personalbestappteam24.report;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.ucsd.cse110.personalbestappteam24.user.DailySteps;
import edu.ucsd.cse110.personalbestappteam24.user.User;



public class WeeklyReport {

    Calendar calendar = Calendar.getInstance();
    DailySteps daySteps;
    DayData[] weekData = new DayData[7];
    Date date;
    User user;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");

    public WeeklyReport(User user) {

        //format date from the first day of the week
        this.user = user;
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        date = calendar.getTime();
        updateReport();
        printReport();
    }

    public void updateReport() {

        int i = 0;
        //loops until date from the first week reaches today
        for (; i < 7; calendar.add(Calendar.DAY_OF_YEAR, 1), i++) {
            date = calendar.getTime();
            DayData oneDay = new DayData( user, dateFormatter.format(date) );
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
}
