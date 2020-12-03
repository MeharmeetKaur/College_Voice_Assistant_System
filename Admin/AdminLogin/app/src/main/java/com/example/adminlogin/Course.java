package com.example.adminlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Course extends AppCompatActivity {
    EditText cIdEditText,nameEditText,detailEditText;
    Button adbtn;
    DatabaseReference reff;
    course_1 crc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        cIdEditText=(EditText)findViewById(R.id.cIdEditText);
        nameEditText=(EditText)findViewById(R.id.nameEditText);
        detailEditText=(EditText)findViewById(R.id.detailEditText);
        adbtn=(Button)findViewById(R.id.adbtn);
        reff= FirebaseDatabase.getInstance().getReference("Course");
        adbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // String id=reff.push().getKey();
                int cid=Integer.parseInt(cIdEditText.getText().toString().trim());
                String name=nameEditText.getText().toString();
                String detail=detailEditText.getText().toString();

                if (name.matches("")) {
                    Toast.makeText(Course.this, "You did not enter Course name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (detail.matches("")) {
                    Toast.makeText(Course.this, "You did not enter Course details", Toast.LENGTH_SHORT).show();
                    return;
                }

                crc=new course_1(cid,detail);
                reff.child(name).setValue(crc);

                Toast.makeText(Course.this,"Added Successfully",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
