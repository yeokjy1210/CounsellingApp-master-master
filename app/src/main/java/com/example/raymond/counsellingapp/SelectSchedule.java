package com.example.raymond.counsellingapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectSchedule extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static String GET_URL = "http://10.0.2.2/ky/getTime.php";
    RequestQueue queue;

    Spinner spinner;
    Button bPick;
    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    TextView txtStuName, txtStuEmail;
    private SharedPreferences prefs;
    List<String> timeList = new ArrayList<String>();

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
        String restoredText = prefs.getString("studentName", null);
        if (restoredText != null) {
            txtStuName.setText(prefs.getString("studentName", "No name"));
            txtStuEmail.setText(prefs.getString("studentEmail", "No email"));
        }

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(SelectSchedule.this, userProfile.class);
                startActivity(s);
            }
        });

        addItemsOnSpinner();

    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal = i1;
        dayFinal = i2;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(SelectSchedule.this, SelectSchedule.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal = i;
        minuteFinal = i1;


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

        switch (id) {

            case R.id.nav_homepage:
                Intent h = new Intent(SelectSchedule.this, homepage.class);
                startActivity(h);
                break;
            case R.id.nav_counselor:
                Intent z = new Intent(SelectSchedule.this, counselorList.class);
                startActivity(z);
                break;
            case R.id.nav_event:
                Intent i = new Intent(SelectSchedule.this, eventList.class);
                startActivity(i);
                break;
            case R.id.nav_aboutus:
                Intent g = new Intent(SelectSchedule.this, aboutUs.class);
                startActivity(g);
                break;
            case R.id.nav_signout:
                prefs = getSharedPreferences("user", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove("user");
                Intent s = new Intent(SelectSchedule.this, login.class);
                startActivity(s);
                Toast.makeText(SelectSchedule.this, "Successful Logout", Toast.LENGTH_LONG).show();
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addItemsOnSpinner() {

        prefs = getSharedPreferences("counselor", MODE_PRIVATE);
        final String idExtra = prefs.getString("counselorID", null);

        spinner = findViewById(R.id.spinner);
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>
                (SelectSchedule.this, android.R.layout.simple_spinner_item, timeList);
        spinner.setAdapter(spinnerAdapter);
        StringRequest strRequest = new StringRequest(Request.Method.POST, GET_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            timeList.clear();
                            int msg = jObj.getInt("success");
                            if (msg == 1) {
                                JSONArray responseTime = jObj.getJSONArray("schedule");
                                for (int i = 0; i < responseTime.length(); i++) {
                                    JSONObject eventObj = responseTime.getJSONObject(i);
                                    String time = eventObj.getString("time");
                                    timeList.add(time);
                                }
                            } else {
                                SelectSchedule.this.finish();
                                Intent intent1 = new Intent(SelectSchedule.this, counselorList.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent1);
                                Toast.makeText(getApplicationContext(), "Error: counselor no free time\n " +
                                        "Please select another counselor", Toast.LENGTH_LONG).show();
                            }
                            spinnerAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Volley Error: " + volleyError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("counselorID", idExtra);
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue = Volley.newRequestQueue(this);
        queue.add(strRequest);


        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                ((TextView) parent.getChildAt(0)).setTextSize(20);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

}
