package com.amozzafiato.pages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amozzafiato.R;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class PageCarImages extends AppCompatActivity {

    private LinearLayout imagesContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_car_images);

        ImageView linkToInterestForm = findViewById(R.id.page_car_arrow_back);

        Intent intent = getIntent();
        Integer idCar = intent.getIntExtra("chaveAtributo", -1);
        String nameCar = intent.getStringExtra("nameCar");

        TextView inputNameCar = findViewById(R.id.page_car_name);
        inputNameCar.setText(nameCar);

        linkToInterestForm.setOnClickListener(v -> {
            onBackPressed();
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        imagesContainer = findViewById(R.id.page_car_images_container);

        // Execute a consulta para buscar documentos na coleção "TbImages" com base no carId
        db.collection("TbImages")
                .whereEqualTo("idCar", idCar)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {

                        ImageView imageView = new ImageView(this);

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );

                        layoutParams.setMargins(0, 0, 0, 40); // Define a margem inferior

                        imageView.setLayoutParams(layoutParams);

                        // Use o Glide para carregar a imagem a partir do URL
                        Glide.with(this)
                                .load(documentSnapshot.getString("url"))
                                .into(imageView);

                        // Adicione o ImageView ao LinearLayout
                        imagesContainer.addView(imageView);

                    }
                });
    }
}