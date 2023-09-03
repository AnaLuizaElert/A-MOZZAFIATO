package com.amozzafiato.pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amozzafiato.R;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class PageCar extends AppCompatActivity {

    private static Integer carId;
    private TextView
            nameCar, year, traction, qtyDoors, price, maxSpeed, exchanger, km, fuel, engine, cv, brand, category, linkToSeeMoreImages, linkToInterestForm;
    private ImageView mainPhoto, image1, image2, image3;
    private Integer cont = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_car);

        linkToSeeMoreImages = findViewById(R.id.page_car_button_see_more_images);
        linkToInterestForm = findViewById(R.id.page_car_button_interested);

        /*inputs*/
        nameCar = findViewById(R.id.page_car_name);
        mainPhoto = findViewById(R.id.page_car_image);
        year = findViewById(R.id.page_car_year);
        traction = findViewById(R.id.page_car_traction);
        qtyDoors = findViewById(R.id.page_car_doors);
        price = findViewById(R.id.page_car_price);
        maxSpeed = findViewById(R.id.page_car_engine_max_speed);
        exchanger = findViewById(R.id.page_car_exchange);
        km = findViewById(R.id.page_car_km);
        fuel = findViewById(R.id.page_car_fuel);
        engine = findViewById(R.id.page_car_engine);
        cv = findViewById(R.id.page_car_horse_power);
        brand = findViewById(R.id.page_car_brand);
        category = findViewById(R.id.page_car_category);

        image1 = findViewById(R.id.page_car_secondary_image_1);
        image2 = findViewById(R.id.page_car_secondary_image_2);
        image3 = findViewById(R.id.page_car_secondary_image_3);

        linkToSeeMoreImages.setOnClickListener(v -> {
            Intent intent = new Intent(PageCar.this, PageCarImages.class);
            intent.putExtra("chaveAtributo", 1);
            startActivity(intent);
        });

        linkToInterestForm.setOnClickListener(v -> {
            Intent intent = new Intent(PageCar.this, InterestForm.class);
            startActivity(intent);
        });

        /*Buscar dados do carro no firebase*/
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String carName;
        Intent intent = getIntent();
        if (intent != null) {
            carName = intent.getStringExtra("carName");
        } else {
            carName = "";
        }

        db.collection("TbCar").get()
                .addOnSuccessListener(documentSnapshot -> {
                    for (DocumentSnapshot document : documentSnapshot) {
                        if (document.get("name").equals(carName)) {

                            carId = Integer.valueOf(document.getId());

                            Integer yearConvert = document.getDouble("year").intValue();
                            Integer qtyDoorsConvert = document.getDouble("qtyDoors").intValue();
                            Integer maxSpeedConvert = document.getDouble("maxSpeed").intValue();
                            Integer kmConvert = document.getDouble("km").intValue();
                            Integer cvConvert = document.getDouble("cv").intValue();

                            year.setText(yearConvert.toString());
                            qtyDoors.setText(qtyDoorsConvert.toString());
                            maxSpeed.setText(maxSpeedConvert.toString());
                            km.setText(kmConvert.toString());
                            cv.setText(cvConvert.toString());

                            price.setText(document.getDouble("price").toString());

                            nameCar.setText(document.getString("name"));
                            traction.setText(document.getString("traction"));
                            fuel.setText(document.getString("fuel"));
                            engine.setText(document.getString("engine"));
                            brand.setText(document.getString("brand"));
                            category.setText(document.getString("category"));

                            if (Boolean.TRUE.equals(document.getBoolean("manual"))) {
                                exchanger.setText("manual");
                            } else {
                                exchanger.setText("automático");
                            }

                            // Carrega a imagem da URL usando o Glide
                            Glide.with(this)
                                    .load(document.getString("mainPhoto"))
                                    .into(mainPhoto);
                        }
                    }
                });

        // Execute a consulta para buscar documentos na coleção "TbImages" com base no carId
        db.collection("TbImages")
                .whereEqualTo("idCar", carId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                        cont = cont + 1;

                        if (cont == 1) {
                            Glide.with(this)
                                    .load(documentSnapshot.getString("url"))
                                    .into(image1);
                        } else if (cont == 2) {
                            Glide.with(this)
                                    .load(documentSnapshot.getString("url"))
                                    .into(image2);
                        } else if (cont == 3) {
                            Glide.with(this)
                                    .load(documentSnapshot.getString("url"))
                                    .into(image3);
                        }

                    }
                });
    }
}