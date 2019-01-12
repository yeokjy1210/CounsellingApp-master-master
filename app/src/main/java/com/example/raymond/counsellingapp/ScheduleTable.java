package com.example.raymond.counsellingapp;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.raymond.counsellingapp.Model.ScheduleInfo;
import com.example.raymond.counsellingapp.Model.StudentSchedule;

import java.util.ArrayList;
import java.util.Locale;

public class ScheduleTable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_table);
        initView();
    }
    private void initView() {
        ScheduleTableLayout scheduleTable = findViewById(R.id.scheduleTable);
        StudentSchedule StudentSchedule = new StudentSchedule();
        ArrayList<ScheduleInfo> ScheduleInfoList = new ArrayList<>();

        // Add schedule1 - sample1
        CustomScheduleInfo customScheduleInfo = new CustomScheduleInfo();
        customScheduleInfo.setName("Schedule 1");
        String[] schedule = {"Mon 18","Tue 10","Wed 12","Thu 14","Fri 16"};
        customScheduleInfo.setScheduleTime(schedule);
        customScheduleInfo.setLocation("A101");
        customScheduleInfo.setStudent("Raymond Chua");
        customScheduleInfo.setCounselor("John Johny");
        ScheduleInfoList.add(customScheduleInfo);


        // Set timetable
        StudentSchedule.setScheduleList(ScheduleInfoList);
        scheduleTable.setStudentSchedule(StudentSchedule);
        scheduleTable.setTableInitializeListener(new ScheduleTableLayout.TableInitializeListener() {
            @Override
            public void onTableInitialized(ScheduleTableLayout schedule_table) {
                Toast.makeText(ScheduleTable.this, "Finish initialized", Toast.LENGTH_SHORT).show();
            }
        });
        scheduleTable.setOnScheduleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomScheduleInfo item = (CustomScheduleInfo) view.getTag();
                showInfoDialog(view.getId(), item.getName(), item);
            }
        });
    }

    private void showInfoDialog(int id, String scheduleName, CustomScheduleInfo schedule) {
        String message = String.format(Locale.ENGLISH, "%s%s\n%s%s\n%s%s",
                "Student：", schedule.getStudent(),
                "Location：", schedule.getLocation(),
                "Counselor：", schedule.getCounselor());
        AlertDialog.Builder scheduleDialogBuilder = new AlertDialog.Builder(this)
                .setTitle(scheduleName)
                .setMessage(message)
                .setPositiveButton("Detail", null);
        scheduleDialogBuilder.show();
    }

}
