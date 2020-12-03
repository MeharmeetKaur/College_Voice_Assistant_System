package com.example.adminlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Course_Update extends AppCompatActivity {


    //DatabaseReference reff;
    EditText nameEditText;
    Button searchButton,delButton;
    EditText ieditText,neditText,deditText,valeditText;
    ArrayList<String>co_arr;
    String c_name,newDetail,id;
    ListView colv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course__update);

        colv=(ListView)findViewById(R.id.colv);
        co_arr = new ArrayList<>();

        //nameEditText=(EditText)findViewById(R.id.nameEditText);
        neditText=(EditText)findViewById(R.id.neditText);
        ieditText=(EditText)findViewById(R.id.ieditText);
        deditText=(EditText)findViewById(R.id.deditText);
        delButton=(Button)findViewById(R.id.delButton);

        DatabaseReference ref1=FirebaseDatabase.getInstance().getReference("Course");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds:dataSnapshot.getChildren())
                    {
                        String item=ds.getKey();
                        co_arr.add(item);
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,co_arr);
        colv.setAdapter(arrayAdapter);

        colv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String c_name=(colv.getItemAtPosition(i)).toString();
                //c_name=nameEditText.getText().toString();

                neditText.setText(c_name);


                DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Course").child(c_name);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                        if(dataSnapshot.exists()) {
                     //       Toast.makeText(Course_Update.this, "Search clicked", Toast.LENGTH_SHORT).show();
                            id=dataSnapshot.child("cid").getValue().toString();
                            ieditText.setText(id);
                            String cdetail=dataSnapshot.child("detail").getValue().toString();
                            deditText.setText(cdetail);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("onCancelled", databaseError.toException());
                    }
                });
            }
        });

        //final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Course").child("ML");

      /*  searchButton=(Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c_name=nameEditText.getText().toString();

                neditText.setText(c_name);


                DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Course").child(c_name);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                        if(dataSnapshot.exists()) {
                            Toast.makeText(Course_Update.this, "Search clicked", Toast.LENGTH_SHORT).show();
                            id=dataSnapshot.child("cid").getValue().toString();
                            ieditText.setText(id);
                            String cdetail=dataSnapshot.child("detail").getValue().toString();
                            deditText.setText(cdetail);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("onCancelled", databaseError.toException());
                    }
                });
            }
        });*/

        deditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c_name=neditText.getText().toString();
                DatabaseReference ref_1=FirebaseDatabase.getInstance().getReference().child("Course").child(c_name);
                ref_1.removeValue();
                Toast.makeText(Course_Update.this, "Deleted"+c_name, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void openDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(Course_Update.this);
        View view=getLayoutInflater().inflate(R.layout.updat_dial,null);

        Button updatButton=(Button)view.findViewById(R.id.updatButton);
        valeditText=(EditText)view.findViewById(R.id.valeditText);


        updatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newDetail=valeditText.getText().toString();
                int cid=Integer.parseInt(id);
                // c_name=nameEditText.getText().toString();
                //String c_id=ieditText.getText().toString();

                if(TextUtils.isEmpty(newDetail))
                {
                    valeditText.setError("Course required");
                }
                updateCourse(cid,newDetail);
                Toast.makeText(Course_Update.this, "clicked", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setView(view);

        AlertDialog dialog=builder.create();
        dialog.show();


    }
    private void updateCourse(int cid,String detail )
    {
        c_name=neditText.getText().toString();

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Course").child(c_name);
        course_1 crc=new course_1(cid,detail);
        ref.setValue(crc);

        Toast.makeText(Course_Update.this, "updated", Toast.LENGTH_SHORT).show();

    }
}
