package com.example.raymond.counsellingapp;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import com.example.raymond.counsellingapp.Model.ScheduleInfo;
import com.example.raymond.counsellingapp.Model.StudentSchedule;


public class ScheduleTableLayout extends LinearLayout {
    private static final int TABLE_COL = 9;
    private static final int TABLE_ROW = 6;
    private boolean isInitialized = false;
    private boolean isDisplayABCD = false;
    private boolean isDisplaySat = false;
    private boolean isDisplaySun = false;
    private boolean isDisplayNoTime = false;
    private int ROW_HEIGHT;
    private View.OnClickListener onClickListener = null;
    private TableInitializeListener initializeListener = null;
    private LinearLayout scheduleContainer;
    private StudentSchedule studentSchedule = new StudentSchedule();
    private OnTouchListener onTouchListener;
    static int rowPos, colPos;

    public ScheduleTableLayout(Context context) {
        super(context);
        inflate(context, R.layout.schedule_table_layout, this);
    }

    public ScheduleTableLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.schedule_table_layout, this);
    }

    private void splitTime(String timeString) {

        String[] temp = timeString.split(" ");
        for (String t : temp) {
            switch (t) {
                case "Mon":
                    rowPos = 1;
                    break;
                case "Tue":
                    rowPos = 2;
                    break;
                case "Wed":
                    rowPos = 3;
                    break;
                case "Thu":
                    rowPos = 4;
                    break;
                case "Fri":
                    rowPos = 5;
                    break;
                case "10":
                    colPos = 1;
                    break;
                case "12":
                    colPos = 2;
                    break;
                case "14":
                    colPos = 3;
                    break;
                case "16":
                    colPos = 4;
                    break;
                case "18":
                    colPos = 5;
                    break;
                default:

                    break;
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!isInitialized) {
            ROW_HEIGHT = Math.round((bottom - top) / 9.5f);
            initScheduleTable();
            isInitialized = true;
            if (initializeListener != null) {
                initializeListener.onTableInitialized(this);
            }
            showSchedule(studentSchedule);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e(getClass().getSimpleName(), "onFinishInflate");
        scheduleContainer = findViewById(R.id.schedule_container);
    }

    public void setOnScheduleClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setTableInitializeListener(
            TableInitializeListener initializeListener) {
        this.initializeListener = initializeListener;
    }

    private void initScheduleTable() {
        scheduleContainer.removeAllViews();
        LayoutParams title_row_params = new LayoutParams(
                LayoutParams.MATCH_PARENT, ROW_HEIGHT / 2);
        LayoutParams row_params = new LayoutParams(LayoutParams.MATCH_PARENT,
                ROW_HEIGHT);
        LayoutParams cell_params = new LayoutParams(0,
                LayoutParams.MATCH_PARENT, 1f);
        LayoutParams title_col_params = new LayoutParams(0,
                LayoutParams.MATCH_PARENT, 0.5f);
        for (int i = 0; i < TABLE_ROW; i++) {
            LinearLayout tableRow = new LinearLayout(getContext());
            tableRow.setOrientation(LinearLayout.HORIZONTAL);
            tableRow.setLayoutParams(i == 0 ? title_row_params : row_params);
            tableRow.setGravity(Gravity.CENTER);
            tableRow.setBackgroundResource(i % 2 != 0 ? R.color.cloud
                    : R.color.white);

            String[] Day = {"Mon", "Tue", "Wed", "Thu", "Fri"};

            for (int j = 0; j < TABLE_COL; j++) {
                ScheduleBlock tableCell = new ScheduleBlock(getContext());
                if (j == 0 && i > 0) {
                    tableCell.setText(Day[i - 1]);
                }
                tableCell.setId(j != TABLE_COL - 1 ? i : 14);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tableCell.setZ(5.0f);
                }
                cell_params.setMargins(5, 5, 5, 5);
//                    tableCell.setBackgroundResource(R.drawable.shape);
//                    tableCell.setElevation(20.0f);
                tableCell.setLayoutParams(j == 0 ? title_col_params
                        : cell_params);
                tableRow.addView(tableCell);
            }
            Log.e(getClass().getSimpleName(), "addRowView");
            scheduleContainer.addView(tableRow);
        }
        LinearLayout titleRow = (LinearLayout) scheduleContainer.getChildAt(0);
        ScheduleBlock text = (ScheduleBlock) titleRow.getChildAt(1);
        text.setText("10am");
        text = (ScheduleBlock) titleRow.getChildAt(2);
        text.setText("12pm");
        text = (ScheduleBlock) titleRow.getChildAt(3);
        text.setText("2pm");
        text = (ScheduleBlock) titleRow.getChildAt(4);
        text.setText("4pm");
        text = (ScheduleBlock) titleRow.getChildAt(5);
        text.setText("6pm");


    }

    private void resetScheduleTable() {
        for (int i = 1; i < TABLE_ROW; i++) {
            for (int j = 1; j < TABLE_COL; j++) {
                LinearLayout tableRow = (LinearLayout) scheduleContainer.getChildAt(i);
                if (tableRow != null) {
                    ScheduleBlock tableCell = (ScheduleBlock) tableRow.getChildAt(j);
                    tableCell.resetBlock();
                }
            }
        }
        isDisplayABCD = false;
        isDisplaySat = false;
        isDisplaySun = false;
        isDisplayNoTime = false;
        requestLayout();
    }

    private void controlColRowShow() {
        for (int i = 0; i < TABLE_ROW; i++) {
            LinearLayout tableRow = (LinearLayout) scheduleContainer
                    .getChildAt(i);
            if (tableRow != null) {
                ScheduleBlock satText = (ScheduleBlock) tableRow.getChildAt(6);
                satText.setVisibility(isDisplaySat ? View.VISIBLE : View.GONE);
                ScheduleBlock sunText = (ScheduleBlock) tableRow.getChildAt(7);
                sunText.setVisibility(isDisplaySun ? View.VISIBLE : View.GONE);
                ScheduleBlock noTimeText = (ScheduleBlock) tableRow.getChildAt(8);
                noTimeText.setVisibility(isDisplayNoTime ? View.INVISIBLE
                        : View.GONE);
                if (i > 9) {
                    tableRow.setVisibility(isDisplayABCD ? View.VISIBLE
                            : View.GONE);
                }
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void showSchedule(StudentSchedule studentSchedule) {
        resetScheduleTable();
        int color_index = 0;
        int[] color_array = getColorArray(studentSchedule.getScheduleList().size());
        int count = 0;
        for (ScheduleInfo item : studentSchedule.getScheduleList()) {
            boolean isHaveTime = false;
            for (int i = 0; i < item.getTotalTimes(); i++) {
                String time = item.getTimes()[i];
                splitTime(time);

                setTableCell(rowPos, colPos, color_array[color_index], item);
                isHaveTime = true;

            }
            if (!isHaveTime) {
                count++;
                isDisplayNoTime = true;
                setTableCell(count, 8, color_array[color_index], item);
            }
            color_index++;
        }
        controlColRowShow();
    }

    public void setStudentSchedule(StudentSchedule studentSchedule) {
        this.studentSchedule = studentSchedule;
    }

    private int[] getColorArray(int color_count) {
        int[] colorArray = new int[color_count];
        int[] ints = getContext().getResources().getIntArray(R.array.schedule_table);
        List<Integer> defaultColor = new ArrayList<>();
        for (int i : ints) {
            defaultColor.add(i);
        }
        for (int i = 0; i < color_count; i++) {
            int random = (int) (Math.random() * defaultColor.size());
            colorArray[i] = defaultColor.remove(random);
        }
        return colorArray;
    }

    private void setTableCell(int row, int col, int color, ScheduleInfo schedule) {
        Log.e(getClass().getSimpleName(), "setTableCell");
        LinearLayout tableRow = (LinearLayout) scheduleContainer.getChildAt(row);
        if (tableRow != null) {
            ScheduleBlock table_cell = (ScheduleBlock) tableRow.getChildAt(col);
            table_cell.setVisibility(View.INVISIBLE);
            table_cell.setText(schedule.getName().trim());
            table_cell.setTag(schedule);
            table_cell.setBackgroundColor(color);
            table_cell.setOnClickListener(onClickListener);
            setAnimation(table_cell);
        }
    }

    private void setAnimation(final ScheduleBlock textview) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displaymetrics);
        }
        final TranslateAnimation translateAnimation = new TranslateAnimation(
                displaymetrics.widthPixels, 0, 0, 0);
        translateAnimation.setDuration(500);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                textview.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // nothing to do
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // nothing to do
            }

        });
        translateAnimation.setInterpolator(new OvershootInterpolator());
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                textview.startAnimation(translateAnimation);
            }
        }, (long) ((Math.random() * 500) + 500));
    }

    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return onTouchListener != null && onTouchListener.onTouch(this, ev) || super.dispatchTouchEvent(ev);
    }

    public interface TableInitializeListener {
        void onTableInitialized(ScheduleTableLayout schedule_table);
    }
}
