package com.amozzafiato.pages.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.amozzafiato.AdapterFavorites;
import com.amozzafiato.Favorite;
import com.amozzafiato.R;

import java.util.ArrayList;

public class ProfileFavorites extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_favorites);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Favorite.createFavorites();
        ArrayList<Favorite> list = Favorite.listFavorite;
        AdapterFavorites adapter = new AdapterFavorites(list);
        recyclerView.setAdapter(adapter);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView linkProfile = findViewById(R.id.profile_favorites_come_back_profile);

        linkProfile.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}