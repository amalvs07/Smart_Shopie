package com.example.smartshopping;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONArray;
import org.json.JSONObject;

public class Store_view extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    Button refresh,purchase;
    ImageButton qrscan, cart, store, more;
    ListView listView;
    String[] store_names, latitudes, longitudes,storemail,storephone;
    int images[]={R.drawable.shop1,R.drawable.shop2,R.drawable.shop3,R.drawable.shop4,R.drawable.shop5,R.drawable.shop6};
//    int backimg[]={R.drawable.bgcolor1,R.drawable.bgcolor2};

    LinearLayout normal;
    ConstraintLayout empty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_view);

        final  Loading_dialog loading_dialog =new Loading_dialog(Store_view.this);

        normal=(LinearLayout)this.findViewById(R.id.NormalView);
        empty=(ConstraintLayout)this.findViewById(R.id.EmptyView);

//        empty.setVisibility(ConstraintLayout.GONE);

        listView = findViewById(R.id.store_lists);

        qrscan = (ImageButton) findViewById(R.id.Qrscan);
        cart = (ImageButton) findViewById(R.id.Cart);
        store = (ImageButton) findViewById(R.id.Store);
        more = (ImageButton) findViewById(R.id.Viewmore);
        refresh = (Button) findViewById(R.id.imageView6);
//        purchase=(ImageView)findViewById(R.id.Purchase);

        refresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                loading_dialog.startLoadingDialog();
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loading_dialog.dismissDialog();
                        startService(new Intent(getApplicationContext(), LocationService.class));
                        Intent intent=new Intent(Store_view.this,Store_view.class);
                        startActivity(intent);

                        finish();
                    }
                },5000);

            }
        });

        startService(new Intent(getApplicationContext(), LocationService.class));


        qrscan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(),User_home.class));
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), Cart_view.class));
            }
        });

//        purchase.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                startActivity(new Intent(getApplicationContext(), Buy.class));
//            }
//        });

//        store.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                startActivity(new Intent(getApplicationContext(), Store_view.class));
//            }
//        });

        more.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), Home_screen.class));
            }
        });



        listView.setOnItemClickListener(this);

        JsonReq JR = new JsonReq(getApplicationContext());
        JR.json_response = (JsonResponse) Store_view.this;
        String q = "/nearby_stores/?latitude=" +LocationService.lati + "&longitude=" + LocationService.logi;
        JR.execute(q);



    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {

                JSONArray ja = (JSONArray) jo.getJSONArray("data");
                if (ja.length() > 0) {
                    store_names = new String[ja.length()];
                    latitudes = new String[ja.length()];
                    longitudes = new String[ja.length()];
                    storemail = new String[ja.length()];
                    storephone = new String[ja.length()];

                    for (int i = 0; i < ja.length(); i++) {
                        store_names[i] = ja.getJSONObject(i).getString("store_name");
                        latitudes[i] = ja.getJSONObject(i).getString("latitude");
                        longitudes[i] = ja.getJSONObject(i).getString("longitude");
                        storemail[i] = ja.getJSONObject(i).getString("email");
                        storephone[i] = ja.getJSONObject(i).getString("phone");
                    }

//                    listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list, store_names));
                    MyAdapter adapter = new MyAdapter(this, store_names, storemail,storephone,images);
                    listView.setAdapter(adapter);
                }
            } else {

                normal.setVisibility(LinearLayout.GONE);
                empty.setVisibility(ConstraintLayout.VISIBLE);

//                Toast.makeText(getApplicationContext(), "No stores nearby you..!", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(getApplicationContext(), View_scan_result.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Exc : " + e, Toast.LENGTH_LONG).show();
        }
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String  nStorename[];
        String  nStoreemail[];
        String  nStorephone[];
        int nImage[];
        //        int nBackimg[];
        MyAdapter (Context c, String sname[], String semail[], String sphone[],int imgs[]) {

            super(c, R.layout.store_items, R.id.store_name, sname);
            this.context = c;
            this.nStorename = sname;
            this.nStoreemail = semail;
            this.nStorephone = sphone;
            this.nImage = imgs;
//            this.nBackimg=backim;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.store_items, null, true);
            ImageView images = row.findViewById(R.id.shopdemoimages);
            TextView storeName = row.findViewById(R.id.store_name);
            TextView storeEmail = row.findViewById(R.id.store_mail);
            TextView storePhone = row.findViewById(R.id.store_phone);

//            ImageView backimages = row.findViewById(R.id.carview);

            // now set our resources on views
            images.setImageResource(nImage[position]);
//            backimages.setImageResource(nImage[position]);

            storeName.setText(nStorename[position]);
            storeEmail.setText(nStoreemail[position]);
            storePhone.setText(nStorephone[position]);

            return row;
        }

    }



    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        String url = "http://www.google.com/maps?saddr=" + LocationService.lati + "" + "," + LocationService.logi + ""
                + "&&daddr=" + latitudes[arg2] + "," + longitudes[arg2];
        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(in);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), User_home.class));
    }
}