


package com.example.adminlogin;

import androidx.appcompat.app.AppCompatActivity;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class RecentTrends extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatePickerDialog.OnDateSetListener setListener;
    DatabaseReference reff;
    Button adbtn;
    EditText acteditText;
    RTrends rtrend;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_trends);

        final Spinner spinner=(Spinner)findViewById(R.id.spinner);
        acteditText=findViewById(R.id.acteditText);
        adbtn=(Button)findViewById(R.id.adbtn);
        reff= FirebaseDatabase.getInstance().getReference("Recent_Trends");


        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.groupName,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



        final TextView dateEditText=(TextView) findViewById(R.id.dateEditText);

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(RecentTrends.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month +1;
                      //  String date=day+"-"+month+"-"+year;
                        dateEditText.setText(day+"-"+month+"-"+year);

                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        adbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // String id=reff.push().getKey();
                final String gname = spinner.getSelectedItem().toString();
                String act=acteditText.getText().toString();
                String date=dateEditText.getText().toString();
                if (act.matches("")) {
                    Toast.makeText(RecentTrends.this, "You did not enter activity", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (date.matches("")) {
                    Toast.makeText(RecentTrends.this, "You did not enter date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (gname.matches("")) {
                    Toast.makeText(RecentTrends.this, "You did not select groupname", Toast.LENGTH_SHORT).show();
                    return;
                }
                rtrend=new RTrends(act,date);
                reff.child(gname).setValue(rtrend);
                //reff.child(id).setValue(rtrend);

                Toast.makeText(RecentTrends.this,"Added Successfully",Toast.LENGTH_SHORT).show();
            }
        });



    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String gname=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



}
