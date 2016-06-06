package com.samsaodev.daterangeselector.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by jingsilu on 2016-05-31.
 */
public class DateUtils {

    /**
     * Ex.: 2010-05-16
     *
     * @param date
     * @return
     */
    public static String formatDateLongAsHyphenString(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        DateFormat localFormatter = new SimpleDateFormat("yyyy-MM-dd");
        localFormatter.setTimeZone(cal.getTimeZone());
        return localFormatter.format(date);
    }


    /**
     * Ex.: Tue Jan 12, 2016
     *
     * @param date
     * @return
     */
    public static String formatDateShort(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat localFormatter = new SimpleDateFormat("EEE MMM d', 'yyyy", Locale.getDefault());
        localFormatter.setTimeZone(cal.getTimeZone());
        return localFormatter.format(date);
    }


    /**
     * Get month of a date
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    /**
     * Get year of a date
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
}
