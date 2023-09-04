package com.amozzafiato.pages.profile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amozzafiato.AdapterFavorites;
import com.amozzafiato.FavoriteCarViewModel;
import com.amozzafiato.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ProfileFavorites extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterFavorites adapter;
    private FavoriteCarViewModel carViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_favorites);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String idUser = user.getUid();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdapterFavorites(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        carViewModel = new ViewModelProvider(this).get(FavoriteCarViewModel.class);

        carViewModel.getCars().observe(this, cars -> {
            adapter.updateItems(cars);
        });

        carViewModel.loadCars(idUser);

    }
}

//        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView linkProfile = findViewById(R.id.profile_favorites_come_back_profile);
//
//        linkProfile.setOnClickListener(v -> {
//            onBackPressed();
//        });
