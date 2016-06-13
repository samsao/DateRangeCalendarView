# DateRangeCalendarView


DateRangeCalendarView provides an easy way to select a start date and an end date on a Calendar. It is inspired by [Custom-Calendar-View](https://github.com/npanigrahy/Custom-Calendar-View).

## How to use DateRangeCalendarView

This library provides a simple CalendarTabsView class which includes two tab layouts and a CustomCalendarView class.
To start using CalendarTabsView, you can simply include it into the xml file as follows:
```java
<com.samsaodev.daterangeselector.calendar.CalendarTabsView
    android:id="@+id/activity_main_calendar_tabs"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
```
First, you may need to initialize annd set up the tabs using the following methods:

```java
//initialization
mCustomCalendarView = (CustomCalendarView) view.findViewById(R.id.calendar_view);
   
//set start date text selected text color
mCalendarTabsView.setStartDateTextSelectedColor(ContextCompat.getColor(this, R.color.theme_green_lime));
//set end date text selected text color
mCalendarTabsView.setEndDateTextSelectedColor(ContextCompat.getColor(this, R.color.theme_green_lime));
//set start date text unselected color
mCalendarTabsView.setStartDateUnselectedColor(ContextCompat.getColor(this, R.color.theme_gray_text_lighter));
//set end date text unselected color
mCalendarTabsView.setEndDateUnselectedColor(ContextCompat.getColor(this, R.color.theme_gray_text_lighter));
//set default date text string
mDefaultDate = "----";
mCalendarTabsView.setDefaultDateText(mDefaultDate);

//set start date tab selected background drawable
mCalendarTabsView.setStartTabSelectedBgDrawable(ContextCompat.getDrawable(this, R.drawable.bg_calendar_selected_tab));
//set end date tab selected background drawable
mCalendarTabsView.setEndTabSelectedBgDrawable(ContextCompat.getDrawable(this, R.drawable.bg_calendar_selected_tab));
//set start date tab unselected background color
mCalendarTabsView.setStartTabUnselectedBgColor(ContextCompat.getColor(this, R.color.theme_gray_light));
//set end date tab unselected background color
mCalendarTabsView.setEndTabUnselectedBgColor(ContextCompat.getColor(this, R.color.theme_gray_light));

//set start date label selected text color
mCalendarTabsView.setStartLabelSelectedColor(ContextCompat.getColor(this, android.R.color.black));
//set end date label selected text color
mCalendarTabsView.setEndLabelSelectedColor(ContextCompat.getColor(this, android.R.color.black));
//set start date label unselected text color
mCalendarTabsView.setStartLabelUnselectedColor(ContextCompat.getColor(this, R.color.theme_gray_text_lighter));
//set end date label unselected text color
mCalendarTabsView.setEndLabelUnselectedColor(ContextCompat.getColor(this, R.color.theme_gray_text_lighter));

//set month changing left arrow drawable
mCalendarTabsView.setPreviousMonthButtonDrawable(ContextCompat.getDrawable(this, R.drawable.ic_keyboard_arrow_left_green_36dp));
//set month changing right arrow drawable
mCalendarTabsView.setNextMonthButtonDrawable(ContextCompat.getDrawable(this, R.drawable.ic_keyboard_arrow_right_green_36dp));
```
Then, you might want to setup the CalendarView using some attributes:

```java
<com.samsaodev.daterangeselector.calendar.CalendarTabsView
    android:id="@+id/activity_main_calendar_tabs"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:calendarBackgroundColor="@android:color/black"
    app:calendarTitleTextColor="@android:color/black"
    app:dayOfWeekTextColor="@color/colorAccent"
    app:currentMonthDaysTextColor="@color/colorPrimaryDark"
    app:daysInBetweenBackgroundColor="@android:color/darker_gray"
    app:disabledDayBackgroundColor="@android:color/black"
    app:disabledDayTextColor="@color/colorPrimary"
    app:selectedDayBackgroundColor="@android:color/white"
    app:selectedDayTextColor="@color/colorAccent"/>
```

* calendarBackgroundColor is the background color for unselected days shown on the Calendar
* calendarTitleTextColor is the text color for the date title (Jun 2016)
* dayOfWeekTextColor is the text color for weekday title (S, M, T, W, T, F, S)
* currentMonthDaysTextColor is the text color for the days in the current month
* daysInBetweenBackgroundColor is the background color for the days in between start date and end date
* disabledDayBackgroundColor is the background color for disabled days (days not in the current month)
* disabledDayTextColor is the text color for disabled days (days not in the current month)
* selectedDayBackground is the background color for selected days (start date and end date)
* selectedDayTextColor is text color for selected days (start date and end date)

## Screenshots

* From Jun 6 to Jun 23

![Jun 6 - Jun 23](https://raw.githubusercontent.com/samsao/DateRangeCalendarView/master/screenshots/Jun6_23.png?token=AFhW-wib3BTIfqTEKG8pX4afx9HCVvHxks5XZFFTwA%3D%3D)

* From Jun 16 to Jul 24

![Jun 16 - Jul 24 start](https://github.com/samsao/DateRangeCalendarView/blob/master/screenshots/Jun16_24_start.png?raw=true)![Jun 16 - Jul 24 end](https://github.com/samsao/DateRangeCalendarView/blob/master/screenshots/Jun16_24_end.png?raw=true)

* From Jun 16 to Aug 17

![Jun 16 - Aug 17 start](https://github.com/samsao/DateRangeCalendarView/blob/master/screenshots/Jun16_Aug17_start.png?raw=true)![Jun 16 - Aug 17 middle](https://github.com/samsao/DateRangeCalendarView/blob/master/screenshots/Jun16_Aug17_middle.png?raw=true)![Jun 16 - Aug 17 end](https://github.com/samsao/DateRangeCalendarView/blob/master/screenshots/Jun16_Aug17_end.png?raw=true)

## Author

[Jingsi Lu](https://github.com/qcdyx)

## License

