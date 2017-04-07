package com.example.leak.my_application;

import android.util.Log;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jihane on 07/04/17.
 */

public class Patient {

    private String patientName;
    private int x,y;
    private static final String URL = "https://geodoc.000webhostapp.com/BackEnd/getCoordinate.php";
    private StringRequest request;
    ProgressBar bar;


    public Patient(){
        patientName="Hello";
        x=0;
        y=5;

    }
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void update() {

        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("ResponseVolley",response);
                    setX(jsonObject.getInt("x"));
                    setY(jsonObject.getInt("y"));

                } catch (JSONException e) {
                    e.printStackTrace();

                }


            }

        }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("patientName", patientName);
            return hashMap;
        }
    };

}

    public String getPatientName() {
        return patientName;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
