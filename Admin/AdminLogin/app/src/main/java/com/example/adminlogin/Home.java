package com.example.adminlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {
    private String s;
    private TextView viewEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        s=getIntent().getStringExtra("email");
        //Toast.makeText(this,s,Toast.LENGTH_SHORT).show();

    }
}
