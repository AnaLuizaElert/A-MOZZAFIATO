package com.amozzafiato.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.amozzafiato.R;
import com.amozzafiato.pages.fragments.Profile;

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
        });

        linkProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileNegotiate.this, Profile.class);
            startActivity(intent);
        });
    }
}