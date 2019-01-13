package com.example.raymond.counsellingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class counselorList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static String GET_URL = "http://10.0.2.2/ky/getCounselor.php";
    ListView mListView;
    RequestQueue queue;
    List<Counselor> counselorList;

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar = null;
    TextView txtStuName, txtStuEmail;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counselor_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Counselor");
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
                Intent s = new Intent(counselorList.this, userProfile.class);
                startActivity(s);
            }
        });


        mListView = findViewById(R.id.counselorList);
        counselorList = new ArrayList<>();

        getCounselor(getApplicationContext(), GET_URL);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(getApplicationContext(), counselorDetail.class);
                String id = counselorList.get(position).getCounselorID();
                String name = counselorList.get(position).getCounselorName();
                int age = counselorList.get(position).getCounselorAge();
                String type = counselorList.get(position).getCounselorType();
                String desc = counselorList.get(position).getCounselorDesc();
                String contact = counselorList.get(position).getCounselorContact();
                String email = counselorList.get(position).getCounselorEmail();
                String img = counselorList.get(position).getCounselorImg();
                String venue = counselorList.get(position).getCounselorVenue();
                int exp = counselorList.get(position).getCounselorExp();


                intent.putExtra("counselorID", id);
                intent.putExtra("counselorName", name);
                intent.putExtra("counselorAge", age);
                intent.putExtra("counselorType", type);
                intent.putExtra("counselorDesc", desc);
                intent.putExtra("counselorContact", contact);
                intent.putExtra("counselorEmail", email);
                intent.putExtra("counselorImage", img);
                intent.putExtra("counselorVenue", venue);
                intent.putExtra("counselorExpYear", exp);
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
                Intent h = new Intent(counselorList.this, homepage.class);
                startActivity(h);
                break;
            case R.id.nav_counselor:
                Intent z = new Intent(counselorList.this, counselorList.class);
                startActivity(z);
                break;
            case R.id.nav_event:
                Intent i = new Intent(counselorList.this, counselorList.class);
                startActivity(i);
                break;
            case R.id.nav_aboutus:
                Intent g = new Intent(counselorList.this, aboutUs.class);
                startActivity(g);
                break;
            case R.id.nav_signout:
                Intent s = new Intent(counselorList.this, aboutUs.class);
                startActivity(s);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getCounselor(Context context, String url) {
        // Instantiate the RequestQueue
        queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            counselorList.clear();
                            int msg = response.getInt("success");

                            if (msg == 1) {

                                JSONArray responseCounselor = response.getJSONArray("counselor");
                                for (int i = 0; i < responseCounselor.length(); i++) {
                                    JSONObject counselorObj = responseCounselor.getJSONObject(i);
                                    String id = counselorObj.getString("counselorID");
                                    String name = counselorObj.getString("counselorName");
                                    int age = counselorObj.getInt("counselorAge");
                                    String type = counselorObj.getString("counselorType");
                                    String desc = counselorObj.getString("counselorDesc");
                                    String contact = counselorObj.getString("counselorContact");
                                    String email = counselorObj.getString("counselorEmail");
                                    String img = counselorObj.getString("counselorImage");
                                    String venue = counselorObj.getString("counselorVenue");
                                    int exp = counselorObj.getInt("counselorExpYear");

                                    Counselor counselor = new Counselor(id, name, age, type, desc, contact, email, img, venue, exp);
                                    counselorList.add(counselor);
                                }
                                loadCounselor();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error: no data", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Volley Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Error: " + volleyError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    public void loadCounselor() {
        final counselorAdapter adapter = new counselorAdapter(this, R.layout.content_counselor_list, counselorList);
        mListView.setAdapter(adapter);
        Toast.makeText(getApplicationContext(), "Count :" + counselorList.size(), Toast.LENGTH_LONG).show();
    }
}
