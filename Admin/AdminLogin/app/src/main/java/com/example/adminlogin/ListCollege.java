package com.example.adminlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ListCollege extends AppCompatActivity {
    private ListView listview;
    ArrayList<String> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_college);
        listview=(ListView)findViewById(R.id.listcollege);
        lists=new ArrayList<>();
        lists.add("Recent Trends");
        lists.add("Notice");
        lists.add("Time Table");
        lists.add("Exam");
        lists.add("Course Forum");

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,lists);
        listview.setAdapter(arrayAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    //startActivity(new Intent(ListCollege.this,RecentTrends.class));
                    openRTDialog();
                }
                else if(i==1){
                    //startActivity(new Intent(ListCollege.this,Notice.class));
                    openNoticeDialog();
                }
                else if(i==2){
                    startActivity(new Intent(ListCollege.this,TimeTable.class));
                    //openTimeTableDialog();
                }
                else if(i==3)
                {
                    // startActivity(new Intent(ListCollege.this,Exam.class));
                    openExamDialog();
                }
                else if(i==4)
                {
                    //startActivity(new Intent(ListCollege.this,Course.class));
                    openCourseDialog();
                }
            }
        });
    }
    public void openCourseDialog()
    {

        // setContentView(R.layout.au_dial);
        AlertDialog.Builder builder=new AlertDialog.Builder(ListCollege.this);
        // LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=getLayoutInflater().inflate(R.layout.au_dial,null);


        Button adbtn=(Button)view.findViewById(R.id.adbtn);
        Button upbtn=(Button)view.findViewById(R.id.upbtn);

        adbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent=new Intent(ListCollege.this,Course.class);
                        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        upbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListCollege.this,Course_Update.class));
            }
        });
        builder.setView(view);

        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public void openNoticeDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(ListCollege.this);
        View view=getLayoutInflater().inflate(R.layout.au_dial,null);

        Button adbtn=(Button)view.findViewById(R.id.adbtn);
        Button upbtn=(Button)view.findViewById(R.id.upbtn);

        adbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent=new Intent(ListCollege.this,Notice.class);
                        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        upbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListCollege.this,Notice_Update.class));
            }
        });
        builder.setView(view);

        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public void openRTDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(ListCollege.this);
        View view=getLayoutInflater().inflate(R.layout.au_dial,null);

        Button adbtn=(Button)view.findViewById(R.id.adbtn);
        Button upbtn=(Button)view.findViewById(R.id.upbtn);

        adbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent=new Intent(ListCollege.this,RecentTrends.class);
                        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        upbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListCollege.this,Trends_Update.class));
            }
        });
        builder.setView(view);

        AlertDialog dialog=builder.create();
        dialog.show();
    }

   /* public void openTimeTableDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(ListCollege.this);
        View view=getLayoutInflater().inflate(R.layout.au_dial,null);

        Button adbtn=(Button)view.findViewById(R.id.adbtn);
        Button upbtn=(Button)view.findViewById(R.id.upbtn);

        adbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent=new Intent(ListCollege.this,TimeTable.class);
                        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        upbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListCollege.this,TimeT_Update.class));
            }
        });
        builder.setView(view);

        AlertDialog dialog=builder.create();
        dialog.show();
    }*/

    public void openExamDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(ListCollege.this);
        View view=getLayoutInflater().inflate(R.layout.au_dial,null);

        Button adbtn=(Button)view.findViewById(R.id.adbtn);
        Button upbtn=(Button)view.findViewById(R.id.upbtn);

        adbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent=new Intent(ListCollege.this,Exam.class);
                        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        upbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListCollege.this,Exam_Update.class));
            }
        });
        builder.setView(view);

        AlertDialog dialog=builder.create();
        dialog.show();
    }
}
