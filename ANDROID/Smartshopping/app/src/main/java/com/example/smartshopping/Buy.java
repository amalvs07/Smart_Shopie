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

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class Buy extends AppCompatActivity implements AdapterView.OnItemClickListener,JsonResponse {
    ImageView qrscan, cart, store, more,refresh,purchase;
    String locname, logid, url;
    ListView lv;
    Button b1;
    TextView Total_amount,NameStore,DateSale;
    int images[]={R.drawable.shop1,R.drawable.shop2,R.drawable.shop3,R.drawable.shop4,R.drawable.shop5,R.drawable.shop6};
    public static String psmid,pamount,pstore;

    LinearLayout normal;
    ConstraintLayout empty;

    String[] total_amount,sales_date,store_name,details,smid,store_id,product_name;
    SharedPreferences sh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
//        Total_amount = (TextView) findViewById(R.id.Total_amount);
//        NameStore = (TextView) findViewById(R.id.NameStore);
//        DateSale = (TextView) findViewById(R.id.DateSale);

        normal=(LinearLayout)this.findViewById(R.id.NormalView);
        empty=(ConstraintLayout)this.findViewById(R.id.EmptyView);

        try {
            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
        } catch (Exception e) {
        }

        qrscan = (ImageView) findViewById(R.id.Qrscan);
        cart = (ImageView) findViewById(R.id.Cart);
        store = (ImageView) findViewById(R.id.Store);
        more = (ImageView) findViewById(R.id.Viewmore);
        refresh = (ImageView) findViewById(R.id.imageView6);
        purchase=(ImageView)findViewById(R.id.Purchase);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        logid = sh.getString("logid", "");
        url = sh.getString("url", "");
        lv = (ListView) findViewById(R.id.listView1);
        b1 = (Button) findViewById(R.id.button1);
        lv.setOnItemClickListener(this);

        JsonReq JR = new JsonReq(getApplicationContext());
        JR.json_response = (JsonResponse) Buy.this;
        String q = "/view_buy_new/?logid=" + sh.getString("login_id", "0");
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

        store.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(),Store_view.class));
            }
        });
//        purchase.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                startActivity(new Intent(getApplicationContext(), Buy.class));
//            }
//        });
        more.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(),Home_screen.class));
            }
        });
    }

    void initarray(int len) {
        total_amount = new String[len];
        sales_date = new String[len];
        store_name = new String[len];
        smid = new String[len];
        store_id = new String[len];
        product_name=new String[len];
        details = new String[len];
//        Tproductname =new String[len];
//        Tquantity =new String[len];

    }



    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        psmid = smid[arg2];
        pamount = total_amount[arg2];
        pstore = store_id[arg2];
        startActivity(new Intent(getApplicationContext(), Payment.class));
//        selectImageOption();

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
                        total_amount[i] = ja.getJSONObject(i).getString("total_amount");
                        sales_date[i] = ja.getJSONObject(i).getString("sales_date");
                        store_name[i] = ja.getJSONObject(i).getString("store_name");
//    					statuss[i] = ja.getJSONObject(i).getString("STATUS");
                        smid[i] = ja.getJSONObject(i).getString("sm_id");
                        store_id[i] = ja.getJSONObject(i).getString("store_id");

                        details[i] = "Total Amount : " + total_amount[i] + "\nSales Date : "
                                + sales_date[i] + "\nStore : " + store_name[i] + "\n";
                    }




                    Buy.MyAdapter adapter = new Buy.MyAdapter(this,total_amount,store_name,sales_date,images);
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

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String  nAmount[];
        String  nStorename[];
        String  nSalesDate[];
        int nImage[];

        MyAdapter (Context c,  String samount[], String sstorename[], String ssalesdate[],int imgs[]){

            super(c, R.layout.bill_items, R.id.storename, sstorename);
            this.context = c;
//            this.nProductname = sname;
            this.nAmount = samount;
            this.nStorename = sstorename;
            this.nSalesDate = ssalesdate;
            this.nImage = imgs;

        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.bill_items, null, true);
            ImageView images = row.findViewById(R.id.Productimages);
            TextView productName = row.findViewById(R.id.Product_names);
            TextView storeName = row.findViewById(R.id.storename);
            TextView productAmount = row.findViewById(R.id.TotalAmount);
            TextView salesDate = row.findViewById(R.id.SalesDate);

//            productName.setText(nProductname[position]);
            storeName.setText(nStorename[position]);
            productAmount.setText(nAmount[position]);
            salesDate.setText(nSalesDate[position]);
            images.setImageResource(nImage[position]);


            return row;
        }

    }
















    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), User_home.class));
    }
}