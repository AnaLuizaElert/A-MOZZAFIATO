package com.amozzafiato.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.amozzafiato.R;

public class PageCarImages extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_car_images);

        ImageView linkToInterestForm = findViewById(R.id.page_car_arrow_back);

        linkToInterestForm.setOnClickListener(v -> {
            Intent intent = new Intent(PageCarImages.this, PageCar.class);
            startActivity(intent);
        });
    }
}