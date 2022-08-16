package com.example.smartshopping;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Complaint_view extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    String  logid, url;
    ListView lv;
    Button b1;
    ImageView img1,refresh;
    int images[]={R.drawable.shop1,R.drawable.shop2,R.drawable.shop3,R.drawable.shop4,R.drawable.shop5,R.drawable.shop6};
    public static String psmid,pamount,pstore;

    String[] store_name,store_id,store_phone,store_email;
    SharedPreferences sh;

    LinearLayout normal;
    ConstraintLayout empty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_view);

        final  Loading_dialog loading_dialog =new Loading_dialog(Complaint_view.this);
        normal=(LinearLayout)this.findViewById(R.id.NormalView);
        empty=(ConstraintLayout)this.findViewById(R.id.EmptyView);

//        empty.setVisibility(ConstraintLayout.GONE);

        img1 = (ImageView) findViewById(R.id.backslash);
        refresh = (ImageView) findViewById(R.id.imageView6);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading_dialog.startLoadingDialog();
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loading_dialog.dismissDialog();
                        Intent intent=new Intent(Complaint_view.this,Complaint_view.class);
                        startActivity(intent);
                        finish();
                    }
                },5000);
            }
        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home_screen.class));
            }
        });


        try {
            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
        } catch (Exception e) {
        }

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        logid = sh.getString("logid", "");
        url = sh.getString("url", "");
        lv = (ListView) findViewById(R.id.Complaint_store_lists);
        b1=(Button)findViewById(R.id.button1);
        lv.setOnItemClickListener(this);

        JsonReq JR = new JsonReq(getApplicationContext());
        JR.json_response = (JsonResponse) Complaint_view.this;
        String q = "/store_view/?logid=" + sh.getString("login_id", "0");
        JR.execute(q);
    }

    void initarray(int len) {

        store_name = new String[len];
        store_phone = new String[len];
        store_id = new String[len];
        store_email = new String[len];

    }



    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        pstore = store_id[arg2];

        startActivity(new Intent(getApplicationContext(), Complaint.class));
    }


    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String  nStorename[];
        String  nStoreemail[];
        String  nStorephone[];
        int nImage[];

        MyAdapter (Context c, String sname[], String semail[], String sphone[],int imgs[]) {

            super(c, R.layout.store_items, R.id.store_name, sname);
            this.context = c;
            this.nStorename = sname;
            this.nStoreemail = semail;
            this.nStorephone = sphone;
            this.nImage = imgs;

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

            images.setImageResource(nImage[position]);


            storeName.setText(nStorename[position]);
            storeEmail.setText(nStoreemail[position]);
            storePhone.setText(nStorephone[position]);

            return row;
        }

    }






    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
                JSONArray ja = (JSONArray) jo.getJSONArray("data");
                if (ja.length() > 0) {
                    initarray(ja.length());
                    for (int i = 0; i < ja.length(); i++) {
                        store_name[i] = ja.getJSONObject(i).getString("store_name");
                        store_id[i] = ja.getJSONObject(i).getString("store_id");
                        store_phone[i] = ja.getJSONObject(i).getString("phone");
                        store_email[i] = ja.getJSONObject(i).getString("email");

                    }
                    Complaint_view.MyAdapter adapter = new Complaint_view.MyAdapter(this, store_name, store_email,store_phone,images);
                    lv.setAdapter(adapter);
                }

            } else {
                normal.setVisibility(LinearLayout.GONE);
                empty.setVisibility(ConstraintLayout.VISIBLE);
//                Toast.makeText(getApplicationContext(), "failed!!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exc : " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Home_screen.class));
    }

}