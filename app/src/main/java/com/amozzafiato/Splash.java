package com.amozzafiato;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.amozzafiato.pages.Login;
import com.google.firebase.FirebaseApp;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        Intent intent = new Intent(Splash.this, Login.class);
        startActivity(intent);
        finish();
    }
}