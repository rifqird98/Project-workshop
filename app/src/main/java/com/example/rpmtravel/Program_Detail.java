package com.example.rpmtravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Program_Detail extends AppCompatActivity {

    Button btn_booking;
    TextView nama_paket, harga_tiket, hotel_makkah, hotel_madinah, maskapai, waktu_keberangkatan, link_wa;
    LinearLayout btn_back,btn_wa;;
    ImageView header_program_detail;

    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_detail);

        btn_booking = findViewById(R.id.btn_booking);
        btn_back = findViewById(R.id.btn_back);
        btn_wa = findViewById(R.id.btn_wa);
        header_program_detail = findViewById(R.id.header_program_detail);

        nama_paket = findViewById(R.id.nama_paket);
        harga_tiket = findViewById(R.id.harga_tiket);
        hotel_makkah = findViewById(R.id.hotel_makkah);
        hotel_madinah = findViewById(R.id.hotel_madinah);
        maskapai = findViewById(R.id.maskapai);
        waktu_keberangkatan = findViewById(R.id.waktu_keberangkatan);

        Bundle bundle = getIntent().getExtras();
        final String jenis_paket_baru = bundle.getString("kode_paket");

        reference = FirebaseDatabase.getInstance().getReference().child("PaketUmroh").child(jenis_paket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //memasukkan nilai baru
                nama_paket.setText(dataSnapshot.child("nama_paket").getValue().toString());
                harga_tiket.setText(dataSnapshot.child("harga_tiket").getValue().toString());
                hotel_makkah.setText(dataSnapshot.child("hotel makkah").getValue().toString());
                hotel_madinah.setText(dataSnapshot.child("hotel madinah").getValue().toString());
                maskapai.setText(dataSnapshot.child("maskapai").getValue().toString());
                waktu_keberangkatan.setText(dataSnapshot.child("waktu_keberangkatan").getValue().toString());
                Picasso.with(Program_Detail.this).load(dataSnapshot.child("url_thumbnail").getValue().toString())
                        .centerCrop().fit().into(header_program_detail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotocheckout = new Intent(Program_Detail.this,Program_Checkout.class);
                gotocheckout.putExtra("jenis_paket", jenis_paket_baru);
                startActivity(gotocheckout);
                finish();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        btn_wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url= "https://api.whatsapp.com/send?phone=6287764147944";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}
