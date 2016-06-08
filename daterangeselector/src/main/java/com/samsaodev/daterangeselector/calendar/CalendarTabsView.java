package com.samsaodev.daterangeselector.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.samsaodev.daterangeselector.R;
import com.samsaodev.daterangeselector.utils.DateUtils;

import java.util.Date;

/**
 * Created by jingsilu on 2016-05-30.
 */
public class CalendarTabsView extends ScrollView implements CalendarListener {
    protected CustomCalendarView mCustomCalendarView;

    protected LinearLayout mStartLayout;
    protected TextView mStartLabel;
    protected TextView mStartDateText;
    private int mStartDateSelectedColor; //start date text color when selected
    private int mStartDateUnselectedColor; //start date text color when unSelected
    private Drawable mStartTabSelectedBgDrawable;

    protected LinearLayout mEndLayout;
    protected TextView mEndLabel;
    protected TextView mEndDateText;

    private Drawable mEndTabSelectedBgDrawable;
    private int mStartTabUnSelectedBgColor;
    private int mEndTabUnSelectedTabBgColor;
    private int mEndDateSelectedColor;  //end date text color when selected
    private int mEndDateUnselectedColor; //end date text color when unSelected

    private Date mStartDate;
    private Date mEndDate;
    private String mDefaultDateString;

    private int mStartLabelSelectedColor;
    private int mEndLabelSelectedColor;
    private int mStartLabelUnselectedColor;
    private int mEndLabelUnselectedColor;

    public void setDefaultDateText(String defaultDate) {
        mDefaultDateString = defaultDate;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public void setStartDate(Date startDate) {
        mStartDate = startDate;
    }

    public void setEndDate(Date endDate) {
        mEndDate = endDate;
    }

    public CalendarTabsView(Context context) {
        super(context);
        init(context);
    }

    public CalendarTabsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        getAttributes(attrs);
    }

