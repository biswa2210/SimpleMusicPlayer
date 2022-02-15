package com.example.simplemusicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class splashActivity extends AppCompatActivity {
    private ImageView SMplaYer_Icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SMplaYer_Icon=(ImageView) findViewById(R.id.imageView2);
        new Handler().postDelayed(new Runnable() {

            // Using handler with postDelayed called runnable run method

            @Override

            public void run() {

                Intent i = new Intent(splashActivity.this, MainActivity.class);

                startActivity(i);

                // close this activity

                finish();

            }

        }, 3*1200);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mysplashanim);
        SMplaYer_Icon.startAnimation(myanim);
    }
}