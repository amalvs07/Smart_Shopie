package com.example.smartshopping;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class Cart_view extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ImageButton qrscan,cart,store,more,close,emmptyimg,purchase;

FloatingActionButton TrashBtn;
    String locname, logid, url;
    ListView lv;
    Button b1;
    int tt;
    LinearLayout normal;
    ConstraintLayout empty;

    int grandtotalamount=0;
    TextView grandtotal;
    int img1[]={R.drawable.shop1,R.drawable.shop2,R.drawable.shop3,R.drawable.shop4,R.drawable.shop5,R.drawable.shop6};
    public static String pcart, pstore, aamount, qty,remove;
    String[] prod_name, amount, quantity, details, prod_id, cart_id,store_id,Picture,tamt,tqty,store_name;
    SharedPreferences sh,total_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);

        normal=(LinearLayout)this.findViewById(R.id.NormalView);
        empty=(ConstraintLayout)this.findViewById(R.id.EmptyView);

        TrashBtn=(FloatingActionButton)this.findViewById(R.id.TrashButton);
//        empty.setVisibility(ConstraintLayout.GONE);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        logid = sh.getString("login_id", "0");

        lv = (ListView) findViewById(R.id.cart_lists);
        b1=(Button)findViewById(R.id.button1);
        lv.setOnItemClickListener(this);
//        b1.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
////                JsonReq JR = new JsonReq(getApplicationContext());
////                JR.json_response = (JsonResponse) Cart_view.this;
////                String q = "/buy/?logid=" + logid;
////                JR.execute(q);
//                //new imported
//                Intent intent =new Intent(getBaseContext(),Payment.class);
//                intent.putExtra("Total_amount_total",tt);
//                startActivity(intent);
//
//            }
//        });

        JsonReq JR = new JsonReq(getApplicationContext());
        JR.json_response = (JsonResponse) Cart_view.this;
        String q = "/NEW_Scan_pro/?logid=" + logid;
        q.replace("", "%20");
        JR.execute(q);

        qrscan=(ImageButton)findViewById(R.id.Qrscan);
        cart=(ImageButton)findViewById(R.id.Cart);
        store=(ImageButton)findViewById(R.id.Store);
        more=(ImageButton)findViewById(R.id.Viewmore);
//        close=(ImageView)findViewById(R.id.remove_product_cart);


//        purchase=(ImageView)findViewById(R.id.Purchase);

//        emmptyimg=(ImageView)findViewById(R.id.Emptyimage);



        grandtotal=(TextView) findViewById(R.id.Total_amounttt);


        qrscan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), User_home.class));
            }
        });

//        cart.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                startActivity(new Intent(getApplicationContext(), Cart_view.class));
//            }
//        });

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

        TrashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Cart_view.this);

                builder.setMessage("Do you want to remove all Products?");
                builder.setPositiveButton("Remove All",new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        JsonReq JR = new JsonReq(getApplicationContext());
                        JR.json_response = (JsonResponse) Cart_view.this;
                        String q = "/Delete_all/?logid=" + logid;
                        JR.execute(q);            }
                }).setNegativeButton("Cancel",null);
                builder.show();
            }
        });
    }


    void initarray(int len) {
        prod_name = new String[len];
        amount = new String[len];
        quantity = new String[len];
        prod_id = new String[len];
        cart_id = new String[len];
        store_id = new String[len];
        details = new String[len];
        Picture= new String[len];
        tamt = new String[len];
        tqty= new String[len];
        store_name= new String[len];
    }


    class MyAdapter extends ArrayAdapter<String> {
//        Context context;
        private Activity context;
        String  nProductname[];
        String  nAmount[];
        String  nQuantity[];
        String nImage[];
        String nCart[];
        String nStorename[];

        MyAdapter (Activity context, String sname[], String samount[], String squantity[], String imgs[],String cart[],String storename[]) {

            super(context, R.layout.cart_items, R.id.ProductimagesCart, imgs);
            this.context = context;
            this.nProductname = sname;
            this.nAmount = samount;
            this.nQuantity = squantity;
            this.nImage = imgs;
            this.nCart = cart;
            this.nStorename = storename;

        }
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

//            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            LayoutInflater layoutInflater = context.getLayoutInflater();

            View row = layoutInflater.inflate(R.layout.cart_items, null, true);
            ImageView images = row.findViewById(R.id.ProductimagesCart);
//new imported code


            ImageButton removeButton=row.findViewById(R.id.remove_product_cart);
            removeButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
//                            Toast.makeText( context,
//                                    "ImageButton clicked"+nCart[position],
//                                    Toast.LENGTH_SHORT).show();

                            selectImageOption(Integer.parseInt(nCart[position]),nQuantity[position],nAmount[position]);
                        }
                    });
     //end of code
            TextView productName = row.findViewById(R.id.Product_names);
            TextView storeName = row.findViewById(R.id.StoreName);
            TextView ProductQuantity = row.findViewById(R.id.QuantityProduct);
            TextView productAmount = row.findViewById(R.id.AmountProduct);

            productName.setText(nProductname[position]);
            storeName.setText(nStorename[position]);
            ProductQuantity.setText(nQuantity[position]);
            productAmount.setText(nAmount[position]);

