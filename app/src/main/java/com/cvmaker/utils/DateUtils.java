package com.cvmaker.utils;

import android.util.Log;

import com.cvmaker.data.local.prefs.AppPreferencesHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.cvmaker.utils.LogUtils.LOGE;

/**
 * Created by aya mohamed on 5/30/2018.
 */

public class DateUtils {

    private static AppPreferencesHelper appPreferencesHelper;

    public DateUtils(AppPreferencesHelper appPreferencesHelper) {
        DateUtils.appPreferencesHelper = appPreferencesHelper;
    }


    /**
     * Gets timestamp in millis and converts it to HH:mm (e.g. 16:44).
     */
    public static String formatTime(long timeInMillis) {
        LOGE("appPreferencesHelper.getLang(): " + new Locale(appPreferencesHelper.getLang()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", new Locale(appPreferencesHelper.getLang()));
        return dateFormat.format(timeInMillis);
    }

    public static String formatTimeWithMarker(long timeInMillis) {
        LOGE("appPreferencesHelper.getLang(): " + new Locale(appPreferencesHelper.getLang()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", new Locale(appPreferencesHelper.getLang()));
        return dateFormat.format(timeInMillis);
    }

    public static int getHourOfDay(long timeInMillis) {
        LOGE("appPreferencesHelper.getLang(): " + new Locale(appPreferencesHelper.getLang()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("H", new Locale(appPreferencesHelper.getLang()));
        return Integer.valueOf(dateFormat.format(timeInMillis));
    }

    public static int getMinute(long timeInMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("m", new Locale(appPreferencesHelper.getLang()));
        return Integer.valueOf(dateFormat.format(timeInMillis));
    }

    /**
     * If the given time is of a different date, display the date.
     * If it is of the same date, display the time.
     *
     * @param timeInMillis The time to convert, in milliseconds.
     * @return The time or date.
     */
    public static String formatDateTime(long timeInMillis) {
        if (isToday(timeInMillis)) {
            return formatTime(timeInMillis);
        } else {
            return formatDate(timeInMillis);
        }
    }

    /**
     * Formats timestamp to 'date month' format (e.g. 'February 3').
     */
    public static String formatDate(long timeInMillis) {
        LOGE("appPreferencesHelper.getLang(): " + new Locale(appPreferencesHelper.getLang()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM YYYY", new Locale(appPreferencesHelper.getLang()));
        return dateFormat.format(timeInMillis);
    }

    /**
     * Formats timestamp to 'date month' format (e.g. 'February 3').
     */
    public static String formatDateNum(long timeInMillis) {
        LOGE("appPreferencesHelper.getLang(): " + new Locale(appPreferencesHelper.getLang()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return dateFormat.format(timeInMillis);
    }

    /**
     * Returns whether the given date is today, based on the user's current locale.
     */
    public static boolean isToday(long timeInMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", new Locale(appPreferencesHelper.getLang()));
        String date = dateFormat.format(timeInMillis);
        return date.equals(dateFormat.format(System.currentTimeMillis()));
    }

    /**
     * @param timeInMillis
     * @return
     */
    public static String reformatDate(long timeInMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'z", new Locale(appPreferencesHelper.getLang()));
        String date = dateFormat.format(timeInMillis);
        return date;
    }

    /**
     * Checks if two dates are of the same day.
     *
     * @param millisFirst  The time in milliseconds of the first date.
     * @param millisSecond The time in milliseconds of the second date.
     * @return Whether {@param millisFirst} and {@param millisSecond} are off the same day.
     */
    public static boolean hasSameDate(long millisFirst, long millisSecond) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", new Locale(appPreferencesHelper.getLang()));
        return dateFormat.format(millisFirst).equals(dateFormat.format(millisSecond));
    }

    /**
     * @param expiredDate
     * @param currentDate
     * @return
     */
    public static long getDurationInMilliSeconds(String expiredDate, String currentDate) {
        long duration = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        Calendar c_endDate = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(currentDate));
            c_endDate.setTime(sdf.parse(expiredDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endDate = sdf1.format(c_endDate.getTime());
        String currDate = sdf1.format(c.getTime());
        Log.i("date", endDate);
        Log.i("curr date", currDate);

        Date curDate = null, eDate = null;
        try {
            eDate = sdf1.parse(endDate);
            long eTimeInMillis = eDate.getTime();
            Log.i("milliSeconds0:", String.valueOf(eTimeInMillis));
            curDate = sdf.parse(currDate);
            long curTimeInMillis = curDate.getTime();
            Log.i("milliSeconds1:", String.valueOf(curTimeInMillis));
            duration = eTimeInMillis - curTimeInMillis;
            Log.i("milliSeconds2:", String.valueOf(duration));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return duration;
    }

    public static String getDateInCommonFormat(String date) {
        String newDate = date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = sdf.parse(date);
            newDate = DateFormat.getDateInstance(DateFormat.FULL, new Locale(appPreferencesHelper.getLang())).format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.w("date", "newFormat:" + newDate);
        return newDate;
    }

    public static String getHrFromDate(String date) {
        String newDate = date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            int hours = cal.get(Calendar.HOUR_OF_DAY);
            int min = cal.get(Calendar.MINUTE);
            if (hours < 10 && min < 10) {
                newDate = "0" + hours + ":0" + min;
            } else if (hours >= 10 && min < 10) {
                newDate = hours + ":0" + min;
            } else if (hours >= 10 && min >= 10) {
                newDate = hours + ":" + min;
            } else if (hours < 10 && min >= 10) {
                newDate = "0" + hours + ":" + min;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.w("date", "newFormat:" + newDate);
        return newDate;
    }

    public static String getHrToDate(String date, String consDuration) {
        String newDate = date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = sdf.parse(date);
            int duration = 0;
            long timeInMillis = d.getTime() + duration;
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(timeInMillis);
            int hours = cal.get(Calendar.HOUR_OF_DAY);
            int min = cal.get(Calendar.MINUTE);
            if (hours < 10 && min < 10) {
                newDate = "0" + hours + ":0" + min;
            } else if (hours >= 10 && min < 10) {
                newDate = hours + ":0" + min;
            } else if (hours >= 10 && min >= 10) {
                newDate = hours + ":" + min;
            } else if (hours < 10 && min >= 10) {
                newDate = "0" + hours + ":" + min;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.w("date", "newFormat:" + newDate);
        return newDate;

    }

    public static String getDayName(long timeInMillis) {
        LOGE("appPreferencesHelper.getLang(): " + new Locale(appPreferencesHelper.getLang()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", new Locale(appPreferencesHelper.getLang()));
        return dateFormat.format(timeInMillis);
    }
}
