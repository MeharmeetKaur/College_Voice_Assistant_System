package com.example.vstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class Exam extends AppCompatActivity {
    private String studentRID,dept,studentclass;
    private TextToSpeech textToSpeech;
    private int result;
    private String data;
    private DatabaseReference databaseReference;
    private TextView textView;
    private ArrayList<String>subjects;
    private ArrayList<String>exams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        subjects=new ArrayList<>();
        exams=new ArrayList<>();
        dept=getIntent().getStringExtra("Department");
        studentclass=getIntent().getStringExtra("Class");
        data="";
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
        readData(new Exam.MyCallback() {
            @Override
            public void onCallback(String value,String examSchedule) {
                data=data+" Exam is "+value+" on\n"+examSchedule+"\n";
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

    public void readData(final Exam.MyCallback myCallback) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Exam");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value1,mainExam,examSchedule="";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    mainExam=snapshot.getKey();
                    Log.e("MAIN EXAM",mainExam);
                    if(snapshot.hasChild(dept)){
                        DataSnapshot data=snapshot.child(dept);
                        Log.e("Department",data.getKey());
                        //value1=snapshot.child(dept).getKey();
                        //Log.e("Child",value1);
                        for(DataSnapshot data1:data.getChildren()) {
                            Log.e("Class", data1.getKey());
                            // DataSnapshot data=snapshot.child(studentclass);
                            //for (DataSnapshot classes:data1.getChildren()) {
                            if(data1.getKey().equals(studentclass)){
                                Log.e("KNOW CLASS",data1.getKey());
                                    Long i = data1.getChildrenCount();
                                if (i != 0) {
                                    Log.e("Msg", "Has children" + data1.getChildrenCount());
                                    for (DataSnapshot snapshot1 : data1.getChildren()) {
                                        String date = snapshot1.getKey();
                                        Log.e("Date", date);
                                        String exam = snapshot1.child("subject").getValue(String.class);
                                        examSchedule = examSchedule + date + " and subject is " + exam + "\n";
                                    }
                                    myCallback.onCallback(mainExam,examSchedule);
                                }
                            }
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public interface MyCallback {
        void onCallback(String value,String examSchedule);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(Exam.this,Home.class));
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
