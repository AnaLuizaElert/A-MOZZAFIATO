package com.amozzafiato.pages;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.recaptcha.RecaptchaException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import android.accounts.NetworkErrorException;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amozzafiato.NavigationPage;
import com.amozzafiato.R;

import java.io.IOException;

public class Login extends AppCompatActivity {

    private TextInputEditText email, password;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(isConnected) {

            email = findViewById(R.id.login_email_edit);
            password = findViewById(R.id.login_password_edit);

            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView linkToRegister = findViewById(R.id.login_link_register);
            String linkTextRegister = getResources().getString(R.string.link_register);
            SpannableString spannableStringRegister = new SpannableString(linkTextRegister);
            spannableStringRegister.setSpan(new UnderlineSpan(), 0, linkTextRegister.length(), 0);
            linkToRegister.setText(spannableStringRegister);

            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView linkForgotPassword = findViewById(R.id.login_link_forgot_password);
            String linkTextForgotPassword = getResources().getString(R.string.link_forgot_password);
            SpannableString spannableStringPassword = new SpannableString(linkTextForgotPassword);
            spannableStringPassword.setSpan(new UnderlineSpan(), 0, linkTextForgotPassword.length(), 0);
            linkForgotPassword.setText(spannableStringPassword);


            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button linkToHome = findViewById(R.id.login_button_login);

            linkToRegister.setOnClickListener(v -> {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            });

            linkForgotPassword.setOnClickListener(v -> {
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);
            });

            linkToHome.setOnClickListener(v -> {
//                String userEmail = ((TextInputEditText) findViewById(R.id.login_email_edit)).getText().toString();
//                String userPassword = ((TextInputEditText) findViewById(R.id.login_password_edit)).getText().toString();
//
//                try {
//                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
//                    mAuth.signInWithEmailAndPassword(userEmail, userPassword)
//                            .addOnCompleteListener(this, task -> {
//                                if (task.isSuccessful()) {
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    // A autenticação foi bem-sucedida, você pode redirecionar o usuário para a próxima tela aqui
                                    Intent intent = new Intent(Login.this, NavigationPage.class);
                                    startActivity(intent);
                                    finish();
//                                } else {
//                                    // A autenticação falhou, você pode mostrar uma mensagem de erro ao usuário aqui
//                                    Exception e = task.getException();
//
//                                    if (e instanceof FirebaseAuthException) {
//                                        email.setError("Dados inconsistentes!");
//                                        password.setError("Dados inconsistentes!");
//                                    }
//                                }
//                            });
//                } catch (Exception e) {
//                    if (userEmail.equals("")) {
//                        email.setError("Preencha este campo!");
//                    }
//
//                    if (userPassword.equals("")) {
//                        password.setError("Preencha este campo!");
//                    }
//
//                    if (!userEmail.equals("") && !userPassword.equals("")) {
//                        Toast.makeText(Login.this, "Falha ao fazer login.", Toast.LENGTH_SHORT).show();
//                    }
//                }
            });
        } else {
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.layout_dialog_error_network, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView);

            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }
}
