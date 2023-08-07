package com.amozzafiato.pages.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.amozzafiato.R;

public class ProfileNegotiate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_negotiate);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button linkPage2 = findViewById(R.id.profile_negotiate_button_next);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView linkProfile = findViewById(R.id.profile_negotiate_come_back_profile);

        linkPage2.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileNegotiate.this, ProfileNegotiate2.class);
            startActivity(intent);
            finish();
        });

        linkProfile.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}