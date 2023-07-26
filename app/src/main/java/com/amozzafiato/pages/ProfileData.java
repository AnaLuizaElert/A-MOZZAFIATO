package com.amozzafiato.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import com.amozzafiato.R;

public class ProfileData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_data);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView linkProfile = findViewById(R.id.profile_data_come_back_profile);

        linkProfile.setOnClickListener(v -> {
                onBackPressed();
        });

    }
}