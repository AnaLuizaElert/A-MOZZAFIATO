package com.amozzafiato.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.amozzafiato.NavigationPage;
import com.amozzafiato.R;

public class Login extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        TextView linkToRegister = findViewById(R.id.linkLoginRegister);
        String linkTextRegister = getResources().getString(R.string.link_register);
        SpannableString spannableStringRegister = new SpannableString(linkTextRegister);
        spannableStringRegister.setSpan(new UnderlineSpan(), 0, linkTextRegister.length(), 0);
        linkToRegister.setText(spannableStringRegister);

        TextView linkForgotPassword = findViewById(R.id.linkLoginForgotPassword);
        String linkTextForgotPassword = getResources().getString(R.string.link_forgot_password);
        SpannableString spannableStringPassword = new SpannableString(linkTextForgotPassword);
        spannableStringPassword.setSpan(new UnderlineSpan(), 0, linkTextForgotPassword.length(), 0);
        linkForgotPassword.setText(spannableStringPassword);


        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button linkToHome = findViewById(R.id.loginButton);

        linkToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });

        linkForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });

        linkToHome.setOnClickListener(v -> {
                Intent intent = new Intent(Login.this, NavigationPage.class);
                startActivity(intent);
                finish();
        });


    }
}