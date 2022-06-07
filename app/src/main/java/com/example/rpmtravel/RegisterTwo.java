package com.example.rpmtravel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class RegisterTwo extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_continue, btn_add_photo;
    ImageView user_pic;
    EditText name, phone_number;

    Uri photo_location;
    Integer photo_max = 1;

    DatabaseReference reference;
    StorageReference storage;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String name_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

        getUsernameLocal();


        btn_back = findViewById(R.id.btn_back);
        btn_continue = findViewById(R.id.btn_continue);
        btn_add_photo = findViewById(R.id.btn_add_photo);
        user_pic = findViewById(R.id.user_pic);
        name = findViewById(R.id.name);
        phone_number = findViewById(R.id.no_phone);


        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPhoto();

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String Nama = name.getText().toString().trim();
                final String Nohp= phone_number.getText().toString().trim();

                //ubah state
                btn_continue.setEnabled(false);
                btn_continue.setText("Loading...");

                // simpan ke database
                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(name_key);
                storage = FirebaseStorage.getInstance().getReference().child("Photousers").child(name_key);
                Toast.makeText(getApplicationContext(),name_key,Toast.LENGTH_LONG).show();

                // validasi file
                if (photo_location == null){
                    Toast.makeText(getApplicationContext(),"foto kosong",Toast.LENGTH_SHORT).show();
                    btn_continue.setEnabled(true);
                    btn_continue.setText("LANJUTKAN");
                }else if (Nama.isEmpty()|| Nohp.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Isi Semua Data", Toast.LENGTH_SHORT).show();
                    btn_continue.setEnabled(true);
                    btn_continue.setText("LANJUTKAN");
                } else if (Nohp.length()<9){
                    Toast.makeText(getApplicationContext(),"No hanphone tidak Valid",Toast.LENGTH_SHORT).show();
                    btn_continue.setEnabled(true);
                    btn_continue.setText("LANJUTKAN");

                } else if (photo_location != null){
                    final StorageReference storageReference1 = storage.child(System.currentTimeMillis() + "." + getFileExtension(photo_location));
                    storageReference1.putFile(photo_location)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String uri_photo = uri.toString();
                                            reference.getRef().child("url_photo_profile").setValue(uri_photo);
                                            reference.getRef().child("name").setValue(name.getText().toString());
                                            reference.getRef().child("phone_number").setValue(phone_number.getText().toString());

                                        }
                                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {

                                            String number = phone_number.getText().toString().trim();
                                            String phoneNumber = number;

                                            Intent intent = new Intent(RegisterTwo.this, SuccessRegister.class);
                                            intent.putExtra("phonenumber", phoneNumber);
                                            startActivity(intent);
                                            finish();


                                        }
                                    });
                                }
                            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                            Intent gotosuccessregister = new Intent(RegisterTwo.this,SuccessRegister.class);
//                            startActivity(gotosuccessregister);
                        }
                    });
                }
            }
        });
    }

    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    public void findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null){
            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(user_pic);
        }
    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        name_key = sharedPreferences.getString(username_key, "");

    }
}
