package com.example.adminlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Notice_Update extends AppCompatActivity {
    private EditText nameEditText;
    Button searchButton,delButton;
    String c_name,newDetail;
    private EditText neditText,deditText,valeditText;
    ListView noticelv;
    ArrayList<String>not_arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice__update);

        nameEditText=(EditText)findViewById(R.id.nameEditText);
        neditText=(EditText)findViewById(R.id.neditText);
        deditText=(EditText)findViewById(R.id.deditText);
        delButton=(Button)findViewById(R.id.delButton);
        noticelv=(ListView)findViewById(R.id.noticelv);
        not_arr = new ArrayList<>();




        DatabaseReference ref_1=FirebaseDatabase .getInstance().getReference("Ncontent");
        ref_1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    String item1=ds.getKey();
                    not_arr.add(item1);
                }

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,not_arr);
        noticelv.setAdapter(arrayAdapter);

        noticelv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                {
                   // c_name=neditText.getText().toString();

                    String c_name=(noticelv.getItemAtPosition(i)).toString();
                    neditText.setText(c_name);
                    DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Ncontent").child(c_name);

                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                            if(dataSnapshot.exists()) {
                                //Toast.makeText(Notice_Update.this, "Search clicked", Toast.LENGTH_SHORT).show();

                                String ndetail=dataSnapshot.child("notice").getValue().toString();
                                deditText.setText(ndetail);


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.w("onCancelled", databaseError.toException());
                        }
                    });
                }
            }
        });

        /*searchButton=(Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c_name=nameEditText.getText().toString();
                neditText.setText(c_name);

                DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Ncontent").child(c_name);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                        if(dataSnapshot.exists()) {
                            // Toast.makeText(Notice_Update.this, "Search clicked", Toast.LENGTH_SHORT).show();

                            String ndetail=dataSnapshot.child("notice").getValue().toString();
                            deditText.setText(ndetail);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("onCancelled", databaseError.toException());
                    }
                });
            }
        }); */

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
                DatabaseReference ref_1=FirebaseDatabase.getInstance().getReference().child("Ncontent").child(c_name);
                ref_1.removeValue();
                Toast.makeText(Notice_Update.this, "Deleted"+" "+c_name, Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void openDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(Notice_Update.this);
        View view=getLayoutInflater().inflate(R.layout.updat_dial,null);

        Button updatButton=(Button)view.findViewById(R.id.updatButton);
        valeditText=(EditText)view.findViewById(R.id.valeditText);

        updatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newDetail=valeditText.getText().toString();

                if(TextUtils.isEmpty(newDetail))
                {
                    valeditText.setError("Notice required");
                }
                updateNotice(newDetail);
                Toast.makeText(Notice_Update.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(view);

        AlertDialog dialog=builder.create();
        dialog.show();


    }
    private void updateNotice(String notice)
    {
        c_name=neditText.getText().toString();

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Ncontent").child(c_name);
        Ncontent nc=new Ncontent(notice);
        ref.setValue(nc);

        Toast.makeText(Notice_Update.this, "updated", Toast.LENGTH_SHORT).show();

    }
}
