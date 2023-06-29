package com.soundeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.soundeeper.pages.Login;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

                Intent intent = new Intent(splash.this, Login.class);
                startActivity(intent);
                finish();
    }
}