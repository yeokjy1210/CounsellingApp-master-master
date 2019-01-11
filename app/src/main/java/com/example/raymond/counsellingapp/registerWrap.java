package com.example.raymond.counsellingapp;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

public class registerWrap {
    String id, pass, name, email, dob;

    //Receive all data we wanted to send
    public registerWrap(String id, String pass, String name, String email, String dob) {
        this.id = id;
        this.pass = pass;
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    //Wrap into a Json Object
    public String WrapData() {

        JSONObject JO = new JSONObject();
        StringBuffer dataWrap = new StringBuffer();

        try {

            JO.put("studentID", id);
            JO.put("studentPass", pass);
            JO.put("studentName", name);
            JO.put("studentEmail", email);
            JO.put("studentDOB", dob);

            Boolean firstValue = true;
            Iterator it = JO.keys();

            do {
                String key = it.next().toString();
                String value = JO.get(key).toString();

                if (firstValue) {
                    firstValue = false;
                } else {
                    dataWrap.append("&");
                }

                dataWrap.append(URLEncoder.encode(key, "UTF-8"));
                dataWrap.append("=");
                dataWrap.append(URLEncoder.encode(value, "UTF-8"));
            } while (it.hasNext());

            return dataWrap.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;

    }
}


