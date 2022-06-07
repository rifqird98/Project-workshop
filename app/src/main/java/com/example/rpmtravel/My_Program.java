package com.example.rpmtravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class My_Program extends AppCompatActivity {
    DatabaseReference reference, reference2;
    FirebaseDatabase myRef;

    TextView NamaProgram, tanggal, waktu, informasi, kode, rute,biaya;
    TextView Nama, No_tlp, tmpt_lahir, tanggal_lahir, nik, almt_lengkap, nama_ahli, hub_ahli;
    LinearLayout btn_back ;
    Button btn_cancel;


    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String name_key = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_program);
        NamaProgram = findViewById(R.id.program_detail);
        tanggal = findViewById(R.id.tanggal);
        waktu=findViewById(R.id.waktu);
        informasi=findViewById(R.id.informasi);
        kode=findViewById(R.id.bc);
        rute=findViewById(R.id.rute);

        //
        Nama = findViewById(R.id.nama);
        No_tlp = findViewById(R.id.no_rumah);
        tmpt_lahir = findViewById(R.id.tmpt_lahir);
        tanggal_lahir = findViewById(R.id.tgl_lahir);
        nik = findViewById(R.id.NIK);
        almt_lengkap=findViewById(R.id.alamat_lengkap);
        nama_ahli=findViewById(R.id.AhliWaris);
        hub_ahli=findViewById(R.id.Hub_ahli);
        biaya=findViewById(R.id.biaya);



        btn_cancel = findViewById(R.id.cancel);
        getUsernameLocal();


        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String nama_wisata_baru = bundle.getString("kode_paket");



        reference2 = FirebaseDatabase.getInstance().getReference().child("PaketUmroh").child(nama_wisata_baru);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                NamaProgram.setText(dataSnapshot.child("nama_paket").getValue().toString());
                tanggal.setText(dataSnapshot.child("maskapai").getValue().toString());
                waktu.setText(dataSnapshot.child("waktu_keberangkatan").getValue().toString());
                informasi.setText(dataSnapshot.child("short_desc").getValue().toString());
                rute.setText(dataSnapshot.child("rute").getValue().toString());
           //     kode.setText(dataSnapshot.child("kode_paket").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference("MyTicket").child(name_key);
        reference.child(nama_wisata_baru).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Nama.setText(dataSnapshot.child("Nama_Lengkap").getValue().toString());
                No_tlp.setText(dataSnapshot.child("No_Telepon").getValue().toString());
                tmpt_lahir.setText(dataSnapshot.child("Tempat_Lahir").getValue().toString());
                tanggal_lahir.setText(dataSnapshot.child("Tanggal_Lahir").getValue().toString());
                almt_lengkap.setText(dataSnapshot.child("Alamat_lengkap").getValue().toString());
                nik.setText(dataSnapshot.child("Nik").getValue().toString());
                nama_ahli.setText(dataSnapshot.child("Ahli_Waris").getValue().toString());
                hub_ahli.setText(dataSnapshot.child("Hubungan_Ahli_Waris").getValue().toString());
                kode.setText(dataSnapshot.child("id_paket").getValue().toString());
                biaya.setText(dataSnapshot.child("harga_tiket").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             onBackPressed();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        reference2 = myRef.getInstance().getReference("MyTicket").child(name_key);
        reference2.child(nama_wisata_baru).removeValue();


        Intent intent = new Intent(My_Program.this, My_Profile.class);
        startActivity(intent);
        finish();

            }
        });
    }


    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        name_key = sharedPreferences.getString(username_key, "");

    }
}