    public CalendarTabsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        getAttributes(attrs);
    }

    private void init(Context context) {
        final LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.calendar_tabs_view, this, true);
        mCustomCalendarView = (CustomCalendarView) view.findViewById(R.id.calendar_view);
        mStartLayout = (LinearLayout) view.findViewById(R.id.calendar_tabs_view_start_layout);
        mStartLabel = (TextView) view.findViewById(R.id.calendar_tabs_view_start_title);
        mStartDateText = (TextView) view.findViewById(R.id.calendar_tabs_view_start_date);
        mEndLayout = (LinearLayout) view.findViewById(R.id.calendar_tabs_view_end_layout);
        mEndLabel = (TextView) view.findViewById(R.id.calendar_tabs_view_end_title);
        mEndDateText = (TextView) view.findViewById(R.id.calendar_tabs_view_end_date);
        mStartLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickStartTab();
            }
        });
        mEndLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setupEndTabSelected();
            }
        });
        setupCalendarView();
    }

    private void getAttributes(AttributeSet attrs) {
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarTabsView, 0, 0);
        //non-selected week day bg color
        int calendarBackgroundColor = typedArray.getColor(R.styleable.CalendarTabsView_calendarBackgroundColor, ContextCompat.getColor(getContext(), R.color.theme_gray_light));
        //date title (Jun 2016) text color
        int calendarTitleTextColor = typedArray.getColor(R.styleable.CalendarTabsView_calendarTitleTextColor, ContextCompat.getColor(getContext(), R.color.theme_green_grass));
        //Weekday title text color (S, M, T, W, T, F, S)
        int dayOfWeekTextColor = typedArray.getColor(R.styleable.CalendarTabsView_dayOfWeekTextColor, ContextCompat.getColor(getContext(), R.color.theme_gray_text));
        //current month days text color
        int currentMonthDaysTextColor = typedArray.getColor(R.styleable.CalendarTabsView_currentMonthDaysTextColor, ContextCompat.getColor(getContext(), R.color.theme_gray_text));

        int daysInBetweenBackgroundColor = typedArray.getColor(R.styleable.CalendarTabsView_daysInBetweenBackgroundColor, ContextCompat.getColor(getContext(), R.color.theme_green_gray));
        //disabled day background color (at the beginning and at the end of a month)
        int disabledDayBackgroundColor = typedArray.getColor(R.styleable.CalendarTabsView_disabledDayBackgroundColor, ContextCompat.getColor(getContext(), R.color.theme_gray_light));
        //disabled day text color (at the beginning and at the end of a month)
        int disabledDayTextColor = typedArray.getColor(R.styleable.CalendarTabsView_disabledDayTextColor, ContextCompat.getColor(getContext(), R.color.theme_gray_text_lighter_alpha));
        //selected day background color (start date and end date)
        int selectedDayBackground = typedArray.getColor(R.styleable.CalendarTabsView_selectedDayBackgroundColor, ContextCompat.getColor(getContext(), R.color.theme_green_lime));
        //selected day text color (start date and end date)
        int selectedDayTextColor = typedArray.getColor(R.styleable.CalendarTabsView_selectedDayTextColor, ContextCompat.getColor(getContext(), R.color.white));
        typedArray.recycle();

        mCustomCalendarView.setAttributes(calendarBackgroundColor, calendarTitleTextColor, dayOfWeekTextColor, currentMonthDaysTextColor, daysInBetweenBackgroundColor, disabledDayBackgroundColor, disabledDayTextColor, selectedDayBackground, selectedDayTextColor);
    }

    private void setupCalendarView() {
        mCustomCalendarView.setCalendarListener(this);
        mCustomCalendarView.setStart(true);
        mStartDateText.setTextColor(mStartDateSelectedColor);
        mEndDateText.setTextColor(mEndDateSelectedColor);
        setStartDateUnselectedText(mDefaultDateString);
        setEndDateUnselectedText(mDefaultDateString);
    }

    public void setStartDateUnselectedColor(int startDateUnselectedColor) {
        mStartDateUnselectedColor = startDateUnselectedColor;
    }

    public void setEndDateUnselectedColor(int endDateUnselectedColor) {
        mEndDateUnselectedColor = endDateUnselectedColor;
    }

    public void setStartTabUnselectedBgColor(int color) {
        mStartTabUnSelectedBgColor = color;
    }

    public void setStartTabSelectedBgDrawable(Drawable drawable) {
        mStartTabSelectedBgDrawable = drawable;
        mStartLayout.setBackground(mStartTabSelectedBgDrawable);
    }

    public void setStartLabelText(String text) {
        mStartDateText.setText(text);
    }

    public void setEndLabelText(String text) {
        mEndDateText.setText(text);
    }

    public void setStartLabelUnselectedColor(int color) {
        mStartLabelUnselectedColor = color;
    }

    public void setEndLabelUnselectedColor(int color) {
        mEndLabelUnselectedColor = color;
    }

    public void setStartLabelSelectedColor(int color) {
        mStartLabelSelectedColor = color;
    }

    public void setEndLabelSelectedColor(int color) {
        mEndLabelSelectedColor = color;
    }

    public void setStartDateTextSelectedColor(int color) {
        mStartDateSelectedColor = color;
    }

    public void setEndTabUnselectedBgColor(int color) {
        mEndTabUnSelectedTabBgColor = color;
    }

    public void setEndTabSelectedBgDrawable(Drawable drawable) {
        mEndTabSelectedBgDrawable = drawable;
        mEndLayout.setBackgroundColor(mEndTabUnSelectedTabBgColor);
    }

    public void setEndDateTextSelectedColor(int color) {
        mEndDateSelectedColor = color;
    }

    public void setStartDateUnselectedText(String text) {
        mStartDateText.setText(text);
    }

    public void setEndDateUnselectedText(String text) {
        mEndDateText.setText(text);
    }

    private void setupStartDateText(String startDate) {
        mStartDateText.setText(startDate);
        mStartDateText.setTextColor(mStartDateSelectedColor);
    }

    private void setupEndDateText(String endDate) {
        mEndDateText.setText(endDate);
        mEndDateText.setTextColor(mEndDateSelectedColor);
    }

    private void setupStartTabSelected() {
        mCustomCalendarView.setStart(true);
        mStartLayout.setBackground(mStartTabSelectedBgDrawable);
        mEndLayout.setBackgroundColor(mEndTabUnSelectedTabBgColor);
        mStartLabel.setTextColor(mStartLabelSelectedColor);
        mEndLabel.setTextColor(mEndLabelUnselectedColor);
    }

    private void setupEndTabSelected() {
        mCustomCalendarView.setStart(false);
        mEndLayout.setBackground(mEndTabSelectedBgDrawable);
        mStartLayout.setBackgroundColor(mStartTabUnSelectedBgColor);
        mEndLabel.setTextColor(mEndLabelSelectedColor);
        mStartLabel.setTextColor(mStartLabelUnselectedColor);
    }

    @Override
    public void onStartDateSelected(Date date) {
        mStartDate = date;
        mStartLabel.setTextColor(mStartLabelSelectedColor);
        mEndLabel.setTextColor(mEndLabelUnselectedColor);
        setupStartDateText(DateUtils.formatDateShort(date));
        setupEndTabSelected();
    }

    @Override
    public void onEndDateSelected(Date date) {
        mEndDate = date;
        mEndLabel.setTextColor(mEndLabelSelectedColor);
        mStartLabel.setTextColor(mStartLabelUnselectedColor);
        setupEndDateText(DateUtils.formatDateShort(date));
    }

    public void setPreviousMonthButtonDrawable(Drawable drawable) {
        mCustomCalendarView.setPreviousMonthButtonDrawable(drawable);
    }

    public void setNextMonthButtonDrawable(Drawable drawable) {
        mCustomCalendarView.setNextMonthButtonDrawable(drawable);
    }

    /**
     * If there is an end date selected in another month, scroll calendar to start month
     */
    public void onClickStartTab() {
        setupStartTabSelected();
        if (mEndDate != null) {
            mCustomCalendarView.scrollToStartMonth();
        }
    }

    /**
     * Scroll calendar to start month if start date and end date are in the same month
     */
    public void scrollToStartMonth(Date startDate) {
        mStartDate = startDate;
        mCustomCalendarView.scrollToStartMonth(startDate);
    }

    /**
     * Scroll calendar to start month if start date and end date are not in the same month
     */
    public void scrollToStartMonth() {
        mCustomCalendarView.scrollToStartMonth();
    }


    /**
     * Reset the tabs and calendar
     *
     * @param defaultDate
     */
    public void setupReset(String defaultDate) {
        mStartDateText.setText(defaultDate);
        mEndDateText.setText(defaultDate);
        mStartDateText.setTextColor(mStartDateUnselectedColor);
        mEndDateText.setTextColor(mEndDateUnselectedColor);
        mStartLabel.setTextColor(mStartLabelUnselectedColor);
        mEndLabel.setTextColor(mEndLabelUnselectedColor);
        onClickStartTab();
        reset();
    }


    /**
     * Set up tabs and calendar with a start date and an end date
     *
     * @param startDate
     * @param endDate
     */
    public void setupSelectedDates(Date startDate, Date endDate) {
        mStartDateText.setText(DateUtils.formatDateShort(startDate));
        mEndDateText.setText(DateUtils.formatDateShort(endDate));
        mStartDateText.setTextColor(mStartDateSelectedColor);
        mEndDateText.setTextColor(mEndDateSelectedColor);
        mCustomCalendarView.setupSelectedDates(startDate, endDate);
    }

    public void reset() {
        mCustomCalendarView.reset();
    }

    public interface ResetHandler {
        void reset();
    }
}
