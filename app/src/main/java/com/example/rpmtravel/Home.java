package com.example.rpmtravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

//    ImageView btn_program1, btn_program2, btn_program3, btn_program4, btn_program5;
    CircleView btn_profile;
    ImageView profile_pic;
    TextView name, phone_number;
    LinearLayout item_listprogram;
    LinearLayout doa,peta, Dzikir;

    DatabaseReference reference, reference2;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String name_key = "";

    RecyclerView list_tiket;
    ArrayList<ListTickets> list;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUsernameLocal();


//        btn_program1 = findViewById(R.id.btn_program1);
//        btn_program2 = findViewById(R.id.btn_program2);
//        btn_program3 = findViewById(R.id.btn_program3);
//        btn_program4 = findViewById(R.id.btn_program4);
//        btn_program5 = findViewById(R.id.btn_program5);
        btn_profile = findViewById(R.id.btn_profile);
        profile_pic = findViewById(R.id.profile_pic);
        name = findViewById(R.id.name);
        doa=findViewById(R.id.doa);
        peta=findViewById(R.id.peta);
        phone_number = findViewById(R.id.phone_number);
        item_listprogram = findViewById(R.id.item_program_saya);
        Dzikir = findViewById(R.id.Dzikir);

        list_tiket = (RecyclerView) findViewById(R.id.list_tiket);
        list_tiket.setLayoutManager (new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        list = new ArrayList<ListTickets>();

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(name_key);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("name").getValue().toString());
                phone_number.setText(dataSnapshot.child("phone_number").getValue().toString());
                Picasso.with(Home.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(profile_pic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference2 = FirebaseDatabase.getInstance().getReference().child("PaketUmroh");
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    ListTickets t = dataSnapshot1.getValue(ListTickets.class);
                    list.add(t);
                }
                listAdapter = new ListAdapter(Home.this, list);
                list_tiket.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        doa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprogramdetail = new Intent(Home.this, Doa.class);
//                gotoprogramdetail.putExtra("jenis_paket", "Paket1");
                startActivity(gotoprogramdetail);

            }
        });
//


        peta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprogramdetail = new Intent(Home.this, Masjid.class);
             //   gotoprogramdetail.putExtra("jenis_paket", "Paket2");
                startActivity(gotoprogramdetail);
            }
        });
//
        Dzikir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprogramdetail = new Intent(Home.this, Dzikir_counter.class);
               // gotoprogramdetail.putExtra("jenis_paket", "Paket3");
                startActivity(gotoprogramdetail);
            }
        });

//        btn_program4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent gotoprogramdetail = new Intent(Home.this, Program_Detail.class);
//                gotoprogramdetail.putExtra("jenis_paket", "Paket4");
//                startActivity(gotoprogramdetail);
//            }
//        });
//
//        btn_program5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent gotoprogramdetail = new Intent(Home.this, Program_Detail.class);
//                gotoprogramdetail.putExtra("jenis_paket", "Paket5");
//                startActivity(gotoprogramdetail);
//            }
//        });
//
        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomyprofile = new Intent(Home.this, My_Profile.class);
                startActivity(gotomyprofile);

            }
        });

    }


    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        name_key = sharedPreferences.getString(username_key, "");

    }
}
