package com.example.smartshopping;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class User_home extends Activity {
    ImageButton qrscan,cart,store,more,scanner,purchase;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    CodeScanner codeScanner;
    CodeScannerView scannView;
    TextView resultData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        qrscan=(ImageButton)findViewById(R.id.Qrscan);
        cart=(ImageButton)findViewById(R.id.Cart);
        store=(ImageButton)findViewById(R.id.Store);
        more=(ImageButton)findViewById(R.id.Viewmore);
//        scanner=(ImageView)findViewById(R.id.QRSCANNER);
//
////        purchase=(ImageView)findViewById(R.id.Purchase);
//
//        scanner.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//                scanQR();
//            }
//        });
        scannView = findViewById(R.id.scannerView);
        codeScanner = new CodeScanner(this,scannView);
//        resultData = findViewById(R.id.resultsOfQr);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

//                        resultData.setText(result.getText());

                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor ed = sh.edit();
                        ed.putString("qrid", result.getText());
                        ed.commit();

                        startActivity(new Intent(getApplicationContext(), Scan_result_view.class));
                    }
                });

            }
        });

//        scannView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                 codeScanner.startPreview();
//            }
//        });
        cart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), Cart_view.class));
            }
        });

        store.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), Store_view.class));
            }
        });

        more.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), Home_screen.class));
            }
        });

//        purchase.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                startActivity(new Intent(getApplicationContext(), Buy.class));
//            }
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), User_home.class));
    }

}