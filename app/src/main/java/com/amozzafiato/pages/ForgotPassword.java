package com.amozzafiato.pages;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amozzafiato.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private Button sendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        sendEmail = findViewById(R.id.forgot_password_button);

        sendEmail.setOnClickListener(v -> {
            String userEmail = ((TextInputEditText) findViewById(R.id.forgot_password_email_edit)).getText().toString();

            if (!userEmail.isEmpty()) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.sendPasswordResetEmail(userEmail)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPassword.this, "Email de recuperação de senha enviado.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ForgotPassword.this, "Falha ao enviar o email de recuperação de senha.", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(ForgotPassword.this, "Insira seu email para recuperação de senha.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}