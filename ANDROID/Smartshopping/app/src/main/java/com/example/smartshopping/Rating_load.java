package com.example.smartshopping;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class Rating_load {
    private Activity activity1;
    private AlertDialog dialog1;
    Rating_load(Activity myActivity){
        activity1=myActivity;
    }
    void startLoadingDialog1(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity1);

        LayoutInflater inflater=activity1.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_toast_rating,null));
        builder.setCancelable(false);
        dialog1=builder.create();
        dialog1.show();
    }
    void dismissDialog1(){
        dialog1.dismiss();
    }
}

