package com.example.vstudent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
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

public class AddQuery extends AppCompatActivity {
    private TextView textView;
    private String queries;
    private TextToSpeech textToSpeech,textToSpeech1;
    private Long qid;
    private int result,cnt;
    private long maxid=0;
    private DatabaseReference databaseReference,databaseReference1;
    private AddingQuery addingQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_query);
        cnt=0;
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
                        speak1("Tap anywhere to add your query.Tap again to save query.");
                    }
                }
                else
                    Log.e("mytts","Initailaization Failed");
            }

        });
        databaseReference1=FirebaseDatabase.getInstance().getReference().child("Queries");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    qid=dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        textView=(TextView)findViewById(R.id.editText);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cnt==0) {
                    speak1("Say your problem");
                    try{
                        Thread.sleep(2000);}
                    catch (Exception e){}
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Add your query");
                    try {
                        startActivityForResult(intent, 5);

                    } catch (ActivityNotFoundException a) {
                        Toast.makeText(getApplicationContext(), "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
                    }
                    cnt=1;
                }
                else{
                    databaseReference1.child(String.valueOf(qid+1)).setValue(textView.getText().toString());
                    cnt=0;
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
                    speak1("Saving your Query.Hope to get it cleared soon.");
                    try {
                        Thread.sleep(3000);
                    }catch(Exception e){}
                    ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    queries=results.get(0);
                    textView.setText(queries);
                    //rid=rid.toUpperCase();
                    //rid=rid.replaceAll(" ","");
                    // try{
                    //   Thread.sleep(5000);
                    //}catch(Exception e){}
                    //speak("Your query is "+rid);
                    // rid=editTextRegistrtionID.getText().toString();
                    //validate(rid);
                }
                break;
        }

    }

    /* public void validate(String s){
        Log.e("Message",s);
        if(!s.equals("")){
            speak();
        }
    }*/
    private void speak(){
        String message=textView.getText().toString();
        addingQuery=new AddingQuery(message);
        message=message+"Tap back to save your query or tap anywhere to listen again your query";
        if(Build.VERSION.SDK_INT>=21) {
            textToSpeech.speak(message, TextToSpeech.QUEUE_ADD, null, null);
        }
        else {
            textToSpeech.speak(message, TextToSpeech.QUEUE_ADD, null);
        }
    }
    private void speak1(String message){
        if(Build.VERSION.SDK_INT>=21) {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(AddQuery.this,Home.class));

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
