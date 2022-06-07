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

public class SuccessRegister extends AppCompatActivity {

    Animation scale_alpha, bottom_top, top_bottom;
    ImageView image_ahlan;
    TextView ahlan_text1, ahlan_text2;

    Button btn_explore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        image_ahlan = findViewById(R.id.image_ahlan);
        ahlan_text1 = findViewById(R.id.ahlan_text1);
        ahlan_text2 = findViewById(R.id.ahlan_text2);
        btn_explore = findViewById(R.id.btn_explore);

        //load animation
        scale_alpha = AnimationUtils.loadAnimation(this, R.anim.scale_alpha);
        bottom_top = AnimationUtils.loadAnimation(this, R.anim.bottom_top);
        top_bottom = AnimationUtils.loadAnimation(this, R.anim.top_bottom);

        //run animation
        btn_explore.startAnimation(bottom_top);
        image_ahlan.startAnimation(scale_alpha);
        ahlan_text1.startAnimation(top_bottom);
        ahlan_text2.startAnimation(top_bottom);


        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohome = new Intent(SuccessRegister.this, Home.class);
                startActivity(gotohome);
                finish();
            }
        });
    }
}
