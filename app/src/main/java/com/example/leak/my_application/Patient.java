package com.example.leak.my_application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by jihane on 07/04/17.
 */

public class Patient{

    private String patientName;
    private int x,y;
    private static final String URL = "https://geodoc.000webhostapp.com/BackEnd/getCoordinate.php";
    private StringRequest request;
    private RequestQueue requestQueue;


    public Patient(){
        patientName="Hello";
        x=0;
        y=5;

    }
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void update() {
        setX(this.getX()+10000000);
        setY(this.getY()+10000000);
       /*request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    setX(jsonObject.getInt("x"));
                    setY(jsonObject.getInt("y"));
                    Log.d("ResponseVolley", String.valueOf(y)+"hello");


                } catch (JSONException e) {
                    e.printStackTrace();

                }


            }

        }, new Response.ErrorListener() {


        @Override
        public void onErrorResponse(VolleyError error) {

            Log.d("ResponseVolley", String.valueOf(y)+"helloError");

        }
    }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("patientName", patientName);
            return hashMap;
        }
    };*/
    //request

}

    public String getPatientName() {
        return patientName;
    }


    public int getX() {
        return x;
    }

    public int setX(int x) {
        this.x = x;
        return x;
    }

    public int getY() {
        return y;
    }

    public int setY(int y) {
        this.y = y;
        return y;
    }

}
