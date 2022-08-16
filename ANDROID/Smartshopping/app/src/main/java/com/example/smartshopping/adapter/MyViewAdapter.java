package com.example.smartshopping.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartshopping.LocationService;
import com.example.smartshopping.R;
import com.example.smartshopping.Store_view;
import com.example.smartshopping.User_home;

import static android.content.Intent.ACTION_VIEW;

public class MyViewAdapter extends RecyclerView.Adapter<MyViewAdapter.ViewHolder>{


    Context context;
            String  nStorename[];
        String  nStoreemail[];
        String  nStorephone[];
        String  nLogitude[];
        String  nLatitude[];
        int nImage[];
    public MyViewAdapter(String sname[], String semail[], String sphone[],int imgs[],String longitude[],String latitude[], Store_view activity) {

        this.context = activity;
        this.nStorename = sname;
            this.nStoreemail = semail;
           this.nStorephone = sphone;
           this.nLogitude = longitude;
           this.nLatitude = latitude;
            this.nImage = imgs;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.store_items,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.storeName.setText(nStorename[position]);
        holder.storeEmail.setText(nStoreemail[position]);
        holder.storePhone.setText(nStorephone[position]);
        holder.images.setImageResource(nImage[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, rTitle[position], Toast.LENGTH_SHORT).show();
                String url = "http://www.google.com/maps?saddr=" + LocationService.lati + "" + "," + LocationService.logi + "" + "&&daddr=" + nLogitude[position] + "," + nLatitude[position];
                context.startActivity(new Intent(ACTION_VIEW, Uri.parse(url)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return nStorename.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView images;
        TextView storeName;
        TextView storeEmail;
        TextView storePhone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.shopdemoimages);
            storeName = itemView.findViewById(R.id.store_name);
            storePhone = itemView.findViewById(R.id.store_mail);
            storeEmail = itemView.findViewById(R.id.store_phone);

        }
    }
}
