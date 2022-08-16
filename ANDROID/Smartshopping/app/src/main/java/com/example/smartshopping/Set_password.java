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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Set_password extends AppCompatActivity implements JsonResponse{
    Button b2;
    EditText e1,e2;
    ImageView img1;
    String password,cpassword,logid;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        logid = sh.getString("forgot_id", "0");

        e1=(EditText)findViewById(R.id.etpassword);
        e2=(EditText)findViewById(R.id.etconfirmpassword);
        b2=(Button)findViewById(R.id.setnewpass);
        img1=(ImageView) findViewById(R.id.backButton);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Forgot_password.class)); //back button
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = e1.getText().toString();
                cpassword = e2.getText().toString();
                if (!password.equals(cpassword)) {
                    e1.setError("Passwords are not same");
                    e1.setFocusable(true);
                }
                else {
                    JsonReq JR = new JsonReq(getApplicationContext());
                    JR.json_response = (JsonResponse) Set_password.this;
                    String q = "/Set_password/?password=" + password +"&login_id="+logid;
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

                JSONArray ja = (JSONArray) jo.getJSONArray("data");
                Toast.makeText(getApplicationContext(), "New password Set successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Cust_login.class));
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
        Intent b=new Intent(getApplicationContext(),Forgot_password.class);
        startActivity(b);
    }
}