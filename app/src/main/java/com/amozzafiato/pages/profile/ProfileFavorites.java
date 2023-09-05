package com.amozzafiato.pages.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amozzafiato.AdapterFavorites;
import com.amozzafiato.FavoriteCarViewModel;
import com.amozzafiato.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProfileFavorites extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterFavorites adapter;
    private FavoriteCarViewModel carViewModel;
    private List<Double> listFavorites = new ArrayList<>();
    private ImageView linkProfile;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_favorites);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String idUser = user.getUid();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdapterFavorites(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        carViewModel = new ViewModelProvider(this).get(FavoriteCarViewModel.class);

        carViewModel.getCars().observe(this, cars -> {
            adapter.updateItems(cars);
        });


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TbFavorites")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        if (documentSnapshot.getString("idUser").equals(idUser)) {
                            listFavorites.add(documentSnapshot.getDouble("idCar"));
                        }
                    }
                    carViewModel.loadCars(listFavorites);
                });

        linkProfile = findViewById(R.id.page_favorites_arrow_back);

        linkProfile.setOnClickListener(v -> {
            onBackPressed();
        });

    }
}

