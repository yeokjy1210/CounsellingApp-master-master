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

public class counselorList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;
    TextView txtStuName, txtStuEmail;
    private SharedPreferences prefs;

    int[] images =  new int[]{R.drawable.person_abu, R.drawable.person_windy, R.drawable.person_samilah, R.drawable.person_john, R.drawable.person_cindy};
    String[] counselorName = new String[]{"Abu Mutuh", "Windy Chong", "Samilah Patutlah", "John John", "Cindy Sydney"};
    String[] counselorAge = new String[]{"28","27","31","40","37"};
    String[] counselorDesc =new String[]{
            "Family relationship, friendship.",
            "Social issue and communication issue.",
            "Academic performance issue.",
            "Health conditions like insomnia or stress issue.",
            "Relationship such as family relationship."};
    String[] counselorEmail=new String[]{"abum@tarc.edu.my","windyc@tarc.edu.my","samilahp@tarc.edu.my","johnj@tarc.edu.my","cindys@tarc.edu.my"};
    String[] counselorContact=new String[]{"010-3664328","010-6666667","010-5771549","016-4568877","010-1234567"};
    String[] counselorVenue=new String[]{"B103","A105","A107","A103","V002"};
    String[] counselorYOE=new String[]{"8","5","11","18","7"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counselor_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Counselor");
        setSupportActionBar(toolbar);

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
                Intent s= new Intent(counselorList.this,userProfile.class);
                startActivity(s);            }
        });

        List<HashMap<String,String>> aList = new ArrayList<HashMap<String, String>>();
        for(int i=0;i<images.length;i++){
            HashMap<String,String> hm= new HashMap<String,String>();
            hm.put("imagesKey",Integer.toString(images[i]));
            hm.put("counselorNameKey",counselorName[i]);
            hm.put("counselorAgeKey",counselorAge[i]);
            hm.put("counselorDescKey",counselorDesc[i]);
            hm.put("counselorVenueKey",counselorVenue[i]);
            hm.put("counselorYOEKey",counselorYOE[i]);
            aList.add(hm);
        }
        String[] from ={
                "imagesKey","counselorNameKey","counselorAgeKey",
                "counselorDescKey","counselorVenueKey","counselorYOEKey"
        };
        int[] to= {
                R.id.counselor_imageView,R.id.textView_name,R.id.textView_age,R.id.textView_desc,R.id.textView_venue,R.id.textView_year
        };

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(),aList,R.layout.counselor_list_item,from,to);
        ListView mListView = (ListView)findViewById(R.id.counselorList);
        mListView.setAdapter(simpleAdapter);


        mListView.setAdapter(simpleAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {

                Intent intent = new Intent(getApplicationContext(), counselorDetail.class);
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
                Intent h= new Intent(counselorList.this,homepage.class);
                startActivity(h);
                break;
            case R.id.nav_counselor:
                Intent z= new Intent(counselorList.this,counselorList.class);
                startActivity(z);
                break;
            case R.id.nav_event:
                Intent i= new Intent(counselorList.this,eventList.class);
                startActivity(i);
                break;
            case R.id.nav_aboutus:
                Intent g= new Intent(counselorList.this,aboutUs.class);
                startActivity(g);
                break;
            case R.id.nav_signout:
                Intent s= new Intent(counselorList.this,aboutUs.class);
                startActivity(s);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
