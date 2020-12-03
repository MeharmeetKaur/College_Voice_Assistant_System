package com.example.adminlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
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


public class RegisterPage extends AppCompatActivity {
    TextView login;
    Button register;
    EditText textEmail,textPass,textCP;
    FirebaseAuth firebaseAuth;
    Tag id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        login = (TextView) findViewById(R.id.textView3);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage.this,MainActivity.class));
                finish();
            }
        });
        register = (Button) findViewById(R.id.buttonRegister);
        textEmail = (EditText) findViewById(R.id.editTextemail);
        textPass = (EditText) findViewById(R.id.editTextPass);
        textCP = (EditText) findViewById(R.id.editTextCP);
        firebaseAuth=FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()==1){
                    registerUser();

                }
                else
                    Toast.makeText(RegisterPage.this,"Registeration Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void registerUser(){
        String email=textEmail.getText().toString().trim();
        String pass=textPass.getText().toString().trim();
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                   sendEmailVerification();
                }
                else {
                    Log.w("msg", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(RegisterPage.this,"Something went wrong!Registeration failed!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private int validate(){
        String email=textEmail.getText().toString();
        String pass=textPass.getText().toString();
        String cp=textCP.getText().toString();
        if(email.isEmpty() || pass.isEmpty() || cp.isEmpty()){
            Toast.makeText(this,"Enter Creditionals Properly",Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if(!pass.equals(cp)){
            Toast.makeText(this,"Password did not Match",Toast.LENGTH_SHORT).show();
            return -1;
        }
        else
            return 1;
    }
    void sendEmailVerification(){
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterPage.this,"Registeration Successful!!Verify your Email by checking Inbox!!!",Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("id", "1");
                        editor.apply();
                        finish();
                        startActivity(new Intent(RegisterPage.this,MainActivity.class));
                    }
                    else {
                        Toast.makeText(RegisterPage.this,"Verification Email not Send!!Check your Internet Connection..",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
