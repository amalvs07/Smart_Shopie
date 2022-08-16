package com.example.smartshopping;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class IPSetting extends AppCompatActivity {
    EditText ed_ip;
    Button bt_ip;
//    String ipVal = "";
    SharedPreferences sh;
    public static String ip,ipVal;
    boolean doubleBackToExitPressedOnce=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipsetting);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ed_ip = (EditText) findViewById(R.id.etip);
        bt_ip = (Button) findViewById(R.id.btip);
        ed_ip.setText(sh.getString("ip", "192.168.43.243:5555"));

        bt_ip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                ipVal = ed_ip.getText().toString();
                if (ipVal.equals("")) {
                    ed_ip.setError("Enter IP address");
                    ed_ip.setFocusable(true);
                } else {
                    SharedPreferences.Editor ed= sh.edit();
                    ed.putString("ip", ipVal);
                    ed.commit();
                    startService(new Intent(getApplicationContext(), LocationService.class));
                    startActivity(new Intent(getApplicationContext(), Frontpage.class));
                }
            }
        });
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
//        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
//                .setTitle("Exit  :")
//                .setMessage("Are you sure you want to exit..?")
//                .setPositiveButton("Yes",new DialogInterface.OnClickListener()
//                {
//
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1)
//                    {
//                        // TODO Auto-generated method stub
//                        Intent i=new Intent(Intent.ACTION_MAIN);
//                        i.addCategory(Intent.CATEGORY_HOME);
//                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(i);
//                        finish();
//                    }
//                }).setNegativeButton("No",null).show();
//        Intent i=new Intent(Intent.ACTION_MAIN);
//        i.addCategory(Intent.CATEGORY_HOME);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(i);
//        finish();



        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
            finishAffinity();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}