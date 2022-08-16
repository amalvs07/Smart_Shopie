package com.example.smartshopping;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Create_pdf extends AppCompatActivity implements JsonResponse{
    String locname, logid, url;
    SharedPreferences sh;
    String[] SalesDate,StoreName,Cust_name,Cust_phone,Grand_total,Quantity,ProductsItems,ProductPrice;

    TextView date,nameofstore,nameofcustomer,phoneofcustomer,subtotal,Grandtotal;
    ListView listitems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pdf);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        logid = sh.getString("login_id", "0");
        url = sh.getString("url", "");
                    JsonReq JR = new JsonReq(getApplicationContext());
                    JR.json_response = (JsonResponse) Create_pdf.this;
                    String q = "/CREATE_PDF/?smid=" + View_bill.psmid + "&logid=" + logid;
//                            View_bill.psmid

                    JR.execute(q);


        date=(TextView)findViewById(R.id.SalesDate);
        nameofstore=(TextView)findViewById(R.id.Store_Name);
        nameofcustomer=(TextView)findViewById(R.id.Customer_Name);
        phoneofcustomer=(TextView)findViewById(R.id.Customer_phone);
        subtotal=(TextView)findViewById(R.id.Total_amount);
        Grandtotal=(TextView)findViewById(R.id.Grand_Total);

        listitems = (ListView) findViewById(R.id.Product_list_items);

    }
    void initarray(int len) {
        SalesDate = new String[len];
        StoreName = new String[len];
        Cust_name = new String[len];
        Cust_phone = new String[len];
        Grand_total = new String[len];
        Quantity=new String[len];
        ProductPrice=new String[len];
        ProductsItems = new String[len];
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
                        subtotal.setText ("₹"+ ""+ja.getJSONObject(0).getString("total_amount"));
                        Grandtotal.setText ( "₹"+""+ja.getJSONObject(0).getString("total_amount"));
                        date.setText ( ja.getJSONObject(0).getString("sales_date"));
                        nameofstore.setText (ja.getJSONObject(0).getString("store_name"));
                        nameofcustomer.setText (ja.getJSONObject(0).getString("first_name")+" "+ja.getJSONObject(0).getString("last_name"));
                        Quantity[i] = ja.getJSONObject(i).getString("quantity");
                        ProductsItems[i] = ja.getJSONObject(i).getString("product_name");
                        ProductPrice[i] = ja.getJSONObject(i).getString("productprice");


                    }




                    Create_pdf.MyAdapter adapter = new Create_pdf.MyAdapter(this,ProductPrice,ProductsItems,Quantity);
                    listitems.setAdapter(adapter);
                }


            } else {

//                Toast.makeText(getApplicationContext(), "failed!!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exc : " + e, Toast.LENGTH_LONG).show();
        }
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String  nAmount[];
        String  nProductitems[];
        String  nQuantity[];


        MyAdapter (Context c,  String samount[], String sproductname[], String sqty[]){

            super(c, R.layout.bill_items, R.id.ProductNames, sproductname);
            this.context = c;
//            this.nProductname = sname;
            this.nAmount = samount;
            this.nProductitems = sproductname;
            this.nQuantity = sqty;


        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.order_lists, null, true);
            TextView productName = row.findViewById(R.id.ProductNames);
            TextView productAmount = row.findViewById(R.id.ProductAmounts);
            TextView productQuantity = row.findViewById(R.id.ProductQuantity);



            productName.setText(nProductitems[position]);
            productAmount.setText(nAmount[position]);
            productQuantity.setText(nQuantity[position]);



            return row;
        }

    }
















    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), View_bill.class));
    }
}