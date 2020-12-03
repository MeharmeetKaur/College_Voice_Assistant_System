package com.example.adminlogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Map;

public class AdminDetails extends AppCompatActivity {
    private EditText editTextEmail,editTextName,editTextPhone;
    private ImageView imageView;
    private Button buttonSave,buttonSaveImage;
    private String s;
    private ProgressDialog progressDialog;
    private FirebaseStorage firebaseStorage;
    private Uri imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_details);
        s=getIntent().getStringExtra("email");
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Getting Admin Details");
        progressDialog.show();
        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextName=(EditText)findViewById(R.id.editTextName);
        editTextPhone=(EditText)findViewById(R.id.editTextPhone);
        imageView=(ImageView)findViewById(R.id.adminimage);
        editTextEmail.setEnabled(false);
        editTextName=(EditText)findViewById(R.id.editTextName);
        editTextPhone=(EditText)findViewById(R.id.editTextPhone);
        buttonSave=(Button)findViewById(R.id.buttonSave);
        loadImage();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("AdminDetails");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("name").getValue(String.class);
                String phone=dataSnapshot.child("phone").getValue(String.class);
                progressDialog.dismiss();
                editTextName.setText(name);
                editTextPhone.setText(phone);
                editTextEmail.setText(s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=editTextName.getText().toString();
                String phone=editTextPhone.getText().toString();
                if(imagePath==null || name.equals("")||phone.equals("")){
                    Toast.makeText(AdminDetails.this,"Add Details properly",Toast.LENGTH_SHORT).show();
                }
                else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("AdminDetails");
                    AdminD admin = new AdminD(name, phone, s);
                    databaseReference.setValue(admin);
                   // DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("AdminImage");
                    StorageReference storageReference=FirebaseStorage.getInstance().getReference("AdminProfile");
                    UploadTask uploadTask=storageReference.putFile(imagePath);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminDetails.this,"Image Upload Failed",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AdminDetails.this,"Image Uploaded Successfully",Toast.LENGTH_SHORT).show();
                        }
                    });
                    Toast.makeText(AdminDetails.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        buttonSaveImage=(Button)findViewById(R.id.addImage);
        buttonSaveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),10);
            }
        });
    }
    void loadImage(){
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        StorageReference storageReference1=firebaseStorage.getReference("AdminProfile") ;
        storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().centerCrop().into(imageView);
                    }
                });
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==10 && resultCode==RESULT_OK && data.getData()!=null){
            imagePath=data.getData();
            try{
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                imageView.setImageBitmap(bitmap);
            }
            catch(IOException io){
                io.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
