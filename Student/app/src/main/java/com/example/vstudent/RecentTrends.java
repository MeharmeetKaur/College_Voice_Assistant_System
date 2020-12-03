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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class RecentTrends extends AppCompatActivity {
    private TextToSpeech textToSpeech;
    private DatabaseReference databaseReference;
    private String data;
    private TextView textView;
    private  int result,temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_trends);
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

        textView=(TextView)findViewById(R.id.editText);
        data="";
        readData(new MyCallback() {
            @Override
            public void onCallback(String key, String value, String value1) {
                data = data + key + " has " + value + " on date " + value1 + "\n";
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
    public void readData(final MyCallback myCallback) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Recent_Trends");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value1,value2,key;
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    key=snapshot.getKey();
                    value1=snapshot.child("act").getValue(String.class);
                    value2=snapshot.child("date").getValue(String.class);
                    myCallback.onCallback(key,value1,value2);
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
        void onCallback(String key,String value,String value1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(RecentTrends.this,Home.class));
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

