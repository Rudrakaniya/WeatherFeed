package com.example.weatherfeed;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CurrentTime {

    private String mDay;
    private String mNextDay1;
    private String mNextDay2;
    private String mNextDay3;
    private String mNextDay4;
    private String mNextDay5;
    private String mMinutes;
    private String mHour;
    private String mDate;
    private String mMonth;

    public enum Months implements Serializable {
        January,
        February,
        March,
        April,
        May,
        June,
        July,
        August,
        September,
        October,
        November,
        December
    }

    public enum Days implements Serializable {
        Sunday,
        Monday,
        Tuesday,
        Wednesday,
        Thursday,
        Friday,
        Saturday
    }

    public static CurrentTime getCurrentTime(long timeStamp) {
        CurrentTime doTime = new CurrentTime();

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeStamp);

        int dayNum = c.get(Calendar.DAY_OF_WEEK);

        Days day = Days.values()[dayNum];
        doTime.mDay = day.toString();

        String upcommingDays[] = new String[5];

        for (int i = 0; i < 5; ++i) {
            dayNum++;
            if (dayNum > 7) {
                dayNum %= 7;
            }
            day = Days.values()[dayNum];
            upcommingDays[i] = day.toString();
        }

        doTime.mNextDay1 = upcommingDays[0];
        doTime.mNextDay2 = upcommingDays[1];
        doTime.mNextDay3 = upcommingDays[2];
        doTime.mNextDay4 = upcommingDays[3];
        doTime.mNextDay5 = upcommingDays[4];


        //Get date and time, don't get excited lol!! its not a reallife wali date. hold your horses kido!!
        Date currentTime = Calendar.getInstance().getTime();


        SimpleDateFormat df = new SimpleDateFormat("MM");
        int monthNo = Integer.parseInt(df.format(c.getTime()));
        Months month = Months.values()[monthNo];
        doTime.mMonth = month.toString();


        df = new SimpleDateFormat("dd");
        doTime.mDate = df.format(currentTime.getTime());

        df = new SimpleDateFormat("HH");
        int intHour = Integer.parseInt(df.format(currentTime.getTime()));
        intHour %= 12;
        doTime.mHour = Integer.toString(intHour);

        df = new SimpleDateFormat("mm");
        doTime.mMinutes = df.format(currentTime.getTime());

        return doTime;
    }

    public String getDay() {
        return mDay;
    }

    public String getNextDay1() {
        return mNextDay1;
    }

    public String getNextDay2() {
        return mNextDay2;
    }

    public String getNextDay3() {
        return mNextDay3;
    }

    public String getNextDay4() {
        return mNextDay4;
    }

    public String getNextDay5() {
        return mNextDay5;
    }

    public String getMinutes() {
        return mMinutes;
    }

    public String getHour() {
        return mHour;
    }

    public String getDate() {
        return mDate;
    }

    public String getMonth() {
        return mMonth;
    }
}
