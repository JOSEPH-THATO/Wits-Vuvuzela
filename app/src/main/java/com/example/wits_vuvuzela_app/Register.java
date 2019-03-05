package com.example.wits_vuvuzela_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.sql.DatabaseMetaData;

public class Register extends AppCompatActivity {

    EditText FirstName;
    EditText LastName;
    EditText Username;
    EditText EmailAddress;
    EditText Password;
    EditText PasswordConfirmation;
    Button Register;
    TextView Login;

    private FirebaseAuth firebaseauth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseauth = FirebaseAuth.getInstance();

        FirstName = (EditText)findViewById(R.id.EdtxtFirstNameReg);
        LastName = (EditText)findViewById(R.id.EdtxtLastNameReg);
        Username = (EditText)findViewById(R.id.EdtxtUsernameReg);
        EmailAddress = (EditText)findViewById(R.id.EdtxtEmailAddressReg);
        Password = (EditText)findViewById(R.id.EdtxtPasswordReg);
        PasswordConfirmation = (EditText)findViewById(R.id.EdtxtPasswordConfirmationReg);
        Register = (Button)findViewById(R.id.BtnRegisterReg);
        Login = (TextView)findViewById(R.id.txtViewLoginReg);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerUser();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterLoginPage();
            }
        });
    }

    private void registerUser(){

        String user_fName = FirstName.getText().toString().trim();
        String User_lName = LastName.getText().toString().trim();
        String User_username = Username.getText().toString().trim();
        String User_email = EmailAddress.getText().toString().trim();
        String User_password = Password.getText().toString().trim();


        firebaseauth.createUserWithEmailAndPassword(User_email,User_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "successful", Toast.LENGTH_SHORT).show();
                            SendEmailConfirmation();
                        }
                        else {
                            Toast.makeText(Register.this, "failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    private void EnterLoginPage() {

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

    private void SendEmailConfirmation(){

        firebaseauth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Toast.makeText(Register.this, "Registration was Succesful Please Check Your Email For Verification Code", Toast.LENGTH_LONG).show();
                }

                else{
                    Toast.makeText(Register.this, "Send Email Verification failed please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
