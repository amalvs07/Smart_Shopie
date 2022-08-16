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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class View_bill extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener{
    ListView lv1;
    String [] store_id,store_name,product_name,quantity,amount,sales_date,val,smid;
    public static String pcart, pstore, aamount, qty,psmid;
    public static String store_ids;
    SharedPreferences sh;
    ImageView BackButton;
    LinearLayout normal;
    ConstraintLayout empty;
    int images[]={R.drawable.shop1,R.drawable.shop2,R.drawable.shop3,R.drawable.shop4,R.drawable.shop5,R.drawable.shop6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);

        normal=(LinearLayout)this.findViewById(R.id.NormalView);
        empty=(ConstraintLayout) this.findViewById(R.id.EmptyView);

//        empty.setVisibility(ConstraintLayout.GONE);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lv1=(ListView)findViewById(R.id.My_order_lists);
        BackButton=(ImageView) findViewById(R.id.backslash);
        BackButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), Home_screen.class));
                    }
                 });
        JsonReq JR=new JsonReq(getApplication());
        JR.json_response=(JsonResponse) View_bill.this;
        String q = "/View_my_orders/?loginid="+sh.getString("login_id","");
        q=q.replace(" ","%20");
        JR.execute(q);
        lv1.setOnItemClickListener(this);
    }
    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("View_my_orders")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");

                    store_id=new String[ja1.length()];
                    store_name=new String[ja1.length()];
//                   product_name=new String[ja1.length()];
//                    quantity=new String[ja1.length()];

                    smid = new String[ja1.length()];
                    amount=new String[ja1.length()];
                    sales_date=new String[ja1.length()];
                    val=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {

                        store_id[i]=ja1.getJSONObject(i).getString("store_id");
                        store_name[i]=ja1.getJSONObject(i).getString("store_name");
//                        product_name[i]=ja1.getJSONObject(i).getString("product_name");
//                        quantity[i]=ja1.getJSONObject(i).getString("quantity");
                        amount[i]=ja1.getJSONObject(i).getString("total_amount");
                        sales_date[i]=ja1.getJSONObject(i).getString("sales_date");
                        smid[i] = ja1.getJSONObject(i).getString("sm_id");


//                        val[i]="store_name : "+store_name[i]+"\namount : "+amount[i]+"\nsales_date : "+sales_date[i];

                    }

//                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,val);
//                    lv1.setAdapter(ar);
                    View_bill.MyAdapter adapter = new View_bill.MyAdapter(this, amount,store_name,sales_date,images);
                    lv1.setAdapter(adapter);

                }
                else {
                    normal.setVisibility(LinearLayout.GONE);
                    empty.setVisibility(ConstraintLayout.VISIBLE);
//                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();
                }
            }



        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
//        String  nProductname[];
        String  nAmount[];
        String  nStorename[];
        String  nSalesDate[];
        int nImage[];

        MyAdapter (Context c,  String samount[], String sstorename[], String ssalesdate[],int imgs[]) {

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
//            TextView productName = row.findViewById(R.id.Product_names);
            TextView storeName = row.findViewById(R.id.storename);
            TextView productAmount = row.findViewById(R.id.TotalAmount);
            TextView salesDate = row.findViewById(R.id.SalesDate);

//            productName.setText(nProductname[position]);
            storeName.setText(nStorename[position]);
            productAmount.setText(nAmount[position]);
            salesDate.setText(nSalesDate[position]);
            images.setImageResource(nImage[position]);

//            sh=PreferenceManager.getDefaultSharedPreferences(getContext());
//
//            String pth = "http://"+sh.getString("ip", "")+nImage[position];
//            pth = pth.replace(" ", "%20");
//
//            Log.d("-------------", pth);
//            Picasso.get().load(pth).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_baseline_error_).into(images);
            return row;
        }

    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

//        pcart = cart_id[arg2];
        pstore = store_id[arg2];
        psmid = smid[arg2];
        aamount = amount[arg2];
//        qty = quantity[arg2];
//        Toast.makeText( getApplicationContext(), "ImageButton clicked on SM ID"+psmid+"Store id"+pstore+"Amount ass"+aamount, Toast.LENGTH_SHORT).show();
//        selectImageOption(Integer.parseInt(quantity[arg2]));
        selectImageOption();
    }

    private void selectImageOption() {
        Intent b=new Intent(getApplicationContext(),Create_pdf.class);
        startActivity(b);
    }


    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Home_screen.class);
        startActivity(b);
    }




}