package com.example.vstudent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class Home extends AppCompatActivity {
    private Button recentTrends,notice,exit,exam,timetable,course,attendance,query;
    private TextToSpeech textToSpeech;
    private int cnt1,cnt2,cnt3,cnt4,cnt5,cnt6,cnt7,cnt,result;
    private String studentclass,dept,division,studentID,sid;
    private DatabaseReference databaseReference,databaseReference1;
    private String queries;
    private long qid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        cnt=0;cnt1=0;cnt2=0;cnt3=0;cnt4=0;cnt5=0;cnt6=0;cnt7=0;qid=0;
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        //String value = sharedPreferences.getString("value","");
        studentID=sharedPreferences.getString("StudentID","");;
        if(studentID.equals("")){}
        sid=studentID;
        Log.e("StudentID",studentID);
        dept=sharedPreferences.getString("Department","");
        studentclass=sharedPreferences.getString("StudentClass","");
        division=sharedPreferences.getString("Division","");
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
                        speak("Press any button to start any activity");
                    }
                }
                else
                    Log.e("mytts","Initailaization Failed");
            }

        });

        recentTrends=(Button)findViewById(R.id.recenttrends);
        recentTrends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cnt==0){
                    speak("You have clicked on Recent Trends Button.Press Again to Confirm");
                    cnt=1;
                }
                else {
                    if(cnt==1){
                        speak("Know your College Recent Trends");
                        finish();
                        startActivity(new Intent(Home.this,RecentTrends.class));
                    }
                }

            }
        });
        notice=(Button)findViewById(R.id.notice);
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cnt1==0){
                    speak("You have clicked on Notice.Press again to confirm ");
                    cnt1=1;
                }
                else{
                    if(cnt1==1){
                        finish();
                        startActivity(new Intent(Home.this,CollegeNotice.class));
                    }
                }
            }
        });
        exit=(Button)findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cnt2==0){
                    speak("You have clicked on Exit button.Press again to confirm Exit");
                    cnt2=1;
                }
                else{
                    speak("Thank you for using College Voice Assistant App");
                    try {
                        Thread.sleep(3000);
                    }
                    catch (Exception e){
                        Log.e("Exception",e.toString());
                    }
                    if(cnt2==1){
                        int pid = android.os.Process.myPid();
                        android.os.Process.killProcess(pid);
                        finish();
                    }
                }
            }

        });
        exam=(Button)findViewById(R.id.exams);
        exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cnt3==0){
                    speak("You have clicked on Exam button.Press again to know when are your exams");
                    cnt3=1;
                }
                else{
                    if(cnt3==1){
                        finish();
                        startActivity(new Intent(Home.this,Exam.class).putExtra("Department",dept).putExtra("Class",studentclass));
                    }
                }
            }
        });
        timetable=(Button)findViewById(R.id.timetable);
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cnt4==0){
                    speak("You have clicked on Time Table button.Press again to know your today's Time Table");
                    cnt4=1;
                }
                else {
                    if(cnt4==1){
                        finish();
                        startActivity(new Intent(Home.this,TimeTable.class).putExtra("Department",dept).putExtra("Division",division));
                    }
                }
            }
        });
        course=(Button)findViewById(R.id.courses);
        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cnt5==0){
                    speak("You have clicked on Course button.Press again to know courses provided by your College");
                    cnt5=1;
                }
                else{
                    finish();
                    startActivity(new Intent(Home.this,Courses.class));
                }
            }
        });
     attendance=(Button)findViewById(R.id.attendance);
     attendance.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             if(cnt6==0){
                 speak("You have clicked on Check Attendance button.Press again to know your attendance");
                 cnt6=1;
             }
             else{
                 if(cnt6==1){
                     cnt6=0;
                     databaseReference= FirebaseDatabase.getInstance().getReference("Attendance");
                     databaseReference.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                             for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                 if (snapshot.getKey().equals(sid)) {
                                     Long attendance = snapshot.child("attendance").getValue(Long.class);
                                     if (attendance >= 75)
                                         speak("Great!Your attendance is above 75. It is " + attendance + "%");
                                     else
                                         speak("Your attendance is below 75. It is " + attendance + "% Please attend your classes");
                                 }
                             }
                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }
                     });
                 }
             }
         }
     });
     query=(Button)findViewById(R.id.query);
    query.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(cnt7==0){
                speak("You clicked on add query button.Press again to add query");
                cnt7=1;
            }
            else{
                if(cnt7==1){
                    //cnt7=0;

                   finish();
                    startActivity(new Intent(Home.this,AddQuery.class));

                }
            }
        }
    });
    }
    private void speak(String message){
        if(Build.VERSION.SDK_INT>=21) {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        textToSpeech.stop();
        textToSpeech.shutdown();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textToSpeech.stop();
        textToSpeech.shutdown();
    }
}
