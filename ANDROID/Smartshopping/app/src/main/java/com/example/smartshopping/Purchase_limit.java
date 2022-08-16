package com.example.smartshopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Purchase_limit extends AppCompatActivity {
    EditText ed_limit;
    TextView tv_limit, tv_balance;
    Button bt_add;
    SharedPreferences sh;
    ImageView img1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_limit);

        img1 = (ImageView) findViewById(R.id.backslash);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home_screen.class));
            }
        });

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ed_limit = (EditText) findViewById(R.id.ed_limit);
        tv_limit = (TextView) findViewById(R.id.tv_limit);
        tv_balance = (TextView) findViewById(R.id.tv_balance);
        bt_add = (Button) findViewById(R.id.bt_add_money);

        tv_limit.setText("" + sh.getString("limit", "0"));
        tv_balance.setText("" + sh.getString("balance", "0"));

        bt_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String limit = ed_limit.getText().toString().trim();
                if (limit.equals(""))
                    ed_limit.setError("Enter amount");
                else {
                    int max_limit = Integer.parseInt(limit) + Integer.parseInt(tv_limit.getText().toString().trim());
                    int balance = Integer.parseInt(limit) + Integer.parseInt(tv_balance.getText().toString().trim());
                    tv_limit.setText("" + max_limit);
                    tv_balance.setText("" + balance);

                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("limit", "" + max_limit);
                    ed.putString("balance", "" + balance);
                    ed.commit();
                    ed_limit.setText("0");
                }
            }
        });
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        startActivity(new Intent(getApplicationContext(), Home_screen.class));
//    }


}
