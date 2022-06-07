package com.example.rpmtravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

public class RegisterOne extends AppCompatActivity implements View.OnClickListener {

    LinearLayout btn_back;
    Button btn_continue;
    EditText username, password, email_address;
    DatabaseReference reference, reference_username;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        btn_back = findViewById(R.id.btn_back);
        btn_continue = findViewById(R.id.btn_continue);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email_address = findViewById(R.id.email_address);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.btn_continue).setOnClickListener(this);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        btn_continue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //ubah state
//                btn_continue.setEnabled(false);
//                btn_continue.setText("Loading...");
//
//                //username
//
//
//                //menyimpan data ke local storage (hp)
//                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString(username_key, username.getText().toString());
//                editor.apply();
//
//                //simpan ke database
//                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
//                reference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        dataSnapshot.getRef().child("username").setValue(username.getText().toString());
//                        dataSnapshot.getRef().child("password").setValue(password.getText().toString());
//                        dataSnapshot.getRef().child("email_address").setValue(email_address.getText().toString());
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//
//                Intent gotoregistertwo = new Intent(RegisterOne.this, RegisterTwo.class);
//                startActivity(gotoregistertwo);
//            }
//        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
        }
    }

    private void registerUser() {
        btn_continue.setEnabled(false);
        btn_continue.setText("Loading...");

        final String name = username.getText().toString().trim();
        final String email = email_address.getText().toString().trim();
        String Password = password.getText().toString().trim();
//        final String phone = editTextPhone.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(getApplicationContext(),"username kosong",Toast.LENGTH_SHORT).show();
            username.requestFocus();
            btn_continue.setEnabled(true);
            btn_continue.setText("Continue");

            return;
        }

        if (name.length()<5) {
            Toast.makeText(getApplicationContext(),"masukkan nama lengkap",Toast.LENGTH_SHORT).show();
            username.requestFocus();
            btn_continue.setEnabled(true);
            btn_continue.setText("Continue");

            return;
        }

        if (Password.isEmpty()) {
            Toast.makeText(getApplicationContext(),"password kosong",Toast.LENGTH_SHORT).show();
            password.requestFocus();
            btn_continue.setEnabled(true);
            btn_continue.setText("Continue");

            return;
        }

        if (Password.length() < 8) {
            Toast.makeText(getApplicationContext(),"Password 6 karakter",Toast.LENGTH_SHORT).show();
            password.requestFocus();
            btn_continue.setEnabled(true);
            btn_continue.setText("Continue");

            return;
        }

        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(),"email kosong",Toast.LENGTH_SHORT).show();
            username.requestFocus();
            btn_continue.setEnabled(true);
            btn_continue.setText("Continue");

            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(),"email tidak valid",Toast.LENGTH_SHORT).show();
            email_address.requestFocus();
            btn_continue.setEnabled(true);
            btn_continue.setText("Continue");

            return;
        }




        mAuth.createUserWithEmailAndPassword(email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {

                            SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(username_key, username.getText().toString());
                            editor.apply();

                            reference_username =  FirebaseDatabase.getInstance().getReference()
                                    .child("Users").child(username.getText().toString());
                            reference_username.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){
                                        Toast.makeText(getApplicationContext(),"username sudah terdaftar",Toast.LENGTH_SHORT).show();
                                        username.requestFocus();
                                        btn_continue.setEnabled(true);
                                        btn_continue.setText("Continue");
                                        return;
                                    }else{

                                        // simpan kepada database
                                        reference = FirebaseDatabase.getInstance().getReference()
                                                .child("Users").child(username.getText().toString());
                                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                                                dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                                                dataSnapshot.getRef().child("email_address").setValue(email_address.getText().toString());
//                                    dataSnapshot.getRef().child("user_balance").setValue(800);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

                                        // berpindah activity
                                        Intent gotonextregister = new Intent(RegisterOne.this,RegisterTwo.class);
                                        startActivity(gotonextregister);
                                        finish();

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        } else {
                            Toast.makeText(RegisterOne.this, "Email tidak valid", Toast.LENGTH_LONG).show();
                            btn_continue.setEnabled(true);
                            btn_continue.setText("Continue");

                        }
                    }
                });

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_continue:
                registerUser();
                break;
        }
    }

}