//            Toast.makeText(getContext(), nProductname[position], Toast.LENGTH_LONG).show();

            sh=PreferenceManager.getDefaultSharedPreferences(getContext());

            String pth = "http://"+sh.getString("ip", "")+nImage[position];
            pth = pth.replace("~", "%20");

            Log.d("-------------", pth);
            Picasso.with(context).load(pth).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_baseline_error_).into(images);
            return row;
        }

    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
                if (jo.getString("method").equals("buy")) {


//                    startActivity(new Intent(getApplicationContext(), Buy.class));
                    Toast.makeText(getApplicationContext(), "Success..!", Toast.LENGTH_LONG).show();
                } else if (jo.getString("method").equals("NEW_Scan_pro")) {
                    JSONArray ja = (JSONArray) jo.getJSONArray("data");
                    if (ja.length() > 0) {
                        initarray(ja.length());

                        for (int i = 0; i < ja.length(); i++) {
                            prod_name[i] = ja.getJSONObject(i).getString("product_name");
                            amount[i] = ja.getJSONObject(i).getString("amount");
                            quantity[i] = ja.getJSONObject(i).getString("quantity");
                            prod_id[i] = ja.getJSONObject(i).getString("product_id");
                            cart_id[i] = ja.getJSONObject(i).getString("cart_id");
                            store_id[i] = ja.getJSONObject(i).getString("store_id");
                            store_name[i] = ja.getJSONObject(i).getString("store_name");
                            Picture[i]= ja.getJSONObject(i).getString("image");
                        }
                        sum(ja.length(),amount,quantity);
                        MyAdapter adapter = new MyAdapter(this, prod_name, amount,quantity,Picture,cart_id,store_name);
                        lv.setAdapter(adapter);
                        TrashBtn.setVisibility(FloatingActionButton.GONE);
                    }
                } else if (jo.getString("method").equals("remove_cart")) {
                    int balance = Integer.parseInt(sh.getString("balance", "0").trim());
                    int amn = Integer.parseInt(aamount.trim());
                    int total = amn * Integer.parseInt(qty.trim());
                    balance = balance + total;

                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("balance", "" + balance);
                    ed.commit();
                    Toast.makeText(getApplicationContext(), "Deleted..!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Cart_view.class));
                }else {
                    Toast.makeText(getApplicationContext(), "off Failed..!!", Toast.LENGTH_LONG).show();
                }
            }



            else if (status.equalsIgnoreCase("duplicate")){

                if (jo.getString("method").equals("NEW_Scan_pro")) {
                    JSONArray ja = (JSONArray) jo.getJSONArray("data");
                    if (ja.length() > 0) {
                        initarray(ja.length());

                        for (int i = 0; i < ja.length(); i++) {
                            prod_name[i] = ja.getJSONObject(i).getString("product_name");
                            amount[i] = ja.getJSONObject(i).getString("amount");
                            quantity[i] = ja.getJSONObject(i).getString("quantity");
                            prod_id[i] = ja.getJSONObject(i).getString("product_id");
                            cart_id[i] = ja.getJSONObject(i).getString("cart_id");
                            store_id[i] = ja.getJSONObject(i).getString("store_id");
                            store_name[i] = ja.getJSONObject(i).getString("store_name");
                            Picture[i]= ja.getJSONObject(i).getString("image");
                        }
                        sum_pro(ja.length(),amount,quantity);
                        MyAdapter adapter = new MyAdapter(this, prod_name, amount,quantity,Picture,cart_id,store_name);
                        lv.setAdapter(adapter);
                        TrashBtn.setVisibility(FloatingActionButton.VISIBLE);
//                        Toast.makeText(getApplicationContext(), "You Cannot Purchase  From Different Shop At Same Time .You Have to Empty Your cart!!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        normal.setVisibility(LinearLayout.GONE);
                        empty.setVisibility(ConstraintLayout.VISIBLE);
                    }
                } else if (jo.getString("method").equals("remove_cart")) {
                    int balance = Integer.parseInt(sh.getString("balance", "0").trim());
                    int amn = Integer.parseInt(aamount.trim());
                    int total = amn * Integer.parseInt(qty.trim());
                    balance = balance + total;

                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("balance", "" + balance);
                    ed.commit();
                    Toast.makeText(getApplicationContext(), "Deleted..!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Cart_view.class));
                }
                else if (jo.getString("method").equals("Delete_all")){
                    int balance = Integer.parseInt(sh.getString("balance", "0").trim());
                    int total = tt;
                    balance = balance + total;
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("balance", "" + balance);
                    ed.commit();
                    Toast.makeText(getApplicationContext(), "Cart is Empty..!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Cart_view.class));
                }
                else {
                    Toast.makeText(getApplicationContext(), "off Failed..!!", Toast.LENGTH_LONG).show();
                }

            }












            else {
                normal.setVisibility(LinearLayout.GONE);
                empty.setVisibility(ConstraintLayout.VISIBLE);
                TrashBtn.setVisibility(FloatingActionButton.GONE);
//                Toast.makeText(getApplicationContext(), "Nothing to show you..!!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Exc : " + e, Toast.LENGTH_LONG).show();
        }
    }

    private void sum_pro(int k,String a[],String b[]) {
        tt=0;
        for (int i=0;i< k;i++){
            tt=tt+(Integer.parseInt(a[i]) * Integer.parseInt(b[i]) );
        }

        grandtotal.setText(Integer.toString(tt));

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //new imported

                AlertDialog.Builder builder = new AlertDialog.Builder(Cart_view.this);
                builder.setMessage("Ops..You Cannot Purchase  From Different Shop At Same Time .You Have to Empty Your cart!!");
                builder.setCancelable(false);
                builder.setNegativeButton("OK",null);

                builder.show();




            }
        });
    }


    private  void sum(int k,String a[],String b[]){
         tt=0;
        for (int i=0;i< k;i++){
            tt=tt+(Integer.parseInt(a[i]) * Integer.parseInt(b[i]) );
        }

    grandtotal.setText(Integer.toString(tt));

    b1.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            //new imported
            Intent intent =new Intent(getBaseContext(),Payment.class);
            intent.putExtra("Total_amount_total",Integer.toString(tt));
            startActivity(intent);

        }
    });
