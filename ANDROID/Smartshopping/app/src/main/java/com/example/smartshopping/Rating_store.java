package com.example.smartshopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class Rating_store extends AppCompatActivity implements JsonResponse{
    RatingBar rb;
    Button b;
    SharedPreferences sh;
String store_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_store);



        store_id=getIntent().getStringExtra("STORE_ID");

        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        rb=(RatingBar)findViewById(R.id.ratingBar1);
        b=(Button)findViewById(R.id.button1);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String rate=String.valueOf(rb.getRating());
                JsonReq JR = new JsonReq(getApplicationContext());
                JR.json_response = (JsonResponse) Rating_store.this;
                String q = "/rate/?logid=" + sh.getString("login_id", "0") + "&rating=" + rate + "&store_id=" +  store_id;
//                        Buy.pstore

                JR.execute(q);
            }
        });
    }



    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
//                Toast.makeText(getApplicationContext(),"Success..",Toast.LENGTH_LONG).show();

//                LayoutInflater rating =getLayoutInflater();
//                View rat =rating.inflate(R.layout.custom_toast_rating,(ViewGroup)findViewById(R.id.Rating_toast));
//                final Toast toast4=new Toast(getApplicationContext());
//                toast4.setGravity(Gravity.CENTER_VERTICAL,10,400);
//                toast4.setDuration(Toast.LENGTH_SHORT);
//                toast4.setView(rat);
//
//                toast4.show();

//                final  Rating_load rating_load =new Rating_load(Rating_store.this);
//                rating_load.startLoadingDialog1();
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        rating_load.dismissDialog1();
//
////                startActivity(new Intent(getApplicationContext(),Pdf_maker.class));
////                finish();
                       startActivity(new Intent(getApplicationContext(), User_home.class));
//                        finish();
//                    }
//                },5000);





            } else {
                Toast.makeText(getApplicationContext(), "Failed!!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exc : " + e, Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent b = new Intent(getApplicationContext(), Rating_store.class);
        startActivity(b);
    }
}