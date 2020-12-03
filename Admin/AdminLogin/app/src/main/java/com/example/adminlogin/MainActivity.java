package com.example.adminlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;


public class
MainActivity extends AppCompatActivity {
    private TextView register,forgetp;
    private EditText textemail, textpass;
    Button login;
    String id;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
       // id=getIntent().getStringExtra("ID");
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        id = sharedPreferences.getString("id","");
        register = (TextView) findViewById(R.id.textviewreg);
        forgetp=(TextView)findViewById(R.id.textViewFP);
        register.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        forgetp.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        forgetp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                startActivity(new Intent(MainActivity.this,ResetPassword.class));
            }
        });
        textemail = (EditText) findViewById(R.id.editTextE);
        textpass = (EditText) findViewById(R.id.editTextPass);
        login = (Button) findViewById(R.id.buttonlogin);
        progressDialog = new ProgressDialog(this);
       if(id.equals("1")){
           // register.setEnabled(false);
        }
       else{
           DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("AdminDetails");
           databaseReference.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   if(dataSnapshot.exists())
                       register.setEnabled(false);
                   else
                       register.setEnabled(true);
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });
       }
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
       /* if(firebaseUser!=null) {
            finish();
            startActivity(new Intent(MainActivity.this,HomeNavigation.class));
        }
        */
        //    Toast.makeText(this,"Login Successfull",Toast.LENGTH_SHORT).show();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate() == 1)
                    loginUser();
                else
                    Toast.makeText(MainActivity.this, "Enter Details Properly!!!", Toast.LENGTH_SHORT).show();

            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterPage.class));
            }
        });

    }

    void loginUser() {
        progressDialog.setMessage("Signing In..");
        progressDialog.show();
        String email = textemail.getText().toString();
        String pass = textpass.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    userEmailVerification();

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Login Failed!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    int validate() {
        String email = textemail.getText().toString();
        String pass = textpass.getText().toString();
        if (email.isEmpty() || pass.isEmpty())
            return 0;
        else
            return 1;
    }

    private void userEmailVerification() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        boolean isVerified = firebaseUser.isEmailVerified();
        if (isVerified) {
            finish();
            register.setEnabled(false);
            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, HomeNavigation.class).putExtra("email", textemail.getText().toString());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Verify Your Email!", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}