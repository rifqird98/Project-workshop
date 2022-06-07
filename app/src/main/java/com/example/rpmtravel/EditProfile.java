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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EditProfile extends AppCompatActivity {

    ImageView photo_edit_profile;
    EditText xname, xphone_number, xusername, xpassword, xemail_address;
    LinearLayout btn_back;
    Button btn_save, btn_add_new_photo;

    Uri photo_location;
    Integer photo_max = 1;

    DatabaseReference reference;
    StorageReference storage;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String name_key = "";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getUsernameLocal();

        btn_add_new_photo = findViewById(R.id.btn_add_new_photo);
        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save);
        photo_edit_profile = findViewById(R.id.photo_edit_profile);

        xname = findViewById(R.id.xname);
        xphone_number = findViewById(R.id.xphone_number);
        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);
        xemail_address = findViewById(R.id.xemail_address);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(name_key);
        storage = FirebaseStorage.getInstance().getReference().child("Photousers").child(name_key);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xname.setText(dataSnapshot.child("name").getValue().toString());
                xphone_number.setText(dataSnapshot.child("phone_number").getValue().toString());
                xusername.setText(dataSnapshot.child("username").getValue().toString());
                xpassword.setText(dataSnapshot.child("password").getValue().toString());
                xemail_address.setText(dataSnapshot.child("email_address").getValue().toString());
                Picasso.with(EditProfile.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo_edit_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("name").setValue(xname.getText().toString());
                        dataSnapshot.getRef().child("phone_number").setValue(xphone_number.getText().toString());
                        dataSnapshot.getRef().child("username").setValue(xusername.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(xpassword.getText().toString());
                        dataSnapshot.getRef().child("email_address").setValue(xemail_address.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Intent gotoprofile = new Intent(EditProfile.this, My_Profile.class);
                startActivity(gotoprofile);
                finish();

                // validasi file
                if (photo_location != null){
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
                                        }
                                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {

                                            Intent gotosuccessregister = new Intent(EditProfile.this,My_Profile.class);
                                            startActivity(gotosuccessregister);
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



        btn_add_new_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPhoto();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goback = new Intent(EditProfile.this, My_Profile.class);
                startActivity(goback);
                onBackPressed();
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
            Picasso.with(this).load(photo_location).centerCrop().fit().into(photo_edit_profile);
        }
    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        name_key = sharedPreferences.getString(username_key, "");

    }
}
