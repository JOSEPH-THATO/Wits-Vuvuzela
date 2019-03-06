package com.example.wits_vuvuzela_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    EditText FirstName;
    EditText LastName;
    EditText Username;
    EditText EmailAddress;
    EditText Password;
    EditText PasswordConfirmation;
    Button Register;
    TextView Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirstName = (EditText) findViewById(R.id.EdtxtFirstNameReg);
        LastName = (EditText) findViewById(R.id.EdtxtLastNameReg);
        Username = (EditText) findViewById(R.id.EdtxtUsernameReg);
        EmailAddress = (EditText) findViewById(R.id.EdtxtEmailAddressReg);
        Password = (EditText) findViewById(R.id.EdtxtPasswordReg);
        PasswordConfirmation = (EditText) findViewById(R.id.EdtxtPasswordConfirmationReg);
        Register = (Button) findViewById(R.id.BtnRegisterReg);
        Login = (TextView) findViewById(R.id.txtViewLoginReg);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterLoginPage();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterLoginPage();
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

}
