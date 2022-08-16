package com.example.smartshopping;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class Transaction_load {

    private Activity activity2;
    private AlertDialog dialog2;
    Transaction_load(Activity myActivity){
        activity2=myActivity;
    }
    void startLoadingDialog2(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity2);

        LayoutInflater inflater=activity2.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_toast_transatction,null));
        builder.setCancelable(false);
        dialog2=builder.create();
        dialog2.show();
    }
    void dismissDialog2(){
        dialog2.dismiss();
    }
}


