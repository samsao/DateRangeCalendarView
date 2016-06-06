package com.samsaodev.daterangecalendarview.utils;

import android.content.Context;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import timber.log.Timber;

/**
 * Created by lcampos on 2016-01-14.
 */
public class DateUtils {

    public static Date parseTimeFromLocalFormat(Context context, String timeString) {
        SimpleDateFormat formatter = new SimpleDateFormat(getTimeFormat(context), Locale.getDefault());

        Date date = new Date();
        if (!TextUtils.isEmpty(timeString)) {
            try {
                date = formatter.parse(timeString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public static Date parseTimeFrom24hFormat(String timeString) {
        DateFormat formatter = new SimpleDateFormat("HH:mm");

        Date date = new Date();
        if (!TextUtils.isEmpty(timeString)) {
            try {
                date = formatter.parse(timeString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }



    /**
     * Parse a Date to a Medium DateFormat string
     * Ex.: Feb 25, 2016
     *
     * @param date
     * @return
     */
    public static String formatDateToMediumDateFormatString(Date date) {
        if (date == null) {
            return "";
        }
        DateFormat localFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
        return localFormatter.format(date);
    }

    /**
     * Ex.: 10-05-16
     *
     * @param date
     * @return
     */
    public static String formatDateShortAsHyphenString(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        DateFormat localFormatter = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.getDefault());
        localFormatter.setTimeZone(cal.getTimeZone());
        return localFormatter.format(date);
    }

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
     * Ex.: 2010-05-16
     *
     * @param date
     * @return
     */
    public static String formatDateLongDashString(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        DateFormat localFormatter = new SimpleDateFormat("dd/MM/yyyy");
        localFormatter.setTimeZone(cal.getTimeZone());
        return localFormatter.format(date);
    }

    /**
     * Ex.: Monday
     *
     * @param date
     * @return
     */
    public static String getDayOfTheWeek(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
    }

    /**
     * Ex.: march 2, 2016
     *
     * @param date
     * @return
     */
    public static String formatDateMedium(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat localFormatter = new SimpleDateFormat("MMMM d', 'yyyy", Locale.getDefault());
        localFormatter.setTimeZone(cal.getTimeZone());
        return localFormatter.format(date);
    }

    /**
     * Ex.: saturday march 2, 2016
     *
     * @param date
     * @return
     */
    public static String formatDateLong(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat localFormatter = new SimpleDateFormat("EEE MMMM d', 'yyyy", Locale.getDefault());
        localFormatter.setTimeZone(cal.getTimeZone());
        return localFormatter.format(date);
    }

    /**
     * Ex.: Tue Jan 12, 2016
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
     * Parse a String (Feb 25, 2016) to a Date
     *
     * @param dateString
     * @return
     */
    public static Date parseMediumDateFormatString(String dateString) {
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
        Date date = null;
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(formatter.parse(dateString));
            date = c.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * Format time string
     * Ex.: 18:06
     *
     * @param context
     * @param time
     * @return
     */
    public static String formatTimeLocalDefault(Context context, Date time) {
        if (time == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat localFormatter = new SimpleDateFormat(getTimeFormat(context), Locale.getDefault());
        localFormatter.setTimeZone(cal.getTimeZone());

        return localFormatter.format(time);
    }

    private static String getTimeFormat(Context context) {
        if (android.text.format.DateFormat.is24HourFormat(context)) {
            return "HH:mm";
        } else {
            return "h:mm aa";
        }
    }



    public static String getDateIn24hFormat(Date dateOldFormat) {
        SimpleDateFormat newDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return newDateFormat.format(dateOldFormat);
    }

    /**
     * Get yesterday string
     *
     * @return
     */
    public static String getYesterday() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return format.format(calendar.getTime());
    }

    /**
     * Get 7 days ago string
     *
     * @return
     */
    public static String getSevenDaysAgo() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        return format.format(calendar.getTime());
    }

    /**
     * Get 28 days ago string
     *
     * @return
     */
    public static String getTwentyEightDaysAgo() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -28);
        return format.format(calendar.getTime());
    }


    /**
     * Get date from yyyy-MM-dd string
     *
     * @param dateString
     * @return
     */
    public static Date parseHyphenStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            Timber.e(e.getMessage());
        }
        return convertedDate;
    }

    /**
     * Increment a date by one
     *
     * @param start
     * @return
     */
    public static Date incrementDateByOneDay(Date start) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * Get the week day name string
     *
     * @param date
     * @return
     */
    public static String getWeekDayName(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH);
    }

    /**
     * Get the Date String 6 months before today
     *
     * @return
     */
    public static String getSixMonthsAgo() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -6);
        return format.format(calendar.getTime());
    }

    /**
     * Get a hms formatted string
     *
     * @param seconds
     * @return
     */
    public static String getFormattedHourMinSec(int seconds) {
        long sec = seconds % 60;
        seconds /= 60;
        long minute = seconds % 60;
        long hour = seconds / 60;

        String result = "";

        if (hour > 0) {
            result = String.format("%dh", hour);
            if (minute > 0) {
                result += String.format("%dm", minute);
            }
        } else {
            if (minute > 0) {
                result += String.format("%dm", minute);
            }

            if (sec > 0) {
                result += String.format("%ds", sec);
            }
        }

        if (result.equals("")) {
            result = "0s";
        }

        return result;
    }

    /**
     * Get abbreviate month name from Date
     *
     * @param date
     * @return
     */
    public static String getMonthAbbName(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getShortMonths();
        String month = months[calendar.get(Calendar.MONTH)];
        if (month.endsWith(".")) {
            month = month.replace(".", "");
        }
        return month;
    }

    /**
     * Get 90 days later
     *
     * @param date
     * @return
     */
    public static String getNinetyDaysLater(Date date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 90);
        return format.format(calendar.getTime());
    }

    /**
     * Get difference in months
     *
     * @param start
     * @param end
     * @return
     */
    public static int getDifferenceInMonths(Date start, Date end) {
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(start);
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(end);
        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        return diffMonth;
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
     * Increment date by one month
     *
     * @param date
     * @return
     */
    public static Date incrementDateByOneMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    /**
     * Get one month ago
     * @return
     */
    public static Date getOneMonthAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }

    /**
     * Get Last Month
     *
     * @param date
     * @return
     */
    public static int getLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return calendar.get(Calendar.MONTH);
    }

    public static boolean isToday(Date date) {
        if (date == null) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date);
        cal2.setTime(new Date());
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    private static Calendar getDatePart(Date date) {
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millisecond in second

        return cal;
    }

    public static Calendar getCalendarDate(Calendar startDate, int i) {
        startDate.add(Calendar.DAY_OF_MONTH, i);
        return startDate;
    }
}
