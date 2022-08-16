package com.example.smartshopping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class Complaint extends AppCompatActivity implements JsonResponse {

    EditText e1, e2;
    Button b1;
    ListView l1;
    ImageView img1;
    String title, desc, logid;
    String[] titles, complaints, replys, date, store_name, details;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);


        e1 = (EditText) findViewById(R.id.id_complaint);

        img1 = (ImageView) findViewById(R.id.backSlash);
    img1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), Complaint_view.class));
    }
    });
        b1 = (Button) findViewById(R.id.compliant);
        l1 = (ListView) findViewById(R.id.Complaint_lists);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        logid = sh.getString("login_id", "0");

        getPrevComplaints();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                title = e1.getText().toString();
                if (title.equals("")) {
                    e1.setError("Fill the field");
                } else {
                    JsonReq JR = new JsonReq(getApplicationContext());
                    JR.json_response = (JsonResponse) Complaint.this;
                    String q = "/user_send_complaint/?logid=" + logid + "&title=" + title + "&store_id=" +Complaint_view.pstore;
                    JR.execute(q);
                }
            }
        });
    }

    void initarray(int len) {
        titles = new String[len];
        replys = new String[len];
        store_name = new String[len];
        date = new String[len];
        details = new String[len];
    }


    void getPrevComplaints() {
        JsonReq JR = new JsonReq(getApplicationContext());
        JR.json_response = (JsonResponse) Complaint.this;
        String q = "/user_view_complaint/?logid=" + logid;
        JR.execute(q);
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String  nStorename[];
        String  nComplaintDescription[];
        String  nComplaintDate[];
        String  nComplaintReply[];


        MyAdapter (Context c, String sname[], String scomplaint[], String sdate[],String sreply[]) {

            super(c, R.layout.complaint_items, R.id.store_name, sname);
            this.context = c;
            this.nStorename = sname;
            this.nComplaintDescription = scomplaint;
            this.nComplaintDate = sdate;
            this.nComplaintReply = sreply;
//            this.nBackimg=backim;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.complaint_items, parent, false);


            TextView storeName = row.findViewById(R.id.storename);
            TextView storeDescription = row.findViewById(R.id.Complaint_description);
            TextView storeReply = row.findViewById(R.id.ReplyComplaint);
            TextView storeDate = row.findViewById(R.id.ComplaintDate);




            storeName.setText(nStorename[position]);
            storeDescription.setText(nComplaintDescription[position]);
            storeDate.setText(nComplaintDate[position]);
            storeReply.setText(nComplaintReply[position]);

            return row;
        }

    }
    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
                if (jo.getString("method").equals("user_send_complaint")) {

                    Toast.makeText(getApplicationContext(), "Complaint Added Successfully..!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Complaint.class));
                } else if (jo.getString("method").equals("user_view_complaint")) {
                    JSONArray ja = (JSONArray) jo.getJSONArray("data");
                    if (ja.length() > 0) {
                        initarray(ja.length());
                        for (int i = 0; i < ja.length(); i++) {
                            titles[i] = ja.getJSONObject(i).getString("complaint_description");
                            store_name[i] = ja.getJSONObject(i).getString("store_name");
                            replys[i] = ja.getJSONObject(i).getString("reply_description");
                            date[i] = ja.getJSONObject(i).getString("complaint_date");

                        }

                        Complaint.MyAdapter adapter = new Complaint.MyAdapter(this, store_name, titles,date,replys);
                        l1.setAdapter(adapter);
                    }
                }
            }
            else {
//                Toast.makeText(getApplicationContext(), "Failed..!!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Exc : " + e, Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent b = new Intent(getApplicationContext(), Complaint_view.class);
        startActivity(b);
    }

}
