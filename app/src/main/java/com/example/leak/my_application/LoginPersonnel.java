package com.example.leak.my_application;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;


public class LoginPersonnel extends AppCompatActivity {

    private EditText username, password;
    private Button sign_in;
    private RequestQueue requestQueue;
    private static final String URL = "https://geodoc.000webhostapp.com/BackEnd/personnel_control.php";
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_personnel);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        sign_in = (Button) findViewById(R.id.sign_in);

        requestQueue = Volley.newRequestQueue(this);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ResponseVolley",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {

                                Intent intent = new Intent(LoginPersonnel.this,MenuPersonnel.class);
                                intent.putExtra("username", username.getText().toString());
                                intent.putExtra("number",jsonObject.getInt("nbrP"));
                                int num=jsonObject.getInt("nbrP");
                                JSONObject pat = jsonObject.getJSONObject("patients");
                                JSONArray p;
                               String [] users=new String[num];
                                int [] x=new int[num];
                                int [] y=new int[num];
                                for(int i=0;i<num;i++){
                                    p = pat.getJSONArray(i+"");
                                    users[i]=p.getString(0);
                                    x[i]=p.getInt(1);
                                    y[i]= p.getInt(2);


                                }
                                intent.putExtra("users",users);
                                intent.putExtra("x",x);
                                 intent.putExtra("y",y);
                                Toast.makeText(getApplicationContext(), jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                LoginPersonnel.this.startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
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
                        hashMap.put("username", username.getText().toString());
                        hashMap.put("password", password.getText().toString());
                        return hashMap;
                    }
                };

                requestQueue.add(request);
            }
        });
    }
}
