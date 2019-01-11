package com.example.raymond.counsellingapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class register extends AppCompatActivity {
    private static final String TAG = "register";
    ProgressDialog progressDialog;
    private static final String URL_FOR_REGISTRATION = "http://counsellingapptarc.000webhostapp.com/androidphp/register.php";
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[0-9])" +    //at least 1 digit
                    ".{4,}" +               //at least 4 characters
                    "$");

    private EditText textInputUserID;
    private EditText textInputPassword;
    private EditText textInputConfirmPassword;
    private EditText textInputName;
    private EditText textInputEmail;
    private EditText textInputPhoneNo;

    final Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date;
    private EditText textInputDOB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textInputDOB=findViewById(R.id.text_input_stuDOB);

        textInputUserID = findViewById(R.id.text_input_stuID);
        textInputPassword = findViewById(R.id.text_input_password);
        textInputConfirmPassword = findViewById(R.id.text_input_confirmPassword);
        textInputName = findViewById(R.id.text_input_name);
        textInputEmail = findViewById(R.id.text_input_Email);
        textInputPhoneNo = findViewById(R.id.text_input_phoneNo);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        date = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        textInputDOB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(register.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               if (!validateEmail() | !validatePassword()| !Is_Valid_Person_Name(textInputName)| !Is_Valid_number(textInputUserID)) {
                    return;
                }else{
                   textInputEmail.setError(textInputDOB.getText().toString());
                registerUser(textInputUserID.getText().toString(),
                        textInputPassword.getText().toString(),
                        textInputName.getText().toString(),
                        textInputEmail.getText().toString(),
                        textInputDOB.getText().toString());
            }
            }
        });

    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        textInputDOB.setText(sdf.format(myCalendar.getTime()));
    }

    public boolean Is_Valid_number(EditText edt) throws NumberFormatException {
        if (edt.getText().toString().length() <= 0) {
            edt.setError("Field cannot be empty.");
            return false;
        } else if (!edt.getText().toString().matches("(?<=\\s|^)\\d+(?=\\s|$)")) {
            edt.setError("Accept Integer Only.");
            return false;
        } else {
            return true;
        }

    }
    public boolean Is_Valid_Person_Name(EditText edt) throws NumberFormatException {
        if (edt.getText().toString().length() <= 0) {
            edt.setError("Field cannot be empty.");
            return false;
        } else if (!edt.getText().toString().matches("[a-zA-Z ]+")) {
            edt.setError("Accept Alphabets Only.");
            return false;
        } else {
            return true;
        }

    }

    private boolean validateEmail() {
        String emailInput = textInputEmail.getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputEmail.setError("Please enter a valid email address");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String passwordInput = textInputPassword.getText().toString().trim();
        String passwordInput1 = textInputConfirmPassword.getText().toString().trim();


        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            textInputPassword.setError("Password too weak");
            return false;
        }else if (passwordInput1.isEmpty()) {
            textInputConfirmPassword.setError("Field can't be empty");
            return false;
        } else if (passwordInput1.compareTo(passwordInput)!=0){
            textInputConfirmPassword.setError("Password Un-match");
            return false;
        } else{
            textInputPassword.setError(null);
            return true;
        }
    }
    private void registerUser(final String id,  final String password, final String name,
                              final String email, final String dob) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";

        progressDialog.setMessage("Adding you ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String user = jObj.getJSONObject("Student").getString("studentName");
                        Toast.makeText(getApplicationContext(), "Hi " + user +", You are successfully Added!", Toast.LENGTH_SHORT).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                register.this,
                                login.class);
                        startActivity(intent);
                        finish();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "You are successfully Added!", Toast.LENGTH_SHORT).show();

                    // Launch login activity
                    Intent intent = new Intent(
                            register.this,
                            login.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("studentID", id);
                params.put("studentPass", password);
                params.put("studentName", name);
                params.put("studentEmail", email);
                params.put("studentDOB", dob);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

}
