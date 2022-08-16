package com.example.smartshopping;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Frontpage extends AppCompatActivity {
    Animation topAnim,bottomAnim;
    ImageView image;
    TextView text1,text2;
    private static int SPLASH_SCREEN= 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontpage);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.topanim);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottomanim);

        image=findViewById(R.id.profile_image);
        text1=findViewById(R.id.textView3);
        text2=findViewById(R.id.textView4);
        //aimation seting
        image.setAnimation(topAnim);
        text1.setAnimation(bottomAnim);
        text2.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Frontpage.this,Logo.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}