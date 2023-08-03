package com.amozzafiato.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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
        ArrayList<Favorite> list = Favorite.listContact;
        MyAdapter adapter = new MyAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}