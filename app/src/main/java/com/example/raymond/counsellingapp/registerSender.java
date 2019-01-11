package com.example.raymond.counsellingapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;


public class registerSender extends AsyncTask<Void, Void, String> {

    Context context;
    String urlAddress;
    EditText editText, editText2, editText3, editText4, editText5;

    String id, pass, name, email, dob;

    //1.Constructor
    //2.receive data

    public registerSender(Context context, String urlAddress, EditText... editTexts) {

        this.context = context;
        this.urlAddress = urlAddress;

        //INPUT EDITTEXTS
        this.editText = editTexts[0];
        this.editText2 = editTexts[1];
        this.editText3 = editTexts[2];
        this.editText4 = editTexts[3];
        this.editText5 = editTexts[4];

        //GET TEXTS FROM EDITEXTS
        id = editText.getText().toString();
        pass = editText2.getText().toString();
        name = editText3.getText().toString();
        email = editText4.getText().toString();
        dob = editText5.getText().toString();
    }


    /*
   1.SHOW PROGRESS DIALOG WHILE DOWNLOADING DATA
    */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    /*
    1.WHERE WE SEND DATA TO NETWORK
    2.RETURNS FOR US A STRING
     */
    @Override
    protected String doInBackground(Void... params) {
        HttpURLConnection conn = EstablishConnection.connect(urlAddress);

        if (conn == null) {
            return null;
        }

        try {
            OutputStream os = conn.getOutputStream();

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            bw.write(new registerWrap(id, pass, name, email, dob).WrapData());

            bw.flush();

            //RELEASE RES
            bw.close();
            os.close();

            //HAS IT BEEN SUCCESSFUL?
            int responseCode = conn.getResponseCode();

            if (responseCode == conn.HTTP_OK) {
                //GET EXACT RESPONSE
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer response = new StringBuffer();

                String line;

                //READ LINE BY LINE
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                //RELEASE RES
                br.close();

                return response.toString();

            } else {

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
  1. CALLED WHEN JOB IS OVER
  2. WE DISMISS OUR PD
  3.RECEIVE A STRING FROM DOINBACKGROUND
   */
    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);


        if (response != null) {
            editText.setText("");
            editText2.setText("");
            editText3.setText("");
            editText4.setText("");
            editText5.setText("");
            Toast.makeText(context, response, Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(context, "Unsuccessful " + response, Toast.LENGTH_LONG).show();
        }
    }

}
