package com.amozzafiato.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.amozzafiato.AdapterFavorites;
import com.amozzafiato.AdapterSearching;
import com.amozzafiato.Favorite;
import com.amozzafiato.R;
import com.amozzafiato.Searching;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class SearchingCar extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_car);

        // Recuperando o parâmetro da Intent
        Intent intent = getIntent();
        if (intent != null) {
            String category = intent.getStringExtra("category");
            if(category != null){
                // Modificando o campo de texto de acordo com o parâmetro
                TextView categoryTextView = findViewById(R.id.searching_car_line_title);
                categoryTextView.setText("Coleção " + category);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

//                db.collection("TbAuxCarCategory").document(userId).get()
//                        .addOnSuccessListener(documentSnapshot -> {
//                            if (documentSnapshot.exists()) {
//                                inputName.setText(documentSnapshot.getString("name"));
//                                inputEmail.setText(documentSnapshot.getString("email"));
//                                inputCountry.setText(documentSnapshot.getString("country"));
//                                inputPassword.setText(documentSnapshot.getString("password"));
//                                inputState.setText(documentSnapshot.getString("state"));
//                                Glide.with(this)
//                                        .load(documentSnapshot.getString("image"))
//                                        .into(imageProfile);
//                            }
//                        });


            }
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Searching.createCars();
        ArrayList<Searching> list = Searching.listCars;
        AdapterSearching adapter = new AdapterSearching(list);
        recyclerView.setAdapter(adapter);


    }
}