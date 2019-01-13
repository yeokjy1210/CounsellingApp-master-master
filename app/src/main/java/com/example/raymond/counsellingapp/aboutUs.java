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
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class aboutUs extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;


    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;
    TextView txtStuName, txtStuEmail;
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("About Us");
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
                Intent s= new Intent(aboutUs.this,userProfile.class);
                startActivity(s);            }
        });

        listView = (ExpandableListView)findViewById(R.id.lvExp);
        initData();
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);

    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        listDataHeader.add("Terms and Condition");
        listDataHeader.add("Disclaimer and Exclusion of Liability");
        listDataHeader.add("Privacy");
        listDataHeader.add("Definitions");


        List<String> termsAndCondition = new ArrayList<>();
        termsAndCondition.add("1. TERMS OF USE\n" +
                "By downloading, browsing, accessing or using this mobile application (“Hope”), you agree to be bound by these Terms and Conditions of Use. We reserve the right to amend these terms and conditions at any time. If you disagree with any of these Terms and Conditions of Use, you must immediately discontinue your access to the Mobile Application and your use of the services offered on the Mobile Application. Continued use of the Mobile Application will constitute acceptance of these Terms and Conditions of Use, as may be amended from time to time.\n" +
                "\n" +
                "2. GENERAL ISSUES ABOUT THE MOBILE APPLICATION AND THE SERVICES\n" +
                "2.1 Applicability of terms and conditions: The use of any Services and/or the Mobile Application and the making of any Redemptions are subject to these Terms and Conditions of Use.\n" +
                "\n" +
                "2.2 Location: The Mobile Application, the Services and any Redemptions are intended solely for use by Users who access the Mobile Application in Singapore. We make no representation that the Services (or any goods or services) are available or otherwise suitable for use outside of Singapore. Notwithstanding the above, if you access the Mobile Application, use the Services or make any Redemptions from locations outside Singapore, you do so on your own initiative and are responsible for the consequences and for compliance with all applicable laws.\n" +
                "\n" +
                "2.3 Scope: The Mobile Application, the Services and any Redemptions are for your non-commercial, personal use only and must not be used for business purposes.\n" +
                "\n" +
                "2.4 Prevention on use: We reserve the right to prevent you using the Mobile Application and the Service (or any part of them) and to prevent you from making any Redemptions.\n" +
                "\n" +
                "2.5 Equipment and Networks: The provision of the Services and the Mobile Application does not include the provision of a mobile telephone or handheld device or other necessary equipment to access the Mobile Application or the Services or make any Redemptions. To use the Mobile Application or Services or to make Redemptions, you will require Internet connectivity and appropriate telecommunication links. You acknowledge that the terms of agreement with your respective mobile network provider (\"Mobile Provider\") will continue to apply when using the Mobile Application. As a result, you may be charged by the Mobile Provider for access to network connection services for the duration of the connection while accessing the Mobile Application or any such third party charges as may arise. You accept responsibility for any such charges that arise.\n" +
                "\n" +
                "2.6 Permission to use Mobile Application: If you are not the bill payer for the mobile telephone or handheld device being used to access the Mobile Application, you will be assumed to have received permission from the bill payer for using the Mobile Application.\n" +
                "\n" +
                "2.7 License to Use Material: By submitting any text or images (including photographs) (“Material”) via the Application, you represent that you are the owner of the Material, or have proper authorization from the owner of the Material to use, reproduce and distribute it. You hereby grant us a worldwide, royalty-free, non-exclusive license to use the Material to promote any products or services.\n" +
                "\n" +
                "3. RULES ABOUT USE OF THE SERVICE AND THE MOBILE APPLICATION\n" +
                "3.1 We will use reasonable endeavours to correct any errors or omissions as soon as practicable after being notified of them. However, we do not guarantee that the Services or the Mobile Application will be free of faults, and we do not accept liability for any such faults, errors or omissions. In the event of any such error, fault or omission, you should report it by contacting us at 6844 0092.\n" +
                "\n" +
                "3.2 We do not warrant that your use of the Services or the Mobile Application will be uninterrupted and we do not warrant that any information (or messages) transmitted via the Services or the Mobile Application will be transmitted accurately, reliably, in a timely manner or at all. Notwithstanding that we will try to allow uninterrupted access to the Services and the Mobile Application, access to the Services and the Mobile Application may be suspended, restricted or terminated at any time.\n" +
                "\n" +
                "3.3 We do not give any warranty that the Services and the Mobile Application are free from viruses or anything else which may have a harmful effect on any technology.\n" +
                "\n" +
                "3.4 We reserve the right to change, modify, substitute, suspend or remove without notice any information or Services on the Mobile Application from time to time. Your access to the Mobile Application and/or the Services may also be occasionally restricted to allow for repairs, maintenance or the introduction of new facilities or services. We will attempt to restore such access as soon as we reasonably can. For the avoidance of doubt, we reserve the right to withdraw any information or Services from the Mobile Application at any time.\n" +
                "\n" +
                "3.5 We reserve the right to block access to and/or to edit or remove any material which in our reasonable opinion may give rise to a breach of these Terms and Conditions of Use.\n" +
                "\n" +
                "4. SUSPENSION AND TERMINATION\n" +
                "4.1 If you use (or anyone other than you, with your permission uses) the Mobile Application, any Services in contravention of these Terms and Conditions of Use, we may suspend your use of the Services and/or Mobile Application.\n" +
                "\n" +
                "4.2 If we suspend the Services or Mobile Application, we may refuse to restore the Services or Mobile Application for your use until we receive an assurance from you, in a form we deem acceptable, that there will be no further breach of the provisions of these Terms and Conditions of Use.\n" +
                "\n" +
                "4.3 Singapore Post Limited shall fully co-operate with any law enforcement authorities or court order requesting or directing Singapore Post Limited to disclose the identity or locate anyone in breach of these Terms and Conditions of Use.\n" +
                "\n" +
                "4.4 Without limitation to anything else in this Clause 8, we shall be entitled immediately or at any time (in whole or in part) to: (a) suspend the Services and/or Mobile Application; (b) suspend your use of the Services and/or Mobile Application; and/or (c) suspend the use of the Services and/or Mobile Application for persons we believe to be connected (in whatever manner) to you, if:\n" +
                "\n" +
                "4.4.1 you commit any breach of these Terms and Conditions of Use;\n" +
                "\n" +
                "4.4.2 we suspect, on reasonable grounds, that you have, might or will commit a breach of these Terms and Conditions of Use; or\n" +
                "\n" +
                "4.4.3 we suspect, on reasonable grounds, that you may have committed or be committing any fraud against us or any person.\n" +
                "\n" +
                "4.5 Our rights under this Clause 8 shall not prejudice any other right or remedy we may have in respect of any breach or any rights, obligations or liabilities accrued prior to termination.\n" +
                "\n" +
                "5. INDEMNITY\n" +
                "You agree to indemnify and keep us indemnified against any claim, action, suit or proceeding brought or threatened to be brought against us which is caused by or arising out of (a) your use of the Services, (b) any other party’s use of the Services using your user ID, verification PIN and/or any identifier number allocated by Singapore Post Limited, and/or (c) your breach of any of these Terms and Conditions of Use, and to pay us damages, costs and interest in connection with such claim, action, suit or proceeding.\n" +
                "\n" +
                "6. INTELLECTUAL PROPERTY RIGHTS\n" +
                "6.1 All editorial content, information, photographs, illustrations, artwork and other graphic materials, and names, logos and trade marks on the Mobile Application are protected by copyright laws and/or other laws and/or international treaties, and belong to us and/or our suppliers, as the case may be. These works, logos, graphics, sounds or images may not be copied, reproduced, retransmitted, distributed, disseminated, sold, published, broadcasted or circulated whether in whole or in part, unless expressly permitted by us and/or our suppliers, as the case may be.\n" +
                "\n" +
                "6.2 Nothing contained on the Mobile Application should be construed as granting by implication, estoppel, or otherwise, any license or right to use any trademark displayed on the Mobile Application without our written permission. Misuse of any trademarks or any other content displayed on the Mobile Application is prohibited.\n" +
                "\n" +
                "6.3 We will not hesitate to take legal action against any unauthorised usage of our trade marks, name or symbols to preserve and protect its rights in the matter. All rights not expressly granted herein are reserved. Other product and company names mentioned herein may also be the trade marks of their respective owners.\n" +
                "\n" +
                "7. AMENDMENTS\n" +
                "7.1 We may periodically make changes to the contents of the Mobile Application, including to the descriptions and prices of goods and services advertised, at any time and without notice. We assume no liability or responsibility for any errors or omissions in the content of the Mobile Application.\n" +
                "\n" +
                "7.2 We reserve the right to amend these Terms and Conditions of Use from time to time without notice. The revised Terms and Conditions of Use will be posted on the Mobile Application and shall take effect from the date of such posting. You are advised to review these terms and conditions periodically as they are binding upon you.\n" +
                "\n" +
                "8. APPLICABLE LAW AND JURISDICTION\n" +
                "8.1 The Mobile Application can be accessed from all countries around the world where the local technology permits. As each of these places have differing laws, by accessing the Mobile Application both you and we agree that the laws of the Republic of Singapore, without regard to the conflicts of laws principles thereof, will apply to all matters relating to the use of the Mobile Application.\n" +
                "\n" +
                "8.2 You accept and agree that both you and we shall submit to the exclusive jurisdiction of the courts of the Republic of Singapore in respect of any dispute arising out of and/or in connection with these Terms and Conditions of Use.\n");

        List<String> privacy = new ArrayList<>();
        privacy.add("1 Access to the Mobile Application and use of the Services offered on the Mobile Application by TARUC and/or its group of companies is subject to this Privacy Policy. " +
                "By accessing the Mobile Application and by continuing to use the Services offered, you are deemed to have accepted this Privacy Policy, and in particular, you are deemed to have consented to our use and disclosure of your personal information in the manner prescribed in this Privacy Policy and for the purposes set out in Clauses 3.7 and/or 4.1. We reserve the right to amend this Privacy Policy from time to time. If you disagree with any part of this Privacy Policy, you must immediately discontinue your access to the Mobile Application and your use of the Services.\n" +
                "\n" +
                "2 As part of the normal operation of our Services, we collect, use and, in some cases, disclose information about you to third parties. Accordingly, we have developed this Privacy Policy in order for you to understand how we collect, use, communicate and disclose and make use of your personal information when you use the Services on the Mobile Application:-\n" +
                "\n" +
                "(a) Before or at the time of collecting personal information, we will identify the purposes for which information is being collected.\n" +
                "\n" +
                "(b) We will collect and use of personal information solely with the objective of fulfilling those purposes specified by us and for other compatible purposes, unless we obtain the consent of the individual concerned or as required by law.\n" +
                "\n" +
                "(c) We will only retain personal information as long as necessary for the fulfillment of those purposes.\n" +
                "\n" +
                "(d) We will collect personal information by lawful and fair means and, where appropriate, with the knowledge or consent of the individual concerned.\n" +
                "\n" +
                "(e) Personal information should be relevant to the purposes for which it is to be used, and, to the extent necessary for those purposes, should be accurate, complete, and up-to-date.\n" +
                "\n" +
                "(f) We will protect personal information by reasonable security safeguards against loss or theft, as well as unauthorized access, disclosure, copying, use or modification.\n" +
                "\n" +
                "We are committed to conducting our business in accordance with these principles in order to ensure that the confidentiality of personal information is protected and maintained.");

        List<String> disclaimer = new ArrayList<>();
        disclaimer.add("1 The Mobile Application, the Services, the information on the Mobile Application and use of all related facilities are provided on an \"as is, as available\" basis without any warranties whether express or implied.\n" +
                "\n" +
                "2 To the fullest extent permitted by applicable law, we disclaim all representations and warranties relating to the Mobile Application and its contents, including in relation to any inaccuracies or omissions in the Mobile Application, warranties of merchantability, quality, fitness for a particular purpose, accuracy, availability, non-infringement or implied warranties from course of dealing or usage of trade.\n" +
                "\n" +
                "3 We do not warrant that the Mobile Application will always be accessible, uninterrupted, timely, secure, error free or free from computer virus or other invasive or damaging code or that the Mobile Application will not be affected by any acts of God or other force majeure events, including inability to obtain or shortage of necessary materials, equipment facilities, power or telecommunications, lack of telecommunications equipment or facilities and failure of information technology or telecommunications equipment or facilities.\n" +
                "\n" +
                "4 While we may use reasonable efforts to include accurate and up-to-date information on the Mobile Application, we make no warranties or representations as to its accuracy, timeliness or completeness.\n" +
                "\n" +
                "5 We shall not be liable for any acts or omissions of any third parties howsoever caused, and for any direct, indirect, incidental, special, consequential or punitive damages, howsoever caused, resulting from or in connection with the mobile application and the services offered in the mobile application, your access to, use of or inability to use the mobile application or the services offered in the mobile application, reliance on or downloading from the mobile application and/or services, or any delays, inaccuracies in the information or in its transmission including but not limited to damages for loss of business or profits, use, data or other intangible, even if we have been advised of the possibility of such damages.\n" +
                "\n" +
                "6 We shall not be liable in contract, tort (including negligence or breach of statutory duty) or otherwise howsoever and whatever the cause thereof, for any indirect, consequential, collateral, special or incidental loss or damage suffered or incurred by you in connection with the Mobile Application and these Terms and Conditions of Use. For the purposes of these Terms and Conditions of Use, indirect or consequential loss or damage includes, without limitation, loss of revenue, profits, anticipated savings or business, loss of data or goodwill, loss of use or value of any equipment including software, claims of third parties, and all associated and incidental costs and expenses.\n" +
                "\n" +
                "7 The above exclusions and limitations apply only to the extent permitted by law. None of your statutory rights as a consumer that cannot be excluded or limited are affected.\n" +
                "\n" +
                "8 Notwithstanding our efforts to ensure that our system is secure, you acknowledge that all electronic data transfers are potentially susceptible to interception by others. We cannot, and do not, warrant that data transfers pursuant to the Mobile Application, or electronic mail transmitted to and from us, will not be monitored or read by others.\n" +
                "\n");

        List<String> definition = new ArrayList<>();
        definition.add("In these Terms and Conditions of Use, the following capitalised terms shall have the following meanings, except where the context otherwise requires:\n" +
                "\n" +
                "\"Account\" means an account created by a Student on the Mobile Application as part of Registration.\n" +
                "\n" +
                "\"Event\" refers to the event held by any sort organisations and students are encouraged to participate in it.\n" +
                "\n" +
                "\"Privacy Policy\" means the privacy policy set out in Clause 14 of these Terms and Conditions of Use.\n" +
                "\n" +
                "\"Register\" means to create an Account on the Mobile Application and \"Registration\" means the act of creating such an Account.\n" +
                "\n" +
                "\"Users\" means users of the Mobile Application, including you and \"User\" means any one of them.\n" +
                "\n" +
                "\"Counselor\" refers ot the counselor hired by Tunku Abdul Rahman University College, Setapak as a full-time or part-time counselor who provides counselling services.\n" +
                "\n" +
                "\"Reserve\" means action of booking a counselling session with the counselor from Tunku Abdul Rahman University College, Setapak.\n" +
                "\n");

        listHash.put(listDataHeader.get(0),termsAndCondition);
        listHash.put(listDataHeader.get(1),privacy);
        listHash.put(listDataHeader.get(2),disclaimer);
        listHash.put(listDataHeader.get(3),definition);


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
                Intent h= new Intent(aboutUs.this,homepage.class);
                startActivity(h);
                break;
            case R.id.nav_counselor:
                Intent z= new Intent(aboutUs.this,counselorList.class);
                startActivity(z);
                break;
            case R.id.nav_event:
                Intent i= new Intent(aboutUs.this,eventList.class);
                startActivity(i);
                break;
            case R.id.nav_aboutus:
                Intent g= new Intent(aboutUs.this,aboutUs.class);
                startActivity(g);
                break;
            case R.id.nav_signout:
                prefs = getSharedPreferences("user", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove("user");
                Intent s= new Intent(aboutUs.this,login.class);
                startActivity(s);
                Toast.makeText(aboutUs.this,"Successful Logout",Toast.LENGTH_LONG).show();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
