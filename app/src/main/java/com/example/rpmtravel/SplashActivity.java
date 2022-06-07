package com.example.rpmtravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    Animation scale_alpha, bottom_top;
    ImageView app_logo_splash;
    TextView app_slogan;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String name_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        //load animation
        scale_alpha = AnimationUtils.loadAnimation(this, R.anim.scale_alpha);
        bottom_top = AnimationUtils.loadAnimation(this, R.anim.bottom_top);

        app_logo_splash = findViewById(R.id.app_logo_splash);
        app_slogan = findViewById(R.id.app_slogan);

        //run animation
        app_logo_splash.startAnimation(scale_alpha);
        app_slogan.startAnimation(bottom_top);

        getUsernameLocal();

    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        name_key = sharedPreferences.getString(username_key, "");
        if(name_key.isEmpty()){
            // setting timer untuk 2 detik
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // merubah activity ke activity lain
                    Intent gogetstarted = new Intent(SplashActivity.this,GetStarted.class);
                    startActivity(gogetstarted);
                    finish();
                }
            }, 2000); // 2000 ms = 2s
        }
        else {
            // setting timer untuk 2 detik
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // merubah activity ke activity lain
                    Intent gogethome = new Intent(SplashActivity.this,Home.class);
                    startActivity(gogethome);
                    finish();
                }
            }, 2000); // 2000 ms = 2s
        }
    }

}
