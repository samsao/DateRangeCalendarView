//package com.samsaodev.daterangecalendarview;
//
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.samsaodev.daterangecalendarview.utils.DateUtils;
//import com.samsaodev.daterangeselector.calendar.CalendarListener;
//import com.samsaodev.daterangeselector.calendar.CustomCalendarView;
//
//import java.util.Date;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//public class CalendarActivity extends AppCompatActivity implements CalendarListener {
//
//
//    @Bind(R.id.calendar_activity_start_layout)
//    protected LinearLayout mStartLayout;
//    @Bind(R.id.calendar_activity_start_title)
//    protected TextView mStartLabel;
//    @Bind(R.id.calendar_activity_start_date)
//    protected TextView mStartDateTab;
//    @Bind(R.id.calendar_activity_end_layout)
//    protected LinearLayout mEndLayout;
//    @Bind(R.id.calendar_activity_end_title)
//    protected TextView mEndLabel;
//    @Bind(R.id.calendar_activity_end_date)
//    protected TextView mEndDateTab;
//    @Bind(R.id.calendar_view)
//    protected CustomCalendarView mCustomCalendarView;
//
//    private Drawable mSelectedTabBg;
//    private int mUnSelectedTabBg;
//    private String mDefaultDate = "----";
//    private Date mStartDate;
//    private Date mEndDate;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_calendar);
//        ButterKnife.bind(this);
//        setup();
//        setupToolbar();
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
////            mStartDate = (Date) bundle.get(MyTripsActivity.PRE_START_DATE);
////            mEndDate = (Date) bundle.get(MyTripsActivity.PRE_END_DATE);
////            mStartDateText.setText(DateUtils.formatDateShort(mStartDate));
////            mEndDateText.setText(DateUtils.formatDateShort(mEndDate));
//            mStartDateTab.setTextColor(ContextCompat.getColor(this, R.color.theme_green_grass));
//            mEndDateTab.setTextColor(ContextCompat.getColor(this, R.color.theme_green_grass));
//            mCustomCalendarView.setupSelectedDates(mStartDate, mEndDate);
//        }
//    }
//
//    private void setupToolbar() {
//
////        fixToolbarMargin(mToolbar);
////        enableUpNavigation(mToolbar);
////        mToolbar.inflateMenu(R.menu.menu_reset);
////        mToolbar.setOnMenuItemClickListener(
////                new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
////                    @Override
////                    public boolean onMenuItemClick(MenuItem item) {
////                        if (item.getItemId() == R.id.menu_reset_id) {
////                            mStartDateText.setText(mDefaultDate);
////                            mEndDateText.setText(mDefaultDate);
////                            mStartDateText.setTextColor(ContextCompat.getColor(CalendarActivity.this, R.color.theme_gray_text_lighter));
////                            mEndDateText.setTextColor(ContextCompat.getColor(CalendarActivity.this, R.color.theme_gray_text_lighter));
////                            onClickStartTab();
////                            mCustomCalendarView.reset();
////                            return true;
////                        }
////                        return false;
////                    }
////                }
////        );
//    }
//
//    private void setup() {
//        mCustomCalendarView.setCalendarListener(this);
//        mCustomCalendarView.setStart(true);
//
//        mSelectedTabBg = ContextCompat.getDrawable(this, R.drawable.bg_calendar_selected_tab);
//        mUnSelectedTabBg = ContextCompat.getColor(this, R.color.theme_gray_light);
//        mStartLayout.setBackground(mSelectedTabBg);
//        mEndLayout.setBackgroundColor(mUnSelectedTabBg);
//        mStartDateTab.setText(mDefaultDate);
//        mEndDateTab.setText(mDefaultDate);
//        mStartDateTab.setTextColor(ContextCompat.getColor(this, R.color.theme_gray_text_lighter));
//        mEndDateTab.setTextColor(ContextCompat.getColor(this, R.color.theme_gray_text_lighter));
//    }
//
//    @OnClick(R.id.calendar_activity_start_layout)
//    public void onClickStartLayout() {
//        setupStartTabSelected();
//        if (mEndDate != null) {
//            mCustomCalendarView.scrollToStartMonth();
//        }
//    }
//
//    @OnClick(R.id.calendar_activity_end_layout)
//    public void onClickEndLayout() {
//        setupEndTabSelected();
//    }
//
//    @OnClick(R.id.calendar_activity_apply_filter)
//    public void onClickApplyFilter() {
//        String toast = mStartDate.toString() + " ~ " + mEndDate.toString();
//        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
//    }
//
//    private void setupStartDateText(String startDate) {
//        mStartDateTab.setText(startDate);
//        mStartDateTab.setTextColor(ContextCompat.getColor(this, R.color.theme_green_grass));
//    }
//
//    private void setupEndDateText(String endDate) {
//        mEndDateTab.setText(endDate);
//        mEndDateTab.setTextColor(ContextCompat.getColor(this, R.color.theme_green_grass));
//    }
//
//
//    private void setupStartTabSelected() {
//        mCustomCalendarView.setStart(true);
//        mStartLayout.setBackground(mSelectedTabBg);
//        mEndLayout.setBackgroundColor(mUnSelectedTabBg);
//    }
//
//    private void setupEndTabSelected() {
//        mCustomCalendarView.setStart(false);
//        mEndLayout.setBackground(mSelectedTabBg);
//        mStartLayout.setBackgroundColor(mUnSelectedTabBg);
//    }
//
//    @Override
//    public void onStartDateSelected(Date date) {
//        mStartDate = date;
//        setupStartDateText(DateUtils.formatDateShort(date));
//        setupEndTabSelected();
//    }
//
//
//    @Override
//    public void onEndDateSelected(Date date) {
//        mEndDate = date;
//        setupEndDateText(DateUtils.formatDateShort(date));
//    }
//
//    public interface MyTripsResetHandler {
//        void reset();
//    }
//
//}
