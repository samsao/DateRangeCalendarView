package com.samsaodev.daterangeselector.calendar;

import java.util.Date;

/**
 * Created by Nilanchala on 9/14/15.
 */
public interface CalendarListener {
    void onStartDateSelected(Date date);

    void onEndDateSelected(Date date);
}