//    SharedPreferences.Editor ed = sh.edit();
//    ed.putString("grand_total_amount", Integer.toString(tt));
//    ed.commit();
}


    private void selectImageOption(int ppp,String qtty,String amt) {
        final CharSequence[] items = {"Remove_Item",  "Cancel"};
//        pstore = store_id[arg2];
        aamount = amt;
        qty = qtty;

        AlertDialog.Builder builder = new AlertDialog.Builder(Cart_view.this);

        builder.setMessage("Do you want to Remove ?");
        builder.setPositiveButton("Remove",new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface arg0, int arg1)
            {
                JsonReq JR = new JsonReq(getApplicationContext());
                JR.json_response = (JsonResponse) Cart_view.this;
                String q = "/remove_cart/?cartid=" + ppp;
                JR.execute(q);            }
        }).setNegativeButton("Cancel",null);
        builder.show();
    }



    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

//        pcart = cart_id[arg2];
//        pstore = store_id[arg2];
//        aamount = amount[arg2];
//        qty = quantity[arg2];
//        Toast.makeText( getApplicationContext(), "ImageButton clicked on"+pcart+"Store id"+pstore+"Amount ass"+aamount+"of quantity"+qty, Toast.LENGTH_SHORT).show();
//        selectImageOption(Integer.parseInt(quantity[arg2]));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), User_home.class));
    }

}