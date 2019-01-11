package com.example.raymond.counsellingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    private static final String TAG = "login";
    private static final String URL_FOR_LOGIN = "http://counsellingapptarc.000webhostapp.com/androidphp/getStudent.php";
    private EditText textInputUserID;
    private EditText textInputPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputUserID = findViewById(R.id.text_input_loginId);
        textInputPassword = findViewById(R.id.text_input_password);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String mUserID = textInputUserID.getText().toString().trim();
                String mPass = textInputPassword.getText().toString().trim();

                if (!mUserID.isEmpty() || !mPass.isEmpty()) {
                    loginUser(mUserID, mPass);
                } else {
                    textInputUserID.setError("Please insert user");
                    textInputPassword.setError("Please insert password");
                }
            }
        });
    }

    private void loginUser(final String userID, final String password) {
        // Tag used to cancel the request


        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);

                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");
                    JSONObject studentJson = jObj.getJSONObject("user");
                    if (success == 1) {


                        Intent intent = new Intent(login.this, homepage.class);
                        startActivity(intent);

                        Toast.makeText(login.this, "Success Login.\n Your Name: " +
                                studentJson.getString("studentName") + "\n Your Email: " +
                                studentJson.getString("studentEmail"), Toast.LENGTH_SHORT).show();

                        Student student = new Student(
                                studentJson.getString("studentID"),
                                studentJson.getString("studentPass"),
                                studentJson.getString("studentName"),
                                studentJson.getString("studentEmail"),
                                studentJson.getString("studentDOB")
                        );

                        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                        editor.putString("studentID", student.getStudentID());
                        editor.putString("studentPass", student.getStudentPass());
                        editor.putString("studentName", student.getStudentName());
                        editor.putString("studentEmail", student.getStudentEmail());
                        editor.putString("studentDOB", student.getStudentDOB());
                        editor.apply();

                    } else
                        Toast.makeText(login.this, "Login.\n Your Name: ", Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(login.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(login.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("studentID", userID);
                params.put("studentPass", password);
                return params;
            }
        };
        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);
    }

}
