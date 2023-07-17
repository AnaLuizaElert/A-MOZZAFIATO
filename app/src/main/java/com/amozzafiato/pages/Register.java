package com.amozzafiato.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.TextView;

import com.amozzafiato.NavigationPage;
import com.amozzafiato.R;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        TextView linkToLogin = findViewById(R.id.linkRegisterLogin);
        String linkTextLogin = getResources().getString(R.string.link_forgot_password);
        SpannableString spannableStringPassword = new SpannableString(linkTextLogin);
        spannableStringPassword.setSpan(new UnderlineSpan(), 0, linkTextLogin.length(), 0);
        linkToLogin.setText(spannableStringPassword);

        Button register = findViewById(R.id.registerButton);

        register.setOnClickListener(v -> {

            Intent intent = new Intent(Register.this, NavigationPage.class);
            startActivity(intent);
            finish();
        });
    }
}