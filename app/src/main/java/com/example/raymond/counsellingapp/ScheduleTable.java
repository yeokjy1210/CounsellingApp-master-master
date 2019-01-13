package com.example.raymond.counsellingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raymond.counsellingapp.Model.ScheduleInfo;
import com.example.raymond.counsellingapp.Model.StudentSchedule;

import java.util.ArrayList;
import java.util.Locale;

public class ScheduleTable extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar = null;
    TextView txtStuName, txtStuEmail;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        txtStuName = headerView.findViewById(R.id.txtStuName);
        txtStuEmail = headerView.findViewById(R.id.txtStuEmail);
        prefs = getSharedPreferences("user", MODE_PRIVATE);
        String restoredText = prefs.getString("studentName", null);
        if (restoredText != null) {
            txtStuName.setText(prefs.getString("studentName", "No name"));
            txtStuEmail.setText(prefs.getString("studentEmail", "No email"));
        }

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(ScheduleTable.this, userProfile.class);
                startActivity(s);
            }
        });


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
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){

            case R.id.nav_homepage:
                Intent h= new Intent(ScheduleTable.this,homepage.class);
                startActivity(h);
                break;
            case R.id.nav_counselor:
                Intent z= new Intent(ScheduleTable.this,counselorList.class);
                startActivity(z);
                break;
            case R.id.nav_event:
                Intent i= new Intent(ScheduleTable.this,eventList.class);
                startActivity(i);
                break;
            case R.id.nav_aboutus:
                Intent g= new Intent(ScheduleTable.this,aboutUs.class);
                startActivity(g);
                break;
            case R.id.nav_signout:
                prefs = getSharedPreferences("user", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove("user");
                Intent s= new Intent(ScheduleTable.this,login.class);
                startActivity(s);
                Toast.makeText(ScheduleTable.this,"Successful Logout",Toast.LENGTH_LONG).show();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
