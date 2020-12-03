package com.example.adminlogin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Exam_Update extends AppCompatActivity {

    private EditText deptEditText,clsEditText,dayEditText,subEditText,subeditText,timeeditText,valeditText;
    Button searchButton,delButton;
    private String dt_name,cls_name,day_name,s_name,Time,e_name;
    TextView examtextView;
    Spinner enamespinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam__update);

        enamespinner=(Spinner)findViewById(R.id.enameSpinner);

        ArrayAdapter<CharSequence> deptAdapterClass = ArrayAdapter.createFromResource(this, R.array.ename,android.R.layout.simple_spinner_item);
        deptAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        enamespinner.setAdapter(deptAdapterClass);

        deptEditText=(EditText)findViewById(R.id.deptEditText);
        clsEditText=(EditText)findViewById(R.id.clsEditText);
        dayEditText=(EditText)findViewById(R.id.dayEditText);
        subEditText=(EditText)findViewById(R.id.subEditText);
        timeeditText=(EditText)findViewById(R.id.timeeditText);
        //delButton=(Button)findViewById(R.id.delButton);
        //examtextView=(TextView) findViewById(R.id.examtextView);
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        dayEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(Exam_Update.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month +1;
                        // String date=day+"/"+month+"/"+year;
                        dayEditText.setText(day+"-"+month+"-"+year);

                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


        searchButton=(Button)findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                dt_name=deptEditText.getText().toString();
                cls_name=clsEditText.getText().toString();
                day_name=dayEditText.getText().toString();
               // s_name=subEditText.getText().toString();
                e_name=enamespinner.getSelectedItem().toString();

                DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Exam").child(e_name).child(dt_name).child(cls_name).child(day_name);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                           // Toast.makeText(Exam_Update.this, "Search clicked", Toast.LENGTH_SHORT).show();
                           String s_name=dataSnapshot.child("subject").getValue(String.class);
                            timeeditText.setText(s_name);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("onCancelled", databaseError.toException());

                    }
                });

            }
        });
        timeeditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });


    }
    public void openDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(Exam_Update.this);
        View view=getLayoutInflater().inflate(R.layout.updat_dial,null);

        Button updatButton=(Button)view.findViewById(R.id.updatButton);
        valeditText=(EditText)view.findViewById(R.id.valeditText);

        updatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String new_sub=valeditText.getText().toString();
                // int cid=Integer.parseInt(id);
                // c_name=nameEditText.getText().toString();
                //String c_id=ieditText.getText().toString();

                if(TextUtils.isEmpty(new_sub))
                {
                    valeditText.setError("Course required");
                }
                updateTime(new_sub);
                Toast.makeText(Exam_Update.this, "clicked", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setView(view);

        AlertDialog dialog=builder.create();
        dialog.show();



    }
    private void updateTime(String new_sub )
    {

        dt_name=deptEditText.getText().toString();
        cls_name=clsEditText.getText().toString();
        day_name=dayEditText.getText().toString();
       // s_name=subEditText.getText().toString();
        e_name=enamespinner.getSelectedItem().toString();

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Exam").child(e_name).child(dt_name).child(cls_name).child(day_name);
        Exam_1 exam_1=new Exam_1(new_sub);
        ref.setValue(exam_1);

        Toast.makeText(Exam_Update.this, "updated", Toast.LENGTH_SHORT).show();
    }
}
