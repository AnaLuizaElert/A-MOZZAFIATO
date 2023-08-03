package com.amozzafiato.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.amozzafiato.R;

public class PageCar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_car);

        TextView linkToSeeMoreImages = findViewById(R.id.page_car_button_see_more_images);
        TextView linkToInterestForm = findViewById(R.id.page_car_button_interested);

        linkToSeeMoreImages.setOnClickListener(v -> {
            Intent intent = new Intent(PageCar.this, PageCarImages.class);
            startActivity(intent);
        });

        linkToInterestForm.setOnClickListener(v -> {
            Intent intent = new Intent(PageCar.this, InterestForm.class);
            startActivity(intent);
        });
    }
}