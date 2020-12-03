package com.example.adminlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewQuery extends AppCompatActivity {
    private TextView textView;
    private DatabaseReference databaseReference;
    private String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_query);
        textView=(TextView)findViewById(R.id.textviewquery);
        data="";
        readData(new MyCallback() {
            @Override
            public void onCallback(String key) {
                textView.setText(key);
            }
        });

    }
    public void readData(final MyCallback myCallback) {
        databaseReference= FirebaseDatabase.getInstance().getReference("Queries");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot childSnapshot:dataSnapshot.getChildren()){
                    String value=childSnapshot.getValue(String.class);
                    data=data+value+"\n";
                    myCallback.onCallback(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public interface MyCallback {
        void onCallback(String key);
    }
}
