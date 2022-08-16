package com.example.smartshopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class Cust_login extends AppCompatActivity implements JsonResponse{
    TextView t1,t2;
    EditText e1,e2;
    Button b1;
    ImageView img1;
    String username, password;
    String logid, type, checkb;
    SharedPreferences sh;
    String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_login);

        try {
            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
        } catch (Exception e) {
        }
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e1=(EditText)findViewById(R.id.editTextemail);
        e2=(EditText)findViewById(R.id.editTextpassword);
        b1=(Button)findViewById(R.id.cirLoginButton);
        img1=(ImageView) findViewById(R.id.backButton);
        t1=(TextView)findViewById(R.id.Newreg);
//        t2=(TextView)findViewById(R.id.ForgotPass);


        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                username = e1.getText().toString();
                password = e2.getText().toString();

                if (username.equals("")) {
                    e1.setError("Enter username");
                    e1.setFocusable(true);
                } else if (password.equals("")) {
                    e2.setError("Enter password");
                    e2.setFocusable(true);
                } else {
                    JsonReq JR = new JsonReq(getApplicationContext());
                    JR.json_response = (JsonResponse) Cust_login.this;
                    String q = "/login/?username=" + username + "&password=" + password;
                    JR.execute(q);
                }
            }
        });


        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Logo.class)); //back button
            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Cust_register.class)); //New registration button
            }
        });

//        t2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),Forgot_password.class)); //Forgot Password  button
//            }
//        });


    }
    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {

                JSONArray ja = (JSONArray) jo.getJSONArray("data");
                String login_id = ja.getJSONObject(0).getString("login_id");
                String type = ja.getJSONObject(0).getString("usertype");

                SharedPreferences.Editor ed = sh.edit();
                ed.putString("login_id", login_id);
                ed.commit();

                Toast.makeText(getApplicationContext(), "Welcome To Smart Shopie", Toast.LENGTH_LONG).show();
                if (type.equals("user")) {

                    startActivity(new Intent(getApplicationContext(), User_home.class));

                }
            } else {
                Toast.makeText(getApplicationContext(), "Login failed!!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Connection problem", Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Logo.class);
        startActivity(b);
    }
}