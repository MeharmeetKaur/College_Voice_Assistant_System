package com.example.adminlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

public class UpdateStudent extends AppCompatActivity {

    private Spinner spinner;
    EditText spinnerDept,spinnerClass;
    private String[] classlist1 = {"Select Class", "FE1", "FE2", "FE3", "FE4", "FE5"};
    private String[] classlist2 = {"Select Class", "SE1", "SE2", "SE3", "SE4", "SE5"};
    private String[] classlist3 = {"Select Class", "TE1", "TE2", "TE3", "TE4", "TE5"};
    private String[] classlist4 = {"Select Class", "BE1", "BE2", "BE3", "BE4", "BE5"};
    private String str="";
    private EditText enrollno,firstname,middlename,lastname,email,mobile,div,department,password;
    private DatabaseReference databaseReference;
    private String rid,fname,mname,lname,emailid,mob,division,dept,pass;
    private String registrationID="";
    private Button button;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);
        spinnerClass=(EditText) findViewById(R.id.EdittextDivision);
        spinner=(Spinner)findViewById(R.id.spinnerClass);
        enrollno=(EditText)findViewById(R.id.registrationIdEditText);
        firstname=(EditText)findViewById(R.id.firstNameEditText);
        middlename=(EditText)findViewById(R.id.middleNmaeEditText);
        lastname=(EditText)findViewById(R.id.lastNameEditText);
        email=(EditText)findViewById(R.id.emailEditText);
        mobile=(EditText)findViewById(R.id.PhoneEditText);
        div=(EditText)findViewById(R.id.EdittextDivision);
        department=(EditText)findViewById(R.id.EdittextDept);
        password=(EditText)findViewById(R.id.passwordEditText);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(UpdateStudent.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Class));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinnerDept=(EditText) findViewById(R.id.EdittextDept);
        progressDialog = new ProgressDialog(this);
        //ArrayAdapter<String> arrayAdapterDept = new ArrayAdapter<String>(UpdateStudent.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Department));
        //arrayAdapterDept.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinnerDept.setAdapter(arrayAdapterDept);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                } else {
                    str = adapterView.getItemAtPosition(i).toString();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        firstname.setEnabled(false);
        middlename.setEnabled(false);
        lastname.setEnabled(false);
        email.setEnabled(false);
        mobile.setEnabled(false);
        div.setEnabled(false);
        department.setEnabled(false);
        password.setEnabled(false);
                   button=(Button)findViewById(R.id.updatebutton);
                   button.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           if (button.getText().equals("SEARCH")) {
                               progressDialog.setMessage("Searching Student..");
                               progressDialog.show();;
                               registrationID = enrollno.getText().toString();
                               databaseReference = FirebaseDatabase.getInstance().getReference("Student").child("Class").child(str).child(registrationID);
                               databaseReference.addValueEventListener(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                       rid = dataSnapshot.child("registerationID").getValue(String.class);
                                       if (rid==null) {
                                           progressDialog.dismiss();
                                           Toast.makeText(UpdateStudent.this, "There is no such Student Record!!!", Toast.LENGTH_SHORT).show();
                                       } else {
                                            enrollno.setText(rid);
                                            firstname.setText(dataSnapshot.child("firstname").getValue(String.class));
                                            middlename.setText(dataSnapshot.child("middlename").getValue(String.class));
                                            lastname.setText(dataSnapshot.child("lastname").getValue(String.class));
                                            email.setText(dataSnapshot.child("emailID").getValue(String.class));
                                            mobile.setText(dataSnapshot.child("phoneNo").getValue(String.class));
                                            div.setText(dataSnapshot.child("division").getValue(String.class));
                                            department.setText(dataSnapshot.child("dept").getValue(String.class));
                                            password.setText(dataSnapshot.child("password").getValue(String.class));
                                            progressDialog.dismiss();
                                           firstname.setEnabled(true);
                                           middlename.setEnabled(true);
                                           lastname.setEnabled(true);
                                           email.setEnabled(true);
                                           mobile.setEnabled(true);
                                           div.setEnabled(true);
                                           department.setEnabled(true);
                                           password.setEnabled(true);
                                            button.setText("UPDATE");
                                       }

                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError databaseError) {
                                   }
                               });

                           }
                           else {
                               registrationID = enrollno.getText().toString();
                               databaseReference=FirebaseDatabase.getInstance().getReference("Student").child("Class").child(str).child(registrationID);
                               fname=firstname.getText().toString();
                               mname=middlename.getText().toString();
                               lname=lastname.getText().toString();
                               emailid=email.getText().toString();
                               mob=mobile.getText().toString();
                               division=div.getText().toString();
                               dept=department.getText().toString();
                               pass=password.getText().toString();
                               StudentProfile studentProfile=new StudentProfile(registrationID,str,fname,mname,lname,emailid,mob,division,dept,pass);
                               databaseReference.setValue(studentProfile);
                               Toast.makeText(UpdateStudent.this,"Updated Successfully!!",Toast.LENGTH_SHORT).show();
                               finish();
                           }
                       }
                   });




    }
}
