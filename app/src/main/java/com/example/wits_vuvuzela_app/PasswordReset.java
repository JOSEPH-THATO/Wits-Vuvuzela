package com.example.wits_vuvuzela_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordReset extends AppCompatActivity {

    private EditText emailPassword;
    private Button btnResetPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        emailPassword = (EditText) findViewById(R.id.etPasswordEmail);
        btnResetPassword = (Button) findViewById(R.id.btnPasswordReset);
        firebaseAuth = FirebaseAuth.getInstance();

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = emailPassword.getText().toString().trim();

                if (userEmail.equals("")) {
                    Toast.makeText(PasswordReset.this, "Enter registered Email", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PasswordReset.this,"Password reset email sent", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(PasswordReset.this, MainActivity.class));
                            }else{
                                Toast.makeText(PasswordReset.this, "Error sending email", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
            }
        });
    }
}
