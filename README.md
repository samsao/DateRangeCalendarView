# DateRangeCalendarView
DateRangeCalendarView provides an easy way to select a start date and an end date on a Calendar. It is inspired by Custom-Calendar-View https://github.com/npanigrahy/Custom-Calendar-View

How to use DateRangeCalendarView Library
This library provides a simple CalendarTabsView class which includes two tabs and a CustomCalendarView class.
To start using CalendarTabsView, you can simply include it into the xml file as follows:

<com.samsaodev.daterangeselector.calendar.CalendarTabsView
    android:id="@+id/activity_main_calendar_tabs"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
    
Then, you may need to initialize annd set up the tabs using the following methods.

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

