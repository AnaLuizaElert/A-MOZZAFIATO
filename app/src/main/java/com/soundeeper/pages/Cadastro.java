package com.soundeeper.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.soundeeper.MainActivity;
import com.soundeeper.NavigationPage;
import com.soundeeper.R;

public class Cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Button register = findViewById(R.id.cadastroButton);

        register.setOnClickListener(v -> {

            Intent intent = new Intent(Cadastro.this, NavigationPage.class);
            startActivity(intent);
            finish();
        });
    }
}