package com.example.smartshopping;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class Transaction_message {

        private Activity activity1;
        private AlertDialog dialog1;
        Transaction_message(Activity myActivity){
            activity1=myActivity;
        }
        void startTransaction(){
            AlertDialog.Builder builder1=new AlertDialog.Builder(activity1);

            LayoutInflater inflater1=activity1.getLayoutInflater();
            builder1.setView(inflater1.inflate(R.layout.custom_toast_transatction,null));
            builder1.setCancelable(false);
            dialog1=builder1.create();
            dialog1.show();
        }
        void dismissDialog(){
            dialog1.dismiss();
        }

}
