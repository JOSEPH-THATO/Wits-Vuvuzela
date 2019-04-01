package com.example.wits_vuvuzela_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity {

    TextView FName;
    TextView LName;
    TextView UName;
    TextView Email;
    TextView Password;
    private DatabaseReference databaseReference;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

     /**/
    }

}


/*
    Intent intent = getIntent();
        email = intent.getStringExtra("Email");

                databaseReference = FirebaseDatabase.getInstance().getReference("UserProfile");

                FName = (TextView) findViewById(R.id.tvFirstName);
                LName = (TextView) findViewById(R.id.tvLastName);
                UName = (TextView) findViewById(R.id.tvUserName);
                Email = (TextView) findViewById(R.id.tvEmail);
//FName = (TextView)findViewById(R.id.tvP);
*/

    /*
    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                    UserProfile userProfile = artistSnapshot.getValue(UserProfile.class);

                    if (userProfile.getUser_email().equals(email)) {

                        FName.setText(userProfile.getUser_fName());
                        LName.setText(userProfile.getUser_lName());
                        UName.setText(userProfile.getUser_username());
                        Email.setText(userProfile.getUser_email());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
*/
