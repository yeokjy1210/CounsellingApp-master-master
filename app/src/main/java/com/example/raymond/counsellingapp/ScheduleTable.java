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
        customScheduleInfo.setScheduleTime("1 2", "", "2 3", "", "", "", "");
        customScheduleInfo.setLocation("A101");
        customScheduleInfo.setStudent("Raymond Chua");
        customScheduleInfo.setCounselor("John Johny");
        ScheduleInfoList.add(customScheduleInfo);

        // Add schedule1 - sample2
        CustomScheduleInfo customScheduleInfo1 = new CustomScheduleInfo();
        customScheduleInfo1.setName("Schedule 2");
        customScheduleInfo1.setScheduleTime(new String[]{"4 ", "5 ", "", "", "", "", ""});
        customScheduleInfo1.setLocation("B001");
        customScheduleInfo1.setStudent("Chok Kah Yang");
        customScheduleInfo1.setCounselor("Mia Khalifa");
        ScheduleInfoList.add(customScheduleInfo1);

        // Add schedule1 - sample3
        CustomScheduleInfo customScheduleInfo2 = new CustomScheduleInfo();
        customScheduleInfo2.setName("Schedule 3");
        customScheduleInfo2.setScheduleTime("5 6", "3 4", "", "7 9", "", "", "");
        customScheduleInfo2.setLocation("V002");
        customScheduleInfo2.setStudent("Yeok Jia Ying");
        customScheduleInfo2.setCounselor("Gaben");
        ScheduleInfoList.add(customScheduleInfo2);

        // Add schedule1 - sample4
        CustomScheduleInfo customScheduleInfo3 = new CustomScheduleInfo();
        customScheduleInfo3.setName("Schedule 4");
        customScheduleInfo3.setScheduleTime("", "1 2", "", "", "5 6", "", "");
        customScheduleInfo3.setLocation("A207");
        customScheduleInfo3.setStudent("Darlie Toothpaste");
        customScheduleInfo3.setCounselor("Colgate Master");
        ScheduleInfoList.add(customScheduleInfo3);

        // Add schedule1 - sample5
        CustomScheduleInfo customScheduleInfo4 = new CustomScheduleInfo();
        customScheduleInfo4.setName("Schedule 5");
        customScheduleInfo4.setScheduleTime("7 8", "", "", "1 2", "", "", "");
        customScheduleInfo4.setLocation("A301");
        customScheduleInfo4.setStudent("Chan Wei Xiong");
        customScheduleInfo4.setCounselor("Tan Zi Jian");
        ScheduleInfoList.add(customScheduleInfo4);

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
