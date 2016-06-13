package com.samsaodev.daterangeselector.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.samsaodev.daterangeselector.R;
import com.samsaodev.daterangeselector.utils.DateUtils;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by jingsilu on 2016-05-11.
 */

public class CustomCalendarView extends LinearLayout implements CalendarTabsView.ResetHandler {
    private static final String START_CELL_TAG_ID = "1";
    private static final String END_CELL_TAG_ID = "35";
    private static final String DAY_OF_WEEK = "dayOfWeek";
    private static final String DAY_OF_MONTH_TEXT = "dayOfMonthText";
    private static final String DAY_OF_MONTH_CONTAINER = "dayOfMonthContainer";

    private Context mContext;

    private View view;
    private ImageView previousMonthButton;
    private ImageView nextMonthButton;

    private CalendarListener calendarListener;
    private Calendar currentCalendar;
    private Locale locale;

    private int firstDayOfWeek = Calendar.SUNDAY;

    private List<DayDecorator> decorators = null;

    private int disabledDayBackgroundColor;
    private int disabledDayTextColor;
    private int calendarBackgroundColor;
    private int selectedDayBackground;
    private int selectedDayTextColor;
    private int calendarTitleTextColor;
    private int dayOfWeekTitleTextColor;
    private int currentMonthDaysTextColor;
    private int daysInBetweenBackgroundColor;

    private int currentMonthIndex = 0;
    private boolean isOverflowDateVisible = true;
    private boolean isStart;

