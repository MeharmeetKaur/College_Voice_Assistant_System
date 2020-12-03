package com.example.adminlogin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListStudent extends AppCompatActivity {
    private ListView listView;
    private String email;
    private String division,rid;
    private View DailogView;
    ArrayList<String> lists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_student);
        listView=(ListView)findViewById(R.id.liststudents);
        lists=new ArrayList<>();
        lists.add("Add Student Information");
        lists.add("Update Student Information");
        lists.add("Delete Student");

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,lists);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               if(i==0){
                   startActivity(new Intent(ListStudent.this,AddStudent.class));
               }
               else if(i==1){
                   startActivity(new Intent(ListStudent.this,UpdateStudent.class));
               }
               else if(i==2){
                   showDeleteDialog();
               }
            }
        });
    }
    private void showDeleteDialog(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        LayoutInflater layoutInflater=getLayoutInflater();
        DailogView=layoutInflater.inflate(R.layout.delete_dialog,null);
        dialog.setView(DailogView);
        final Spinner spinnerClass=(Spinner)DailogView.findViewById(R.id.spinnerClass);
        final EditText editTextRID=(EditText)DailogView.findViewById(R.id.editTextRID);
        final Button delete=(Button)DailogView.findViewById(R.id.buttonDelete);
        dialog.setTitle("Deleting Student");
        final AlertDialog alertDialog=dialog.create();
        alertDialog.show();
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){}
                else{
                    division=adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
       delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               rid=editTextRID.getText().toString();
               if(division.equals("")||rid.equals("")){
                   Toast.makeText(ListStudent.this,"Enter details Properly!",Toast.LENGTH_SHORT).show();
               }
               else{
               DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Student").child("Class").child(division).child(rid);
               databaseReference.removeValue();
               Toast.makeText(ListStudent.this,"Student Deleted Successfully!",Toast.LENGTH_SHORT).show();
               alertDialog.dismiss();
           }
           }
       });
    }
}
