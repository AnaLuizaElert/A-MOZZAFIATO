package com.amozzafiato.pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.amozzafiato.R;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import de.cketti.mailto.EmailIntentBuilder;

public class PageCar extends AppCompatActivity {

    private static Integer carId;
    private static String nameCarIntent;
    private TextView
            nameCar, year, traction, qtyDoors, price, maxSpeed, exchanger, km, fuel, engine, cv, brand, category, linkToSeeMoreImages, linkToInterestForm;
    private ImageView mainPhoto, image1, image2, image3, linkComeBack;
    private Integer cont = 0;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_car);

        linkToSeeMoreImages = findViewById(R.id.page_car_button_see_more_images);
        linkToInterestForm = findViewById(R.id.page_car_button_interested);
        linkComeBack = findViewById(R.id.page_car_come_back);

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

        generateData();

        linkToSeeMoreImages.setOnClickListener(v -> {
            Intent intentLink = new Intent(PageCar.this, PageCarImages.class);
            generateData();
            intentLink.putExtra("chaveAtributo", carId);
            intentLink.putExtra("nameCar", nameCarIntent);
            startActivity(intentLink);
        });

        linkToInterestForm.setOnClickListener(v -> {
            showInterestFormDialog();
        });

        linkComeBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }


    private void generateData() {
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

                            price.setText("R$ " + document.getDouble("price"));

                            traction.setText(document.getString("traction"));
                            fuel.setText(document.getString("fuel"));
                            engine.setText(document.getString("engine"));
                            brand.setText(document.getString("brand"));
                            category.setText(document.getString("category"));
                            nameCar.setText(document.getString("name"));
                            nameCarIntent = document.getString("name");

                            if (Boolean.TRUE.equals(document.getBoolean("manual"))) {
                                exchanger.setText("manual");
                            } else {
                                exchanger.setText("automático");
                            }

                            // Carrega a imagem da URL usando o Glide
                            Glide.with(this)
                                    .load(document.getString("mainPhoto"))
                                    .into(mainPhoto);

                            db.collection("TbImages")
                                    .whereEqualTo("idCar", carId)
                                    .get()
                                    .addOnSuccessListener(querySnapshot -> {
                                        for (QueryDocumentSnapshot listDocuments : querySnapshot) {
                                            cont = cont + 1;

                                            if (cont == 1) {
                                                Glide.with(this)
                                                        .load(listDocuments.getString("url"))
                                                        .into(image1);
                                            } else if (cont == 2) {
                                                Glide.with(this)
                                                        .load(listDocuments.getString("url"))
                                                        .into(image2);
                                            } else if (cont == 3) {
                                                Glide.with(this)
                                                        .load(listDocuments.getString("url"))
                                                        .into(image3);
                                            }

                                        }
                                    });

                        }
                    }
                });
    }

    private void showInterestFormDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.activity_interest_form, null);
        builder.setView(dialogView);

        Button send = dialogView.findViewById(R.id.interest_form_button_send);
        TextView description = dialogView.findViewById(R.id.interest_form_description);

        send.setOnClickListener(v -> {
            String emailContent = description.getText().toString();

            // Crie uma Intent para enviar o email
            Intent emailIntent = EmailIntentBuilder.from(PageCar.this)
                    .to("ana_elert@estudante.sesisenai.org.br")
                    .subject("Entrar em contato")
                    .body(emailContent)
                    .build();

            startActivity(emailIntent);
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}