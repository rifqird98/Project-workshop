package com.example.rpmtravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class My_Profile extends AppCompatActivity {

    LinearLayout item_program_saya;
    Button btn_logout, btn_ubah_profil;

    TextView name, phone_number;
    ImageView profile_pic;

    DatabaseReference reference, reference2;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String name_key = "";

    RecyclerView mytiket_place;
    ArrayList<ListTickets> list;
    AdapterPaket adapterPaket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        getUsernameLocal();

        profile_pic = findViewById(R.id.profile_pic);
        name = findViewById(R.id.name);
        phone_number = findViewById(R.id.phone_number);

        item_program_saya = findViewById(R.id.item_program_saya);
        btn_logout = findViewById(R.id.btn_logout);
        btn_ubah_profil = findViewById(R.id.btn_ubah_profil);


        mytiket_place = (RecyclerView) findViewById(R.id.mytiket_place);
        mytiket_place.setLayoutManager (new LinearLayoutManager(this));
        list = new ArrayList<ListTickets>();

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(name_key);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("name").getValue().toString());
                phone_number.setText(dataSnapshot.child("phone_number").getValue().toString());
                Picasso.with(My_Profile.this).load(dataSnapshot.child("url_photo_profile").getValue()
                        .toString()).centerCrop().fit().into(profile_pic);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, null);
                editor.apply();

                // berpindah activity
                Intent gotohome = new Intent(My_Profile.this,SignIn.class);
                startActivity(gotohome);
                finish();
            }
        });

        btn_ubah_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoeditprofile = new Intent(My_Profile.this, EditProfile.class);
                startActivity(gotoeditprofile);
                finish();

            }
        });

        reference2 = FirebaseDatabase.getInstance().getReference().child("MyTicket").child(name_key);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    ListTickets p = dataSnapshot1.getValue(ListTickets.class);
                    list.add(p);
                }
                adapterPaket = new AdapterPaket(My_Profile.this, list);
                mytiket_place.setAdapter(adapterPaket);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        name_key = sharedPreferences.getString(username_key, "");

    }
}
