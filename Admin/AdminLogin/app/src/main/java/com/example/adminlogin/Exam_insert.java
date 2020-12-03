package com.example.adminlogin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Exam_insert extends AppCompatActivity {

    Spinner deptSpinner;
    Spinner classSpinner;
   // Spinner daySpinner;
   // Spinner timeSpinner;
    TextView examtextView;
    DatabaseReference dref,dref_1,dref_2,dref_3,dref_4;
    EditText subjectEditText,dateeditText;
    Button addButton;
    Exam_1 exam_1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_insert);

        examtextView=(TextView)findViewById(R.id.examtextView);
        Bundle bundle=getIntent().getExtras();
        String exam_name=bundle.getString("exam_name");
        examtextView.setText(exam_name);

        addButton=(Button)findViewById(R.id.addButton);
        subjectEditText=(EditText)findViewById(R.id.subjectEditText);

        deptSpinner=(Spinner)findViewById(R.id.deptSpinner);
        classSpinner=(Spinner)findViewById(R.id.classSpinner);
       // daySpinner=(Spinner)findViewById(R.id.daySpinner);
        //timeSpinner=(Spinner)findViewById(R.id.timeSpinner);

        dateeditText=(EditText) findViewById(R.id.dateeditText);

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        dateeditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(Exam_insert.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month +1;
                       // String date=day+"/"+month+"/"+year;
                        dateeditText.setText(day+"-"+month+"-"+year);

                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        ArrayAdapter<CharSequence> deptAdapterClass = ArrayAdapter.createFromResource(this, R.array.Dept,android.R.layout.simple_spinner_item);
        deptAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deptSpinner.setAdapter(deptAdapterClass);




        deptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected (AdapterView< ? > adapterView, View view, int i, long l) {

                if (i == 0) {
                    ArrayAdapter<CharSequence> arrayAdapterClass = ArrayAdapter.createFromResource(Exam_insert.this, R.array.feClass, android.R.layout.simple_spinner_item);
                    arrayAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    classSpinner.setAdapter(arrayAdapterClass);
                } else if (i == 1||i==2||i==3) {

                    ArrayAdapter<CharSequence> arrayAdapterClass = ArrayAdapter.createFromResource(Exam_insert.this, R.array.eClass, android.R.layout.simple_spinner_item);
                    arrayAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    classSpinner.setAdapter(arrayAdapterClass);
                }

            }
            @Override
            public void onNothingSelected (AdapterView < ? > adapterView){

            }
        });
        dref= FirebaseDatabase.getInstance().getReference("Exam");

    addButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final String department=deptSpinner.getSelectedItem().toString();
            final String classname=classSpinner.getSelectedItem().toString();
           // final String day=daySpinner.getSelectedItem().toString();
            final String subject=subjectEditText.getText().toString();
          //  final  String time=timeSpinner.getSelectedItem().toString();
            final String exam=examtextView.getText().toString();
            final String date=dateeditText.getText().toString();
            if (department.matches("")) {
                Toast.makeText(Exam_insert.this, "You did not enter deaprtment", Toast.LENGTH_SHORT).show();
                return;
            }
            if (classname.matches("")) {
                Toast.makeText(Exam_insert.this, "You did not enter Class", Toast.LENGTH_SHORT).show();
                return;
            }
            if (subject.matches("")) {
                Toast.makeText(Exam_insert.this, "You did not enter Subject", Toast.LENGTH_SHORT).show();
                return;
            }

            if (date.matches("")) {
                Toast.makeText(Exam_insert.this, "You did not enter date", Toast.LENGTH_SHORT).show();
                return;
            }

            exam_1=new Exam_1(subject);


            dref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(exam)) {
                        dref_4 = dref.child(exam);
                        dref_4.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.hasChild(department)) {
                                    dref_1 = dref_4.child(department);
                                    dref_1.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.hasChild(classname)) {
                                                dref_2 = dref_1.child(classname);
                                                dref_2.child(date).setValue(exam_1);


                                            }
                                            else {
                                                dref_1.child(classname).child(date).setValue(exam_1);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                } else {
                                    dref_4.child(department).child(classname).child(date).setValue(exam_1);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    else
                    {
                        dref.child(exam).child(department).child(classname).child(date).setValue(exam_1);

                    }
                }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });






            Toast.makeText(Exam_insert.this,"Added Successfully",Toast.LENGTH_SHORT).show();
        }
    });

    }
}
