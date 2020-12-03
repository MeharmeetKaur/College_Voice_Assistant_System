package com.example.adminlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdminList extends AppCompatActivity {
    ArrayList<String> lists;
    private ListView listView;
    private String s;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list);
        s=getIntent().getStringExtra("email");
        listView=(ListView)findViewById(R.id.ListAdmin);
        lists=new ArrayList<>();
        lists.add("Edit My Account");
        //lists.add("Delete My Account");
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,lists);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    startActivity(new Intent(AdminList.this,AdminDetails.class).putExtra("email",s));
                }
               /* if(i==1){
                    AlertDialog.Builder alertDialog=new AlertDialog.Builder(AdminList.this);
                    alertDialog.setTitle("Are you sure?");
                    alertDialog.setMessage("Deleting this account will result in completely removing your account from the system and you won't be able to access the sysytem");
                    alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("AdminDetails");
                                        databaseReference.removeValue();
                                        StorageReference storageReference= FirebaseStorage.getInstance().getReference("AdminProfile");
                                        storageReference.delete().addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(AdminList.this,"Deletion of Admin Profile Failed",Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(AdminList.this,"Deletion of Admin Profile Successful",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        Toast.makeText(AdminList.this,"Deleted Successfully",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(AdminList.this,MainActivity.class).putExtra("ID","1"));
                                    }
                                }
                            });
                        }
                    });
                    alertDialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog1=alertDialog.create();
                    alertDialog1.show();
                }*/
            }
        });
    }
}
