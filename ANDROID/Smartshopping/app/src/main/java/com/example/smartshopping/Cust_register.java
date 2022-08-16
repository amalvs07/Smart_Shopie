package com.example.smartshopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class Cust_register extends AppCompatActivity implements JsonResponse {
EditText e1,e2,e3,e4,e5,e6,e7,e8;
TextView t1;
Button b1;
ImageView img1;
    SharedPreferences sh;

    String username, password, fname, lname, hname, pincode, place, phone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_register);

        startService(new Intent(getApplicationContext(), LocationService.class));

        e1=(EditText)findViewById(R.id.FirstName);
        e2=(EditText)findViewById(R.id.LastName);
        e3=(EditText)findViewById(R.id.HouseName);
        e4=(EditText)findViewById(R.id.PlaceName);
        e5=(EditText)findViewById(R.id.Pincode);
        e6=(EditText)findViewById(R.id.EmailID);
        e7=(EditText)findViewById(R.id.PasswordReg);
        e8=(EditText)findViewById(R.id.PhonNo);
        t1=(TextView) findViewById(R.id.LoginText);
        b1=(Button)findViewById(R.id.RegisterButton);
        img1=(ImageView) findViewById(R.id.backButtonReg);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Cust_login.class)); ////New login  button
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Logo.class)); //back button
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                fname = e1.getText().toString();
                lname = e2.getText().toString();
                hname = e3.getText().toString();
                place = e4.getText().toString();
                pincode = e5.getText().toString();
                phone = e8.getText().toString();
                email = e6.getText().toString();
                password = e7.getText().toString();

                int flg = 0;
                if (fname.equals("")) {
                    e1.setError("First name please");
                    flg++;
                }
                if (lname.equals("")) {
                    e2.setError("Last name please");
                    flg++;
                }
                if (hname.equals("")) {
                    e3.setError("House name please");
                    flg++;
                }
                if (place.equals("")) {
                    e4.setError("Place please");
                    flg++;
                }
                if (pincode.length() != 6) {
                    e5.setError("Valid pincode please");
                    flg++;
                }
                if (phone.length() != 10) {
                    e8.setError("Valid phone number");
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    e6.setError("Valid email please");
                    flg++;
                }
                if (password.equals("")) {
                    e7.setError("Password please");
                    flg++;
                }

                if (flg == 0) {
                    JsonReq JR = new JsonReq(getApplicationContext());
                    JR.json_response = (JsonResponse) Cust_register.this;
                    String q = "/register/?first_name=" + fname + "&last_name=" + lname + "&house_name=" + hname
                            + "&place=" + place + "&pincode=" + pincode + "&phone=" + phone + "&email=" + email
                            + "&latitude=" + LocationService.lati + "&longitude=" + LocationService.logi
                            + "&password=" + password;
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
                Toast.makeText(getApplicationContext(), "Registration success.!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Cust_login.class));
            } else {
                Toast.makeText(getApplicationContext(), "Failed..!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exc : " + e, Toast.LENGTH_LONG).show();
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