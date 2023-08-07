package com.amozzafiato.pages;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amozzafiato.R;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Button sendEmail = findViewById(R.id.forgot_password_button);
        sendEmail.setOnClickListener(v -> {
            String userEmail = ((TextInputEditText) findViewById(R.id.forgot_password_email_edit)).getText().toString();

            if (!userEmail.isEmpty()) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.sendPasswordResetEmail(userEmail)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Email de recuperação de senha enviado com sucesso
                                Toast.makeText(ForgotPassword.this, "Email de recuperação de senha enviado.", Toast.LENGTH_SHORT).show();
                            } else {
                                // Falha ao enviar o email de recuperação de senha
                                Toast.makeText(ForgotPassword.this, "Falha ao enviar o email de recuperação de senha.", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                // O campo de email está vazio, informe o usuário
                Toast.makeText(ForgotPassword.this, "Insira seu email para recuperação de senha.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}