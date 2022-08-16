package com.example.smartshopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class Scan_result_view extends AppCompatActivity implements JsonResponse {
    TextView tv_prod_name, tv_price, tv_desc, tv_store;
    ImageView iv_product,backButton,CartButton;
    Button bt_add_cart;
    EditText ed_qty;

    SharedPreferences sh;
    String prod_id = "0", amount = "0",quantitut;
    int balance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result_view);

        tv_prod_name = (TextView)findViewById(R.id.tv_prod_name);
        tv_price = (TextView)findViewById(R.id.tv_price);
        tv_desc = (TextView)findViewById(R.id.tv_desc);
        tv_store = (TextView)findViewById(R.id.tv_store);
        iv_product = (ImageView)findViewById(R.id.iv_product);
        ed_qty = (EditText)findViewById(R.id.ed_qty);
        bt_add_cart = (Button)findViewById(R.id.bt_add_cart);

        backButton = (ImageView)findViewById(R.id.backButton);

        CartButton = (ImageView)findViewById(R.id.CartButton);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        JsonReq JR = new JsonReq(getApplicationContext());
        JR.json_response = (JsonResponse) Scan_result_view.this;
        String q = "/scan_product/?qrid=" + sh.getString("qrid", "");
        JR.execute(q);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), User_home.class));
            }
        });

        CartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Purchase_limit.class));
            }
        });

        bt_add_cart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int total = Integer.parseInt(ed_qty.getText().toString().trim()) * Integer.parseInt(amount.trim());
                balance = Integer.parseInt(sh.getString("balance", "0").trim());

                quantitut=ed_qty.getText().toString();

                int flaagg = 0;
                if (quantitut.equals("")) {
                    ed_qty.setError("Quantity Requied");
                    flaagg++;
                }
                if (balance > total) {
                    balance = balance - total;



                    if (flaagg == 0) {
                        JsonReq JR = new JsonReq(getApplicationContext());
                        JR.json_response = (JsonResponse) Scan_result_view.this;
                        String q = "/add_cart_new/?pid=" + prod_id + "&logid=" + sh.getString("login_id", "0") + "&qty=" + quantitut;
                        JR.execute(q);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Your shopping limit succeeded..!", Toast.LENGTH_LONG).show();
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
                if (jo.getString("method").equals("add_cart")) {
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("balance", "" + balance);
                    ed.commit();
                  startActivity(new Intent(getApplicationContext(), Cart_view.class));
                    Toast.makeText(getApplicationContext(), "Added to cart..!", Toast.LENGTH_LONG).show();
                } else if (jo.getString("method").equals("scan_product")) {
                    JSONArray ja = (JSONArray) jo.getJSONArray("data");

                    prod_id = ja.getJSONObject(0).getString("product_id");
                    amount = ja.getJSONObject(0).getString("amount");
                    tv_prod_name.setText(ja.getJSONObject(0).getString("product_name"));
                    tv_price.setText(ja.getJSONObject(0).getString("amount"));
                    tv_desc.setText(ja.getJSONObject(0).getString("description"));
                    tv_store.setText(ja.getJSONObject(0).getString("store_name"));
                    String img_path = "http://" + sh.getString("ip", "") + ja.getJSONObject(0).getString("image");
                    img_path = img_path.replace(" ", "%20");
                    Picasso.with(getApplicationContext()).load(img_path).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_complaint).into(iv_product);
//get()
                }
            } else {
                Toast.makeText(getApplicationContext(), "Failed..!!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), User_home.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Exc : " + e, Toast.LENGTH_LONG).show();
        }
    }


    }

