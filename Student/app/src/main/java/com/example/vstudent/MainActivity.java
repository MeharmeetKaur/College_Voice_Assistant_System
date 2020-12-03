package com.example.vstudent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech textToSpeech;
    private int result;
    private ArrayList<String> strlist;
    private EditText editTextRegistrtionID;
    private ImageButton microphone;
    private ProgressDialog progressDialog;
    private String rid="";
    private String password="";
    private String classes="";
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        strlist=new ArrayList<String>();
        progressDialog=new ProgressDialog(this);
        editTextRegistrtionID=(EditText)findViewById(R.id.editTextRID);
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
                       speak("Welcome to College Assistant App.Press corner microphone button to enter Registration ID");
                    }
                }
                else
                    Log.e("mytts","Initailaization Failed");
            }


        });
        microphone=(ImageButton)findViewById(R.id.imageButton4);
        microphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something");
                try{
                    startActivityForResult(intent,5);

                }
                catch(ActivityNotFoundException a){
                    Toast.makeText(getApplicationContext(),"Your Device Don't Support Speech Input",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 5:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String rid=results.get(0);
                    rid=rid.toUpperCase();
                    rid=rid.replaceAll(" ","");
                    editTextRegistrtionID.setText(rid);
                    rid=editTextRegistrtionID.getText().toString();
                    validate(rid);
                }
                break;
        }

    }


    private void speak(String message){
        if(Build.VERSION.SDK_INT>=21) {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
    private void validate(final String rid){
        if(rid==null){
           speak("Enter Registration ID Properly");
        }
        else{
          //  rid=rid.toUpperCase();
          //  rid=rid.replaceAll(" ","");
          //  Toast.makeText(MainActivity.this,"ReID"+rid,Toast.LENGTH_SHORT).show();
            if(rid.length()==1 || rid.length()==2){
                speak("Enter Registration ID Properly");
            }
            else {
                String sclass = rid.substring(0, 2);
              //  Toast.makeText(MainActivity.this, sclass, Toast.LENGTH_SHORT).show();
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Student").child("Class").child(sclass).child(rid);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         id = dataSnapshot.child("registerationID").getValue(String.class);
                        String dept=dataSnapshot.child("dept").getValue(String.class);
                        String studentclass=dataSnapshot.child("sclass").getValue(String.class);
                        String division=dataSnapshot.child("division").getValue(String.class);
                        SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("StudentID", id);
                        editor.putString("Department",dept);
                        editor.putString("Division",division);
                        editor.putString("StudentClass",studentclass);
                        editor.apply();
                        if (id == null)
                            speak("No such Student");
                        else {
                            if(rid.equals(id)) {
                                speak("Login Successful");
                                try {
                                    Thread.sleep(2000);
                                } catch (Exception e) {
                                    Log.e("Exception", e.toString());
                                }
                               finish();
                                startActivity(new Intent(MainActivity.this, Home.class));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textToSpeech.stop();
        textToSpeech.shutdown();
    }

}

