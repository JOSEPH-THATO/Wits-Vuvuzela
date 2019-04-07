package com.example.wits_vuvuzela_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                   // "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    EditText FirstName;
    EditText LastName;
    EditText Username;
    EditText EmailAddress;
    EditText Password;
    EditText PasswordConfirmation;
    Button Register;
    TextView Login;
    ProgressBar progressBar;
    FirebaseAuth firebaseauth;
    DatabaseReference databaseReference;
    UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SetupUI();

        progressBar.setVisibility(View.GONE);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserProfile");
        firebaseauth = FirebaseAuth.getInstance();
        userProfile = new UserProfile();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEmail();
                validatePassword();
                validateUsername();

                if(validateEmail() && validatePassword() && validateUsername()) {
                    registerUser();
                }
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

        progressBar.setVisibility(View.VISIBLE);
        Register.setVisibility(View.GONE);

        String User_email = EmailAddress.getText().toString().trim();
        String User_password = Password.getText().toString().trim();

        firebaseauth.createUserWithEmailAndPassword(User_email,User_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            progressBar.setVisibility(View.GONE);
                            SendEmailConfirmation();
                        }
                        else {
                            Register.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void EnterLoginPage() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private boolean validateEmail() {

        String emailInput = EmailAddress.getText().toString().trim();

        if (emailInput.isEmpty()) {
            EmailAddress.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            EmailAddress.setError("Please enter a valid email address");
            return false;
        } else {
            EmailAddress.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {

        String usernameInput = Username.getText().toString().trim();

        if (usernameInput.isEmpty()) {
            Username.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            Username.setError("Username too long");
            return false;
        } else {
            Username.setError(null);
            return true;
        }

    }

    private boolean validatePassword() {

        String passwordInput = Password.getText().toString().trim();
        String PasswordCorn = PasswordConfirmation.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            Password.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            Password.setError("Password too weak");
            return false;
        }else if (!passwordInput.equals(PasswordCorn))
                    {
                        PasswordConfirmation.setError("Password does not match");
                        return  false;
                    }
        else {
            Password.setError(null);
            return true;
        }

    }

    public void confirmInput(View v) {
        if (!validateEmail() | !validateUsername() | !validatePassword()) {
            return;
        }

        String input = "Email: " + EmailAddress.getText().toString();
        input += "\n";
        input += "Username: " + Username.getText().toString();
        input += "\n";
        input += "Password: " + Password.getText().toString();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Register.this, "Registration was Successful Please Check Your Email For Verification Code", Toast.LENGTH_LONG).show();
                    EnterLoginPage();
                    FirstName.setText("");
                    LastName.setText("");
                    Username.setText("");
                    EmailAddress.setText("");
                    Password.setText("");
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
        progressBar = (ProgressBar)findViewById(R.id.progressBarReg);

    }
}
