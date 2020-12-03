package com.example.adminlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TimeTable extends AppCompatActivity {

    Spinner deptSpinner;
    Spinner classSpinner;
    Spinner daySpinner;
    //Spinner timeSpinner;
    // Button addButton;
    EditText subjectEditText;
    Time_1 time_1;
    DatabaseReference dreff,dreff_1,dreff_2,dreff_3;
    ArrayList<String>subjects;
    long max_id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        deptSpinner=(Spinner)findViewById(R.id.deptSpinner);
        classSpinner=(Spinner)findViewById(R.id.classSpinner);
        daySpinner=(Spinner)findViewById(R.id.daySpinn);
        //  timeSpinner=(Spinner)findViewById(R.id.timeSpinn);
        Button addButton=(Button)findViewById(R.id.addButton);
        Button saveButton=(Button)findViewById(R.id.saveButton);
        //Button addMoreButton=(Button)findViewById(R.id.addMoreButton);
        subjectEditText=(EditText)findViewById(R.id.subjectEditText);

        subjects = new ArrayList<>();

        ArrayAdapter<CharSequence> deptAdapterClass = ArrayAdapter.createFromResource(this,R.array.Dept,android.R.layout.simple_spinner_item);
        deptAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deptSpinner.setAdapter(deptAdapterClass);

        ArrayAdapter<CharSequence> dayAdapterClass = ArrayAdapter.createFromResource(this,R.array.Day,android.R.layout.simple_spinner_item);
        dayAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapterClass);

        deptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected (AdapterView < ? > adapterView, View view, int i, long l) {

                if (i == 0) {
                    ArrayAdapter<CharSequence> arrayAdapterClass = ArrayAdapter.createFromResource(TimeTable.this,R.array.feClass, android.R.layout.simple_spinner_item);
                    arrayAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    classSpinner.setAdapter(arrayAdapterClass);
                } else if (i == 1) {

                    ArrayAdapter<CharSequence> arrayAdapterClass = ArrayAdapter.createFromResource(TimeTable.this, R.array.CompClass, android.R.layout.simple_spinner_item);
                    arrayAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    classSpinner.setAdapter(arrayAdapterClass);
                } else if (i == 2) {

                    ArrayAdapter<CharSequence> arrayAdapterClass = ArrayAdapter.createFromResource(TimeTable.this, R.array.EntcClass, android.R.layout.simple_spinner_item);
                    arrayAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    classSpinner.setAdapter(arrayAdapterClass);
                } else if (i == 3) {

                    ArrayAdapter<CharSequence> arrayAdapterClass = ArrayAdapter.createFromResource(TimeTable.this, R.array.ITClass, android.R.layout.simple_spinner_item);
                    arrayAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    classSpinner.setAdapter(arrayAdapterClass);
                }

            }
            @Override
            public void onNothingSelected (AdapterView < ? > adapterView){

            }




        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sub=subjectEditText.getText().toString();
                subjects.add(sub);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String department=deptSpinner.getSelectedItem().toString();
                final String classname=classSpinner.getSelectedItem().toString();
                final String day=daySpinner.getSelectedItem().toString();
                final String subject=subjectEditText.getText().toString();

                if (department.matches("")) {
                    Toast.makeText(TimeTable.this, "You did not enter Department", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (classname.matches("")) {
                    Toast.makeText(TimeTable.this, "You did not enter Class", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (day.matches("")) {
                    Toast.makeText(TimeTable.this, "You did not enter Day", Toast.LENGTH_SHORT).show();
                    return;
                }


                // max_id=0;
                //  eu_arr = new ArrayList<>();
//                final  String time=timeSpinner.getSelectedItem().toString();
                //  eu_arr.add(subject);
                final DatabaseReference dreff= FirebaseDatabase.getInstance().getReference("TimeTable");



                time_1=new Time_1(subjects);
                dreff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(department))
                        {
                            final DatabaseReference dreff_1=dreff.child(department);
                            dreff_1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild(classname))
                                    {
                                        final DatabaseReference dreff_2=dreff_1.child(classname);
                                        dreff_2.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.hasChild(day))
                                                {
                                                    //max_id=(dataSnapshot.getChildrenCount());

                                                    final DatabaseReference dreff_3=dreff_2.child(day);
                                                    dreff_3.setValue(time_1);

                                                }
                                                else
                                                {
                                                    dreff_2.child(day).setValue(time_1);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                    else
                                    {
                                        dreff_1.child(classname).child(day).setValue(time_1);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        else
                        {
                            dreff.child(department).child(classname).child(day).setValue(time_1);                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //  dreff.child(department).child(classname).child(day).child(subject).setValue(time_1);

                Toast.makeText(TimeTable.this,"Added Successfully",Toast.LENGTH_SHORT).show();

            }
        });


    }

}
