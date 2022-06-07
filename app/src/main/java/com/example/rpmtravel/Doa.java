package com.example.rpmtravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Doa extends AppCompatActivity {

    DatabaseReference reference2;

    RecyclerView list_doa;
     ArrayList<ListDoa> list;
     AdapterDoa adapterDoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doa);


        list_doa = (RecyclerView) findViewById(R.id.list_doa);


        list_doa.setLayoutManager (new LinearLayoutManager(this));
        list = new ArrayList<ListDoa>();

        reference2 = FirebaseDatabase.getInstance().getReference().child("Doa-Doa");
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    ListDoa p = dataSnapshot1.getValue(ListDoa.class);
                    list.add(p);
                }
                adapterDoa = new AdapterDoa(Doa.this, list);
                list_doa.setAdapter(adapterDoa);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
