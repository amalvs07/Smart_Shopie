package com.example.smartshopping;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class Payment extends AppCompatActivity implements JsonResponse {
    TextView tv;
    Button b1;
    String locname, logid, url;

    SharedPreferences sh;
    EditText e1,e2,e3,e4;
    String card_no,cvv,exp_date,card_h_name,totall;
    ImageView backButton;
    String[] store_iid,tt_amount;

    ImageButton imgb1;
    private int nYear;
    private int nMonth;
    private int nDay;
    static final int DATE_DIALOG_ID=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);






        imgb1=(ImageButton)findViewById(R.id.ondatepicker);
        imgb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        final Calendar c=Calendar.getInstance();
        nYear=c.get(Calendar.YEAR);
        nMonth=c.get(Calendar.MONTH);
        nDay=c.get(Calendar.DAY_OF_MONTH);

//        updateDisplay();
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        logid = sh.getString("login_id", "0");
        url = sh.getString("url", "");
        tv = (TextView) findViewById(R.id.tvamount);
        b1=(Button)findViewById(R.id.button1);
//        tv.setText(Buy.pamount);

        totall=getIntent().getStringExtra("Total_amount_total");
        tv.setText(totall);

        e1=(EditText)findViewById(R.id.eTcardName);
        e2=(EditText)findViewById(R.id.eTcardNumber);
        e3=(EditText)findViewById(R.id.eTCvv);
        e4=(EditText)findViewById(R.id.eTdate);

        backButton=(ImageView) findViewById(R.id.backButton);

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                card_h_name = e1.getText().toString();
                card_no = e2.getText().toString();
                cvv = e3.getText().toString();
                exp_date = e4.getText().toString();

                int flaag = 0;
                if (card_h_name.equals("")) {
                    e1.setError("Card name please");
                    flaag++;
                }
                if (card_no.length() != 16) {
                    e2.setError("Valid Card No Please");
                    flaag++;
                }
                if (cvv.length() != 3) {
                    e3.setError("Valid CVV Please ");
                    flaag++;
                }
                if (exp_date.equals("")) {
                    e4.setError("Expiry Date please");
                    flaag++;
                }
                if (flaag == 0) {
//                    JsonReq JR = new JsonReq(getApplicationContext());
//                    JR.json_response = (JsonResponse) Payment.this;
//                    String q = "/make_paymet/?smid=" + Buy.psmid + "&amount=" + Buy.pamount;
//                    JR.execute(q);

                    JsonReq JR = new JsonReq(getApplicationContext());
                    JR.json_response = (JsonResponse) Payment.this;
                    String q = "/new_buy/?logid=" + logid;
                    JR.execute(q);
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Cart_view.class));
            }
        });
//        e4.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                exp_date = e4.getText().toString();
//                if (exp_date.length() == 2 && !exp_date.contains("/")) {
//                    e4.setText(exp_date + "/");
//                    e4.setSelection(e4.getText().length());
//                }
//            }
//        });
//        b1.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//                JsonReq JR = new JsonReq(getApplicationContext());
//                JR.json_response = (JsonResponse) Payment.this;
//                String q = "/make_paymet/?smid=" + sh.getString("sm_id", "0")+ "&amount=" + sh.getString("totalamount", "0") +"&login_id="+logid;
//                JR.execute(q);
//            }
//        });



    }

    void initarray(int len) {
        store_iid = new String[len];
        tt_amount = new String[len];

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Cart_view.class));
    }

    protected Dialog onCreateDialog(int id){
        switch (id){
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,dateSetListener,nYear,nMonth,nDay);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            nYear=year;
            nMonth=month;
            nDay=dayOfMonth;
            updateDisplay();
        }
    };
//
    private void updateDisplay(){
        this.e4.setText(new StringBuilder()
                .append(nYear).append("/")
                .append(nMonth + 1).append("/")
                .append(nDay).append(" ")
        );
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {

                if (jo.getString("method").equals("new_buy")) {

                    JSONArray ja = (JSONArray) jo.getJSONArray("data");
                    if (ja.length() > 0) {
                        initarray(ja.length());

                        for (int i = 0; i < ja.length(); i++) {
                            store_iid[i] = ja.getJSONObject(0).getString("store_id");
                            tt_amount[i] = ja.getJSONObject(0).getString("total_amount");

                        }


                    }


//                Toast.makeText(getApplicationContext(),"Payment Compleated please wait for your delivery..",Toast.LENGTH_LONG).show();
                    final Loading_dialog loading_dialog = new Loading_dialog(Payment.this);
                    final Transaction_load transaction_load = new Transaction_load(Payment.this);
//                final Toast toast2=new Toast(getApplicationContext());
//                LayoutInflater payment =getLayoutInflater();
//
//
//                View pay=payment.inflate(R.layout.custom_toast_transatction,(ViewGroup)findViewById(R.id.Tosot_message_layout));
//
//                toast2.setGravity(Gravity.CENTER_VERTICAL,0,0);
//                toast2.setDuration(Toast.LENGTH_SHORT);
//                toast2.setView(pay);
                    loading_dialog.startLoadingDialog();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loading_dialog.dismissDialog();

//                        toast2.show();
                            transaction_load.startLoadingDialog2();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    transaction_load.dismissDialog2();

//                startActivity(new Intent(getApplicationContext(),Pdf_maker.class));

                                    Intent intent =new Intent(getBaseContext(),Rating_store.class);
                                    intent.putExtra("STORE_ID",store_iid[0]);
                                    startActivity(intent);


//                                    startActivity(new Intent(getApplicationContext(), Rating_store.class));

                                    finish();

                                }
                            }, 3000);


//                        finish();
                        }
                    }, 5000);
//loading and toast above
                }
            } else {
                Toast.makeText(getApplicationContext(), "Failed!!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exc : " + e, Toast.LENGTH_LONG).show();
        }
    }

}
