package com.example.adminlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Trends_Update extends AppCompatActivity {


    EditText nameEditText;
    Button searchButton,delButton;
    String c_name,newDetail,dat;
    EditText ieditText,neditText,deditText,valeditText,dateditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends__update);

        nameEditText=(EditText)findViewById(R.id.nameEditText);
        neditText=(EditText)findViewById(R.id.neditText);
        dateditText=(EditText)findViewById(R.id.dateditText);
        // ieditText=(EditText)findViewById(R.id.ieditText);
        deditText=(EditText)findViewById(R.id.deditText);
        delButton=(Button)findViewById(R.id.delButton);


        searchButton=(Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c_name=nameEditText.getText().toString();
                neditText.setText(c_name);

                DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Recent_Trends").child(c_name);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                        if(dataSnapshot.exists()) {
                           // Toast.makeText(Trends_Update.this, "Search clicked", Toast.LENGTH_SHORT).show();
                            //id=dataSnapshot.child("cid").getValue().toString();
                            //ieditText.setText(id);
                            String actdetail=dataSnapshot.child("act").getValue().toString();
                            deditText.setText(actdetail);
                            dat=dataSnapshot.child("date").getValue().toString();
                            dateditText.setText(dat);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("onCancelled", databaseError.toException());
                    }
                });
            }
        });
        deditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();

            }
        });
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c_name=nameEditText.getText().toString();
                DatabaseReference ref_1=FirebaseDatabase.getInstance().getReference().child("Recent_Trends").child(c_name);
                ref_1.removeValue();
                Toast.makeText(Trends_Update.this, "Deleted"+c_name, Toast.LENGTH_SHORT).show();

            }
        });


    }
    private void openDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(Trends_Update.this);
        View view=getLayoutInflater().inflate(R.layout.updat_dial,null);

        Button updatButton=(Button)view.findViewById(R.id.updatButton);
        valeditText=(EditText)view.findViewById(R.id.valeditText);

        updatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newDetail=valeditText.getText().toString();

                // int nid=Integer.parseInt(id);
                // c_name=nameEditText.getText().toString();
                //String c_id=ieditText.getText().toString();



                if(TextUtils.isEmpty(newDetail))
                {
                    valeditText.setError("Course required");
                }
                updateTrends(newDetail,dat);
               // Toast.makeText(Trends_Update.this, "clicked", Toast.LENGTH_SHORT).show();

            }
        });

        builder.setView(view);

        AlertDialog dialog=builder.create();
        dialog.show();

    }

    private void updateTrends(String newDetail,String dat)
    {
        c_name=nameEditText.getText().toString();


        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Recent_Trends").child(c_name);
        RTrends nc=new RTrends(newDetail,dat);
        ref.setValue(nc);

        Toast.makeText(Trends_Update.this, "updated", Toast.LENGTH_SHORT).show();

    }




}
