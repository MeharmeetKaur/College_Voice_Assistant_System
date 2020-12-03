package com.example.adminlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {
    private EditText editTextChangePassword;
    private Button buttonChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        editTextChangePassword=(EditText)findViewById(R.id.editTextChangePassword);
        buttonChangePassword=(Button)findViewById(R.id.buttonChangePassword);
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password=editTextChangePassword.getText().toString();
                FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                firebaseUser.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ChangePassword.this,"Password Changed Successfully!",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                            Toast.makeText(ChangePassword.this,"Password Change Failed!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
