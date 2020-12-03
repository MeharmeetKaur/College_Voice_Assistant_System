package com.example.vstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Locale;

public class CollegeNotice extends AppCompatActivity {
    private TextView textView;
    private TextToSpeech textToSpeech;
    private int result;
    private String data;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_notice);
        textView=(TextView)findViewById(R.id.editText);
        data="";
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
            public void onCallback(String value,int i) {
                data=data+" Notice "+i+" is "+value+"\n";
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
    public void readData(final CollegeNotice.MyCallback myCallback) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Ncontent");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value1;
                int i=0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    value1 = snapshot.child("notice").getValue(String.class);
                    i++;
                    myCallback.onCallback(value1,i);
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
        void onCallback(String value,int n);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(CollegeNotice.this,Home.class));
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
