package com.example.raymond.counsellingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class eventList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static String GET_URL = "http://10.0.2.2/ky/getEvent.php";
    ListView mListView;
    RequestQueue queue;
    List<Event> eventList;

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar = null;
    TextView txtStuName, txtStuEmail;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Event");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

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
                Intent s = new Intent(eventList.this, userProfile.class);
                startActivity(s);
            }
        });


        mListView = findViewById(R.id.eventList);
        eventList = new ArrayList<>();

        getEvent(getApplicationContext(), GET_URL);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(getApplicationContext(), eventDetail.class);
                String id = eventList.get(position).getEventID();
                String name = eventList.get(position).getEventName();
                String desc = eventList.get(position).getEventDesc();
                String date = eventList.get(position).getEventDate();
                String time = eventList.get(position).getEventTime();
                String venue = eventList.get(position).getEventVenue();
                int fee = eventList.get(position).getEventFee();
                String img = eventList.get(position).getEventImg();

                intent.putExtra("eventID", id);
                intent.putExtra("eventName", name);
                intent.putExtra("eventDesc", desc);
                intent.putExtra("eventDate", date);
                intent.putExtra("eventTime", time);
                intent.putExtra("eventVenue", venue);
                intent.putExtra("eventFee", fee);
                intent.putExtra("eventImg", img);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

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
                Intent h = new Intent(eventList.this, homepage.class);
                startActivity(h);
                break;
            case R.id.nav_counselor:
                Intent z = new Intent(eventList.this, counselorList.class);
                startActivity(z);
                break;
            case R.id.nav_event:
                Intent i = new Intent(eventList.this, eventList.class);
                startActivity(i);
                break;
            case R.id.nav_aboutus:
                Intent g = new Intent(eventList.this, aboutUs.class);
                startActivity(g);
                break;
            case R.id.nav_signout:
                Intent s = new Intent(eventList.this, aboutUs.class);
                startActivity(s);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getEvent(Context context, String url) {
        // Instantiate the RequestQueue
        queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            eventList.clear();
                            int msg = response.getInt("success");

                            if (msg == 1) {
                                JSONArray responseEvent = response.getJSONArray("event");
                                for (int i = 0; i < responseEvent.length(); i++) {
                                    JSONObject eventObj = responseEvent.getJSONObject(i);
                                    String id = eventObj.getString("eventID");
                                    String name = eventObj.getString("eventName");
                                    String desc = eventObj.getString("eventDesc");
                                    String date = eventObj.getString("eventDate");
                                    String time = eventObj.getString("eventTime");
                                    String venue = eventObj.getString("eventVenue");
                                    int fee = eventObj.getInt("eventFee");
                                    String img = eventObj.getString("eventImg");
                                    Event event = new Event(id, name, desc, date, time, venue, fee, img);
                                    eventList.add(event);
                                }
                                loadEvent();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error: no data", Toast.LENGTH_LONG).show();
                            }
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
                });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    public void loadEvent() {
        final eventAdapter adapter = new eventAdapter(this, R.layout.content_event_list, eventList);
        mListView.setAdapter(adapter);
        Toast.makeText(getApplicationContext(), "Count :" + eventList.size(), Toast.LENGTH_LONG).show();
    }
}
