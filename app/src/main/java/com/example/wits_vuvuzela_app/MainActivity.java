package com.example.wits_vuvuzela_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText Username;
    EditText Password;
    Button Login;
    TextView ForgotPassword;
    TextView Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = (EditText)findViewById(R.id.EdtxtUsernameLogin);
        Password = (EditText)findViewById(R.id.EdTxtPasswordLogin);
        Login = (Button)findViewById(R.id.BtnLogin);
        ForgotPassword = (TextView)findViewById(R.id.txtViewForgotPasswordLogin);
        Register = (TextView)findViewById(R.id.txtViewRegisterLogin);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterHomePage();
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

        Intent intent = new Intent(this,Register.class);
        startActivity(intent);


    }

    private void EnterHomePage() {

        //Intent intent = new Intent(this,Register.class);
        //startActivity(intent);
    }
}
