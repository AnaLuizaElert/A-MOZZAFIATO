package com.soundeeper.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.soundeeper.MainActivity;
import com.soundeeper.NavigationPage;
import com.soundeeper.R;

public class Login extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        Button login = findViewById(R.id.loginButton);
        TextInputEditText user = findViewById(R.id.UserNameEdit);
        TextInputEditText password = findViewById(R.id.UserPasswordEdit);
        TextInputLayout userLayout = findViewById(R.id.UserNameLayout);
        TextInputLayout passwordLayout = findViewById(R.id.UserPasswordLayout);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView linkTextView = findViewById(R.id.textView10);

        linkTextView.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Cadastro.class);
            startActivity(intent);
        });

        login.setOnClickListener(v -> {
            if (user.getText().toString().isEmpty() && password.getText().toString().isEmpty()) {
                userLayout.setError("Campo obrigatório");
                passwordLayout.setError("Campo obrigatório");
            }

            if (user.getText().toString().isEmpty()) {
                userLayout.setError("Campo obrigatório");
            } else {
                userLayout.setError(null);
            }

            if (password.getText().toString().isEmpty()) {
                passwordLayout.setError("Campo obrigatório");
            } else {
                passwordLayout.setError(null);
            }

            if (!user.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                userLayout.setError(null);
                passwordLayout.setError(null);
                Intent intent = new Intent(Login.this, NavigationPage.class);
                startActivity(intent);
                finish();
            }
        });


    }
}