package com.example.raymond.counsellingapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.format.DateFormat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.util.Calendar;

public class SelectSchedule extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{
    Button bPick;
    int day,month,year,hour,minute;
    int dayFinal,monthFinal,yearFinal,hourFinal,minuteFinal;
    TextView txtStuName, txtStuEmail;
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        txtStuName = headerView.findViewById(R.id.txtStuName);
        txtStuEmail = headerView.findViewById(R.id.txtStuEmail);
        prefs = getSharedPreferences("user", MODE_PRIVATE);
        String restoredText = prefs.getString("studentName",null);
        if(restoredText !=null){
            txtStuName.setText(prefs.getString("studentName","No name"));
            txtStuEmail.setText(prefs.getString("studentEmail","No email"));
        }

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s= new Intent(SelectSchedule.this,userProfile.class);
                startActivity(s);            }
        });

        bPick = (Button)findViewById(R.id.btn_pick);
        bPick.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Calendar c = Calendar.getInstance();
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH);
                    day = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(SelectSchedule.this,SelectSchedule.this,year,month,day);
                    datePickerDialog.show();


                }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i,int i1,int i2){
        yearFinal=i;
        monthFinal=i1;
        dayFinal=i2;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(SelectSchedule.this,SelectSchedule.this,hour,minute,DateFormat.is24HourFormat(this));
        timePickerDialog.show();
        }
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1){
        hourFinal=i;
        minuteFinal=i1;


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
                Intent h= new Intent(SelectSchedule.this,homepage.class);
                startActivity(h);
                break;
            case R.id.nav_counselor:
                Intent z= new Intent(SelectSchedule.this,counselorList.class);
                startActivity(z);
                break;
            case R.id.nav_event:
                Intent i= new Intent(SelectSchedule.this,eventList.class);
                startActivity(i);
                break;
            case R.id.nav_aboutus:
                Intent g= new Intent(SelectSchedule.this,aboutUs.class);
                startActivity(g);
                break;
            case R.id.nav_signout:
                Intent s= new Intent(SelectSchedule.this,aboutUs.class);
                startActivity(s);
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
