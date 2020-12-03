package com.example.adminlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Notice extends AppCompatActivity {

    EditText neditText,ideditText;
    Button addbtn;
    DatabaseReference reff;
    Ncontent ncontent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        neditText=(EditText)findViewById(R.id.ieditText);
        ideditText=(EditText)findViewById(R.id.ideditText);
        addbtn=(Button)findViewById(R.id.addbtn);

        reff=FirebaseDatabase.getInstance().getReference("Ncontent");
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // String id=reff.push().getKey();
                String id=ideditText.getText().toString();
                String notice=neditText.getText().toString();
                if (id.matches("")) {
                    Toast.makeText(Notice.this, "You did not enter an id", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (notice.matches("")) {
                    Toast.makeText(Notice.this, "You did not enter a notice", Toast.LENGTH_SHORT).show();
                    return;
                }
                ncontent=new Ncontent(notice);

                reff.child(id).setValue(ncontent);
                // reff.push().setValue(ncontent);
                Toast.makeText(Notice.this,"Added Successfully",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
