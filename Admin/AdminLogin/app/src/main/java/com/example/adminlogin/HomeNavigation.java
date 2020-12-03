package com.example.adminlogin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeNavigation extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private String s;
    private TextView textViewId;
    private FirebaseAuth firebaseAuth;
    private CircleImageView circleImageViewStudent,circleImageViewCollege,circleImageViewQuery;
    private TextView username;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_navigation);
        s=getIntent().getStringExtra("email");
        firebaseAuth=FirebaseAuth.getInstance();
        Toast.makeText(this,"Signed In as:"+s,Toast.LENGTH_SHORT).show();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //textViewId=(TextView)toolbar.findViewById(R.id.textViewID);
        //textViewId.setText(s);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
         textViewId = (TextView) headerView.findViewById(R.id.textViewID);
        textViewId.setText(s);
        imageView=(ImageView)headerView.findViewById(R.id.imageViewAdmin);
        username=(TextView)headerView.findViewById(R.id.USerName);
        loadImage();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("AdminDetails");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("name").getValue(String.class);
                username.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       // mAppBarConfiguration = new AppBarConfiguration.Builder(
         //       R.id.myaccount, R.id.mypassword, R.id.logout)
           //     .setDrawerLayout(drawer)
             //   .build();
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //textViewId=(TextView)findViewById(R.id.textViewID);
        //textViewId.setText(""+s);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                if(id==R.id.myaccount){
                   startActivity(new Intent(HomeNavigation.this,AdminList.class).putExtra("email",s));
                }
                else if(id==R.id.mypassword){
                    startActivity(new Intent(HomeNavigation.this,ChangePassword.class));
                }
                else if(id==R.id.logout){
                   Toast.makeText(HomeNavigation.this,"Signing Out...",Toast.LENGTH_SHORT).show();
                   firebaseAuth.signOut();
                   finish();
                   startActivity(new Intent(HomeNavigation.this,MainActivity.class));
                }
                return true;
            }
        });
        circleImageViewStudent=(CircleImageView)findViewById(R.id.studentview);
        circleImageViewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeNavigation.this,ListStudent.class));
            }
        });
        circleImageViewCollege=(CircleImageView)findViewById(R.id.collegeview);
        circleImageViewCollege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeNavigation.this,ListCollege.class));
            }
        });
        circleImageViewQuery=(CircleImageView)findViewById(R.id.queryview);
        circleImageViewQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeNavigation.this,ViewQuery.class));
            }
        });
        invalidateOptionsMenu();
    }
    void loadImage(){
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        StorageReference storageReference1=firebaseStorage.getReference("AdminProfile") ;
        storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(imageView);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.home_navigation, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
