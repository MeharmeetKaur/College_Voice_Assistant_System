package com.example.adminlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Exam extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        listView=(ListView)findViewById(R.id.listView);

        ArrayList<String> arrayList=new ArrayList<String>();
        arrayList.add("Unit Test");
        arrayList.add("Online Exam");
        arrayList.add("In-Sem");
        arrayList.add("End-Sem");
        arrayList.add("Practicals");

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String exam_type=(listView.getItemAtPosition(i)).toString();
                Intent intent=new Intent(Exam.this,Exam_insert.class);
                Bundle bundle=new Bundle();
                bundle.putString("exam_name",exam_type);
                //startActivity(new Intent(Exam.this,TimeTable.class));
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });



    }
}
