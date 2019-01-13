package com.example.raymond.counsellingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
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
import android.widget.ImageView;
import android.widget.TextView;

public class counselorDetail extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar = null;
    TextView txtStuName, txtStuEmail;
    private SharedPreferences prefs;
    TextView name, age, type, desc, contact, email, venue, exp;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counselor_detail);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Counselor Detail");
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
                Intent s = new Intent(counselorDetail.this, userProfile.class);
                startActivity(s);
            }
        });

        final String idExtra = getIntent().getStringExtra("counselorID");
        String nameExtra = getIntent().getStringExtra("counselorName");
        int ageExtra = getIntent().getIntExtra("counselorAge", 0);
        String typeExtra = getIntent().getStringExtra("counselorType");
        String descExtra = getIntent().getStringExtra("counselorDesc");
        String contactExtra = getIntent().getStringExtra("counselorContact");
        String emailExtra = getIntent().getStringExtra("counselorEmail");
        String imgExtra = getIntent().getStringExtra("counselorImage");
        String venueExtra = getIntent().getStringExtra("counselorVenue");
        int expExtra = getIntent().getIntExtra("counselorExpYear", 0);

        name = findViewById(R.id.txtName);
        age = findViewById(R.id.txtAge);
        type = findViewById(R.id.txtType);
        desc = findViewById(R.id.txtDesc);
        contact = findViewById(R.id.txtContact);
        email = findViewById(R.id.txtEmail);
        venue = findViewById(R.id.txtVenue);
        exp = findViewById(R.id.txtExp);
        img = findViewById(R.id.imgCounselor);


        name.setText(nameExtra);
        age.setText(ageExtra + "");
        type.setText(typeExtra);
        desc.setText(descExtra);
        contact.setText(contactExtra);
        email.setText(emailExtra);
        venue.setText(venueExtra);
        exp.setText(expExtra + "");
        showImage(imgExtra);

        Button btnReg = findViewById(R.id.btnRegister);
        btnReg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(counselorDetail.this, SelectSchedule.class);
                startActivity(i);

                SharedPreferences.Editor editor = getSharedPreferences("counselor", MODE_PRIVATE).edit();
                editor.putString("counselorID", idExtra);
                editor.apply();
            }
        });
    }

    private void showImage(String image) {

        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,
                0, decodedString.length);
        if (decodedByte != null) {
            img.setImageBitmap(decodedByte);
        } else {
            img.setImageResource(R.drawable.hope_icon);
        }
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
                Intent h = new Intent(counselorDetail.this, homepage.class);
                startActivity(h);
                break;
            case R.id.nav_counselor:
                Intent z = new Intent(counselorDetail.this, counselorList.class);
                startActivity(z);
                break;
            case R.id.nav_event:
                Intent i = new Intent(counselorDetail.this, eventList.class);
                startActivity(i);
                break;
            case R.id.nav_aboutus:
                Intent g = new Intent(counselorDetail.this, aboutUs.class);
                startActivity(g);
                break;
            case R.id.nav_signout:
                Intent s = new Intent(counselorDetail.this, aboutUs.class);
                startActivity(s);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
