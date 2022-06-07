package com.example.rpmtravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GetStarted extends AppCompatActivity {

    Button btn_sign_in, btn_create_new_account;
    Animation top_bottom, bottom_top;
    ImageView app_emblem;
    TextView welcome_text1, welcome_text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        //load animation
        top_bottom = AnimationUtils.loadAnimation(this, R.anim.top_bottom);
        bottom_top = AnimationUtils.loadAnimation(this, R.anim.bottom_top);

        btn_sign_in = findViewById(R.id.btn_sign_in);
        btn_create_new_account = findViewById(R.id.btn_create_new_account);
        app_emblem = findViewById(R.id.app_emblem);
        welcome_text1 = findViewById(R.id.welcome_text1);
        welcome_text2 = findViewById(R.id.welcome_text2);

        //run animation
        app_emblem.startAnimation(top_bottom);
        welcome_text1.startAnimation(top_bottom);
        welcome_text2.startAnimation(top_bottom);
        btn_sign_in.startAnimation(bottom_top);
        btn_create_new_account.startAnimation(bottom_top);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosign = new Intent(GetStarted.this,SignIn.class);
                startActivity(gotosign);
                finish();
            }
        });
        btn_create_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoregisterone = new Intent(GetStarted.this,RegisterOne.class);
                startActivity(gotoregisterone);
        finish();

            }
        });

    }
}
