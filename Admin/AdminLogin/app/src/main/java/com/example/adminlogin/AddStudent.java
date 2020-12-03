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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddStudent extends AppCompatActivity {
    private Spinner spinner, spinnerClass, spinnerDept;
    private String registerationID1;
    private String[] classlist1 = {"Select Class", "FE1", "FE2", "FE3", "FE4", "FE5"};
    private String[] classlist2 = {"Select Class", "SE1", "SE2", "SE3", "SE4", "SE5"};
    private String[] classlist3 = {"Select Class", "TE1", "TE2", "TE3", "TE4", "TE5"};
    private String[] classlist4 = {"Select Class", "BE1", "BE2", "BE3", "BE4", "BE5"};
    private EditText editTextFName, editTextMName, editTextLName, editTextEmail, editTextPhone;
    private Button add;
    private DatabaseReference obj;
    private FirebaseAuth firebaseAuth;
    private StudentProfile student;
    private TextView enrollNo;
    private EditText password;
    private long rid = 0;
    String str = null;
    String srid = null;
    String sclass=null;
    String name = null;
    String department = null;
    String emailid = "";
    String phone = "";
    long reg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        spinner = (Spinner) findViewById(R.id.spinnerClass);
        enrollNo = (TextView) findViewById(R.id.registrationIdEditText);
        spinnerDept = (Spinner) findViewById(R.id.SpinnerDept);
        spinnerClass = (Spinner) findViewById(R.id.SpinnerDivision);
        editTextFName = (EditText) findViewById(R.id.firstNameEditText);
        editTextMName = (EditText) findViewById(R.id.middleNmaeEditText);
        editTextLName = (EditText) findViewById(R.id.lastNameEditText);
        editTextEmail = (EditText) findViewById(R.id.emailEditText);
        editTextPhone = (EditText) findViewById(R.id.PhoneEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        add = (Button) findViewById(R.id.buttonupdate);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddStudent.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Class));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        ArrayAdapter<String> arrayAdapterDept = new ArrayAdapter<String>(AddStudent.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Department));
        arrayAdapterDept.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDept.setAdapter(arrayAdapterDept);

        // spinner.setPrompt("YEAR");

        //ArrayAdapter<String> arrayAdapterDivision = new ArrayAdapter<String>(AddStudent.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Division));
        //arrayAdapterDivision.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinnerDivision.setAdapter(arrayAdapterDivision);
       // String classofStudent=chooseClass();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){}
                else {
                    if (i == 1) {
                        ArrayAdapter<String> arrayAdapterClass = new ArrayAdapter<String>(AddStudent.this, android.R.layout.simple_spinner_item, classlist1);
                        arrayAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerClass.setAdapter(arrayAdapterClass);
                    }
                    else if (i == 2) {
                        ArrayAdapter<String> arrayAdapterClass = new ArrayAdapter<String>(AddStudent.this, android.R.layout.simple_spinner_item, classlist2);
                        arrayAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerClass.setAdapter(arrayAdapterClass);
                    }
                    else if (i == 3) {
                        ArrayAdapter<String> arrayAdapterClass = new ArrayAdapter<String>(AddStudent.this, android.R.layout.simple_spinner_item, classlist3);
                        arrayAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerClass.setAdapter(arrayAdapterClass);
                    } else if (i == 4) {
                        ArrayAdapter<String> arrayAdapterClass = new ArrayAdapter<String>(AddStudent.this, android.R.layout.simple_spinner_item, classlist4);
                        arrayAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerClass.setAdapter(arrayAdapterClass);
                    }
                    str = adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(AddStudent.this,str,Toast.LENGTH_SHORT).show();
                    firebaseAuth=FirebaseAuth.getInstance();
                    obj=FirebaseDatabase.getInstance().getReference("Student").child("Class").child(str);
                    obj.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                rid = dataSnapshot.getChildrenCount();
                            }
                            if(rid==0){
                                srid=str+"000001";
                                enrollNo.setText(srid);
                            }
                            else {
                                //srid=dataSnapshot.child("registerationID").getValue(String.class);
                                //StudentProfile s=dataSnapshot.getValue(StudentProfile.class);
                                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                    StudentProfile user = childSnapshot.getValue(StudentProfile.class);
                                    srid=user.getRegisterationID();
                                }
                                Long convertStoL = Long.parseLong(srid.substring(2,srid.length()));
                                convertStoL++;
                                String x = String.format("%06d", convertStoL);
                                String convertLtoS = str + x;
                                enrollNo.setText(convertLtoS);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected (AdapterView < ? > adapterView, View view,int i, long l){
                sclass = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected (AdapterView < ? > adapterView){

            }
        });
        spinnerDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected (AdapterView < ? > adapterView, View view,int i, long l){
                department = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected (AdapterView < ? > adapterView){

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enrollNumber = enrollNo.getText().toString();
                String studentFirstName = editTextFName.getText().toString();
                String studentMiddleName=editTextMName.getText().toString();
                String studentLastname=editTextLName.getText().toString();
                String studentEmail = editTextEmail.getText().toString();
                String studentPhone = editTextPhone.getText().toString();
                String spassword = password.getText().toString();
                if (str.isEmpty() || studentFirstName.isEmpty() || studentEmail.isEmpty() || studentPhone.isEmpty() || sclass.isEmpty() || department.isEmpty() ||
                        enrollNumber.isEmpty() || (!spassword.equals("123456") && spassword.isEmpty())) {
                    Toast.makeText(AddStudent.this, "Enter Details Properly or Enter Correct Password!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String id = obj.push().getKey();
                    StudentProfile student = new StudentProfile(enrollNumber,str, studentFirstName,studentMiddleName,studentLastname ,studentEmail,studentPhone, sclass, department,  spassword);
                    obj.child(enrollNumber).setValue(student);
                    finish();
                    Toast.makeText(AddStudent.this, "Added Successfully!!!", Toast.LENGTH_SHORT).show();
                    finish();
                }

                }
        });

    }
}


