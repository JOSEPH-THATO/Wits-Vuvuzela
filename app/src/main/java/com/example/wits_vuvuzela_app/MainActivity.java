package com.example.wits_vuvuzela_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText Username;
    EditText Password;
    Button Login;
    TextView ForgotPassword;
    TextView Register;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    private ProgressBar progressBar;
    ArrayList<String> ArticlesHead;
    ArrayList<String> ArticlesAuth;
    String User="";
    String Email= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetupUserInterface();

        progressBar.setVisibility(View.GONE);
        TextView NumLikes;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserProfile");

        firebaseAuth = FirebaseAuth.getInstance();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Email = Username.getText().toString().trim();
                Email = "abdullah";

                progressBar.setVisibility(View.VISIBLE);
                Login.setVisibility(View.GONE);

                firebaseAuth.signInWithEmailAndPassword(Username.getText().toString(),Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful()){

                          firebaseUser = firebaseAuth.getCurrentUser();

                          if(firebaseUser.isEmailVerified()) {
                              progressBar.setVisibility(View.GONE);
                              Login.setVisibility(View.VISIBLE);
                              getUserName();
                              EnterHomePage();
                              Username.setText("");
                              Password.setText("");
                          }

                          else{
                              progressBar.setVisibility(View.GONE);
                              Login.setVisibility(View.VISIBLE);
                              Toast.makeText(MainActivity.this,"Email not yet verified , Please Go To Your Email",Toast.LENGTH_SHORT).show();
                          }
                      }
                      else {

                          progressBar.setVisibility(View.GONE);
                          Login.setVisibility(View.VISIBLE);
                          Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                          Username.setError("Wrong Email of password");

                      }
                    }
                });

            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterRegistrationPage();
            }
        });
    }


    private void EnterRegistrationPage() {

        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    private void EnterHomePage() {

        Intent intent = new Intent(this,HomePage.class);
        intent.putExtra("Email", User);
        startActivity(intent);
    }

    private void SetupUserInterface(){

        Username = (EditText) findViewById(R.id.EdtxtUsernameLogin);
        Password = (EditText) findViewById(R.id.EdTxtPasswordLogin);
        Login = (Button) findViewById(R.id.BtnLogin);
        ForgotPassword = (TextView) findViewById(R.id.txtViewForgotPasswordLogin);
        Register = (TextView) findViewById(R.id.txtViewRegisterLogin);
        progressBar = (ProgressBar)findViewById(R.id.progressBarLog);

    }

    public void getUserName(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                    UserProfile userProfile = artistSnapshot.getValue(UserProfile.class);

                    if (userProfile.getUser_email().equals(Username.getText().toString().trim())) {
                        User = userProfile.getUser_username();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
