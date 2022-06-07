package com.example.rpmtravel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyDoa extends AppCompatActivity {

    DatabaseReference reference;


    TextView nama_doa, text_doa, latin, terjemah;
    LinearLayout btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_doa);
        nama_doa = findViewById(R.id.nama_doa);
        text_doa = findViewById(R.id.text_doa);
        latin=findViewById(R.id.latin);
        terjemah=findViewById(R.id.Terjemahan);


        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String nama_wisata_baru = bundle.getString("kode_doa");

        reference = FirebaseDatabase.getInstance().getReference().child("Doa-Doa").child(nama_wisata_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nama_doa.setText(dataSnapshot.child("nama_doa").getValue().toString());
                text_doa.setText(dataSnapshot.child("text_doa").getValue().toString());
                latin.setText(dataSnapshot.child("latin_doa").getValue().toString());
                terjemah.setText(dataSnapshot.child("terjemah_doa").getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//
//        btn_back = findViewById(R.id.btn_back);
//
//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
    }
}
