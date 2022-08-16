package com.example.smartshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Forgot_password extends AppCompatActivity implements JsonResponse{
Button b1;
EditText e1;
ImageView img1;
String username;
SharedPreferences sh;
    String login_id,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        e1=(EditText)findViewById(R.id.etEmailAddress);
        b1=(Button)findViewById(R.id.Verify);
        img1=(ImageView) findViewById(R.id.backButton);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Cust_login.class)); //back button
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = e1.getText().toString();
                if (username.equals("")) {
                    e1.setError("Enter username");
                    e1.setFocusable(true);
                }
                else {
                    JsonReq JR = new JsonReq(getApplicationContext());
                    JR.json_response = (JsonResponse) Forgot_password.this;
                    String q = "/Forgot_password/?username=" + username ;
                    JR.execute(q);
                }
            }
        });

    }


    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
//                if (jo.getString("method").equals("Forgot_password")) {
                    JSONArray ja = (JSONArray) jo.getJSONArray("data");
                    String login_id = ja.getJSONObject(0).getString("login_id");
                    String type = ja.getJSONObject(0).getString("usertype");
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("forgot_id", login_id);
                    Toast.makeText(getApplicationContext(), "sucess!!", Toast.LENGTH_LONG).show();
                    ed.commit();

                if (type.equals("user")) {

                    startActivity(new Intent(getApplicationContext(), Set_password.class));

                }else {
                    Toast.makeText(getApplicationContext(), "Username is invalid!!", Toast.LENGTH_LONG).show();
                }
//                }
            } else {
                Toast.makeText(getApplicationContext(), "failed!!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Connection problem", Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Cust_login.class);
        startActivity(b);
    }
}