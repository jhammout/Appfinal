package com.example.leak.my_application;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.leak.my_application.R.id.username;

/**
 * Created by jihane on 07/04/17.
 */

public class Patient {

    private String patientName="Hello";
    private int x =0,y=0;
    private static final String URL = "https://geodoc.000webhostapp.com/BackEnd/getCoordinate.php";
    private StringRequest request;

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void update(final String patientName) {
        this.patientName = patientName ;
        final int [] pos =new int[2];
        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
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