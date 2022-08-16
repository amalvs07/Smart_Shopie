package com.example.smartshopping;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class Home_screen extends AppCompatActivity implements JsonResponse{
    TextView t1,t2;
    ImageButton qrscan,cart,store,more,purchaseBuy;
    TableRow purchase,bill,logout,compalint;

    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        qrscan=(ImageButton)findViewById(R.id.Qrscan);
        cart=(ImageButton)findViewById(R.id.Cart);
        store=(ImageButton)findViewById(R.id.Store);
        more=(ImageButton)findViewById(R.id.Viewmore);
//        purchaseBuy=(ImageView)findViewById(R.id.Purchase);


        purchase=(TableRow)findViewById(R.id.PurchaseLimit);
        bill=(TableRow)findViewById(R.id.ViewBill);
        compalint=(TableRow)findViewById(R.id.ComplaintView);
        logout=(TableRow)findViewById(R.id.Logout);

        t1=(TextView)findViewById(R.id.User_Name);
        t2=(TextView)findViewById(R.id.User_phone_No);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        JsonReq JR = new JsonReq(getApplicationContext());
        JR.json_response = (JsonResponse) Home_screen.this;
        String q = "/user_details/?login_id=" + sh.getString("login_id", "");
        JR.execute(q);





        qrscan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), User_home.class));
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), Cart_view.class));
            }
        });
//        purchaseBuy.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                startActivity(new Intent(getApplicationContext(), Buy.class));
//            }
//        });
        store.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), Store_view.class));
            }
        });

//        more.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                startActivity(new Intent(getApplicationContext(), Home_screen.class));
//
//            }
//        });

        purchase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), Purchase_limit.class));
            }
        });

        bill.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), View_bill.class));
            }
        });

        compalint.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), Complaint_view.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectImageOption();
            }
        });


    }




    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
                if (jo.getString("method").equals("user_details")) {

                    JSONArray ja = (JSONArray) jo.getJSONArray("data");

                    t1.setText(ja.getJSONObject(0).getString("first_name")+" "+ja.getJSONObject(0).getString("last_name"));
                    t2.setText(ja.getJSONObject(0).getString("phone"));

                }
            } else {
                Toast.makeText(getApplicationContext(), "Failed..!!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Home_screen.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Exc : " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), User_home.class));
    }

    private void selectImageOption() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home_screen.this);
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes",new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface arg0, int arg1)
            {
                startActivity(new Intent(getApplicationContext(), Cust_login.class));
            }
        }).setNegativeButton("No",null);
        builder.show();
    }
}