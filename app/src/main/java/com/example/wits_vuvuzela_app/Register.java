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

import java.util.regex.Pattern;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    FirebaseAuth firebaseauth;
    DatabaseReference databaseReference;
    UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

      /**  SetupUI();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserProfile");
        firebaseauth = FirebaseAuth.getInstance();
        userProfile = new UserProfile();

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
        });*/
    }

    private void registerUser(){

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

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private boolean validateEmail(){

        String emailIput = EmailAddress.getEditableText().toString().trim();

        if(emailIput.isEmpty()){
            EmailAddress.setError("Field cannot be empty");
            return false;
        }else if(emailIput.length() < 5){
            EmailAddress.setError("Email Address too short");
            return false;
        }else EmailAddress.setError(null);
        return true;
    }

    public  void confirmInput(View v){
        if(!validateEmail()){
            return;
        }

     //   String input = "Email: " + EmailAddress.getEditableText().toString();

        Toast.makeText(this,"invalid email", Toast.LENGTH_SHORT).show();
    }

    private void SendEmailConfirmation(){

        firebaseauth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    String User_fName = FirstName.getText().toString().trim();
                   String User_lName = LastName.getText().toString().trim();
                    String User_username = Username.getText().toString().trim();
                    String User_email = EmailAddress.getText().toString().trim();
                    String User_password = Password.getText().toString().trim();

                    userProfile.setUser_email(User_email);
                    userProfile.setUser_fName(User_fName);
                    userProfile.setUser_lName(User_lName);
                    userProfile.setUser_username(User_username);
                    userProfile.setUser_password(User_password);

                    databaseReference.push().setValue(userProfile);
                    Toast.makeText(Register.this, "Registration was Succesful Please Check Your Email For Verification Code", Toast.LENGTH_LONG).show();
                }

                else{
                    Toast.makeText(Register.this, "Send Email Verification failed please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void SetupUI(){

        FirstName = (EditText)findViewById(R.id.EdtxtFirstNameReg);
        LastName = (EditText)findViewById(R.id.EdtxtLastNameReg);
        Username = (EditText)findViewById(R.id.EdtxtUsernameReg);
        EmailAddress = (EditText)findViewById(R.id.EdtxtEmailAddressReg);
        Password = (EditText)findViewById(R.id.EdtxtPasswordReg);
        PasswordConfirmation = (EditText)findViewById(R.id.EdtxtPasswordConfirmationReg);
        Register = (Button)findViewById(R.id.BtnRegisterReg);
        Login = (TextView)findViewById(R.id.txtViewLoginReg);

    }
}