    private String mStartTagId;
    private String mEndTagId;

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date startDate) {
        mStartDate = startDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Date endDate) {
        mEndDate = endDate;
    }

    private Date mStartDate;
    private Date mEndDate;

    public void setPreviousMonthButtonDrawable(Drawable drawable) {
        previousMonthButton.setImageDrawable(drawable);
    }

    public void setNextMonthButtonDrawable(Drawable drawable) {
        nextMonthButton.setImageDrawable(drawable);
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public CustomCalendarView(Context mContext) {
        this(mContext, null);
    }

    public CustomCalendarView(Context mContext, AttributeSet attrs) {
        super(mContext, attrs);
        this.mContext = mContext;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            if (isInEditMode())
                return;
        }
    }

    public void setAttributes(int calendarBackgroundColor, int calendarTitleTextColor, int dayOfWeekTextColor, int currentMonthDaysTextColor, int daysInBetweenBackgroundColor, int disabledDayBackgroundColor, int disabledDayTextColor, int selectedDayBackground, int selectedDayTextColor) {
        this.calendarBackgroundColor = calendarBackgroundColor;
        this.calendarTitleTextColor = calendarTitleTextColor;

        this.dayOfWeekTitleTextColor = dayOfWeekTextColor;
        this.currentMonthDaysTextColor = currentMonthDaysTextColor;
        this.daysInBetweenBackgroundColor = daysInBetweenBackgroundColor;

        this.selectedDayBackground = selectedDayBackground;
        this.selectedDayTextColor = selectedDayTextColor;

        this.disabledDayTextColor = disabledDayTextColor;
        this.disabledDayBackgroundColor = disabledDayBackgroundColor;
        initializeCalendar();
    }

    private void initializeCalendar() {
        final LayoutInflater inflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflate.inflate(R.layout.custom_calendar_layout, this, true);
        previousMonthButton = (ImageView) view.findViewById(R.id.leftButton);
        nextMonthButton = (ImageView) view.findViewById(R.id.rightButton);

        previousMonthButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMonthIndex--;
                currentCalendar = Calendar.getInstance(Locale.getDefault());
                currentCalendar.add(Calendar.MONTH, currentMonthIndex);
                refreshCalendar(currentCalendar);
                onMonthChanged(DateUtils.getYear(currentCalendar.getTime()), DateUtils.getMonth(currentCalendar.getTime()));
            }
        });

        nextMonthButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMonthIndex++;
                currentCalendar = Calendar.getInstance(Locale.getDefault());
                currentCalendar.add(Calendar.MONTH, currentMonthIndex);
                refreshCalendar(currentCalendar);
                onMonthChanged(DateUtils.getYear(currentCalendar.getTime()), DateUtils.getMonth(currentCalendar.getTime()));
            }
        });

        // Initialize calendar for current month
        Locale locale = mContext.getResources().getConfiguration().locale;
        Calendar currentCalendar = Calendar.getInstance(locale);

        setFirstDayOfWeek(Calendar.SUNDAY);
        refreshCalendar(currentCalendar);
    }

    private void onMonthChanged(int currentYear, int currentMonth) {
        if (mStartDate != null && mEndDate != null) {
            int startYear = DateUtils.getYear(mStartDate);
            int endYear = DateUtils.getYear(mEndDate);
            int startMonth = DateUtils.getMonth(mStartDate);
            int endMonth = DateUtils.getMonth(mEndDate);
            if (startYear == endYear) {
                if (currentMonth < endMonth && currentMonth > startMonth) {
                    selectDaysInBetweenInAMonth();
                } else if (currentMonth == endMonth && currentMonth != startMonth) {
                    setupSelectionInEndMonth();
                } else if (currentMonth == startMonth && currentMonth != endMonth) {
                    setupSelectionInStartMonth();
                } else if (currentMonth == startMonth && currentMonth == endMonth) {
                    setupStartAndEndInSameMonth();
                }
            } else {
                //startYear != endYear
                if (currentYear == endYear) {
                    if (currentMonth == endMonth) {
                        setupSelectionInEndMonth();
                    } else if (currentMonth < endMonth) {
                        selectDaysInBetweenInAMonth();
                    }
                } else if (currentYear < endYear && currentYear > startYear) {
                    selectDaysInBetweenInAMonth();
                } else if (currentYear == startYear) {
                    if (currentMonth > startMonth) {
                        selectDaysInBetweenInAMonth();
                    } else if (currentMonth == startMonth) {
                        setupSelectionInStartMonth();
                    }
                }
            }
        }
    }

    private void selectDaysInBetweenInAMonth() {
        int index = 0;
        while (++index <= 35) {
            TextView textView = (TextView) view.findViewWithTag(DAY_OF_MONTH_TEXT + index);
            textView.setBackgroundColor(daysInBetweenBackgroundColor);
            textView.setTextColor(currentMonthDaysTextColor);
        }
    }

    /**
     * Set up selection in the start month when start date and end date are in different months
     */
    private void setupSelectionInStartMonth() {
        int index = Integer.parseInt(mStartTagId);
        TextView startTextView = (TextView) view.findViewWithTag(DAY_OF_MONTH_TEXT + index);
        startTextView.setBackgroundColor(selectedDayBackground);
        startTextView.setTextColor(selectedDayTextColor);
        while (++index <= 35) {
            TextView textView = (TextView) view.findViewWithTag(DAY_OF_MONTH_TEXT + index);
            textView.setBackgroundColor(daysInBetweenBackgroundColor);
            textView.setTextColor(currentMonthDaysTextColor);
        }
    }


    /**
     * Display calendar title with next previous month button
     */
    private void initializeTitleLayout() {
//        View titleLayout = view.findViewById(R.id.titleLayout);
//        titleLayout.setBackgroundColor(calendarTitleBackgroundColor);

        String dateText = new DateFormatSymbols(locale).getShortMonths()[currentCalendar.get(Calendar.MONTH)].toString();
        dateText = dateText.substring(0, 1).toUpperCase() + dateText.subSequence(1, dateText.length());

        TextView dateTitle = (TextView) view.findViewById(R.id.dateTitle);
        dateTitle.setTextColor(calendarTitleTextColor);
        dateTitle.setText(dateText + " " + currentCalendar.get(Calendar.YEAR));
        dateTitle.setTextColor(calendarTitleTextColor);
    }

    /**
     * Initialize the calendar week layout, considers start day
     */
    @SuppressLint("DefaultLocale")
    private void initializeWeekLayout() {
        TextView dayOfWeek;
        String dayOfTheWeekString;

        //Setting background color white
//        View titleLayout = view.findViewById(R.id.weekLayout);
//        titleLayout.setBackgroundColor(weekTitleBackgroundColor);

        final String[] weekDaysArray = new DateFormatSymbols(locale).getShortWeekdays();
        for (int i = 1; i < weekDaysArray.length; i++) {
            dayOfTheWeekString = weekDaysArray[i];
            dayOfTheWeekString = dayOfTheWeekString.substring(0, 1).toUpperCase();
            dayOfWeek = (TextView) view.findViewWithTag(DAY_OF_WEEK + getWeekIndex(i, currentCalendar));
            dayOfWeek.setText(dayOfTheWeekString);
            dayOfWeek.setTextColor(dayOfWeekTitleTextColor);  //set week titles
        }
    }

    private void setDaysInCalendar() {
        Calendar calendar = Calendar.getInstance(locale);
        calendar.setTime(currentCalendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.setFirstDayOfWeek(getFirstDayOfWeek());
        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);

        // Calculate dayOfMonthIndex
        int dayOfMonthIndex = getWeekIndex(firstDayOfMonth, calendar);
        int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        final Calendar startCalendar = (Calendar) calendar.clone();
        //Add required number of days
        startCalendar.add(Calendar.DATE, -(dayOfMonthIndex - 1));
        int monthEndIndex = 35 - (actualMaximum + dayOfMonthIndex - 1);

        DayView dayView;
        ViewGroup dayOfMonthContainer;
        //todo set MONTH text
        for (int i = 1; i < 36; i++) {
            dayOfMonthContainer = (ViewGroup) view.findViewWithTag(DAY_OF_MONTH_CONTAINER + i);
            dayView = (DayView) view.findViewWithTag(DAY_OF_MONTH_TEXT + i);
            if (dayView == null)
                continue;

            //Apply the default styles
            dayOfMonthContainer.setOnClickListener(null);
            dayView.bind(startCalendar.getTime(), getDecorators());
            dayView.setVisibility(View.VISIBLE);

            if (isSameMonth(calendar, startCalendar)) {
                dayOfMonthContainer.setOnClickListener(onDayOfMonthClickListener);
                dayView.setBackgroundColor(calendarBackgroundColor);
                dayView.setTextColor(currentMonthDaysTextColor);
            } else {
                dayView.setBackgroundColor(disabledDayBackgroundColor);
                dayView.setTextColor(disabledDayTextColor);

                if (!isOverflowDateVisible())
                    dayView.setVisibility(View.GONE);
                else if (i >= 36 && ((float) monthEndIndex / 7.0f) >= 1) {
                    dayView.setVisibility(View.GONE);
                }
            }
            dayView.decorate();

            startCalendar.add(Calendar.DATE, 1);
            dayOfMonthIndex++;
        }
    }


    public boolean isSameMonth(Calendar c1, Calendar c2) {
        if (c1 == null || c2 == null)
            return false;
        return (c1.get(Calendar.ERA) == c2.get(Calendar.ERA)
                && c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH));
    }


    private DayView getDayOfMonthText(Calendar currentCalendar) {
        return (DayView) getView(DAY_OF_MONTH_TEXT, currentCalendar);
    }

    private int getDayIndexByDate(Calendar currentCalendar) {
        int monthOffset = getMonthOffset(currentCalendar);
        int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
        int index = currentDay + monthOffset;
        return index;
    }

    private int getMonthOffset(Calendar currentCalendar) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(getFirstDayOfWeek());
        calendar.setTime(currentCalendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayWeekPosition = calendar.getFirstDayOfWeek();
        int dayPosition = calendar.get(Calendar.DAY_OF_WEEK);

        if (firstDayWeekPosition == 1) {
            return dayPosition - 1;
        } else {
            if (dayPosition == 1) {
                return 6;
            } else {
                return dayPosition - 2;
            }
        }
    }

    private int getWeekIndex(int weekIndex, Calendar currentCalendar) {
        int firstDayWeekPosition = currentCalendar.getFirstDayOfWeek();
        if (firstDayWeekPosition == 1) {
            return weekIndex;
        } else {

            if (weekIndex == 1) {
                return 7;
            } else {
                return weekIndex - 1;
            }
        }
    }

    private View getView(String key, Calendar currentCalendar) {
        int index = getDayIndexByDate(currentCalendar);
        View childView = view.findViewWithTag(key + index);
        return childView;
    }

    private Calendar getTodaysCalendar() {
        Calendar currentCalendar = Calendar.getInstance(mContext.getResources().getConfiguration().locale);
        currentCalendar.setFirstDayOfWeek(getFirstDayOfWeek());
        return currentCalendar;
    }

    @SuppressLint("DefaultLocale")
    public void refreshCalendar(Calendar currentCalendar) {
        this.currentCalendar = currentCalendar;
        this.currentCalendar.setFirstDayOfWeek(getFirstDayOfWeek());
        locale = mContext.getResources().getConfiguration().locale;

        // Set date title
        initializeTitleLayout();

        // Set weeks days titles
        initializeWeekLayout();

        // Initialize and set days in calendar
        setDaysInCalendar();
    }

    private int getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    public void setFirstDayOfWeek(int firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
    }

    public void markDayAsSelectedDay(Date currentDate) {
        final Calendar currentCalendar = getTodaysCalendar();
        currentCalendar.setFirstDayOfWeek(getFirstDayOfWeek());
        currentCalendar.setTime(currentDate);

        // Mark current day as selected
        DayView view = getDayOfMonthText(currentCalendar);
        view.setBackgroundColor(selectedDayBackground);
        view.setTextColor(selectedDayTextColor);
    }

    protected void setupSelectedDates(Date startDate, Date endDate) {
        mStartDate = startDate;
        mEndDate = endDate;
        final Calendar currentCalendar = getTodaysCalendar();
        currentCalendar.setFirstDayOfWeek(getFirstDayOfWeek());
        currentCalendar.setTime(startDate);
        mStartTagId = String.valueOf(getDayIndexByDate(currentCalendar));
        currentCalendar.setTime(endDate);
        mEndTagId = String.valueOf(getDayIndexByDate(currentCalendar));

        if (DateUtils.getMonth(mStartDate) == DateUtils.getMonth(mEndDate)) {
            scrollToStartMonth(mStartDate);
            setupStartAndEndInSameMonth();
        } else {
            scrollToStartMonth();
        }
    }

    public void setCalendarListener(CalendarListener calendarListener) {
        this.calendarListener = calendarListener;
    }

    private OnClickListener onDayOfMonthClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            // Extract day selected
            ViewGroup dayOfMonthContainer = (ViewGroup) view;
            String tagId = (String) dayOfMonthContainer.getTag();
            tagId = tagId.substring(DAY_OF_MONTH_CONTAINER.length(), tagId.length());
            final TextView dayOfMonthText = (TextView) view.findViewWithTag(DAY_OF_MONTH_TEXT + tagId);

            // Fire event
            final Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(getFirstDayOfWeek());
            calendar.setTime(currentCalendar.getTime());
            calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dayOfMonthText.getText().toString()));

            if (isStart) {
                if (!TextUtils.isEmpty(mStartTagId) && !TextUtils.isEmpty(mEndTagId)) {
                    if ((mStartDate != null) && (mEndDate != null)) {
                        if (DateUtils.getMonth(mStartDate) == DateUtils.getMonth(mEndDate)) {
                            //preStartDate and EndDate are in the same month
                            if (DateUtils.getMonth(calendar.getTime()) == DateUtils.getMonth(mStartDate)) {
                                //newStartDate, preStartDate and EndDate are in the same month
                                int tagIdNum = Integer.parseInt(tagId);
                                int preStartTagId = Integer.parseInt(mStartTagId);
                                int tagEndNum = Integer.parseInt(mEndTagId);

                                if (tagIdNum > preStartTagId && tagIdNum < tagEndNum) {
                                    //clear first
                                    clearDays(mStartTagId, String.valueOf(tagIdNum - 1));
                                    //then set up
                                    mStartTagId = tagId;
                                    mStartDate = calendar.getTime();
                                } else if (tagIdNum > tagEndNum) {
                                    //first: clear
                                    clearDays(mStartTagId, String.valueOf(tagEndNum - 1));
                                    //second: reset date
                                    mStartDate = mEndDate;
                                    mEndDate = calendar.getTime();
                                    //last: exchange
                                    mStartTagId = mEndTagId;
                                    mEndTagId = tagId;

                                } else if (tagIdNum < preStartTagId) {
                                    mStartTagId = tagId;
                                    mStartDate = calendar.getTime();
                                }
                                setupStartAndEndInSameMonth();
                            } else {
                                //newStartDate is in a different month
                                if (DateUtils.getMonth(calendar.getTime()) < DateUtils.getMonth(mStartDate)) {
                                    mStartDate = calendar.getTime();
                                    setupSelectionInStartMonth();
                                } else {
                                    mStartDate = mEndDate;
                                    mEndDate = calendar.getTime();
                                    mStartTagId = mEndTagId;
                                    mEndTagId = tagId;
                                    setupSelectionInEndMonth();
//                                    Timber.e("You cannot select start date after end date!");
                                }
                            }
                        } else {
                            //preStartDate and EndDate are in different months
                            Date preStartDate = mStartDate;
                            mStartDate = calendar.getTime();
                            int preStartMonth = DateUtils.getMonth(preStartDate);
                            int newStartMonth = DateUtils.getMonth(mStartDate);

                            if (preStartMonth == newStartMonth) {
                                String preStartTagId = mStartTagId;
                                mStartTagId = tagId;
                                int newStartId = Integer.parseInt(tagId);
                                int preStartId = Integer.parseInt(preStartTagId);
                                if (newStartId > preStartId) {
                                    markDayAsSelectedDay(mStartDate);
                                    clearDays(preStartTagId, String.valueOf(newStartId - 1));
                                } else {
                                    setupSelectionInStartMonth();
                                }
                            } else if (newStartMonth < preStartMonth) {
                                setupSelectionInStartMonth();
                            } else if (newStartMonth > preStartMonth) {
                                int endDateMonth = DateUtils.getMonth(mEndDate);
                                if (newStartMonth < endDateMonth) {
                                    //newStartMonth is between preStartMonth and endDateMonth
                                    markDayAsSelectedDay(mStartDate);
                                    clearDays(START_CELL_TAG_ID, String.valueOf(Integer.parseInt(tagId) - 1));
                                } else if (newStartMonth > endDateMonth) {
                                    mStartDate = mEndDate;
                                    mEndDate = calendar.getTime();
                                    mStartTagId = mEndTagId;
                                    mEndTagId = tagId;
                                    setupSelectionInEndMonth();
                                } else if (newStartMonth == endDateMonth) {
                                    int tagIdNum = Integer.parseInt(tagId);
                                    int endTagIdNum = Integer.parseInt(mEndTagId);
                                    if (tagIdNum <= endTagIdNum) {
                                        mStartTagId = tagId;
                                        mStartDate = calendar.getTime();
                                        markDayAsSelectedDay(mStartDate);
                                        clearDays(START_CELL_TAG_ID, String.valueOf(tagIdNum - 1));
                                    } else {
                                        clearDays(START_CELL_TAG_ID, String.valueOf(endTagIdNum - 1));
                                        mStartTagId = mEndTagId;
                                        mEndTagId = tagId;
                                        mStartDate = mEndDate;
                                        mEndDate = calendar.getTime();
                                        setupStartAndEndInSameMonth();
                                    }
                                }
                            }
                        }
                    }
                } else {
                    //first time start time selection
                    mStartTagId = tagId;
                    mStartDate = calendar.getTime();
                    markDayAsSelectedDay(mStartDate);
                }
            } else {
                //end date selection
                int tagIdNum = Integer.parseInt(tagId);
                Date preEndDate = mEndDate;
                if (DateUtils.getMonth(calendar.getTime()) == DateUtils.getMonth(mStartDate)) {
                    if (tagIdNum > Integer.parseInt(mStartTagId)) {
                        if (null != preEndDate) {
                            if (DateUtils.getMonth(calendar.getTime()) == DateUtils.getMonth(preEndDate)) {
                                if (!TextUtils.isEmpty(mEndTagId) && (tagIdNum < Integer.parseInt(mEndTagId))) {
                                    String clearStart = String.valueOf(tagIdNum + 1);
                                    clearDays(clearStart, mEndTagId);
                                }
                            } else {
                                clearDays(getClearStartString(tagId), END_CELL_TAG_ID);
                            }
                        }
                        //after clear days, set the new EndTagId
                        mEndTagId = tagId;
                        mEndDate = calendar.getTime();
                    } else {
                        //first: clear
                        if (!TextUtils.isEmpty(mEndTagId)) {
                            //preEndDate is in the same month: clear till mEndTagId; preEndDate is in future month: clear till the end, so clear till the end
                            clearDays(getClearStartString(mStartTagId), END_CELL_TAG_ID);
                        }
                        //second: reset date
                        mEndDate = mStartDate;
                        mStartDate = calendar.getTime();
                        //last: exchange
                        mEndTagId = mStartTagId;
                        mStartTagId = tagId;
                    }
                    setupStartAndEndInSameMonth();
                } else {
                    //start and end are in different months
                    if (mStartDate.before(calendar.getTime())) {
                        if (null == mEndDate) {
                            //first time selecting end date
                            mEndTagId = tagId;
                            mEndDate = calendar.getTime();
                            markDayAsSelectedDay(mEndDate);
                            setupSelectionInEndMonth();
                        } else {
                            //not first time
                            String preEndTagId = mEndTagId;
                            mEndTagId = tagId;
                            mEndDate = calendar.getTime();
                            int preEnd = Integer.parseInt(preEndTagId);
                            int newEnd = Integer.parseInt(mEndTagId);
                            if (DateUtils.getMonth(preEndDate) == DateUtils.getMonth(mEndDate)) {
                                if (newEnd < preEnd) {
                                    markDayAsSelectedDay(mEndDate);
                                    clearDays(getClearStartString(mEndTagId), preEndTagId);
                                } else {
                                    setupSelectionInEndMonth();
                                }
                            } else {
                                if (mEndDate.before(preEndDate)) {
                                    markDayAsSelectedDay(mEndDate);
                                    clearDays(getClearStartString(mEndTagId), END_CELL_TAG_ID);
                                } else {
                                    setupSelectionInEndMonth();
                                }
                            }
                        }
                    } else if ((calendar.getTime()).before(mStartDate)) {
                        mEndDate = mStartDate;
                        mStartDate = calendar.getTime();
                        mEndTagId = mStartTagId;
                        mStartTagId = tagId;
                        markDayAsSelectedDay(mStartDate);
                        setupSelectionInStartMonth();
                    }
                }

            }
            if (calendarListener != null) {
                calendarListener.onStartDateSelected(mStartDate);
                calendarListener.onEndDateSelected(mEndDate);
            }
        }
    };

    private String getClearStartString(String tagId) {
        return String.valueOf(Integer.parseInt(tagId) + 1);
    }

    /**
     * Set up selection in the end month when start date and end date are in different months
     */
    private void setupSelectionInEndMonth() {
        int index = Integer.parseInt(mEndTagId);
        TextView endTextView = (TextView) view.findViewWithTag(DAY_OF_MONTH_TEXT + index);
        endTextView.setBackgroundColor(selectedDayBackground);
        endTextView.setTextColor(selectedDayTextColor);
        while (--index > 0) {
            TextView textView = (TextView) view.findViewWithTag(DAY_OF_MONTH_TEXT + index);
            textView.setBackgroundColor(daysInBetweenBackgroundColor);
            textView.setTextColor(currentMonthDaysTextColor);
        }
    }

    private void clearDays(String clearStart, String endTagId) {
        int start = Integer.parseInt(clearStart);
        int end = Integer.parseInt(endTagId);
        for (int i = start; i <= end; i++) {
            TextView textView = (TextView) view.findViewWithTag(DAY_OF_MONTH_TEXT + i);
            textView.setBackgroundColor(calendarBackgroundColor);
            textView.setTextColor(currentMonthDaysTextColor);
        }
    }

    /**
     * Set up selection when start date and end date are in the same month
     */
    private void setupStartAndEndInSameMonth() {
        TextView start = (TextView) view.findViewWithTag(DAY_OF_MONTH_TEXT + mStartTagId);
        start.setBackgroundColor(selectedDayBackground);
        start.setTextColor(selectedDayTextColor);
        int index = Integer.parseInt(mStartTagId);
        int end = Integer.parseInt(mEndTagId);
        while (++index < end) {
            TextView textView = (TextView) view.findViewWithTag(DAY_OF_MONTH_TEXT + index);
            textView.setBackgroundColor(daysInBetweenBackgroundColor);
            if (textView.getCurrentTextColor() == selectedDayTextColor) {
                textView.setTextColor(currentMonthDaysTextColor);
            }
        }
        TextView endView = (TextView) view.findViewWithTag(DAY_OF_MONTH_TEXT + mEndTagId);
        endView.setBackgroundColor(selectedDayBackground);
        endView.setTextColor(selectedDayTextColor);
    }

    public List<DayDecorator> getDecorators() {
        return decorators;
    }

    public boolean isOverflowDateVisible() {
        return isOverflowDateVisible;
    }

    @Override
    public void reset() {
        removeSelection();
    }

    /**
     * Remove start date and end date selection
     */
    private void removeSelection() {
        setDaysInCalendar();
        mStartDate = null;
        mEndDate = null;
        mStartTagId = null;
        mEndTagId = null;
    }

    /**
     * Scroll calendar to the start month when switch to start tab if start date and end date are not in the same month
     */
    public void scrollToStartMonth() {
        if (mStartDate != null && mEndDate != null) {
            int startMonth = DateUtils.getMonth(mStartDate);
            int endMonth = DateUtils.getMonth(mEndDate);
            int startYear = DateUtils.getYear(mStartDate);
            int currentMonth = DateUtils.getMonth(currentCalendar.getTime());
            int currentYear = DateUtils.getYear(currentCalendar.getTime());
            if (startMonth != endMonth) {
                if (startYear == currentYear) {
                    if (DateUtils.getMonth(mStartDate) != currentMonth) {
                        currentMonthIndex -= currentMonth - startMonth;
                        currentCalendar = Calendar.getInstance(Locale.getDefault());
                        currentCalendar.add(Calendar.MONTH, currentMonthIndex);
                        refreshCalendar(currentCalendar);
                    }
                    setupSelectionInStartMonth();
                } else {
                    currentMonthIndex -= currentMonth - startMonth + 12;
                    currentMonthIndex %= 12;
                    currentCalendar = Calendar.getInstance(Locale.getDefault());
                    currentCalendar.setTime(mStartDate);
                    refreshCalendar(currentCalendar);
                    setupSelectionInStartMonth();
                }
            }
        }
    }

    /**
     * Scroll calendar to start month if start date and end date are in the same month
     */
    public void scrollToStartMonth(Date startDate) {
        int startMonth = DateUtils.getMonth(startDate);
        int currentMonth = DateUtils.getMonth(currentCalendar.getTime());
        if (DateUtils.getMonth(startDate) != currentMonth) {
            currentMonthIndex -= currentMonth - startMonth;
            currentCalendar = Calendar.getInstance(Locale.getDefault());
            currentCalendar.add(Calendar.MONTH, currentMonthIndex);
            refreshCalendar(currentCalendar);
        }
    }

}
