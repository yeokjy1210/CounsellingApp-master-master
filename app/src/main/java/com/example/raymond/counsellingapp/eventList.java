package com.example.raymond.counsellingapp;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class eventList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;
    TextView txtStuName, txtStuEmail;
    private SharedPreferences prefs;

    int[] images = new int[]{R.drawable.event_photo1,R.drawable.event_photo2,R.drawable.event_photo3,R.drawable.event_photo4,R.drawable.event_photo5};
    String[] eventTitle = new String[]{"Never Give Up","Just Do It","You Da Best","Bright Future","Big Family"};

    ArrayList<HashMap<String,String>> data = new ArrayList<>();
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Event");
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
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
        String restoredText = prefs.getString("studentName",null);
        if(restoredText !=null){
            txtStuName.setText(prefs.getString("studentName","No name"));
            txtStuEmail.setText(prefs.getString("studentEmail","No email"));
        }

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s= new Intent(eventList.this,userProfile.class);
                startActivity(s);            }
        });

        List<HashMap<String,String>> aList = new ArrayList<HashMap<String, String>>();
        for(int i=0;i<images.length;i++){
            HashMap<String,String> hm= new HashMap<String,String>();
            hm.put("imagesKey",Integer.toString(images[i]));
            hm.put("eventTitleKey",eventTitle[i]);
            aList.add(hm);
        }
        String[] from ={
                "imagesKey","eventTitleKey"
        };
        int[] to= {
                R.id.event_image,R.id.event_title
        };

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(),aList,R.layout.event_list_item,from,to);
        ListView mListView = (ListView)findViewById(R.id.eventList);
        mListView.setAdapter(simpleAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {

                Intent intent = new Intent(getApplicationContext(), eventDetail.class);
                intent.putExtra("position", position);
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

        switch (id){

            case R.id.nav_homepage:
                Intent h= new Intent(eventList.this,homepage.class);
                startActivity(h);
                break;
            case R.id.nav_counselor:
                Intent z= new Intent(eventList.this,counselorList.class);
                startActivity(z);
                break;
            case R.id.nav_event:
                Intent i= new Intent(eventList.this,eventList.class);
                startActivity(i);
                break;
            case R.id.nav_aboutus:
                Intent g= new Intent(eventList.this,aboutUs.class);
                startActivity(g);
                break;
            case R.id.nav_signout:
                Intent s= new Intent(eventList.this,aboutUs.class);
                startActivity(s);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
