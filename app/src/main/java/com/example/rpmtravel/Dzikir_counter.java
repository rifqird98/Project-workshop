package com.example.rpmtravel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Dzikir_counter extends AppCompatActivity implements View.OnClickListener {

    // deklarasi komponen View
    private Button btnCount, btnResult, btnReset;
    private TextView tvNumberCounter;
    Integer dzikir = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dzikir_counter);

        // menghubungkan antara JAVA dan XML dengan mengaitkan ID masing-masing View
        btnCount = findViewById(R.id.btn_main_count);
        btnResult = findViewById(R.id.btn_main_result);
        btnReset = findViewById(R.id.btn_main_reset);
        tvNumberCounter = findViewById(R.id.tv_main_number_counter);

        // deklarasi kondisi ketika Button di tekan oleh pengguna
        btnCount.setOnClickListener(this);
        btnResult.setOnClickListener(this);
        btnReset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_main_count) {
            dzikir+=1;
            tvNumberCounter.setText(dzikir.toString());
            if (dzikir>1){
                btnCount.animate().alpha(1).setDuration(300).start();
                btnCount.setEnabled(true);
            }
        } else if (v.getId() == R.id.btn_main_reset) {
            dzikir = 0;
            tvNumberCounter.setText(dzikir.toString());
        } else if (v.getId() == R.id.btn_main_result) {
            Toast.makeText(this, "Current dzikir count: " + dzikir, Toast.LENGTH_SHORT).show();
        }
    }
    }

