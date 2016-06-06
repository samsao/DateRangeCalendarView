package com.samsaodev.daterangecalendarview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.samsaodev.daterangeselector.calendar.CalendarTabsView;
import com.samsaodev.daterangeselector.utils.DateUtils;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jingsilu on 2016-05-31.
 */
public class MainActivity extends Activity {
    private CalendarTabsView mCalendarTabsView;
    private Button mResetBtn;
    private Button mDoneBtn;
    private Button mCurrentMonSelectionBtn;
    private Button mNextMonSelectionBtn;
    private String mDefaultDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCalendarTabsView = (CalendarTabsView) findViewById(R.id.activity_main_calendar_tabs);
        mResetBtn = (Button) findViewById(R.id.activity_main_btn_reset);
        mDoneBtn = (Button) findViewById(R.id.activity_main_btn_done);
        mCurrentMonSelectionBtn = (Button) findViewById(R.id.activity_main_btn_select_current_month);
        mNextMonSelectionBtn = (Button) findViewById(R.id.activity_main_btn_select_next_month);
        setup();
    }

    private void setup() {
        mDefaultDate = "----";
        mCalendarTabsView.setStartDateUnselectedColor(ContextCompat.getColor(this, R.color.theme_gray_text_lighter));
        mCalendarTabsView.setEndDateUnselectedColor(ContextCompat.getColor(this, R.color.theme_gray_text_lighter));
        mCalendarTabsView.setStartTabSelectedBgDrawable(ContextCompat.getDrawable(this, R.drawable.bg_calendar_selected_tab));
        mCalendarTabsView.setEndTabSelectedBgDrawable(ContextCompat.getDrawable(this, R.drawable.bg_calendar_selected_tab));
        mCalendarTabsView.setStartTabUnselectedBgColor(ContextCompat.getColor(this, R.color.theme_gray_light));
        mCalendarTabsView.setEndTabUnselectedBgColor(ContextCompat.getColor(this, R.color.theme_gray_light));
        mCalendarTabsView.setStartLabelSelectedColor(ContextCompat.getColor(this, R.color.theme_gray_text));
        mCalendarTabsView.setEndLabelSelectedColor(ContextCompat.getColor(this, R.color.theme_gray_text));
        mCalendarTabsView.setStartLabelUnselectedColor(ContextCompat.getColor(this, R.color.theme_gray_text_lighter));
        mCalendarTabsView.setEndLabelUnselectedColor(ContextCompat.getColor(this, R.color.theme_gray_text_lighter));
        mCalendarTabsView.setStartDateTextSelectedColor(ContextCompat.getColor(this, R.color.theme_green_lime));
        mCalendarTabsView.setEndDateTextSelectedColor(ContextCompat.getColor(this, R.color.theme_green_lime));
        mCalendarTabsView.setDefaultDateText(mDefaultDate);
        mCalendarTabsView.setPreviousMonthButtonDrawable(ContextCompat.getDrawable(this, R.drawable.ic_keyboard_arrow_left_green_36dp));
        mCalendarTabsView.setNextMonthButtonDrawable(ContextCompat.getDrawable(this, R.drawable.ic_keyboard_arrow_right_green_36dp));

        mResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarTabsView.reset();
                mCalendarTabsView.setupReset(mDefaultDate);
            }
        });

        mDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Start Date: ");
                stringBuilder.append(DateUtils.formatDateLongAsHyphenString(mCalendarTabsView.getStartDate()));
                stringBuilder.append(" End Date: ");
                stringBuilder.append(DateUtils.formatDateLongAsHyphenString(mCalendarTabsView.getEndDate()));
                Toast.makeText(MainActivity.this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        final Calendar calendar = Calendar.getInstance();
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getShortMonths();
        String currentMon = months[calendar.get(Calendar.MONTH)];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Select ");
        stringBuilder.append(currentMon);
        stringBuilder.append(" 16th");
        stringBuilder.append(" and ");
        stringBuilder.append(currentMon);
        stringBuilder.append(" 24th");
        mCurrentMonSelectionBtn.setText(stringBuilder.toString());
        calendar.set(Calendar.DAY_OF_MONTH, 16);
        final Date startDate1 = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, 24);
        final Date endDate1 = calendar.getTime();
        String nextMon = months[calendar.get(Calendar.MONTH) + 1];
        stringBuilder.setLength(0);
        stringBuilder.append("Select ");
        stringBuilder.append(currentMon);
        stringBuilder.append(" 16th");
        stringBuilder.append(" and ");
        stringBuilder.append(nextMon);
        stringBuilder.append(" 24th");
        mNextMonSelectionBtn.setText(stringBuilder.toString());
        calendar.set(Calendar.DAY_OF_MONTH, 16);
        final Date startDate2 = calendar.getTime();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DAY_OF_MONTH, 24);
        final Date endDate2 = calendar.getTime();

        mCurrentMonSelectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarTabsView.reset();
                mCalendarTabsView.setupSelectedDates(startDate1, endDate1);
                mCalendarTabsView.setStartDate(startDate1);
                mCalendarTabsView.scrollToStartMonth(startDate1); //this method is called when start date and end date are in the same month
            }
        });

        mNextMonSelectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarTabsView.reset();
                mCalendarTabsView.setupSelectedDates(startDate2, endDate2);
                mCalendarTabsView.scrollToStartMonth();  //this method is called when start date and end date are not in the same month
            }
        });
    }
}
