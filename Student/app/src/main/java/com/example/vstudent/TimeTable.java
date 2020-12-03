package com.example.vstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Locale;

public class TimeTable extends AppCompatActivity {
    private TextView textView;
    private TextToSpeech textToSpeech;
    private int result;
    private DatabaseReference databaseReference;
    private String dept,division,day;
    private String tt,data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        dept=getIntent().getStringExtra("Department");
        Log.e("Department",dept);
        division=getIntent().getStringExtra("Division");
        Log.e("Division",division);
        day=getDay();
        data="";
        Log.e("Day",day);
        tt="";
        textView=(TextView)findViewById(R.id.editText);
        textToSpeech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    result = textToSpeech.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("mytts", "Language not supported");
                        finish();
                    }
                    else {
                        // speak();
                    }
                }
                else
                    Log.e("mytts","Initailaization Failed");
            }

        });
        readData(new MyCallback() {
            @Override
            public void onCallback(String value) {
                data=data+" Your today's schedule is \n"+value;
                textView.setText(data);
                speak();
            }
        });
       textView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               speak();
           }
       });

    }
    public void readData(final TimeTable.MyCallback myCallback) {
       databaseReference = FirebaseDatabase.getInstance().getReference("TimeTable").child(dept).child(division).child(day);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                        String timetable = dataSnapshot1.getValue(String.class);
                        tt = tt + timetable + "\n";
                    }
                    myCallback.onCallback(tt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
       private void speak(){
        String message=textView.getText().toString();
        message=message+"Press anywhere to listen again.";
        if(Build.VERSION.SDK_INT>=21) {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
    public interface MyCallback {
        void onCallback(String value);
    }
    private String getDay(){
        String dayname="";
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                dayname="Sunday";
                break;
            case Calendar.MONDAY:
                dayname="Monday";
                break;
            case Calendar.TUESDAY:
                dayname="Tuesday";
                break;
            case Calendar.WEDNESDAY:
                dayname="Wednesday";
                break;
            case Calendar.THURSDAY:
                dayname="Thursday";
                break;
            case Calendar.FRIDAY:
                dayname="Friday";
                break;
            case Calendar.SATURDAY:
                dayname="Saturday";
                break;
        }
        return dayname;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(TimeTable.this,Home.class));
    }
    @Override
    protected void onDestroy() {
        if(textToSpeech!=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();

    }
}
