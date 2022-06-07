package com.example.rpmtravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Program_Checkout extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_booking;

    Calendar myCalendar;
    EditText namaLengkap, tmpt_lahir, tgl_lahir, Nik, almtLengkap, no_tlp, Ahliwaris, HubAhli;
    Spinner JenisK, hubWaris;
    private ArrayAdapter adapter;

    DatabaseReference reference, reference2;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String name_key = "";

    String waktu_keberangkatan = "";
    String kode="";
    String nama_paket="";
    String harga_paket="";
    String jenis_kelamin="";
    String HubunganWaris="";

    String tanggal_lahir="";
    private String[] androidVersion;

    //generate monor random
    Integer nomor_transaksi = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_checkout);

        getUsernameLocal();

        Bundle bundle = getIntent().getExtras();
        final String jenis_paket_baru = bundle.getString("jenis_paket");

        btn_back = findViewById(R.id.btn_back);
        btn_booking = findViewById(R.id.btn_pesan);

        tgl_lahir=findViewById(R.id.tanggal_lahir);
        JenisK = (Spinner) findViewById(R.id.jk);
        namaLengkap=findViewById(R.id.nama_lengkap);
        tmpt_lahir=findViewById(R.id.tempat_lahir);
        Nik=findViewById(R.id.nik);
        almtLengkap=findViewById(R.id.alamat);
        no_tlp=findViewById(R.id.telepon_rumah);
        Ahliwaris=findViewById(R.id.ahli_waris);
        hubWaris= findViewById(R.id.hubwaris);

//        androidVersion = getResources().getStringArray(R.array.Jenis_kelamin);


        final String[] JK = new String[] {
                "Perempuan", "Laki-Laki"
        };

        final String[] Hb = new String[]{
                "Anak", "Suami/Istri", "Kerabat/saudara", "Ayah/Ibu"
        };


        final ArrayAdapter Jenis_Kelamin;
        Jenis_Kelamin = new ArrayAdapter(this, android.R.layout.simple_spinner_item, JK);
        Jenis_Kelamin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        JenisK.setAdapter(Jenis_Kelamin);

        JenisK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                // TODO Auto-generated method stub
              jenis_kelamin = JK[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });


        final ArrayAdapter Hubungan_waris;
        Hubungan_waris = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Hb);
        Hubungan_waris.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hubWaris.setAdapter(Hubungan_waris);

        hubWaris.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                // TODO Auto-generated method stub
    HubunganWaris = Hb[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

//

        tgl_lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCalendar =Calendar.getInstance();
                final int year=myCalendar.get(Calendar.YEAR);
                final int mount=myCalendar.get(Calendar.MONTH);
                final int day=myCalendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Program_Checkout.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int mount, int dayOfMount) {
                        tgl_lahir.setText(String.valueOf(dayOfMount)+"-"+String.valueOf(mount+1)+"-"+String.valueOf(year));

                        tanggal_lahir = String.valueOf(dayOfMount) +"-"+ String.valueOf(mount+1) +"- "+ String.valueOf(year);


                    }
                },year,mount,day);
                datePickerDialog.show();

//
            }
        });
//



        reference = FirebaseDatabase.getInstance().getReference().child("PaketUmroh").child(jenis_paket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //memasukkan nilai baru
                nama_paket = dataSnapshot.child("nama_paket").getValue().toString();
                harga_paket=dataSnapshot.child("harga_tiket").getValue().toString();
                waktu_keberangkatan = dataSnapshot.child("waktu_keberangkatan").getValue().toString();
                kode =dataSnapshot.child("kode_paket").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String Nama= namaLengkap.getText().toString().trim();
                final String tmptLahir= tmpt_lahir.getText().toString().trim();
                final String jenisKl= jenis_kelamin;
                final String tgl_lahir= tanggal_lahir;
                final String nik= Nik.getText().toString().trim();
                final String ahliwaris= Ahliwaris.getText().toString().trim();
                final String hub_waris= HubunganWaris;

                if (Nama.isEmpty()||tmptLahir.isEmpty()||jenisKl.isEmpty()||tgl_lahir.isEmpty()||nik.isEmpty()||
                ahliwaris.isEmpty()||hub_waris.isEmpty()){

                    Toast.makeText(getApplicationContext(),"Lengkapi Semua data",Toast.LENGTH_SHORT).show();
                }else {

                    //menyimpan data pada firebase
                    reference2 = FirebaseDatabase.getInstance().getReference().child("MyTicket")
                            .child(name_key).child(kode);
                    reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            reference2.getRef().child("id_paket").setValue( nomor_transaksi);
                            reference2.getRef().child("nama_paket").setValue(nama_paket);
                            reference2.getRef().child("harga_tiket").setValue(harga_paket);
                            reference2.getRef().child("waktu_keberangkatan").setValue(waktu_keberangkatan);
                            reference2.getRef().child("kode_paket").setValue(kode);
                            reference2.getRef().child("Nama_Lengkap").setValue(namaLengkap.getText().toString());
                            reference2.getRef().child("Tempat_Lahir").setValue(tmpt_lahir.getText().toString());
                            reference2.getRef().child("Jenis_Kelamin").setValue(jenis_kelamin);
                            reference2.getRef().child("Tanggal_Lahir").setValue(tanggal_lahir);
                            reference2.getRef().child("No_Telepon").setValue(no_tlp.getText().toString());
                            reference2.getRef().child("Alamat_lengkap").setValue(almtLengkap.getText().toString());
                            reference2.getRef().child("Nik").setValue(Nik.getText().toString());
                            reference2.getRef().child("Ahli_Waris").setValue(Ahliwaris.getText().toString());
                            reference2.getRef().child("Hubungan_Ahli_Waris").setValue(HubunganWaris);


                            Toast.makeText(getApplicationContext(),"Booking Tiket Berhasil",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Program_Checkout.this, SuccessBooking.class);
                            startActivity(intent);
                            finish();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });


    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        name_key = sharedPreferences.getString(username_key, "");

    }
}
